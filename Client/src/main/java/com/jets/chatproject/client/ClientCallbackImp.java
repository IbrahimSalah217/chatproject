/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client;

import com.jets.chatproject.module.rmi.client.ClientCallback;
import com.jets.chatproject.module.rmi.dto.MessageDTO;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ibrahim
 */
public class ClientCallbackImp extends UnicastRemoteObject implements ClientCallback {

    List<MessageListener> messageListeners;
    List<AnnouncementListener> announcementListeners;

    static ClientCallbackImp sInstance = getInstance();

    public static ClientCallbackImp getInstance() {
        if (sInstance == null) {
            try {
                sInstance = new ClientCallbackImp();
            } catch (RemoteException ex) {
                Logger.getLogger(ClientCallbackImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sInstance;
    }

    private ClientCallbackImp() throws RemoteException {
        messageListeners = new LinkedList<>();
        announcementListeners = new LinkedList<>();
    }

    public void addMessageListener(MessageListener listener) {
        messageListeners.add(listener);
    }

    public void removeMessageListener(MessageListener listener) {
        messageListeners.remove(listener);
    }

    public void addAnnouncementListener(AnnouncementListener listener) {
        announcementListeners.add(listener);
    }

    public void removeAnnouncementListener(AnnouncementListener listener) {
        announcementListeners.remove(listener);
    }

    @Override
    public void receiveDirectMessage(int friendId, MessageDTO messageDTO) throws RemoteException {
        Iterator<MessageListener> iter = messageListeners.iterator();
        while (iter.hasNext()) {
            MessageListener listener = iter.next();
            try {
                listener.onDirectMessageReceived(friendId, messageDTO);
            } catch (Exception ex) {
                ex.printStackTrace();
                iter.remove();
            }
        }
    }

    @Override
    public void receiveGroupMessage(int groupId, MessageDTO messageDTO) throws RemoteException {
        Iterator<MessageListener> iter = messageListeners.iterator();
        while (iter.hasNext()) {
            MessageListener listener = iter.next();
            try {
                listener.onGroupMessageReceived(groupId, messageDTO);
            } catch (Exception ex) {
                ex.printStackTrace();
                iter.remove();
            }
        }
    }

    @Override
    public void receiveServerMessage(String message) throws RemoteException {
        Iterator<AnnouncementListener> iter = announcementListeners.iterator();
        while (iter.hasNext()) {
            AnnouncementListener listener = iter.next();
            try {
                listener.onServerMessageReceived(message);
            } catch (Exception ex) {
                ex.printStackTrace();
                iter.remove();
            }
        }
    }

    public interface MessageListener {

        void onDirectMessageReceived(int friendId, MessageDTO message);

        void onGroupMessageReceived(int groupId, MessageDTO message);
    }

    public interface AnnouncementListener {

        void onServerMessageReceived(String message);
    }

}
