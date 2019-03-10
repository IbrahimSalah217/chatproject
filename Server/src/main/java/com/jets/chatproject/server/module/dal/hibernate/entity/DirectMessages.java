package com.jets.chatproject.server.module.dal.hibernate.entity;
// Generated Mar 10, 2019 4:24:14 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * DirectMessages generated by hbm2java
 */
public class DirectMessages  implements java.io.Serializable {


     private Integer id;
     private Users usersByReceiverId;
     private Users usersBySenderId;
     private String messageType;
     private String content;
     private String fontStyle;
     private Date time;
     private Set friendshipses = new HashSet(0);

    public DirectMessages() {
    }

	
    public DirectMessages(Users usersByReceiverId, Users usersBySenderId, String messageType, String content, Date time) {
        this.usersByReceiverId = usersByReceiverId;
        this.usersBySenderId = usersBySenderId;
        this.messageType = messageType;
        this.content = content;
        this.time = time;
    }
    public DirectMessages(Users usersByReceiverId, Users usersBySenderId, String messageType, String content, String fontStyle, Date time, Set friendshipses) {
       this.usersByReceiverId = usersByReceiverId;
       this.usersBySenderId = usersBySenderId;
       this.messageType = messageType;
       this.content = content;
       this.fontStyle = fontStyle;
       this.time = time;
       this.friendshipses = friendshipses;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Users getUsersByReceiverId() {
        return this.usersByReceiverId;
    }
    
    public void setUsersByReceiverId(Users usersByReceiverId) {
        this.usersByReceiverId = usersByReceiverId;
    }
    public Users getUsersBySenderId() {
        return this.usersBySenderId;
    }
    
    public void setUsersBySenderId(Users usersBySenderId) {
        this.usersBySenderId = usersBySenderId;
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
    public Set getFriendshipses() {
        return this.friendshipses;
    }
    
    public void setFriendshipses(Set friendshipses) {
        this.friendshipses = friendshipses;
    }




}


