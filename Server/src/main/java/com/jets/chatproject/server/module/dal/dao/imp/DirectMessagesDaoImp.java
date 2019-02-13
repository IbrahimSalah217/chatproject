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
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public DirectMessage getLastDirectMessage(int userId, int anotherUserId) {
        DirectMessage directMessage = null;

        try {
            Connection connection = dataSource.getConnection();
            String query = "select * from direct_messages";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.last()) {
                int id = resultSet.getInt(1);
                int senderId = resultSet.getInt(2);
                int receiverId = resultSet.getInt(3);
                MessageType messageType = (MessageType) resultSet.getObject(4);
                String content = resultSet.getString(5);
                String fontStyle = resultSet.getString(6);
                Time time = resultSet.getTime(7);
                if (((senderId == userId) && (receiverId == anotherUserId)) || ((senderId == anotherUserId) && (receiverId == userId))) {
                    directMessage = new DirectMessage(id, senderId, receiverId, messageType, content, fontStyle, time);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return directMessage;
    }

    @Override
    public ArrayList<DirectMessage> getAllDirectMessages(int userId, int anotherUserId) {
        ArrayList<DirectMessage> directMessageList = new ArrayList<DirectMessage>();
        DirectMessage directMessage = null;

        try {
            Connection connection = dataSource.getConnection();
            String query = "select * from direct_messages";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            do {
                resultSet.next();
                int id = resultSet.getInt(1);
                int senderId = resultSet.getInt(2);
                int receiverId = resultSet.getInt(3);
                MessageType messageType = (MessageType) resultSet.getObject(4);
                String content = resultSet.getString(5);
                String fontStyle = resultSet.getString(6);
                Time time = resultSet.getTime(7);
                if (((senderId == userId) && (receiverId == anotherUserId)) || ((senderId == anotherUserId) && (receiverId == userId))) {
                    directMessage = new DirectMessage(id, senderId, receiverId, messageType, content, fontStyle, time);
                    directMessageList.add(directMessage);
                }
            } while (!resultSet.isLast());

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return directMessageList;
    }

    @Override
    public boolean insert(DirectMessage directMessage) {
        boolean isInserted = false;
        try {
            Connection connection = dataSource.getConnection();
            String query = "insert into direct_messages (sender_id,receiver_id,message_type,content,font_style,time) values(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            //preparedStatement.setInt(1, directMessage.getMessageId());
            preparedStatement.setInt(2, directMessage.getSenderId());
            preparedStatement.setInt(3, directMessage.getReceiverId());
            preparedStatement.setString(4, directMessage.getMessageType().toString());
            preparedStatement.setString(5, directMessage.getContent());
            preparedStatement.setString(6, directMessage.getStyle());
            preparedStatement.setTime(7, directMessage.getMessageTime());
            preparedStatement.executeUpdate();
            isInserted = true;
        } catch (SQLException ex) {
            isInserted = false;
        }
        return isInserted;
    }

    @Override
    public boolean update(DirectMessage directMessage) {
        throw new UnsupportedOperationException("Not supported yet.");
       /* boolean isUpdated = false;
        try {
            Connection connection = dataSource.getConnection();
            String query = "update direct_messages set message_type = '" + directMessage.getMessageType()
                    + "',content = '" + directMessage.getContent()
                    + "',font_style = '" + directMessage.getStyle()
                    + "',time = '" + directMessage.getMessageTime()
                    + "',sender_id = '" + directMessage.getSenderId()
                    + "',receiver_id = '" + directMessage.getReceiverId() + "';";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            isUpdated = true;
        } catch (SQLException ex) {
            isUpdated = false;
        }
        return isUpdated;*/
    }

    @Override
    public boolean delete(DirectMessage object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
