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
import com.jets.chatproject.server.module.dal.entities.Friendship;
import com.jets.chatproject.server.module.dal.entities.Request;
import com.jets.chatproject.server.module.dal.entities.User;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.jets.chatproject.server.module.session.SessionManager;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public void sendRequest(String session, String phone) throws RemoteException {
        try {
            int senderId = sessionManager.findUserId(session);
            int receiverId = userdao.findByPhone(phone).getId();
            Request request = requestsDoa.findBySenderReceiver(receiverId, senderId);
            Friendship friendship = friendshipsDao.findByUserAndFriend(senderId, receiverId);
            if (friendship == null) {
                if (request != null) {
                    acceptRequest(session, receiverId);
                } else {
                    Date requestTime = new Date();
                    request = new Request(senderId, receiverId, requestTime);
                    requestsDoa.insert(request);
                }
            }
        }catch (Exception ex) {
            throw new RemoteException("Database exception", ex);
        }
    }

    @Override
    public List<RequestDTO> getAllRequests(String session) throws RemoteException {
        try {
            int userId = sessionManager.findUserId(session);
            List<Request> requests = requestsDoa.findAllByReceiver(userId);
            List<RequestDTO> requestDTOs = new ArrayList<>();
            for (Request request : requests) {
                User user = userdao.findById(request.getSenderId());
                requestDTOs.add(DTOMapper.createRequestDTO(user, request));
            }
            return requestDTOs;
        } catch (Exception ex) {
            throw new RemoteException("Database exception", ex);
        }
    }

    @Override
    public void acceptRequest(String session, int senderId) throws RemoteException {
        try {
            deleteRequest(session, senderId);
            int userId = sessionManager.findUserId(session);
            friendshipsDao.addMitualFriendship(userId, senderId);
        } catch (Exception ex) {
            throw new RemoteException("Database falied", ex);
        }
    }

    @Override
    public void rejectRequest(String session, int senderId) throws RemoteException {
        try {
            deleteRequest(session, senderId);
        } catch (Exception ex) {
            throw new RemoteException("Database falied", ex);
        }
    }

    private void deleteRequest(String session, int senderId) throws Exception {
        int userId = sessionManager.findUserId(session);
        Request request = requestsDoa.findBySenderReceiver(senderId, userId);
        requestsDoa.delete(request);
    }

}
