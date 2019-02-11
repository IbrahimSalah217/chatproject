/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao.imp;

import com.jets.chatproject.server.module.dal.dao.DirectMessagesDao;
import com.jets.chatproject.server.module.dal.entities.DirectMessage;
import java.util.ArrayList;

/**
 *
 * @author ibrahim
 */
public class DirectMessagesDaoImp implements DirectMessagesDao {

    @Override
    public DirectMessage getLastDirectMessage(int userId, int anotherUserId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<DirectMessage> getAllDirectMessages(int userId, int anotherUserId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean insert(DirectMessage object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(DirectMessage object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(DirectMessage object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
