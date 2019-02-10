/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.entities;

/**
 *
 * @author Ibrahim
 */
public class Friendship {

    private final User user;
    private final User friend;
    private String category;
    private boolean blocked;
    private DirectMessage lastSeenMessage;

    public Friendship(User user, User friend, String category, boolean blocked, DirectMessage lastSeenMessage) {
        this.user = user;
        this.friend = friend;
        this.category = category;
        this.blocked = blocked;
        this.lastSeenMessage = lastSeenMessage;
    }

    public User getUser() {
        return user;
    }

    public User getFriend() {
        return friend;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public DirectMessage getLastSeenMessage() {
        return lastSeenMessage;
    }

    public void setLastSeenMessage(DirectMessage lastSeenMessage) {
        this.lastSeenMessage = lastSeenMessage;
    }

}
