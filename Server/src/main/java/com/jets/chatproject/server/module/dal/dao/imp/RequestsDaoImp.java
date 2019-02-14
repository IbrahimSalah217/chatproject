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
    public List<Request> findAllByReceiver(int userId) {
        List< Request> list = null;
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

    @Override
    public boolean insert(Request request) {
        try {
            Connection conn = dataSource.getConnection();
            String query = "insert into Requests values(?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, request.getSenderId());
            preparedStatement.setInt(2, request.getReceiverId());
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RequestsDaoImp.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean update(Request request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(Request request) {
        try {
            Connection conn = dataSource.getConnection();
            String query = "delete from Requests where sender_id = ? and receiver_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, request.getSenderId());
            preparedStatement.setInt(2, request.getReceiverId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RequestsDaoImp.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public Request findBySenderReceiver(int senderId, int receiverId) {
        Request request = null;
        try {
            Connection conn = dataSource.getConnection();
            String query = "select * from Requests where receiver_id = ? AND sender_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, receiverId);
            preparedStatement.setInt(2, senderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            request = new Request(resultSet.getInt(1),
                    resultSet.getInt(2), resultSet.getTimestamp(3));
        } catch (SQLException ex) {
            Logger.getLogger(RequestsDaoImp.class.getName())
                    .log(Level.SEVERE, null, ex);

        }
        return request;
    }

}
