/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.module.rmi;

import com.jets.chatproject.module.rmi.dto.UserDTO;
import com.jets.chatproject.module.rmi.dto.UserStatus;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author ibrahim
 */
public interface UsersService extends Remote {

    void goOnline(String session) throws RemoteException;

    void goOffline(String session) throws RemoteException;

    UserDTO getProfileById(String session, int userId) throws RemoteException;

    UserDTO getProfileByPhone(String session, String userPhone) throws RemoteException;

    void updateProfile(String session, UserDTO userProfile, byte[] picture) throws RemoteException;

    void changeStatus(String session, UserStatus status) throws RemoteException;

    byte[] getPicture(String session, int pictureId) throws RemoteException;

}
