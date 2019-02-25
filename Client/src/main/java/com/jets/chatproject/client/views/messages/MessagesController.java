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
import com.jets.chatproject.module.rmi.MessagesService;
import com.jets.chatproject.module.rmi.UsersService;
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

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
    UsersService userService;
    File file;
    volatile boolean isRecording;
    byte[] voiceArray;
    int bytesRead = 0;
    int numBytesrReaded = 0;
    TargetDataLine dataLine;
    AudioFormat audioFormat = new AudioFormat(8000.0F, 16, 2, true, true);
    DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
    ByteArrayOutputStream outstream = new ByteArrayOutputStream();

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

        /*          Salah         */
        @Override
        public void onVoiceRecordRecieve(int friendId, byte[] arrayVoice) {
            getRecord(friendId, arrayVoice);

        }

        private void getRecord(int friendId, byte[] arrayVoice) {
            Platform.runLater(() -> {

                try {
                    file = new File("audio_.wav" + System.currentTimeMillis());
                    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
                    AudioFormat audioFormat2 = new AudioFormat(8000.0F, 16, 2, true, true);
                    ByteArrayInputStream byteArray = new ByteArrayInputStream(arrayVoice);
                    AudioInputStream audioInputStream = new AudioInputStream(byteArray, audioFormat2, 1000000);
                    AudioSystem.write(audioInputStream, fileType, file);
                    audioInputStream.reset();
                    audioInputStream.close();
                    HBox mediaBar = new HBox();
                    Button play = new Button(">");
                    play.setStyle("-fx-background-color:#cyan;");
                    Button pause = new Button("||");
                    pause.setStyle("-fx-background-color:#cyan;");
                    Button stop = new Button("#");
                    stop.setStyle("-fx-background-color:#cyan;");
//                    play.setShape(new Circle(20, new ImagePattern(new Image(getClass().getResource("/images/play.png").toString()))));
//                    stop.setShape(new Circle(20, new ImagePattern(new Image(getClass().getResource("/images/stop.png").toString()))));
//                    pause.setShape(new Circle(20, new ImagePattern(new Image(getClass().getResource("/images/Pause.png").toString()))));

                    Pane pane1 = new Pane();
                    pane1.setShape(new Rectangle(20, USE_PREF_SIZE));
                    Pane pane2 = new Pane();
                    pane2.setShape(new Rectangle(20, USE_PREF_SIZE));
                    mediaBar.getChildren().addAll(play, pane1, pause, pane2, stop);
//                    mediaBar.setHgrow(pane1, Priority.ALWAYS);
//                    mediaBar.setHgrow(pane2, Priority.ALWAYS);
                    Media record = new Media(file.toURI().toString());
                    MediaPlayer recordPlayer = new MediaPlayer(record);
                    recordPlayer.setAutoPlay(false);
                    play.setOnAction((event) -> {
                        recordPlayer.play();
                    });
                    pause.setOnAction((event) -> {
                        recordPlayer.pause();
                    });
                    stop.setOnAction((event) -> {
                        recordPlayer.stop();
                    });

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("voice Record");
                    alert.setContentText("you have voice Record from" + userService.getProfileById(screenController.getSession(), friendId).getDisplyName() + "if you want to listen to it click Ok");
                    alert.setGraphic(mediaBar);
                    alert.showAndWait();

                } catch (IOException ex) {
                    DialogUtils.showException(ex);
                }

            });
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
            userService = ServiceLocator.getService(UsersService.class);
            clientCallback = (ClientCallbackImp) ServiceLocator.getService(ClientCallback.class);
            clientCallback.addMessageListener(messageListener);
        } catch (Exception ex) {
            DialogUtils.showException(ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        messagesListView.setCellFactory(listView -> {
            return new MessageBubble(screenController.getId(), pictureResolver);
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
    private MessageBubble.PictureResolver pictureResolver = new MessageBubble.PictureResolver() {
        Map<Integer, byte[]> map = new HashMap<>();

        @Override
        public byte[] getProfilePicture(int userId) {
            if (map.containsKey(userId)) {
                return map.get(userId);
            }
            try {
                int pictureId = userService.getProfileById(
                        screenController.getSession(), userId).getPictureId();
                byte[] picture = userService.getPicture(screenController.getSession(), pictureId);
                map.put(userId, picture);
                return picture;
            } catch (RemoteException ex) {
                Logger.getLogger(MessagesController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    };

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
        isRecording = false;

    }

    @FXML
    private void recordAction(MouseEvent event) {
        Platform.runLater(() -> {
            try {
                recordBtn.setDisable(true);
                isRecording = true;
                dataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
                dataLine.open(audioFormat);
                voiceArray = new byte[dataLine.getBufferSize() / 5];
                dataLine.start();
                Thread th = new Thread(() -> {
                    while (isRecording) {
                        numBytesrReaded = dataLine.read(voiceArray, 0, 1024);
                        bytesRead += numBytesrReaded;
                        outstream.write(voiceArray, 0, numBytesrReaded);
                    }
                    recordBtn.setDisable(false);

                    dataLine.drain();
                    dataLine.close();
                    try {
                        messagesService.sendVoice(screenController.getSession(), id, outstream.toByteArray());
                        outstream.reset();
                        outstream.close();
                    } catch (RemoteException ex) {
                        Logger.getLogger(MessagesController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(MessagesController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });
                th.start();

            } catch (LineUnavailableException ex) {
                DialogUtils.showException(ex);
            }
        });
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
