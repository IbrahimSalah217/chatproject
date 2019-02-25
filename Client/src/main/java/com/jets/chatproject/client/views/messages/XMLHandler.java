/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.views.messages;

import com.jets.chatproject.module.rmi.dto.MessageDTO;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import xmlclass.ChatSessionType;
import xmlclass.MessageType;
import xmlclass.MsgType;
import xmlclass.ObjectFactory;

/**
 *
 * @author salma
 */
public class XMLHandler {

    List<MsgType> msgList;

    public XMLHandler(List<MessageDTO> messagesUserList) {

        msgList = new ArrayList<>();
        try {
            for (MessageDTO message : messagesUserList) {
           
                MsgType msgType = new MsgType();
                msgType.setFrom(message.getSenderName());
                msgType.setFont(message.getFormat().toString());
                msgType.setContent(message.getContent());
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(message.getTimestamp());
                msgType.setTime(new XMLGregorianCalendarImpl(calendar));
                msgList.add(msgType);
            }
            MessageType messageType = new MessageType();
            messageType.setMsg(msgList);
            ChatSessionType chatSessionType = new ChatSessionType();
            chatSessionType.setMessages(messageType);
            JAXBContext context = JAXBContext.newInstance("xmlclass");
            ObjectFactory factory = new ObjectFactory();
            JAXBElement chatSession = factory.createChatSession(chatSessionType);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(chatSession, new FileOutputStream("Message.xml"));
        } catch (JAXBException | FileNotFoundException ex) {
            Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
