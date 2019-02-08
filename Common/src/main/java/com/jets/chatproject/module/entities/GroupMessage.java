/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.module.entities;

import java.sql.Time;
import static com.jets.chatproject.module.entities.DirectMessage.TypeMessage;
/**
 *
 * @author Ibrahim
 */
public class GroupMessage {
    private final int messageId;                    //primary Key
    private final User senderUser;                  
    private final Group groupReciever;
    private TypeMessage messageType;                      // "string" or "image" or "file"
    private String content;                         // if message is image it will be URL 
    private String style;                           // "fonttype ** fontsize ** color ** ......."
    private final Time messageTime;                 // created automaticly

    public GroupMessage(int messageId, User senderUser, Group groupReciever, TypeMessage messageType, String content, String style, Time messageTime) {
        this.messageId = messageId;
        this.senderUser = senderUser;
        this.groupReciever = groupReciever;
        this.messageType = messageType;
        this.content = content;
        this.style = style;
        this.messageTime = messageTime;
    }

    public int getMessageId() {
        return messageId;
    }

    public User getSenderUser() {
        return senderUser;
    }

    public Group getGroupReciever() {
        return groupReciever;
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
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.messageId;
        return hash;
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
        final GroupMessage other = (GroupMessage) obj;
        if (this.messageId != other.messageId) {
            return false;
        }
        return true;
    }
    
    
}
