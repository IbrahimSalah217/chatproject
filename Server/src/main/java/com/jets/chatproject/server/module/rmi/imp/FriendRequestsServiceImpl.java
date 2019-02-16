/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.rmi.imp;

import com.jets.chatproject.module.rmi.FriendRequestsService;
import com.jets.chatproject.module.rmi.dto.RequestDTO;
import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import com.jets.chatproject.server.module.dal.dao.FriendshipsDao;
import com.jets.chatproject.server.module.dal.dao.RequestsDoa;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
import com.jets.chatproject.server.module.dal.entities.DTOMapper;
import com.jets.chatproject.server.module.dal.entities.Request;
import com.jets.chatproject.server.module.dal.entities.User;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.jets.chatproject.server.module.session.SessionManager;

/**
 *
 * @author salma
 */
public class FriendRequestsServiceImpl extends UnicastRemoteObject implements FriendRequestsService {

    SessionManager sessionManager;
    UsersDao userdao;
    RequestsDoa requestsDoa;
    FriendshipsDao friendshipsDao;

    public FriendRequestsServiceImpl(DaosFactory daosFactory, SessionManager sessionManager) throws RemoteException {
        this.sessionManager = sessionManager;
        this.userdao = daosFactory.getUsersDao();
        this.requestsDoa = daosFactory.getRequestsDoa();
        this.friendshipsDao = daosFactory.getFriendshipsDao();
    }

    @Override
    public void sendRequest(String session, String phone, String email) throws RemoteException {
        try {
            int senderId = sessionManager.findUserId(session);
            int receiverId = userdao.findByPhone(phone).getId();
            Request request = requestsDoa.findBySenderReceiver(receiverId, senderId);
            if (request != null) {
                acceptRequest(session, receiverId);
            } else {
                Date requestTime = new Date();
                request = new Request(senderId, receiverId, requestTime);
                requestsDoa.insert(request);
            }
        } catch (Exception ex) {
            throw new RemoteException("Database falied",ex);
        }
    }

    @Override
    public List<RequestDTO> getAllRequests(String session) throws RemoteException {
        try {
            int userId = sessionManager.findUserId(session);
            List<Request> myRequestList = requestsDoa.findAllByReceiver(userId);
            List<RequestDTO> myReturnRequestList = new ArrayList<>();
            for (Request r : myRequestList) {
                User user = userdao.findById(r.getSenderId());
                RequestDTO requestDTO = DTOMapper.createRequestDTO(user, r);
                myReturnRequestList.add(requestDTO);
            }
            return myReturnRequestList;
        } catch (Exception ex) {
            throw new RemoteException("Database falied",ex);
        }
    }

    @Override
    public void acceptRequest(String session, int senderId) throws RemoteException {
        try {
            deleteRequest(session, senderId);
            int userId = sessionManager.findUserId(session);
            friendshipsDao.addMitualFriendship(userId, senderId);
        } catch (Exception ex) {
            throw new RemoteException("Database falied",ex);
        }
    }

    @Override
    public void rejectRequest(String session, int senderId) throws RemoteException {
        deleteRequest(session, senderId);
    }

    private void deleteRequest(String session, int senderId) throws RemoteException {
        try {
            int userId = sessionManager.findUserId(session);
            Request request = requestsDoa.findBySenderReceiver(userId, senderId);
            requestsDoa.delete(request);
        } catch (Exception ex) {
            throw new RemoteException("Database falied",ex);
        }
    }

}
