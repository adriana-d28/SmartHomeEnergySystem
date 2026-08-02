/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.integrationclient;

import com.smarthome.solar.GetCurrentProductionRequest;
import com.smarthome.solar.ProductionInfo;
import com.smarthome.solar.SolarPanelServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.jmdns.ServiceInfo;
import com.smarthome.discovery.JmDNSDiscovery;
import com.smarthome.solar.MonitorProductionRequest;
import io.grpc.stub.StreamObserver;
import io.grpc.Context;

import com.smarthome.advancedgrpc.GrpcClientInterceptor;

import io.grpc.Channel;
import io.grpc.ClientInterceptors;

/**
 *
 * @author Adriana Dinelly - ID 25165771
 *
 * Internal gRPC client used by other services (Battery. Smart Meter, and GUI) to communicate
 * with the Solar Panel Service.
 *
 */
public class SolarGrpcClient {
    
    // Communication channel with the Solar Service.
    private ManagedChannel channel;
    // Blocking Stub used to invoke Unary RPCs.
    private SolarPanelServiceGrpc.SolarPanelServiceBlockingStub stub;
    // Asynchronous Stub used to invoke Server Streaming RPCs.
    private SolarPanelServiceGrpc.SolarPanelServiceStub asyncStub;
    // Cancellable context used to stop the monitoring stream.
    private Context.CancellableContext monitoringContext;
    // Client interceptor responsible for attaching Metadata and JWT.
    private final GrpcClientInterceptor interceptor = new GrpcClientInterceptor("SolarGrpcClient");

    // Constructor 
    public SolarGrpcClient() {
        
    }
    
    // This method discover the Solar Service and create the communication channel
    private void connect() {
        
        if (stub != null) {
            return;
        }
        
        try {

            // Create the discovery jmDNS instance.
            JmDNSDiscovery discovery = new JmDNSDiscovery();

            // Discover the Solar Service.
            ServiceInfo serviceInfo = discovery.discoverService("SolarService");

            // Close the discovery instance.
            discovery.close();

            // Create the communication channel.
            channel = ManagedChannelBuilder.forAddress(serviceInfo.getHostAddresses()[0], serviceInfo.getPort()).usePlaintext().build();

            // Create a channel that passes through the interceptor before reaching the server.
            Channel interceptedChannel = ClientInterceptors.intercept(channel, interceptor);

            // Create the Blocking Stub using the intercepted channel.
            stub = SolarPanelServiceGrpc.newBlockingStub(interceptedChannel);

            // Create the Async Stub using the intercepted channel.
            asyncStub = SolarPanelServiceGrpc.newStub(interceptedChannel);
            
        } catch (IOException e) {
            
            // Return a gRPC UNAVAILABLE error.
            throw io.grpc.Status.UNAVAILABLE.withDescription("Unable to discover the Solar Service.").withCause(e).asRuntimeException();
        }
    }

    // This method retrieves the current production from the Solar Service.
    public ProductionInfo getCurrentProduction(String panelId) {
        
        // Call the method to connect to the Solar service
        connect();

        // Create the request.
        GetCurrentProductionRequest request = GetCurrentProductionRequest.newBuilder().setPanelId(panelId).build();
        // Invoke the Unary RPC.
        return stub.withDeadlineAfter(2, TimeUnit.SECONDS).getCurrentProduction(request);
    }
    
    // This method starts the production monitoring stream.
    public void monitorProduction(String panelId, StreamObserver<ProductionInfo> responseObserver) {

        // Connect to the Solar Service if necessary.
        connect();
        
        // Cancel any previous monitoring session.
        if (monitoringContext != null) {
            monitoringContext.cancel(null);
        }

        // Create a new cancellable context.
        monitoringContext = Context.current().withCancellation();

        // Create the request.
        MonitorProductionRequest request = MonitorProductionRequest.newBuilder().setPanelId(panelId).build();

        // Invoke the Server Streaming RPC inside the cancellable context.
        monitoringContext.run(() -> asyncStub.monitorProduction(request, responseObserver));
    }
    

    // Helper method to stop the current production monitoring stream.
    public void stopMonitoring() {

        // Cancel the monitoring context if it exists.
        if (monitoringContext != null) {
            monitoringContext.cancel(null);
            monitoringContext = null;
        }
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

    // Closes the communication channel (this independent method allows the other services to close the communication channel)
    public void shutdown() {
        // Stop any active monitoring stream.
        stopMonitoring();

        // Shutdown the communication channel.
        if (channel != null) {
            channel.shutdown();
        }
    }
}
