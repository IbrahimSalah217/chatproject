/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.controller;

import com.jets.chatproject.client.ChatApp;
import com.jets.chatproject.client.views.addcontacts.AddContactsController;
import com.jets.chatproject.client.views.login.LoginCheckPhoneController;
import com.jets.chatproject.client.views.login.LoginPasswordController;
import com.jets.chatproject.client.views.register.RegisterController;
import com.jets.chatproject.client.views.updateprofile.UpdateProfileFXMLController;
import com.jets.chatproject.client.views.userscreen.UserScreenController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ibrahim
 */
public class ScreenController {

    private final Stage stage;

    private String session;
    private String phone;

    public ScreenController(Stage stage) {
        this.stage = stage;
    }

    public void saveSession(String session, String phone) {
        this.session = session;
        this.phone = phone;
    }

    public String getSession() {
        return session;
    }

    public String getPhone() {
        return phone;
    }

    public void switchToLoginPhoneScreen() {
        FXMLLoader loader = new FXMLLoader();
        LoginCheckPhoneController controller = new LoginCheckPhoneController(this);
        loader.setController(controller);

        try {
            Parent root = loader.load(controller.getClass().getResource("loginCheckPhone.fxml").openStream());
            Scene scene = new Scene(root);
            stage.setTitle("ButterFly Chat logIn Phone");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ChatApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void switchToLoginPasswordScreen() {
        FXMLLoader loader = new FXMLLoader();
        LoginPasswordController controller = new LoginPasswordController(this);
        loader.setController(controller);

        try {
            Parent root = loader.load(controller.getClass().getResource("loginPassword.fxml").openStream());
            Scene scene = new Scene(root);
            stage.setTitle("ButterFly Chat logIn Password");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ChatApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void switchToRegisterScreen() {
        FXMLLoader loader = new FXMLLoader();
        RegisterController controller = new RegisterController(this);
        loader.setController(controller);

        try {
            Parent root = loader.load(controller.getClass().getResource("register.fxml").openStream());
            Scene scene = new Scene(root);
            stage.setTitle("ButterFly Chat Register");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ChatApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void switchToUserScreen() {
        switchToUpdateProfileScreen();
        if (1 < 10) {
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            UserScreenController controller = new UserScreenController(this);
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load(controller.getClass().getResource("userscreen.fxml").openStream());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void switchToAddContactsScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            AddContactsController controller = new AddContactsController(this);
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load(controller.getClass().getResource("AddContacts.fxml").openStream());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void switchToUpdateProfileScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            UpdateProfileFXMLController controller = new UpdateProfileFXMLController(this);
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load(controller.getClass().getResource("updateProfileFXML.fxml").openStream());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
