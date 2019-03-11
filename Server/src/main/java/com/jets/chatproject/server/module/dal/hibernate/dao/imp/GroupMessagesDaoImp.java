/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.hibernate.dao.imp;

import com.jets.chatproject.module.rmi.dto.MessageType;
import com.jets.chatproject.server.module.dal.dao.GroupMessagesDao;
import com.jets.chatproject.server.module.dal.entities.GroupMessage;
import com.jets.chatproject.server.module.dal.hibernate.entity.GroupMessages;
import com.jets.chatproject.server.module.dal.hibernate.entity.Groups;
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
public class GroupMessagesDaoImp implements GroupMessagesDao {

    Session session;

    public GroupMessagesDaoImp(Session session) {
        this.session = session;
    }

    private GroupMessage mapMsg(GroupMessages messages) {
        return new GroupMessage(messages.getId(), messages.getUsers().getUserId(),
                messages.getGroups().getGroupId(),
                MessageType.valueOf(messages.getMessageType()), messages.getContent(),
                messages.getFontStyle(), messages.getTime());
    }

    @Override
    synchronized public GroupMessage getLastMessage(int groupId) throws Exception {
        Groups groups = (Groups) session.get(Groups.class, groupId);
        Criteria criteria = session.createCriteria(GroupMessages.class)
        .add(Restrictions.eq("groups", groups));
         List<GroupMessages> groupMessages = criteria.list();
         GroupMessages groupMessagesElement = groupMessages.get(groupMessages.size()-1);
         
         if(groupMessagesElement == null)
             return null;
         return mapMsg(groupMessagesElement);
    }

    @Override
    synchronized public List<GroupMessage> getAllGroupMessages(int groupId) throws Exception {
        Groups groups = (Groups) session.get(Groups.class, groupId);
        Criteria criteria = session.createCriteria(GroupMessages.class)
        .add(Restrictions.eq("groups",groups));

        List<GroupMessages> groupMessages = criteria.list();
        List<GroupMessage> groupMessage = new ArrayList<>();
        groupMessages.forEach(m -> groupMessage.add(mapMsg(m)));

        if (groupMessages.isEmpty()) {
            return null;
        } else {
            return groupMessage;
        }
    }

    @Override
    synchronized public int insert(GroupMessage groupMessage) throws Exception {
        try {
            GroupMessages groupMessages = createDbGroupMsg(groupMessage);
            session.beginTransaction();
            session.persist(groupMessages);
            session.getTransaction().commit();
            return groupMessages.getId();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }
    
    private GroupMessages createDbGroupMsg(GroupMessage groupMessage) {
        Groups groups = (Groups) session.get(Groups.class, groupMessage.getGroupId());
        Users users = (Users) session.get(Users.class, groupMessage.getSenderId());

                
        return new GroupMessages(groups, users, groupMessage.getMessageType().toString(),
                groupMessage.getContent(), groupMessage.getStyle(),
                groupMessage.getMessageTime(),groups.getGroupMemberses());
        
    }

    @Override
    public boolean update(GroupMessage object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(GroupMessage object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
