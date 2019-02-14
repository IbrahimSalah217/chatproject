/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.cfg;

import java.rmi.Remote;

/**
 *
 * @author salma
 */
public class ServiceLocator {

    private static Cache cache = new Cache();

    public static Remote getService(Remote requiredService) {

        Remote service = cache.getService(requiredService);
        if (service != null) {

            return service;

        }
        ServerConnection serverConnection = new ServerConnection();
        Remote newRequiredService = serverConnection.lookup(requiredService);
        cache.addService(newRequiredService);
        return newRequiredService;

    }

}
