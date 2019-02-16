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
import com.jets.chatproject.server.module.dal.entities.DTOMapper;
import com.jets.chatproject.server.module.dal.entities.DirectMessage;
import com.jets.chatproject.server.module.dal.entities.Friendship;
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
public class FriendshipServiceImp implements FriendshipService {
    
    SessionManager sessionManager;
    UsersDao usersDao;
    PicturesDao picturesDao;
    FriendshipsDao friendshipsDao;
    DirectMessagesDao messagesDao;
    
    public FriendshipServiceImp(DaosFactory daosFactory, SessionManager sessionManager) {
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
            friendshipsDao.update(friendship);
        } catch (Exception ex) {
            Logger.getLogger(FriendshipServiceImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void unblockFriend(String session, int friendId) throws RemoteException {
        try {
            int userId = sessionManager.findUserId(session);
            Friendship friendship = friendshipsDao.findByUserAndFriend(userId, friendId);
            friendship.setBlocked(false);
            friendshipsDao.update(friendship);
        } catch (Exception ex) {
            Logger.getLogger(FriendshipServiceImp.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FriendshipServiceImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
