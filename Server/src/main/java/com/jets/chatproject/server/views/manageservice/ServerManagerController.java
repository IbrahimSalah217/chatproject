package com.jets.chatproject.server.views.manageservice;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jets.chatproject.server.ServerApplication;
import com.jets.chatproject.server.controller.ScreenController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author salma
 */
public class ServerManagerController implements Initializable {

    
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button backButton;
    ScreenController screenController;
    ServerApplication application;
    
    public ServerManagerController(ScreenController screenController){
        
        application  = new ServerApplication();
        this.screenController = screenController;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        startButton.setOnAction((ActionEvent event)->{
            
            stopButton.setDisable(false);
            application.stratServer();
            startButton.setDisable(true);
        });
        stopButton.setOnAction((ActionEvent event)->{
        
            startButton.setDisable(false);
            application.stopServer();
            stopButton.setDisable(true);
        });
        backButton.setOnAction((ActionEvent event)->{
        
            screenController.switchToMainPage();
        });
        
    }    
    
}
