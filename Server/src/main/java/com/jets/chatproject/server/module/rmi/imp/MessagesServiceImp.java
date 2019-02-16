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
import com.jets.chatproject.server.module.dal.dao.GroupMessagesDao;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
import com.jets.chatproject.server.module.dal.dao.imp.DirectMessagesDaoImp;
import com.jets.chatproject.server.module.dal.entities.DirectMessage;
import com.jets.chatproject.server.module.dal.entities.Friendship;
import com.jets.chatproject.server.module.dal.entities.GroupMessage;
import com.jets.chatproject.server.module.dal.entities.User;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import com.jets.chatproject.server.module.session.SessionManager;

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

    public MessagesServiceImp(DaosFactory daosFactory, SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        userDoa = daosFactory.getUsersDao();
        directmessageDao = daosFactory.getDirectMessagesDao();
        friendShipDao = daosFactory.getFriendshipsDao();
        groupMessageDao = daosFactory.getGroupMessagesDao();
    }

    @Override
    public List<MessageDTO> getAllGroupMessages(String session, int groupId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<MessageDTO> getAllDirectMessages(String session, int friendId) throws RemoteException {
        int userId = sessionManager.findUserId(session);
        User user = userDoa.findById(userId);
        List<DirectMessage> messageList =  directmessageDao.getAllDirectMessages(userId, friendId);
        List<MessageDTO> messageDtoList = new ArrayList<>();
        for(DirectMessage m : messageList){
            messageDtoList.add(new MessageDTO(m.getMessageId(),userId,user.getDisplyName(),
                    m.getMessageType(),m.getContent(),MessageFormat.of(m.getStyle()),m.getMessageTime()));
        }
        return messageDtoList;
    }

    @Override
    public void sendGroupMessage(String session, int groupId, MessageDTO message) throws RemoteException {
        int userId = sessionManager.findUserId(session);
        groupMessageDao.insert(new GroupMessage(message.getId(),userId,groupId,message.getType(),message.getContent()
                ,message.getFormat().toString(),message.getTimestamp()));
    }

    @Override
    public void sendDirectMessage(String session, int friendId, MessageDTO message) throws RemoteException {
        int userId = sessionManager.findUserId(session);
        directmessageDao.insert(new DirectMessage(message.getId(),userId,friendId,message.getType(),message.getContent(),
                message.getFormat().toString(),message.getTimestamp()));
    }

    @Override
    public void markGroupMessageRead(String session,int groubId) throws RemoteException {
        
    }

    @Override
    public void markDirectMessageRead(String session,int friendId) throws RemoteException {
        int userId = sessionManager.findUserId(session);
        Friendship friendShip = friendShipDao.findByUserAndFriend(userId, friendId);
        int lastMessageId = directmessageDao.getLastDirectMessage(friendId, userId).getMessageId();
        if(!(friendShipDao.update(new Friendship(userId,friendId, friendShip.getCategory(), 
                friendShip.isBlocked(),lastMessageId))))
            throw new RemoteException("update faild");
        
    }

}
