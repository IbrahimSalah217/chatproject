/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao.imp;

import com.jets.chatproject.server.module.dal.dao.RequestsDoa;
import com.jets.chatproject.server.module.dal.entities.Request;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author ibrahim
 */
public class RequestsDaoImp implements RequestsDoa {

    DataSource dataSource;

    public RequestsDaoImp(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Request> findAllByReceiver(int userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean insert(Request object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(Request object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(Request object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
