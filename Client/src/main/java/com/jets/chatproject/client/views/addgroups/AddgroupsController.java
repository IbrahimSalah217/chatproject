/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.views.addgroups;

import com.jets.chatproject.client.cfg.ServiceLocator;
import com.jets.chatproject.client.controller.ScreenController;
import com.jets.chatproject.module.rmi.AuthService;
import com.jets.chatproject.module.rmi.GroupsService;
import com.jets.chatproject.module.rmi.UsersService;
import com.jets.chatproject.module.rmi.dto.UserDTO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import org.omg.PortableServer.ServantLocator;

/**
 * FXML Controller class
 *
 * @author Ibrahim
 */
public class AddgroupsController implements Initializable {

    @FXML
    private TextField groupNameTxt;
    @FXML
    private Button chossePicBtn;
    @FXML
    private TextField memberTxtField;
    @FXML
    private ImageView addMemberBtn;
    @FXML
    private ImageView removeMemberBtn;
    @FXML
    private ImageView submitView;
    @FXML
    private ListView<String> membersList;
    @FXML
    private ImageView groupImg;

    ScreenController screenController;
    String userSession;
    String userPhone;
    int groupId;
    Image image;
    byte[] bytesImage;

    FileChooser fileChooser;
    AuthService authService;
    GroupsService groupsService;
    UsersService usersService;
    ObservableList<String> groupMembers;

    public AddgroupsController(ScreenController screenController) {
        try {
            this.screenController = screenController;
            this.userPhone = screenController.getPhone();
            this.userSession = screenController.getSession();

            groupMembers = FXCollections.observableArrayList();

            authService = ServiceLocator.getService(AuthService.class);
            groupsService = ServiceLocator.getService(GroupsService.class);
            usersService = ServiceLocator.getService(UsersService.class);

            fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PNG Image", "*.png"),
                    new FileChooser.ExtensionFilter("JPG Image", "*.jpg"));
        } catch (Exception ex) {
            Logger.getLogger(AddgroupsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        membersList.setItems(groupMembers);
    }

    @FXML
    private void choosePicAction(ActionEvent event) {
        File selectedFile = fileChooser.showOpenDialog(null);
        setPicture(selectedFile);

    }

    @FXML
    private void addMemberAction(MouseEvent event) {
        try {
            boolean isExist = authService.checkPhone(memberTxtField.getText());
            if (checkPhoneNumber(memberTxtField.getText())) {
                if (isExist) {
                    groupMembers.add(memberTxtField.getText());
                    memberTxtField.clear();
                } else {
                    getAlert("Invalid Phone Number!", "It seems like you entered a phone number that's not exist.", Alert.AlertType.ERROR);
                }

            } else {
                getAlert("Invalid Phone Number!", "Please,enter the correct phone Number", Alert.AlertType.ERROR);

            }
        } catch (RemoteException ex) {
            Logger.getLogger(AddgroupsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void removeMemberAction(MouseEvent event) {
        if (checkPhoneNumber(memberTxtField.getText())) {
            if (groupMembers.contains(memberTxtField.getText())) {
                groupMembers.remove(memberTxtField.getText());
                memberTxtField.clear();
            } else {
                getAlert("Not Exist", "It seems like you entered a phone number that's not exist in Group Members", Alert.AlertType.ERROR);
            }

        } else {
            getAlert("Invalid Phone Number!", "Please,enter the correct phone Number", Alert.AlertType.ERROR);

        }
    }

    @FXML
    private void SubmitAction(MouseEvent event) {
        getData();
        try {
            groupId = groupsService.createGroup(userSession, groupNameTxt.getText(), bytesImage);
            groupMembers.stream().forEach(s -> {
                try {
                    UserDTO user = usersService.getProfileByPhone(userSession, memberTxtField.getText());
                    groupsService.addGroupMember(userSession, groupId, user.getId());
                    getAlert("The Group Has Successfully Created", "", Alert.AlertType.INFORMATION);
                } catch (RemoteException ex) {
                    Logger.getLogger(AddgroupsController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
        } catch (RemoteException ex) {
            Logger.getLogger(AddgroupsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setPicture(File selectedFile) {
        if (selectedFile != null) {
            image = new Image(selectedFile.toURI().toString());
            groupImg.setImage(image);
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

    public boolean checkPhoneNumber(String phoneNumber) {
        boolean isCorrect = true;
        Pattern pattern = Pattern.compile("\\d{11}");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (!matcher.matches()) {
            isCorrect = false;
        }
        return isCorrect;
    }

    private void getAlert(String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void getData() {
        bytesImage = extractBytes(image);
    }

}
