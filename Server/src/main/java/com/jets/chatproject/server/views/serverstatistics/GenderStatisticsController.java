package com.jets.chatproject.server.views.serverstatistics;

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
import java.util.ArrayList;
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
public class GenderStatisticsController implements Initializable {

    @FXML
    private PieChart genderPieChart;
    UsersDao userDao;
    List<User> userList;
    List<User> maleUserList;
    List<User> femaleUserList;
    
    public GenderStatisticsController(DaosFactory daosFactory){
        
        maleUserList = new ArrayList<>();
        femaleUserList = new ArrayList<>();
        userDao = daosFactory.getUsersDao();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            userList = userDao.findAllUser();
            userList.forEach((user) -> {
                if(user.getGender() == Gender.MALE){
                    maleUserList.add(user);
                }else{
                    femaleUserList.add(user);
                }
            });
            
            pieChartData.addAll(new PieChart.Data(Gender.MALE.toString(), maleUserList.size()),
                    new PieChart.Data(Gender.FEMALE.toString(), femaleUserList.size()));
            genderPieChart.setData(pieChartData);
            genderPieChart.setTitle("Gender Statistics");
            genderPieChart.getData().forEach((data) -> {
                Node slice = data.getNode();
                double percentage = (data.getPieValue()/(maleUserList.size() + femaleUserList.size()))*100;
                String tip = data.getName()+" = "+String.format("%.2f", percentage)+"%";
                Tooltip tooltip = new Tooltip(tip);
                tooltip.install(slice, tooltip);
            });
            
        } catch (Exception ex) {
            Logger.getLogger(GenderStatisticsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
