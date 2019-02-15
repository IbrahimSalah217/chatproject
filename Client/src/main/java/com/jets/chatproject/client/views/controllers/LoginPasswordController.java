/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.views.controllers;

import com.jets.chatproject.client.ChatApp;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ibrahim
 */
public class LoginPasswordController implements Initializable {

    @FXML
    private Label passwordLabel;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginBtnID;
    @FXML
    private Button backButton;

    /**
     * Initializes the controller class.
     */
    Stage stage;
    String userPhone;

    public LoginPasswordController(Stage stage, String userPhone) {
        this.stage = stage;
        this.userPhone = userPhone;
    }

   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void passwordEntered(KeyEvent event) {
        logIn();
    }

    @FXML
    private void logInAction(ActionEvent event) {
        logIn();
    }

    @FXML
    private void backAction(ActionEvent event) {
        ChatApp clientApp = new ChatApp();
        clientApp.switchToLoginPhoneScreen(stage);
    }
    public void logIn(){
        ChatApp clientApp = new ChatApp();
        try {
            String userSession = clientApp.isRealUser(userPhone,passwordField.getText());
            clientApp.switchToUserScreen(stage,userSession);
        } catch (Exception ex) {
             Alert wrongPhone = new Alert(Alert.AlertType.ERROR);
            wrongPhone.setContentText("not Valid Password please enter right Password again");
            wrongPhone.show();
        }
    }
}
