/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.entities;

import java.util.Date;

/**
 *
 * @author salma
 */
public class Request {
    
    private int senderId;
    private int receiverId;
    private Date requestTime;
    
    public Request(int senderId, int receiverId, Date requestTime) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.requestTime = requestTime; 
   }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }
    
    
}
