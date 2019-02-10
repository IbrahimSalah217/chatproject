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
import org.junit.Ignore;

/**
 *
 * @author ibrahim
 */
public class MessageFormatTest {
    
    public MessageFormatTest() {
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

    @Ignore
    @Test
    public void testSetBold() {
        System.out.println("setBold");
        boolean bold = false;
        MessageFormat instance = new MessageFormat();
        instance.setBold(bold);
        fail("The test case is a prototype.");
    }

    @Ignore
    @Test
    public void testSetItalic() {
        System.out.println("setItalic");
        boolean italic = false;
        MessageFormat instance = new MessageFormat();
        instance.setItalic(italic);
        fail("The test case is a prototype.");
    }

    @Ignore
    @Test
    public void testSetUnderline() {
        System.out.println("setUnderline");
        boolean underline = false;
        MessageFormat instance = new MessageFormat();
        instance.setUnderline(underline);
        fail("The test case is a prototype.");
    }

    @Ignore
    @Test
    public void testSetTextColor() {
        System.out.println("setTextColor");
        int textColor = 0;
        MessageFormat instance = new MessageFormat();
        instance.setTextColor(textColor);
        fail("The test case is a prototype.");
    }

    @Ignore
    @Test
    public void testSetBackgroundColor() {
        System.out.println("setBackgroundColor");
        int backgroundColor = 0;
        MessageFormat instance = new MessageFormat();
        instance.setBackgroundColor(backgroundColor);
        fail("The test case is a prototype.");
    }

    @Ignore
    @Test
    public void testSetFontSize() {
        System.out.println("setFontSize");
        int fontSize = 0;
        MessageFormat instance = new MessageFormat();
        instance.setFontSize(fontSize);
        fail("The test case is a prototype.");
    }

    @Ignore
    @Test
    public void testToString() {
        System.out.println("toString");
        MessageFormat instance = new MessageFormat();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
    
}
