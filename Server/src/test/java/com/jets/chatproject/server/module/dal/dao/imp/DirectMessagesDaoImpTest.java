/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao.imp;

import com.jets.chatproject.server.module.dal.cfg.DataSourceFactory;
import com.jets.chatproject.server.module.dal.entities.DirectMessage;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Hadeer
 */
public class DirectMessagesDaoImpTest {
    DataSource dataSource = DataSourceFactory.getDataSource();

    
    public DirectMessagesDaoImpTest() {
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

    /**
     * Test of getLastDirectMessage method, of class DirectMessagesDaoImp.
     */
    @Test
    public void testGetLastDirectMessage() {
        System.out.println("getLastDirectMessage");
        int userId = 0;
        int anotherUserId = 0;
        DirectMessagesDaoImp instance = new DirectMessagesDaoImp(dataSource);
        DirectMessage expResult = null;
        DirectMessage result = instance.getLastDirectMessage(userId, anotherUserId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getAllDirectMessages method, of class DirectMessagesDaoImp.
     */
    @Test
    public void testGetAllDirectMessages() {
        System.out.println("getAllDirectMessages");
        int userId = 0;
        int anotherUserId = 0;
        DirectMessagesDaoImp instance = null;
        ArrayList<DirectMessage> expResult = null;
        ArrayList<DirectMessage> result = instance.getAllDirectMessages(userId, anotherUserId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of insert method, of class DirectMessagesDaoImp.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        DirectMessage directMessage = null;
        DirectMessagesDaoImp instance = null;
        boolean expResult = false;
        boolean result = instance.insert(directMessage);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class DirectMessagesDaoImp.
     */
   
    
    
}
