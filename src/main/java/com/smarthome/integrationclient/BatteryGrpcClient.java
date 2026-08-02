/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.integrationclient;

import com.smarthome.battery.BatteryStatusInfo;
import com.smarthome.battery.BatteryStorageServiceGrpc;
import com.smarthome.battery.GetBatteryStatusRequest;

import com.smarthome.battery.BatteryCommand;
import com.smarthome.battery.BatteryMode;
import com.smarthome.battery.BatteryMonitoringRequest;
import com.smarthome.battery.BatteryMonitoringResponse;

import io.grpc.Context;
import io.grpc.stub.StreamObserver;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.jmdns.ServiceInfo;
import com.smarthome.discovery.JmDNSDiscovery;

import com.smarthome.advancedgrpc.GrpcClientInterceptor;

import io.grpc.Channel;
import io.grpc.ClientInterceptors;

/**
 *
 * @author Adriana Dinelly - ID 25165771
 *
 * Internal gRPC client used by Smart Meter Service and GUI to communicate with the Battery Storage Service.
 *
 */
public class BatteryGrpcClient {
    
    // Communication channel with the Battery Service.
    private ManagedChannel channel;
    // Blocking Stub used to invoke Unary RPCs.
    private BatteryStorageServiceGrpc.BatteryStorageServiceBlockingStub stub;
    // Async Stub used to invoke streaming RPCs.
    private BatteryStorageServiceGrpc.BatteryStorageServiceStub asyncStub;
    // Context used to cancel an active monitoring stream.
    private Context.CancellableContext cancellableContext;
    // Stream used to send requests to the server.
    private StreamObserver<BatteryMonitoringRequest> requestObserver;
    // Client interceptor responsible for attaching Metadata and JWT.
    private final GrpcClientInterceptor interceptor = new GrpcClientInterceptor("BatteryGrpcClient");

    // Constructor
    public BatteryGrpcClient() {
        
    }
    
    // =============== METHODS CALL ===============
    
    // Helper method - This method discover the Battery Service and create the communication channel
    private void connect() {
        
        if (stub != null) {
            return;
        }
        
        try {

            // Create the discovery jmDNS instance.
            JmDNSDiscovery discovery = new JmDNSDiscovery();

            // Discover the Battery Service.
            ServiceInfo serviceInfo = discovery.discoverService("BatteryService");

            // Close the discovery instance.
            discovery.close();

            // Create the communication channel.
            channel = ManagedChannelBuilder.forAddress(serviceInfo.getHostAddresses()[0], serviceInfo.getPort()).usePlaintext().build();

            // Create a channel that passes through the interceptor before reaching the server.
            Channel interceptedChannel = ClientInterceptors.intercept(channel, interceptor);

            // Create the Blocking Stub using the intercepted channel.
            stub = BatteryStorageServiceGrpc.newBlockingStub(interceptedChannel);

            // Create the Async Stub using the intercepted channel.
            asyncStub = BatteryStorageServiceGrpc.newStub(interceptedChannel);
            
        } catch (IOException e) {
            
            // Return a gRPC UNAVAILABLE error.
            throw io.grpc.Status.UNAVAILABLE.withDescription("Unable to discover the Battery Service.").withCause(e).asRuntimeException();
        }
    }
    
    /**
     * Helper method
     * Sends a battery mode change request through the active stream.
     * @param mode New battery mode.
     */
    public void changeBatteryMode(BatteryMode mode) {

        // Check whether the monitoring stream is active.
        if (requestObserver == null) {
            return;
        }

        // Create the CHANGE_MODE request.
        BatteryMonitoringRequest request = BatteryMonitoringRequest.newBuilder().setCommand(BatteryCommand.CHANGE_MODE).setMode(mode).build();
        // Send the request to the server.
        requestObserver.onNext(request);
    }
    
    /**
     * Helper method
     * Stops the active battery monitoring stream.
     */
    public void stopMonitoring() {

        // Cancel the monitoring context if it exists.
        if (cancellableContext != null) {
            cancellableContext.cancel(null);
        }

        // Clear the request observer.
        requestObserver = null;
        // Clear the cancellable context.
        cancellableContext = null;
    }
    
    /**
     * Updates the JWT used by this client.
     *
     * @param jwtToken JWT generated after authentication.
     */
    public void setJwtToken(String jwtToken) {

        // Forward the JWT to the interceptor.
        interceptor.setJwtToken(jwtToken);

    }
    

    // ======== UNARY METHOD ==========
    // This method retrieves the current battery status from the Battery Service.
    public BatteryStatusInfo getBatteryStatus(String batteryId) {
        
        // Call the method to connect to the Battery service
        connect();

        // Create the request.
        GetBatteryStatusRequest request = GetBatteryStatusRequest.newBuilder().setBatteryId(batteryId).build();
        // Invoke the Unary RPC.
        return stub.withDeadlineAfter(4, TimeUnit.SECONDS).getBatteryStatus(request);
    }
    
    // ========= BIDIRECTIONAL METHOD ===========
    // Starts the bidirectional battery monitoring stream.
    public void monitorBatteryStatus(String batteryId, StreamObserver<BatteryMonitoringResponse> responseObserver) {

        // Connect to the Battery Service if necessary.
        connect();

        // Create a cancellable context for this monitoring session.
        cancellableContext = Context.current().withCancellation();

        // Execute the streaming call inside the cancellable context.
        cancellableContext.run(() -> {
            // Open the bidirectional stream.
            requestObserver = asyncStub.monitorBatteryStatus(responseObserver);

            // Create the START command.
            BatteryMonitoringRequest request = BatteryMonitoringRequest.newBuilder().setBatteryId(batteryId).setCommand(BatteryCommand.START).build();
            // Send the START command.
            requestObserver.onNext(request);

        });
    }

    // Closes the communication channel (it allows the other services to close the communication channel)
    public void shutdown() {
         if (channel != null) {
            channel.shutdown();
        }
    }
}
