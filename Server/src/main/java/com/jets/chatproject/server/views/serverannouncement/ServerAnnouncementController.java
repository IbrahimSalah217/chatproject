package com.jets.chatproject.server.views.serverannouncement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jets.chatproject.module.rmi.dto.MessageDTO;
import com.jets.chatproject.module.rmi.dto.MessageType;
import com.jets.chatproject.module.rmi.dto.UserStatus;
import com.jets.chatproject.server.controller.ScreenController;
import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
import com.jets.chatproject.server.module.dal.entities.User;
import com.jets.chatproject.server.module.session.Broadcaster;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.HTMLEditor;

/**
 * FXML Controller class
 *
 * @author salma
 */
public class ServerAnnouncementController implements Initializable {

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private Button broadcastButton;
    @FXML
    private Button backButton;
    UsersDao userDao;
    List<User> userList;
    List<Integer> onlineUserList;
    Broadcaster broadcaster;
    MessageDTO serverMessage;
    ScreenController screenController;

    public ServerAnnouncementController(DaosFactory daosFactory, ScreenController screenController) {

        onlineUserList = new ArrayList<>();
        userDao = daosFactory.getUsersDao();
        broadcaster = Broadcaster.getInstance();
        this.screenController = screenController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        broadcastButton.setOnAction((ActionEvent event) -> {
            broadcaster.broadcastFromServer(htmlEditor.getHtmlText());
        });
        backButton.setOnAction((ActionEvent event) -> {

            screenController.switchToMainPage();
        });
    }

}
