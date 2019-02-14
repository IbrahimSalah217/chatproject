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
public class ServerConnection {
    public static Remote lookup(Remote remoteService){
        Remote returnedService = null;
        
        try {
            
            Registry registry = LocateRegistry.getRegistry("127.0.0.1",5000);
            returnedService = registry.lookup(remoteService.toString());
            
        } catch (RemoteException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnedService;
    }
}
