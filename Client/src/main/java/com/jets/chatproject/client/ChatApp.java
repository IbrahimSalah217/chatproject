/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client;

import com.jets.chatproject.client.views.addcontacts.AddContactsFXMLController;
import java.io.IOException;
import java.rmi.RemoteException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ibrahim
 */
public class ChatApp extends Application {

    Stage primaStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaStage = primaryStage;
        primaryStage.show();
        showAddContact();
    }

    private void showAddContact() throws IOException {
        Stage stage = new Stage();
        AddContactsFXMLController addContactsController
                = new AddContactsFXMLController();
        FXMLLoader loader = new FXMLLoader();
        loader.setController(addContactsController);
        Parent root = loader.load(AddContactsFXMLController.class
                .getResource("AddContactsFXML.fxml").openStream());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) throws RemoteException {
        launch(args);
    }

}
