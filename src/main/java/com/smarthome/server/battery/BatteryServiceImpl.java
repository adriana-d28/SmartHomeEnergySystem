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
    // Thread responsible for sending battery updates.
    private Thread monitoringThread;
    
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
        ProductionInfo production = solarClient.getCurrentProduction("SP-001");
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
        // Remote error handling treatment - validate the Battery ID received from the client.
        if (request.getBatteryId().isBlank()) {

            // Return an INVALID_ARGUMENT gRPC error.
            responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT.withDescription("Battery ID cannot be empty.").asRuntimeException());

            // Stop the method execution.
            return;
        }
        
        try {
            
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
             
        } catch (Exception e) {
            
            // Forward existing gRPC errors without changing them.
            if (e instanceof io.grpc.StatusRuntimeException) {
                responseObserver.onError(e);
                return;
            }
            
            // Remote error handling - return an INTERNAL gRPC error.
            responseObserver.onError(io.grpc.Status.INTERNAL.withDescription("Unable to retrieve the battery status.").withCause(e).asRuntimeException());
        }
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
                        // Prevent multiple monitoring threads.
                        if (monitoringThread != null && monitoringThread.isAlive()) {
                            return;
                        }

                        monitoringThread = new Thread(() -> {

                            try {

                                while (!Thread.currentThread().isInterrupted()) {
                                    // Update the battery status according to the Solar Panel information.
                                    updateBatteryStatus();

                                    // Create the battery information.
                                    BatteryStatusInfo batteryInfo = BatteryStatusInfo.newBuilder().setBatteryId(batteryData.getBatteryId())
                                            .setBatteryLevel(batteryData.getBatteryLevel()).setChargingStatus(batteryData.getChargingStatus())
                                            .setCurrentMode(batteryData.getCurrentMode()).build();

                                    // Create the streaming response.
                                    BatteryMonitoringResponse response = BatteryMonitoringResponse.newBuilder().setBatteryInfo(batteryInfo)
                                            .setEstimatedTimeRemaining("45 minutes").setMessage("Monitoring battery...").build();

                                    // Send the response.
                                    responseObserver.onNext(response);
                                    // Wait before sending the next update.
                                    Thread.sleep(2000);
                                }

                            } catch (InterruptedException e) {
                                // Restore the interrupted status.
                                Thread.currentThread().interrupt();

                                // Remote error handling - return a CANCELLED gRPC status.
                                responseObserver.onError(io.grpc.Status.CANCELLED.withDescription("Battery monitoring was interrupted.").withCause(e).asRuntimeException());
                            }
                        });

                        monitoringThread.start();
                        break;
                        
                    case CHANGE_MODE:
                        // Change the current battery mode.
                        batteryData.setCurrentMode(request.getMode());

                        // Inform the client that the mode has changed.
                        BatteryStatusInfo batteryInfo = BatteryStatusInfo.newBuilder().setBatteryId(batteryData.getBatteryId())
                                .setBatteryLevel(batteryData.getBatteryLevel()).setChargingStatus(batteryData.getChargingStatus())
                                .setCurrentMode(batteryData.getCurrentMode()).build();

                        BatteryMonitoringResponse response = BatteryMonitoringResponse.newBuilder().setBatteryInfo(batteryInfo)
                                .setEstimatedTimeRemaining("45 minutes").setMessage("Battery mode changed to " + request.getMode() + ".").build();

                        responseObserver.onNext(response);
                        break;
                        
                    case STOP:
                        if (monitoringThread != null) {
                            monitoringThread.interrupt();
                        }

                        responseObserver.onCompleted();
                        break;
                    default:
                        // Remote error handling - return an INVALID_ARGUMENT gRPC error.
                        responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT.withDescription("Unknown battery command.").asRuntimeException());                       
                        break;
                }
            }

            @Override
            public void onError(Throwable t) {
                if (monitoringThread != null) {
                    monitoringThread.interrupt();
                }

                // Remote error handling - return an INTERNAL gRPC error to the client.
                responseObserver.onError(io.grpc.Status.INTERNAL.withDescription("Battery monitoring failed.").withCause(t).asRuntimeException());
            }

            @Override
            public void onCompleted() {
                if (monitoringThread != null) {
                    monitoringThread.interrupt();
                }
                
                responseObserver.onCompleted();
            }
        };
    }
}
