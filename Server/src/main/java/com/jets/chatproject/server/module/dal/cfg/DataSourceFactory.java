/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.cfg;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.sql.DataSource;

/**
 *
 * @author ibrahim
 */
public class DataSourceFactory {

    static DataSource dataSource = createDataSource();

    private static DataSource createDataSource() {
        Properties prop = new Properties();
        MysqlDataSource mySqlDataSource = null;
        try (FileInputStream fis = new FileInputStream("db.properties")) {
            prop.load(fis);
            mySqlDataSource = new MysqlDataSource();
            mySqlDataSource.setURL(prop.getProperty("MYSQL_DB_URL"));
            mySqlDataSource.setUser(prop.getProperty("MYSQL_DB_USERNAME"));
            mySqlDataSource.setPassword(prop.getProperty("MYSQL_DB_PASSWORD"));
        } catch (IOException e) {
            throw new RuntimeException("Can't find db.properties", e);
        }
        return mySqlDataSource;
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
