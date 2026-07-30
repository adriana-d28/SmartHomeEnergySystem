/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.smarthome.integrationclient;

import com.smarthome.discovery.JmDNSDiscovery;
import com.smarthome.smartmeter.ConsumptionReading;
import com.smarthome.smartmeter.ConsumptionSummary;
import com.smarthome.smartmeter.GenerateEnergyReportRequest;
import com.smarthome.smartmeter.ReportEntry;
import com.smarthome.smartmeter.SmartMeterServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.Iterator;

import javax.jmdns.ServiceInfo;

/**
 *
 * @author Adriana Dinelly - ID 25165771
 *
 * Internal gRPC client used by the GUI to communicate with the
 * Smart Meter Service.
 * 
 * This client encapsulates all communication details, allowing the GUI to invoke the Smart Meter RPC methods.
 *
 */
public class SmartMeterGrpcClient {

    // Communication channel with the Smart Meter Service.
    private ManagedChannel channel;
    // Blocking Stub used to invoke Server Streaming RPCs.
    private SmartMeterServiceGrpc.SmartMeterServiceBlockingStub stub;
    // Asynchronous Stub used to invoke Client Streaming RPCs.
    private SmartMeterServiceGrpc.SmartMeterServiceStub asyncStub;

    // Constructor
    public SmartMeterGrpcClient() {

    }

    // This method discovers the Smart Meter Service and creates the communication channel.
    private void connect() {

        // Avoid creating the connection more than once.
        if (stub != null) {
            return;
        }

        try {

            // Create the discovery jmDNS instance.
            JmDNSDiscovery discovery = new JmDNSDiscovery();
            // Discover the Smart Meter Service.
            ServiceInfo serviceInfo = discovery.discoverService("SmartMeterService");
            // Close the discovery instance.
            discovery.close();

            // Create the communication channel.
            channel = ManagedChannelBuilder.forAddress(serviceInfo.getHostAddresses()[0], serviceInfo.getPort()).usePlaintext().build();

            // Create the Blocking and Async Stub.
            stub = SmartMeterServiceGrpc.newBlockingStub(channel);
            asyncStub = SmartMeterServiceGrpc.newStub(channel);

        } catch (IOException e) {

            throw new RuntimeException("Unable to discover the Smart Meter Service.", e);

        }
    }

    // This method requests the energy report from the Smart Meter Service.
    public void generateEnergyReport(String houseId, StreamObserver<ReportEntry> responseObserver) {

        // Connect to the Smart Meter Service.
        connect();

        // Create the request.
        GenerateEnergyReportRequest request = GenerateEnergyReportRequest.newBuilder().setHouseId(houseId).build();

        // Invoke the Server Streaming RPC.
        asyncStub.generateEnergyReport(request, responseObserver);
    }

    // This method starts the Client Streaming communication.
    public StreamObserver<ConsumptionReading> uploadConsumptionReadings(StreamObserver<ConsumptionSummary> responseObserver) {

        // Connect to the Smart Meter Service.
        connect();

        // Invoke the Client Streaming RPC.
        return asyncStub.uploadConsumptionReadings(responseObserver);
    }

    // Closes the communication channel.
    public void shutdown() {

        // Shutdown the communication channel.
        if (channel != null) {
            channel.shutdown();
        }
    }
}