/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.views.login;

import com.jets.chatproject.client.ChatApp;
import com.jets.chatproject.client.views.register.RegisterController;
import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ibrahim
 */
public class FirstScreenController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Button register;
    @FXML
    private Button login;
    @FXML
    private ImageView logoViewer;

    ChatApp chatApp;

    public FirstScreenController(ChatApp chatApp) {
        this.chatApp = chatApp;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("dummychat.FirstScreenController.initialize()");
    }

    @FXML
    private void regiserInformation(MouseEvent event) {

    }

    @FXML
    private void registerAction(ActionEvent event) {
        chatApp.switchToRegisterScreen();
    }

    @FXML
    private void loginInformation(MouseEvent event) {
    }

    @FXML
    private void loginAction(ActionEvent event) {
        chatApp.switchToLoginPhoneScreen();
    }

    @FXML
    private void logoInformation(MouseEvent event) {
    }

}
