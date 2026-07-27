/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.integrationclient;

import com.smarthome.battery.BatteryStatusInfo;
import com.smarthome.battery.BatteryStorageServiceGrpc;
import com.smarthome.battery.GetBatteryStatusRequest;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 *
 * @author Adriana Dinelly - ID 25165771
 *
 * Internal gRPC client used by Smart Meter Service and GUI to communicate with the Battery Storage Service.
 *
 */
public class BatteryGrpcClient {

    // Battery Service address and port.
    private static final String HOST = "localhost";
    private static final int PORT = 50052;
    
    // Communication channel with the Battery Service.
    private final ManagedChannel channel;
    // Blocking Stub used to invoke Unary RPCs.
    private final BatteryStorageServiceGrpc.BatteryStorageServiceBlockingStub stub;

    // Creates the communication channel and initializes the gRPC stub.
    public BatteryGrpcClient() {

        // Create the communication channel.
        channel = ManagedChannelBuilder.forAddress(HOST, PORT).usePlaintext().build();

        // Create the Blocking Stub.
        stub = BatteryStorageServiceGrpc.newBlockingStub(channel);
    }

    // This method retrieves the current battery status from the Battery Service.
    public BatteryStatusInfo getBatteryStatus() {

        // Create the request.
        GetBatteryStatusRequest request = GetBatteryStatusRequest.newBuilder().setBatteryId("BAT001").build();
        // Invoke the Unary RPC.
        return stub.getBatteryStatus(request);
    }

    // Closes the communication channel (it allows the other services to close the communication channel)
    public void shutdown() {
        channel.shutdown();
    }
}
