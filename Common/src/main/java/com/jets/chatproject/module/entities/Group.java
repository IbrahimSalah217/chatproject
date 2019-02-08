/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.module.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hadeer
 */
public class Group {
    private final int groupId;
    private final int adminId;
    private String groupName;
    static List<User> groupMembers;
    
    public Group(int groupId, int adminId,String groupName){
        this.groupId=groupId;
        this.adminId=adminId;
        this.groupName=groupName;
        groupMembers = new ArrayList<>();
    }
    
    public void setGroupName(String groupName){
        this.groupName=groupName;
    }
    
    public int getGroupId(){
        return groupId;
    }
    
    public int getAdminId(){
        return adminId;
    }
    
    public String getGroupName(){
        return groupName;
    }
    
    private User findMemberById(int id){
        User groupMember=null;
        for(User member:groupMembers){
            if(member.getId()==id)
                groupMember=member;
        }
        return groupMember;
    }
    
    public Boolean addMember(User member){
        User groupMember=findMemberById(member.getId());
        boolean isAdded = false;
        if(groupMember == null){
            groupMembers.add(member);
            isAdded = true;
        }
        return isAdded;
    }
    
    public Boolean removeMember(User member){
        User groupMember=findMemberById(member.getId());
        boolean isRemoved = false;
        if(groupMember != null){
            groupMembers.remove(member);
            isRemoved=true;
        }
        return isRemoved;
    }
    
    public List<User> getAllGroupMembers(){
        return groupMembers;
    }
    
    
}
