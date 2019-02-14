/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server;

import com.jets.chatproject.module.rmi.AuthService;
import com.jets.chatproject.server.module.dal.cfg.DataSourceFactory;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
import com.jets.chatproject.server.module.dal.dao.imp.UsersDaoImp;
import com.jets.chatproject.server.module.rmi.imp.AuthServiceImpl;
import com.jets.chatproject.server.module.session.ISessionManager;
import com.jets.chatproject.server.module.session.DummySessionManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ibrahim
 */
public class Application {

    public static void main(String[] args) {
        try {
            Registry registry;
            try {
                registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            } catch (RemoteException e) {
                registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
            }

            ISessionManager sessionManager = new DummySessionManager();
            UsersDao usersDao = new UsersDaoImp(DataSourceFactory.getDataSource());
            AuthService authService = new AuthServiceImpl(usersDao, sessionManager);
            registry.rebind(AuthService.class.getName(), authService);

        } catch (RemoteException ex) {
            Logger.getLogger(Application.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

}
