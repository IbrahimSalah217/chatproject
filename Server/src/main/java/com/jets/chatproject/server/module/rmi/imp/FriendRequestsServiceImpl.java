/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.rmi.imp;

import com.jets.chatproject.module.rmi.FriendRequestsService;
import com.jets.chatproject.module.rmi.dto.RequestDTO;
import com.jets.chatproject.server.controllers.SessionManagerInt;
import com.jets.chatproject.server.module.dal.dao.FriendshipsDao;
import com.jets.chatproject.server.module.dal.dao.RequestsDoa;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
import com.jets.chatproject.server.module.dal.entities.Request;
import com.jets.chatproject.server.module.dal.entities.User;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author salma
 */
public class FriendRequestsServiceImpl extends UnicastRemoteObject implements FriendRequestsService {

    SessionManagerInt sessionManager;
    UsersDao userdao;
    RequestsDoa requestsDoa;
    FriendshipsDao friendshipsDao;

    public FriendRequestsServiceImpl(UsersDao userdao, SessionManagerInt sessionManager, RequestsDoa requestsDoa, FriendshipsDao friendshipsDao) throws RemoteException{
        this.userdao = userdao;
        this.sessionManager = sessionManager;
        this.requestsDoa = requestsDoa;
        this.friendshipsDao = friendshipsDao;
    }

    @Override
    public void sendRequest(String session, String phone, String email) throws RemoteException {

        int senderId = sessionManager.findUserId(session);
        int receiverId = userdao.findByPhone(phone).getId();
        Date requestTime = new Date();
        List<Request> myRequestList = requestsDoa.findAllByReceiver(senderId);
        for (Request r : myRequestList) {
            if(r.getSenderId() == receiverId){
                acceptRequest(session, receiverId);
            }else{
                Request request = new Request(senderId, receiverId, requestTime);
                requestsDoa.insert(request);
            }
        }
    }

    @Override
    public List<RequestDTO> getAllRequests(String session) throws RemoteException {
        int userId = sessionManager.findUserId(session);
        List<Request> myRequestList = requestsDoa.findAllByReceiver(userId);
        List<RequestDTO> myReturnRequestList = new ArrayList<>();
        for (Request r : myRequestList) {
            User user = userdao.findById(r.getSenderId());
            RequestDTO requestDTO = new RequestDTO(r.getSenderId(), user.getDisplyName(),
                    user.getPictureId(), r.getRequestTime());
            myReturnRequestList.add(requestDTO);
        }
        return myReturnRequestList;
    }

    @Override
    public void acceptRequest(String session, int senderId) throws RemoteException {
        int userId = sessionManager.findUserId(session);
        friendshipsDao.addMitualFriendship(userId, senderId);
    }

    @Override
    public void rejectRequest(String session, int senderId) throws RemoteException {
        int userId = sessionManager.findUserId(session);
        Request request = requestsDoa.findByReceiverSender(userId, senderId);
        requestsDoa.delete(request);
    }

}
