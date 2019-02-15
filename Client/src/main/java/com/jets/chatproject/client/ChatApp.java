/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client;

import com.jets.chatproject.client.cfg.ServiceLocator;
import com.jets.chatproject.client.views.login.FirstScreenController;
import com.jets.chatproject.client.views.login.LoginCheckPhoneController;
import com.jets.chatproject.client.views.login.LoginPasswordController;
import com.jets.chatproject.client.views.controllers.RigisterController;
import com.jets.chatproject.client.views.controllers.userScreenController;
import com.jets.chatproject.module.rmi.AuthService;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ibrahim
 */
public class ChatApp extends Application {

    @Override
    public void start(Stage primaryStage){
        switchToFirstScreen(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
 public void switchToFirstScreen(Stage stage) {
        Stage primaryStage = stage;
        FXMLLoader loader = new FXMLLoader();
        FirstScreenController controller = new FirstScreenController(stage);
        loader.setController(controller);
       
        try {
            Parent root = loader.load(getClass().getResource("firstScreen.fxml").openStream());
            Scene scene = new Scene(root);
            primaryStage.setTitle("ButterFly Chat");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(ChatApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void switchToLoginPhoneScreen(Stage stage) {
        Stage primaryStage = stage;
        FXMLLoader loader = new FXMLLoader();
        LoginCheckPhoneController controller = new LoginCheckPhoneController(stage);
        loader.setController(controller);
       
        try {
            Parent root = loader.load(getClass().getResource("loginCheckPhone.fxml").openStream());
            Scene scene = new Scene(root);
            primaryStage.setTitle("ButterFly Chat logIn Phone");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(ChatApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void switchToLoginPasswordScreen(Stage stage,String userPhone) {
        Stage primaryStage = stage;
        FXMLLoader loader = new FXMLLoader();
        LoginPasswordController controller = new LoginPasswordController(stage,userPhone);
        loader.setController(controller);
       
        try {
            Parent root = loader.load(getClass().getResource("loginPassword.fxml").openStream());
            Scene scene = new Scene(root);
            primaryStage.setTitle("ButterFly Chat logIn Password");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(ChatApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void switchToRegisterScreen(Stage stage) {
        Stage primaryStage = stage;
        FXMLLoader loader = new FXMLLoader();
        RigisterController controller = new RigisterController(stage);
        loader.setController(controller);
       
        try {
            Parent root = loader.load(getClass().getResource("register.fxml").openStream());
            Scene scene = new Scene(root);
            primaryStage.setTitle("ButterFly Chat Register");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(ChatApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void switchToUserScreen(Stage stage,String userSession) {
        Stage primaryStage = stage;
        FXMLLoader loader = new FXMLLoader();
        userScreenController controller = new userScreenController(stage,userSession);
        loader.setController(controller);
       
        try {
            Parent root = loader.load(getClass().getResource("userScreen.fxml").openStream());
            Scene scene = new Scene(root);
            primaryStage.setTitle("ButterFly Chat userSCreen");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(ChatApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isExistPhone(String phone) throws Exception{
        boolean PhoneExist = false;
        try {
            AuthService authService = ServiceLocator.getService(AuthService.class);
            if(authService.checkPhone(phone)){
                PhoneExist = true;
            }
        } catch (Exception ex) {
            throw ex;                                               //// if throw Exception
        }
        return PhoneExist;
    } 
    
    public String isRealUser(String phone,String password)throws Exception{
        String userSession = null;
       try {
            AuthService authService = ServiceLocator.getService(AuthService.class);
            userSession = authService.login(phone, password);
        } catch (Exception ex) {
            throw ex;
        }
        return userSession;
        
    }
}
