/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client;

import com.jets.chatproject.module.rmi.client.ClientCallback;
import com.jets.chatproject.module.rmi.dto.MessageDTO;
import com.jets.chatproject.module.rmi.dto.UserStatus;
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

    List<FriendListener> friendListeners;

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
        friendListeners = new LinkedList<>();
    }

    public void addFriendListener(FriendListener listener) {

        System.out.println("friend listener added");
        friendListeners.add(listener);
        System.out.println("currently listening: " + friendListeners.size());
    }

    public void removeFriendListener(FriendListener listener) {
        System.out.println("listener removed");
        friendListeners.remove(listener);
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
    public void receiveServerMessage(String message) throws RemoteException {
        messageListeners.forEach(listener -> {
            listener.onServerMessageReceived(message);
        });
    }

    @Override
    public void friendupdateStatus(int friendID, UserStatus status) throws RemoteException {
        friendListeners.forEach((listener) -> {
            listener.onFiendStatusUpdated(friendID, status);
        });

    }

    @Override
    public void friendBlockedMe(int friendID) throws RemoteException {
        friendListeners.forEach((listener) -> {
            listener.onFriendBlockedME(friendID);
        });
    }

    @Override
    public void friendUnBlockedMe(int friendID) throws RemoteException {
        friendListeners.forEach((listener) -> {
            listener.onFriendUnBlockedME(friendID);
        });
    }

    public interface FriendListener {

        void onFiendStatusUpdated(int friendId, UserStatus friendStatus);

        void onFriendBlockedME(int friendId);

        void onFriendUnBlockedME(int friendId);

    }

    public interface MessageListener {

        void onDirectMessageReceived(int friendId, MessageDTO message);

        void onGroupMessageReceived(int groupId, MessageDTO message);

        void onServerMessageReceived(String message);

    }

}
