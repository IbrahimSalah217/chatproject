/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hadeer
 */
public class Group {

    private final int groupId;
    private final User admin;
    private String groupName;
    private int pictureId;
    private List<User> members;
    private List<GroupMessage> messages;

    public Group(int groupId, User admin, String groupName, int pictureId) {
        this.groupId = groupId;
        this.admin = admin;
        this.groupName = groupName;
        this.pictureId = pictureId;
        members = new ArrayList<>();
        messages = new ArrayList<>();
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

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<GroupMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<GroupMessage> messages) {
        this.messages = messages;
    }

    private User findMemberById(int id) {
        User groupMember = null;
        for (User member : members) {
            if (member.getId() == id) {
                groupMember = member;
            }
        }
        return groupMember;
    }

    public void addMember(User member) {
        members.add(member);
    }

    public boolean removeMember(User member) {
        User groupMember = findMemberById(member.getId());
        boolean isRemoved = false;
        if (groupMember != null) {
            members.remove(member);
            isRemoved = true;
        }
        return isRemoved;
    }

}
