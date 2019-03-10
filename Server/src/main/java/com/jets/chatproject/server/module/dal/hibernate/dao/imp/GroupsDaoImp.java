/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.hibernate.dao.imp;

import com.jets.chatproject.server.module.dal.dao.GroupsDao;
import com.jets.chatproject.server.module.dal.entities.Group;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author ibrahim
 */
public class GroupsDaoImp implements GroupsDao {

    Session session;

    public GroupsDaoImp(Session session) {
        this.session = session;
    }

    @Override
    public List<Group> findAllForUser(int userId) throws Exception {
        return null;
    }

    @Override
    public int insert(Group object) throws Exception {
        return 0;

    }

    @Override
    public boolean update(Group object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(Group object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
