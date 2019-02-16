/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.dao;

/**
 *
 * @author ibrahim
 * @param <T>
 */
public interface AbstractDAO<T> {

    int insert(T object) throws Exception;

    boolean update(T object) throws Exception;

    boolean delete(T object) throws Exception;
}
