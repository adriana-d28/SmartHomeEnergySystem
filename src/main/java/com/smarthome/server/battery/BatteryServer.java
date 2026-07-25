/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.server.battery;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

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
        
        BatteryServer server = new BatteryServer();
        server.start();
        server.server.awaitTermination();
    }

    // This small method creates and starts the gRPC server.
    public void start() throws IOException {
        
        server = ServerBuilder.forPort(PORT)
                // Registers the service implementation.
                .addService(new BatteryServiceImpl())
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
