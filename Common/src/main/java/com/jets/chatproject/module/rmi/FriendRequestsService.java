/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.module.rmi;

import com.jets.chatproject.module.rmi.dto.RequestDTO;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author ibrahim
 */
public interface FriendRequestsService extends Remote {

    void sendRequest(String session, String phone) throws RemoteException;

    List<RequestDTO> getAllRequests(String session) throws RemoteException;

    void acceptRequest(String session, int senderId) throws RemoteException;

    void rejectRequest(String session, int senderId) throws RemoteException;
    
}
