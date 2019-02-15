/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.cfg;

import java.rmi.Remote;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author salma
 */
public class Cache {

    private final Map<String, Remote> services;

    public Cache() {
        services = new HashMap<>();
    }

    public Remote getService(Class<? extends Remote> serviceClass) {
        Remote service = null;
        for (Map.Entry<String, Remote> entry : services.entrySet()) {
            String key = entry.getKey();
            if (key.equals(serviceClass.getName())) {
                service = entry.getValue();
                break;
            }
        }
        return service;
    }

    public void addService(Remote service) {
        services.put(service.getClass().getName(), service);
    }
}
