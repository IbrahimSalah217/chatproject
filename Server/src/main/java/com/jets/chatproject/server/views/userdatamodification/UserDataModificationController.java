package com.jets.chatproject.server.views.userdatamodification;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jets.chatproject.module.rmi.dto.Gender;
import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
import com.jets.chatproject.server.module.dal.entities.User;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 * FXML Controller class
 *
 * @author salma
 */
public class UserDataModificationController implements Initializable {

    @FXML
    private TableView<User> tableInfo;
    @FXML
    private TableColumn<User, Integer> userId;
    @FXML
    private TableColumn<User, String> userPhoneNumber;
    @FXML
    private TableColumn<User, String> userName;
    @FXML
    private TableColumn<User, String> userEmail;
    @FXML
    private TableColumn<User, Gender> userGender;
    @FXML
    private TableColumn<User, String> userCountry;
    @FXML
    private TableColumn<User, Date> userDateOfBirth;
    ObservableList<User> userList;
    List<User> userEntityList;
    UsersDao userdao;

    public UserDataModificationController(DaosFactory daosFactory) {

        userdao = daosFactory.getUsersDao();
        userList = FXCollections.observableArrayList();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
        initcols();
    }

    private void initcols() {

        userPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        userName.setCellValueFactory(new PropertyValueFactory<>("displyName"));
        userEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        userGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        userCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        userDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        editablecols();
    }

    private void editablecols() {

        userPhoneNumber.setEditable(false);
        userName.setCellFactory(TextFieldTableCell.forTableColumn());
        userName.setOnEditCommit((e) -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setDisplyName(e.getNewValue());
        });
        userEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        userEmail.setOnEditCommit((e) -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setEmail(e.getNewValue());
        });
        userGender.setCellFactory((column) -> {
            TableCell<User, Gender> cell = new TableCell<User, Gender>() {
                @Override
                protected void updateItem(Gender item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item.toString());
                    }
                }

            };
            return cell;
        });
        userGender.setOnEditCommit((e) -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setGender(e.getNewValue());
        });
        userCountry.setCellFactory(TextFieldTableCell.forTableColumn());
        userCountry.setOnEditCommit((e) -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setCountry(e.getNewValue());
        });
        userDateOfBirth.setCellFactory((column) -> {
            TableCell<User, Date> cell = new TableCell<User, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(format.format(item));
                    }
                }
            };

            return cell;
        });
        userDateOfBirth.setOnEditCommit((e) -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setDateOfBirth(e.getNewValue());
        });
        tableInfo.setEditable(true);
    }

    private void loadData() {

        try {
            userEntityList = userdao.findAllUser();
        } catch (Exception ex) {
            Logger.getLogger(UserDataModificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        userEntityList.forEach((user) -> {
            userList.add(user);
        });
        tableInfo.setItems(userList);
    }

}
