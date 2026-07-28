/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.discovery;

import java.io.IOException;
import java.net.InetAddress;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

/**
 * Registers a gRPC service using jmDNS.
 * This class is responsible only for publishing services on the local network.
 *
 * @author Adriana Dinelly - ID 25165771
 * 
 */
public class JmDNSRegistration {
    // jmDNS instance used to register services.
    private JmDNS jmdns;
    
    // Service type that will be passed when registering jmDNS instance
    private static final String SERVICE_TYPE = "_grpc._tcp.local.";

    /**
     * Creates the jmDNS instance.
     *
     * @throws IOException if the network interface cannot be initialized.
     */
    public JmDNSRegistration() throws IOException {
        
        // the jmDNS instance is associated to the IP address of the machine in which the server runs
        jmdns = JmDNS.create(InetAddress.getLocalHost());
    }

    /**
     * Registers a service on the local network.
     *
     * @param serviceName Name of the service.
     * @param serviceType Service type (e.g. "_grpc._tcp.local.").
     * @param port Service port.
     * @param description Service description.
     *
     * @throws IOException if registration fails.
     */
    public void registerService(String serviceName, int port, String description) throws IOException {

        ServiceInfo serviceInfo = ServiceInfo.create(SERVICE_TYPE, serviceName, port, description);

        jmdns.registerService(serviceInfo);

        System.out.println(serviceName + " registered successfully.");
    }

    /**
     * Closes the jmDNS instance.
     *
     * @throws IOException if closing fails.
     */
    public void close() throws IOException {
        if (jmdns != null) {
            jmdns.close();
        }
    }
}
