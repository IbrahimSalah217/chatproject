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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author ibrahim
 */
public class GroupsDaoImp implements GroupsDao {

    DataSource dataSource;

    public GroupsDaoImp(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Group> findAllForUser(int userId) throws Exception {
        Connection conn = dataSource.getConnection();
        String query = "select * from groups g JOIN group_members gm ON g.groub_id = gm.groub_id where gm.user_id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Group> groups = new ArrayList<>();
        while (resultSet.next()) {
            groups.add(new Group(resultSet.getInt(1),
                    resultSet.getInt(2), resultSet.getString(3),
                    resultSet.getInt(4)));
        }
        return groups;
    }

    @Override
    public int insert(Group object) throws Exception {
        Connection connection = dataSource.getConnection();
        String query = "insert into groups (admin_id,disply_name,picture_id) values(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, object.getAdminId());
        preparedStatement.setString(2, object.getGroupName());
        preparedStatement.setInt(3, object.getPictureId());
        preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        generatedKeys.next();
        return generatedKeys.getInt(1);
    }

    @Override
    public boolean update(Group object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(Group object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
