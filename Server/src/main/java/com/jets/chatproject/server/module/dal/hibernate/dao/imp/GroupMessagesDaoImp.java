/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.hibernate.dao.imp;

import com.jets.chatproject.server.module.dal.dao.GroupMessagesDao;
import com.jets.chatproject.server.module.dal.entities.GroupMessage;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author ibrahim
 */
public class GroupMessagesDaoImp implements GroupMessagesDao {

    Session session;

    public GroupMessagesDaoImp(Session session) {
        this.session = session;
    }

    @Override
    public GroupMessage getLastMessage(int groupId) throws Exception {
        return null;
    }

    @Override
    public List<GroupMessage> getAllGroupMessages(int groupId) throws Exception {
        return null;
    }

    @Override
    public int insert(GroupMessage groupMessage) throws Exception {
        return 0;
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
