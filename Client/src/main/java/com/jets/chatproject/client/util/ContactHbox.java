/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.util;

import com.jets.chatproject.client.cfg.ServiceLocator;
import com.jets.chatproject.module.rmi.FriendshipService;
import com.jets.chatproject.module.rmi.UsersService;
import com.jets.chatproject.module.rmi.dto.FriendshipDTO;
import java.io.ByteArrayInputStream;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**
 *
 * @author salma
 */
public class ContactHbox extends ListCell<FriendshipDTO> {

    UsersService usersService;
    FriendshipService friendService;
    String session;
    HBox hBox = new HBox();
    Label friendName = new Label();
    Circle userImage = new Circle(30);
    Color statusCircle;
    ToggleButton blockBtn = new ToggleButton();

    Rectangle toggleRect = new Rectangle(20, 20);
    Rectangle toggleRect2 = new Rectangle(20, 20);
    VBox vbox = new VBox(toggleRect, toggleRect2);
    RadioButton r = new RadioButton();

    Pane pane1 = new Pane();
    Pane pane2 = new Pane();

    public ContactHbox(String session) {
        this.session = session;
        friendName.setStyle("-fx-font-style: oblique;-fx-font-size:18;");
        //blockBtn.setShape(toggleRect);
        toggleRect.setFill(new ImagePattern(new Image(getClass().getResource("/images/block.png").toString())));
        toggleRect2.setFill(new ImagePattern(new Image(getClass().getResource("/images/unblock2.png").toString())));
        pane1.setShape(new Rectangle(10, USE_PREF_SIZE));
        pane2.setShape(new Rectangle(20, USE_PREF_SIZE));
        hBox.getChildren().addAll(userImage, pane1, friendName, pane2, vbox);
        hBox.setHgrow(pane1, Priority.ALWAYS);
        hBox.setHgrow(pane2, Priority.ALWAYS);

        try {
            usersService = ServiceLocator.getService(UsersService.class);
            friendService = ServiceLocator.getService(FriendshipService.class);
        } catch (Exception ex) {
            DialogUtils.showException(ex);
        }
    }

    @Override
    protected void updateItem(FriendshipDTO friend, boolean empty) {
        super.updateItem(friend, empty);
        setText(null);  // No text in label of super class
        if (empty) {
            setGraphic(null);
        } else {
            try {
                if (friend.isBlocked()) {
                    toggleRect.setVisible(false);
                    hBox.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));
                    //hBox.setDisable(true);
                } else {
                    toggleRect2.setVisible(false);
                    hBox.setBorder(Border.EMPTY);
                    //hBox.setDisable(false);
                }
                toggleRect.setOnMousePressed((event) -> {
                    try {
                        // if(!friend.isBlocked())                       
                        friendService.blockFriend(session, friend.getFriendId());
                        toggleRect.setVisible(false);
                        toggleRect2.setVisible(true);
                        //hBox.setDisable(true);
                        hBox.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));
                    } catch (RemoteException ex) {
                        DialogUtils.showException(ex);
                    }
                });
                toggleRect2.setOnMousePressed((event) -> {
                    try {
                        friendService.unblockFriend(session, friend.getFriendId());
                        toggleRect2.setVisible(false);
                        toggleRect.setVisible(true);
                        hBox.setBorder(Border.EMPTY);
                        //hBox.setDisable(false);
                    } catch (RemoteException ex) {
                        DialogUtils.showException(ex);
                    }
                });

                Image image = new Image(new ByteArrayInputStream(usersService.getPicture(session, friend.getMemberPictureId())));
                userImage.setFill(new ImagePattern(image));
                friendName.setText(friend.getFriendName() + "\n" + friend.getLastMessage().getContent());
                switch (friend.getFriendStatus()) {
                    case AVAILABLE:
                        statusCircle = Color.GREENYELLOW;
                        break;
                    case AWAY:
                        statusCircle = Color.RED;
                        break;
                    case BUSY:
                        statusCircle = Color.YELLOW;
                        break;
                    case OFFLINE:
                        statusCircle = Color.BLACK;
                        break;
                }
                userImage.setStroke(statusCircle);
                userImage.setStrokeWidth(2);
                userImage.setStrokeType(StrokeType.OUTSIDE);
                setGraphic(hBox);
            } catch (RemoteException ex) {
                DialogUtils.showException(ex);
            }
        }
    }
}
