/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.util;

import com.jets.chatproject.client.cfg.ServiceLocator;
import com.jets.chatproject.module.rmi.UsersService;
import com.jets.chatproject.module.rmi.dto.FriendshipDTO;
import java.io.ByteArrayInputStream;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 *
 * @author salma
 */
public class ContactHbox extends ListCell<FriendshipDTO> {

    UsersService usersService;
    String session;
    HBox hBox = new HBox();
    Label friendName = new Label();
    ImageView userImage = new ImageView();

    public ContactHbox(String session) {
        this.session = session;
        
        userImage.setFitHeight(60);
        userImage.setFitWidth(60);
        //Circle clip = new Circle(30);
        //userImage.setClip(clip);
        hBox.getChildren().addAll(userImage, friendName);
        try {
            usersService = ServiceLocator.getService(UsersService.class);
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
                Image image = new Image(new ByteArrayInputStream(usersService.getPicture(session, friend.getMemberPictureId())));
                userImage.setImage(image);
                friendName.setText(friend.getFriendName()+"\n"+friend.getLastMessage().getContent());
                setGraphic(hBox);
            } catch (RemoteException ex) {
                Logger.getLogger(ContactHbox.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
