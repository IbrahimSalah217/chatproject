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
import com.jets.chatproject.server.module.dal.dao.FriendshipsDao;
import com.jets.chatproject.server.module.dal.dao.PicturesDao;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
import com.jets.chatproject.server.module.dal.entities.DTOMapper;
import com.jets.chatproject.server.module.dal.entities.Friendship;
import com.jets.chatproject.server.module.dal.entities.Picture;
import com.jets.chatproject.server.module.dal.entities.User;
import com.jets.chatproject.server.module.session.Broadcaster;
import java.rmi.RemoteException;
import com.jets.chatproject.server.module.session.SessionManager;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ibrahim
 */
public class UsersServiceImp extends UnicastRemoteObject implements UsersService {

    SessionManager sessionManager;
    UsersDao usersDao;
    PicturesDao picturesDao;
    FriendshipsDao friendshipsDao;

    public UsersServiceImp(DaosFactory daosFactory, SessionManager sessionManager) throws RemoteException {
        this.sessionManager = sessionManager;
        usersDao = daosFactory.getUsersDao();
        picturesDao = daosFactory.getPicturesDao();
        friendshipsDao = daosFactory.getFriendshipsDao();
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
        try {
            User user = usersDao.findById(userId);
            return DTOMapper.createUserDTO(user);
        } catch (Exception ex) {
            throw new RemoteException("Database exception", ex);
        }
    }

    @Override
    public UserDTO getProfileByPhone(String session, String userPhone) throws RemoteException {
        try {
            User user = usersDao.findByPhone(userPhone);
            return DTOMapper.createUserDTO(user);
        } catch (Exception ex) {
            throw new RemoteException("Database exception", ex);
        }
    }

    @Override
    public void updateProfile(String session, UserDTO userProfile, byte[] picture) throws RemoteException {
        try {
            int userId = sessionManager.findUserId(session);
//            if (userId != userProfile.getId()) {
//                throw new RemoteException("Unauthorized operation");
//            }
            picturesDao.update(new Picture(userProfile.getPictureId(), picture));
            User user = usersDao.findById(userId);
            user.setDisplyName(userProfile.getDisplyName());
            user.setEmail(userProfile.getEmail());
            user.setGender(userProfile.getGender());
            user.setCountry(userProfile.getCountry());
            user.setDateOfBirth(userProfile.getDateOfBirth());
            user.setBio(userProfile.getBio());
            user.setPictureId(userProfile.getPictureId());
            user.setSystemRegistration(false);
            usersDao.update(user);
        } catch (Exception ex) {
            throw new RemoteException("Database exception", ex);
        }
    }

    @Override
    public void changeStatus(String session, UserStatus status) throws RemoteException {
        try {
            int userId = sessionManager.findUserId(session);
            User user = usersDao.findById(userId);
            user.setState(status);
            usersDao.update(user);
            List<Friendship> friendships = friendshipsDao.getAllFriendshipsForUser(userId);
            List<Integer> friendsIdList = new ArrayList<>();
            friendships.forEach((friend) -> {
                friendsIdList.add(friend.getFriendId());
            });
            Broadcaster.getInstance().broadcastStatus(userId,friendsIdList, status);
        } catch (Exception ex) {
            throw new RemoteException("Database exception", ex);
        }
    }

    @Override
    public byte[] getPicture(String session, int pictureId) throws RemoteException {
        try {
            Picture picture = picturesDao.findById(pictureId);
            return picture.getData();
        } catch (Exception ex) {
            throw new RemoteException("Database exception", ex);
        }
    }

    @Override
    public void updateStatus(String session, UserStatus status) throws RemoteException {
        try {
            int userID = sessionManager.findUserId(session);
            User user = usersDao.findById(userID);
            user.setState(status);
            usersDao.update(user);
            List<Friendship> friendships = friendshipsDao.getAllFriendshipsForUser(userID);
            List<Integer> friendsIdList = new ArrayList<>();
            friendships.forEach((friend) -> {
                friendsIdList.add(friend.getFriendId());
            });
            Broadcaster.getInstance().broadcastStatus(userID,friendsIdList, status);
        } catch (Exception ex) {
            throw new RemoteException("Data Base Exception", ex);
        }
    }

    @Override
    public UserStatus getStatus(String userSession, int friendId) throws RemoteException {
        int userId;
        User user;
        try {
              userId = sessionManager.findUserId(userSession);
              if (friendId == -1) {
                user = usersDao.findById(userId);
              } else {
                user = usersDao.findById(friendId);
            }
        } catch (Exception ex) {
            throw new RemoteException("Data Base Exception", ex);
        }
        return user.getState();
    }
}
