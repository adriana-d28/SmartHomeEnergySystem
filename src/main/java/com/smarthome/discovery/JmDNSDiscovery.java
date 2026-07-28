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
 * Discovers services registered through jmDNS.
 * This class is responsible only for locating services available on the local network.
 *
 * @author Adriana Dinelly - ID 25165771
 * 
 */
public class JmDNSDiscovery {

    // jmDNS instance used to discover services.
    private final JmDNS jmdns;
    
    // Service type that will be passed when discovering jmDNS instance
    private static final String SERVICE_TYPE = "_grpc._tcp.local.";

    /**
     * Creates the jmDNS discovery instance.
     * @throws IOException if the network interface cannot be initialized.
     */
    public JmDNSDiscovery() throws IOException {

        jmdns = JmDNS.create(InetAddress.getLocalHost());

    }

    /**
     * Discovers a registered service.
     *
     * @param serviceName Name of the service.
     *
     * @return ServiceInfo containing host and port.
     * @throws IOException if the discovery process fails.
     * 
     */
    public ServiceInfo discoverService(String serviceName) throws IOException {

        ServiceInfo serviceInfo = jmdns.getServiceInfo(SERVICE_TYPE, serviceName, 5000);

        if (serviceInfo == null) {
            
            throw new IOException("Service not found: " + serviceName);
        }
        return serviceInfo;
    }

    /**
     * Closes the jmDNS instance.
     * @throws IOException if closing fails.
     * 
     */
    public void close() throws IOException {

        jmdns.close();
    }

}