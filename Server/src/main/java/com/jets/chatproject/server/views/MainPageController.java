package com.jets.chatproject.server.views;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jets.chatproject.server.controller.ScreenController;
import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import com.jets.chatproject.server.module.dal.hibernate.dao.imp.DbDaosFactory;
import com.jets.chatproject.server.views.manageservice.ServerManagerController;
import com.jets.chatproject.server.views.serverannouncement.ServerAnnouncementController;
import com.jets.chatproject.server.views.serverstatistics.ServerStatisticsController;
import com.jets.chatproject.server.views.userdatamodification.UserDataModificationController;
import com.jets.chatproject.server.views.userregister.RegisterController;
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
public class MainPageController implements Initializable {

    @FXML
    private Button UserRegistration;
    @FXML
    private Button ServerStatistics;
    @FXML
    private Button ServerAnnouncement;
    @FXML
    private Button UserDataModification;
    @FXML
    private Button ManageService;
    Stage stage;
    DaosFactory daosFactory ;
    ScreenController screenController;

    public MainPageController(Stage stage, ScreenController screenController) {
        this.stage = stage;
        daosFactory = new DbDaosFactory();
        this.screenController = screenController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        UserRegistration.setOnAction((ActionEvent event) -> {
            
            FXMLLoader loader = new FXMLLoader();
            RegisterController controller = new RegisterController(daosFactory, screenController);
            loader.setController(controller);
            try {
                Parent root = loader.load(controller.getClass().getResource("register.fxml").openStream());
                Scene scene = new Scene(root);
                stage.setTitle("Server Manager");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        ServerStatistics.setOnAction((ActionEvent event) -> {

            FXMLLoader loader = new FXMLLoader();
            ServerStatisticsController controller = new ServerStatisticsController(daosFactory, screenController);
            loader.setController(controller);
            try {
                Parent root = loader.load(controller.getClass().getResource("ServerStatistics.fxml").openStream());
                Scene scene = new Scene(root);
                stage.setTitle("Server Manager");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        ServerAnnouncement.setOnAction((ActionEvent event) -> {

            FXMLLoader loader = new FXMLLoader();
            ServerAnnouncementController controller = new ServerAnnouncementController(daosFactory, screenController);
            loader.setController(controller);
            try {
                Parent root = loader.load(controller.getClass().getResource("ServerAnnouncement.fxml").openStream());
                Scene scene = new Scene(root);
                stage.setTitle("Server Manager");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        UserDataModification.setOnAction((ActionEvent event) -> {
            
            FXMLLoader loader = new FXMLLoader();
            UserDataModificationController controller = new UserDataModificationController(daosFactory, screenController);
            loader.setController(controller);
            try {
                Parent root = loader.load(controller.getClass().getResource("UserDataModification.fxml").openStream());
                Scene scene = new Scene(root);
                stage.setTitle("Server Manager");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        ManageService.setOnAction((ActionEvent event) -> {
            
            FXMLLoader loader = new FXMLLoader();
            ServerManagerController controller = new ServerManagerController(screenController);
            loader.setController(controller);
            try {
                Parent root = loader.load(controller.getClass().getResource("ServerManager.fxml").openStream());
                Scene scene = new Scene(root);
                stage.setTitle("Server Manager");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }

}
