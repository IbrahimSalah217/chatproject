/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.module.rmi.dto;

import java.io.Serializable;

/**
 *
 * @author ibrahim
 */
public class FriendshipDTO implements Serializable {

    private final int friendId;
    private String friendName;
    private int friendPictureId;
    private String category;
    private MessageDTO lastMessage;
    private int lastMessageReadByFriend;
    private int unreadMessagesCount;

    public FriendshipDTO(int friendId) {
        this.friendId = friendId;
    }

    public FriendshipDTO(int friendId, String friendName, int memberPictureId, String category, MessageDTO lastMessage, int lastMessageReadByFriend, int unreadMessagesCount) {
        this.friendId = friendId;
        this.friendName = friendName;
        this.friendPictureId = memberPictureId;
        this.category = category;
        this.lastMessage = lastMessage;
        this.lastMessageReadByFriend = lastMessageReadByFriend;
        this.unreadMessagesCount = unreadMessagesCount;
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

    public int getMemberPictureId() {
        return friendPictureId;
    }

    public void setMemberPictureId(int memberPictureId) {
        this.friendPictureId = memberPictureId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public MessageDTO getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(MessageDTO lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getLastMessageReadByFriend() {
        return lastMessageReadByFriend;
    }

    public void setLastMessageReadByFriend(int lastMessageReadByFriend) {
        this.lastMessageReadByFriend = lastMessageReadByFriend;
    }

    public int getUnreadMessagesCount() {
        return unreadMessagesCount;
    }

    public void setUnreadMessagesCount(int unreadMessagesCount) {
        this.unreadMessagesCount = unreadMessagesCount;
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
        final FriendshipDTO other = (FriendshipDTO) obj;
        return this.friendId == other.friendId;
    }

}
