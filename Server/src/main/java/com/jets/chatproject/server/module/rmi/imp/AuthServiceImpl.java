/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.rmi.imp;

import com.jets.chatproject.module.rmi.AuthService;
import com.jets.chatproject.module.rmi.dto.UserDTO;
import com.jets.chatproject.module.rmi.dto.UserStatus;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
import java.rmi.RemoteException;
import com.jets.chatproject.server.module.dal.entities.User;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;
import com.jets.chatproject.server.module.session.ISessionManager;

/**
 *
 * @author salma
 */
public class AuthServiceImpl extends UnicastRemoteObject implements AuthService {

    UsersDao userdao;
    ISessionManager sessionManager;

    public AuthServiceImpl(UsersDao userdao, ISessionManager sessionManager) throws RemoteException {
        this.userdao = userdao;
        this.sessionManager = sessionManager;
    }

    @Override
    public String login(String phone, String password) throws RemoteException {
        User myUser = userdao.findByPhone(phone);
        if (myUser == null || !myUser.getPassword().equals(password)) {
            throw new RemoteException("Login Failed");
        }
        String uuid = sessionManager.createSession(myUser.getId());
        return uuid;
    }

    @Override
    public String register(UserDTO user, byte[] picture, String password) throws RemoteException {
        if (userdao.findByPhone(user.getPhoneNumber()) == null) {
            User myUser = new User(user.getId(), user.getPhoneNumber(), user.getDisplyName(),
                    user.getEmail(), password, user.getGender(), user.getCountry(), user.getDateOfBirth(),
                    user.getBio(), UserStatus.AVAILABLE, user.getPictureId());
            if (!userdao.insert(myUser)) {
                throw new RemoteException("Failed to create account");
            }
            return UUID.randomUUID().toString();
        } else {
            throw new RemoteException("Phone already registered");
        }
    }

    @Override
    public void logout(String session) throws RemoteException {
        sessionManager.removeSession(session);
    }

    @Override
    public boolean checkPhone(String phone) throws RemoteException {
        return userdao.findByPhone(phone) != null;
    }

}
