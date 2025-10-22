package controller;

import logtrack.ExceptionLogTrack;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class AppConfig {
    
    private boolean verbose;
    private String url;
    private String user;
    private String password;
    
    
    private static AppConfig singleton;
    
    private AppConfig() {
        
        try {
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();        
            
//            Document document = docBuilder.parse( new File("./app-config.xml") );
            
            Document document = docBuilder.parse( 
                    Thread.currentThread().getContextClassLoader().getResourceAsStream("./model/framework/app-config.xml") 
            );
            
            document.getDocumentElement().normalize();
            Element config = document.getDocumentElement();
            
            Node settings = config.getElementsByTagName("settings").item(0);
            verbose = Boolean.valueOf( settings.getAttributes().getNamedItem("verbose").getNodeValue() );
            
            Node database = config.getElementsByTagName("database").item(0);
            url = database.getAttributes().getNamedItem("url").getNodeValue();
            user = database.getAttributes().getNamedItem("user").getNodeValue();
            password = database.getAttributes().getNamedItem("password").getNodeValue();
            
        } catch(Exception ex) {
            ExceptionLogTrack.getInstance().addLog(ex);
        }
        
    }
    
    public static AppConfig getInstance() {
        if( singleton == null ) {
            singleton = new AppConfig();
        }
        return singleton;            
    }
    
    
    public boolean isVerbose() {
        return verbose;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

}