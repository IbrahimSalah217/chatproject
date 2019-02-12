/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.controllers;



/**
 *
 * @author salma
 */
public interface SessionManagerInt {
    
    String createSession(int userId);
    void removeSession(String session);
    int findUserId(String session);
}
