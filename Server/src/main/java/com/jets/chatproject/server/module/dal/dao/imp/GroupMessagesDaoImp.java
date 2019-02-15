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
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author ibrahim
 */
public class GroupMessagesDaoImp implements GroupMessagesDao {
    ArrayList<GroupMessage> groupMessages;
    DataSource dataSource;

    public GroupMessagesDaoImp(DataSource dataSource) {
        this.dataSource = dataSource;
        groupMessages = new ArrayList<>();
    }
    

    @Override
    public GroupMessage getLastMessage(int groupId) {
        GroupMessage groupMessage = null;
        try {
            Connection connection = dataSource.getConnection();
            String query = "selec * from group_messages where time = ("
                    + "select max(time)"
                    + "from group_messages)";
            
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                int id = resultSet.getInt(1);
                int senderId = resultSet.getInt(2);
                int group_Id = resultSet.getInt(3);
                MessageType messageType = MessageType.valueOf(resultSet.getString(4));
                String Content = resultSet.getString(5);
                String fontStyle = resultSet.getString(6);
                Timestamp time = resultSet.getTimestamp(7);
                
                groupMessage = new GroupMessage(id, senderId, group_Id, messageType, Content, fontStyle, time);
            }
            }catch (SQLException ex) {
                groupMessage = null;
        }
        return groupMessage;
    }

    @Override
    public ArrayList<GroupMessage> getAllGroupMessages(int groupId) {
        try {
            Connection connection = dataSource.getConnection();
            String query = "select * from group_messages where group_id = "+groupId;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                int id = resultSet.getInt(1);
                int senderId = resultSet.getInt(2);
                int group_Id = resultSet.getInt(3);
                MessageType messageType = MessageType.valueOf(resultSet.getString(4));
                String Content = resultSet.getString(5);
                String fontStyle = resultSet.getString(6);
                Timestamp time = resultSet.getTimestamp(7);
                
                GroupMessage groupMessage = new GroupMessage(id, senderId, group_Id, messageType, Content, fontStyle, time);
                groupMessages.add(groupMessage);
            }          
        } catch (SQLException ex) {
            Logger.getLogger(GroupMessagesDaoImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return groupMessages;
    }

    @Override
    public boolean insert(GroupMessage groupMessage) {
        boolean isInserted = false;
        try {
            Connection connection = dataSource.getConnection();
            String query = "insert into users values(?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, groupMessage.getMessageId());
            preparedStatement.setInt(2, groupMessage.getSenderId());
            preparedStatement.setInt(3, groupMessage.getGroupId());
            preparedStatement.setString(4, groupMessage.getMessageType().toString());
            preparedStatement.setString(5, groupMessage.getContent());
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            preparedStatement.execute();
            isInserted=true;
        } catch (SQLException ex) {
            isInserted = false;
        }
        
        return isInserted;
        
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
