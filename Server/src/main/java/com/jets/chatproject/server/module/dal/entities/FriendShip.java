/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.entities;

import java.util.Objects;

/**
 *
 * @author Ibrahim
 */
public class FriendShip {

    private final User user;                    // 
    private final User frind;                   // compisteKey
    private Category category;                 //not Null ---- "family","freind","work","others"
    private int blockedStatus;                  // default value 0
    private DirectMessage lastSeenMessage;      // only for individual

    public FriendShip(User user, User friend, Category category, int blockedStatus, DirectMessage lastSeenMessage) {
        this.user = user;
        this.frind = friend;
        this.category = category;
        this.blockedStatus = blockedStatus;
        this.lastSeenMessage = lastSeenMessage;
    }

    public User getUserId() {
        return user;
    }

    public User getFrindId() {
        return frind;
    }

    public Category getCategory() {
        return category;
    }

    public int getBlockedStatus() {
        return blockedStatus;
    }

    public DirectMessage getLastSeenMessage() {
        return lastSeenMessage;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setBlockedStatus(int blockedStatus) {
        this.blockedStatus = blockedStatus;
    }

    public void setLastSeenMessage(DirectMessage lastSeenMessage) {
        this.lastSeenMessage = lastSeenMessage;
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
        final FriendShip other = (FriendShip) obj;
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.frind, other.frind)) {
            return false;
        }
        return true;
    }
    public static enum Category{
        FAMILY,FRIEND,WORK,OTHERS
    }

}
