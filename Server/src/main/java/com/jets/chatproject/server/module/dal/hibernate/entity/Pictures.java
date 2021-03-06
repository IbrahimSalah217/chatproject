package com.jets.chatproject.server.module.dal.hibernate.entity;
// Generated Mar 10, 2019 4:24:14 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Pictures generated by hbm2java
 */
public class Pictures  implements java.io.Serializable {


     private Integer pictureId;
     private byte[] picture;
     private Set groupses = new HashSet(0);
     private Set userses = new HashSet(0);

    public Pictures() {
    }

	
    public Pictures(byte[] picture) {
        this.picture = picture;
    }
    public Pictures(byte[] picture, Set groupses, Set userses) {
       this.picture = picture;
       this.groupses = groupses;
       this.userses = userses;
    }
   
    public Integer getPictureId() {
        return this.pictureId;
    }
    
    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }
    public byte[] getPicture() {
        return this.picture;
    }
    
    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
    public Set getGroupses() {
        return this.groupses;
    }
    
    public void setGroupses(Set groupses) {
        this.groupses = groupses;
    }
    public Set getUserses() {
        return this.userses;
    }
    
    public void setUserses(Set userses) {
        this.userses = userses;
    }




}


