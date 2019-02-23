/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.views.messages;

import com.jets.chatproject.client.ClientCallbackImp;
import com.jets.chatproject.client.cfg.ServiceLocator;
import com.jets.chatproject.client.controller.ScreenController;
import com.jets.chatproject.client.util.DialogUtils;
import com.jets.chatproject.module.rmi.MessagesService;
import com.jets.chatproject.module.rmi.client.ClientCallback;
import com.jets.chatproject.module.rmi.dto.MessageDTO;
import com.jets.chatproject.module.rmi.dto.MessageFormat;
import com.jets.chatproject.module.rmi.dto.MessageType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author ibrahim
 */
public class MessagesController implements Initializable {

    @FXML
    private JFXToggleButton boldToggle;
    @FXML
    private JFXToggleButton italicToggle;
    @FXML
    private JFXColorPicker textColorPicker;
    @FXML
    private JFXColorPicker backgroundColorPicker;
    @FXML
    private JFXComboBox<Integer> fontSizeCombo;
    @FXML
    private JFXTextField messageTextField;
    @FXML
    private JFXButton sendButton;
    @FXML
    private ListView<MessageDTO> messagesListView;

    ScreenController screenController;
    MessagesService messagesService;
    ClientCallbackImp clientCallback;

    ChatType chatType;
    int id;
    MessageFormat messageFormat = new MessageFormat();

    private final ClientCallbackImp.MessageListener messageListener
            = new ClientCallbackImp.MessageListener() {
        @Override
        public void onDirectMessageReceived(int friendId, MessageDTO message) {
            System.out.println(friendId);
            if (chatType == ChatType.Direct && id == friendId) {
                Platform.runLater(() -> {
                    messagesListView.getItems().add(message);
                });
            }
        }

        @Override
        public void onGroupMessageReceived(int groupId, MessageDTO message) {
            System.out.println(groupId);
            if (chatType == ChatType.Group && id == groupId) {
                Platform.runLater(() -> {
                    messagesListView.getItems().add(message);
                });
            }
        }

        @Override
        public void onServerMessageReceived(String message) {
            getAlert("Server Message", message, Alert.AlertType.INFORMATION);
        }
    };

    public enum ChatType {
        Direct, Group
    }

    public MessagesController(ScreenController screenController, ChatType chatType, int id) {
        try {
            this.screenController = screenController;
            this.chatType = chatType;
            this.id = id;
            messagesService = ServiceLocator.getService(MessagesService.class);
            clientCallback = (ClientCallbackImp) ServiceLocator.getService(ClientCallback.class);
            clientCallback.addMessageListener(messageListener);
        } catch (Exception ex) {
            DialogUtils.showException(ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        messagesListView.setCellFactory(listView -> {
            return new ListCell<MessageDTO>() {
                @Override
                protected void updateItem(MessageDTO message, boolean empty) {
                    if (message != null && !empty) {
                        Text text = new Text();
                        TextFlow flow = new TextFlow(text);
                        if (message.getSenderId() == screenController.getId()) {
                            flow.setTextAlignment(TextAlignment.RIGHT);
                            text.setText(message.getContent() + " :" + message.getSenderName());
                        } else {
                            text.setText(message.getSenderName() + ": " + message.getContent());
                        }
                        FontWeight weight
                                = message.getFormat().isBold() ? FontWeight.BOLD : FontWeight.NORMAL;
                        FontPosture posture
                                = message.getFormat().isItalic() ? FontPosture.ITALIC : FontPosture.REGULAR;
                        text.setFont(Font.font(Font.getDefault().getFamily(),
                                weight, posture, message.getFormat().getFontSize()));
                        text.setFill(Color.web(message.getFormat().getTextColorCode()));
                        flow.setStyle("-fx-background-color:" + message.getFormat().getBackgroundColorCode());
                        setGraphic(flow);
                        setStyle("-fx-padding: 0px;");
                    } else {
                        setGraphic(null);
                    }
                }
            };
        });
        messagesListView.getItems().addListener((ListChangeListener.Change<? extends MessageDTO> c) -> {
            messagesListView.scrollTo(c.getList().size() - 1);
        });

        try {
            switch (chatType) {
                case Direct:
                    List<MessageDTO> allDirectMessages
                            = messagesService.getAllDirectMessages(screenController.getSession(), id);
                    messagesListView.getItems().addAll(allDirectMessages);
                    break;

                case Group:
                    List<MessageDTO> allGroupMessages
                            = messagesService.getAllGroupMessages(screenController.getSession(), id);
                    messagesListView.getItems().addAll(allGroupMessages);
                    break;
            }
        } catch (RemoteException ex) {
            DialogUtils.showException(ex);
        }

        fontSizeCombo.getItems().addAll(12, 14, 16, 18, 20, 22, 26, 30, 36, 44);
        fontSizeCombo.setButtonCell(new ListCell<Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                if (item != null) {
                    setText("Font size: " + item);
                }
            }

        });

        textColorPicker.setStyle("-fx-color-label-visible: false;");
        backgroundColorPicker.setStyle("-fx-color-label-visible: false;");
    }

    @FXML
    private void sendMessage(ActionEvent event) {
        try {
            String messageTxt = messageTextField.getText().trim();
            if (messageTxt.isEmpty()) {
                return;
            }
            MessageDTO messageDTO = new MessageDTO(-1, screenController.getId());
            messageDTO.setContent(messageTxt);
            messageDTO.setFormat(messageFormat);
            messageDTO.setType(MessageType.PLAIN_TEXT);
            switch (chatType) {
                case Direct:
                    messagesService.sendDirectMessage(
                            screenController.getSession(), id, messageDTO);
                    break;
                case Group:
                    messagesService.sendGroupMessage(
                            screenController.getSession(), id, messageDTO);
                    break;
            }
            messageTextField.clear();
        } catch (RemoteException ex) {
            DialogUtils.showException(ex);
        }

    }

    @FXML
    private void toggleBold(ActionEvent event) {
        messageFormat.setBold(boldToggle.isSelected());
        updateTextField();
    }

    @FXML
    private void toggleItalic(ActionEvent event) {
        messageFormat.setItalic(italicToggle.isSelected());
        updateTextField();
    }

    @FXML
    private void pickTextColor(ActionEvent event) {
        messageFormat.setTextColor((int) Long.decode(textColorPicker.getValue().toString()).longValue());
        updateTextField();
    }

    @FXML
    private void pickBackgroundColor(ActionEvent event) {
        messageFormat.setBackgroundColor((int) Long.decode(backgroundColorPicker.getValue().toString()).longValue());
        updateTextField();
    }

    @FXML
    private void setFontSize(ActionEvent event) {
        messageFormat.setFontSize(fontSizeCombo.getValue());
        updateTextField();
    }

    private void updateTextField() {
        FontWeight weight
                = messageFormat.isBold() ? FontWeight.BOLD : FontWeight.NORMAL;
        FontPosture posture
                = messageFormat.isItalic() ? FontPosture.ITALIC : FontPosture.REGULAR;
        messageTextField.setFont(Font.font(Font.getDefault().getFamily(),
                weight, posture, messageFormat.getFontSize()));

        messageTextField.setStyle("-fx-text-fill:" + messageFormat.getTextColorCode() + ";"
                + "-fx-control-inner-background:" + messageFormat.getBackgroundColorCode());
    }
    private void getAlert(String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
