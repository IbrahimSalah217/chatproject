/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.views.userscreen;

import com.jets.chatproject.client.cfg.ServiceLocator;
import com.jets.chatproject.client.controller.ScreenController;
import com.jets.chatproject.module.rmi.FriendshipService;
import com.jets.chatproject.module.rmi.dto.FriendshipDTO;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 *
 * @author ibrahim
 */
public class UserScreenController implements Initializable {

    @FXML
    ListView onlineList;
    @FXML
    ListView friendsList;
    @FXML
    Button moodButton;
    FriendshipService friendshipService;
    FriendshipDTO friendshipDTO;
    ObservableList<FriendshipDTO> myFriendsList;
    ScreenController screenController;
    String session;

    public UserScreenController(ScreenController screenController) {

        this.screenController = screenController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            friendshipService = ServiceLocator.getService(FriendshipService.class);
            session = screenController.getSession();
            List<FriendshipDTO> returnedFriendsList = friendshipService.getAllFriendships(session);
            myFriendsList = FXCollections.observableArrayList(returnedFriendsList);
            friendsList.getItems().addAll(myFriendsList);
            friendsList.setCellFactory((friend) -> 
            {
                return new ContactHbox(session);
            });

        } catch (Exception ex) {
            Logger.getLogger(UserScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
