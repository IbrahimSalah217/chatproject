/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao.imp;

import com.jets.chatproject.server.module.dal.dao.GroupMessagesDao;
import com.jets.chatproject.server.module.dal.entities.GroupMessage;
import java.util.ArrayList;

/**
 *
 * @author ibrahim
 */
public class GroupMessagesDaoImp implements GroupMessagesDao {

    @Override
    public GroupMessage getLastMessage(int groupId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<GroupMessage> getAllGroupMessages(int groupId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean insert(GroupMessage object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(GroupMessage object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(GroupMessage object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
