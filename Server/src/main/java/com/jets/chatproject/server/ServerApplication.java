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
import com.jets.chatproject.server.module.session.DummySessionManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jets.chatproject.server.module.session.SessionManager;
import com.jets.chatproject.server.views.MainPageController;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ibrahim
 */
public class ServerApplication extends Application {

    Registry registry;
    SessionManager sessionManager;
    DaosFactory daosFactory;
    AuthService authService;
    FriendRequestsService friendRequestsService;
    FriendshipService friendshipService;
    GroupsService groupsService;
    MessagesService messagesService;
    UsersService usersService;

    public ServerApplication() {
        try {

            try {
                registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            } catch (RemoteException e) {

                registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
            }
            sessionManager = new DummySessionManager();
            daosFactory = new DbDaosFactory();

            authService
                    = new AuthServiceImpl(daosFactory, sessionManager);
            friendRequestsService
                    = new FriendRequestsServiceImpl(daosFactory, sessionManager);
            friendshipService
                    = new FriendshipServiceImp(daosFactory, sessionManager);
            groupsService
                    = new GroupsServiceImp(daosFactory, sessionManager);
            messagesService
                    = new MessagesServiceImp(daosFactory, sessionManager);
            usersService
                    = new UsersServiceImp(daosFactory, sessionManager);

        } catch (RemoteException ex) {
            Logger.getLogger(ServerApplication.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        launch(args);
//        ServerApplication application = new ServerApplication();
    }

    private static <T extends Remote> void bind(Registry registry, Class<T> service, T serviceImp)
            throws RemoteException {
        System.out.println("Binding service: " + service.getName());
        registry.rebind(service.getName(), serviceImp);
    }

    private static <T extends Remote> void unbind(Registry registry, Class<T> service)
            throws AccessException, RemoteException, NotBoundException {
        System.out.println("Binding service: " + service.getName());
        registry.unbind(service.getName());
    }

    public void stratServer() {

        try {
            bind(registry, AuthService.class, authService);
            bind(registry, FriendRequestsService.class, friendRequestsService);
            bind(registry, FriendshipService.class, friendshipService);
            bind(registry, GroupsService.class, groupsService);
            bind(registry, MessagesService.class, messagesService);
            bind(registry, UsersService.class, usersService);
        } catch (RemoteException ex) {
            Logger.getLogger(ServerApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stopServer() {

        try {
            unbind(registry, AuthService.class);
            unbind(registry, FriendRequestsService.class);
            unbind(registry, FriendshipService.class);
            unbind(registry, GroupsService.class);
            unbind(registry, MessagesService.class);
            unbind(registry, UsersService.class);
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(ServerApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        MainPageController controller = new MainPageController(primaryStage);
        loader.setController(controller);
        Parent root = loader.load(getClass().getResource("MainPage.fxml"));
        Scene scene = new Scene(root, 800, 400);
        primaryStage.setTitle("Server Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
