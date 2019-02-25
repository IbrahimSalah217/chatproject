/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.client.cfg;

import java.io.File;
import java.io.IOException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author ibrahim
 */
public final class ServerConfiguration {

    public static String REGISTRY_HOST;
    public static int REGISTRY_PORT;

    static {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File("server-conf.xml"));
            REGISTRY_HOST = document.getElementsByTagName("ip").item(0).getTextContent();
            REGISTRY_PORT = Integer.valueOf(document.getElementsByTagName("port")
                    .item(0).getTextContent().trim());
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(ServerConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
