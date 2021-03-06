/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.controller;

import com.jets.chatproject.client.ChatApp;
import com.jets.chatproject.client.cfg.ServiceLocator;
import com.jets.chatproject.client.util.DialogUtils;
import com.jets.chatproject.client.views.addcontacts.AddContactsController;
import com.jets.chatproject.client.views.addgroups.AddgroupsController;
import com.jets.chatproject.client.views.login.LoginCheckPhoneController;
import com.jets.chatproject.client.views.login.LoginPasswordController;
import com.jets.chatproject.client.views.register.RegisterController;
import com.jets.chatproject.client.views.updateprofile.UpdateProfileFXMLController;
import com.jets.chatproject.client.views.userProfile.userProfileController;
import com.jets.chatproject.client.views.userscreen.UserScreenController;
import com.jets.chatproject.module.rmi.AuthService;
import com.jets.chatproject.module.rmi.UsersService;
import com.jets.chatproject.module.rmi.client.ClientCallback;
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
    private int id;

    public ScreenController(Stage stage) {
        this.stage = stage;
    }

    public void saveSession(String session, String phone) {
        this.session = session;
        this.phone = phone;
        try {
            if (session != null) {
                ClientCallback clientCallback
                        = ServiceLocator.getService(ClientCallback.class);
                AuthService authService
                        = ServiceLocator.getService(AuthService.class);
                authService.setCallBack(session,clientCallback);
                UsersService usersService
                        = ServiceLocator.getService(UsersService.class);
                this.id = usersService.getProfileByPhone(session, phone).getId();
            }
        } catch (Exception ex) {
            DialogUtils.showException(ex);
        }
    }

    public String getSession() {
        return session;
    }

    public String getPhone() {
        return phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void switchToLoginPhoneScreen() {
        FXMLLoader loader = new FXMLLoader();
        LoginCheckPhoneController controller = new LoginCheckPhoneController(this);
        loader.setController(controller);

        try {
            Parent root = loader.load(controller.getClass().getResource("loginCheckPhone.fxml").openStream());
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/loginCheckPhone.css").toExternalForm());
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
            scene.getStylesheets().add(getClass().getResource("/styles/loginPassword.css").toExternalForm());
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
            scene.getStylesheets().add(getClass().getResource("/styles/register.css").toExternalForm());
            stage.setTitle("ButterFly Chat Register");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ChatApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void switchToUserScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            UserScreenController controller = new UserScreenController(this);
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load(controller.getClass().getResource("userscreen.fxml").openStream());
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/userscreen.css").toExternalForm());
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
            scene.getStylesheets().add(getClass().getResource("/styles/AddContacts.css").toExternalForm());
            Stage contactsStage = new Stage();
            contactsStage.setScene(scene);
            contactsStage.show();
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
            scene.getStylesheets().add(getClass().getResource("/styles/updateProfileCSS.css").toExternalForm());
            Stage stage1 = new Stage();
            stage1.setScene(scene);
            stage1.show();
        } catch (IOException ex) {
            Logger.getLogger(ScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void switchToUSerProfileScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            userProfileController controller = new userProfileController(this);
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load(controller.getClass().getResource("userProfileFXML.fxml").openStream());
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/userProfileCSS.css").toExternalForm());
            stage.setScene(scene);
            stage.setMinWidth(1200);
            stage.setMinHeight(600);
            stage.show();
        } catch (IOException ex) {
            DialogUtils.showException(ex);
        }
    }
    public void switchToAddGroupsScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            AddgroupsController controller = new AddgroupsController(this);
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load(controller.getClass().getResource("addgroups.fxml").openStream());
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/addgroups.css").toExternalForm());
            Stage groupStage = new Stage();
            groupStage.setScene(scene);
            groupStage.show();
        } catch (IOException ex) {
            DialogUtils.showException(ex);
        }
    }
}
