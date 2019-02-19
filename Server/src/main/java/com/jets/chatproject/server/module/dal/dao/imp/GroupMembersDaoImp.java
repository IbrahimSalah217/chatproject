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
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author ibrahim
 */
public class GroupMembersDaoImp implements GroupMembersDao {

    DataSource dataSource;

    public GroupMembersDaoImp(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<GroupMember> findAllByGroup(int groupId) throws Exception {
        Connection conn = dataSource.getConnection();
        String query = "select * from group_members where groub_id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, groupId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<GroupMember> myGroupList = new ArrayList<>();
        while (resultSet.next()) {
            myGroupList.add(new GroupMember(resultSet.getInt(1),
                    resultSet.getInt(2), resultSet.getInt(3)));
        }
        return myGroupList;
    }

    @Override
    public GroupMember findByGroupAndUser(int groupId, int userId) throws Exception {
        Connection conn = dataSource.getConnection();
        String query = "select * from group_members where groub_id = ? and user_id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, groupId);
        preparedStatement.setInt(2, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new GroupMember(resultSet.getInt(1),
                    resultSet.getInt(2), resultSet.getInt(3));
        } else {
            return null;
        }
    }

    @Override
    public int insert(GroupMember object) throws Exception {
        Connection connection = dataSource.getConnection();
        String query = "insert into group_members values(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, object.getGroupId());
        preparedStatement.setInt(2, object.getUserId());
        if (object.getLastSeenMessageId() == -1) {
            preparedStatement.setNull(3, Types.INTEGER);
        } else {
            preparedStatement.setInt(3, object.getLastSeenMessageId());
        }
        preparedStatement.executeUpdate();
        return -1;
    }

    @Override
    public boolean update(GroupMember object) throws Exception {
        Connection connection = dataSource.getConnection();
        String query = "update group_members set last_seen_message = ? WHERE group_id = ? and user_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, object.getLastSeenMessageId());
        preparedStatement.setInt(2, object.getGroupId());
        preparedStatement.setInt(3, object.getUserId());
        preparedStatement.executeUpdate();
        return true;
    }

    @Override
    public boolean delete(GroupMember object) throws Exception {
        Connection connection = dataSource.getConnection();
        String query = "DELETE FROM group_members WHERE group_id = ? and user_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, object.getGroupId());
        preparedStatement.setInt(2, object.getUserId());
        preparedStatement.executeUpdate();
        return true;
    }

}
