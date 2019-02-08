/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao;

import java.util.ArrayList;
import com.jets.chatproject.server.module.dal.entities.Group;

/**
 *
 * @author salma
 */
public interface GroupsDao {
    
    void insertGroup(Group group);

    void deleteGroup(Group group);
    
    void updateGroup(Group group);
    
    Group findGroupByName(String groupName);
    
    ArrayList<Group> getAllGroups();
    
}