/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao.imp;

import com.jets.chatproject.server.module.dal.dao.GroupsDao;
import com.jets.chatproject.server.module.dal.entities.Group;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author ibrahim
 */
public class GroupsDaoImp implements GroupsDao {

    DataSource dataSource;

    @Override
    public ArrayList<Group> findAllForUser(int userId) {
        ArrayList<Group> myGroupList = null;
        try {
            Connection conn = dataSource.getConnection();
            String query = "select * from groups g JOIN group_messages gm ON g.groub_id = gm.groub_id where gm.user_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            myGroupList = new ArrayList<>();
            while (resultSet.next()) {
                Group group = new Group(resultSet.getInt(1),
                        resultSet.getInt(2), resultSet.getString(3),
                         resultSet.getInt(4));
                myGroupList.add(group);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RequestsDaoImp.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return myGroupList;
    }

    @Override
    public boolean insert(Group object) {
        try {
            Connection connection = dataSource.getConnection();
            String query = "insert into groups values(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, object.getGroupId());
            preparedStatement.setInt(2, object.getAdminId());
            preparedStatement.setString(3, object.getGroupName());
            preparedStatement.setInt(4, object.getPictureId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RequestsDaoImp.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean update(Group object) {
        try {
            Connection connection = dataSource.getConnection();
            String query = "update groups set admin_id = ? display_name = ? picture_id = ?  WHERE group_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, object.getAdminId());
            preparedStatement.setString(2, object.getGroupName());
            preparedStatement.setInt(3, object.getPictureId());
            preparedStatement.setInt(4, object.getGroupId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RequestsDaoImp.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }

    }

    @Override
    public boolean delete(Group object) {
        try {
            Connection connection = dataSource.getConnection();
            String query = "DELETE FROM groups WHERE group_id = ? ";
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
