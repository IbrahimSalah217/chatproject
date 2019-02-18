/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.module.rmi.dto;

import java.io.Serializable;

/**
 *
 * @author ibrahim
 */
public class MessageFormat implements Serializable {

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
        this.textColor = 0x000000FF;
        this.backgroundColor = 0xFFFFFF00;
        this.fontSize = 12;
    }

    public static MessageFormat of(String format) {
        MessageFormat messageFormat = new MessageFormat();
        if (format.matches("[Bb][Ii][Uu]:[0-9a-fA-F]{8}:[0-9a-fA-F]{8}:[0-9]+")) {
            String[] components = format.split(":");
            messageFormat.bold = components[0].charAt(0) == 'B';
            messageFormat.italic = components[0].charAt(0) == 'I';
            messageFormat.underline = components[0].charAt(0) == 'U';
            messageFormat.textColor = (int)Long.parseLong(components[1], 16);
            messageFormat.backgroundColor = (int)Long.parseLong(components[2], 16);
            messageFormat.fontSize = Integer.valueOf(components[3]);
        }
        return messageFormat;
    }

    public MessageFormat(boolean bold, boolean italic, boolean underline, int textColor, int backgroundColor, int fontSize) {
        this.bold = bold;
        this.italic = italic;
        this.underline = underline;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        this.fontSize = fontSize;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean isUnderline() {
        return underline;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    @Override
    public String toString() {
        return String.format("%c%c%c:%s:%s:%d", bold ? 'B' : 'b',
                italic ? 'I' : 'i', underline ? 'U' : 'u',
                Integer.toHexString(textColor), Integer.toHexString(backgroundColor),
                fontSize);
    }

}
