/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.client;

import com.smarthome.battery.BatteryStatusInfo;
import com.smarthome.battery.BatteryStorageServiceGrpc;
import com.smarthome.battery.GetBatteryStatusRequest;
import com.smarthome.battery.BatteryCommand;
import com.smarthome.battery.BatteryMode;
import com.smarthome.battery.BatteryMonitoringRequest;
import com.smarthome.battery.BatteryMonitoringResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

/**
 *
 * @author Adriana Dinelly - ID 25165771
 * 
 * This class is used to communicate with the other services. Test client.
 * 
 */
public class BatteryClient {
    // Server address.
    private static final String HOST = "localhost";

    // Server port.
    private static final int PORT = 50052;

    // Main Method
    public static void main(String[] args) {

        // Create the communication channel.
        ManagedChannel channel = ManagedChannelBuilder.forAddress(HOST, PORT).usePlaintext().build();

        // ======== UNARY TEST ==========
        
        // Create the Blocking Stub and pass channel as argument.
        BatteryStorageServiceGrpc.BatteryStorageServiceBlockingStub stub = BatteryStorageServiceGrpc.newBlockingStub(channel);
        
        // Create the request.
        GetBatteryStatusRequest request = GetBatteryStatusRequest.newBuilder().setBatteryId("BAT001").build();

        // Call the Unary RPC.
        BatteryStatusInfo response = stub.getBatteryStatus(request);

        // Display the received information.
        System.out.println("===== Battery Status =====");
        System.out.println("Battery ID: " + response.getBatteryId());
        System.out.println("Battery Level: " + response.getBatteryLevel() + "%");
        System.out.println("Charging Status: " + response.getChargingStatus());
        System.out.println("Current Mode: " + response.getCurrentMode());
        
        // ========= BIDIRECTIONAL TEST ===========
        
        // Create the asynchronous stub.
        BatteryStorageServiceGrpc.BatteryStorageServiceStub asyncStub = BatteryStorageServiceGrpc.newStub(channel);

        // Create the response observer.
        StreamObserver<BatteryMonitoringResponse> responseObserver = new StreamObserver<BatteryMonitoringResponse>() {

            @Override
            public void onNext(BatteryMonitoringResponse response) {

                System.out.println("\n===== Battery Monitoring Task =====");
                System.out.println("Battery ID: " + response.getBatteryInfo().getBatteryId());
                System.out.println("Battery Level: " + response.getBatteryInfo().getBatteryLevel() + "%");
                System.out.println("Charging Status: " + response.getBatteryInfo().getChargingStatus());
                System.out.println("Current Mode: " + response.getBatteryInfo().getCurrentMode());
                System.out.println("Estimated Time: " + response.getEstimatedTimeRemaining());
                System.out.println("Message: " + response.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("\nMonitoring finished.");
            }
        };

        // Open the bidirectional stream.
        StreamObserver<BatteryMonitoringRequest> requestObserver = asyncStub.monitorBatteryStatus(responseObserver);

        // Send START command.
        requestObserver.onNext(BatteryMonitoringRequest.newBuilder().setBatteryId("BAT001").setCommand(BatteryCommand.START).build());

        // Send CHANGE_MODE command.
        requestObserver.onNext(BatteryMonitoringRequest.newBuilder().setBatteryId("BAT001").setCommand(BatteryCommand.CHANGE_MODE)
                .setMode(BatteryMode.ECO).build());

        // Send STOP command.
        requestObserver.onNext(BatteryMonitoringRequest.newBuilder().setBatteryId("BAT001").setCommand(BatteryCommand.STOP).build());

        // Inform the server that no more requests will be sent.
        requestObserver.onCompleted();

        // Wait a little before closing the channel.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Close the communication channel.
        channel.shutdown();
    }
}
