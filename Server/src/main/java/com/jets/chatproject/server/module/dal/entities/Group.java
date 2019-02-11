/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.entities;

/**
 *
 * @author Hadeer
 */
public class Group {

    private final int groupId;
    private final int adminId;
    private String groupName;
    private int pictureId;

    public Group(int groupId, int adminId, String groupName, int pictureId) {
        this.groupId = groupId;
        this.adminId = adminId;
        this.groupName = groupName;
        this.pictureId = pictureId;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getAdminId() {
        return adminId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

}
