/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.rmi.imp;

import com.jets.chatproject.module.rmi.GroupsService;
import com.jets.chatproject.module.rmi.dto.GroupDTO;
import com.jets.chatproject.module.rmi.dto.GroupMemberDTO;
import com.jets.chatproject.module.rmi.dto.MessageDTO;
import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import com.jets.chatproject.server.module.dal.dao.GroupMembersDao;
import com.jets.chatproject.server.module.dal.dao.GroupMessagesDao;
import com.jets.chatproject.server.module.dal.dao.GroupsDao;
import com.jets.chatproject.server.module.dal.dao.PicturesDao;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
import com.jets.chatproject.server.module.dal.entities.DTOMapper;
import com.jets.chatproject.server.module.dal.entities.Group;
import com.jets.chatproject.server.module.dal.entities.GroupMember;
import com.jets.chatproject.server.module.dal.entities.GroupMessage;
import com.jets.chatproject.server.module.dal.entities.Picture;
import com.jets.chatproject.server.module.dal.entities.User;
import java.rmi.RemoteException;
import java.util.List;
import com.jets.chatproject.server.module.session.SessionManager;
import java.util.ArrayList;

/**
 *
 * @author ibrahim
 */
public class GroupsServiceImp implements GroupsService {

    SessionManager sessionManager;
    PicturesDao picturesDao;
    GroupsDao groupDao;
    GroupMembersDao groupMemberDao;
    GroupMessagesDao groupMessagesDao;
    UsersDao usersDao;

    public GroupsServiceImp(DaosFactory daosFactory, SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        picturesDao = daosFactory.getPicturesDao();
        groupMemberDao = daosFactory.getGroupMembersDao();
        groupMessagesDao = daosFactory.getGroupMessagesDao();
        usersDao = daosFactory.getUsersDao();
    }

    @Override
    public int createGroup(String session, String groupName, byte[] groupPicture) throws RemoteException {
        
        int userId = sessionManager.findUserId(session);
        int pictureId;
        try {
            pictureId = picturesDao.insert(new Picture(1, groupPicture));
        } catch (Exception ex) {
            throw new RemoteException("upload picture failed", ex);
        }
        Group group = new Group(1, userId, groupName, pictureId);
        int groupId;
        try {
            groupId = groupDao.insert(group);
        } catch (Exception ex) {
            throw new RemoteException("Database failed", ex);
        }
        return groupId;
    }

    @Override
    public void addGroupMember(String session, int groupId, int userId) throws RemoteException {
        
        try {
            GroupMember groupMember = new GroupMember(groupId, userId, -1);
            groupMemberDao.insert(groupMember);
        } catch (Exception ex) {
            throw new RemoteException("Database failed", ex);
        }
    }

    @Override
    public List<GroupDTO> getAllGroups(String session) throws RemoteException {
        
        List<GroupDTO> groupDTOList = new ArrayList<>();
        try {
            int userId = sessionManager.findUserId(session);
            User user = usersDao.findById(userId);
            List<Group> groupList = groupDao.findAllForUser(userId);
            for(Group group : groupList){
                GroupMessage groupMessage = groupMessagesDao.getLastMessage(group.getGroupId());
                MessageDTO messageDTO = DTOMapper.createMessageDTO(user, groupMessage);
                groupDTOList.add(new GroupDTO(group.getGroupId(), group.getAdminId(), group.getGroupName(), group.getPictureId(),
                        messageDTO));
            }
        } catch (Exception ex) {
            throw new RemoteException("Database failed", ex);
        }
        return groupDTOList;
    }

    @Override
    public List<GroupMemberDTO> getGroupMembers(String session, int groupId) throws RemoteException {
        
        List<GroupMemberDTO> groupMemberDTOList = new ArrayList<>();
        try {
            List<GroupMember> groupMembersList = groupMemberDao.findAllByGroup(groupId);
            for(GroupMember groupMember : groupMembersList){
                User user = usersDao.findById(groupMember.getUserId());
                GroupMemberDTO groupMemberDTO = DTOMapper.createGroupMemberDTO(user, groupMember);
                groupMemberDTOList.add(groupMemberDTO);
            }
        } catch (Exception ex) {
            throw new RemoteException("Database failed", ex);
        }
        return groupMemberDTOList;
    }

}
