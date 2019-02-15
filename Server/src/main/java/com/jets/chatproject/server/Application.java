/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server;

import com.jets.chatproject.module.rmi.*;
import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import com.jets.chatproject.server.module.dal.dao.imp.DbDaosFactory;
import com.jets.chatproject.server.module.rmi.imp.AuthServiceImpl;
import com.jets.chatproject.server.module.rmi.imp.FriendRequestsServiceImpl;
import com.jets.chatproject.server.module.rmi.imp.FriendshipServiceImp;
import com.jets.chatproject.server.module.rmi.imp.GroupsServiceImp;
import com.jets.chatproject.server.module.rmi.imp.MessagesServiceImp;
import com.jets.chatproject.server.module.rmi.imp.UsersServiceImp;
import com.jets.chatproject.server.module.session.ISessionManager;
import com.jets.chatproject.server.module.session.DummySessionManager;
import java.rmi.Remote;
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
            DaosFactory daosFactory = new DbDaosFactory();

            AuthService authService
                    = new AuthServiceImpl(daosFactory, sessionManager);
            FriendRequestsService friendRequestsService
                    = new FriendRequestsServiceImpl(daosFactory, sessionManager);
            FriendshipService friendshipService
                    = new FriendshipServiceImp(daosFactory, sessionManager);
            GroupsService groupsService
                    = new GroupsServiceImp(daosFactory, sessionManager);
            MessagesService messagesService
                    = new MessagesServiceImp(daosFactory, sessionManager);
            UsersService usersService
                    = new UsersServiceImp(daosFactory, sessionManager);

            bind(registry, authService);
            bind(registry, friendRequestsService);
            bind(registry, friendshipService);
            bind(registry, groupsService);
            bind(registry, messagesService);
            bind(registry, usersService);

        } catch (RemoteException ex) {
            Logger.getLogger(Application.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private static void bind(Registry registry, Remote service) throws RemoteException {
        registry.rebind(service.getClass().getName(), service);
    }

}
