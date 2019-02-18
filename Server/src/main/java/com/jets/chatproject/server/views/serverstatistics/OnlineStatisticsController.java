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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

/**
 * FXML Controller class
 *
 * @author salma
 */
public class OnlineStatisticsController implements Initializable {

    @FXML
    private PieChart onlinePieChart;
    UsersDao userDao;
    List<User> userList;

    public OnlineStatisticsController(DaosFactory daosFactory) {
        
        userDao = daosFactory.getUsersDao();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        userList = userDao.findAllUser();
    }

}
