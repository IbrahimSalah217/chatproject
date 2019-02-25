/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.views.messages;

import com.jets.chatproject.module.rmi.dto.MessageDTO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class MessageBubble extends ListCell<MessageDTO> {

    private final int currentUserId;
    private final PictureResolver pictureResolver;

    MessageBubble(int currentUserId, PictureResolver pictureResolver) {
        this.currentUserId = currentUserId;
        this.pictureResolver = pictureResolver;
    }

    @Override
    protected void updateItem(MessageDTO message, boolean empty) {
        if (message != null && !empty) {
            try {
                Parent root;
                if (message.getSenderId() == currentUserId) {
                    root = FXMLLoader.load(getClass().getResource("MessageRight.fxml"));
                } else {
                    root = FXMLLoader.load(getClass().getResource("MessageLeft.fxml"));
                }
                Circle senderPicture = (Circle) root.lookup("#senderPicture");
                Label senderName = (Label) root.lookup("#senderName");
                Label messageBody = (Label) root.lookup("#messageBody");
                byte[] profilePicture
                        = pictureResolver.getProfilePicture(message.getSenderId());
                if (profilePicture != null) {
                    senderPicture.setFill(new ImagePattern(new Image(
                            new ByteArrayInputStream(profilePicture))));
                }
                senderName.setText(message.getSenderName());
                messageBody.setText(message.getContent());

                setGraphic(root);
            } catch (IOException ex) {
                Logger.getLogger(MessageBubble.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            setGraphic(null);
        }
    }

    interface PictureResolver {

        byte[] getProfilePicture(int userId);
    }
}
