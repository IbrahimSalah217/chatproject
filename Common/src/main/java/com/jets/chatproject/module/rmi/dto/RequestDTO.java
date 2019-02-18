/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.module.rmi.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ibrahim
 */
public class RequestDTO implements Serializable {

    private final int senderId;
    private String senderName;
    private int senderPictureId;
    private Date timestamp;

    public RequestDTO(int senderId) {
        this.senderId = senderId;
    }

    public RequestDTO(int senderId, String senderName, int senderPictureId, Date timestamp) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderPictureId = senderPictureId;
        this.timestamp = timestamp;
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

    public int getSenderPictureId() {
        return senderPictureId;
    }

    public void setSenderPictureId(int senderPictureId) {
        this.senderPictureId = senderPictureId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.senderId;
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
        final RequestDTO other = (RequestDTO) obj;
        return this.senderId == other.senderId;
    }

}
