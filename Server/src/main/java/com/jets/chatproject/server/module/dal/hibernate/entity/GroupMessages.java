package com.jets.chatproject.server.module.dal.hibernate.entity;
// Generated Mar 10, 2019 4:24:14 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * GroupMessages generated by hbm2java
 */
public class GroupMessages  implements java.io.Serializable {


     private Integer id;
     private Groups groups;
     private Users users;
     private String messageType;
     private String content;
     private String fontStyle;
     private Date time;
     private Set groupMemberses = new HashSet(0);

    public GroupMessages() {
    }

	
    public GroupMessages(Groups groups, Users users, String messageType, String content, Date time) {
        this.groups = groups;
        this.users = users;
        this.messageType = messageType;
        this.content = content;
        this.time = time;
    }
    public GroupMessages(Groups groups, Users users, String messageType, String content, String fontStyle, Date time, Set groupMemberses) {
       this.groups = groups;
       this.users = users;
       this.messageType = messageType;
       this.content = content;
       this.fontStyle = fontStyle;
       this.time = time;
       this.groupMemberses = groupMemberses;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Groups getGroups() {
        return this.groups;
    }
    
    public void setGroups(Groups groups) {
        this.groups = groups;
    }
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    public String getMessageType() {
        return this.messageType;
    }
    
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    public String getFontStyle() {
        return this.fontStyle;
    }
    
    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }
    public Date getTime() {
        return this.time;
    }
    
    public void setTime(Date time) {
        this.time = time;
    }
    public Set getGroupMemberses() {
        return this.groupMemberses;
    }
    
    public void setGroupMemberses(Set groupMemberses) {
        this.groupMemberses = groupMemberses;
    }




}


