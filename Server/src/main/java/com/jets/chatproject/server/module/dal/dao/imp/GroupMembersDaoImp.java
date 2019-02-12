/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao.imp;

import com.jets.chatproject.server.module.dal.dao.GroupMembersDao;
import com.jets.chatproject.server.module.dal.entities.GroupMember;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author ibrahim
 */
public class GroupMembersDaoImp implements GroupMembersDao {

    DataSource dataSource;

    public GroupMembersDaoImp(DataSource dataSource){
        this.dataSource = dataSource;
    }
    @Override
    public List<GroupMember> findAllByGroup(int groupId) {
        List<GroupMember> myGroupList = null;
        try {
            Connection conn = dataSource.getConnection();
            String query = "select * from group_members where groub_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, groupId);
            ResultSet resultSet = preparedStatement.executeQuery();
            myGroupList = new ArrayList<>();
            while (resultSet.next()) {
                GroupMember groupMember = new GroupMember(resultSet.getInt(1),
                        resultSet.getInt(2), resultSet.getInt(3));
                myGroupList.add(groupMember);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RequestsDaoImp.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return myGroupList;
    }

    @Override
    public GroupMember findByGroupAndUser(int groupId, int userId) {
        GroupMember groupmember = null;
        try {
            Connection conn = dataSource.getConnection();
            String query = "select * from group_members where groub_id = ? && user_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, groupId);
            preparedStatement.setInt(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                groupmember = new GroupMember(resultSet.getInt(1),
                        resultSet.getInt(2), resultSet.getInt(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RequestsDaoImp.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return groupmember;
    }

    @Override
    public boolean insert(GroupMember object) {
        try {
            Connection connection = dataSource.getConnection();
            String query = "insert into group_members values(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, object.getGroupId());
            preparedStatement.setInt(2, object.getUserId());
            preparedStatement.setInt(3, object.getLastSeenMessageId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RequestsDaoImp.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean update(GroupMember object) {
        try {
            Connection connection = dataSource.getConnection();
            String query = "update group_members set user_id = ? last_seen_message = ? WHERE group_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, object.getUserId());
            preparedStatement.setInt(2, object.getLastSeenMessageId());
            preparedStatement.setInt(3, object.getGroupId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RequestsDaoImp.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean delete(GroupMember object) {
        try {
            Connection connection = dataSource.getConnection();
            String query = "DELETE FROM group_members WHERE group_id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, object.getGroupId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RequestsDaoImp.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
