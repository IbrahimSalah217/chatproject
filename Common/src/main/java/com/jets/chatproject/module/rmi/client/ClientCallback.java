/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.module.rmi.client;

import com.jets.chatproject.module.rmi.dto.MessageDTO;
import com.jets.chatproject.module.rmi.dto.UserDTO;
import com.jets.chatproject.module.rmi.dto.UserStatus;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author ibrahim
 */
public interface ClientCallback extends Remote {

    public void receiveDirectMessage(int friendId, MessageDTO messageDTO) throws RemoteException;

    public void receiveGroupMessage(int groupId, MessageDTO messageDTO) throws RemoteException;

    public void receiveServerMessage(String message) throws RemoteException;

    public void friendupdateStatus(int friendID, UserStatus status) throws RemoteException;

    public void friendBlockedMe(int friendID) throws RemoteException;

    public void friendUnBlockedMe(int friendID) throws RemoteException;

}
