/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.chatbot;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ibrahim
 */
public class ChatbotManager {

    private static ChatbotManager sInstance = getInstance();
    private final ChatbotContext chatbotContext;

    private final Set<Integer> enabledFriendSet;
    private final Set<Integer> enabledGroupSet;

    public static ChatbotManager getInstance() {
        if (sInstance == null) {
            sInstance = new ChatbotManager();
        }
        return sInstance;
    }

    private ChatbotManager() {
        chatbotContext = new ChatbotContext();
        enabledFriendSet = new HashSet<>();
        enabledGroupSet = new HashSet<>();
    }

    public String getResponse(String message) {
        return chatbotContext.respond(message);
    }

    public boolean isEnabledForFriend(int friendId) {
        return enabledFriendSet.contains(friendId);
    }

    public boolean isEnabledForGroup(int groupId) {
        return enabledGroupSet.contains(groupId);
    }

    public void enableForFriend(int friendId) {
        enabledFriendSet.add(friendId);
    }

    public void enableForGroup(int groupId) {
        enabledGroupSet.add(groupId);
    }

    public void disableForFriend(int friendId) {
        enabledFriendSet.remove(friendId);
    }

    public void disableForGroup(int groupId) {
        enabledGroupSet.remove(groupId);
    }

}
