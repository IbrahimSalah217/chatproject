/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.hibernate.dao.imp;

import com.jets.chatproject.server.module.dal.dao.RequestsDoa;
import com.jets.chatproject.server.module.dal.entities.Request;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author ibrahim
 */
public class RequestsDaoImp implements RequestsDoa {

    Session session;

    public RequestsDaoImp(Session session) {
        this.session = session;
    }

    @Override
    public List<Request> findAllByReceiver(int userId) throws Exception {
        return null;
    }

    @Override
    public int insert(Request request) throws Exception {
        return 0;
    }

    @Override
    public boolean update(Request request) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(Request request) throws Exception {
        return true;
    }

    @Override
    public Request findBySenderReceiver(int senderId, int receiverId) throws Exception {
        return null;
    }

}
