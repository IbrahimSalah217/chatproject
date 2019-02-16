/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao.imp;

import com.jets.chatproject.server.module.dal.dao.PicturesDao;
import com.jets.chatproject.server.module.dal.entities.Picture;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialBlob;

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
    public Picture findById(int id) {
        Picture userPic = null;
        try {
            Connection connection = dataSource.getConnection();
            String query = "select * from pictures where picture_id=" + id + "";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                int pictureId = resultSet.getInt(1);
                byte[] pictureData = resultSet.getBytes(2);
                userPic = new Picture(pictureId, pictureData);
            }
        } catch (SQLException ex) {
            userPic = null;
        }
        return userPic;
    }

    @Override
    public boolean insert(Picture picture) {
        boolean isInserted = false;
        try {
            Connection connection = dataSource.getConnection();
            String query = "insert into pictures values(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, picture.getId());
            preparedStatement.setBytes(2, picture.getData());
            preparedStatement.execute();
            isInserted = true;
        } catch (SQLException ex) {
            isInserted = false;
        }
        return isInserted;
    }

    @Override
    public boolean update(Picture picture) {
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
    public boolean delete(Picture object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int createPicture(byte[] picture) {
        int pictureId;
        try {
            Connection connection = dataSource.getConnection();
            String query = "insert into pictures(picture) values(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBytes(1, picture);
            preparedStatement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            pictureId = generatedKeys.getInt(1);
        } catch (SQLException ex) {
            pictureId = -1;
        }
        return pictureId;
    }

}
