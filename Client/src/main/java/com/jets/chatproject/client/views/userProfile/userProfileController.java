/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.views.userProfile;

import com.jets.chatproject.client.ClientCallbackImp;
import com.jets.chatproject.client.cfg.ServiceLocator;
import com.jets.chatproject.client.controller.ScreenController;
import com.jets.chatproject.client.util.ContactHbox;
import com.jets.chatproject.client.util.DialogUtils;
import com.jets.chatproject.client.util.GroupHbox;
import com.jets.chatproject.client.util.RequestHbox;
import com.jets.chatproject.client.views.messages.MessagesController;
import com.jets.chatproject.module.rmi.AuthService;
import com.jets.chatproject.module.rmi.FriendRequestsService;
import com.jets.chatproject.module.rmi.FriendshipService;
import com.jets.chatproject.module.rmi.GroupsService;
import com.jets.chatproject.module.rmi.MessagesService;
import com.jets.chatproject.module.rmi.UsersService;
import com.jets.chatproject.module.rmi.dto.FriendshipDTO;
import com.jets.chatproject.module.rmi.dto.GroupDTO;
import com.jets.chatproject.module.rmi.dto.MessageDTO;
import com.jets.chatproject.module.rmi.dto.RequestDTO;
import com.jets.chatproject.module.rmi.dto.UserDTO;
import com.jets.chatproject.module.rmi.dto.UserStatus;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.geometry.Pos;
import javafx.scene.AccessibleAction;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import org.controlsfx.control.Notifications;

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
    private Circle userImage;
    @FXML
    private Label userNameLable;
    @FXML
    private Circle statusCircle;
    @FXML
    private BorderPane borderPane;
    @FXML
    private ListView<FriendshipDTO> friendsCList;
    @FXML
    private ListView<FriendshipDTO> familyCList;
    @FXML
    private ListView<FriendshipDTO> workCList;

    ScreenController screenController;
    MessagesService messageService;
    GroupsService groupsService;
    GroupDTO groupDto;
    FriendshipService friendshipService;
    FriendRequestsService requestsService;
    UsersService userService;
    AuthService authService;
    String userSession;
    String userPhone;
    FriendshipDTO friendshipDTO;
    ObservableList<FriendshipDTO> myFriendsList;
    ObservableList<FriendshipDTO> categoryFriendsList;
    ObservableList<GroupDTO> myGroupsList;
    ObservableList<RequestDTO> myRequestsList;
    UserDTO userDto;
    UserStatus userStatus;
    Color userColor;
    String statusTip;
    BooleanProperty friendStatusUpdated = new SimpleBooleanProperty(true);

    //Tooltip circleTip = new Tooltip("update Status");
    public userProfileController(ScreenController screenController) {
        this.screenController = screenController;
        userSession = screenController.getSession();
        userPhone = screenController.getPhone();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            requestsService = ServiceLocator.getService(FriendRequestsService.class);
            userService = ServiceLocator.getService(UsersService.class);
            friendshipService = ServiceLocator.getService(FriendshipService.class);
            authService = ServiceLocator.getService(AuthService.class);
            friendshipService.getAllFriendships(userSession);
            groupsService = ServiceLocator.getService(GroupsService.class);
            userDto = userService.getProfileByPhone(userSession, userPhone);
            byte[] storedImage = userService.getPicture(userSession, userDto.getPictureId());
            //userImage.setImage(new Image(new ByteArrayInputStream(storedImage)));
            userImage.setFill(new ImagePattern(new Image(new ByteArrayInputStream(storedImage))));

            userNameLable.setText(userDto.getDisplyName());
            userStatus = userService.getStatus(userSession, -1);
        } catch (Exception ex) {
            DialogUtils.showException(ex);
        }

        Tooltip.install(userImage, new Tooltip("Update profile"));
        Tooltip.install(contactsBtn, new Tooltip("conatcts"));
        Tooltip.install(groupsBtn, new Tooltip("groups"));
        Tooltip.install(setting, new Tooltip("setting"));
        Tooltip.install(addContactImage, new Tooltip("add contact"));
        Tooltip.install(addGroupAction, new Tooltip("create group"));
        Tooltip.install(logoutLable, new Tooltip("log Out"));
//        Platform.runLater(() -> {
//            Tooltip.install(statusCircle, circleTip);
//        });
        switch (userStatus) {
            case AVAILABLE:
                userColor = Color.GREENYELLOW;
                statusTip = "Available";
                break;
            case AWAY:
                userColor = Color.RED;
                statusTip = "Away";
                break;
            case BUSY:
                userColor = Color.YELLOW;
                statusTip = "Busy";
                break;
            case OFFLINE:
                userColor = Color.BLACK;
                statusTip = "Offline";
                break;
        }
        statusCircle.setFill(userColor);
        userImage.setStrokeWidth(3);
        userImage.setStrokeType(StrokeType.OUTSIDE);
        userImage.setStroke(userColor);
        //Thread th = new Thread(() -> {
        //circleTip.setText(statusTip);

        //th.start();
        try {
            listMessages.setVisible(true);
            listGroups.setVisible(false);
            listRequests.setVisible(false);
            friendshipService = ServiceLocator.getService(FriendshipService.class);
            userSession = screenController.getSession();
            updateListMessages();
            updateCategories("family");
            updateCategories("friends");
            updateCategories("work");
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
        listGroups.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends GroupDTO> observable,
                        GroupDTO oldValue, GroupDTO newValue) -> {
                    if (newValue != null) {
                        showChatFor(newValue);
                    }
                });

        ClientCallbackImp.getInstance().addFriendListener(new ClientCallbackImp.FriendListener() {
            @Override
            public void onFiendStatusUpdated(int friendId, UserStatus friendStatus) {
                Platform.runLater(() -> {
                    System.out.println(".onFiendStatusUpdated()");
                    try {
                        Notifications.create()
                                .title("status updated")
                                .text(userService.getProfileById(userSession, friendId).getDisplyName() + " Become " + friendStatus)
                                .position(Pos.BASELINE_RIGHT).darkStyle()
                                .show();
                        AudioClip audioClip = new AudioClip(getClass().getResource("/sounds/Slack - Knock brush.mp3").toString());
                        audioClip.play();
                        updateListMessages();
                        updateCategories("family");
                        updateCategories("friends");
                        updateCategories("work");
                    } catch (RemoteException ex) {
                        DialogUtils.showException(ex);
                    }
                });
                System.out.println(".onFiendStatusUpdated()");
            }

            @Override
            public void onFriendBlockedME(int friendId) {
                Platform.runLater(() -> {
                    System.out.println(".onFriendBlockedME()");
                    try {
                        Notifications.create()
                                .title("frindship updated")
                                .text(userService.getProfileById(userSession, friendId).getDisplyName() + " BlockedYou ")
                                .position(Pos.BASELINE_RIGHT).darkStyle()
                                .show();
                        AudioClip audioClip = new AudioClip(getClass().getResource("/sounds/Slack - Knock brush.mp3").toString());
                        audioClip.play();
                        updateListMessages();
                        updateCategories("family");
                        updateCategories("friends");
                        updateCategories("work");
                    } catch (RemoteException ex) {
                        DialogUtils.showException(ex);
                    }
                });
                System.out.println(".onFriendBlockedME()");
            }

            @Override
            public void onFriendUnBlockedME(int friendId) {
                Platform.runLater(() -> {
                    System.out.println(".onFriendUnBlockedME()");
                    try {
                        Notifications.create()
                                .title("frindship updated")
                                .text(userService.getProfileById(userSession, friendId).getDisplyName() + "UnBlocked You ")
                                .position(Pos.BASELINE_RIGHT).darkStyle()
                                .show();
                        AudioClip audioClip = new AudioClip(getClass().getResource("/sounds/Slack - Knock brush.mp3").toString());
                        audioClip.play();
                        friendStatusUpdated.set(!friendStatusUpdated.get());
                        List<FriendshipDTO> returnedFriendsList = friendshipService.getAllFriendships(userSession);
                        myFriendsList = FXCollections.observableArrayList(returnedFriendsList);
                        listMessages.getItems().clear();
                        listMessages.setItems(myFriendsList);
                        listMessages.setCellFactory((param) -> {
                            return new ContactHbox(userSession);
                        });
                    } catch (RemoteException ex) {
                        Logger.getLogger(userProfileController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                System.out.println(".onFriendUnBlockedME()");
            }

            @Override
            public void onFriendSendRequest(int friendId) {
                Platform.runLater(() -> {
                    System.out.println(".onFriendSendRequest()");
                    try {
                        Notifications.create()
                                .title("frindship Request")
                                .text(userService.getProfileById(userSession, friendId).getDisplyName() + "Send You Friend Request")
                                .position(Pos.BASELINE_RIGHT).darkStyle()
                                .show();
                        AudioClip audioClip = new AudioClip(getClass().getResource("/sounds/Slack - Knock brush.mp3").toString());
                        audioClip.play();
                        updateListMessages();
                        updateCategories("family");
                        updateCategories("friends");
                        updateCategories("work");
                    } catch (RemoteException ex) {
                        DialogUtils.showException(ex);
                    }
                });
                System.out.println(".onFriendSendRequest()");
            }
        });

        ClientCallbackImp.getInstance().addMessageListener(new ClientCallbackImp.MessageListener() {

            @Override
            public void onDirectMessageReceived(int friendId, MessageDTO message) {
                Platform.runLater(() -> {
                    Notifications.create()
                            .title(message.getSenderName())
                            .text(message.getContent())
                            .position(Pos.TOP_RIGHT)
                            .show();
                    AudioClip audioClip = new AudioClip(getClass().getResource("/sounds/Slack - Knock brush.mp3").toString());
                    audioClip.play();
                });
            }

            @Override
            public void onGroupMessageReceived(int groupId, MessageDTO message) {
                Platform.runLater(() -> {
                    Notifications.create()
                            .title(message.getSenderName())
                            .text(message.getContent())
                            .position(Pos.TOP_RIGHT)
                            .show();
                    AudioClip audioClip = new AudioClip(getClass().getResource("/sounds/Slack - Knock brush.mp3").toString());
                    audioClip.play();
                });
            }

            @Override
            public void onServerMessageReceived(String message) {
                Platform.runLater(() -> {
                    AudioClip audioClip = new AudioClip(getClass().getResource("/sounds/Slack - Knock brush.mp3").toString());
                    audioClip.play();
                });
            }
        /*          Salah         */

            @Override
            public void onVoiceRecordRecieve(int friendId, byte[] arrayVoice) {
                Platform.runLater(() -> {
                    try {
                        System.out.println(".onVoiceRecordRecieve()");
                        Notifications.create()
                                .title("VOICE RECORD")
                                .text(userService.getProfileById(userSession, friendId).getDisplyName() + " Send Voice record")
                                .position(Pos.TOP_RIGHT)
                                .show();
                        AudioClip audioClip = new AudioClip(getClass().getResource("/sounds/Slack - Knock brush.mp3").toString());
                        audioClip.play();
                    } catch (RemoteException ex) {
                        DialogUtils.showException(ex);
                    }
                    System.out.println(".onVoiceRecordRecieve()");
                });
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

    private void showChatFor(GroupDTO groupDTO) {
        try {
            FXMLLoader loader = new FXMLLoader();
            MessagesController controller = new MessagesController(screenController,
                    MessagesController.ChatType.Group, groupDTO.getId());
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
        listMessages.setVisible(true);
        listGroups.setVisible(false);
        listRequests.setVisible(false);
        updateListMessages();
        updateCategories("family");
        updateCategories("friends");
        updateCategories("work");

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
            listRequests.setCellFactory((param) -> {
                return new RequestHbox(userSession, this);
            });
        } catch (RemoteException ex) {
            DialogUtils.showException(ex);
        }
    }

    public ObservableList<RequestDTO> getRequestDTOs() {
        return myRequestsList;
    }

    @FXML
    private void logoutLable(MouseEvent event) {

    }

    @FXML
    private void logoutAction(MouseEvent event) {
        try {
            userService.goOffline(userSession);
            authService.logout(userSession);
            screenController.switchToLoginPhoneScreen();
        } catch (RemoteException ex) {
            DialogUtils.showException(ex);
        }

    }

    @FXML
    private void updateProfileAction(MouseEvent event) {
        screenController.switchToUpdateProfileScreen();
    }

    @FXML
    private void statusLable(MouseDragEvent event) {
    }

    @FXML
    private void statusAction(MouseEvent event) {
        Thread th = new Thread(() -> {
            Platform.runLater(() -> {
                try {
                    if (userColor.equals(Color.GREENYELLOW)) {
                        userStatus = UserStatus.BUSY;
                        userColor = Color.YELLOW;
                        statusTip = "Busy";
                    } else if (userColor.equals(Color.YELLOW)) {
                        userStatus = UserStatus.AWAY;
                        userColor = Color.RED;
                        statusTip = "Away";
                    } else if (userColor.equals(Color.RED)) {
                        userStatus = UserStatus.OFFLINE;
                        userColor = Color.BLACK;
                        statusTip = "Offline";
                    } else if (userColor.equals(Color.BLACK)) {
                        userStatus = UserStatus.AVAILABLE;
                        userColor = Color.GREENYELLOW;
                        statusTip = "Available";
                    }
                    userImage.setStroke(userColor);
                    statusCircle.setFill(userColor);
                    userService.updateStatus(userSession, userStatus);
                    //circleTip.setText(statusTip);
//                    Tooltip.install(statusCircle, new Tooltip(statusTip));
                } catch (RemoteException ex) {
                    DialogUtils.showException(ex);
                }
            });
        });
        th.start();
    }

    @FXML
    private void addContactsign(MouseEvent event) {

    }

    private void updateListMessages() {
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

    private void updateCategories(String category) {
        try {
            friendshipService = ServiceLocator.getService(FriendshipService.class);
            userSession = screenController.getSession();
            List<FriendshipDTO> returnedFriendsList = friendshipService.getAllFriendships(userSession);
            returnedFriendsList.removeIf((friend) -> {
                return !friend.getCategory().equalsIgnoreCase(category); //To change body of generated lambdas, choose Tools | Templates.
            });
//            .forEach((friend) -> {
//                if (!friend.getCategory().equalsIgnoreCase(category)) {
//                    returnedFriendsList.remove(friend);
//                }
//            });
            categoryFriendsList = FXCollections.observableArrayList(returnedFriendsList);
            if (category.equalsIgnoreCase("friends")) {
                friendsCList.getItems().clear();
                friendsCList.setItems(categoryFriendsList);
                friendsCList.setCellFactory((param) -> {
                    return new ContactHbox(userSession);
                });
            } else if (category.equalsIgnoreCase("family")) {
                familyCList.getItems().clear();
                familyCList.setItems(categoryFriendsList);
                familyCList.setCellFactory((param) -> {
                    return new ContactHbox(userSession);
                });
            } else if (category.equalsIgnoreCase("work")) {
                workCList.getItems().clear();
                workCList.setItems(categoryFriendsList);
                workCList.setCellFactory((param) -> {
                    return new ContactHbox(userSession);
                });
            }
        } catch (Exception ex) {
            DialogUtils.showException(ex);
        }

    }
}
