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

import javax.jmdns.ServiceInfo;
import com.smarthome.discovery.JmDNSDiscovery;
import com.smarthome.solar.MonitorProductionRequest;
import io.grpc.stub.StreamObserver;
import io.grpc.Context;

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

            // Create the Blocking and Async Stub.
            stub = SolarPanelServiceGrpc.newBlockingStub(channel);
            asyncStub = SolarPanelServiceGrpc.newStub(channel);
            
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
        return stub.getCurrentProduction(request);
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
