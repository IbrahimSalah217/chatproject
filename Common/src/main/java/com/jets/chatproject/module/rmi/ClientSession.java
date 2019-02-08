/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.module.rmi;

import com.jets.chatproject.module.rmi.entities.Friendship;
import com.jets.chatproject.module.rmi.entities.Group;
import com.jets.chatproject.module.rmi.entities.Message;
import com.jets.chatproject.module.rmi.entities.Request;
import com.jets.chatproject.module.rmi.entities.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author ibrahim
 */
public interface ClientSession extends Remote {

    void sendRequest(String phone) throws RemoteException;

    List<Request> getAllRequests() throws RemoteException;

    void acceptRequest(Request request) throws RemoteException;

    void rejectRequest(Request request) throws RemoteException;

    List<Friendship> getAllFriendships() throws RemoteException;

    void updateFriendship(Friendship friendship) throws RemoteException;

    void createGroup(Group group) throws RemoteException;

    List<Group> getAllGroups() throws RemoteException;

    List<User> getGroupMembers(int groupId) throws RemoteException;

    List<Message> getDirectMessages(int friendId) throws RemoteException;

    List<Message> getGroupMessages(int groupId) throws RemoteException;

    void sendDirectMessage(int friendId, Message message) throws RemoteException;

    void sendGroupMessage(int groupId, Message message) throws RemoteException;

}
