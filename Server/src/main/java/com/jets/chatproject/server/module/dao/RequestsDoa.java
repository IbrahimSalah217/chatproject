/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dao;

import com.jets.chatproject.module.entities.Request;
import java.util.ArrayList;

/**
 *
 * @author salma
 */
public interface RequestsDoa {
    
    void insertRequest(Request request);

    void deleteRequest(Request request);

    ArrayList<Request> getAllRequest();
}
