package com.jets.chatproject.server.views.userdatamodification;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
import com.jets.chatproject.server.module.dal.entities.User;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author salma
 */
public class UserDataModificationController implements Initializable {

    @FXML
    private TableView<?> tableInfo;
    @FXML
    private TableColumn<?, ?> userId;
    @FXML
    private TableColumn<?, ?> userPhoneNumber;
    @FXML
    private TableColumn<?, ?> userName;
    @FXML
    private TableColumn<?, ?> userEmail;
    @FXML
    private TableColumn<?, ?> userPassword;
    @FXML
    private TableColumn<?, ?> userGender;
    @FXML
    private TableColumn<?, ?> userCountry;
    @FXML
    private TableColumn<?, ?> userDateOfBirth;
    @FXML
    private TableColumn<?, ?> userBio;
    @FXML
    private TableColumn<?, ?> userState;
    @FXML
    private TableColumn<?, ?> userPictureId;
    ObservableList<User> userList;
    List<User> userEntityList;
    UsersDao userdao;
    
    public UserDataModificationController(DaosFactory daosFactory){
     
        userdao = daosFactory.getUsersDao();
        userList = FXCollections.observableArrayList();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            userEntityList = userdao.findAllUser();
        } catch (Exception ex) {
            Logger.getLogger(UserDataModificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(User user : userEntityList){
            userList.add(user);
        }
        
    }   
    
    
}
