/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao;

import com.jets.chatproject.server.module.dal.entities.Group;
import java.util.List;

/**
 *
 * @author salma
 */
public interface GroupsDao extends AbstractDAO<Group> {

    List<Group> findAllForUser(int userId) throws Exception;

}
