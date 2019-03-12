/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.hibernate.dao.imp;

import com.jets.chatproject.server.module.dal.dao.FriendshipsDao;
import com.jets.chatproject.server.module.dal.entities.Friendship;
import com.jets.chatproject.server.module.dal.hibernate.entity.DirectMessages;
import com.jets.chatproject.server.module.dal.hibernate.entity.Friendships;
import com.jets.chatproject.server.module.dal.hibernate.entity.FriendshipsId;
import com.jets.chatproject.server.module.dal.hibernate.entity.Users;
import java.util.LinkedList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ibrahim
 */
public class FriendshipsDaoImp implements FriendshipsDao {

    Session session;

    public FriendshipsDaoImp(Session session) {
        this.session = session;
    }

    private Friendship mapFriendShip(Friendships dbFriendships) {
        return new Friendship(dbFriendships.getUsersByUserId().getUserId(), dbFriendships.getUsersByFriendId().getUserId(),
                dbFriendships.getCategory(), dbFriendships.getBlocked(), dbFriendships.getDirectMessages().getId());
    }

    @Override
    public void addMitualFriendship(int userId, int anotherUserId) throws Exception {
        insert(new Friendship(userId, anotherUserId, "", false, -1));
        insert(new Friendship(anotherUserId, userId, "", false, -1));
    }

    @Override
    public Friendship findByUserAndFriend(int userId, int friendId) throws Exception {
        Friendships friendships;
        Friendship friendship;
        Criteria criteria = session.createCriteria(Friendships.class);
        friendships = (Friendships) criteria.add(Restrictions.idEq(new FriendshipsId(userId, friendId))).uniqueResult();
        friendship = mapFriendShip(friendships);
        return friendship;
    }

    @Override
    public List<Friendship> getAllFriendshipsForUser(int userId) throws Exception {
        List<Friendships> friendshipsList;
        Criteria criteria = session.createCriteria(Friendships.class);
        friendshipsList = criteria.createCriteria("usersByUserId").add(Restrictions.idEq(userId)).list();
        List<Friendship> friendshipList = new LinkedList<>();
        friendshipsList.forEach((friendships) -> {
            friendshipList.add(mapFriendShip(friendships));
        });
        return friendshipList;
    }

    @Override
    public int insert(Friendship object) throws Exception {
        try {
            Friendships friendships = createFriendships(object);
            session.beginTransaction();
            session.persist(friendships);
            session.getTransaction().commit();
            return friendships.getDirectMessages().getId();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }

    @Override
    public boolean update(Friendship object) throws Exception {
        try {
            Friendships friendships = createFriendships(object);
            session.beginTransaction();
            session.saveOrUpdate(friendships);
            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }

    @Override
    public boolean delete(Friendship object) throws Exception {
        try {
            Friendships friendships = createFriendships(object);
            session.beginTransaction();
            session.delete(friendships);
            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }

    private Friendships createFriendships(Friendship friendship) {
        Criteria criteria = session.createCriteria(DirectMessages.class);
        DirectMessages directMessages = (DirectMessages) criteria.
                add(Restrictions.idEq(friendship.getLastSeenMessageId())).uniqueResult();
        Criteria criteria2 = session.createCriteria(Users.class);
        Users users = (Users) criteria2.
                add(Restrictions.idEq(friendship.getUserId())).uniqueResult();
        Users friends = (Users) criteria2.
                add(Restrictions.idEq(friendship.getFriendId())).uniqueResult();

        FriendshipsId friendshipsId = new FriendshipsId(friendship.getUserId(), friendship.getFriendId());
        Friendships friendships = new Friendships(friendshipsId, directMessages, users,
                friends, friendship.getCategory(), friendship.isBlocked());
        return friendships;

    }

}
