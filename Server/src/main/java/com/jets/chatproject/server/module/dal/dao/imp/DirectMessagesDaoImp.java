/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao.imp;

import com.jets.chatproject.module.rmi.dto.MessageType;
import com.jets.chatproject.server.module.dal.dao.DirectMessagesDao;
import com.jets.chatproject.server.module.dal.entities.DirectMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author ibrahim
 */
public class DirectMessagesDaoImp implements DirectMessagesDao {

    DataSource dataSource;

    public DirectMessagesDaoImp(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public DirectMessage getLastDirectMessage(int userId, int anotherUserId) throws Exception {
        DirectMessage directMessage = null;
        Connection connection = dataSource.getConnection();
        String query = "select * from direct_messages where (sender_id = ? and receiver_id = ?) or (receiver_id = ? and sender_id = ?) order by time desc limit 1";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        statement.setInt(2, anotherUserId);
        statement.setInt(3, userId);
        statement.setInt(4, anotherUserId);
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            int senderId = resultSet.getInt(2);
            int receiverId = resultSet.getInt(3);
            MessageType messageType = MessageType.valueOf(resultSet.getString(4));
            String content = resultSet.getString(5);
            String fontStyle = resultSet.getString(6);
            Date time = resultSet.getTimestamp(7);
            directMessage = new DirectMessage(id, senderId, receiverId,
                    messageType, content, fontStyle, time);
        }
        return directMessage;
    }

    @Override
    public List<DirectMessage> getAllDirectMessages(int userId, int anotherUserId) throws Exception {
        ArrayList<DirectMessage> list = new ArrayList<DirectMessage>();
        Connection connection = dataSource.getConnection();
        String query = "select * from direct_messages where (sender_id = ? and receiver_id = ?) or (receiver_id = ? and sender_id = ?) order by time";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        statement.setInt(2, anotherUserId);
        statement.setInt(3, userId);
        statement.setInt(4, anotherUserId);
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            int senderId = resultSet.getInt(2);
            int receiverId = resultSet.getInt(3);
            MessageType messageType = MessageType.valueOf(resultSet.getString(4));
            String content = resultSet.getString(5);
            String fontStyle = resultSet.getString(6);
            Date time = resultSet.getTimestamp(7);
            list.add(new DirectMessage(id, senderId, receiverId,
                    messageType, content, fontStyle, time));
        }
        return list;
    }

    @Override
    public int insert(DirectMessage directMessage) throws Exception {
        Connection connection = dataSource.getConnection();
        String query = "insert into direct_messages (sender_id,receiver_id,message_type,content,font_style,time) values(?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, directMessage.getSenderId());
        preparedStatement.setInt(2, directMessage.getReceiverId());
        preparedStatement.setString(3, directMessage.getMessageType().toString());
        preparedStatement.setString(4, directMessage.getContent());
        preparedStatement.setString(5, directMessage.getStyle());
        preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
        preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        generatedKeys.next();
        return generatedKeys.getInt(1);
    }

    @Override
    public boolean update(DirectMessage directMessage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(DirectMessage object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
