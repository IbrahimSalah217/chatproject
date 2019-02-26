/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.views.announcement;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ibrahim
 */
public class AnnouncementController implements Initializable {

    @FXML
    private WebView webview;
    @FXML
    private Button btn;

    private String message;

    public AnnouncementController(String message) {
        this.message = message;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        webview.getEngine().loadContent(message);
        btn.setOnAction((a) -> {
            btn.getScene().getWindow().hide();
        });
    }

    public static void showAnnouncement(String message) {
        try {
            AnnouncementController controller = new AnnouncementController(message);
            FXMLLoader loader = new FXMLLoader();
            loader.setController(controller);
            Parent root = loader.load(controller.getClass().getResourceAsStream("Announcement.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Server announcement");
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(AnnouncementController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
