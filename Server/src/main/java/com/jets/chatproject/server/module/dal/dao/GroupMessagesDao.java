/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao;

import com.jets.chatproject.server.module.dal.entities.GroupMessage;
import java.util.List;

/**
 *
 * @author salma
 */
public interface GroupMessagesDao extends AbstractDAO<GroupMessage> {

    GroupMessage getLastMessage(int groupId) throws Exception;

    List<GroupMessage> getAllGroupMessages(int groupId) throws Exception;
}
