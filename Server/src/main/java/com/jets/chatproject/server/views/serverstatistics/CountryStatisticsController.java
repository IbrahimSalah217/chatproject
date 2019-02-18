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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;

/**
 * FXML Controller class
 *
 * @author salma
 */
public class CountryStatisticsController implements Initializable {

    @FXML
    private PieChart countryPieChart;
    UsersDao userDao;
    List<User> userList;
    List<User> egyptainUserList;
    List<User> otherUserList;
    
    public CountryStatisticsController(DaosFactory daosFactory){
        
        userDao = daosFactory.getUsersDao();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            userList = userDao.findAllUser();
            for(User user : userList){
                if(user.getCountry().toLowerCase() == "egypt"){
                    egyptainUserList.add(user);
                }else{
                    otherUserList.add(user);
                }
            }
            pieChartData.addAll(new PieChart().Data("Egyptain Users", egyptainUserList.size()),
                    new PieChart().Data("Other users", otherUserList.size()));
            countryPieChart.setData(pieChartData);
            countryPieChart.setTitle("Gender Statistics");
            for(PieChart.Data data : countryPieChart.getData()){
                
                Node slice = data.getNode();
                double percentage = (data.getPieValue()/(egyptainUserList.size() + otherUserList.size()))*100;
                String tip = data.getName()+" = "+String.format("%.2f", percentage)+"%";
                Tooltip tooltip = new Tooltip(tip);
                tooltip.install(slice, tooltip);
                
            }
    } catch (Exception ex) {
            Logger.getLogger(GenderStatisticsController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
}
