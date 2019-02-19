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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public User findByPhone(String phone) throws Exception {
        Connection connection = dataSource.getConnection();
        String query = "select * from users where phone_number = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, phone);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            String phoneNumber = resultSet.getString(2);
            String name = resultSet.getString(3);
            String email = resultSet.getString(4);
            String password = resultSet.getString(5);
            UserStatus status = UserStatus.valueOf(resultSet.getString(6));
            Gender gender = Gender.valueOf(resultSet.getString(7));
            String country = resultSet.getString(8);
            Date dateOfBirth = resultSet.getDate(9);
            String bio = resultSet.getString(10);
            int pictureId = resultSet.getInt(11);
            boolean systemRegistration = resultSet.getBoolean(12);
            return new User(id, phoneNumber, name, email, password, gender, country, dateOfBirth, bio, status, pictureId, systemRegistration);
        } else {
            return null;
        }
    }

    @Override
    public User findById(int id) throws Exception {
        Connection connection = dataSource.getConnection();
        String query = "select * from users where user_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            id = resultSet.getInt(1);
            String phoneNumber = resultSet.getString(2);
            String name = resultSet.getString(3);
            String email = resultSet.getString(4);
            String password = resultSet.getString(5);
            UserStatus status = UserStatus.valueOf(resultSet.getString(6));
            Gender gender = Gender.valueOf(resultSet.getString(7));
            String country = resultSet.getString(8);
            Date dateOfBirth = resultSet.getDate(9);
            String bio = resultSet.getString(10);
            int pictureId = resultSet.getInt(11);
            boolean systemRegistration = resultSet.getBoolean(12);
            return new User(id, phoneNumber, name, email, password, gender, country, dateOfBirth, bio, status, pictureId, systemRegistration);
        } else {
            return null;
        }
    }

    @Override
    public int insert(User user) throws Exception {
        Connection connection = dataSource.getConnection();
        String query = "insert into users (phone_number, display_name, email, password, state, gender, country, date_of_birth, bio, picture_id, system_registration) values(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getPhoneNumber());
        preparedStatement.setString(2, user.getDisplyName());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setString(5, user.getState().toString());
        preparedStatement.setString(6, user.getGender().toString());
        preparedStatement.setString(7, user.getCountry());
        preparedStatement.setDate(8, new java.sql.Date(user.getDateOfBirth().getTime()));
        preparedStatement.setString(9, user.getBio());
        preparedStatement.setInt(10, user.getPictureId());
        preparedStatement.setBoolean(11, user.getSystemRegistration());
        preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        generatedKeys.next();
        return generatedKeys.getInt(1);
    }

    @Override
    public boolean update(User user) throws Exception {
        String query = "update users set phone_number = ?, display_name = ?, email = ?, password = ?, state = ?, gender = ?, country = ?, date_of_birth = ?, bio = ?, picture_id = ?, system_registration = ? where user_id = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getPhoneNumber());
        preparedStatement.setString(2, user.getDisplyName());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setString(5, user.getState().toString());
        preparedStatement.setString(6, user.getGender().toString());
        preparedStatement.setString(7, user.getCountry());
        preparedStatement.setDate(8, new java.sql.Date(user.getDateOfBirth().getTime()));
        preparedStatement.setString(9, user.getBio());
        preparedStatement.setInt(10, user.getPictureId());
        preparedStatement.setBoolean(11, user.getSystemRegistration());
        preparedStatement.setInt(12, user.getId());
        preparedStatement.executeUpdate();
        return true;
    }

    @Override
    public boolean delete(User object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<User> findAllUser() throws Exception {

        List<User> userList = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        String query = "select * from users";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String phoneNumber = resultSet.getString(2);
            String name = resultSet.getString(3);
            String email = resultSet.getString(4);
            String password = resultSet.getString(5);
            UserStatus status = UserStatus.valueOf(resultSet.getString(6));
            Gender gender = Gender.valueOf(resultSet.getString(7));
            String country = resultSet.getString(8);
            Date dateOfBirth = resultSet.getDate(9);
            String bio = resultSet.getString(10);
            int pictureId = resultSet.getInt(11);
            boolean systemRegistration = resultSet.getBoolean(12);

            userList.add(new User(id, phoneNumber, name, email, password, gender, country, dateOfBirth, bio, status, pictureId, systemRegistration));
        }
        return userList;
    }

}
