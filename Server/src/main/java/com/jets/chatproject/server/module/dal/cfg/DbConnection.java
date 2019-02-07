/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.cfg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ibrahim
 */
public class DbConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/chat_project";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public DbConnection() throws SQLException {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }

}
