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
                Blob picture = resultSet.getBlob(2);
                byte[] pictureToBytes = picture.getBytes(1, (int) picture.length());

                userPic = new Picture(pictureId, pictureToBytes);
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
            Blob picData = new SerialBlob(picture.getData());
            preparedStatement.setBlob(2, picData);
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
            String query = "update pictures set picture = '" + picture.getData()
                    + "where picture_id = " + picture.getId();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
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

}
