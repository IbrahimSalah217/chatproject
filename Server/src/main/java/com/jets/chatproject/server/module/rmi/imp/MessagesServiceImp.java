/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.rmi.imp;

import com.jets.chatproject.module.rmi.MessagesService;
import com.jets.chatproject.module.rmi.dto.Gender;
import com.jets.chatproject.module.rmi.dto.MessageDTO;
import com.jets.chatproject.module.rmi.dto.MessageFormat;
import com.jets.chatproject.module.rmi.dto.MessageType;
import com.jets.chatproject.module.rmi.dto.UserDTO;
import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import com.jets.chatproject.server.module.dal.dao.DirectMessagesDao;
import com.jets.chatproject.server.module.dal.dao.FriendshipsDao;
import com.jets.chatproject.server.module.dal.dao.GroupMembersDao;
import com.jets.chatproject.server.module.dal.dao.GroupMessagesDao;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
import com.jets.chatproject.server.module.dal.dao.imp.DirectMessagesDaoImp;
import com.jets.chatproject.server.module.dal.entities.DirectMessage;
import com.jets.chatproject.server.module.dal.entities.Friendship;
import com.jets.chatproject.server.module.dal.entities.GroupMember;
import com.jets.chatproject.server.module.dal.entities.GroupMessage;
import com.jets.chatproject.server.module.dal.entities.User;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import com.jets.chatproject.server.module.session.SessionManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ibrahim
 */
public class MessagesServiceImp implements MessagesService {

    SessionManager sessionManager;
    UsersDao userDoa;
    DirectMessagesDao directmessageDao;
    FriendshipsDao friendShipDao;
    GroupMessagesDao groupMessageDao;
    GroupMembersDao groupMembersDao;

    public MessagesServiceImp(DaosFactory daosFactory, SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        userDoa = daosFactory.getUsersDao();
        directmessageDao = daosFactory.getDirectMessagesDao();
        friendShipDao = daosFactory.getFriendshipsDao();
        groupMessageDao = daosFactory.getGroupMessagesDao();
        groupMembersDao = daosFactory.getGroupMembersDao();
    }

    @Override
    public List<MessageDTO> getAllGroupMessages(String session, int groupId) throws RemoteException {
        List<MessageDTO> messageDtoList = new ArrayList<>();
        try {
            int userId = sessionManager.findUserId(session);
            User user = userDoa.findById(userId);
            List<GroupMessage> messageList = groupMessageDao.getAllGroupMessages(groupId);
            for (GroupMessage m : messageList) {
                messageDtoList.add(new MessageDTO(m.getMessageId(), userId, user.getDisplyName(),
                        m.getMessageType(), m.getContent(), MessageFormat.of(m.getStyle()), m.getMessageTime()));
            }
        } catch (Exception ex) {
            throw new RemoteException("database Error getAllGroupMessages", ex);
        }

        return messageDtoList;
    }

    @Override
    public List<MessageDTO> getAllDirectMessages(String session, int friendId) throws RemoteException {
        List<MessageDTO> messageDtoList = new ArrayList<>();
        try {
            int userId = sessionManager.findUserId(session);
            User user = userDoa.findById(userId);
            List<DirectMessage> messageList = directmessageDao.getAllDirectMessages(userId, friendId);
            for (DirectMessage m : messageList) {
                messageDtoList.add(new MessageDTO(m.getMessageId(), userId, user.getDisplyName(),
                        m.getMessageType(), m.getContent(), MessageFormat.of(m.getStyle()), m.getMessageTime()));
            }
        } catch (Exception ex) {
            throw new RemoteException("database Error getAllDirectMessages", ex);
        }

        return messageDtoList;
    }

    @Override
    public void sendGroupMessage(String session, int groupId, MessageDTO message) throws RemoteException {
        try {
            int userId = sessionManager.findUserId(session);
            groupMessageDao.insert(new GroupMessage(message.getId(), userId, groupId, message.getType(), message.getContent(),
                    message.getFormat().toString(), message.getTimestamp()));
        } catch (Exception ex) {
            throw new RemoteException("database Error insert new group message", ex);

        }
    }

    @Override
    public void sendDirectMessage(String session, int friendId, MessageDTO message) throws RemoteException {
        try {
            int userId = sessionManager.findUserId(session);
            directmessageDao.insert(new DirectMessage(message.getId(), userId, friendId, message.getType(), message.getContent(),
                    message.getFormat().toString(), message.getTimestamp()));
        } catch (Exception ex) {
            throw new RemoteException("database Error insert new direct message", ex);
        }
    }

    @Override
    public void markGroupMessageRead(String session, int groubId) throws RemoteException {
        try {
            int userId = sessionManager.findUserId(session);
            GroupMember groupMember = groupMembersDao.findByGroupAndUser(groubId, userId);
            GroupMessage lastMessage = groupMessageDao.getLastMessage(groubId);
            groupMember.setLastSeenMessageId(lastMessage.getMessageId());
            groupMembersDao.update(new GroupMember(groubId, userId, lastMessage.getMessageId()));
        } catch (Exception ex) {
            throw new RemoteException("database Error set last group message", ex);

        }
    }

    @Override
    public void markDirectMessageRead(String session, int friendId) throws RemoteException {
        try {
            int userId = sessionManager.findUserId(session);
            Friendship friendShip = friendShipDao.findByUserAndFriend(userId, friendId);
            int lastMessageId = directmessageDao.getLastDirectMessage(friendId, userId).getMessageId();
            friendShip.setLastSeenMessageId(lastMessageId);
            friendShipDao.update(new Friendship(userId, friendId, friendShip.getCategory(),
                    friendShip.isBlocked(), lastMessageId));
        } catch (Exception ex) {
            throw new RemoteException("database Error set last direct message", ex);

        }

    }

}
