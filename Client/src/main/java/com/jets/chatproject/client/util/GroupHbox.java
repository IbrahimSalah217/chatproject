/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.util;

import com.jets.chatproject.client.cfg.ServiceLocator;
import com.jets.chatproject.module.rmi.UsersService;
import com.jets.chatproject.module.rmi.dto.GroupDTO;
import java.io.ByteArrayInputStream;
import java.rmi.RemoteException;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 *
 * @author Ibrahim
 */
public class GroupHbox extends ListCell<GroupDTO> {
    UsersService usersService;
    String session;
    HBox hBox = new HBox();
    Label groupName = new Label();
    ImageView groupImage = new ImageView();

    public GroupHbox(String session) {
        this.session = session;
        
        groupImage.setFitHeight(60);
        groupImage.setFitWidth(60);
        //Circle clip = new Circle(30);
        //userImage.setClip(clip);
        hBox.getChildren().addAll(groupImage, groupName);
        try {
            usersService = ServiceLocator.getService(UsersService.class);
        } catch (Exception ex) {
            DialogUtils.showException(ex);
        }
    }

    @Override
    protected void updateItem(GroupDTO group, boolean empty) {
        super.updateItem(group, empty);
        setText(null);  // No text in label of super class
        if (empty) {
            setGraphic(null);
        } else {
            try {
                Image image = new Image(new ByteArrayInputStream(usersService.getPicture(session, group.getPictureId())));
                groupImage.setImage(image);
                groupName.setText(group.getDisplayName()+"\n"+group.getLastMessage().getContent());
                setGraphic(hBox);
            } catch (RemoteException ex) {
                DialogUtils.showException(ex);
            }
        }
    }
    
}
