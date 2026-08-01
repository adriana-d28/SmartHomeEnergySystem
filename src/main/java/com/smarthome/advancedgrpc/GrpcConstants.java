/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.advancedgrpc;

// Import Metadata class used by gRPC.
import io.grpc.Metadata;
// Import gRPC Context.
import io.grpc.Context;


/**
 *
 * @author Adriana Dinelly - ID 25165771
 *
 * Stores all Metadata and Context keys shared by the gRPC clients and servers.
 *
 */
public final class GrpcConstants {

    // Prevent this utility class from being instantiated.
    private GrpcConstants() {
    }

    // ========== Metadata Keys ===========

    // Metadata key used to identify the client application.
    public static final Metadata.Key<String> CLIENT_NAME_KEY =
            Metadata.Key.of("client-name", Metadata.ASCII_STRING_MARSHALLER);

    // Metadata key used to uniquely identify each request.
    public static final Metadata.Key<String> REQUEST_ID_KEY =
            Metadata.Key.of("request-id", Metadata.ASCII_STRING_MARSHALLER);

    // Metadata key used to identify the application version.
    public static final Metadata.Key<String> APPLICATION_VERSION_KEY =
            Metadata.Key.of("application-version", Metadata.ASCII_STRING_MARSHALLER);

    // Metadata key reserved for JWT authentication.
    public static final Metadata.Key<String> AUTHORIZATION_KEY =
            Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER);

    // ========= Context Keys ============


    // Context key used to store the client name.
    public static final Context.Key<String> CLIENT_NAME_CONTEXT =
            Context.key("client-name");

    // Context key used to store the request identifier.
    public static final Context.Key<String> REQUEST_ID_CONTEXT =
            Context.key("request-id");

    // Context key used to store the application version.
    public static final Context.Key<String> APPLICATION_VERSION_CONTEXT =
            Context.key("application-version");
    
    // Context key used to store the authenticated username.
    public static final Context.Key<String> USERNAME_CONTEXT =
            Context.key("username");

}