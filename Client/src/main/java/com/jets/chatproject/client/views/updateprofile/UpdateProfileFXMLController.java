/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.views.updateprofile;

import com.jets.chatproject.client.cfg.ServiceLocator;
import com.jets.chatproject.client.controller.ScreenController;
import com.jets.chatproject.module.rmi.UsersService;
import com.jets.chatproject.module.rmi.dto.Gender;
import com.jets.chatproject.module.rmi.dto.UserDTO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Hadeer
 */
public class UpdateProfileFXMLController implements Initializable {

     @FXML
    private JFXPasswordField passwordTxtID;
    @FXML
    private JFXPasswordField verifyPasswordTxtID;
    @FXML
    private JFXTextField emailTxtID;
    @FXML
    private JFXTextField nameTxtID;
    @FXML
    private JFXComboBox countryTxtID;
    @FXML
    private JFXTextField phoneNumberTxtID;
    @FXML
    private JFXComboBox genderCBoxID;
    @FXML
    private JFXDatePicker datePickerID;
    @FXML
    private JFXTextArea bio;
    @FXML
    private JFXButton updateBtnID;
    @FXML
    private ImageView userImageID;
    @FXML
    private JFXButton choosePictuerBtnID;

    FileChooser fileChooser;
    Image image;
    byte[] bytesImage;
    String name, password, verifyPassword, email, country, gender, phoneNumber, bioIn;
    Date birthdate;
    InputsValidation inputsValidation;
    UserDTO newUser, oldUser;

    String userSession;
    UsersService usersService;

    ScreenController screenController;

    public UpdateProfileFXMLController(ScreenController screenController) {
        this.screenController = screenController;
        userSession = screenController.getSession();
        phoneNumber = screenController.getPhone();
        try {
            System.out.println(userSession + ":" + phoneNumber);
            usersService = ServiceLocator.getService(UsersService.class);
            oldUser = usersService.getProfileByPhone(userSession, phoneNumber);
        } catch (RemoteException ex) {
            Logger.getLogger(UpdateProfileFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UpdateProfileFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Image", "*.png"),
                new FileChooser.ExtensionFilter("JPG Image", "*.jpg"));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Circle clip = new Circle(userImageID.getFitWidth() / 2, userImageID.getFitHeight() / 2, 50);
        userImageID.setClip(clip);
        for(com.jets.chatproject.client.views.register.countries country: com.jets.chatproject.client.views.register.countries.values())
        {
            countryTxtID.getItems().add(country.toString());
        }
        phoneNumberTxtID.setText(oldUser.getPhoneNumber());
        phoneNumberTxtID.setDisable(true);
        bio.setText(oldUser.getBio());
        nameTxtID.setText(oldUser.getDisplyName());
        emailTxtID.setText(oldUser.getEmail());
        countryTxtID.setValue(oldUser.getCountry());
        genderCBoxID.setValue(oldUser.getGender().name());
       //LocalDate localDate = oldUser.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
       //datePickerID.setValue(localDate);
        
        try {
            bytesImage = usersService.getPicture(userSession, oldUser.getPictureId());
            image = new Image(new ByteArrayInputStream(bytesImage));
            userImageID.setImage(image);
        } catch (RemoteException ex) {
            Logger.getLogger(UpdateProfileFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        genderCBoxID.setItems(FXCollections.observableArrayList("MALE", "FEMALE"));
        inputsValidation = new InputsValidation(this);
    }

    @FXML
    private void update(ActionEvent event) {
        getData();
        String state = inputsValidation.checkBeforeLeave();
        if (!state.equals("")) {
            getAlert("Invalid Data!", state, Alert.AlertType.ERROR);
        } else {
            try {
                newUser = new UserDTO(1, phoneNumber, name, email, Gender.valueOf(gender), country, birthdate, bioIn, 1);
                usersService.updateProfile(userSession, newUser, bytesImage);
                getAlert("You've Updated Your Profile successfully", "", Alert.AlertType.INFORMATION);
                genderCBoxID.getScene().getWindow().hide();
                         screenController.switchToUSerProfileScreen();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void changePicture(MouseEvent event) {
        File selectedFile = fileChooser.showOpenDialog(null);
        setPicture(selectedFile);
    }

    private void setPicture(File imgPath) {
        if (imgPath != null) {
            image = new Image(imgPath.toURI().toString());
            userImageID.setImage(image);
        }
    }

    private void getData() {
        boolean isGenderComboBoxEmpty = genderCBoxID.getSelectionModel().isEmpty();

        name = nameTxtID.getText();
        password = passwordTxtID.getText();
        email = emailTxtID.getText();
        country = (String) countryTxtID.getValue();
        verifyPassword = verifyPasswordTxtID.getText();
        phoneNumber = phoneNumberTxtID.getText();
        bytesImage = extractBytes(image);
        bioIn = bio.getText();

        if (!isGenderComboBoxEmpty) {
            gender = (String) genderCBoxID.getValue();
        }

        LocalDate localDate = datePickerID.getValue();
        if (localDate != null) {
            birthdate = Date.valueOf(localDate);
        }
    }

    public byte[] extractBytes(Image img) {
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(img, null);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", stream);
            bytesImage = stream.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return (bytesImage);
    }

    private void getAlert(String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait().get();//.equals(ButtonType.OK)&& (header.equalsIgnoreCase("Invalid Data!")));
     //       screenController.switchToUSerProfileScreen();
    }

    @FXML
    private void choosePicture(ActionEvent event){
        File selectedFile = fileChooser.showOpenDialog(null);
        setPicture(selectedFile);
    }
}
