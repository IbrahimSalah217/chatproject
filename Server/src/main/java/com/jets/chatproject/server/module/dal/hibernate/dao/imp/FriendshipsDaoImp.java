/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.hibernate.dao.imp;

import com.jets.chatproject.server.module.dal.dao.FriendshipsDao;
import com.jets.chatproject.server.module.dal.entities.Friendship;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author ibrahim
 */
public class FriendshipsDaoImp implements FriendshipsDao {

    Session session;

    public FriendshipsDaoImp(Session session) {
        this.session = session;
    }

    @Override
    public void addMitualFriendship(int userId, int anotherUserId) throws Exception {
        insert(new Friendship(userId, anotherUserId, "", false, -1));
        insert(new Friendship(anotherUserId, userId, "", false, -1));
    }

    @Override
    public Friendship findByUserAndFriend(int userId, int friendId) throws Exception {
        return null;
    }

    @Override
    public List<Friendship> getAllFriendshipsForUser(int userId) throws Exception {
        return null;
    }

    @Override
    public int insert(Friendship object) throws Exception {
        return 0;
    }

    @Override
    public boolean update(Friendship object) throws Exception {
        return true;
    }

    @Override
    public boolean delete(Friendship object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
