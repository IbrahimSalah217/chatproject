/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.chatbot;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;

/**
 *
 * @author ibrahim
 */
public class ChatbotContext {

    Bot bot = new Bot("super", getResources());
    Chat chatSession = new Chat(bot);

    public String respond(String request) {
        String response = chatSession.multisentenceRespond(request);
        response = response.replaceAll(Pattern.quote("&lt;"), "<");
        response = response.replaceAll(Pattern.quote("&gt;"), ">");
        return response;
    }

    public static void main(String[] args) {
        ChatbotContext context = new ChatbotContext();
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            System.out.println(context.respond(in.nextLine()));
        }
    }

    private String getResources() {
        File file = new File("lib/");
        return file.toString();
    }

}
