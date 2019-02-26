/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.views.messages;

import com.jets.chatproject.module.rmi.dto.MessageDTO;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
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
    File file;
    String xslFilename;
    String inFilename;

    public XMLHandler(List<MessageDTO> messagesUserList) {

        xslFilename = "chatView.xsd";
        inFilename = "Message.xml";
        msgList = new ArrayList<>();
        try {
            FileChooser fileChooser = new FileChooser();
            file = fileChooser.showSaveDialog(new Stage());
            if (file == null) {
                return;
            } else {
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
                marshaller.marshal(chatSession, new FileOutputStream(inFilename));
                xsl(inFilename, file.toString(), xslFilename);
            }
        } catch (JAXBException | FileNotFoundException ex) {
            Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void xsl(String inFilename, String outFilename, String xslFilename) {
        try {
           
            TransformerFactory factory = TransformerFactory.newInstance();
            Templates template = factory.newTemplates(new StreamSource(
                new FileInputStream(xslFilename)));
            Transformer xformer = template.newTransformer();
            Source source = new StreamSource(new FileInputStream(inFilename));
            Result result = new StreamResult(new FileOutputStream(outFilename));
            xformer.transform(source, result);
        } catch (FileNotFoundException | TransformerConfigurationException e) {
            e.printStackTrace();
        }
         catch (TransformerException e) {
            SourceLocator locator = e.getLocator();
            int col = locator.getColumnNumber();
            int line = locator.getLineNumber();
            String publicId = locator.getPublicId();
            String systemId = locator.getSystemId();
        }
    }
}
