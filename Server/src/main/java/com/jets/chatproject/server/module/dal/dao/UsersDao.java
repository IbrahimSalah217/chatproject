/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao;

import com.jets.chatproject.server.module.dal.entities.User;

/**
 *
 * @author ibrahim
 */
public interface UsersDao {

    void insert(User user);

    void update(User user);

    void delete(User user);

    User findByPhone(String phone);

    User findById(int userId);
}
