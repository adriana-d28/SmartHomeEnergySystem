/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.server.battery;

import com.smarthome.battery.BatteryMode;
import com.smarthome.battery.BatteryStatusInfo;
import com.smarthome.battery.BatteryStorageServiceGrpc;
import com.smarthome.battery.ChargingStatus;
import com.smarthome.battery.GetBatteryStatusRequest;
import com.smarthome.battery.BatteryCommand;
import com.smarthome.battery.BatteryMonitoringRequest;
import com.smarthome.battery.BatteryMonitoringResponse;
import com.smarthome.integrationclient.SolarGrpcClient;
import com.smarthome.solar.ProductionInfo;
import io.grpc.stub.StreamObserver;



/**
 *
 * @author Adriana Dinelly - ID 25165771
 * 
 * This class implements the Battery Storage gRPC service.
 * 
 */
public class BatteryServiceImpl extends BatteryStorageServiceGrpc.BatteryStorageServiceImplBase {
    // Create a battery internal state instance
    private final BatteryData batteryData;
    // Internal client used to communicate with the Solar Service.
    private final SolarGrpcClient solarClient;
    // Constructor - after creating the service, the server initializes a standard state of the battery
    public BatteryServiceImpl() {
        batteryData = new BatteryData("BAT001", 75, ChargingStatus.CHARGING, BatteryMode.NORMAL);
            // Create the internal client used to communicate with the Solar Service.
            solarClient = new SolarGrpcClient();
    }

    // ======= Methods Development =======
    
    /**
    * Updates the battery status based on the current solar production.
    */
    private void updateBatteryStatus() {
        
        // Request the current solar production.
        ProductionInfo production = solarClient.getCurrentProduction();
        // Update the charging status according to the current production.
        if (production.getCurrentProduction() >= 5.0) {
            batteryData.setChargingStatus(ChargingStatus.CHARGING);
        } else {
            batteryData.setChargingStatus(ChargingStatus.DISCHARGING);
        }
    }    
    
    /**
     * ======= UNARY METHOD ========
     * 
     * Handles Unary requests that retrieve the current battery status (e.g. charging, fully charged etc).
     * 
     */
    @Override
    public void getBatteryStatus(GetBatteryStatusRequest request, StreamObserver<BatteryStatusInfo> responseObserver) {
        // test log - remove it
        System.out.println("Battery Service received a request from another service.");
        // Call the method to update the battery status according to the Solar Panel information
        updateBatteryStatus();
        // Create the response message using the Builder pattern: sets the data, the format and build.
        BatteryStatusInfo response = BatteryStatusInfo.newBuilder().setBatteryId(batteryData.getBatteryId())
                .setBatteryLevel(batteryData.getBatteryLevel()).setChargingStatus(batteryData.getChargingStatus())
                .setCurrentMode(batteryData.getCurrentMode()).build();
        
        // Send the response to the client - due to its unary nature, we only need one.
        responseObserver.onNext(response);
        // Finish and close connection
        responseObserver.onCompleted();
    }
    
    /**
     * ========= BIDIRECTIONAL METHOD ==========
     * 
     * Handles Bidirectional Streaming requests used to monitor and control the battery.
     * The method needs to pass a StreamObserver to monitor several requests 
     * whilst it returns a StreamObserver as well, for the client to monitor several responses.
     * 
     */
    @Override
    public StreamObserver<BatteryMonitoringRequest> monitorBatteryStatus(StreamObserver<BatteryMonitoringResponse> responseObserver) {

        return new StreamObserver<BatteryMonitoringRequest>() {

            @Override
            public void onNext(BatteryMonitoringRequest request) {
                // Call the method to update the battery status according to the Solar Panel information
                updateBatteryStatus();
                
                String message;
                // Process the command received from the client.
                switch (request.getCommand()) {
                    case START:
                        message = "Battery monitoring started.";
                        break;
                    case CHANGE_MODE:
                        batteryData.setCurrentMode(request.getMode());
                        message = "Battery mode changed to " + request.getMode() + ".";
                        break;
                    case STOP:
                        message = "Battery monitoring stopped.";
                        break;
                    default:
                        message = "Unknown command. Please try again.";
                        break;
                }

                // Create the battery information.
                BatteryStatusInfo batteryInfo = BatteryStatusInfo.newBuilder().setBatteryId(batteryData.getBatteryId())
                        .setBatteryLevel(batteryData.getBatteryLevel()).setChargingStatus(batteryData.getChargingStatus())
                        .setCurrentMode(batteryData.getCurrentMode()).build();

                // Create the streaming response.
                BatteryMonitoringResponse response = BatteryMonitoringResponse.newBuilder().setBatteryInfo(batteryInfo)
                                .setEstimatedTimeRemaining("45 minutes").setMessage(message).build();

                // Send the response.
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Monitoring interrupted: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
