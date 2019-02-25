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
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**
 *
 * @author Ibrahim
 */
public class GroupHbox extends ListCell<GroupDTO> {
    UsersService usersService;
    String session;
    HBox hBox = new HBox();
    Label groupName = new Label();
   // ImageView groupImage = new ImageView();
    Label friendName = new Label();
    Circle groupImage = new Circle(30);
    Color statusCircle = Color.GREENYELLOW;
    Pane pane1 = new Pane(); 
    Pane pane2 = new Pane();

    public GroupHbox(String session) {
        this.session = session;
        
//        groupImage.setFitHeight(60);
//        groupImage.setFitWidth(60);
                groupName.setStyle("-fx-font-style: oblique;-fx-font-size:18;");

        //hBox.getChildren().addAll(groupImage, groupName);
       pane1.setShape(new Rectangle(10, USE_PREF_SIZE));
        pane2.setShape(new Rectangle(20, USE_PREF_SIZE));
        hBox.getChildren().addAll(groupImage, pane1,groupName,pane2);
        hBox.setHgrow(pane1, Priority.ALWAYS);
        hBox.setHgrow(pane2, Priority.ALWAYS);
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
//                groupImage.setImage(image);
                groupImage.setFill(new ImagePattern(image));
                groupName.setText(group.getDisplayName()+"\n"+group.getLastMessage().getContent());
                groupImage.setStrokeType(StrokeType.OUTSIDE);

                setGraphic(hBox);
            } catch (RemoteException ex) {
                DialogUtils.showException(ex);
            }
        }
    }
    
}
