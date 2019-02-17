/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.views.login;

import com.jets.chatproject.client.cfg.ServiceLocator;
import com.jets.chatproject.client.controller.ScreenController;
import com.jets.chatproject.client.util.DialogUtils;
import com.jets.chatproject.module.rmi.AuthService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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

    String userPhone;
    ScreenController screenController;

    public LoginPasswordController(ScreenController screenController) {
        this.screenController = screenController;
        this.userPhone = screenController.getPhone();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        screenController.switchToLoginPhoneScreen();
    }

    public void logIn() {
        try {
            String userSession = isRealUser(userPhone, passwordField.getText());
            screenController.saveSession(userSession, userPhone);
           // System.out.println(screenController.getSession()+"  "+screenController.getPhone());
            screenController.switchToUserProfileScreen();
        } catch (Exception ex) {
            DialogUtils.showException(ex);
        }
    }

    public String isRealUser(String phone, String password) throws Exception {
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
