/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.server.solar;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
/**
 *
 * @author Adriana Dinelly - ID 25165771
 * 
 * This class initializes the gRPC Solar Panel server. 
 * It is responsible for creating, starting and keeping the server running.
 * 
 */
public class SolarServer {
    // Port where the Solar Service will listen for client requests.
    private static final int PORT = 50051;

    // This object represents a gRPC server instance.
    private Server server;

    // We create a server instance, start it and keep it running by calling the methods
    public static void main(String[] args) throws IOException, InterruptedException {
        
        SolarServer server = new SolarServer();
        server.start();
        server.server.awaitTermination();
    }

    // This small method creates and starts the gRPC server.
    public void start() throws IOException {
        
        server = ServerBuilder.forPort(PORT)
                // Registers the service implementation.
                .addService(new SolarServiceImpl())
                // Builds the server.
                .build()
                // Starts listening for client connections.
                .start();

        System.out.println("------------------------------------------------");
        System.out.println("Solar Panel Service started.");
        System.out.println("Listening on port " + PORT);
        System.out.println("------------------------------------------------");
    }
}
