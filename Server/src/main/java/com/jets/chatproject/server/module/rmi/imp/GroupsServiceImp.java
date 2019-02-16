/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.rmi.imp;

import com.jets.chatproject.module.rmi.GroupsService;
import com.jets.chatproject.module.rmi.dto.GroupDTO;
import com.jets.chatproject.module.rmi.dto.GroupMemberDTO;
import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import java.rmi.RemoteException;
import java.util.List;
import com.jets.chatproject.server.module.session.SessionManager;

/**
 *
 * @author ibrahim
 */
public class GroupsServiceImp implements GroupsService {

    SessionManager sessionManager;

    public GroupsServiceImp(DaosFactory daosFactory, SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public int createGroup(String session, String groupName) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addGroupMember(String session, int groupId, int userId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<GroupDTO> getAllGroups(String session) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<GroupMemberDTO> getGroupMembers(String session, int groupId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
