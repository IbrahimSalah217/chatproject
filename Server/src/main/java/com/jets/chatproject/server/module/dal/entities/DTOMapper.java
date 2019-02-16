/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.entities;

import com.jets.chatproject.module.rmi.dto.FriendshipDTO;
import com.jets.chatproject.module.rmi.dto.GroupMemberDTO;
import com.jets.chatproject.module.rmi.dto.MessageDTO;
import com.jets.chatproject.module.rmi.dto.MessageFormat;
import com.jets.chatproject.module.rmi.dto.RequestDTO;
import com.jets.chatproject.module.rmi.dto.UserDTO;

/**
 *
 * @author ibrahim
 */
public class DTOMapper {

    public static UserDTO createUserDTO(User user) {
        return new UserDTO(user.getId(), user.getPhoneNumber(),
                user.getDisplyName(), user.getEmail(), user.getGender(),
                user.getCountry(), user.getDateOfBirth(), user.getBio(),
                user.getPictureId());
    }

    public static RequestDTO createRequestDTO(User sender, Request request) {
        return new RequestDTO(request.getSenderId(), sender.getDisplyName(),
                sender.getPictureId(), request.getRequestTime());
    }

    public static MessageDTO createMessageDTO(User sender, DirectMessage message) {
        return new MessageDTO(message.getMessageId(),
                sender.getId(), sender.getDisplyName(),
                message.getMessageType(), message.getContent(),
                MessageFormat.of(message.getStyle()), message.getMessageTime());
    }

    public static MessageDTO createMessageDTO(User sender, GroupMessage message) {
        return new MessageDTO(message.getMessageId(),
                sender.getId(), sender.getDisplyName(),
                message.getMessageType(), message.getContent(),
                MessageFormat.of(message.getStyle()), message.getMessageTime());
    }

    public static FriendshipDTO createFriendshipDTO(User friend, Friendship friendship, MessageDTO messageDto) {
        return new FriendshipDTO(friend.getId(), friend.getDisplyName(),
                friend.getPictureId(), friendship.getCategory(), messageDto,
                friendship.getLastSeenMessageId(), 0);
    }
    
    public static GroupMemberDTO createGroupMemberDTO(User member, GroupMember groupMember) {
        
        return new GroupMemberDTO(member.getId(), member.getDisplyName(), member.getPictureId(), 
                groupMember.getLastSeenMessageId());
    }

}
