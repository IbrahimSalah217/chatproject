/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.module.rmi;

import com.jets.chatproject.module.rmi.client.ClientCallback;
import com.jets.chatproject.module.rmi.dto.UserDTO;
import com.jets.chatproject.module.rmi.dto.UserStatus;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author ibrahim
 */
public interface AuthService extends Remote {

    String login(String phone, String password) throws RemoteException;

    String register(UserDTO user, byte[] picture, String password) throws RemoteException;
    
    boolean registerbyServer(String phone) throws RemoteException;

    void logout(String session) throws RemoteException;

    boolean checkPhone(String phone) throws RemoteException;

    void setCallBack(String session, ClientCallback clientCallback) throws RemoteException;
    
    
}
