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
public class GroupMemberDTO implements Serializable {

    private final int memberId;
    private String memberName;
    private int memberPictureId;
    private int lastMessageRead;

    public GroupMemberDTO(int memberId) {
        this.memberId = memberId;
    }

    public GroupMemberDTO(int memberId, String memberName, int memberPictureId, int lastMessageRead) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberPictureId = memberPictureId;
        this.lastMessageRead = lastMessageRead;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getMemberPictureId() {
        return memberPictureId;
    }

    public void setMemberPictureId(int memberPictureId) {
        this.memberPictureId = memberPictureId;
    }

    public int getLastMessageRead() {
        return lastMessageRead;
    }

    public void setLastMessageRead(int lastMessageRead) {
        this.lastMessageRead = lastMessageRead;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.memberId;
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
        final GroupMemberDTO other = (GroupMemberDTO) obj;
        return this.memberId == other.memberId;
    }

}
