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

    @Test
    public void testSetBold() {
        System.out.println("setBold");
        boolean bold = true;
        MessageFormat instance = new MessageFormat();
        instance.setBold(bold);
        assertEquals(bold, instance.isBold());
    }

    @Test
    public void testSetItalic() {
        System.out.println("setItalic");
        boolean italic = true;
        MessageFormat instance = new MessageFormat();
        instance.setItalic(italic);
        assertEquals(italic, instance.isItalic());
    }

    @Test
    public void testSetUnderline() {
        System.out.println("setUnderline");
        boolean underline = true;
        MessageFormat instance = new MessageFormat();
        instance.setUnderline(underline);
        assertEquals(underline, instance.isUnderline());
    }

    @Test
    public void testSetTextColor() {
        System.out.println("setTextColor");
        int textColor = 0xff00ff;
        MessageFormat instance = new MessageFormat();
        instance.setTextColor(textColor);
        assertEquals(textColor, instance.getTextColor());
    }

    @Test
    public void testSetBackgroundColor() {
        System.out.println("setBackgroundColor");
        int backgroundColor = 0xff00ff;
        MessageFormat instance = new MessageFormat();
        instance.setBackgroundColor(backgroundColor);
        assertEquals(backgroundColor, instance.getBackgroundColor());
    }

    @Test
    public void testSetFontSize() {
        System.out.println("setFontSize");
        int fontSize = 20;
        MessageFormat instance = new MessageFormat();
        instance.setFontSize(fontSize);
        assertEquals(fontSize, instance.getFontSize());
    }

}
