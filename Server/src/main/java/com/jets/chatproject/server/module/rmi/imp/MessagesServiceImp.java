/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.rmi.imp;

import com.jets.chatproject.module.rmi.MessagesService;
import com.jets.chatproject.module.rmi.dto.MessageDTO;
import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import com.jets.chatproject.server.module.session.ISessionManager;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author ibrahim
 */
public class MessagesServiceImp implements MessagesService {

    ISessionManager sessionManager;

    public MessagesServiceImp(DaosFactory daosFactory, ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public List<MessageDTO> getAllGroupMessages(String session, int groupId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<MessageDTO> getAllDirectMessages(String session, int friendId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sendGroupMessage(String session, int groupId, MessageDTO message) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sendDirectMessage(String session, int friendId, MessageDTO message) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void markGroupMessageRead(String session, int messageId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void markDirectMessageRead(String session, int messageId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
