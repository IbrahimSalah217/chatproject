/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.module.rmi;

import com.jets.chatproject.module.rmi.entities.User;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author ibrahim
 */
public interface AuthService extends Remote {

    ClientSession login(String phone, String password) throws RemoteException;

    ClientSession register(User user) throws RemoteException;

    void logout(ClientSession session) throws RemoteException;

}
