/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.client;

import com.smarthome.smartmeter.GenerateEnergyReportRequest;
import com.smarthome.smartmeter.ReportEntry;
import com.smarthome.smartmeter.SmartMeterServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.io.IOException;

import java.util.Iterator;
import javax.jmdns.ServiceInfo;
import com.smarthome.discovery.JmDNSDiscovery;

/**
 *
 * @author Adriana Dinelly - ID 25165771
 * 
 * Simple client used to test the Smart Meter gRPC service.
 * 
 */
public class SmartMeterClient {

    // Main Method
    public static void main(String[] args) throws IOException {

JmDNSDiscovery discovery = new JmDNSDiscovery();

        ServiceInfo serviceInfo = discovery.discoverService("SmartMeterService");

        // Create the communication channel.
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serviceInfo.getHostAddresses()[0],serviceInfo.getPort()).usePlaintext().build();
        
        // Creates a blocking stub.
        SmartMeterServiceGrpc.SmartMeterServiceBlockingStub stub = SmartMeterServiceGrpc.newBlockingStub(channel);

        // Creates the request message.
        GenerateEnergyReportRequest request = GenerateEnergyReportRequest.newBuilder().setHouseId("HOUSE001").build();

        // We use an Iterator to get all the server responses of the Server Streaming RPC.
        Iterator<ReportEntry> reportEntries = stub.generateEnergyReport(request);

        System.out.println("===== ENERGY REPORT =====");

        // Reads every report entry received from the server.
        while (reportEntries.hasNext()) {

            // Retrieves the next report entry.
            ReportEntry report = reportEntries.next();

            System.out.println("--------------------------------");
            System.out.println("Section: " + report.getSection());
            System.out.println("Value: " + report.getValue());
            System.out.println("Description: " + report.getDescription());
        }
        // Closes the communication and discovery channel.
        channel.shutdown();
        discovery.close();
    }     
}
