/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao.imp;

import com.jets.chatproject.module.rmi.dto.MessageType;
import com.jets.chatproject.server.module.dal.dao.GroupMessagesDao;
import com.jets.chatproject.server.module.dal.entities.GroupMessage;
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
public class GroupMessagesDaoImp implements GroupMessagesDao {

    DataSource dataSource;

    public GroupMessagesDaoImp(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public GroupMessage getLastMessage(int groupId) throws Exception {
        Connection connection = dataSource.getConnection();
        String query = "select * from group_messages where groupd_id = ? sort by time desc limit 1";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, groupId);
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            int senderId = resultSet.getInt(2);
            int group_Id = resultSet.getInt(3);
            MessageType messageType = MessageType.valueOf(resultSet.getString(4));
            String Content = resultSet.getString(5);
            String fontStyle = resultSet.getString(6);
            Date time = resultSet.getTimestamp(7);
            return new GroupMessage(id, senderId, group_Id, messageType, Content, fontStyle, time);
        } else {
            return null;
        }
    }

    @Override
    public List<GroupMessage> getAllGroupMessages(int groupId) throws Exception {
        Connection connection = dataSource.getConnection();
        String query = "select * from group_messages where groupd_id = ? sort by time";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, groupId);
        ResultSet resultSet = statement.executeQuery(query);
        List<GroupMessage> messages = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            int senderId = resultSet.getInt(2);
            int group_Id = resultSet.getInt(3);
            MessageType messageType = MessageType.valueOf(resultSet.getString(4));
            String content = resultSet.getString(5);
            String fontStyle = resultSet.getString(6);
            Date time = resultSet.getTimestamp(7);
            messages.add(new GroupMessage(id, senderId, group_Id, messageType, content, fontStyle, time));
        }
        return messages;
    }

    @Override
    public int insert(GroupMessage groupMessage) throws Exception {
        Connection connection = dataSource.getConnection();
        String query = "insert into group_members (sender_id, group_id, message_type, content, font_style, time) values(?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, groupMessage.getSenderId());
        preparedStatement.setInt(2, groupMessage.getGroupId());
        preparedStatement.setString(3, groupMessage.getMessageType().toString());
        preparedStatement.setString(4, groupMessage.getContent());
        preparedStatement.setString(5, groupMessage.getStyle());
        preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
        preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        generatedKeys.next();
        return generatedKeys.getInt(1);
    }

    @Override
    public boolean update(GroupMessage object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(GroupMessage object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
