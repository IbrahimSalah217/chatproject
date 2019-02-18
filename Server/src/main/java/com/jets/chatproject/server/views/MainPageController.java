package com.jets.chatproject.server.views;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import com.jets.chatproject.server.module.dal.dao.imp.DbDaosFactory;
import com.jets.chatproject.server.views.manageservice.ServerManagerController;
import com.jets.chatproject.server.views.serverstatistics.ServerStatisticsController;
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
    private Button ServerAvailability;
    @FXML
    private Button ServerAnnouncement;
    @FXML
    private Button DataAvailability;
    @FXML
    private Button UserDataModification;
    @FXML
    private Button ManageService;
    Stage stage;
    DaosFactory daosFactory ;

    public MainPageController(Stage stage) {
        this.stage = stage;
        daosFactory = new DbDaosFactory();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        UserRegistration.setOnAction((ActionEvent event) -> {
            
            FXMLLoader loader = new FXMLLoader();
            RegisterController controller = new RegisterController(daosFactory);
            loader.setController(controller);
            try {
                Parent root = loader.load(getClass().getResource("register.fxml"));
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
            ServerStatisticsController controller = new ServerStatisticsController(daosFactory);
            loader.setController(controller);
            try {
                Parent root = loader.load(getClass().getResource("ServerStatistics.fxml"));
                Scene scene = new Scene(root);
                stage.setTitle("Server Manager");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
//        ServerAvailability.setOnAction((ActionEvent event) -> {
//
//            FXMLLoader loader = new FXMLLoader();
//            RegisterController controller = new RegisterController();
//            loader.setController(controller);
//            try {
//                Parent root = loader.load(getClass().getResource("chatPageFXML.fxml"));
//                Scene scene = new Scene(root);
//                stage.setTitle("Server Manager");
//                stage.setScene(scene);
//                stage.show();
//            } catch (IOException ex) {
//                Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        });
//        ServerAnnouncement.setOnAction((ActionEvent event) -> {
//
//            FXMLLoader loader = new FXMLLoader();
//            RegisterController controller = new RegisterController();
//            loader.setController(controller);
//            try {
//                Parent root = loader.load(getClass().getResource("chatPageFXML.fxml"));
//                Scene scene = new Scene(root);
//                stage.setTitle("Server Manager");
//                stage.setScene(scene);
//                stage.show();
//            } catch (IOException ex) {
//                Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        });
//        DataAvailability.setOnAction((ActionEvent event) -> {
//
//            FXMLLoader loader = new FXMLLoader();
//            RegisterController controller = new RegisterController();
//            loader.setController(controller);
//            try {
//                Parent root = loader.load(getClass().getResource("chatPageFXML.fxml"));
//                Scene scene = new Scene(root);
//                stage.setTitle("Server Manager");
//                stage.setScene(scene);
//                stage.show();
//            } catch (IOException ex) {
//                Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        });
//        UserDataModification.setOnAction((ActionEvent event) -> {
//            
//            FXMLLoader loader = new FXMLLoader();
//            RegisterController controller = new RegisterController();
//            loader.setController(controller);
//            try {
//                Parent root = loader.load(getClass().getResource("chatPageFXML.fxml"));
//                Scene scene = new Scene(root);
//                stage.setTitle("Server Manager");
//                stage.setScene(scene);
//                stage.show();
//            } catch (IOException ex) {
//                Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        });
        ManageService.setOnAction((ActionEvent event) -> {

            FXMLLoader loader = new FXMLLoader();
            ServerManagerController controller = new ServerManagerController();
            loader.setController(controller);
            try {
                Parent root = loader.load(getClass().getResource("ServerManager.fxml"));
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
