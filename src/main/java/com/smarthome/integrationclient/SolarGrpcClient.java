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
import java.io.IOException;

import javax.jmdns.ServiceInfo;
import com.smarthome.discovery.JmDNSDiscovery;

/**
 *
 * @author Adriana Dinelly - ID 25165771
 *
 * Internal gRPC client used by other services (Battery. Smart Meter, and GUI) to communicate
 * with the Solar Panel Service.
 *
 */
public class SolarGrpcClient {
    
    // Communication channel with the Solar Service.
    private ManagedChannel channel;
    // Blocking Stub used to invoke Unary RPCs.
    private SolarPanelServiceGrpc.SolarPanelServiceBlockingStub stub;

    // Constructor 
    public SolarGrpcClient() {
        
    }
    
    // This method discover the Solar Service and create the communication channel
    private void connect() {
        
        if (stub != null) {
            return;
        }
        
        try {

            // Create the discovery jmDNS instance.
            JmDNSDiscovery discovery = new JmDNSDiscovery();

            // Discover the Solar Service.
            ServiceInfo serviceInfo = discovery.discoverService("SolarService");

            // Close the discovery instance.
            discovery.close();

            // Create the communication channel.
            channel = ManagedChannelBuilder.forAddress(serviceInfo.getHostAddresses()[0], serviceInfo.getPort()).usePlaintext().build();

            // Create the Blocking Stub.
            stub = SolarPanelServiceGrpc.newBlockingStub(channel);
            
        } catch (IOException e) {
            
            throw new RuntimeException("Unable to discover the Solar Service.", e);
        }
    }

    // This method retrieves the current production from the Solar Service.
    public ProductionInfo getCurrentProduction() {
        
        // Call the method to connect to the Solar service
        connect();

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
