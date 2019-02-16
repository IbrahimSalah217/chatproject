/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.views.login;

import com.jets.chatproject.client.ChatApp;
import com.jets.chatproject.client.util.DialogUtils;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Ibrahim
 */
public class LoginCheckPhoneController implements Initializable {

    @FXML
    private Pane loginPane;
    @FXML
    private Label PhoneNumLabl;
    @FXML
    private TextField phoneNumLoginTxtId;
    @FXML
    private Button nextLoginBtnID;
    @FXML
    private Hyperlink registerHLinkID;

    ChatApp chatApp;

    public LoginCheckPhoneController(ChatApp chatApp) {
        this.chatApp = chatApp;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        phoneNumLoginTxtId.setStyle("-fx-text-inner-color: red;");
        phoneNumLoginTxtId.textProperty().addListener((observable) -> {
            phoneNumLoginTxtId.setStyle("-fx-text-inner-color: red;");
            if (isRealPhoneNum(phoneNumLoginTxtId.getText())) {
                phoneNumLoginTxtId.setStyle("-fx-text-inner-color: black;");
            }

        });
    }

    @FXML
    private void phoneNumEntered(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (isRealPhoneNum(phoneNumLoginTxtId.getText())) {
                phoneNumLoginTxtId.setStyle("-fx-text-inner-color: green;");
            }
        }
    }

    @FXML
    private void nextLoginAction(ActionEvent event) {
        String phoneNum = phoneNumLoginTxtId.getText();
        if (isRealPhoneNum(phoneNum)) {
            try {
                if (chatApp.isExistPhone(phoneNum)) {
                    chatApp.switchToLoginPasswordScreen(phoneNum);
                } else {
                    Alert notUserPhone = new Alert(Alert.AlertType.ERROR);
                    notUserPhone.setContentText("this number is not Registered");
                    notUserPhone.show();
                }
            } catch (Exception ex) {
                DialogUtils.showException(ex);
            }
        } else {
            Alert wrongPhone = new Alert(Alert.AlertType.ERROR);
            wrongPhone.setContentText("not Valid Phone Number please enter right again");
            wrongPhone.show();
        }

    }

    private boolean isRealPhoneNum(String phone) {
        boolean isRealPhone = false;
        String pattern = "\\d{11}|(?:\\d{3}-){3}\\d{5}";
        if (phone.matches(pattern)) {
            isRealPhone = true;
        }
        return isRealPhone;
    }
}
