/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.hibernate.dao.imp;

import com.jets.chatproject.server.module.dal.dao.RequestsDoa;
import com.jets.chatproject.server.module.dal.entities.Request;
import com.jets.chatproject.server.module.dal.hibernate.entity.Requests;
import com.jets.chatproject.server.module.dal.hibernate.entity.RequestsId;
import com.jets.chatproject.server.module.dal.hibernate.entity.Users;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ibrahim
 */
public class RequestsDaoImp implements RequestsDoa {

    Session session;

    public RequestsDaoImp(Session session) {
        this.session = session;
    }

    private Request mapRequest(Requests dbRequest) {
        return new Request(dbRequest.getUsersBySenderId().getUserId(),
                dbRequest.getUsersByReceiverId().getUserId(),
                dbRequest.getTime());
    }

    @Override
    public List<Request> findAllByReceiver(int userId) throws Exception {
        Criteria criteria = session.createCriteria(Requests.class);
        List<Requests> list = criteria.list();
        List<Request> result = new LinkedList<>();
        list.forEach((dbRequest) -> {
            result.add(mapRequest(dbRequest));
        });
        return result;
    }

    @Override
    public Request findBySenderReceiver(int senderId, int receiverId) throws Exception {
        Criteria criteria = session.createCriteria(Requests.class);
        RequestsId id = new RequestsId(senderId, receiverId);
        criteria.add(Restrictions.eq("id", id));
        Requests dbRequest = (Requests) criteria.uniqueResult();
        if (dbRequest == null) {
            return null;
        } else {
            return mapRequest(dbRequest);
        }
    }

    private Requests createDbRequest(Request request) {
        Requests dbRequest = new Requests();
        Users sender = (Users) session.get(Users.class, request.getSenderId());
        Users receiver = (Users) session.get(Users.class, request.getReceiverId());
        dbRequest.setUsersBySenderId(sender);
        dbRequest.setUsersBySenderId(receiver);
        dbRequest.setTime(new Date());
        return dbRequest;
    }

    @Override
    public int insert(Request request) throws Exception {
        try {
            Requests dbRequest = createDbRequest(request);
            session.beginTransaction();
            session.persist(dbRequest);
            session.getTransaction().commit();
            return 0;
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }

    @Override
    public boolean update(Request request) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(Request request) throws Exception {
        RequestsId id = new RequestsId(request.getSenderId(), request.getReceiverId());
        Requests dbRequest = (Requests) session.get(Requests.class, id);
        try {
            session.beginTransaction();
            session.delete(dbRequest);
            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }

}
