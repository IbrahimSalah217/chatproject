/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.cfg;

import com.jets.chatproject.client.ClientCallbackImp;
import com.jets.chatproject.module.rmi.client.ClientCallback;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author salma
 */
public class ServiceLocator {

    private static final Cache CACHE = new Cache();

    public static <T extends Remote> T getService(Class<T> serviceClass) throws Exception {
        Remote service = CACHE.getService(serviceClass);
        if (service == null) {
            if (serviceClass.equals(ClientCallback.class)) {
                service = ClientCallbackImp.getInstance();
            } else {
                service = lookup(serviceClass);
            }
            CACHE.addService(service);
        }
        return (T) service;
    }

    private static Remote lookup(Class<? extends Remote> serviceClass) throws Exception {
        System.out.println("server location: " + ServerConfiguration.REGISTRY_HOST
                + ":" + ServerConfiguration.REGISTRY_PORT);
        Registry registry = LocateRegistry.getRegistry(
                ServerConfiguration.REGISTRY_HOST, ServerConfiguration.REGISTRY_PORT);
        return registry.lookup(serviceClass.getName());
    }

}
