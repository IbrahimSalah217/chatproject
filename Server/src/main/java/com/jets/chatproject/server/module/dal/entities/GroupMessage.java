/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.entities;

import com.jets.chatproject.module.rmi.dto.MessageType;
import java.sql.Time;

/**
 *
 * @author Ibrahim
 */
public class GroupMessage {

    private final int messageId;
    private final User sender;
    private final Group group;
    private MessageType messageType;
    private String content;
    private String style;
    private Time messageTime;

    public GroupMessage(int messageId, User sender, Group group, MessageType messageType, String content, String style, Time messageTime) {
        this.messageId = messageId;
        this.sender = sender;
        this.group = group;
        this.messageType = messageType;
        this.content = content;
        this.style = style;
        this.messageTime = messageTime;
    }

    public int getMessageId() {
        return messageId;
    }

    public User getSender() {
        return sender;
    }

    public Group getGroup() {
        return group;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public Time getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Time messageTime) {
        this.messageTime = messageTime;
    }

}
