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
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
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
    public List<Request> findAllByReceiver(int userId) throws Exception {
        Connection conn = dataSource.getConnection();
        String query = "select * from requests where receiver_id = ? order by time";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Request> requests = new ArrayList<>();
        while (resultSet.next()) {
            requests.add(new Request(resultSet.getInt(1),
                    resultSet.getInt(2), resultSet.getTimestamp(3)));
        }
        return requests;
    }

    @Override
    public int insert(Request request) throws Exception {
        Connection conn = dataSource.getConnection();
        String query = "insert into requests values(?,?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, request.getSenderId());
        preparedStatement.setInt(2, request.getReceiverId());
        preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
        preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        generatedKeys.next();
        return generatedKeys.getInt(1);
    }

    @Override
    public boolean update(Request request) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(Request request) throws Exception {
        Connection conn = dataSource.getConnection();
        String query = "delete from requests where sender_id = ? and receiver_id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, request.getSenderId());
        preparedStatement.setInt(2, request.getReceiverId());
        preparedStatement.executeUpdate();
        return true;
    }

    @Override
    public Request findBySenderReceiver(int senderId, int receiverId) throws Exception {
        Connection conn = dataSource.getConnection();
        String query = "select * from Requests where sender_id = ? and receiver_id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, senderId);
        preparedStatement.setInt(2, receiverId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Request(resultSet.getInt(1),
                    resultSet.getInt(2), resultSet.getTimestamp(3));
        } else {
            return null;
        }
    }

}
