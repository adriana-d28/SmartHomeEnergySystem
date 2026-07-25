/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.server.solar;

import com.smarthome.solar.GetCurrentProductionRequest;
import com.smarthome.solar.ProductionInfo;
import com.smarthome.solar.SolarPanelServiceGrpc;

import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    // Constructor - creates the service and initializes the solar panel with sample data.
    public SolarServiceImpl() {
        solarData = new SolarData("SP-001", 4.75, "kWh", LocalDateTime.now());
    }
    
    // ===== Methods Development =====

    /**
     * Handles Unary requests that retrieve the current production of the solar panel.
     * @param request Request received from the client.
     * @param responseObserver Used to send the response back to the client.
     */
    @Override
    public void getCurrentProduction(GetCurrentProductionRequest request, StreamObserver<ProductionInfo> responseObserver) {
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
}
