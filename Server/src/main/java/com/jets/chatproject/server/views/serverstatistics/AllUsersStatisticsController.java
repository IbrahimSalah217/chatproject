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
import javafx.scene.chart.PieChart;

/**
 * FXML Controller class
 *
 * @author salma
 */
public class AllUsersStatisticsController implements Initializable {

    @FXML
    private PieChart countryPieChart;
    UsersDao userDao;
    List<User> userList;
    
    public AllUsersStatisticsController(DaosFactory daosFactory){
        
        userDao = daosFactory.getUsersDao();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            userList = userDao.findAllUser();
        } catch (Exception ex) {
            Logger.getLogger(AllUsersStatisticsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
