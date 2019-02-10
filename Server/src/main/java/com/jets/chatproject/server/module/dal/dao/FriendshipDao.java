/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao;

import com.jets.chatproject.server.module.dal.entities.User;
import com.jets.chatproject.server.module.dal.entities.Friendship;
import java.util.List;

/**
 *
 * @author ibrahim
 */
public interface FriendshipDao {

    void addFriends(User user1, User user2);

    void update(Friendship friendship);

    List<Friendship> getAllFriendshipsForUser(int userId);

}
