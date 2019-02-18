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
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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
    private ToggleButton boldToggle;
    @FXML
    private ToggleButton italicToggle;
    @FXML
    private ToggleButton underlineToggle;
    @FXML
    private ColorPicker textColorPicker;
    @FXML
    private ColorPicker backgroundColorPicker;
    @FXML
    private ComboBox<Integer> fontSizeCombo;
    @FXML
    private TextField messageTextField;
    @FXML
    private Button sendButton;
    @FXML
    private ListView<MessageDTO> messagesListView;
    
    ScreenController screenController;
    MessagesService messagesService;
    ClientCallbackImp clientCallback;
    
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
            if (chatType == ChatType.Direct && id == groupId) {
                Platform.runLater(() -> {
                    messagesListView.getItems().add(message);
                });
            }
        }
    };
    
    ChatType chatType;
    int id;
    MessageFormat messageFormat = new MessageFormat();
    
    public enum ChatType {
        Direct, Group
    }
    
    public MessagesController(ScreenController screenController, ChatType chatType, int id) {
        this.screenController = screenController;
        this.chatType = chatType;
        this.id = id;
        try {
            messagesService = ServiceLocator.getService(MessagesService.class);
            clientCallback = (ClientCallbackImp) ServiceLocator.getService(ClientCallback.class);
            clientCallback.addMessageListener(messageListener);
        } catch (Exception ex) {
            Logger.getLogger(MessagesController.class.getName()).log(Level.SEVERE, null, ex);
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
                        FontWeight weight = message.getFormat().isBold() ? FontWeight.BOLD : FontWeight.NORMAL;
                        FontPosture posture = message.getFormat().isItalic() ? FontPosture.ITALIC : FontPosture.REGULAR;
                        text.setFont(Font.font(Font.getDefault().getFamily(),
                                weight, posture, message.getFormat().getFontSize()));
                        setGraphic(flow);
                    }else{
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
    }
    
    @FXML
    private void toggleItalic(ActionEvent event) {
        messageFormat.setItalic(italicToggle.isSelected());
    }
    
    @FXML
    private void toggleUnderline(ActionEvent event) {
        messageFormat.setUnderline(underlineToggle.isSelected());
    }
    
    @FXML
    private void pickTextColor(ActionEvent event) {
        messageFormat.setTextColor((int) Long.decode(textColorPicker.getValue().toString()).longValue());
    }
    
    @FXML
    private void pickBackgroundColor(ActionEvent event) {
        messageFormat.setBackgroundColor(backgroundColorPicker.getValue().hashCode());
    }
    
    @FXML
    private void setFontSize(ActionEvent event) {
        messageFormat.setFontSize(fontSizeCombo.getValue());
    }
    
}
