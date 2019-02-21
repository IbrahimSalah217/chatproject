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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

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
    ImageView userImage = new ImageView();
    Circle statusCircle = new Circle(10);
    ToggleButton blockBtn =new ToggleButton();
    Rectangle toggleRect = new Rectangle(15, 15);
    Pane pane = new Pane();

    public ContactHbox(String session) {
        this.session = session;
        blockBtn.setShape(toggleRect);
        statusCircle.setRadius(10);
        statusCircle.setFill(Color.GREEN);
        userImage.setFitHeight(60);
        userImage.setFitWidth(60);
        hBox.getChildren().addAll(userImage, friendName,pane,blockBtn,statusCircle);
        hBox.setHgrow(pane, Priority.ALWAYS);
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
                blockBtn.setOnAction((event) -> {
                    try {
                        if(!friend.isBlocked())                       
                            friendService.blockFriend(session,friend.getFriendId());
                        else
                            friendService.unblockFriend(session,friend.getFriendId());
                    } catch (RemoteException ex) {
                        Logger.getLogger(ContactHbox.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                });
                Image image = new Image(new ByteArrayInputStream(usersService.getPicture(session, friend.getMemberPictureId())));
                userImage.setClip(new Circle(60));
                userImage.setImage(image);
                friendName.setText(friend.getFriendName()+"\n"+friend.getLastMessage().getContent());
                switch (friend.getFriendStatus()) {
                case AVAILABLE:
                    statusCircle.setFill(Color.GREEN);
                    break;
                case AWAY:
                    statusCircle.setFill(Color.RED);
                    break;
                case BUSY:
                    statusCircle.setFill(Color.YELLOW);
                    break;
                case OFFLINE:
                    statusCircle.setFill(Color.BLACK);
                    break;
            }
                setGraphic(hBox);
            } catch (RemoteException ex) {
                DialogUtils.showException(ex);
            }
        }
    }
}
