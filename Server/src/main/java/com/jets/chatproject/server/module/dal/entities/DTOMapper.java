/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.entities;

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

}
