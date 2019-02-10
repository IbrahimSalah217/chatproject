/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao;

import java.util.ArrayList;
import com.jets.chatproject.server.module.dal.entities.Group;
import com.jets.chatproject.server.module.dal.entities.User;

/**
 *
 * @author salma
 */
public interface GroupsDao {

    void insert(Group group);

    void update(Group group);

    void delete(Group group);

    void addUserToGroup(User user, int groupId);

    ArrayList<Group> getAllForUser(int userId);

}
