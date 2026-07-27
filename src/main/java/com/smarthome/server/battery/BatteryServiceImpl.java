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
    // Constructor - after creating the service, the server initializes a standard state of the battery
    public BatteryServiceImpl() {
        batteryData = new BatteryData("BAT001", 75, ChargingStatus.CHARGING, BatteryMode.NORMAL);
    }

    // ======= Methods Development =======
    
    
    /**
     * ======= UNARY METHOD ========
     * 
     * Handles Unary requests that retrieve the current battery status (e.g. charging, fully charged etc).
     * 
     */
    @Override
    public void getBatteryStatus(GetBatteryStatusRequest request, StreamObserver<BatteryStatusInfo> responseObserver) {
        
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

                // Finish the monitoring session when STOP is received.
                if (request.getCommand() == BatteryCommand.STOP) {
                    responseObserver.onCompleted();
                }
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
