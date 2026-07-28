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
import java.io.IOException;

import javax.jmdns.ServiceInfo;
import com.smarthome.discovery.JmDNSDiscovery;

/**
 *
 * @author Adriana Dinelly - ID 25165771
 *
 * Internal gRPC client used by Smart Meter Service and GUI to communicate with the Battery Storage Service.
 *
 */
public class BatteryGrpcClient {
    
    // Communication channel with the Battery Service.
    private ManagedChannel channel;
    // Blocking Stub used to invoke Unary RPCs.
    private BatteryStorageServiceGrpc.BatteryStorageServiceBlockingStub stub;

    // Constructor
    public BatteryGrpcClient() {
        
    }
    
    // This method discover the Battery Service and create the communication channel
    private void connect() {
        
        if (stub != null) {
            return;
        }
        
        try {

            // Create the discovery jmDNS instance.
            JmDNSDiscovery discovery = new JmDNSDiscovery();

            // Discover the Solar Service.
            ServiceInfo serviceInfo = discovery.discoverService("BatteryService");

            // Close the discovery instance.
            discovery.close();

            // Create the communication channel.
            channel = ManagedChannelBuilder.forAddress(serviceInfo.getHostAddresses()[0], serviceInfo.getPort()).usePlaintext().build();

            // Create the Blocking Stub.
            stub = BatteryStorageServiceGrpc.newBlockingStub(channel);
            
        } catch (IOException e) {
            
            throw new RuntimeException("Unable to discover the Battery Service.", e);
        }
    }

    // This method retrieves the current battery status from the Battery Service.
    public BatteryStatusInfo getBatteryStatus() {
        
        // Call the method to connect to the Solar service
        connect();

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
