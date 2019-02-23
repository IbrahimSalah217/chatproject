/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.controller;

import com.jets.chatproject.server.views.MainPageController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author salma
 */
public class ScreenController {
    
    private final Stage stage;
    
    public ScreenController(Stage stage){
        this.stage = stage;
    }
    
    public void switchToMainPage(){
        
        try {
            FXMLLoader loader = new FXMLLoader();
            MainPageController controller = new MainPageController(stage, this);
            loader.setController(controller);
            Parent root = loader.load(controller.getClass().getResource("MainPage.fxml").openStream());
            Scene scene = new Scene(root);
            stage.setTitle("Server Manager");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
