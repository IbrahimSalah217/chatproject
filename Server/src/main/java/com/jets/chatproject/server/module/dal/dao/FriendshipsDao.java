/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao;

import com.jets.chatproject.server.module.dal.entities.Friendship;
import java.util.List;

/**
 *
 * @author ibrahim
 */
public interface FriendshipsDao extends AbstractDAO<Friendship> {

    void addMitualFriendship(int userId, int anotherUserId);

    Friendship findByUserAndFriend(int userId, int friendId);

    List<Friendship> getAllFriendshipsForUser(int userId);

}
