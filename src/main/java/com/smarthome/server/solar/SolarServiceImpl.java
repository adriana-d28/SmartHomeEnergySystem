/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.server.solar;

import com.smarthome.solar.GetCurrentProductionRequest;
import com.smarthome.solar.ProductionInfo;
import com.smarthome.solar.SolarPanelServiceGrpc;
import com.smarthome.solar.MonitorProductionRequest;

import io.grpc.stub.StreamObserver;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import io.grpc.Context;

/**
 *
 * @author Adriana Dinelly - ID 25165771
 * 
 * This class implements the gRPC service. 
 * It contains the methods logic that answers to client requests.
 * The SolarPanelServiceImplBase is the contract/interface for the SolarServiceImpl.
 * 
 */
public class SolarServiceImpl extends SolarPanelServiceGrpc.SolarPanelServiceImplBase {
    
    // Formatter used to convert LocalDateTime into a readable String (proto file uses 'string' as date format).
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Represents the current state of the solar panel.
    private final SolarData solarData;
    
    // Formatter to format the output
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0.00");

    // Constructor - creates the service and initializes the solar panel with sample data.
    public SolarServiceImpl() {
        solarData = new SolarData("SP-001", 4.75, "kWh", LocalDateTime.now());
    }
    
    // ===== Methods Development =====

    /**
     * ======== UNARY RPC METHOD =========
     * 
     * Handles Unary requests that retrieve the current production of the solar panel.
     * 
     */
    @Override
    public void getCurrentProduction(GetCurrentProductionRequest request, StreamObserver<ProductionInfo> responseObserver) {
        // test log - remove it
        System.out.println("Solar Service received a request from another service.");
        // Update the timestamp to represent the current measurement time.
        solarData.setTimestamp(LocalDateTime.now());
        
        // Create the response message using the Builder pattern: sets the data, the format and build.
        ProductionInfo response = ProductionInfo.newBuilder().setPanelId(solarData.getPanelId())
                .setCurrentProduction(solarData.getCurrentProduction()).setUnit(solarData.getUnit()).setTimestamp(
                solarData.getTimestamp().format(FORMATTER)).build();

        // Send the response to the client - due to its unary nature, we only need one.
        responseObserver.onNext(response);
        // Finish and close the RPC call.
        responseObserver.onCompleted();

    }
    
     /**
     * ======== SERVER STREAMING METHOD =========
     * 
     * Handles Server Streaming requests that continuously send updated solar production values to the client.
     *
     */
    @Override
    public void monitorProduction(MonitorProductionRequest request, StreamObserver<ProductionInfo> responseObserver) {
        
        try {
            // Send five production updates - simple version for tests.
            for (int i = 0; i < 5; i++) {
                
                // Stop the stream if the client has cancelled the RPC.
                if (Context.current().isCancelled()) {
                    System.out.println("Production monitoring cancelled by the client.");
                    return;
                }
                
                // Simulate a small production variation.
                double newProduction = solarData.getCurrentProduction() + 0.2;
                
                // Round the value to two decimal places.
                newProduction = Double.parseDouble(DECIMAL_FORMAT.format(newProduction));

                solarData.setCurrentProduction(newProduction);
                solarData.setTimestamp(LocalDateTime.now());

                // Create the response message.
                ProductionInfo response = ProductionInfo.newBuilder().setPanelId(solarData.getPanelId())
                        .setCurrentProduction(solarData.getCurrentProduction()).setUnit(solarData.getUnit())
                        .setTimestamp(solarData.getTimestamp().format(FORMATTER)).build();

                // Send the current production to the client.
                responseObserver.onNext(response);
                // Wait two seconds before sending the next update.
                Thread.sleep(2000);
            }

            // Finish the stream.
            responseObserver.onCompleted();

        } catch (InterruptedException e) {

            // Restore the interrupted status.
            Thread.currentThread().interrupt();
            // Notify the client about the error.
            responseObserver.onError(e);
        }
    }
}
