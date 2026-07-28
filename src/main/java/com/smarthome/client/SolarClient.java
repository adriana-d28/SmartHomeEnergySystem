/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.client;

import com.smarthome.solar.GetCurrentProductionRequest;
import com.smarthome.solar.ProductionInfo;
import com.smarthome.solar.SolarPanelServiceGrpc;
import com.smarthome.solar.MonitorProductionRequest;
import java.util.Iterator;
import java.io.IOException;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import javax.jmdns.ServiceInfo;
import com.smarthome.discovery.JmDNSDiscovery;

/**
 *
 * @author Adriana Dinelly - ID 25165771
 * 
 * Simple client used to test the Solar Panel gRPC service.
 */
public class SolarClient {

    // Main Method
    public static void main(String[] args) throws IOException {

        JmDNSDiscovery discovery = new JmDNSDiscovery();

        ServiceInfo serviceInfo = discovery.discoverService("SolarService");

        // Create the communication channel.
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serviceInfo.getHostAddresses()[0],serviceInfo.getPort()).usePlaintext().build();

        // Create the Blocking Stub and pass channel as argument.
        SolarPanelServiceGrpc.SolarPanelServiceBlockingStub stub = SolarPanelServiceGrpc.newBlockingStub(channel);

        // ========= UNARY TEST ============
        
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
        
        // ========= SERVER STREAMING TEST ==========
        
        // Create the monitoring request.
        MonitorProductionRequest monitorRequest = MonitorProductionRequest.newBuilder().setPanelId("SP-001").build();

        // Call the Server Streaming RPC.
        Iterator<ProductionInfo> productionUpdates = stub.monitorProduction(monitorRequest);

        System.out.println("\n----- Monitoring Solar Production -----");

        // Read each update sent by the server.
        while (productionUpdates.hasNext()) {
            ProductionInfo production = productionUpdates.next();

            System.out.println("------------------------------");
            System.out.println("Panel ID: " + production.getPanelId());
            System.out.println("Current Production: " + production.getCurrentProduction());
            System.out.println("Unit: " + production.getUnit());
            System.out.println("Timestamp: " + production.getTimestamp());
        }

        // Close the communication and discovery channel.
        channel.shutdown();
        discovery.close();
    }
}
