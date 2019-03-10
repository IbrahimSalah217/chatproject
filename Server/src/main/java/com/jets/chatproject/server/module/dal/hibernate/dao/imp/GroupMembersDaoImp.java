/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.hibernate.dao.imp;

import com.jets.chatproject.server.module.dal.dao.GroupMembersDao;
import com.jets.chatproject.server.module.dal.entities.GroupMember;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author ibrahim
 */
public class GroupMembersDaoImp implements GroupMembersDao {

    Session session;

    public GroupMembersDaoImp(Session session) {
        this.session = session;
    }
    @Override
    public List<GroupMember> findAllByGroup(int groupId) throws Exception {
        return null;
    }

    @Override
    public GroupMember findByGroupAndUser(int groupId, int userId) throws Exception {
        return null;
    }

    @Override
    public int insert(GroupMember object) throws Exception {
        return 0;
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
