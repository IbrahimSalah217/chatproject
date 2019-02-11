/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao.imp;

import com.jets.chatproject.server.module.dal.dao.GroupMembersDao;
import com.jets.chatproject.server.module.dal.entities.GroupMember;
import java.util.List;

/**
 *
 * @author ibrahim
 */
public class GroupMembersDaoImp implements GroupMembersDao {

    @Override
    public List<GroupMember> findAllByGroup(int groupId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GroupMember findByGroupAndUser(int groupId, int userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean insert(GroupMember object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(GroupMember object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(GroupMember object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
