/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.views.addcontacts;

import com.jets.chatproject.client.cfg.ServiceLocator;
import com.jets.chatproject.client.util.DialogUtils;
import com.jets.chatproject.module.rmi.AuthService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ibrahim
 */
public class AddContactsFXMLController implements Initializable {

    @FXML
    private ListView<String> listView;

    @FXML
    private TextField phoneTextField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listView.setFocusTraversable(false);
        Platform.runLater(() -> phoneTextField.requestFocus());
    }

    @FXML
    private void checkPhone(ActionEvent ae) {
        try {
            AuthService authService = ServiceLocator.getService(AuthService.class);
            authService.checkPhone(phoneTextField.getText().trim());
        } catch (Exception ex) {
            DialogUtils.showException(ex);
        }
    }

    @FXML
    private void addAll(ActionEvent ae) {

    }

}
