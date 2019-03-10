/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.hibernate.dao.imp;

import com.jets.chatproject.server.module.dal.dao.DirectMessagesDao;
import com.jets.chatproject.server.module.dal.entities.DirectMessage;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author ibrahim
 */
public class DirectMessagesDaoImp implements DirectMessagesDao {

    Session session;

    public DirectMessagesDaoImp(Session session) {
        this.session = session;
    }

    @Override
    public DirectMessage getLastDirectMessage(int userId, int anotherUserId) throws Exception {
        return null;
    }

    @Override
    public List<DirectMessage> getAllDirectMessages(int userId, int anotherUserId) throws Exception {
        return null;
    }

    @Override
    public int insert(DirectMessage directMessage) throws Exception {
        return 0;
    }

    @Override
    public boolean update(DirectMessage directMessage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(DirectMessage object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DirectMessage findMessageById(int id) throws Exception {
        return null;
    }

}
