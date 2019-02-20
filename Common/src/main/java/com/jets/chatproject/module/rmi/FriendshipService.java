/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.module.rmi;

import com.jets.chatproject.module.rmi.dto.FriendshipDTO;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author ibrahim
 */
public interface FriendshipService extends Remote {

    List<FriendshipDTO> getAllFriendships(String session) throws RemoteException;

    void blockFriend(String session, int friendId) throws RemoteException;

    void unblockFriend(String session, int friendId) throws RemoteException;

    void setCategory(String session, int friendId, String category) throws RemoteException;
    
    boolean areFriends(String session,String phone) throws RemoteException;
}
