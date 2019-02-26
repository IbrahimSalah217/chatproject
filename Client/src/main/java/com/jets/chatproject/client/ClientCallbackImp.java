/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client;

import com.healthmarketscience.rmiio.RemoteInputStream;
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
    List<AnnouncementListener> announcementListeners;
    List<FriendListener> friendListeners;

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

    @Override
    public void friendSendRequest(int friendID) throws RemoteException {
        friendListeners.forEach((listener) -> {
            listener.onFriendSendRequest(friendID);
        });
    }

    @Override
    public void receiveVoice(int friendId, byte[] voiceArray) throws RemoteException {
        messageListeners.forEach((listener) -> {
            listener.onVoiceRecordRecieve(friendId, voiceArray);
        });
    }

    @Override
    public void receiveDirectFile(int friendId, String fileName, RemoteInputStream fileData) throws RemoteException {
        messageListeners.forEach((listener) -> {
            listener.onFileRecieve(friendId, fileName, fileData);
        });
    }

    public interface FriendListener {

        void onFiendStatusUpdated(int friendId, UserStatus friendStatus);

        void onFriendBlockedME(int friendId);

        void onFriendUnBlockedME(int friendId);

        void onFriendSendRequest(int friendId);

    }

    public interface MessageListener {

        void onDirectMessageReceived(int friendId, MessageDTO message);

        void onGroupMessageReceived(int groupId, MessageDTO message);

        void onVoiceRecordRecieve(int friendId, byte[] arrayVoice);
        
        void onFileRecieve(int friendId, String fileName, RemoteInputStream fileData);
    }

    public interface AnnouncementListener {

        void onServerMessageReceived(String message);
    }
    
    

}
