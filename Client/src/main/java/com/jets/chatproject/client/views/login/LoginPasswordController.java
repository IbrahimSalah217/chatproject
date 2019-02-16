/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.views.login;

import com.jets.chatproject.client.ChatApp;
import com.jets.chatproject.client.util.DialogUtils;
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
import javafx.scene.input.KeyCode;
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
    String userPhone;
    ChatApp chatApp;
    
    public LoginPasswordController(ChatApp chatApp,String userPhone) {
        this.userPhone = userPhone;
        this.chatApp = chatApp;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void passwordEntered(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            logIn();
        }
    }
    
    @FXML
    private void logInAction(ActionEvent event) {
        logIn();
    }
    
    @FXML
    private void backAction(ActionEvent event) {
        chatApp.switchToLoginPhoneScreen();
    }
    
    public void logIn() {
        try {
            String userSession = chatApp.isRealUser(userPhone, passwordField.getText());
            //chatApp.switchToUserScreen(userSession);
        } catch (Exception ex) {
            DialogUtils.showException(ex);
        }
    }
}
