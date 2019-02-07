/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dao;

import com.jets.chatproject.module.entities.User;

/**
 *
 * @author ibrahim
 */
public interface UsersDao {

    void insertUser(User user);

    void updateUser(User user);

    void deleteUser(User user);

    User findUserByPhone(String phone);
}
