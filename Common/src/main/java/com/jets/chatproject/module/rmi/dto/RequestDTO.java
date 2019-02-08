/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.module.rmi.dto;

import java.util.Date;

/**
 *
 * @author ibrahim
 */
public class RequestDTO {

    private final int senderId;
    private final String senderName;
    private final Date timestamp;

    public RequestDTO(int senderId, String senderName, Date timestamp) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.timestamp = timestamp;
    }

    public int getSenderId() {
        return senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public Date getTimestamp() {
        return timestamp;
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
