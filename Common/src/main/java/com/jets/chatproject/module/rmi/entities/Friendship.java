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
public class Friendship {

    private final int friendId;
    private String friendName;
    private String category;
    private Message lastMessage;

    public Friendship(int friendId, String friendName, String category, Message lastMessage) {
        this.friendId = friendId;
        this.friendName = friendName;
        this.category = category;
        this.lastMessage = lastMessage;
    }

    public int getFriendId() {
        return friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.friendId;
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
        final Friendship other = (Friendship) obj;
        return this.friendId == other.friendId;
    }

}
