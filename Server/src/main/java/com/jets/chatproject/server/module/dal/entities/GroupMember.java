/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.entities;

/**
 *
 * @author ibrahim
 */
public class GroupMember {

    private final int groupId;
    private final int userId;
    private int lastSeenMessageId;

    public GroupMember(int groupId, int userId, int lastSeenMessageId) {
        this.groupId = groupId;
        this.userId = userId;
        this.lastSeenMessageId = lastSeenMessageId;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getUserId() {
        return userId;
    }

    public int getLastSeenMessageId() {
        return lastSeenMessageId;
    }

    public void setLastSeenMessageId(int lastSeenMessageId) {
        this.lastSeenMessageId = lastSeenMessageId;
    }

}
