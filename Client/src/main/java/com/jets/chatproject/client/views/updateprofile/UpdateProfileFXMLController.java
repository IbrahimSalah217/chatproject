/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.views.updateprofile;

import com.jets.chatproject.client.cfg.ServiceLocator;
import com.jets.chatproject.client.views.controllers.userScreenController;
import com.jets.chatproject.client.views.login.LoginCheckPhoneController;
import com.jets.chatproject.module.rmi.AuthService;
import com.jets.chatproject.module.rmi.UsersService;
import com.jets.chatproject.module.rmi.dto.Gender;
import com.jets.chatproject.module.rmi.dto.UserDTO;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Hadeer
 */
public class UpdateProfileFXMLController implements Initializable {

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
    private TextArea bio;
    @FXML
    private ImageView userImageID;
    @FXML
    private Button updateBtnID;

    FileChooser fileChooser;
    Image image;
    byte[] bytesImage;
    String name, password, verifyPassword, email, country, gender, phoneNumber, bioIn;
    Date birthdate;
    InputsValidation inputsValidation;
    UserDTO newUser ,oldUser ;
    Stage stage;
    String userSession;
    UsersService usersService;

    /**
     * Initializes the controller class.
     */
    public UpdateProfileFXMLController(Stage stage, String userSession ,String phoneNumber) {
        this.stage = stage;
        this.userSession = userSession;
        try {
             usersService = ServiceLocator.getService(UsersService.class);
             this.oldUser = usersService.getProfileByPhone(userSession, phoneNumber);
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
        Circle clip = new Circle(userImageID.getFitWidth()/2,userImageID.getFitHeight()/2,50);
            userImageID.setClip(clip);
        phoneNumberTxtID.setText(oldUser.getPhoneNumber());
        phoneNumberTxtID.setDisable(true);
        bio.setText(oldUser.getBio());
        nameTxtID.setText(oldUser.getDisplyName());
        emailTxtID.setText(oldUser.getEmail());
        countryTxtID.setText(oldUser.getCountry());
        genderCBoxID.setValue(oldUser.getGender().name());
        LocalDate localDate = oldUser.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        datePickerID.setValue(localDate);
        try {
            bytesImage=usersService.getPicture(userSession, oldUser.getPictureId());
            image =  image = new Image(new ByteArrayInputStream(bytesImage),30, 30, true, true);
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
                FXMLLoader fxmlLoader = new FXMLLoader();
                userScreenController controller = new userScreenController(stage,userSession);
                fxmlLoader.setController(controller);
                Parent root = fxmlLoader.load(getClass().getResource("userScreen.fxml").openStream());
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();

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
        country = countryTxtID.getText();
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
        alert.showAndWait();
    }

}
