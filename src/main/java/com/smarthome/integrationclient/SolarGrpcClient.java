/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.integrationclient;

import com.smarthome.solar.GetCurrentProductionRequest;
import com.smarthome.solar.ProductionInfo;
import com.smarthome.solar.SolarPanelServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 *
 * @author Adriana Dinelly - ID 25165771
 *
 * Internal gRPC client used by other services (Battery. Smart Meter, and GUI) to communicate
 * with the Solar Panel Service.
 *
 */
public class SolarGrpcClient {
    
    // Solar Service address and port.
    private static final String HOST = "localhost";
    private static final int PORT = 50051;
    // Communication channel with the Solar Service.
    private final ManagedChannel channel;
    // Blocking Stub used to invoke Unary RPCs.
    private final SolarPanelServiceGrpc.SolarPanelServiceBlockingStub stub;

    // Constructor - creates the communication channel and initializes the gRPC stub.
    public SolarGrpcClient() {

        // Create the communication channel.
        channel = ManagedChannelBuilder.forAddress(HOST, PORT).usePlaintext().build();
        // Create the Blocking Stub.
        stub = SolarPanelServiceGrpc.newBlockingStub(channel);
    }

    // This method retrieves the current production from the Solar Service.
    public ProductionInfo getCurrentProduction() {

        // Create the request.
        GetCurrentProductionRequest request = GetCurrentProductionRequest.newBuilder().setPanelId("SP-001").build();
        // Invoke the Unary RPC.
        return stub.getCurrentProduction(request);
    }

    // Closes the communication channel (it allows the other services to close the communication channel)
    public void shutdown() {
        channel.shutdown();
    }
}
