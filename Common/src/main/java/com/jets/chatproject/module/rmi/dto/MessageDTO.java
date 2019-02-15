/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.module.rmi.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author ibrahim
 */
public class MessageDTO implements Serializable {

    private final int id;
    private final int senderId;
    private String senderName;
    private MessageType type;
    private String content;
    private MessageFormat format;
    private Timestamp timestamp;

    public MessageDTO(int id, int senderId) {
        this.id = id;
        this.senderId = senderId;
    }

    public MessageDTO(int id, int senderId, String senderName, MessageType type,
            String content, MessageFormat format, Timestamp timestamp) {
        this.id = id;
        this.senderId = senderId;
        this.senderName = senderName;
        this.type = type;
        this.content = content;
        this.format = format;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public int getSenderId() {
        return senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageFormat getFormat() {
        return format;
    }

    public void setFormat(MessageFormat format) {
        this.format = format;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.id;
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
        final MessageDTO other = (MessageDTO) obj;
        return this.id == other.id;
    }

}
