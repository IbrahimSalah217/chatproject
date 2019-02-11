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

    private final int userId;
    private final int friendId;
    private String category;
    private boolean blocked;
    private int lastSeenMessageId;

    public Friendship(int user, int friend, String category, boolean blocked, int lastSeenMessageId) {
        this.userId = user;
        this.friendId = friend;
        this.category = category;
        this.blocked = blocked;
        this.lastSeenMessageId = lastSeenMessageId;
    }

    public int getUserId() {
        return userId;
    }

    public int getFriendId() {
        return friendId;
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

    public int getLastSeenMessageId() {
        return lastSeenMessageId;
    }

    public void setLastSeenMessageId(int lastSeenMessageId) {
        this.lastSeenMessageId = lastSeenMessageId;
    }

}
