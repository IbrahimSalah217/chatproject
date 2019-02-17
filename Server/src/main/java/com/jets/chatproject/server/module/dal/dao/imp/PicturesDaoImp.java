/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao.imp;

import com.jets.chatproject.server.module.dal.dao.PicturesDao;
import com.jets.chatproject.server.module.dal.entities.Picture;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

/**
 *
 * @author ibrahim
 */
public class PicturesDaoImp implements PicturesDao {

    DataSource dataSource;

    public PicturesDaoImp(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Picture findById(int id) throws Exception {
        Connection connection = dataSource.getConnection();
        String query = "select * from pictures where picture_id = "+id;
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            int pictureId = resultSet.getInt(1);
            byte[] pictureData = resultSet.getBytes(2);
            return new Picture(pictureId, pictureData);
        } else {
            return null;
        }
    }

    @Override
    public int insert(Picture picture) throws Exception {
        Connection connection = dataSource.getConnection();
        String query = "insert into pictures (picture) values (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setBytes(1, picture.getData());
        preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        generatedKeys.next();
        return generatedKeys.getInt(1);
    }

    @Override
    public boolean update(Picture picture) throws Exception {
        boolean isUpdated = false;
        try {
            Connection connection = dataSource.getConnection();
            String query = "update pictures set picture = ? where picture_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBytes(1, picture.getData());
            preparedStatement.setInt(2, picture.getId());
            preparedStatement.execute();
            isUpdated = true;
        } catch (SQLException ex) {
            isUpdated = false;
        }
        return isUpdated;
    }

    @Override
    public boolean delete(Picture object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
