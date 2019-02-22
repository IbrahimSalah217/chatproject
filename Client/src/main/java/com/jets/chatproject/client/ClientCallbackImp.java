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

    static ClientCallbackImp sInstance = getInstance();

    private static ClientCallbackImp createSingleton() throws RemoteException {
        return new ClientCallbackImp();
    }

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
    }

    public void addMessageListener(MessageListener listener) {
        System.out.println("listener added");
        messageListeners.add(listener);
        System.out.println("currently listening: " + messageListeners.size());
    }

    public void removeMessageListener(MessageListener listener) {
        System.out.println("listener removed");
        messageListeners.remove(listener);
    }

    @Override
    public void receiveDirectMessage(int friendId, MessageDTO messageDTO) throws RemoteException {
        System.out.println("broadcasting message from: " + friendId);
        for (Iterator<MessageListener> iter = messageListeners.iterator(); iter.hasNext();) {
            MessageListener listener = iter.next();
            System.out.println("sending");
            try {
                listener.onDirectMessageReceived(friendId, messageDTO);
            } catch (Throwable th) {
                iter.remove();
            }
        }
    }

    @Override
    public void receiveGroupMessage(int groupId, MessageDTO messageDTO) throws RemoteException {
        messageListeners.forEach(listener -> {
            listener.onGroupMessageReceived(groupId, messageDTO);
        });
    }

    @Override
    public void receiveServerMessage(int userId, String message) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public interface MessageListener {

        void onDirectMessageReceived(int friendId, MessageDTO message);

        void onGroupMessageReceived(int groupId, MessageDTO message);
    }

}
