/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao.imp;

import com.jets.chatproject.server.module.dal.cfg.DataSourceFactory;
import com.jets.chatproject.server.module.dal.entities.GroupMessage;
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
public class GroupMessagesDaoImpTest {
    DataSource dataSource = DataSourceFactory.getDataSource();
    
    public GroupMessagesDaoImpTest() {
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
     * Test of getLastMessage method, of class GroupMessagesDaoImp.
     */
    @Test
    public void testGetLastMessage() {
        System.out.println("getLastMessage");
        int groupId = 0;
        GroupMessagesDaoImp instance = new GroupMessagesDaoImp(dataSource);
        GroupMessage expResult = null;
        GroupMessage result = instance.getLastMessage(groupId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getAllGroupMessages method, of class GroupMessagesDaoImp.
     */
    @Test
    public void testGetAllGroupMessages() {
        System.out.println("getAllGroupMessages");
        int groupId = 0;
        GroupMessagesDaoImp instance = new GroupMessagesDaoImp(dataSource);
        ArrayList<GroupMessage> expResult = null;
        ArrayList<GroupMessage> result = instance.getAllGroupMessages(groupId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of insert method, of class GroupMessagesDaoImp.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        GroupMessage groupMessage = null;
        GroupMessagesDaoImp instance = new GroupMessagesDaoImp(dataSource);
        boolean expResult = false;
        boolean result = instance.insert(groupMessage);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }
  
    
}
