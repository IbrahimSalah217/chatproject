/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao;

import java.util.ArrayList;
import com.jets.chatproject.server.module.dal.entities.GroupMessage;

/**
 *
 * @author salma
 */
public interface GroupMessagesDao extends AbstractDAO<GroupMessage>{

    GroupMessage getLastMessage(int groupId);

    ArrayList<GroupMessage> getAllGroupMessages(int groupId);
}
