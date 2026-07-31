/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.server.smartmeter;

import com.smarthome.battery.BatteryMode;
import com.smarthome.battery.ChargingStatus;
import com.smarthome.smartmeter.ConsumptionReading;
import com.smarthome.smartmeter.ConsumptionSummary;
import com.smarthome.smartmeter.GenerateEnergyReportRequest;
import com.smarthome.smartmeter.ReportEntry;
import com.smarthome.smartmeter.SmartMeterServiceGrpc;
import com.smarthome.integrationclient.SolarGrpcClient;
import com.smarthome.integrationclient.BatteryGrpcClient;

import com.smarthome.solar.ProductionInfo;
import com.smarthome.battery.BatteryStatusInfo;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.Context;

import java.util.ArrayList;
import io.grpc.stub.StreamObserver;
/**
 *
 * @author Adriana Dinelly - ID 25165771
 * 
 * Implements the Smart Meter gRPC service.
 * 
 */
public class SmartMeterServiceImpl extends SmartMeterServiceGrpc.SmartMeterServiceImplBase {
    // Stores the current internal state of the Smart Meter.
    private final SmartMeterData smartMeterData;
    
    // Internal clients used to communicate with other services.
    private final SolarGrpcClient solarClient;
    private final BatteryGrpcClient batteryClient;

    /**
     * This constructor creates the service with sample data.
     * The values are temporary and will later be updated
     * through communication with the Solar and Battery services.
     */
    public SmartMeterServiceImpl() {
        // Default values for simulation purposes
        smartMeterData = new SmartMeterData(
                // Basic Information
                "HOUSE001",
                // Solar Information
                24.8,
                "kWh",
                // Battery Information
                82,
                ChargingStatus.CHARGING,
                BatteryMode.NORMAL,
                // Consumption Statistics
                2450,
                612.5,
                "Air Conditioner",
                1400,
                // Energy Analysis
                0,
                "Waiting for analysis..."               
        );
        
        solarClient = new SolarGrpcClient();
        batteryClient = new BatteryGrpcClient();
    }

    // ============ METHODS DEVELOPMENT ============
    
    // Updates the Smart Meter information using the Solar and Battery services.
    private void updateSmartMeterData() {

        // Request the current solar production.
        ProductionInfo production = solarClient.getCurrentProduction("SP-001");
        // Request the current battery status.
        BatteryStatusInfo battery = batteryClient.getBatteryStatus("BAT-001");

        // Update the Smart Meter internal state.
        smartMeterData.setCurrentSolarProduction(production.getCurrentProduction());
        smartMeterData.setProductionUnit(production.getUnit());

        smartMeterData.setBatteryLevel(battery.getBatteryLevel());
        smartMeterData.setChargingStatus(battery.getChargingStatus());
        smartMeterData.setBatteryMode(battery.getCurrentMode());
        
        // Calculate the current energy efficiency.
        smartMeterData.setEnergyEfficiency(calculateEnergyEfficiency());

        // Generate a recommendation based on the current house status.
        smartMeterData.setRecommendation(generateRecommendation());
    }
    
    /**
     * Calculates the current home energy efficiency.
     * The efficiency is calculated using the relation between
     * the current solar production and the total energy consumption.
     * @return The calculated efficiency percentage.
     */
    private double calculateEnergyEfficiency() {
        
        // Avoid division by zero.
        if (smartMeterData.getCurrentSolarProduction() <= 0) {
            return 0;
        }

        // Calculate the efficiency percentage - Mock values.
        double production = smartMeterData.getCurrentSolarProduction();
        int battery = smartMeterData.getBatteryLevel();

        if (production >= 5 && battery >= 80) {
            return 95;
        }

        if (production >= 3 && battery >= 50) {
            return 80;
        }

        if (production >= 2) {
            return 65;
        }

        return 40;
    }

    /**
     * Generates an energy recommendation based on
     * the current production and battery level.
     * @return A recommendation message.
     */
    private String generateRecommendation() {

        // High production and low battery.
        if (smartMeterData.getCurrentSolarProduction() >= 5 && smartMeterData.getBatteryLevel() < 40) {
            
            return "Charge the battery using available solar energy.";
        }

        // Low production and high consumption.
        if (smartMeterData.getCurrentSolarProduction() < 3 && smartMeterData.getTotalConsumption() > 1000) {

            return "Reduce unnecessary energy consumption.";
        }

        // Battery almost full.
        if (smartMeterData.getBatteryLevel() > 90) {

            return "Battery is almost full. Consider using stored energy.";
        }

        // Default recommendation.
        return "Energy usage is operating efficiently.";
    }
    
    
    // ======== SERVER STREAMING METHOD IMPLEMENTATION ===========
    
    // One request is received, and multiple ReportEntry messages are streamed back to the client, generating the energy report.
    @Override
    public void generateEnergyReport(GenerateEnergyReportRequest request, StreamObserver<ReportEntry> responseObserver) {
        
        // Validate the House ID received from the client.
        if (request.getHouseId().isBlank()) {

            // Return an INVALID_ARGUMENT gRPC error.
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("House ID cannot be empty.").asRuntimeException());

            // Stop the method execution.
            return;
        }
        
        try{

            // Calling the method to update the Smart Meter based on information gotten from Solar and Battery Services
            updateSmartMeterData();

            // Future validation:
            // In future versions, the Smart Meter will validate if the requested house exists before generating the report.
            String requestedHouse = request.getHouseId();
            
            // Stop the stream if the client has cancelled the RPC.
            if (Context.current().isCancelled()) {
                System.out.println("Energy report cancelled by the client.");
                return;
            }

            // Sends the current solar production.
            responseObserver.onNext(createReportEntry("Current Solar Production",
                    smartMeterData.getCurrentSolarProduction() + " " + smartMeterData.getProductionUnit(),
                    "Current energy produced by the solar panels."
            ));
            
            Thread.sleep(2000);

            // Stop the stream if the client has cancelled the RPC.
            if (Context.current().isCancelled()) {
                System.out.println("Energy report cancelled by the client.");
                return;
            }

            // Sends the battery level.
            responseObserver.onNext(createReportEntry("Battery Level", smartMeterData.getBatteryLevel() + "%",
                    "Current battery charge level."
            ));
            
            Thread.sleep(2000);

            // Stop the stream if the client has cancelled the RPC.
            if (Context.current().isCancelled()) {
                System.out.println("Energy report cancelled by the client.");
                return;
            }

            // Sends the highest consuming appliance.
            responseObserver.onNext(createReportEntry("Highest Consumer", smartMeterData.getHighestConsumer(),
                    "Appliance with the highest energy consumption."
            ));
            
            Thread.sleep(2000);

            // Stop the stream if the client has cancelled the RPC.
            if (Context.current().isCancelled()) {
                System.out.println("Energy report cancelled by the client.");
                return;
            }

            // Sends the calculated energy efficiency.
            responseObserver.onNext(createReportEntry("Energy Efficiency", smartMeterData.getEnergyEfficiency() + "%",
                    "Overall home energy efficiency."
            ));
            
            Thread.sleep(2000);

            // Stop the stream if the client has cancelled the RPC.
            if (Context.current().isCancelled()) {
                System.out.println("Energy report cancelled by the client.");
                return;
            }

            // Sends the Smart Meter recommendation.
            responseObserver.onNext(createReportEntry("Recommendation", smartMeterData.getRecommendation(),
                    "Recommendation generated by the Smart Meter."
            ));

            // Indicates that no more report entries will be sent.
            responseObserver.onCompleted();
            
        } catch (StatusRuntimeException e) {

            // Forward the original gRPC error to the client.
            responseObserver.onError(e);
        
        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
            responseObserver.onError(Status.CANCELLED.withDescription("Energy report generation was interrupted.").withCause(e).asRuntimeException());
            
        } catch (Exception e) {
            
            // Return an INTERNAL gRPC error.
            responseObserver.onError(Status.INTERNAL.withDescription("Unable to generate the energy report.").withCause(e).asRuntimeException());
        }         
    }


    // Creates one report section. This helper method avoids repeating the Builder code.
    private ReportEntry createReportEntry(String section, String value, String description) {
        return ReportEntry.newBuilder().setSection(section).setValue(value).setDescription(description).build();
    }

    // ======== CLIENT STREAMING METHOD IMPLEMENTATION ========
    @Override
    public StreamObserver<ConsumptionReading> uploadConsumptionReadings(StreamObserver<ConsumptionSummary> responseObserver) {
        
        // Creates an ArrayList to store all received comsumption readings.
        ArrayList<ConsumptionReading> readings = new ArrayList<>();
        
        // We need to return a StreamObserver, which will be responsible for observing all the streamed messages from the client
        return new StreamObserver<ConsumptionReading>() {

            @Override
            public void onNext(ConsumptionReading reading) {
                // Validate the received reading.
                if (reading.getDevice().isBlank()) {

                    // Return an INVALID_ARGUMENT gRPC error.
                    responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("Device name cannot be empty.").asRuntimeException());

                    return;
                }
                
                if (reading.getConsumption() < 0) {
                    
                    // Return an INVALID_ARGUMENT gRPC error.
                    responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("Consumption cannot be negative.").asRuntimeException());
                }
                
                // Stores each reading received from the client
                readings.add(reading);
            }

            @Override
            public void onError(Throwable t) {
                // Forward existing gRPC errors.
                if (t instanceof StatusRuntimeException) {
                    responseObserver.onError(t);
                    return;
                }

                // Return an INTERNAL gRPC error.
                responseObserver.onError(Status.INTERNAL.withDescription("Consumption upload failed.").withCause(t).asRuntimeException());
            }

            // This method will calculate the response after the client finishes sending information
            @Override
            public void onCompleted() {
                
                // Validate whether at least one reading was received.
                if (readings.isEmpty()) {

                    // Return an INVALID_ARGUMENT gRPC error.
                    responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("At least one consumption reading is required.").asRuntimeException());

                    return;
                }
                
                int totalConsumption = 0;
                int highestConsumption = Integer.MIN_VALUE;
                String highestConsumer = "";

                for (ConsumptionReading reading : readings) {
                    totalConsumption += reading.getConsumption();
                    
                    if (reading.getConsumption() > highestConsumption) {
                        highestConsumption = reading.getConsumption();
                        highestConsumer = reading.getDevice();

                    }
                }
                // calculating the average consumption
                double averageConsumption;
                
                if(readings.isEmpty()) {
                    averageConsumption = 0;
                } else {
                    averageConsumption = (double) totalConsumption / readings.size();
                }

            // building the consumption summary to send back to the client
            ConsumptionSummary summary = ConsumptionSummary.newBuilder().setHighestConsumer(highestConsumer)
                            .setHighestConsumption(highestConsumption).setTotalConsumption(totalConsumption)
                            .setAverageConsumption(averageConsumption).build();

            // Only one onNext because the client streaming answers only once
            responseObserver.onNext(summary);
            // Communicates the end of the response
            responseObserver.onCompleted();
            
            }
        };
    }
}
