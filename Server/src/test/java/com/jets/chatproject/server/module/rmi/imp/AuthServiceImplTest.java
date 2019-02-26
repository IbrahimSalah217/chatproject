/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.rmi.imp;

import com.jets.chatproject.module.rmi.client.ClientCallback;
import com.jets.chatproject.module.rmi.dto.Gender;
import com.jets.chatproject.module.rmi.dto.UserDTO;
import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import com.jets.chatproject.server.module.dal.dao.imp.DbDaosFactory;
import com.jets.chatproject.server.module.session.DummySessionManager;
import com.jets.chatproject.server.module.session.SessionManager;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Azza
 */
public class AuthServiceImplTest {

    SessionManager sessionManager;
    DaosFactory daosFactory;
    AuthServiceImpl authServiceImpl;

    public AuthServiceImplTest() {
        try {
            sessionManager = new DummySessionManager();
            daosFactory = new DbDaosFactory();
            authServiceImpl = new AuthServiceImpl(daosFactory, sessionManager);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
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
     * Test of login method, of class AuthServiceImpl.
     */
    @Test
    public void testLogin() throws Exception {
        System.out.println("login");
        String phone = "01234567890";
        String password = "hello";
        //AuthServiceImpl instance = new AuthServiceImpl(daosFactory, sessionManager);
        //String expResult = "3";
        String result = authServiceImpl.login(phone, password);
        //assertEquals(expResult.trim(),result.trim());
        assertNotNull(result);
    }

    /**
     * Test of register method, of class AuthServiceImpl.
     */
    @Test
    public void testRegister() throws Exception {
        System.out.println("register");
        if (authServiceImpl.userdao.findByPhone("01234567890") == null) {
            int picId = authServiceImpl.userdao.findByPhone("01234567890").getPictureId();
            UserDTO user = new UserDTO(0, "01055566677", "ahmed", "ahmed@gmail.com", Gender.MALE, "Egypt", new Date(), "bio", 0);
            byte[] picture = authServiceImpl.picturesDao.findById(picId).getData();
            String password = "1234";
            String result = authServiceImpl.register(user, picture, password);
            assertNotNull(result);
        }

    }

    /**
     * Test of checkPhone method, of class AuthServiceImpl.
     */
    @Test
    public void testCheckPhone() throws Exception {
        System.out.println("checkPhone");
        String phone = "01234567890";
        //AuthServiceImpl instance = null;
        boolean expResult = true;
        boolean result = authServiceImpl.checkPhone(phone);
        assertEquals(expResult, result);

        expResult = false;
        result = authServiceImpl.checkPhone("01112223434");
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of registerbyServer method, of class AuthServiceImpl.
     */
    @Test
    public void testRegisterbyServer() throws Exception {
        System.out.println("registerbyServer");
        String phone = "01234567890";
        //AuthServiceImpl instance = null;
        boolean expResult = false;
        boolean result = authServiceImpl.registerbyServer(phone);
        assertEquals(expResult, result);
        expResult = true;
        result = authServiceImpl.registerbyServer("01014348669");
        assertEquals(expResult, result);
    }

}
