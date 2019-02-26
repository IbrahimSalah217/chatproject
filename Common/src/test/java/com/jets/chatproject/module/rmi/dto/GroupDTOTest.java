/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.module.rmi.dto;

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
public class GroupDTOTest {
    
    public GroupDTOTest() {
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
     * Test of getId method, of class GroupDTO.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        GroupDTO instance = new GroupDTO(0);
        int expResult = 0;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAdminId method, of class GroupDTO.
     */
    @Test
    public void testGetAdminId() {
        System.out.println("getAdminId");
        GroupDTO instance = new GroupDTO(1, 1, "testGroup", 0, new MessageDTO(1, 3));
        int expResult = 1;
        int result = instance.getAdminId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAdminId method, of class GroupDTO.
     */
    @Test
    public void testSetAdminId() {
        System.out.println("setAdminId");
        int adminId = 0;
        GroupDTO instance = new GroupDTO(1);
        instance.setAdminId(2);
        assertEquals(2, instance.getAdminId());
    }

    /**
     * Test of getDisplayName method, of class GroupDTO.
     */
    @Test
    public void testGetDisplayName() {
        System.out.println("getDisplayName");
        GroupDTO instance = new GroupDTO(1);
        instance.setDisplayName("testName");
        String expResult = "testName";
        String result = instance.getDisplayName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDisplayName method, of class GroupDTO.
     */
    @Test
    public void testSetDisplayName() {
        System.out.println("setDisplayName");
        String displayName = "testName";
        GroupDTO instance = new GroupDTO(1);
        instance.setDisplayName(displayName);
        assertEquals(displayName, instance.getDisplayName());
    }

    /**
     * Test of getPictureId method, of class GroupDTO.
     */
    @Test
    public void testGetPictureId() {
        System.out.println("getPictureId");
        GroupDTO instance = new GroupDTO(1);
        instance.setPictureId(0);
        int expResult = 0;
        int result = instance.getPictureId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPictureId method, of class GroupDTO.
     */
    @Test
    public void testSetPictureId() {
        System.out.println("setPictureId");
        int pictureId = 0;
        GroupDTO instance = new GroupDTO(1);
        instance.setPictureId(pictureId);
        assertEquals(pictureId, instance.getPictureId());
    }

    /**
     * Test of getLastMessage method, of class GroupDTO.
     */
    @Test
    public void testGetLastMessage() {
        System.out.println("getLastMessage");
        GroupDTO instance = new GroupDTO(1);
        MessageDTO lastMessage = new MessageDTO(1, 3);
        instance.setLastMessage(lastMessage);
        
        MessageDTO result = instance.getLastMessage();
        assertEquals(lastMessage, result);
    }

    /**
     * Test of setLastMessage method, of class GroupDTO.
     */
    @Test
    public void testSetLastMessage() {
        System.out.println("setLastMessage");
        MessageDTO lastMessage = new MessageDTO(1, 3);
        GroupDTO instance = new GroupDTO(1);
        
        instance.setLastMessage(lastMessage);
        assertEquals(lastMessage, instance.getLastMessage());
    }

    /**
     * Test of hashCode method, of class GroupDTO.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        GroupDTO instance = new GroupDTO(1);
        int expResult = 130;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class GroupDTO.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new GroupDTO(1);
        GroupDTO instance = new GroupDTO(1);
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }
    
}
