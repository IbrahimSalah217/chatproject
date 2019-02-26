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
import java.util.ArrayList;
import java.util.List;
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
    public void addMitualFriendship(int userId, int anotherUserId) throws Exception {
        insert(new Friendship(userId, anotherUserId, "", false, -1));
        insert(new Friendship(anotherUserId, userId, "", false, -1));
    }

    @Override
    public Friendship findByUserAndFriend(int userId, int friendId) throws Exception {
        Connection connection = dataSource.getConnection();
        String Query = "select * from friendships where user_id = ? and friend_id = ?";
        PreparedStatement select = connection.prepareStatement(Query);
        select.setInt(1, userId);
        select.setInt(2, friendId);
        ResultSet result = select.executeQuery();
        Friendship friendship = null;
        if (result.next()) {
            friendship = new Friendship(result.getInt(1), result.getInt(2),
                    result.getString(3), result.getBoolean(4), result.getInt(5));

        }
        result.close();
        select.close();
        connection.close();
        return friendship;
    }

    @Override
    public List<Friendship> getAllFriendshipsForUser(int userId) throws Exception {
        List<Friendship> friendList = null;
        Connection connection = dataSource.getConnection();
        String Query = "select * from friendships where user_id = ?";
        PreparedStatement select = connection.prepareStatement(Query);
        select.setInt(1, userId);
        ResultSet result = select.executeQuery();
        friendList = new ArrayList<>();
        while (result.next()) {
            friendList.add(new Friendship(result.getInt(1), result.getInt(2),
                    result.getString(3), result.getBoolean(4), result.getInt(5)));
        }
        result.close();
        select.close();
        connection.close();
        return friendList;
    }

    @Override
    public int insert(Friendship object) throws Exception {
        Connection connection = dataSource.getConnection();
        String Query = "insert into friendships values(?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(Query);
        statement.setInt(1, object.getUserId());
        statement.setInt(2, object.getFriendId());
        statement.setString(3, object.getCategory());
        statement.setBoolean(4, object.isBlocked());
        if (object.getLastSeenMessageId() == -1) {
            statement.setNull(5, java.sql.Types.INTEGER);
        } else {
            statement.setInt(5, object.getLastSeenMessageId());
        }
        statement.executeUpdate();
        statement.close();
        connection.close();
        return -1;
    }

    @Override
    public boolean update(Friendship object) throws Exception {
        Connection connection = dataSource.getConnection();
        String Query = "update friendships set category = ?, blocked = ?, last_seen_message = ? where  user_id = ? and friend_id = ?";
        PreparedStatement update = connection.prepareStatement(Query);
        update.setString(1, object.getCategory());
        update.setBoolean(2, object.isBlocked());
        if (object.getLastSeenMessageId() == -1) {
            update.setNull(3, java.sql.Types.INTEGER);
        } else {
            update.setInt(3, object.getLastSeenMessageId());
        }
        //update.setInt(3, object.getLastSeenMessageId());
        update.setInt(4, object.getUserId());

        update.setInt(5, object.getFriendId());
        update.executeUpdate();
        update.close();
        connection.close();
        return true;
    }

    @Override
    public boolean delete(Friendship object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
