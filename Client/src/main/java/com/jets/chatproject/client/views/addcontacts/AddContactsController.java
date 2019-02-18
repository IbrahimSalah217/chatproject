/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.views.addcontacts;

import com.jets.chatproject.client.cfg.ServiceLocator;
import com.jets.chatproject.client.controller.ScreenController;
import com.jets.chatproject.client.util.DialogUtils;
import com.jets.chatproject.module.rmi.AuthService;
import com.jets.chatproject.module.rmi.FriendRequestsService;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ibrahim
 */
public class AddContactsController implements Initializable {

    @FXML
    private ListView<String> listView;

    @FXML
    private TextField phoneTextField;

    private ScreenController screenController;

    ObservableList<String> contactsToAdd;

    FriendRequestsService friendRequestsService;

    public AddContactsController(ScreenController screenController) {
        try {
            this.screenController = screenController;
            contactsToAdd = FXCollections.observableArrayList();
            friendRequestsService = ServiceLocator.getService(FriendRequestsService.class);
        } catch (Exception ex) {
            Logger.getLogger(AddContactsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listView.setFocusTraversable(false);
        Platform.runLater(() -> phoneTextField.requestFocus());
        listView.setItems(contactsToAdd);

    }

    @FXML
    private void checkPhone(ActionEvent ae) {
        try {
            AuthService authService = ServiceLocator.getService(AuthService.class);
            boolean isExist = authService.checkPhone(phoneTextField.getText().trim());
            if (isExist) {
                contactsToAdd.add(phoneTextField.getText().trim());
                phoneTextField.clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("User not fould");
                alert.setContentText("Unfortunately this user is not registered");
                alert.showAndWait();
            }
        } catch (Exception ex) {
            DialogUtils.showException(ex);
        }
    }

    @FXML
    private void addAll(ActionEvent ae) {
        contactsToAdd.stream().forEach(
                (s) -> {
                    try {
                        friendRequestsService.sendRequest(screenController.getSession(), s);
                    } catch (RemoteException ex) {
                        DialogUtils.showException(ex);
                    }
                });
        listView.getScene().getWindow().hide();
    }

}
