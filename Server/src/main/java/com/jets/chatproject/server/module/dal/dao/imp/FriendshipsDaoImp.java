/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao.imp;

import com.jets.chatproject.server.module.dal.dao.FriendshipsDao;
import com.jets.chatproject.server.module.dal.entities.Friendship;
import java.util.List;

/**
 *
 * @author ibrahim
 */
public class FriendshipsDaoImp implements FriendshipsDao {

    @Override
    public void addMitualFriendship(int userId, int anotherUserId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Friendship findByUserAndFriend(int userId, int friendId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Friendship> getAllFriendshipsForUser(int userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean insert(Friendship object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(Friendship object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(Friendship object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

