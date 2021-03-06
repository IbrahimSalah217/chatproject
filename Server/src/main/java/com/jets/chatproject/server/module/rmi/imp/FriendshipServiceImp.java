/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.rmi.imp;

import com.jets.chatproject.module.rmi.FriendshipService;
import com.jets.chatproject.module.rmi.dto.FriendshipDTO;
import com.jets.chatproject.module.rmi.dto.MessageDTO;
import com.jets.chatproject.module.rmi.dto.MessageFormat;
import com.jets.chatproject.module.rmi.dto.MessageType;
import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import com.jets.chatproject.server.module.dal.dao.DirectMessagesDao;
import com.jets.chatproject.server.module.dal.dao.FriendshipsDao;
import com.jets.chatproject.server.module.dal.dao.PicturesDao;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
import com.jets.chatproject.server.module.dal.entities.DTOMapper;
import com.jets.chatproject.server.module.dal.entities.DirectMessage;
import com.jets.chatproject.server.module.dal.entities.Friendship;
import com.jets.chatproject.server.module.dal.entities.User;
import com.jets.chatproject.server.module.session.Broadcaster;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import com.jets.chatproject.server.module.session.SessionManager;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ibrahim
 */
public class FriendshipServiceImp extends UnicastRemoteObject implements FriendshipService {

    SessionManager sessionManager;
    UsersDao usersDao;
    PicturesDao picturesDao;
    FriendshipsDao friendshipsDao;
    DirectMessagesDao messagesDao;

    public FriendshipServiceImp(DaosFactory daosFactory, SessionManager sessionManager) throws RemoteException {
        this.sessionManager = sessionManager;
        usersDao = daosFactory.getUsersDao();
        picturesDao = daosFactory.getPicturesDao();
        friendshipsDao = daosFactory.getFriendshipsDao();
        messagesDao = daosFactory.getDirectMessagesDao();
    }

    @Override
    public List<FriendshipDTO> getAllFriendships(String session) throws RemoteException {
        try {
            int userId = sessionManager.findUserId(session);
            List<Friendship> friendships = friendshipsDao.getAllFriendshipsForUser(userId);
            List<FriendshipDTO> result = new ArrayList<>();
            for (Friendship friendship : friendships) {
                User friend = usersDao.findById(friendship.getFriendId());
                DirectMessage lastMessage = messagesDao.getLastDirectMessage(userId, friend.getId());
                if (lastMessage == null) {
                    lastMessage = new DirectMessage(-1, friend.getId(), -1, MessageType.PLAIN_TEXT,
                            "Start conversation", new MessageFormat().toString(), new Date());
                }
                User sender = usersDao.findById(lastMessage.getSenderId());
                MessageDTO messageDTO = DTOMapper.createMessageDTO(sender, lastMessage);
                result.add(DTOMapper.createFriendshipDTO(friend, friendship, messageDTO));
            }
            return result;
        } catch (Exception ex) {
            throw new RemoteException("Database exception", ex);
        }
    }

    @Override
    public void blockFriend(String session, int friendId) throws RemoteException {
        try {
            int userId = sessionManager.findUserId(session);
            Friendship friendship = friendshipsDao.findByUserAndFriend(userId, friendId);
            friendship.setBlocked(true);
            friendship.setLastSeenMessageId(-1);
            friendshipsDao.update(friendship);
            Broadcaster.getInstance().broadcastBlocked(userId, friendId);
        } catch (Exception ex) {
            throw new RemoteException("data base Exception", ex);
        }
    }

    @Override
    public void unblockFriend(String session, int friendId) throws RemoteException {
        try {
            int userId = sessionManager.findUserId(session);
            Friendship friendship = friendshipsDao.findByUserAndFriend(userId, friendId);
            friendship.setBlocked(false);
            friendship.setLastSeenMessageId(-1);
            friendshipsDao.update(friendship);
            Broadcaster.getInstance().broadcastUnBlocked(userId, friendId);
        } catch (Exception ex) {
            throw new RemoteException("data base Exception", ex);
        }
    }

    @Override
    public void setCategory(String session, int friendId, String category) throws RemoteException {
        try {
            int userId = sessionManager.findUserId(session);
            Friendship friendship = friendshipsDao.findByUserAndFriend(userId, friendId);
            friendship.setCategory(category);
            friendshipsDao.update(friendship);
        } catch (Exception ex) {
            throw new RemoteException("data base Exception", ex);
        }
    }

    @Override
    public boolean areFriends(String session, String phone) throws RemoteException {
        boolean areFriends = false;
        int userId = sessionManager.findUserId(session);

        try {
            User friend = usersDao.findByPhone(phone);
            Friendship friendship = friendshipsDao.findByUserAndFriend(userId, friend.getId());
            if (friendship != null) {
                areFriends = true;
            } else {
                areFriends = false;
            }
        } catch (Exception ex) {
            throw new RemoteException("data base Exception", ex);
        }
        return areFriends;
    }

    @Override
    public boolean isBlockedByFriend(String session, int friendId) throws RemoteException {
        int userId = sessionManager.findUserId(session);
        try {
            Friendship friendship = friendshipsDao.findByUserAndFriend(friendId, userId);
            if (friendship != null) {
                return friendship.isBlocked();
            } else {
                return false;
            }
        } catch (Exception ex) {
            throw new RemoteException("data base Exception", ex);
        }
    }

}
