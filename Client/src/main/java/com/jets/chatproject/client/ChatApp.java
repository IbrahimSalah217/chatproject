/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client;

import com.jets.chatproject.client.cfg.ServiceLocator;
import com.jets.chatproject.client.controller.ScreenController;
import com.jets.chatproject.client.util.DialogUtils;
import com.jets.chatproject.module.rmi.AuthService;
import com.jets.chatproject.module.rmi.client.ClientCallback;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author ibrahim
 */
public class ChatApp extends Application {

    Stage primaryStage;

    ScreenController screenController;

    @Override
    public void start(Stage primaryStage) {
        try {
            this.primaryStage = primaryStage;
            screenController = new ScreenController(primaryStage);
            screenController.switchToLoginPhoneScreen();
        } catch (Exception ex) {
            DialogUtils.showException(ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
