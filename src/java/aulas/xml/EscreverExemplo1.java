package aulas.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class EscreverExemplo1 {
    public static void main(String[] args) throws Exception  {
        
        // criar um arquivo novo
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        
        // raiz
        Element raiz = doc.createElement( "raiz" );
        doc.appendChild(raiz);
        
        // elementos
        Element paiA = doc.createElement( "pai_A" );
        paiA.setAttribute("atributo1", "valor");
        paiA.setAttribute("atributo2", "valor");
        
        Element filho = doc.createElement( "filho" );
        filho.setAttribute("atributo1", "valor");
        filho.setAttribute("atributo2", "valor");
        filho.setTextContent( "conteudo" );
        
        paiA.appendChild( filho );        
        raiz.appendChild( paiA );
        
        // salvar
        FileOutputStream output = new FileOutputStream("./arquivo.xml");
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        String xsl = "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:output method=\"xml\" omit-xml-declaration=\"yes\" indent=\"yes\"/><xsl:strip-space elements=\"*\"/><xsl:template match=\"node()|@*\"><xsl:copy><xsl:apply-templates select=\"node()|@*\"/></xsl:copy></xsl:template></xsl:stylesheet>";
        InputStream inputStream = new ByteArrayInputStream( xsl.getBytes(StandardCharsets.UTF_8) );
        Source xslt = new StreamSource( inputStream );
        Transformer transformer = transformerFactory.newTransformer(xslt);
        
        DOMSource source = new DOMSource( doc );
        StreamResult result = new StreamResult( output );
        
        transformer.setOutputProperty( OutputKeys.OMIT_XML_DECLARATION, "yes" );
        transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
        transformer.setOutputProperty( OutputKeys.ENCODING,"UTF-8" );
        transformer.transform( source, result );
        
    }
}