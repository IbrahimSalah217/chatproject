/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.rmi.imp;

import com.jets.chatproject.module.rmi.UsersService;
import com.jets.chatproject.module.rmi.dto.UserDTO;
import com.jets.chatproject.module.rmi.dto.UserStatus;
import com.jets.chatproject.server.module.dal.dao.PicturesDao;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
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

    @Override
    public void goOnline(String session) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void goOffline(String session) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public UserDTO getProfileById(String session, int userId) throws RemoteException {
        User user = usersDao.findById(userId);
        return new UserDTO(user.getId(), user.getPhoneNumber(),
                user.getDisplyName(), user.getEmail(), user.getGender(),
                user.getCountry(), user.getDateOfBirth(), user.getBio(),
                user.getPictureId());
    }

    @Override
    public UserDTO getProfileByPhone(String session, String userPhone) throws RemoteException {
        User user = usersDao.findByPhone(userPhone);
        return new UserDTO(user.getId(), user.getPhoneNumber(),
                user.getDisplyName(), user.getEmail(), user.getGender(),
                user.getCountry(), user.getDateOfBirth(), user.getBio(),
                user.getPictureId());
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public byte[] getPicture(String session, int pictureId) throws RemoteException {
        Picture picture = picturesDao.findById(pictureId);
        return picture.getData();
    }

}
