/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.module.entities;


import java.sql.Time;
import java.time.LocalTime;

/**
 *
 * @author Ibrahim
 */
public class DirectMessage {
    private final int messageId;                        //primery key
    private final User minUser;                         // user with min ID
    private final User maxUser;                         
    private final User senderUser;                   // min or max user
    private TypeMessage messageType;                      // "string" or "image" or "file"
    private String content;                         // if message is image it will be URL 
    private String style;                           // "fonttype ** fontsize ** color ** ......."
    private final Time messageTime;                 // created automaticly

    public DirectMessage(int messageId, User minUser, User maxUser, User senderUser, TypeMessage messageType, String content, String style) {
        this.messageId = messageId;
        this.minUser = minUser;
        this.maxUser = maxUser;
        this.senderUser = senderUser;
        this.messageType = messageType;
        this.content = content;
        this.style = style;
        this.messageTime = Time.valueOf(LocalTime.now()); // may take from object creator  this.messageTime = messageTime;
    }

    public int getMessageId() {
        return messageId;
    }

    public User getMinUser() {
        return minUser;
    }

    public User getMaxUser() {
        return maxUser;
    }

    public User getSenderUser() {
        return senderUser;
    }

    public TypeMessage getMessageType() {
        return messageType;
    }

    public String getContent() {
        return content;
    }

    public String getStyle() {
        return style;
    }

    public Time getMessageTime() {
        return messageTime;
    }

    public void setMessageType(TypeMessage messageType) {
        this.messageType = messageType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStyle(String style) {
        this.style = style;
    }
    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DirectMessage other = (DirectMessage) obj;
        if (this.messageId != other.messageId) {
            return false;
        }
        return true;
    }
    public static enum TypeMessage{
        STRING,IMAGE,FILE
    }
}
