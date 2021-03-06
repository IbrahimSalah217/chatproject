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
import com.jets.chatproject.module.rmi.UsersService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Ibrahim
 */
public class LoginPasswordController implements Initializable {

    @FXML
    private AnchorPane PaneLoginPassword;
    @FXML
    private JFXButton loginBtnID;
    @FXML
    private JFXButton backButton;
    @FXML
    private Label passwordLabel;
    @FXML
    private JFXPasswordField passwordField;

    String userPhone;
    ScreenController screenController;

    UsersService userService;

    public LoginPasswordController(ScreenController screenController) {
        this.screenController = screenController;
        this.userPhone = screenController.getPhone();
        try {
            userService = ServiceLocator.getService(UsersService.class);
        } catch (Exception ex) {
            Logger.getLogger(LoginPasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        //screenController.switchToLoginPhoneScreen();
    }

    public void logIn() {
        try {
            String userSession = isRealUser(userPhone, passwordField.getText());
            userService.goOnline(userSession);
            screenController.saveSession(userSession, userPhone);
            registeredbyServer();
            screenController.switchToUSerProfileScreen();
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

    private void registeredbyServer() {
        try {
            System.out.println("enter");
            AuthService authService = ServiceLocator.getService(AuthService.class);
            if (authService.registerbyServer(userPhone)) {
                System.out.println("reach");
                getAlert("Registered by server", "You can change your password", Alert.AlertType.INFORMATION);
                screenController.switchToUpdateProfileScreen();
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginPasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getAlert(String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
