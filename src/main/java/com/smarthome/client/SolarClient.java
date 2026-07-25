/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.client;

import com.smarthome.solar.GetCurrentProductionRequest;
import com.smarthome.solar.ProductionInfo;
import com.smarthome.solar.SolarPanelServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 *
 * @author Adriana
 * Simple client used to test the Solar Panel gRPC service.
 */
public class SolarClient {
    // Server address.
    private static final String HOST = "localhost";

    // Server port.
    private static final int PORT = 50051;

    // Main Method
    public static void main(String[] args) {

        // Create the communication channel.
        ManagedChannel channel = ManagedChannelBuilder.forAddress(HOST, PORT).usePlaintext().build();

        // Create the Blocking Stub and pass channel as argument.
        SolarPanelServiceGrpc.SolarPanelServiceBlockingStub stub = SolarPanelServiceGrpc.newBlockingStub(channel);

        // Create the request.
        GetCurrentProductionRequest request = GetCurrentProductionRequest.newBuilder().setPanelId("SP-001").build();

        // Call the Unary RPC.
        ProductionInfo response = stub.getCurrentProduction(request);

        // Display the received information.
        System.out.println("----- Solar Panel Information -----");
        System.out.println("Panel ID: " + response.getPanelId());
        System.out.println("Current Production: " + response.getCurrentProduction());
        System.out.println("Unit: " + response.getUnit());
        System.out.println("Timestamp: " + response.getTimestamp());

        // Close the communication channel.
        channel.shutdown();
    }
}
