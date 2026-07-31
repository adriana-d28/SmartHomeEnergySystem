/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.server.battery;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import com.smarthome.discovery.JmDNSRegistration;

import com.smarthome.advancedgrpc.AuthInterceptor;
import io.grpc.ServerInterceptors;

/**
 *
 * @author Adriana Dinelly - ID 25165771
 * 
 * This class starts the Battery Storage gRPC server.
 * It is responsible for creating, starting and keeping the server running.
 * 
 */
public class BatteryServer {
    // Port where the Battery Service will listen for client requests.
    private static final int PORT = 50052;

    // This object represents a gRPC server instance.
    private Server server;

    // We create a server instance, start it and keep it running by calling the methods
    public static void main(String[] args) throws IOException, InterruptedException {
        
        // Registration object used to publish the service on the local network.
        JmDNSRegistration registration = null;
        
        try {
            
            BatteryServer server = new BatteryServer();
            server.start();
            
            // Create an instance of the register
            registration = new JmDNSRegistration();
            // Register the service
            registration.registerService("BatteryService", PORT, "Battery Storage Service");
            
            server.server.awaitTermination();
        
        } catch (IOException e) {
            
            System.err.println("Error starting Battery Service: " + e.getMessage());
            throw e;
        }
    }

    // This small method creates and starts the gRPC server.
    public void start() throws IOException {
        
        server = ServerBuilder.forPort(PORT)
                // Registers the service implementation using an authentication interceptor.
                .addService(ServerInterceptors.intercept(new BatteryServiceImpl(), new AuthInterceptor()))
                // Builds the server.
                .build()
                // Starts listening for client connections.
                .start();

        System.out.println("------------------------------------------------");
        System.out.println("Battery Storage Service started.");
        System.out.println("Listening on port " + PORT);
        System.out.println("------------------------------------------------");
    }
    
}
