/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.session;

import com.jets.chatproject.module.rmi.client.ClientCallback;
import com.jets.chatproject.module.rmi.dto.MessageDTO;
import com.jets.chatproject.module.rmi.dto.UserStatus;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ibrahim
 */
public class Broadcaster {

    private static final Broadcaster sInstance = createSingleTon();
    private final Map<Integer, List<ClientCallback>> map = new HashMap<>();

    private static Broadcaster createSingleTon() {
        return new Broadcaster();
    }

    public static Broadcaster getInstance() {
        return sInstance;
    }

    private Broadcaster() {
    }

    public void saveClient(int userId, ClientCallback clientCallback) {
        if (!map.containsKey(userId)) {
            map.put(userId, new LinkedList<>());
        }
        map.get(userId).add(clientCallback);
    }

    private void updateUserState(int userId) {
        if (map.containsKey(userId) && map.get(userId).isEmpty()) {
            map.remove(userId);
            // TODO: user offline
        }
    }

    public void broadcastDirectMessage(int userId, int friendId, MessageDTO messageDTO) {
        if (map.containsKey(userId)) {
            Iterator<ClientCallback> iterator = map.get(userId).iterator();
            while (iterator.hasNext()) {
                ClientCallback client = iterator.next();
                try {
                    client.receiveDirectMessage(friendId, messageDTO);
                } catch (RemoteException ex) {
                    iterator.remove();
                }
            }
            updateUserState(userId);
        }
    }

    public void broadcastGroupMessage(int userId, int groupId, MessageDTO messageDTO) {
        if (map.containsKey(userId)) {
            Iterator<ClientCallback> iterator = map.get(userId).iterator();
            while (iterator.hasNext()) {
                ClientCallback client = iterator.next();
                try {
                    client.receiveGroupMessage(groupId, messageDTO);
                } catch (RemoteException ex) {
                    iterator.remove();
                }
            }
            updateUserState(userId);
        }
    }

    public void broadcastFromServer(String message) {
        int c = 0;
        for (Map.Entry<Integer, List<ClientCallback>> entity : map.entrySet()) {
            Iterator<ClientCallback> iterator = entity.getValue().iterator();
            while (iterator.hasNext()) {
                ClientCallback client = iterator.next();
                try {
                    client.receiveServerMessage(message);
                    c++;
                } catch (RemoteException ex) {
                    iterator.remove();
                }
            }
            updateUserState(entity.getKey());
        }
        // System.out.println("Announcement sent to " + c + " clients");
    }

    public void broadcastStatus(int userId, List<Integer> friendIdList, UserStatus status) {
        friendIdList.forEach((friendId) -> {
            if (map.containsKey(friendId)) {
                Iterator<ClientCallback> iterator = map.get(friendId).iterator();
                while (iterator.hasNext()) {
                    ClientCallback client = iterator.next();
                    try {
                        client.friendupdateStatus(userId, status);
                    } catch (RemoteException ex) {
                        iterator.remove();
                    }
                }
                if (map.get(friendId).isEmpty()) {
                    map.remove(userId);
                    // TODO: user offline
                }
            }
        });
    }

    public void broadcastBlocked(int userId, int friendId) {
        if (map.containsKey(friendId)) {
            Iterator<ClientCallback> iterator = map.get(friendId).iterator();
            while (iterator.hasNext()) {
                ClientCallback client = iterator.next();
                try {
                    client.friendBlockedMe(userId);
                } catch (RemoteException ex) {
                    iterator.remove();
                }
            }
            if (map.get(friendId).isEmpty()) {
                map.remove(friendId);
                // TODO: user offline
            }
        }

    }

    public void broadcastUnBlocked(int userId, int friendId) {
        if (map.containsKey(friendId)) {
            Iterator<ClientCallback> iterator = map.get(friendId).iterator();
            while (iterator.hasNext()) {
                ClientCallback client = iterator.next();
                try {
                    client.friendUnBlockedMe(userId);
                } catch (RemoteException ex) {
                    iterator.remove();
                }
            }
            if (map.get(friendId).isEmpty()) {
                map.remove(friendId);
                // TODO: user offline
            }
        }

    }

    public void broadcastFriendRequest(int userId, int friendId) {
        if (map.containsKey(friendId)) {
            Iterator<ClientCallback> iterator = map.get(friendId).iterator();
            while (iterator.hasNext()) {
                ClientCallback client = iterator.next();
                try {
                    client.friendSendRequest(userId);
                } catch (RemoteException ex) {
                    iterator.remove();
                }
            }
            if (map.get(friendId).isEmpty()) {
                map.remove(friendId);
                // TODO: user offline
            }
        }

    }

    public void broadcastVoice(int userId, int friendId, byte[] voiceArray) {
        if (map.containsKey(friendId)) {
            Iterator<ClientCallback> iterator = map.get(friendId).iterator();
            while (iterator.hasNext()) {
                ClientCallback client = iterator.next();
                try {
                    client.receiveVoice(userId, voiceArray);
                } catch (RemoteException ex) {
                    iterator.remove();
                }
            }
            if (map.get(friendId).isEmpty()) {
                map.remove(friendId);
                // TODO: user offline
            }
        }
    }
}
