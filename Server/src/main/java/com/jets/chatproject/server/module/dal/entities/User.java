/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.entities;

import com.jets.chatproject.module.rmi.dto.Gender;
import java.util.Date;

/**
 *
 * @author ibrahim
 */
public class User {

    private final int id;
    private final String phoneNumber;
    private String displyName;
    private String email;
    private String password;
    private Gender gender;
    private String country;
    private Date dateOfBirth;
    private String bio;
    private int pictureId;

    public User(int id, String phoneNumber, String displyName, String email, String password, Gender gender, String country, Date dateOfBirth, String bio, int pictureId) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.displyName = displyName;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
