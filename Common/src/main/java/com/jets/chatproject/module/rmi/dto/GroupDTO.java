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
public class GroupDTO implements Serializable {

    private final int id;
    private int adminId;
    private String displayName;
    private int pictureId;
    private MessageDTO lastMessage;

    public GroupDTO(int id) {
        this.id = id;
    }

    public GroupDTO(int id, int adminId, String displayName, int pictureId, MessageDTO lastMessage) {
        this.id = id;
        this.adminId = adminId;
        this.displayName = displayName;
        this.pictureId = pictureId;
        this.lastMessage = lastMessage;
    }

    public int getId() {
        return id;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public MessageDTO getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(MessageDTO lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + this.id;
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
        final GroupDTO other = (GroupDTO) obj;
        return this.id == other.id;
    }

}
