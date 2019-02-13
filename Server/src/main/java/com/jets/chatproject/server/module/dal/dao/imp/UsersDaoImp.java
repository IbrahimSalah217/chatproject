/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao.imp;

import com.jets.chatproject.module.rmi.dto.Gender;
import com.jets.chatproject.module.rmi.dto.UserStatus;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
import com.jets.chatproject.server.module.dal.entities.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author Hadeer
 */
public class UsersDaoImp implements UsersDao {
    DataSource dataSource;

    public UsersDaoImp(DataSource dataSource) {
        this.dataSource = dataSource;
        
    }
    
    

    @Override
    public User findByPhone(String phone) {
        User user = null ;

        try {
            Connection connection = dataSource.getConnection();
            String query = "select * from users where phone_number='"+phone+"'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                int id = resultSet.getInt(1);
                String phoneNumber = resultSet.getString(2);
                String name = resultSet.getString(3);
                String email = resultSet.getString(4);
                String password = resultSet.getString(5);
                UserStatus status = UserStatus.valueOf(resultSet.getString(6));
                String gender = resultSet.getString(7);
                String country = resultSet.getString(8);
                Date dateOfBirth = resultSet.getDate(9);
                String bio = resultSet.getString(10);
                int pictureId = resultSet.getInt(11);
                
                user = new User(id, phoneNumber, name, email, password, Gender.MALE, country, dateOfBirth, bio, status, pictureId);
                
            
            }
                
            
            
        } catch (SQLException ex) {
            user = null;
        }
        return user;
    }

    @Override
    public User findById(int id) {
        User user = null ;

        try {
            Connection connection = dataSource.getConnection();
            String query = "select * from users where user_id="+id+"";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                int userId = resultSet.getInt(1);
                String phoneNumber = resultSet.getString(2);
                String name = resultSet.getString(3);
                String email = resultSet.getString(4);
                String password = resultSet.getString(5);
                UserStatus status = UserStatus.valueOf(resultSet.getString(6));
                String gender = resultSet.getString(7);
                String country = resultSet.getString(8);
                Date dateOfBirth = resultSet.getDate(9);
                String bio = resultSet.getString(10);
                int pictureId = resultSet.getInt(11);
                
                user = new User(id, phoneNumber, name, email, password, Gender.MALE, country, dateOfBirth, bio, status, pictureId);
            }
        } catch (SQLException ex) {
            user = null;
        }
        return user;
    }

        
    

    @Override
    public boolean insert(User user) {
        boolean isInserted = false;
        try {
            Connection connection = dataSource.getConnection();
            String query = "insert into users values(?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getPhoneNumber());
            preparedStatement.setString(3, user.getDisplyName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getState().toString());
            preparedStatement.setString(7, user.getGender().toString());
            preparedStatement.setString(8, user.getCountry());
            preparedStatement.setDate(9, user.getDateOfBirth());
            preparedStatement.setString(10, user.getBio());
            preparedStatement.setInt(11, user.getPictureId());
            preparedStatement.execute();
            isInserted=true;


        } catch (SQLException ex) {
            isInserted=false;
        }
        return isInserted;
    }

    @Override
    public boolean update(User user) {
        boolean isUpdated = false;
        try {
            Connection connection = dataSource.getConnection();
            String query = "update users set phone_number = '"+user.getPhoneNumber()+
                    "',display_name = '"+user.getDisplyName()+
                    "',email = '"+user.getEmail()+
                    "',password = '"+user.getPassword()+
                    "',state = '"+user.getState()+
                    "',gender = '"+user.getGender().toString()+
                    "',country = '"+user.getCountry()+
                    "',date_of_birth = '"+user.getDateOfBirth()+
                    "',bio = '"+user.getBio()+
                    "',picture_id = "+user.getPictureId();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            isUpdated=true;
            
        } catch (SQLException ex) {
            isUpdated = false;
        }
        return isUpdated;
    }

    @Override
    public boolean delete(User object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
