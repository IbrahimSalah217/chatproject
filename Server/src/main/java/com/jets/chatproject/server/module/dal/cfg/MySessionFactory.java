/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.cfg;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author ibrahim
 */
public class MySessionFactory {

    static SessionFactory sessionFactory = createSessionFactory();

    private static SessionFactory createSessionFactory() {
        SessionFactory factory = new Configuration()
                .configure()
                .buildSessionFactory();
        return factory;
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }
}
