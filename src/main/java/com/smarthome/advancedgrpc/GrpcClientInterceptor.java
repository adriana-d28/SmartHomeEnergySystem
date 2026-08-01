/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.advancedgrpc;

// Import gRPC classes used to intercept client requests.
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;

// Import UUID generator.
import java.util.UUID;

/**
 *
 * @author Adriana Dinelly - ID 25165771
 *
 * Client interceptor responsible for attaching Metadata
 * to every outgoing gRPC request.
 *
 */
public class GrpcClientInterceptor implements ClientInterceptor {

    // Stores the name of the application/service sending the request.
    private final String clientName;
    // Stores the JWT used to authenticate outgoing requests.
    private String jwtToken;

    /**
     * Constructor.
     *
     * @param clientName Name of the client application.
     */
    public GrpcClientInterceptor(String clientName) {

        // Store the client name.
        this.clientName = clientName;
    }
    
    /**
     * Updates the JWT that will be attached to outgoing requests.
     *
     * @param jwtToken JWT generated after a successful login.
     */
    public void setJwtToken(String jwtToken) {

        // Store the JWT.
        this.jwtToken = jwtToken;

    }

    /**
     * This method is automatically executed before every outgoing RPC.
     */
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> method,
            CallOptions callOptions,
            Channel next) {

        // Create the original gRPC call.
        ClientCall<ReqT, RespT> clientCall = next.newCall(method, callOptions);

        // Return a wrapped client call.
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(clientCall) {

            /**
             * This method executes immediately before the request
             * is sent to the server.
             */
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {

                // Generate a unique request identifier.
                String requestId = UUID.randomUUID().toString();

                // Add the client name to the Metadata.
                headers.put(GrpcConstants.CLIENT_NAME_KEY, clientName);

                // Add the unique request identifier.
                headers.put(GrpcConstants.REQUEST_ID_KEY, requestId);

                // Add the application version.
                headers.put(GrpcConstants.APPLICATION_VERSION_KEY, "1.0");

                // Add the JWT if one is available - it first verifies if the user is authenticated so it can generate the header.
                if (jwtToken != null && !jwtToken.isEmpty()) {
                    // Add the JWT token to the metadata 
                    headers.put(GrpcConstants.AUTHORIZATION_KEY, jwtToken);
                }
                
                // Continue the RPC call normally.
                super.start(responseListener, headers);
            }
        };
    }
}