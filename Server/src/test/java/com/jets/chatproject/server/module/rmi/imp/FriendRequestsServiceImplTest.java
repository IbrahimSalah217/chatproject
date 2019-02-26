/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.rmi.imp;

import com.jets.chatproject.module.rmi.FriendRequestsService;
import com.jets.chatproject.module.rmi.dto.RequestDTO;
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
public class FriendRequestsServiceImplTest {
    FriendRequestsService friendRequestsService;
    SessionManager sessionManager;
    DaosFactory daosFactory;
    public FriendRequestsServiceImplTest() {
        try {
            sessionManager = new DummySessionManager();
            daosFactory = new DbDaosFactory();
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
     * Test of sendRequest method, of class FriendRequestsServiceImpl.
     */
    @Test
    public void testSendRequest() throws Exception {
        System.out.println("sendRequest");
        String session = "1";
        String phone = "01014348669";
        friendRequestsService.sendRequest(session, phone);
        friendRequestsService.rejectRequest("6", 1);
    }

    /**
     * Test of getAllRequests method, of class FriendRequestsServiceImpl.
     */
    @Test
    public void testGetAllRequests() throws Exception {
        System.out.println("getAllRequests");
        String session = "3";
        List<RequestDTO> result = friendRequestsService.getAllRequests(session);
        if(result.size() ==1)
            assertEquals(1, result.size());
        else
            assertFalse(result.size()<0);
    }

    /**
     * Test of acceptRequest method, of class FriendRequestsServiceImpl.
     */
    @Test
    public void testAcceptRequest() throws Exception {
        System.out.println("acceptRequest");
        String session = "3";
        int oldSize =friendRequestsService.getAllRequests(session).size();
        if(oldSize >0)
        {
            int senderId =  friendRequestsService.getAllRequests(session).get(0).getSenderId();
            friendRequestsService.acceptRequest(session, senderId);
            assertEquals(oldSize -1, friendRequestsService.getAllRequests(session).size());
        }
         
    }

    /**
     * Test of rejectRequest method, of class FriendRequestsServiceImpl.
     */
    @Test
    public void testRejectRequest() throws Exception {
        System.out.println("rejectRequest");
         String session = "3";
        int oldSize =friendRequestsService.getAllRequests(session).size();
        if(oldSize >0)
        {
            int senderId =  friendRequestsService.getAllRequests(session).get(0).getSenderId();
            friendRequestsService.rejectRequest(session, senderId);
            assertEquals(oldSize -1, friendRequestsService.getAllRequests(session).size());
        }
    }
    
}
