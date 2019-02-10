/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao.imp;

import com.jets.chatproject.server.module.dal.cfg.MyDataSourceFactory;
import com.jets.chatproject.server.module.dal.dao.RequestsDoa;
import com.jets.chatproject.server.module.dal.entities.Request;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author ibrahim
 */
public class RequestDaoImpTest {

    DataSource dataSource;

    public RequestDaoImpTest() {
        dataSource = MyDataSourceFactory.getMySQLDataSource();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testInsert() throws SQLException {
        System.out.println("insert");
        Request request = new Request(10, 20, new Date());
        RequestsDoa instance = new RequestsDaoImp(dataSource);
        instance.insert(request);
        Connection conn = dataSource.getConnection();
        Statement statement = conn.createStatement();
        assertTrue(statement.execute("select * from Requests where sender_id = 10 and receiver_id = 10"));
    }

    @Ignore
    @Test
    public void testDelete() {
        System.out.println("delete");
        Request request = null;
        RequestsDaoImp instance = null;
        instance.delete(request);
    }

    @Ignore
    @Test
    public void testGetAllByReceiver() {
        System.out.println("getAllByReceiver");
        int userId = 0;
        RequestsDaoImp instance = null;
        List<Request> expResult = null;
        List<Request> result = instance.getAllByReceiver(userId);
    }

}
