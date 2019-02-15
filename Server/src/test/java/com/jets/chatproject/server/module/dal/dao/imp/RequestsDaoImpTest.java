/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao.imp;

import com.jets.chatproject.server.module.dal.cfg.DataSourceFactory;
import com.jets.chatproject.server.module.dal.entities.Request;
import java.util.List;
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
 * @author Hadeer
 */
@Ignore
public class RequestsDaoImpTest {

    DataSource dataSource = DataSourceFactory.getDataSource();

    public RequestsDaoImpTest() {
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
     * Test of findAllByReceiver method, of class RequestsDaoImp.
     */
    @Test
    public void testFindAllByReceiver() {
        System.out.println("findAllByReceiver");
        int userId = 0;
        RequestsDaoImp instance = null;
        List<Request> expResult = null;
        List<Request> result = instance.findAllByReceiver(userId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insert method, of class RequestsDaoImp.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        Request object = null;
        RequestsDaoImp instance = null;
        boolean expResult = false;
        boolean result = instance.insert(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class RequestsDaoImp.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        Request object = null;
        RequestsDaoImp instance = null;
        boolean expResult = false;
        boolean result = instance.update(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class RequestsDaoImp.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        Request object = null;
        RequestsDaoImp instance = null;
        boolean expResult = false;
        boolean result = instance.delete(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
