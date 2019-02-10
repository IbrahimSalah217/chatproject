/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao;

import com.jets.chatproject.server.module.dal.entities.Request;
import java.util.ArrayList;

/**
 *
 * @author salma
 */
public interface RequestsDoa {

    void insert(Request request);

    void delete(Request request);

    ArrayList<Request> getAllByReceiver(int userId);
}
