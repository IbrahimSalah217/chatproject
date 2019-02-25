/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.rmi.imp;

import com.jets.chatproject.module.rmi.MessagesService;
import com.jets.chatproject.module.rmi.dto.MessageDTO;
import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import com.jets.chatproject.server.module.dal.dao.DirectMessagesDao;
import com.jets.chatproject.server.module.dal.dao.FriendshipsDao;
import com.jets.chatproject.server.module.dal.dao.GroupMembersDao;
import com.jets.chatproject.server.module.dal.dao.GroupMessagesDao;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
import com.jets.chatproject.server.module.dal.entities.DTOMapper;
import com.jets.chatproject.server.module.dal.entities.DirectMessage;
import com.jets.chatproject.server.module.dal.entities.Friendship;
import com.jets.chatproject.server.module.dal.entities.GroupMember;
import com.jets.chatproject.server.module.dal.entities.GroupMessage;
import com.jets.chatproject.server.module.dal.entities.User;
import com.jets.chatproject.server.module.session.Broadcaster;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import com.jets.chatproject.server.module.session.SessionManager;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author ibrahim
 */
public class MessagesServiceImp extends UnicastRemoteObject implements MessagesService {

    SessionManager sessionManager;
    UsersDao userDoa;
    DirectMessagesDao directmessageDao;
    FriendshipsDao friendShipDao;
    GroupMessagesDao groupMessageDao;
    GroupMembersDao groupMembersDao;

    public MessagesServiceImp(DaosFactory daosFactory, SessionManager sessionManager) throws RemoteException {
        this.sessionManager = sessionManager;
        userDoa = daosFactory.getUsersDao();
        directmessageDao = daosFactory.getDirectMessagesDao();
        friendShipDao = daosFactory.getFriendshipsDao();
        groupMessageDao = daosFactory.getGroupMessagesDao();
        groupMembersDao = daosFactory.getGroupMembersDao();
    }

    @Override
    public List<MessageDTO> getAllGroupMessages(String session, int groupId) throws RemoteException {
        try {
            int userId = sessionManager.findUserId(session);
            User user = userDoa.findById(userId);
            List<MessageDTO> messages = new ArrayList<>();
            for (GroupMessage message : groupMessageDao.getAllGroupMessages(groupId)) {
                User sender = userDoa.findById(message.getSenderId());
                messages.add(DTOMapper.createMessageDTO(sender, message));
            }
            return messages;
        } catch (Exception ex) {
            throw new RemoteException("database Error getAllGroupMessages", ex);
        }

    }

    @Override
    public List<MessageDTO> getAllDirectMessages(String session, int friendId) throws RemoteException {
        try {
            int userId = sessionManager.findUserId(session);
            User user = userDoa.findById(userId);
            List<MessageDTO> messages = new ArrayList<>();
            for (DirectMessage message : directmessageDao.getAllDirectMessages(userId, friendId)) {
                User sender = userDoa.findById(message.getSenderId());
                messages.add(DTOMapper.createMessageDTO(sender, message));
            }
            return messages;
        } catch (Exception ex) {
            throw new RemoteException("Database exception", ex);
        }
    }

    @Override
    public void sendGroupMessage(String session, int groupId, MessageDTO messageDto) throws RemoteException {
        try {
            int userId = sessionManager.findUserId(session);
            GroupMessage message
                    = new GroupMessage(messageDto.getId(), userId, groupId,
                            messageDto.getType(), messageDto.getContent(),
                            messageDto.getFormat().toString(),
                            messageDto.getTimestamp());
            groupMessageDao.insert(message);
            messageDto.setSenderName(userDoa.findById(userId).getDisplyName());
            List<GroupMember> findAllByGroup = groupMembersDao.findAllByGroup(groupId);
            if (findAllByGroup != null) {
                findAllByGroup.forEach(groupMember -> {
                    Broadcaster.getInstance().broadcastGroupMessage(
                            groupMember.getUserId(), groupId, messageDto);
                });
            }
        } catch (Exception ex) {
            throw new RemoteException("Database exception", ex);
        }
    }

    @Override
    public void sendDirectMessage(String session, int friendId, MessageDTO messageDto) throws RemoteException {
        try {
            int userId = sessionManager.findUserId(session);
            DirectMessage message
                    = new DirectMessage(messageDto.getId(), userId, friendId,
                            messageDto.getType(), messageDto.getContent(),
                            messageDto.getFormat().toString(),
                            messageDto.getTimestamp());
            directmessageDao.insert(message);
            messageDto.setSenderName(userDoa.findById(userId).getDisplyName());
            Broadcaster.getInstance().broadcastDirectMessage(userId, friendId, messageDto);
            Broadcaster.getInstance().broadcastDirectMessage(friendId, userId, messageDto);
        } catch (Exception ex) {
            throw new RemoteException("Database exception", ex);
        }
    }

    @Override
    public void markGroupMessageRead(String session, int groubId) throws RemoteException {
        try {
            int userId = sessionManager.findUserId(session);
            GroupMember groupMember = groupMembersDao.findByGroupAndUser(groubId, userId);
            GroupMessage lastMessage = groupMessageDao.getLastMessage(groubId);
            groupMember.setLastSeenMessageId(lastMessage.getMessageId());
            groupMembersDao.update(groupMember);
        } catch (Exception ex) {
            throw new RemoteException("Database exception", ex);
        }
    }

    @Override
    public void markDirectMessageRead(String session, int friendId) throws RemoteException {
        try {
            int userId = sessionManager.findUserId(session);
            DirectMessage lasMessage = directmessageDao.getLastDirectMessage(userId, friendId);
            Friendship friendShip = friendShipDao.findByUserAndFriend(userId, friendId);
            friendShip.setLastSeenMessageId(lasMessage.getMessageId());
            friendShipDao.update(friendShip);
        } catch (Exception ex) {
            throw new RemoteException("Database exception", ex);
        }
    }

    /*Ibrahim Salah */
     @Override
    public void sendVoice(String session, int friendId, byte[] voiceArray) throws RemoteException {
        int userId = sessionManager.findUserId(session);
        Broadcaster.getInstance().broadcastVoice(userId, friendId, voiceArray);
    }
}
