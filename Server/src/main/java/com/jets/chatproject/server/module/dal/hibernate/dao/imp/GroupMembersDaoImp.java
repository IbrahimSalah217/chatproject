/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.hibernate.dao.imp;

import com.jets.chatproject.module.rmi.dto.MessageType;
import com.jets.chatproject.server.module.dal.dao.GroupMembersDao;
import com.jets.chatproject.server.module.dal.entities.GroupMember;
import com.jets.chatproject.server.module.dal.entities.GroupMessage;
import com.jets.chatproject.server.module.dal.hibernate.entity.GroupMembers;
import com.jets.chatproject.server.module.dal.hibernate.entity.GroupMembersId;
import com.jets.chatproject.server.module.dal.hibernate.entity.GroupMessages;
import com.jets.chatproject.server.module.dal.hibernate.entity.Groups;
import com.jets.chatproject.server.module.dal.hibernate.entity.Users;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ibrahim
 */
public class GroupMembersDaoImp implements GroupMembersDao {

    Session session;

    public GroupMembersDaoImp(Session session) {
        this.session = session;
    }

    private GroupMember mapMember(GroupMembers members) {
        try {
            int groupId = members.getGroupMessages().getGroups().getGroupId();
            GroupMessagesDaoImp groupMessagesDaoImp = new GroupMessagesDaoImp(session);
            GroupMessage groupMessage = groupMessagesDaoImp.getLastMessage(groupId);

            return new GroupMember(members.getGroups().getGroupId(), members.getUsers().getUserId(),
                    groupMessage.getMessageId());
        } catch (Exception ex) {
            Logger.getLogger(GroupMembersDaoImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    synchronized public List<GroupMember> findAllByGroup(int groupId) throws Exception {
        Groups groups = (Groups) session.get(Groups.class, groupId);
        Criteria criteria = session.createCriteria(GroupMembers.class)
                .add(Restrictions.eq("groups", groups));

        List<GroupMembers> groupMemberses = criteria.list();
        List<GroupMember> groupMembers = new ArrayList<>();
        groupMemberses.forEach(m -> groupMembers.add(mapMember(m)));

        if (groupMembers.isEmpty()) {
            return null;
        } else {
            return groupMembers;
        }
    }

    @Override
    synchronized public GroupMember findByGroupAndUser(int groupId, int userId) throws Exception {
        Groups groups = (Groups) session.get(Groups.class, groupId);
        Users users = (Users) session.get(Users.class, userId);

        Criteria criteria = session.createCriteria(GroupMembers.class)
                .add(Restrictions.eq("groups", groups)).add(Restrictions.eq("users", users));

        GroupMembers groupMembers = (GroupMembers) criteria.uniqueResult();
        if (groupMembers == null) {
            return null;
        } else {
            return mapMember(groupMembers);
        }
    }

    @Override
    synchronized public int insert(GroupMember member) throws Exception {
         try {
            GroupMembers groupMembers = createDbGroupmMember(member);
            session.beginTransaction();
            session.persist(groupMembers);
            session.getTransaction().commit();
            return 1;
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }

    private GroupMembers createDbGroupmMember(GroupMember groupMember) {
        Groups groups = (Groups) session.get(Groups.class, groupMember.getGroupId());
        Users users = (Users) session.get(Users.class, groupMember.getUserId());
        GroupMessages groupMessages = new GroupMessages(groups, users, MessageType.PLAIN_TEXT.toString(), "new user", new Date());
        GroupMembersId groupMembersId = new GroupMembersId(groups.getGroupId(), users.getUserId());
        return new GroupMembers(groupMembersId, groupMessages, groups, users);

    }

    @Override
    public boolean update(GroupMember object) throws Exception {
        return true;
    }

    @Override
    public boolean delete(GroupMember object) throws Exception {
        return true;
    }

}
