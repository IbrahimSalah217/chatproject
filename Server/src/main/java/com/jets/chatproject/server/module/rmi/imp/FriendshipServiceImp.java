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
import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import com.jets.chatproject.server.module.dal.dao.DirectMessagesDao;
import com.jets.chatproject.server.module.dal.dao.FriendshipsDao;
import com.jets.chatproject.server.module.dal.dao.PicturesDao;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
import com.jets.chatproject.server.module.dal.entities.DirectMessage;
import com.jets.chatproject.server.module.dal.entities.Friendship;
import com.jets.chatproject.server.module.dal.entities.User;
import com.jets.chatproject.server.module.session.ISessionManager;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ibrahim
 */
public class FriendshipServiceImp implements FriendshipService {

    ISessionManager sessionManager;
    UsersDao usersDao;
    PicturesDao picturesDao;
    FriendshipsDao friendshipsDao;
    DirectMessagesDao messagesDao;

    public FriendshipServiceImp(DaosFactory daosFactory, ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
        usersDao = daosFactory.getUsersDao();
        picturesDao = daosFactory.getPicturesDao();
        friendshipsDao = daosFactory.getFriendshipsDao();
        messagesDao = daosFactory.getDirectMessagesDao();
    }

    @Override
    public List<FriendshipDTO> getAllFriendships(String session) throws RemoteException {
        int userId = sessionManager.findUserId(session);
        List<Friendship> friendships = friendshipsDao.getAllFriendshipsForUser(userId);
        List<FriendshipDTO> result = new ArrayList<>();
        for (Friendship f : friendships) {
            User friend = usersDao.findById(f.getFriendId());
            DirectMessage lastMessage = messagesDao.getLastDirectMessage(userId, friend.getId());
            User sender = usersDao.findById(lastMessage.getSenderId());
            MessageDTO messageDTO = new MessageDTO(lastMessage.getMessageId(),
                    sender.getId(), sender.getDisplyName(),
                    lastMessage.getMessageType(), lastMessage.getContent(),
                    MessageFormat.of(lastMessage.getStyle()), lastMessage.getMessageTime());
            result.add(new FriendshipDTO(friend.getId(), friend.getDisplyName(),
                    friend.getPictureId(), f.getCategory(), messageDTO,
                    f.getLastSeenMessageId(), 0));
        }
        return result;
    }

    @Override
    public void blockFriend(String session, int friendId) throws RemoteException {
        int userId = sessionManager.findUserId(session);
        Friendship friendship = friendshipsDao.findByUserAndFriend(userId, friendId);
        friendship.setBlocked(true);
        friendshipsDao.update(friendship);
    }

    @Override
    public void unblockFriend(String session, int friendId) throws RemoteException {
        int userId = sessionManager.findUserId(session);
        Friendship friendship = friendshipsDao.findByUserAndFriend(userId, friendId);
        friendship.setBlocked(false);
        friendshipsDao.update(friendship);
    }

    @Override
    public void setCategory(String session, int friendId, String category) throws RemoteException {
        int userId = sessionManager.findUserId(session);
        Friendship friendship = friendshipsDao.findByUserAndFriend(userId, friendId);
        friendship.setCategory(category);
        friendshipsDao.update(friendship);
    }

}
