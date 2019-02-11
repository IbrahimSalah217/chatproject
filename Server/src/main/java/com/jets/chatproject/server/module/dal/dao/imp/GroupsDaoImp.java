/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao.imp;

import com.jets.chatproject.server.module.dal.dao.GroupsDao;
import com.jets.chatproject.server.module.dal.entities.Group;
import java.util.ArrayList;

/**
 *
 * @author ibrahim
 */
public class GroupsDaoImp implements GroupsDao {

    @Override
    public ArrayList<Group> findAllForUser(int userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean insert(Group object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(Group object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(Group object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
