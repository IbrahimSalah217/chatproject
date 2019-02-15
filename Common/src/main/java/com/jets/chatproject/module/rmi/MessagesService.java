/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.module.rmi;

import com.jets.chatproject.module.rmi.dto.MessageDTO;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author ibrahim
 */
public interface MessagesService extends Remote {

    List<MessageDTO> getAllGroupMessages(String session, int groupId) throws RemoteException;

    List<MessageDTO> getAllDirectMessages(String session, int friendId) throws RemoteException;

    void sendGroupMessage(String session, int groupId, MessageDTO message) throws RemoteException;

    void sendDirectMessage(String session, int friendId, MessageDTO message) throws RemoteException;

    void markGroupMessageRead(String session,int friendId) throws RemoteException;

    void markDirectMessageRead(String session,int groubId) throws RemoteException;

}
