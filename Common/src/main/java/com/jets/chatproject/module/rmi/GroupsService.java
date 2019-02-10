/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.module.rmi;

import com.jets.chatproject.module.rmi.dto.GroupDTO;
import com.jets.chatproject.module.rmi.dto.GroupMemberDTO;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author ibrahim
 */
public interface GroupsService extends Remote {

    //return the id of the newly created group
    int createGroup(String session, String groupName) throws RemoteException;

    void addGroupMember(String session, int groupId, int userId) throws RemoteException;

    List<GroupDTO> getAllGroups(String session) throws RemoteException;

    List<GroupMemberDTO> getGroupMembers(String session, int groupId) throws RemoteException;

}
