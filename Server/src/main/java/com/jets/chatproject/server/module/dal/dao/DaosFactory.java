/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao;

/**
 *
 * @author ibrahim
 */
public interface DaosFactory {

    DirectMessagesDao getDirectMessagesDao();

    FriendshipsDao getFriendshipsDao();

    GroupMembersDao getGroupMembersDao();

    GroupMessagesDao getGroupMessagesDao();

    GroupsDao getGroupsDao();

    PicturesDao getPicturesDao();

    RequestsDoa getRequestsDoa();

    UsersDao getUsersDao();

}
