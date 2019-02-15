/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.module.rmi.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author ibrahim
 */
public class UserDTO implements Serializable {

    private final int id;
    private final String phoneNumber;
    private String displyName;
    private String email;
    private Gender gender;
    private String country;
    private Date dateOfBirth;
    private String bio;
    private int pictureId;

    public UserDTO(int id, String phoneNumber) {
        this.id = id;
        this.phoneNumber = phoneNumber;
    }

    public UserDTO(String phoneNumber) {
        this(Integer.MIN_VALUE, phoneNumber);
    }

    public UserDTO(int id, String phoneNumber, String displyName, String email, Gender gender, String country, Date dateOfBirth, String bio, int pictureId) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.displyName = displyName;
        this.email = email;
        this.gender = gender;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
        this.pictureId = pictureId;
    }

    public int getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDisplyName() {
        return displyName;
    }

    public void setDisplyName(String displyName) {
        this.displyName = displyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.id;
        hash = 71 * hash + Objects.hashCode(this.phoneNumber);
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
        final UserDTO other = (UserDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.phoneNumber, other.phoneNumber);
    }

}
