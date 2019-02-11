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

    boolean insert(T object);

    boolean update(T object);

    boolean delete(T object);
}
