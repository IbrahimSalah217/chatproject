/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.hibernate.dao.imp;

import com.jets.chatproject.server.module.dal.dao.GroupsDao;
import com.jets.chatproject.server.module.dal.entities.Group;
import com.jets.chatproject.server.module.dal.entities.Request;
import com.jets.chatproject.server.module.dal.hibernate.entity.Groups;
import com.jets.chatproject.server.module.dal.hibernate.entity.Requests;
import com.jets.chatproject.server.module.dal.hibernate.entity.Users;
import com.jets.chatproject.server.module.dal.hibernate.entity.Pictures;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 * @author ibrahim
 */
public class GroupsDaoImp implements GroupsDao {

    Session session;

    public GroupsDaoImp(Session session) {
        this.session = session;
    }

    private Group mapGroup(Groups group) {
        return new Group(group.getGroupId(), group.getUsers().getUserId(),
                group.getDisplayName(), group.getPictures().getPictureId());
    }

    @Override
    public List<Group> findAllForUser(int userId) throws Exception {
        Criteria criteria = session.createCriteria(Requests.class);
        List<Groups> list = criteria.list();
        List<Group> result = new LinkedList<>();
        list.forEach((dbGroup) -> {
            result.add(mapGroup(dbGroup));
        });
        return result;
    }

    private Groups createDbGroup(Group group) {
        Groups dbGroup = new Groups();
        Pictures picture = (Pictures) session.get(Pictures.class, group.getPictureId());
        dbGroup.setPictures(picture);
        dbGroup.setDisplayName(group.getGroupName());
        Users admin = (Users) session.get(Users.class, group.getAdminId());
        dbGroup.setUsers(admin);
        return dbGroup;
    }

    @Override
    public int insert(Group object) throws Exception {
        try {
            Groups dbGroup = createDbGroup(object);
            session.beginTransaction();
            session.persist(dbGroup);
            session.getTransaction().commit();
            return dbGroup.getGroupId();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }

    @Override
    public boolean update(Group object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(Group object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
