/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.hibernate.dao.imp;

import com.jets.chatproject.module.rmi.dto.Gender;
import com.jets.chatproject.module.rmi.dto.UserStatus;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
import com.jets.chatproject.server.module.dal.entities.User;
import com.jets.chatproject.server.module.dal.hibernate.entity.Pictures;
import com.jets.chatproject.server.module.dal.hibernate.entity.Users;
import java.util.LinkedList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Hadeer
 */
public class UsersDaoImp implements UsersDao {

    Session session;

    public UsersDaoImp(Session session) {
        this.session = session;
    }

    private User mapUser(Users dbUser) {
        return new User(dbUser.getUserId(), dbUser.getPhoneNumber(),
                dbUser.getDisplayName(), dbUser.getEmail(),
                dbUser.getPassword(), Gender.valueOf(dbUser.getGender()),
                dbUser.getCountry(), dbUser.getDateOfBirth(), dbUser.getBio(),
                UserStatus.valueOf(dbUser.getState()),
                dbUser.getPictures().getPictureId(),
                dbUser.getSystemRegistration() != 0);
    }

    @Override
    synchronized public User findByPhone(String phone) throws Exception {
        Criteria criteria = session.createCriteria(Users.class);
        criteria.add(Restrictions.eq("phoneNumber", phone));
        Users dbUser = (Users) criteria.uniqueResult();
        if (dbUser == null) {
            return null;
        } else {
            return mapUser(dbUser);
        }
    }

    @Override
    synchronized public User findById(int id) throws Exception {
        Criteria criteria = session.createCriteria(Users.class);
        criteria.add(Restrictions.eq("userId", id));
        Users dbUser = (Users) criteria.uniqueResult();
        if (dbUser == null) {
            return null;
        } else {
            return mapUser(dbUser);
        }
    }

    @Override
    synchronized public List<User> findAllUser() throws Exception {
        Criteria criteria = session.createCriteria(Users.class);
        List<Users> list = criteria.list();
        List<User> result = new LinkedList<>();
        list.forEach((dbUser) -> {
            result.add(mapUser(dbUser));
        });
        return result;
    }

    private Users createDbUser(User user) {
        Pictures picture = (Pictures) session.get(Pictures.class, user.getPictureId());
        Users dbUser = new Users(picture, user.getPhoneNumber(),
                user.getDisplyName(), user.getEmail(), user.getPassword(),
                user.getState().toString(), user.getGender().toString(),
                user.getCountry(), user.getDateOfBirth(), user.getBio(),
                user.getSystemRegistration() ? (byte) 1 : 0, null,
                null, null, null, null, null, null, null, null);
        return dbUser;
    }

    @Override
    synchronized public int insert(User user) throws Exception {
        try {
            Users dbUser = createDbUser(user);
            session.beginTransaction();
            session.persist(dbUser);
            session.getTransaction().commit();
            return dbUser.getUserId();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }

    private void updateDbUser(User user, Users dbUser) {
        Pictures picture = (Pictures) session.get(Pictures.class, user.getPictureId());
        dbUser.setPhoneNumber(user.getPhoneNumber());
        dbUser.setDisplayName(user.getDisplyName());
        dbUser.setEmail(user.getEmail());
        dbUser.setPassword(user.getPassword());
        dbUser.setState(user.getState().toString());
        dbUser.setGender(user.getGender().toString());
        dbUser.setCountry(user.getCountry());
        dbUser.setDateOfBirth(new java.sql.Date(user.getDateOfBirth().getTime()));
        dbUser.setBio(user.getBio());
        dbUser.setPictures(picture);
        dbUser.setSystemRegistration(user.getSystemRegistration() ? (byte) 1 : 0);
    }

    @Override
    synchronized public boolean update(User user) throws Exception {
        Users dbUser = (Users) session.get(Users.class, user.getId());
        updateDbUser(user, dbUser);
        try {
            session.beginTransaction();
            session.update(dbUser);
            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }

    @Override
    synchronized public boolean delete(User object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
