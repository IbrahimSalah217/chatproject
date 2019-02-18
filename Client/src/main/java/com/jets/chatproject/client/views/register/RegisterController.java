package com.jets.chatproject.client.views.register;

import com.jets.chatproject.client.cfg.ServiceLocator;
import com.jets.chatproject.client.controller.ScreenController;
import com.jets.chatproject.module.rmi.AuthService;
import com.jets.chatproject.module.rmi.dto.Gender;
import com.jets.chatproject.module.rmi.dto.UserDTO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
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
    private ImageView userImageID;
    @FXML
    private Button choosePictuerBtnID;

    FileChooser fileChooser;
    Image image;
    byte[] bytesImage;
    String name, password, verifyPassword, email, country, gender, phoneNumber;
    Date birthdate;
    InputsValidation inputsValidation;
    UserDTO user;

    ScreenController screenController;

    public RegisterController(ScreenController screenController) {
        this.screenController = screenController;
        name = password = verifyPassword = email = country = gender = phoneNumber = "";
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Image", "*.png"),
                new FileChooser.ExtensionFilter("JPG Image", "*.jpg"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Circle clip = new Circle(userImageID.getFitWidth() / 2, userImageID.getFitHeight() / 2, 50);
        userImageID.setClip(clip);
        image = userImageID.getImage();
        genderCBoxID.setItems(FXCollections.observableArrayList("MALE", "FEMALE"));
        inputsValidation = new InputsValidation(this);
        Platform.runLater(() -> nameTxtID.requestFocus());
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
                user = new UserDTO(1, phoneNumber, name, email, Gender.valueOf(gender), country, birthdate, "Chat User", 1);
                AuthService authService = ServiceLocator.getService(AuthService.class);
                if (!authService.checkPhone(phoneNumber)) {
                    authService.register(user, bytesImage, password);
                    getAlert("You've registired successfully", "Welcome to our application.", Alert.AlertType.INFORMATION);

                    screenController.switchToLoginPhoneScreen();

                } else {
                    getAlert("Invalid Phone Number!", "It seems like you entered a phone number that already exists.", Alert.AlertType.ERROR);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    @FXML
    private void choosePicture(ActionEvent event) {
        File selectedFile = fileChooser.showOpenDialog(null);
        setPicture(selectedFile);
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

    public byte[] extractBytes(Image img) {
        byte[] imageToBytes = null;
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(img, null);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", stream);
            imageToBytes = stream.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return imageToBytes;
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
        bytesImage = extractBytes(image);

        if (!isGenderComboBoxEmpty) {
            gender = (String) genderCBoxID.getValue();
        }

        LocalDate localDate = datePickerID.getValue();
        if (localDate != null) {
            birthdate = Date.valueOf(localDate);
        }
    }
}
