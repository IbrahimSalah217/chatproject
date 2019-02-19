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
import com.jets.chatproject.client.util.GroupHbox;
import com.jets.chatproject.client.util.RequestHbox;
import com.jets.chatproject.client.views.messages.MessagesController;
import com.jets.chatproject.module.rmi.FriendRequestsService;
import com.jets.chatproject.module.rmi.FriendshipService;
import com.jets.chatproject.module.rmi.GroupsService;
import com.jets.chatproject.module.rmi.MessagesService;
import com.jets.chatproject.module.rmi.dto.FriendshipDTO;
import com.jets.chatproject.module.rmi.dto.GroupDTO;
import com.jets.chatproject.module.rmi.dto.RequestDTO;
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
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
    private ImageView requestsView;
    @FXML
    private ListView<FriendshipDTO> listMessages;
    @FXML
    private ListView<GroupDTO> listGroups;
    @FXML
    private ListView<RequestDTO> listRequests;

    @FXML
    private ImageView userImage;
    @FXML
    private Label userNameLable;
    @FXML
    private Circle statusCircle;
    @FXML
    private BorderPane borderPane;

    ScreenController screenController;
    MessagesService messageService;
    GroupsService groupsService;
    GroupDTO groupDto;
    FriendshipService friendshipService;
    FriendRequestsService requestsService;
    String userSession;
    String userPhone;
    FriendshipDTO friendshipDTO;
    ObservableList<FriendshipDTO> myFriendsList;
    ObservableList<GroupDTO> myGroupsList;
    ObservableList<RequestDTO> myRequestsList;

    public userProfileController(ScreenController screenController) {
        this.screenController = screenController;
        userSession = screenController.getSession();
        userPhone = screenController.getPhone();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Tooltip.install(userImage, new Tooltip("Update profile"));
        Tooltip.install(contactsBtn, new Tooltip("conatcts"));
        Tooltip.install(groupsBtn, new Tooltip("groups"));
        Tooltip.install(setting, new Tooltip("setting"));
        Tooltip.install(addContactImage, new Tooltip("add contact"));
        Tooltip.install(addGroupAction, new Tooltip("create group"));
        Tooltip.install(logoutLable, new Tooltip("log Out"));

        try {
            friendshipService = ServiceLocator.getService(FriendshipService.class);
            friendshipService.getAllFriendships(userSession);
        } catch (Exception ex) {
            DialogUtils.showException(ex);
        }
        try {
            groupsService = ServiceLocator.getService(GroupsService.class);
        } catch (Exception ex) {
            DialogUtils.showException(ex);
        }
        try {
            requestsService = ServiceLocator.getService(FriendRequestsService.class);
        } catch (Exception ex) {
            DialogUtils.showException(ex);
        }

        listMessages.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<FriendshipDTO>() {
                    @Override
                    public void changed(ObservableValue<? extends FriendshipDTO> observable, FriendshipDTO oldValue, FriendshipDTO newValue) {
                        if (newValue != null) {
                            showChatFor(newValue);
                        }
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
            borderPane.setCenter(root);
        } catch (IOException ex) {
            DialogUtils.showException(ex);
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
        try {
            listGroups.setVisible(true);
            listMessages.setVisible(false);
            listRequests.setVisible(false);
            List<GroupDTO> returnedGroups = groupsService.getAllGroups(userSession);
            myGroupsList = FXCollections.observableArrayList(returnedGroups);
            listGroups.getItems().clear();
            listGroups.setItems(myGroupsList);
            listGroups.setCellFactory((param) -> {
                return new GroupHbox(userSession);
            });
        } catch (RemoteException ex) {
            DialogUtils.showException(ex);
        }
    }

    @FXML
    private void contactsAction(MouseEvent event) {

        try {
            listMessages.setVisible(true);
            listGroups.setVisible(false);
            listRequests.setVisible(false);
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
    private void requestsViewAction(MouseEvent event) {
        
        listRequests.setVisible(true);
        listMessages.setVisible(false);
        listGroups.setVisible(false);
        try {
            List<RequestDTO> returnedRequests = requestsService.getAllRequests(userSession);
            myRequestsList = FXCollections.observableArrayList(returnedRequests);
            listRequests.getItems().clear();
            listRequests.setItems(myRequestsList);
            System.out.println(returnedRequests.get(0).getSenderName());
            listRequests.setCellFactory((param) -> {
                System.out.println("com.jets.chatproject.client.views.userProfile.userProfileController.requestsViewAction()");
                return new RequestHbox(userSession);
                
            });
            
        } catch (RemoteException ex) {
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
        screenController.switchToUpdateProfileScreen();
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
