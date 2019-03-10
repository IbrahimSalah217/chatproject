/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.hibernate.dao.imp;

import com.jets.chatproject.server.module.dal.cfg.MySessionFactory;
import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import com.jets.chatproject.server.module.dal.dao.DirectMessagesDao;
import com.jets.chatproject.server.module.dal.dao.FriendshipsDao;
import com.jets.chatproject.server.module.dal.dao.GroupMembersDao;
import com.jets.chatproject.server.module.dal.dao.GroupMessagesDao;
import com.jets.chatproject.server.module.dal.dao.GroupsDao;
import com.jets.chatproject.server.module.dal.dao.PicturesDao;
import com.jets.chatproject.server.module.dal.dao.RequestsDoa;
import com.jets.chatproject.server.module.dal.dao.UsersDao;

/**
 *
 * @author ibrahim
 */
public class DbDaosFactory implements DaosFactory {

    @Override
    public DirectMessagesDao getDirectMessagesDao() {
        return new DirectMessagesDaoImp(MySessionFactory.getSession());
    }

    @Override
    public FriendshipsDao getFriendshipsDao() {
        return new FriendshipsDaoImp(MySessionFactory.getSession());
    }

    @Override
    public GroupMembersDao getGroupMembersDao() {
        return new GroupMembersDaoImp(MySessionFactory.getSession());
    }

    @Override
    public GroupMessagesDao getGroupMessagesDao() {
        return new GroupMessagesDaoImp(MySessionFactory.getSession());
    }

    @Override
    public GroupsDao getGroupsDao() {
        return new GroupsDaoImp(MySessionFactory.getSession());
    }

    @Override
    public PicturesDao getPicturesDao() {
        return new PicturesDaoImp(MySessionFactory.getSession());
    }

    @Override
    public RequestsDoa getRequestsDoa() {
        return new RequestsDaoImp(MySessionFactory.getSession());
    }

    @Override
    public UsersDao getUsersDao() {
        return new UsersDaoImp(MySessionFactory.getSession());
    }

}
