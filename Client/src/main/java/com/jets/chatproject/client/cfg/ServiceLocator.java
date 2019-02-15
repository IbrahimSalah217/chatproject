/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.cfg;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author salma
 */
public class ServiceLocator {

    private static final Cache CACHE = new Cache();

    public static <T extends Remote> T getService(Class<T> serviceClass) {
        Remote service = CACHE.getService(serviceClass);
        if (service == null) {
            service = lookup(serviceClass);
            CACHE.addService(service);
        }
        return (T) service;
    }

    private static Remote lookup(Class<? extends Remote> serviceClass) {
        Remote returnedService = null;
        try {
            // TODO: set servre ip and port
            Registry registry = LocateRegistry.getRegistry();
            returnedService = registry.lookup(serviceClass.getName());
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(ServiceLocator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnedService;
    }

}
