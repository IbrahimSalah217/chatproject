/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.cfg;

import java.rmi.Remote;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author salma
 */
public class Cache {

    private List<Remote> services;

    public Cache() {
        services = new ArrayList<>();
    }

    public Remote getService(Remote requiredService) {

        for (Remote service : services) {
            if (requiredService.toString().equals(service.toString())) {

                return service;
            }
        }
        return null;
    }

    public void addService(Remote newService) {
        boolean exists = false;

        if (services.contains(newService)) {
            exists = true;

        }
        if (!exists) {
            services.add(newService);
        }

    }
}
