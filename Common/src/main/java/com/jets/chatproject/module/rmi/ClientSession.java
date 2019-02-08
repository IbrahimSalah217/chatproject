/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.module.rmi;

import com.jets.chatproject.module.rmi.dto.FriendshipDTO;
import com.jets.chatproject.module.rmi.dto.GroupDTO;
import com.jets.chatproject.module.rmi.dto.MessageDTO;
import com.jets.chatproject.module.rmi.dto.RequestDTO;
import com.jets.chatproject.module.rmi.dto.UserDTO;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author ibrahim
 */
public interface ClientSession extends Remote {

    void updateProfile(UserDTO user) throws RemoteException;

    UserDTO findUserById(int id) throws RemoteException;

    UserDTO findUserByPhone(String phone) throws RemoteException;

    void sendRequest(String phone) throws RemoteException;

    List<RequestDTO> getAllRequests() throws RemoteException;

    void acceptRequest(RequestDTO request) throws RemoteException;

    void rejectRequest(RequestDTO request) throws RemoteException;

    void blockUser(UserDTO user) throws RemoteException;

    List<FriendshipDTO> getAllFriendships() throws RemoteException;

    void updateFriendship(FriendshipDTO friendship) throws RemoteException;

    void createGroup(GroupDTO group) throws RemoteException;

    List<GroupDTO> getAllGroups() throws RemoteException;

    List<UserDTO> getGroupMembers(int groupId) throws RemoteException;

    void addFriendToGroup(int friendId, int groupId) throws RemoteException;

    List<MessageDTO> getDirectMessages(int friendId) throws RemoteException;

    List<MessageDTO> getGroupMessages(int groupId) throws RemoteException;

    void markMessageAsRead(int messageId, int friendId) throws RemoteException;

    void sendDirectMessage(int friendId, MessageDTO message) throws RemoteException;

    void sendGroupMessage(int groupId, MessageDTO message) throws RemoteException;

}
