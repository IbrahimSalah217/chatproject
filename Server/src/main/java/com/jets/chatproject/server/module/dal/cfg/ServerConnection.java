/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.cfg;

import com.jets.chatproject.module.rmi.AuthService;
import com.jets.chatproject.module.rmi.FriendRequestsService;
import com.jets.chatproject.module.rmi.FriendshipService;
import com.jets.chatproject.module.rmi.GroupsService;
import com.jets.chatproject.module.rmi.MessagesService;
import com.jets.chatproject.module.rmi.UsersService;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author salma
 */
public class ServerConnection {
    
    AuthService authService;
    FriendRequestsService friendRequestsService;
    FriendshipService friendshipService;
    GroupsService groupsService;
    MessagesService messagesService;
    UsersService usersService; 
    public ServerConnection(AuthService authService, FriendRequestsService friendRequestsService, FriendshipService friendshipService,
            GroupsService groupsService, MessagesService messagesService, UsersService usersService)
    {
        this.authService = authService;
        this.friendRequestsService = friendRequestsService;
        this.friendshipService = friendshipService;
        this.groupsService = groupsService;
        this.messagesService = messagesService;
        this.usersService = usersService;
        
        try {
            Registry registry= LocateRegistry.createRegistry(5000);
            registry.rebind(authService.toString(), authService);
            registry.rebind(friendRequestsService.toString(), friendRequestsService);
            registry.rebind(friendshipService.toString(), friendshipService);
            registry.rebind(groupsService.toString(), groupsService);
            registry.rebind(messagesService.toString(), messagesService);
            registry.rebind(usersService.toString(), usersService);
        } catch (RemoteException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
