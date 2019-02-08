/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.module.rmi.entities;

/**
 *
 * @author ibrahim
 */
public class MessageFormat {

    private boolean bold;
    private boolean italic;
    private boolean underline;
    private int textColor;
    private int backgroundColor;
    private int fontSize;

    public MessageFormat() {
        this.bold = false;
        this.italic = false;
        this.underline = false;
        this.textColor = 0xFF000000;
        this.backgroundColor = 0x00FFFFFF;
        this.fontSize = 12;
    }

    public MessageFormat(String format) throws Exception {
        if (!format.matches("[Bb][Ii][Uu]:[0-9]+:[0-9]+:[0-9]+")) {
            throw new Exception();
        }
        String[] components = format.split(":");
        this.bold = components[0].charAt(0) == 'B';
        this.italic = components[0].charAt(0) == 'I';
        this.underline = components[0].charAt(0) == 'U';
        try {
            textColor = Integer.valueOf(components[1]);
            backgroundColor = Integer.valueOf(components[2]);
            fontSize = Integer.valueOf(components[3]);
        } catch (NumberFormatException ex) {
            throw new Exception(ex);
        }
    }

    public MessageFormat(boolean bold, boolean italic, boolean underline, int textColor, int backgroundColor, int fontSize) {
        this.bold = bold;
        this.italic = italic;
        this.underline = underline;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        this.fontSize = fontSize;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    @Override
    public String toString() {
        return String.format("%c%c%c:%d:%d:%d", bold ? "B" : "b",
                italic ? "I" : "i", underline ? "U" : "u",
                textColor, backgroundColor, fontSize);
    }

}
