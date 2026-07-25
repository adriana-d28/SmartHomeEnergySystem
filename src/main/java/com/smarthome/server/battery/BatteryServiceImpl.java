/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.server.battery;

import com.smarthome.battery.BatteryMode;
import com.smarthome.battery.BatteryStatusInfo;
import com.smarthome.battery.BatteryStorageServiceGrpc;
import com.smarthome.battery.ChargingStatus;
import com.smarthome.battery.GetBatteryStatusRequest;
import io.grpc.stub.StreamObserver;

/**
 *
 * @author Adriana Dinelly - ID 25165771
 * 
 * This class implements the Battery Storage gRPC service.
 * 
 */
public class BatteryServiceImpl extends BatteryStorageServiceGrpc.BatteryStorageServiceImplBase {
    // Create a battery internal state instance
    private final BatteryData batteryData;
    // Constructor - after creating the service, the server initializes a standard state of the battery
    public BatteryServiceImpl() {
        batteryData = new BatteryData("BAT001", 75, ChargingStatus.CHARGING, BatteryMode.NORMAL);
    }

    // ======= Methods Development =======
    
    
    /**
     * Handles Unary requests that retrieve the current battery status (e.g. charging, fully charged etc).
     * @param request Request received from the client.
     * @param responseObserver Used to send the response back to the client.
     */
    @Override
    public void getBatteryStatus(GetBatteryStatusRequest request, StreamObserver<BatteryStatusInfo> responseObserver) {
        
        // Create the response message using the Builder pattern: sets the data, the format and build.
        BatteryStatusInfo response = BatteryStatusInfo.newBuilder().setBatteryId(batteryData.getBatteryId())
                .setBatteryLevel(batteryData.getBatteryLevel()).setChargingStatus(batteryData.getChargingStatus())
                .setCurrentMode(batteryData.getCurrentMode()).build();
        
        // Send the response to the client - due to its unary nature, we only need one.
        responseObserver.onNext(response);
        // Finish and close connection
        responseObserver.onCompleted();
    }
}
