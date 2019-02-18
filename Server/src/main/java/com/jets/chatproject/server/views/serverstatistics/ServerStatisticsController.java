package com.jets.chatproject.server.views.serverstatistics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import com.jets.chatproject.server.module.dal.dao.UsersDao;
import com.jets.chatproject.server.module.dal.entities.User;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

/**
 * FXML Controller class
 *
 * @author salma
 */
public class ServerStatisticsController implements Initializable {

    @FXML
    private BarChart barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    UsersDao userDao;
    List<User> userList;

    public ServerStatisticsController(DaosFactory daosFactory) {
        userDao = daosFactory.getUsersDao();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            xAxis.setLabel("Users");
            yAxis.setLabel("categories");
            userList = userDao.findAllUser();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
