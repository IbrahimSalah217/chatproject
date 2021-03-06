package com.jets.chatproject.server.views.serverstatistics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jets.chatproject.server.controller.ScreenController;
import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author salma
 */
public class ServerStatisticsController implements Initializable {

    @FXML
    private Button onlineOffline;
    @FXML
    private Button gender;
    @FXML
    private Button country;
    @FXML
    private Button backButton;
    Stage stage;
    DaosFactory daosFactory;
    ScreenController screenController;


    public ServerStatisticsController(DaosFactory daosFactory, ScreenController screenController) {
        
        this.daosFactory = daosFactory;
        stage = new Stage();
        this.screenController = screenController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        onlineOffline.setOnAction((ActionEvent event)->{
        
            FXMLLoader loader = new FXMLLoader();
            OnlineStatisticsController controller = new OnlineStatisticsController(daosFactory);
            loader.setController(controller);
            try {
                Parent root = loader.load(controller.getClass().getResource("OnlineStatistics.fxml").openStream());
                Scene scene = new Scene(root);
                stage.setTitle("Online Statistics");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ServerStatisticsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        gender.setOnAction((ActionEvent event)->{
            System.out.println("gender");
            FXMLLoader loader = new FXMLLoader();
            GenderStatisticsController controller = new GenderStatisticsController(daosFactory);
            loader.setController(controller);
            try {
                Parent root = loader.load(controller.getClass().getResource("GenderStatistics.fxml").openStream());
                Scene scene = new Scene(root);
                stage.setTitle("Gender Statistics");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ServerStatisticsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        country.setOnAction((ActionEvent event)->{
        
            FXMLLoader loader = new FXMLLoader();
            CountryStatisticsController controller = new CountryStatisticsController(daosFactory);
            loader.setController(controller);
            try {
                Parent root = loader.load(controller.getClass().getResource("CountryStatistics.fxml").openStream());
                Scene scene = new Scene(root);
                stage.setTitle("Country Statistics");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ServerStatisticsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        backButton.setOnAction((ActionEvent event)->{
        
            screenController.switchToMainPage();
        });
    }

}
