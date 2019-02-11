/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao;

import com.jets.chatproject.server.module.dal.entities.DirectMessage;
import java.util.ArrayList;

/**
 *
 * @author ibrahim
 */
public interface DirectMessagesDao extends AbstractDAO<DirectMessage> {

    DirectMessage getLastDirectMessage(int userId, int anotherUserId);

    ArrayList<DirectMessage> getAllDirectMessages(int userId, int anotherUserId);

}
