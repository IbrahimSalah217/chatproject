/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao;

import com.jets.chatproject.server.module.dal.entities.Picture;

/**
 *
 * @author ibrahim
 */
public interface PicturesDao extends AbstractDAO<Picture> {

    Picture findById(int id) throws Exception;
}
