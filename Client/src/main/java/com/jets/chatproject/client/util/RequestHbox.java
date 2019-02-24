/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.util;

import com.jets.chatproject.client.cfg.ServiceLocator;
import com.jets.chatproject.client.views.userProfile.userProfileController;
import com.jets.chatproject.module.rmi.FriendRequestsService;
import com.jets.chatproject.module.rmi.UsersService;
import com.jets.chatproject.module.rmi.dto.RequestDTO;
import java.io.ByteArrayInputStream;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Ibrahim
 */
public class RequestHbox extends ListCell<RequestDTO> {

    UsersService usersService;
    FriendRequestsService requestService;
    String session;
    HBox hBox = new HBox();
    Label friendName = new Label();
    Circle userImage = new Circle(30);
    Button acceptBtn = new Button("Accept");
    Button rejectBtn = new Button("Reject");
    Pane pane1 = new Pane();
    Pane pane2 = new Pane();
    Pane pane3 = new Pane();

    VBox vBox = new VBox(pane1, pane2);
    userProfileController controller;

    public RequestHbox(String session, userProfileController controller) {
        this.session = session;
        this.controller = controller;
        acceptBtn.setShape(new Rectangle(60, 20));
        rejectBtn.setShape(new Rectangle(60, 20));

        pane1.setShape(new Rectangle(10, USE_PREF_SIZE));
        pane2.setShape(new Rectangle(10, USE_PREF_SIZE));
        pane3.setShape(new Rectangle(USE_PREF_SIZE, 5));
        //Circle clip = new Circle(30);
        //userImage.setClip(clip);
        hBox.getChildren().addAll(userImage, pane1, friendName, acceptBtn, pane2, rejectBtn);
        hBox.setHgrow(pane1, Priority.ALWAYS);
        hBox.setHgrow(pane2, Priority.ALWAYS);
        try {
            usersService = ServiceLocator.getService(UsersService.class);
            requestService = ServiceLocator.getService(FriendRequestsService.class);

        } catch (Exception ex) {
            DialogUtils.showException(ex);
        }
    }

    @Override
    protected void updateItem(RequestDTO request, boolean empty) {
        super.updateItem(request, empty);
        setText(null);  // No text in label of super class
        if (empty) {
            setGraphic(null);
        } else {
            try {
                Image image = new Image(new ByteArrayInputStream(usersService.getPicture(session, request.getSenderPictureId())));
                userImage.setFill(new ImagePattern(image));
                friendName.setText(request.getSenderName() + "\nSEND Request  ");
                System.out.println("com.jets.chatproject.client.util.RequestHbox.updateItem()");
                acceptBtn.setOnAction(e -> {
                    try {
                        requestService.acceptRequest(session, request.getSenderId());
                        controller.getRequestDTOs().remove(request);
                    } catch (RemoteException ex) {
                        Logger.getLogger(RequestHbox.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });
                rejectBtn.setOnAction(e -> {
                    try {
                        requestService.rejectRequest(session, request.getSenderId());
                        controller.getRequestDTOs().remove(request);

                    } catch (RemoteException ex) {
                        DialogUtils.showException(ex);
                    }
                });

                setGraphic(hBox);
            } catch (RemoteException ex) {
                DialogUtils.showException(ex);
            }
        }
    }

}
