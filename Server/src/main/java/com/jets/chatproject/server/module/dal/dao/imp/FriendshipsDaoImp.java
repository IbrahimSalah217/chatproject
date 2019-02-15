/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao.imp;

import com.jets.chatproject.server.module.dal.dao.FriendshipsDao;
import com.jets.chatproject.server.module.dal.entities.Friendship;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author ibrahim
 */
public class FriendshipsDaoImp implements FriendshipsDao {
    DataSource dataSource;

    public FriendshipsDaoImp(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addMitualFriendship(int userId, int anotherUserId) {
        if(findByUserAndFriend(userId, anotherUserId)==null){
            try {
                Connection connection = dataSource.getConnection();
                String Query = "update friendships set friend_id = ? where user_id=?";
                PreparedStatement update = connection.prepareStatement(Query);
                update.setInt(1,anotherUserId);
                update.setInt(2, userId);
                update.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(FriendshipsDaoImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
    }

    @Override
    public Friendship findByUserAndFriend(int userId, int friendId) {
        
            Friendship friendShip = null;
        try {
            
            Connection connection = dataSource.getConnection();
            String Query = "select * from friendships where user_id=? friend_id =?";
            PreparedStatement select = connection.prepareStatement(Query);
            select.setInt(1, userId);
            ResultSet set = select.executeQuery();
            while(set.next())
                friendShip = new Friendship(userId, friendId,set.getString(3),set.getBoolean(4),set.getInt(5));            
        } catch (SQLException ex) {
            Logger.getLogger(FriendshipsDaoImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return friendShip;
        
    }

    @Override
    public List<Friendship> getAllFriendshipsForUser(int userId) {
        List<Friendship> friendList = null;
        try {
            
            Connection connection = dataSource.getConnection();
            String Query = "select * from friendships where user_id=?";
            PreparedStatement select = connection.prepareStatement(Query);
            select.setInt(1, userId);
            ResultSet set = select.executeQuery();
            friendList = new ArrayList<>();
            while(set.next())
                friendList.add(new Friendship(set.getInt(1),set.getInt(2),set.getString(3),set.getBoolean(4),set.getInt(5)));           
        } catch (SQLException ex) {
            Logger.getLogger(FriendshipsDaoImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return friendList;
    }

    @Override
    public boolean insert(Friendship object) {
            try {
                Connection connection = dataSource.getConnection();
                String Query = "insert into friendships values(?,?,?,?,?)";
                PreparedStatement insert = connection.prepareStatement(Query);
                insert.setInt(1,object.getUserId());
                insert.setInt(2,object.getFriendId());
                insert.setString(3,object.getCategory());
                insert.setBoolean(4,object.isBlocked());
                insert.setInt(5,object.getLastSeenMessageId());
                insert.executeUpdate();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(FriendshipsDaoImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        return false;
    }

    @Override
    public boolean update(Friendship object) {
        if(findByUserAndFriend(object.getUserId(), object.getFriendId())==null){
            try {
                Connection connection = dataSource.getConnection();
                String Query = "update friendships set friend_id = ? where user_id=?";
                PreparedStatement update = connection.prepareStatement(Query);
                update.setInt(1,object.getFriendId());
                update.setInt(2, object.getUserId());
                update.executeUpdate();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(FriendshipsDaoImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
        
    }

    @Override
    public boolean delete(Friendship object) {
        try {
            Connection connection = dataSource.getConnection();
            String query = "DELETE FROM firendships WHERE user_id = ? and friend_id = ? ";
            PreparedStatement delete = connection.prepareStatement(query);
            delete.setInt(1, object.getUserId());
            delete.setInt(2,object.getFriendId());
            delete.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(FriendshipsDaoImp.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
