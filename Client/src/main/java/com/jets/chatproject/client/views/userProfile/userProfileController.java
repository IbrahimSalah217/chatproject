/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.views.userProfile;

import com.jets.chatproject.client.cfg.ServiceLocator;
import com.jets.chatproject.client.controller.ScreenController;
import com.jets.chatproject.client.util.ContactHbox;
import com.jets.chatproject.client.util.DialogUtils;
import com.jets.chatproject.client.views.messages.MessagesController;
import com.jets.chatproject.module.rmi.FriendshipService;
import com.jets.chatproject.module.rmi.MessagesService;
import com.jets.chatproject.module.rmi.dto.FriendshipDTO;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author Ibrahim
 */
public class userProfileController implements Initializable {

    @FXML
    private AnchorPane addContactImage;
    @FXML
    private ImageView addContatact;
    @FXML
    private ImageView addGroupAction;
    @FXML
    private ImageView setting;
    @FXML
    private ImageView groupsBtn;
    @FXML
    private ImageView contactsBtn;
    @FXML
    private ImageView logoutLable;
    @FXML
    private ListView<FriendshipDTO> listMessages;
    @FXML
    private ImageView userImage;
    @FXML
    private Label userNameLable;
    @FXML
    private Circle statusCircle;
    @FXML
    private Pane switchPane;

    ScreenController screenController;
    MessagesService messageService;
    FriendshipService friendshipService;
    String userSession;
    String userPhone;
    FriendshipDTO friendshipDTO;
    ObservableList<FriendshipDTO> myFriendsList;

    public userProfileController(ScreenController screenController) {
        this.screenController = screenController;
        userSession = screenController.getSession();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            System.out.println("user profile " + screenController.getSession());
            friendshipService = ServiceLocator.getService(FriendshipService.class);
            friendshipService.getAllFriendships(userSession);
        } catch (Exception ex) {
            DialogUtils.showException(ex);
        }

        listMessages.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<FriendshipDTO>() {
                    @Override
                    public void changed(ObservableValue<? extends FriendshipDTO> observable, FriendshipDTO oldValue, FriendshipDTO newValue) {
                        showChatFor(newValue);
                    }
                });
    }

    private void showChatFor(FriendshipDTO friendshipDTO) {
        try {
            FXMLLoader loader = new FXMLLoader();
            MessagesController controller = new MessagesController(screenController,
                    MessagesController.ChatType.Direct, friendshipDTO.getFriendId());
            loader.setController(controller);
            Parent root = loader.load(controller.getClass()
                    .getResourceAsStream("Messages.fxml"));
            switchPane.getChildren().clear();
            switchPane.getChildren().add(root);
        } catch (IOException ex) {
            Logger.getLogger(userProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void addcontactAction(MouseEvent event) {
        screenController.switchToAddContactsScreen();
    }

    @FXML
    private void addGroupAction(MouseEvent event) {
        screenController.switchToAddGroupsScreen();
    }

    @FXML
    private void settingAction(MouseEvent event) {
    }

    @FXML
    private void groupsAction(MouseEvent event) {
    }

    @FXML
    private void contactsAction(MouseEvent event) {

        try {
            friendshipService = ServiceLocator.getService(FriendshipService.class);
            userSession = screenController.getSession();
            List<FriendshipDTO> returnedFriendsList = friendshipService.getAllFriendships(userSession);
            myFriendsList = FXCollections.observableArrayList(returnedFriendsList);
            listMessages.getItems().clear();
            listMessages.setItems(myFriendsList);
            listMessages.setCellFactory((param) -> {
                return new ContactHbox(userSession);
            });
        } catch (Exception ex) {
            DialogUtils.showException(ex);
        }
    }

    @FXML
    private void logoutLable(MouseEvent event) {
    }

    @FXML
    private void logoutAction(MouseEvent event) {
    }

    @FXML
    private void updateProfileLable(MouseEvent event) {
    }

    @FXML
    private void updateProfileAction(MouseEvent event) {
    }

    @FXML
    private void statusLable(MouseDragEvent event) {
    }

    @FXML
    private void statusAction(KeyEvent event) {
    }

    @FXML
    private void addContactsign(MouseEvent event) {
    }

}
