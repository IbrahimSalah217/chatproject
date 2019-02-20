/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.rmi.imp;

import com.jets.chatproject.module.rmi.AuthService;
import com.jets.chatproject.module.rmi.client.ClientCallback;
import com.jets.chatproject.module.rmi.dto.UserDTO;
import com.jets.chatproject.module.rmi.dto.UserStatus;
import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import com.jets.chatproject.server.module.dal.dao.PicturesDao;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
import com.jets.chatproject.server.module.dal.entities.Picture;
import java.rmi.RemoteException;
import com.jets.chatproject.server.module.dal.entities.User;
import com.jets.chatproject.server.module.session.Broadcaster;
import java.rmi.server.UnicastRemoteObject;
import com.jets.chatproject.server.module.session.SessionManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author salma
 */
public class AuthServiceImpl extends UnicastRemoteObject implements AuthService {

    SessionManager sessionManager;
    UsersDao userdao;
    PicturesDao picturesDao;

    public AuthServiceImpl(DaosFactory daosFactory, SessionManager sessionManager) throws RemoteException {
        this.sessionManager = sessionManager;
        userdao = daosFactory.getUsersDao();
        picturesDao = daosFactory.getPicturesDao();
    }

    @Override
    public String login(String phone, String password) throws RemoteException {
        try {
            User myUser = userdao.findByPhone(phone);
            if (myUser == null || !myUser.getPassword().equals(password)) {
                throw new RemoteException("Login Failed");
            }
            return sessionManager.createSession(myUser.getId());
        } catch (Exception ex) {
            throw new RemoteException("Database exception", ex);
        }
    }

    @Override
    public String register(UserDTO user, byte[] picture, String password) throws RemoteException {
        try {
            if (userdao.findByPhone(user.getPhoneNumber()) == null) {
                int pictureId = picturesDao.insert(new Picture(0, picture));
                User myUser = new User(user.getId(), user.getPhoneNumber(), user.getDisplyName(),
                        user.getEmail(), password, user.getGender(), user.getCountry(), user.getDateOfBirth(),
                        user.getBio(), UserStatus.AVAILABLE, pictureId);
                int userId = userdao.insert(myUser);
                return sessionManager.createSession(userId);
            } else {
                throw new RemoteException("Phone already registered");
            }
        } catch (Exception ex) {
            throw new RemoteException("Database exception", ex);
        }
    }

    @Override
    public void logout(String session) throws RemoteException {
        sessionManager.removeSession(session);
    }

    @Override
    public boolean checkPhone(String phone) throws RemoteException {
        try {
            return userdao.findByPhone(phone) != null;
        } catch (Exception ex) {
            throw new RemoteException("Database exception", ex);
        }
    }

    @Override
    public void setCallBack(String session, ClientCallback clientCallback) throws RemoteException {
        Broadcaster.getInstance().saveClient(
                sessionManager.findUserId(session),
                clientCallback);
    }

    

}
