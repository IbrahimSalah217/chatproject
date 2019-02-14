/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.rmi.imp;

import com.jets.chatproject.module.rmi.AuthService;
import com.jets.chatproject.module.rmi.dto.UserDTO;
import com.jets.chatproject.module.rmi.dto.UserStatus;
import com.jets.chatproject.server.controllers.SessionManagerInt;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
import java.rmi.RemoteException;
import com.jets.chatproject.server.module.dal.entities.User;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

/**
 *
 * @author salma
 */
public class AuthServiceImpl extends UnicastRemoteObject implements AuthService {

    UsersDao userdao;
    SessionManagerInt sessionManager;

    public AuthServiceImpl(UsersDao userdao, SessionManagerInt sessionManager) throws RemoteException{
        this.userdao = userdao;
        this.sessionManager = sessionManager;
    }

    @Override
    public String login(String phone, String password) throws RemoteException {
        User myUser = userdao.findByPhone(phone);
        if(! myUser.getPassword().equals(password)){
            throw new RemoteException("");
        }
        String uuid = sessionManager.createSession(myUser.getId());
        return uuid;
    }

    @Override
    public String register(UserDTO user, String password) throws RemoteException {
        if (userdao.findByPhone(user.getPhoneNumber()) == null) {
            User myUser = new User(user.getId(), user.getPhoneNumber(), user.getDisplyName(),
                    user.getEmail(), password, user.getGender(), user.getCountry(), user.getDateOfBirth(),
                    user.getBio(), UserStatus.AVAILABLE, user.getPictureId());
            if(!userdao.insert(myUser))
                throw new RemoteException("");
            
        }
        else{
            throw new RemoteException("");
        }
        
                return UUID.randomUUID().toString();
    }

    @Override
    public void logout(String session) throws RemoteException {
        sessionManager.removeSession(session);
    }

    @Override
    public boolean checkPhone(String phone) throws RemoteException {
        if (userdao.findByPhone(phone) == null){
            return false;
        }else{
            return true;
        }
    }
    
    @Override
    public String toString(){
        String serviceName = "Auth Services";
        return serviceName;
    } 


}
