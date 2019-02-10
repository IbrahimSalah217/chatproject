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

    private final int senderId;
    private final int receiverId;
    private final Date requestTime;

    public Request(int senderId, int receiverId, Date requestTime) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.requestTime = requestTime;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public Date getRequestTime() {
        return requestTime;
    }

}
