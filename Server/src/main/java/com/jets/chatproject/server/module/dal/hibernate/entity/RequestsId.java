package com.jets.chatproject.server.module.dal.hibernate.entity;
// Generated Mar 10, 2019 4:24:14 PM by Hibernate Tools 4.3.1



/**
 * RequestsId generated by hbm2java
 */
public class RequestsId  implements java.io.Serializable {


     private int senderId;
     private int receiverId;

    public RequestsId() {
    }

    public RequestsId(int senderId, int receiverId) {
       this.senderId = senderId;
       this.receiverId = receiverId;
    }
   
    public int getSenderId() {
        return this.senderId;
    }
    
    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }
    public int getReceiverId() {
        return this.receiverId;
    }
    
    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RequestsId) ) return false;
		 RequestsId castOther = ( RequestsId ) other; 
         
		 return (this.getSenderId()==castOther.getSenderId())
 && (this.getReceiverId()==castOther.getReceiverId());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getSenderId();
         result = 37 * result + this.getReceiverId();
         return result;
   }   


}

