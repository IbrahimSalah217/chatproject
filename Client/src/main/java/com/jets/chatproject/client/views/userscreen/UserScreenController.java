/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.views.userscreen;

import com.jets.chatproject.client.controller.ScreenController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 *
 * @author ibrahim
 */
public class UserScreenController implements Initializable {

    ScreenController screenController;

    public UserScreenController(ScreenController screenController) {
        this.screenController = screenController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
