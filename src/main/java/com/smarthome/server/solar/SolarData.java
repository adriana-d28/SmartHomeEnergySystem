/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.server.solar;

/**
 *
 * @author Adriana Dinelly - ID 25165771
 * 
 * This class represents the Solar Panel state. It only stores solar panel data 
 * and doesn't contain any business logic or gRPC communication.
 * 
 */
import java.time.LocalDateTime;
public class SolarData {

    // Unique identifier of the solar panel.
    private String panelId;
    // Current energy production.
    private double currentProduction;
    // Unit used to represent the production (e.g., kWh).
    private String unit;
    // Date and time when the production was recorded.
    private LocalDateTime timestamp;

    // Constructors - creates a new SolarData object with initial values.
    public SolarData(String panelId,double currentProduction,String unit,LocalDateTime timestamp) {
        this.panelId = panelId;
        this.currentProduction = currentProduction;
        this.unit = unit;
        this.timestamp = timestamp;
    }
    
    public SolarData() {
    }

    // This getter returns the panel identifier.
    public String getPanelId() {
        return panelId;
    }

    // This setter allows panel identifier updates.
    public void setPanelId(String panelId) {
        this.panelId = panelId;
    }

    
    // This getter returns the current energy production.
    public double getCurrentProduction() {
        return currentProduction;
    }

    
    // This setter allows the current energy production updates.
    public void setCurrentProduction(double currentProduction) {
        this.currentProduction = currentProduction;
    }

    
    // Returns the measurement unit.
    public String getUnit() {
        return unit;
    }

    
    // Updates the measurement unit.
    public void setUnit(String unit) {
        this.unit = unit;
    }

    // Returns the timestamp of the last measurement.
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Updates the timestamp.
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // This toString method returns a readable object.
    @Override
    public String toString() {
        return "SolarData{" + "panelId='" + panelId + '\'' + ", currentProduction=" + currentProduction +
                ", unit='" + unit + '\'' + ", timestamp=" + timestamp + '}';
    }
}
