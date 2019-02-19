package com.jets.chatproject.server.views.userdatamodification;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jets.chatproject.module.rmi.dto.Gender;
import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
import com.jets.chatproject.server.module.dal.entities.User;
import java.net.URL;
import java.util.Date;
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
    private TableView<User> tableInfo;
    @FXML
    private TableColumn<User, Integer> userId;
    @FXML
    private TableColumn<User, String> userPhoneNumber;
    @FXML
    private TableColumn<User, String> userName;
    @FXML
    private TableColumn<User, String> userEmail;
    @FXML
    private TableColumn<User, String> userPassword;
    @FXML
    private TableColumn<User, Gender> userGender;
    @FXML
    private TableColumn<User, String> userCountry;
    @FXML
    private TableColumn<User, Date> userDateOfBirth;
    @FXML
    private TableColumn<User, String> userBio;
    @FXML
    private TableColumn<User, String> userState;
    @FXML
    private TableColumn<User, Integer> userPictureId;
    ObservableList<User> userList;
    List<User> userEntityList;
    UsersDao userdao;
    
    public UserDataModificationController(DaosFactory daosFactory){
     
        userdao = daosFactory.getUsersDao();
        userList = FXCollections.observableArrayList();
    }
    
    public void initTable(){
        
    }
    public void initcol(){
        
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
