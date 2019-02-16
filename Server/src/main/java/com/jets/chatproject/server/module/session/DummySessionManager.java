/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.session;

/**
 *
 * @author ibrahim
 */
public class DummySessionManager implements SessionManager {

    @Override
    public String createSession(int userId) {
        return String.valueOf(userId);
    }

    @Override
    public void removeSession(String session) {

    }

    @Override
    public int findUserId(String session) {
        return Integer.valueOf(session);
    }

}
