/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.rmi.imp;

import com.jets.chatproject.module.rmi.GroupsService;
import com.jets.chatproject.module.rmi.dto.GroupDTO;
import com.jets.chatproject.module.rmi.dto.GroupMemberDTO;
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
public class GroupsServiceImpTest {

    SessionManager sessionManager;
    DaosFactory daosFactory;
    GroupsService groupsService;

    public GroupsServiceImpTest() {
        try {
            sessionManager = new DummySessionManager();
            daosFactory = new DbDaosFactory();
            groupsService
                    = new GroupsServiceImp(daosFactory, sessionManager);
        } catch (RemoteException ex) {
            Logger.getLogger(GroupsServiceImpTest.class.getName()).log(Level.SEVERE, null, ex);
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
     * Test of createGroup method, of class GroupsServiceImp.
     */
    @Test
    public void testCreateGroup() throws Exception {
        System.out.println("createGroup");
        String session = "3";
        String groupName = "family";
        byte[] groupPicture = daosFactory.getPicturesDao().findById(3).getData();
        //GroupsServiceImp instance = null;
        int expResult = 0;
        int result = groupsService.createGroup(session, groupName, groupPicture);
        assertNotEquals(expResult, result);
    }

    /**
     * Test of addGroupMember method, of class GroupsServiceImp.
     */
    @Test
    public void testAddGroupMember() throws Exception {
        System.out.println("addGroupMember");
        String session = "3";
        if (groupsService.getAllGroups(session).size() > 0) {
            int groupId = groupsService.getAllGroups(session).get(0).getId();
            int userId = 2;
            int oldSize = groupsService.getGroupMembers(session, groupId).size();
            boolean check = true;
            for (int i = 0; i < oldSize; i++) {
                if (groupsService.getGroupMembers(session, groupId).get(i).getMemberId() == userId) {
                    check = false;
                }
            }
            if (check) {
                groupsService.addGroupMember(session, groupId, userId);
                assertEquals(oldSize + 1, groupsService.getGroupMembers(session, groupId).size());
            }
        }
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of getAllGroups method, of class GroupsServiceImp.
     */
    @Test
    public void testGetAllGroups() throws Exception {
        System.out.println("getAllGroups");
        String session = "3";
        List<GroupDTO> result = groupsService.getAllGroups(session);
        assertFalse(result.size()<0);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getGroupMembers method, of class GroupsServiceImp.
     */
    @Test
    public void testGetGroupMembers() throws Exception {
        System.out.println("getGroupMembers");
        String session = "3";
        if (groupsService.getAllGroups(session).size() > 0) {
            int groupId = groupsService.getAllGroups(session).get(0).getId();
            int userId = 2;
            int oldSize = groupsService.getGroupMembers(session, groupId).size();
            boolean check = true;
            for (int i = 0; i < oldSize; i++) {
                if (groupsService.getGroupMembers(session, groupId).get(i).getMemberId() == userId) {
                    check = false;
                }
            }
            if (check) {
                groupsService.addGroupMember(session, groupId, userId);
                assertEquals(oldSize + 1, groupsService.getGroupMembers(session, groupId).size());
            }
        }
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

}
