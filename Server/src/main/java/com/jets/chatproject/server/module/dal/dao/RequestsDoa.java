/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao;

import com.jets.chatproject.server.module.dal.entities.Request;
import java.util.List;

/**
 *
 * @author salma
 */
public interface RequestsDoa extends AbstractDAO<Request> {

    List<Request> findAllByReceiver(int userId);
    Request findBySenderReceiver(int senderId, int receiverId);
}
