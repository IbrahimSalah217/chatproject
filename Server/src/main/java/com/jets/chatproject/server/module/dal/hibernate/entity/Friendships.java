package com.jets.chatproject.server.module.dal.hibernate.entity;
// Generated Mar 10, 2019 4:24:14 PM by Hibernate Tools 4.3.1



/**
 * Friendships generated by hbm2java
 */
public class Friendships  implements java.io.Serializable {


     private FriendshipsId id;
     private DirectMessages directMessages;
     private Users usersByUserId;
     private Users usersByFriendId;
     private String category;
     private Boolean blocked;

    public Friendships() {
    }

	
    public Friendships(FriendshipsId id, Users usersByUserId, Users usersByFriendId, String category) {
        this.id = id;
        this.usersByUserId = usersByUserId;
        this.usersByFriendId = usersByFriendId;
        this.category = category;
    }
    public Friendships(FriendshipsId id, DirectMessages directMessages, Users usersByUserId, Users usersByFriendId, String category, Boolean blocked) {
       this.id = id;
       this.directMessages = directMessages;
       this.usersByUserId = usersByUserId;
       this.usersByFriendId = usersByFriendId;
       this.category = category;
       this.blocked = blocked;
    }
   
    public FriendshipsId getId() {
        return this.id;
    }
    
    public void setId(FriendshipsId id) {
        this.id = id;
    }
    public DirectMessages getDirectMessages() {
        return this.directMessages;
    }
    
    public void setDirectMessages(DirectMessages directMessages) {
        this.directMessages = directMessages;
    }
    public Users getUsersByUserId() {
        return this.usersByUserId;
    }
    
    public void setUsersByUserId(Users usersByUserId) {
        this.usersByUserId = usersByUserId;
    }
    public Users getUsersByFriendId() {
        return this.usersByFriendId;
    }
    
    public void setUsersByFriendId(Users usersByFriendId) {
        this.usersByFriendId = usersByFriendId;
    }
    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    public Boolean getBlocked() {
        return this.blocked;
    }
    
    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }




}


