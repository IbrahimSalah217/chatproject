/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.views.messages;

import com.jets.chatproject.module.rmi.dto.MessageDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class MessageBubble extends HBox {

    private final Color DEFAULT_SENDER_COLOR = Color.GOLD;
    private final Color DEFAULT_RECEIVER_COLOR = Color.LIMEGREEN;
    private Background DEFAULT_SENDER_BACKGROUND, DEFAULT_RECEIVER_BACKGROUND;

    private final MessageDTO message;
    private final SpeechDirection direction;

    private Label displayedText;
    private SVGPath directionIndicator;

    public MessageBubble(MessageDTO message, SpeechDirection direction) {
        this.message = message;
        this.direction = direction;
        initialiseDefaults();
        setupElements();
    }

    private void initialiseDefaults() {
        DEFAULT_SENDER_BACKGROUND = new Background(
                new BackgroundFill(DEFAULT_SENDER_COLOR, new CornerRadii(5, 0, 5, 5, false), Insets.EMPTY));
        DEFAULT_RECEIVER_BACKGROUND = new Background(
                new BackgroundFill(DEFAULT_RECEIVER_COLOR, new CornerRadii(0, 5, 5, 5, false), Insets.EMPTY));
    }

    private void setupElements() {
        displayedText = new Label(message.getContent());
        displayedText.setPadding(new Insets(5));
        displayedText.setWrapText(true);
        directionIndicator = new SVGPath();

        if (direction == SpeechDirection.LEFT) {
            configureForReceiver();
        } else {
            configureForSender();
        }
    }

    private void configureForSender() {
        displayedText.setBackground(DEFAULT_SENDER_BACKGROUND);
        displayedText.setAlignment(Pos.CENTER_RIGHT);
        directionIndicator.setContent("M10 0 L0 10 L0 0 Z");
        directionIndicator.setFill(DEFAULT_SENDER_COLOR);

        HBox container = new HBox(displayedText, directionIndicator);
        Label sender = new Label(message.getSenderName());
        sender.setMaxWidth(Double.MAX_VALUE);
        sender.setAlignment(Pos.CENTER_RIGHT);
        container.setMaxWidth(Double.MAX_VALUE);
        container.setAlignment(Pos.CENTER_RIGHT);
        VBox con = new VBox(sender, container);
        //Use at most 75% of the width provided to the MessageBubble for displaying the message
        container.maxWidthProperty().bind(widthProperty().multiply(0.75));
        container.prefHeightProperty().bind(displayedText.widthProperty());
        getChildren().setAll(con);
        setAlignment(Pos.CENTER_RIGHT);
    }

    private void configureForReceiver() {
        displayedText.setBackground(DEFAULT_RECEIVER_BACKGROUND);
        displayedText.setAlignment(Pos.CENTER_LEFT);
        directionIndicator.setContent("M0 0 L10 0 L10 10 Z");
        directionIndicator.setFill(DEFAULT_RECEIVER_COLOR);

        HBox container = new HBox(directionIndicator, displayedText);
        Label sender = new Label(message.getSenderName());
        sender.setMaxWidth(Double.MAX_VALUE);
        sender.setAlignment(Pos.CENTER_LEFT);
        container.setMaxWidth(Double.MAX_VALUE);
        container.setAlignment(Pos.CENTER_LEFT);
        VBox con = new VBox(sender, container);
        //Use at most 75% of the width provided to the MessageBubble for displaying the message
        container.maxWidthProperty().bind(widthProperty().multiply(0.75));
        container.prefHeightProperty().bind(displayedText.widthProperty());
        getChildren().setAll(con);
        setAlignment(Pos.CENTER_LEFT);
    }

    public enum SpeechDirection {
        LEFT, RIGHT
    }
}
