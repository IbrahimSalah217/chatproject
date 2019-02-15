/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao.imp;

import com.jets.chatproject.module.rmi.dto.Gender;
import com.jets.chatproject.module.rmi.dto.UserStatus;
import com.jets.chatproject.server.module.dal.cfg.DataSourceFactory;
import com.jets.chatproject.server.module.dal.entities.User;
import java.sql.Date;
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
public class UsersDaoImpTest {

    DataSource dataSource = DataSourceFactory.getDataSource();

    public UsersDaoImpTest() {

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
     * Test of findByPhone method, of class UsersDaoImp.
     */
    @Test
    public void testFindByPhone() {
        System.out.println("findByPhone");
        String phone = "01006663978";
        UsersDaoImp instance = new UsersDaoImp(dataSource);
        User expResult = null;
        User result = instance.findByPhone(phone);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of findById method, of class UsersDaoImp.
     */
    @Test
    public void testFindById() {
        System.out.println("findById");
        int id = 1;
        UsersDaoImp instance = new UsersDaoImp(dataSource);
        User expResult = null;
        User result = instance.findById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of insert method, of class UsersDaoImp.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        User user = new User(1, "01006663978", "hadeer", "hadeer.ayman1994@gmail.com", "123456", Gender.FEMALE, "Egypt", new Date(System.currentTimeMillis()), "jvkjdvbkjd", UserStatus.AVAILABLE, 1);
        UsersDaoImp instance = new UsersDaoImp(dataSource);
        boolean expResult = true;
        boolean result = instance.insert(user);
        assertEquals(expResult, result);
    }
}
