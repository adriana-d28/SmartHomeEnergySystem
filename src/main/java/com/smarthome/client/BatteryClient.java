/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.client;

import com.smarthome.battery.BatteryStatusInfo;
import com.smarthome.battery.BatteryStorageServiceGrpc;
import com.smarthome.battery.GetBatteryStatusRequest;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

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
        
        // Close the communication channel.
        channel.shutdown();
    }
}
