package com.jets.chatproject.server.views.serverstatistics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
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
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        xAxis.setLabel("Users");
        yAxis.setLabel("categories");
        
    }    
    
}
