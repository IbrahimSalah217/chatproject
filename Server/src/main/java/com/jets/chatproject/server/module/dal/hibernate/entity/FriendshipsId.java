package com.jets.chatproject.server.module.dal.hibernate.entity;
// Generated Mar 10, 2019 4:24:14 PM by Hibernate Tools 4.3.1



/**
 * FriendshipsId generated by hbm2java
 */
public class FriendshipsId  implements java.io.Serializable {


     private int userId;
     private int friendId;

    public FriendshipsId() {
    }

    public FriendshipsId(int userId, int friendId) {
       this.userId = userId;
       this.friendId = friendId;
    }
   
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getFriendId() {
        return this.friendId;
    }
    
    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof FriendshipsId) ) return false;
		 FriendshipsId castOther = ( FriendshipsId ) other; 
         
		 return (this.getUserId()==castOther.getUserId())
 && (this.getFriendId()==castOther.getFriendId());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getUserId();
         result = 37 * result + this.getFriendId();
         return result;
   }   


}


