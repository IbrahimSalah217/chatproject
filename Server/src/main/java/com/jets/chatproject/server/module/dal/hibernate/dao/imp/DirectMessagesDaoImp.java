/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.hibernate.dao.imp;

import com.jets.chatproject.module.rmi.dto.MessageType;
import com.jets.chatproject.server.module.dal.dao.DirectMessagesDao;
import com.jets.chatproject.server.module.dal.entities.DirectMessage;
import com.jets.chatproject.server.module.dal.hibernate.entity.DirectMessages;
import com.jets.chatproject.server.module.dal.hibernate.entity.Friendships;
import com.jets.chatproject.server.module.dal.hibernate.entity.FriendshipsId;
import com.jets.chatproject.server.module.dal.hibernate.entity.Users;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ibrahim
 */
public class DirectMessagesDaoImp implements DirectMessagesDao {

    Session session;

    public DirectMessagesDaoImp(Session session) {
        this.session = session;
    }

    private DirectMessage mapMsg(DirectMessages messages) {
        return new DirectMessage(messages.getId(), messages.getUsersBySenderId().getUserId(),
                messages.getUsersByReceiverId().getUserId(),
                MessageType.valueOf(messages.getMessageType()), messages.getContent(),
                messages.getFontStyle(), messages.getTime());
    }

    @Override
    synchronized public DirectMessage getLastDirectMessage(int userId, int anotherUserId) throws Exception {
        Criteria criteria = session.createCriteria(DirectMessages.class);
        criteria.add(Restrictions.eq("usersBySenderId", userId)).
                add(Restrictions.eq("usersByReceiverId", anotherUserId));
        List<DirectMessages> directMessagesList = criteria.list();
        DirectMessages directMessages = directMessagesList.get(directMessagesList.size() - 1);

        if (directMessages == null) {
            return null;
        }
        return mapMsg(directMessages);
    }

    @Override
    synchronized public List<DirectMessage> getAllDirectMessages(int userId, int anotherUserId) throws Exception {
        Criteria criteria = session.createCriteria(DirectMessages.class);
        criteria.add(Restrictions.eq("usersBySenderId", userId)).
                add(Restrictions.eq("usersByReceiverId", anotherUserId));
        List<DirectMessages> directMessagesList = criteria.list();
        List<DirectMessage> directMessages = new ArrayList<>();
        directMessagesList.forEach(m -> directMessages.add(mapMsg(m)));

        if (directMessages.isEmpty()) {
            return null;
        } else {
            return directMessages;
        }
    }

    @Override
    synchronized public int insert(DirectMessage directMessage) throws Exception {
        try {
            DirectMessages directMessages = createDbMsg(directMessage);
            session.beginTransaction();
            session.persist(directMessages);
            session.getTransaction().commit();
            return directMessages.getId();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }

    private DirectMessages createDbMsg(DirectMessage directMessage) {
        FriendshipsId friendshipsId = new FriendshipsId(directMessage.getReceiverId(), directMessage.getSenderId());
        Friendships friendships = (Friendships) session.get(Friendships.class, friendshipsId);
        Users receiver = (Users) session.get(Users.class, directMessage.getReceiverId());
        Users sender = (Users) session.get(Users.class, directMessage.getSenderId());

        DirectMessages directMessages = new DirectMessages(receiver, sender,
                directMessage.getMessageType().toString(), directMessage.getContent(),
                directMessage.getMessageTime());
        directMessages.setFontStyle(directMessage.getStyle());
        directMessages.getFriendshipses().add(friendships);

        return directMessages;

    }

    @Override
    public boolean update(DirectMessage directMessage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(DirectMessage object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    synchronized public DirectMessage findMessageById(int id) throws Exception {
        Criteria criteria = session.createCriteria(DirectMessages.class);
        criteria.add(Restrictions.eq("id", id));
        DirectMessages directMessages = (DirectMessages) criteria.uniqueResult();
          if (directMessages==null) {
            return null;
        } else {
            return mapMsg(directMessages);
        }
    }

}
