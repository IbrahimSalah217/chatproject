/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao.imp;

import com.jets.chatproject.server.module.dal.dao.RequestsDoa;
import com.jets.chatproject.server.module.dal.entities.Request;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public void insert(Request request) {
        try {
            Connection conn = dataSource.getConnection();
            String query = "insert into Requests values(?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, request.getSenderId());
            preparedStatement.setInt(2, request.getReceiverId());
            preparedStatement.setTimestamp(3, new Timestamp(new Date().getTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RequestsDaoImp.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Request request) {
        try {
            Connection conn = dataSource.getConnection();
            String query = "delete from Requests where sender_id = ? and receiver_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, request.getSenderId());
            preparedStatement.setInt(2, request.getReceiverId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RequestsDaoImp.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Request> getAllByReceiver(int userId) {
        List<Request> list = null;
        try {
            Connection conn = dataSource.getConnection();
            String query = "select * from Requests where receiver_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            list = new ArrayList<>();
            while (resultSet.next()) {
                Request request = new Request(resultSet.getInt(1),
                        userId, resultSet.getTimestamp(3));
                list.add(request);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RequestsDaoImp.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
