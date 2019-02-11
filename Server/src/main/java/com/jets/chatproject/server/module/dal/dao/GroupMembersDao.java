/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao;

import com.jets.chatproject.server.module.dal.entities.GroupMember;
import java.util.List;

/**
 *
 * @author ibrahim
 */
public interface GroupMembersDao extends AbstractDAO<GroupMember> {

    List<GroupMember> findAllByGroup(int groupId);

    GroupMember findByGroupAndUser(int groupId, int userId);
}
