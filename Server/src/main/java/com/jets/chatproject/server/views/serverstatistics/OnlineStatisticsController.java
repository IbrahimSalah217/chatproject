package com.jets.chatproject.server.views.serverstatistics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jets.chatproject.module.rmi.dto.UserStatus;
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
public class OnlineStatisticsController implements Initializable {

    @FXML
    private PieChart onlinePieChart;
    UsersDao userDao;
    List<User> userList;
    List<User> onlineUserList;
    List<User> offlineUserList;
    List<User> busyUserList;
    List<User> awayUserList;

    public OnlineStatisticsController(DaosFactory daosFactory) {

        onlineUserList = new ArrayList<>();
        offlineUserList = new ArrayList<>();
        busyUserList = new ArrayList<>();
        awayUserList = new ArrayList<>();
        userDao = daosFactory.getUsersDao();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            userList = userDao.findAllUser();
            userList.forEach((user) -> {
                if (user.getState() == UserStatus.AVAILABLE) {
                    onlineUserList.add(user);
                } else if (user.getState() == UserStatus.OFFLINE) {
                    offlineUserList.add(user);
                }else if (user.getState() == UserStatus.BUSY) {
                    busyUserList.add(user);
                }else if (user.getState() == UserStatus.AWAY) {
                    awayUserList.add(user);
                }
                
            });
            pieChartData.addAll(new PieChart.Data(UserStatus.AVAILABLE.toString(), onlineUserList.size()),
                    new PieChart.Data(UserStatus.OFFLINE.toString(), offlineUserList.size()),
                    new PieChart.Data(UserStatus.AWAY.toString(), awayUserList.size()),
                    new PieChart.Data(UserStatus.BUSY.toString(), busyUserList.size()));
            onlinePieChart.setData(pieChartData);
            onlinePieChart.setTitle("Gender Statistics");
            onlinePieChart.getData().forEach((data) -> {
                Node slice = data.getNode();
                double percentage = (data.getPieValue()/(onlineUserList.size() + offlineUserList.size()+
                        awayUserList.size() + busyUserList.size()))*100;
                String tip = data.getName()+" = "+String.format("%.2f", percentage)+"%";
                Tooltip tooltip = new Tooltip(tip);
                tooltip.install(slice, tooltip);
            });
        } catch (Exception ex) {
            Logger.getLogger(OnlineStatisticsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
