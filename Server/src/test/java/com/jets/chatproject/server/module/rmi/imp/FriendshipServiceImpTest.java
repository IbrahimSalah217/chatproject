/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.rmi.imp;

import com.jets.chatproject.module.rmi.FriendRequestsService;
import com.jets.chatproject.module.rmi.FriendshipService;
import com.jets.chatproject.module.rmi.dto.FriendshipDTO;
import com.jets.chatproject.server.module.dal.dao.DaosFactory;
import com.jets.chatproject.server.module.dal.dao.imp.DbDaosFactory;
import com.jets.chatproject.server.module.session.DummySessionManager;
import com.jets.chatproject.server.module.session.SessionManager;
import java.rmi.RemoteException;
import java.util.List;
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
public class FriendshipServiceImpTest {

    SessionManager sessionManager;
    DaosFactory daosFactory;
    FriendshipService friendshipService;
    FriendRequestsService friendRequestsService;

    public FriendshipServiceImpTest() {
        try {
            sessionManager = new DummySessionManager();
            daosFactory = new DbDaosFactory();
            friendshipService
                    = new FriendshipServiceImp(daosFactory, sessionManager);
            friendRequestsService
                    = new FriendRequestsServiceImpl(daosFactory, sessionManager);
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
     * Test of getAllFriendships method, of class FriendshipServiceImp.
     */
    @Test
    public void testGetAllFriendships() throws Exception {
        System.out.println("getAllFriendships");
        String session = "1";
        //FriendshipServiceImp instance = null;
        //List<FriendshipDTO> expResult = friendshipService.getAllFriendships(session);
        List<FriendshipDTO> result = friendshipService.getAllFriendships(session);
        assertFalse(result.size() < 3);
        /// friendshipService.getAllFriendships(session).remove(friendshipService.getAllFriendships(session).size()-1);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of areFriends method, of class FriendshipServiceImp.
     */
    @Test
    public void testAreFriends() throws Exception {
        System.out.println("areFriends");
        String session = "2";
        String phone = "01014348668";
        boolean expResult = true;
        boolean result = friendshipService.areFriends(session, phone);
        assertEquals(expResult, result);

        phone = "01001248247";
        expResult = false;
        result = friendshipService.areFriends(session, phone);
        assertEquals(expResult, result);
    }

}
