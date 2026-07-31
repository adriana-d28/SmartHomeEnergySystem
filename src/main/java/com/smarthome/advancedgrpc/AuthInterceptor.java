/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.advancedgrpc;


import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

/**
 *
 * @author Adriana Dinelly - ID 25165771
 *
 * Server interceptor responsible for reading Metadata received from
 * the client and storing it in the gRPC Context.
 *
 */
public class AuthInterceptor implements ServerInterceptor {

    /**
     * Intercepts every incoming gRPC request before it reaches
     * the service implementation.
     */
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        // Read the client name from the Metadata.
        String clientName = headers.get(GrpcConstants.CLIENT_NAME_KEY);

        // Read the request identifier from the Metadata.
        String requestId = headers.get(GrpcConstants.REQUEST_ID_KEY);

        // Read the application version from the Metadata.
        String applicationVersion = headers.get(GrpcConstants.APPLICATION_VERSION_KEY);

        // Display the received Metadata.
        System.out.println("------------ Incoming Metadata ------------");
        System.out.println("Client Name: " + clientName);
        System.out.println("Request ID: " + requestId);
        System.out.println("Application Version: " + applicationVersion);
        System.out.println("-------------------------------------------");

        // Store the client name in the Context.
        Context context = Context.current()
                .withValue(GrpcConstants.CLIENT_NAME_CONTEXT, clientName)
                .withValue(GrpcConstants.REQUEST_ID_CONTEXT, requestId)
                .withValue(GrpcConstants.APPLICATION_VERSION_CONTEXT, applicationVersion);

        // Continue the request using the updated Context.
        return Contexts.interceptCall(context, call, headers, next);

    }

}