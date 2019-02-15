/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.rmi.imp;

import com.jets.chatproject.module.rmi.UsersService;
import com.jets.chatproject.module.rmi.dto.UserDTO;
import com.jets.chatproject.module.rmi.dto.UserStatus;
import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import com.jets.chatproject.server.module.dal.dao.PicturesDao;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
import com.jets.chatproject.server.module.dal.entities.DTOMapper;
import com.jets.chatproject.server.module.dal.entities.Picture;
import com.jets.chatproject.server.module.dal.entities.User;
import com.jets.chatproject.server.module.session.ISessionManager;
import java.rmi.RemoteException;

/**
 *
 * @author ibrahim
 */
public class UsersServiceImp implements UsersService {

    ISessionManager sessionManager;
    UsersDao usersDao;
    PicturesDao picturesDao;

    public UsersServiceImp(DaosFactory daosFactory, ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
        usersDao = daosFactory.getUsersDao();
        picturesDao = daosFactory.getPicturesDao();
    }

    @Override
    public void goOnline(String session) throws RemoteException {
        changeStatus(session, UserStatus.AVAILABLE);
    }

    @Override
    public void goOffline(String session) throws RemoteException {
        changeStatus(session, UserStatus.OFFLINE);
    }

    @Override
    public UserDTO getProfileById(String session, int userId) throws RemoteException {
        User user = usersDao.findById(userId);
        return DTOMapper.createUserDTO(user);
    }

    @Override
    public UserDTO getProfileByPhone(String session, String userPhone) throws RemoteException {
        User user = usersDao.findByPhone(userPhone);
        return DTOMapper.createUserDTO(user);
    }

    @Override
    public void updateProfile(String session, UserDTO userProfile, byte[] picture) throws RemoteException {
        int userId = sessionManager.findUserId(session);
        if (userId != userProfile.getId()) {
            throw new RemoteException("Unauthorized operation");
        }
        User user = usersDao.findById(userProfile.getId());
        user.setBio(userProfile.getBio());
        user.setCountry(userProfile.getCountry());
        user.setDateOfBirth(userProfile.getDateOfBirth());
        user.setDisplyName(userProfile.getDisplyName());
        user.setEmail(userProfile.getEmail());
        user.setGender(userProfile.getGender());
        user.setPictureId(userProfile.getPictureId());
        usersDao.update(user);
    }

    @Override
    public void changeStatus(String session, UserStatus status) throws RemoteException {
        int userId = sessionManager.findUserId(session);
        User user = usersDao.findById(userId);
        user.setState(status);
        usersDao.update(user);
    }

    @Override
    public byte[] getPicture(String session, int pictureId) throws RemoteException {
        Picture picture = picturesDao.findById(pictureId);
        return picture.getData();
    }

}
