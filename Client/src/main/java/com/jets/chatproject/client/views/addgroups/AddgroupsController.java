/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.views.addgroups;

import com.jets.chatproject.client.controller.ScreenController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Ibrahim
 */
public class AddgroupsController implements Initializable {

    @FXML
    private TextField groupNameTxt;
    @FXML
    private Button chossePicBtn;
    @FXML
    private TextField memberTxtField;
    @FXML
    private ImageView addMemberBtn;
    @FXML
    private ImageView removeMemberBtn;
    @FXML
    private ImageView submitView;
    @FXML
    private ListView<?> membersList;

    ScreenController screenController;
    String userSession;
    String userPhone;
    
    public AddgroupsController(ScreenController screenController) {
        this.screenController = screenController;
        this.userPhone = screenController.getPhone();
        this.userSession = screenController.getSession();
    }    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void choosePicAction(ActionEvent event) {
    }

    @FXML
    private void addMemberAction(MouseEvent event) {
    }

    @FXML
    private void removeMemberAction(MouseEvent event) {
    }

    @FXML
    private void SubmitAction(MouseEvent event) {
    }
    
}
