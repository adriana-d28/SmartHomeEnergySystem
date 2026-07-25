/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.server.battery;

import com.smarthome.battery.BatteryMode;
import com.smarthome.battery.ChargingStatus;

/**
 *
 * @author Adriana Dinelly - ID 25165771
 * 
 * This class represents the internal state of the home battery. 
 * It stores all the battery data and it never communicates with the client or uses gRPC.
 * 
 */
public class BatteryData {
    
    private String batteryId;
    private int batteryLevel;
    private ChargingStatus chargingStatus;
    private BatteryMode currentMode;
    
    // Constructor
    public BatteryData(String batteryId, int batteryLevel, ChargingStatus chargingStatus, BatteryMode currentMode) {
        this.batteryId = batteryId;
        this.batteryLevel = batteryLevel;
        this.chargingStatus = chargingStatus;
        this.currentMode = currentMode;
    }

    // Getters and setters
    public String getBatteryId() {
        return batteryId;
    }

    public void setBatteryId(String batteryId) {
        this.batteryId = batteryId;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public ChargingStatus getChargingStatus() {
        return chargingStatus;
    }

    public void setChargingStatus(ChargingStatus chargingStatus) {
        this.chargingStatus = chargingStatus;
    }

    public BatteryMode getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(BatteryMode currentMode) {
        this.currentMode = currentMode;
    }

    // Method toString
    @Override
    public String toString() {
        return "BatteryData{" + "batteryId='" + batteryId + '\'' + ", batteryLevel=" + batteryLevel +
                ", chargingStatus=" + chargingStatus + ", currentMode=" + currentMode + '}';
    } 
}
