/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.views.updateprofile;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hadeer
 */
public class UpdateProfileFXMLController implements Initializable {

    @FXML
    private PasswordField passwordTxtID;
    @FXML
    private PasswordField verifyPasswordTxtID;
    @FXML
    private TextField emailTxtID;
    @FXML
    private TextField nameTxtID;
    @FXML
    private TextField countryTxtID;
    @FXML
    private TextField phoneNumberTxtID;
    @FXML
    private ComboBox<?> genderCBoxID;
    @FXML
    private DatePicker datePickerID;
    @FXML
    private TextField bio;
    @FXML
    private Button registerBtnID;
    @FXML
    private ImageView userImageID;

    Stage stage;
    String userSession;
    /**
     * Initializes the controller class.
     */
    
    
    public UpdateProfileFXMLController(Stage stage, String userSession) {
        this.stage = stage;
        this.userSession = userSession;
        // TODO
        
    }    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void update(ActionEvent event) {
    }

    @FXML
    private void changePicture(MouseEvent event) {
    }
    
}
