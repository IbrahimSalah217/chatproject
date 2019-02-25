/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.views.messages;

import com.jets.chatproject.client.ClientCallbackImp;
import com.jets.chatproject.client.cfg.ServiceLocator;
import com.jets.chatproject.client.chatbot.ChatbotManager;
import com.jets.chatproject.client.controller.ScreenController;
import com.jets.chatproject.client.util.DialogUtils;
import com.jets.chatproject.client.views.messages.MessageBubble.SpeechDirection;
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
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

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
    private FontAwesomeIconView sendFileBtn;
    @FXML
    private FontAwesomeIconView recordBtn;
    @FXML
    private JFXButton sendButton;
    @FXML
    private ListView<MessageDTO> messagesListView;
    @FXML
    private JFXToggleButton botToggle;

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
                        MessageBubble.SpeechDirection direction
                                = message.getSenderId() == screenController.getId()
                                ? SpeechDirection.RIGHT : SpeechDirection.LEFT;
                        setGraphic(new MessageBubble(message, direction));
                    }
//                    if (message != null && !empty) {
//                        Text text = new Text();
//                        TextFlow flow = new TextFlow(text);
//                        if (message.getSenderId() == screenController.getId()) {
//                            flow.setTextAlignment(TextAlignment.RIGHT);
//                            text.setText(message.getContent() + " :" + message.getSenderName());
//                        } else {
//                            text.setText(message.getSenderName() + ": " + message.getContent());
//                        }
//                        FontWeight weight
//                                = message.getFormat().isBold() ? FontWeight.BOLD : FontWeight.NORMAL;
//                        FontPosture posture
//                                = message.getFormat().isItalic() ? FontPosture.ITALIC : FontPosture.REGULAR;
//                        text.setFont(Font.font(Font.getDefault().getFamily(),
//                                weight, posture, message.getFormat().getFontSize()));
//                        text.setFill(Color.web(message.getFormat().getTextColorCode()));
//                        flow.setStyle("-fx-background-color:" + message.getFormat().getBackgroundColorCode());
//                        setGraphic(flow);
//                        setStyle("-fx-padding: 0px;");
//                    } else {
//                        setGraphic(null);
//                    }
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
        boolean isBotEnabled = false;
        switch (chatType) {
            case Direct:
                isBotEnabled = ChatbotManager.getInstance().isEnabledForFriend(id);
                break;
            case Group:
                isBotEnabled = ChatbotManager.getInstance().isEnabledForGroup(id);
                break;
        }
        botToggle.setSelected(isBotEnabled);
        botToggle.selectedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue && chatType == ChatType.Direct) {
                    ChatbotManager.getInstance().enableForFriend(id);
                } else if (newValue && chatType == ChatType.Group) {
                    ChatbotManager.getInstance().enableForGroup(id);
                } else if (!newValue && chatType == ChatType.Direct) {
                    ChatbotManager.getInstance().disableForFriend(id);
                } else if (!newValue && chatType == ChatType.Group) {
                    ChatbotManager.getInstance().disableForGroup(id);
                }
            }
        });
    }

    @FXML
    private void sendMessage(ActionEvent event) {
        sendMessage(messageTextField.getText().trim());
    }

    private void sendMessage(String message) {
        try {
            if (message.isEmpty()) {
                return;
            }
            MessageDTO messageDTO = new MessageDTO(-1, screenController.getId());
            messageDTO.setContent(message);
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
    @FXML
    private void sendFileAction(MouseEvent event) {
    }

    @FXML
    private void recordAction(MouseEvent event) {
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
    
}
