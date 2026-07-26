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

import java.util.Iterator;

/**
 *
 * @author Adriana Dinelly - ID 25165771
 * 
 * This class is used to communicate with the other services. Simple Smart Meter Client for tests
 * 
 */
public class SmartMeterClient {
    // Server address.
    private static final String HOST = "localhost";

    // Server port.
    private static final int PORT = 50053;

    // Main Method
    public static void main(String[] args) {

        // Create the communication channel.
        ManagedChannel channel = ManagedChannelBuilder.forAddress(HOST, PORT).usePlaintext().build();
        
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
        // Closes the communication channel.
        channel.shutdown();
    }     
}
