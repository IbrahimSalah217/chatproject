package com.jets.chatproject.server.views.userregister;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jets.chatproject.module.rmi.dto.Gender;
import com.jets.chatproject.module.rmi.dto.UserStatus;
import com.jets.chatproject.server.controller.ScreenController;
import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import com.jets.chatproject.server.module.dal.dao.PicturesDao;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
import com.jets.chatproject.server.module.dal.entities.Picture;
import com.jets.chatproject.server.module.dal.entities.User;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author pc
 */

public class RegisterController implements Initializable {

    @FXML
    private PasswordField passwordTxtID;
    @FXML
    private PasswordField verifyPasswordTxtID;
    @FXML
    private TextField emailTxtID;
    @FXML
    private TextField nameTxtID;
    @FXML
    private TextField countryTxtID;
    @FXML
    private TextField phoneNumberTxtID;
    @FXML
    private ComboBox genderCBoxID;
    @FXML
    private DatePicker datePickerID;
    @FXML
    private Button registerBtnID;
    @FXML
    private Button backToMainPage;
    
    ScreenController screenController;
    String name, password, verifyPassword, email, country, gender, phoneNumber;
    Date birthdate;
    InputsValidation inputsValidation;
    User user;
    UsersDao userDao ; 
    PicturesDao picturesDao;
    Image image;
    byte[] picture;



    public RegisterController(DaosFactory daosFactory, ScreenController screenController) {
        
        this.screenController = screenController;
        picturesDao = daosFactory.getPicturesDao();
        image = new Image("\\Images\\userimg.png");
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", stream);
        } catch (IOException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        picture = stream.toByteArray();
        name = password = verifyPassword = email = country = gender = phoneNumber = "";
        userDao = daosFactory.getUsersDao();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        genderCBoxID.setItems(FXCollections.observableArrayList("MALE", "FEMALE"));
        inputsValidation = new InputsValidation(this);
        Platform.runLater(() -> nameTxtID.requestFocus());
        backToMainPage.setOnAction((ActionEvent event)->{
        
            screenController.switchToMainPage();
        });
    }

    @FXML
    private void getPassword(ActionEvent event) {
        Platform.runLater(() -> verifyPasswordTxtID.requestFocus());
    }

    @FXML
    private void getVerifyPassword(ActionEvent event) {
        password = passwordTxtID.getText();
        verifyPassword = verifyPasswordTxtID.getText();
        if (inputsValidation.validatePassword()) {
            Platform.runLater(() -> countryTxtID.requestFocus());
        } else {
            getAlert("Your Passwords Don't Match!", "Please, re-enter your password correctly.", Alert.AlertType.ERROR);
            verifyPasswordTxtID.clear();
        }
    }

    @FXML
    private void getEmail(ActionEvent event) {
        email = emailTxtID.getText();
        if (inputsValidation.checkEmail()) {
            Platform.runLater(() -> passwordTxtID.requestFocus());
        } else {
            getAlert("Your Email Is Not Valid!", "Please, re-enter your email correctly.", Alert.AlertType.ERROR);
            emailTxtID.clear();
        }
    }

    @FXML
    private void getName(ActionEvent event) {
        Platform.runLater(() -> emailTxtID.requestFocus());
    }

    @FXML
    private void getCountry(ActionEvent event) {
        Platform.runLater(() -> phoneNumberTxtID.requestFocus());
    }

    @FXML
    private void getPhoneNumber(ActionEvent event) {
        phoneNumber = phoneNumberTxtID.getText();
        if (inputsValidation.checkPhoneNumber()) {
            Platform.runLater(() -> genderCBoxID.requestFocus());
        } else {
            getAlert("Phone Numeber Is Invalid !", "Please, re-enter your phone number correctly.", Alert.AlertType.ERROR);
            phoneNumberTxtID.clear();
        }

    }

    @FXML
    private void getGender(ActionEvent event) {
        Platform.runLater(() -> datePickerID.requestFocus());
    }

    @FXML
    private void getBirthdate(ActionEvent event) {
        Platform.runLater(() -> registerBtnID.requestFocus());
    }

    @FXML
    private void register(ActionEvent event) {
        getData();
        String state = inputsValidation.checkBeforeLeave();
        if (!state.equals("")) {
            getAlert("Invalid Data!", state, Alert.AlertType.ERROR);
        } else {
            try {
                int pictureId = picturesDao.insert(new Picture(0, picture));
                user = new User(1, phoneNumber, name, email, password, Gender.valueOf(gender), country, birthdate, "Chat User", UserStatus.OFFLINE, pictureId, true);
                
                if (userDao.findByPhone(phoneNumber)== null) {
                    
                    userDao.insert(user);
                    getAlert("You've registired successfully", "Welcome to our application.", Alert.AlertType.INFORMATION);

                    return;

                } else {
                    getAlert("Invalid Phone Number!", "It seems like you entered a phone number that already exists.", Alert.AlertType.ERROR);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    private void getAlert(String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void getData() {
        boolean isGenderComboBoxEmpty = genderCBoxID.getSelectionModel().isEmpty();

        name = nameTxtID.getText();
        password = passwordTxtID.getText();
        email = emailTxtID.getText();
        country = countryTxtID.getText();
        verifyPassword = verifyPasswordTxtID.getText();
        phoneNumber = phoneNumberTxtID.getText();

        if (!isGenderComboBoxEmpty) {
            gender = (String) genderCBoxID.getValue();
        }

        LocalDate localDate = datePickerID.getValue();
        if (localDate != null) {
            birthdate = Date.valueOf(localDate);
        }
    }
}

