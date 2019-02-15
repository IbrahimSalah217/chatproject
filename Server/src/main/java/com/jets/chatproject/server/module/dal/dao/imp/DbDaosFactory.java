/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao.imp;

import com.jets.chatproject.server.module.dal.cfg.DataSourceFactory;
import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import com.jets.chatproject.server.module.dal.dao.DirectMessagesDao;
import com.jets.chatproject.server.module.dal.dao.FriendshipsDao;
import com.jets.chatproject.server.module.dal.dao.GroupMembersDao;
import com.jets.chatproject.server.module.dal.dao.GroupMessagesDao;
import com.jets.chatproject.server.module.dal.dao.GroupsDao;
import com.jets.chatproject.server.module.dal.dao.PicturesDao;
import com.jets.chatproject.server.module.dal.dao.RequestsDoa;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
import javax.sql.DataSource;

/**
 *
 * @author ibrahim
 */
public class DbDaosFactory implements DaosFactory {

    DataSource datasource;

    public DbDaosFactory() {
        datasource = DataSourceFactory.getDataSource();
    }

    @Override
    public DirectMessagesDao getDirectMessagesDao() {
        return new DirectMessagesDaoImp(datasource);
    }

    @Override
    public FriendshipsDao getFriendshipsDao() {
        return new FriendshipsDaoImp(datasource);
    }

    @Override
    public GroupMembersDao getGroupMembersDao() {
        return new GroupMembersDaoImp(datasource);
    }

    @Override
    public GroupMessagesDao getGroupMessagesDao() {
        return new GroupMessagesDaoImp(datasource);
    }

    @Override
    public GroupsDao getGroupsDao() {
        return new GroupsDaoImp(datasource);
    }

    @Override
    public PicturesDao getPicturesDao() {
        return new PicturesDaoImp(datasource);
    }

    @Override
    public RequestsDoa getRequestsDoa() {
        return new RequestsDaoImp(datasource);
    }

    @Override
    public UsersDao getUsersDao() {
        return new UsersDaoImp(datasource);
    }

}
