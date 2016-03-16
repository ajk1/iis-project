package util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class ParseSenticNetXml {
	static String readFile(String path) 
			  throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
			return new String(encoded, "UTF-8");
	}

    static public Document getDocument(String xmlString) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document spyDoc = null;
        try {
        builder = factory.newDocumentBuilder();
        spyDoc = builder.parse( new InputSource( new StringReader( xmlString ) ) );

        } catch (Exception e) {
        e.printStackTrace();
        }
        return spyDoc;
    }
	
	public static void main(String[] args) {
		try {
			String xmlString = readFile("src/main/resources/libraries/senticnet-2.0/senticnet2.rdf.xml");
//			String xmlString = readFile("src/main/resources/libraries/senticnet-3.0/senticnet3.rdf.xml");
//			String xmlString = readFile("src/main/resources/libraries/senticnet-3.0/senticnet3_sub.rdf.xml");
//			String xmlString = readFile("pom.xml");
	        Document doc = getDocument(xmlString);

	        doc.getDocumentElement().normalize();
	        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			
	        
	        NodeList nl = doc.getElementsByTagName("rdf:Description");
	        System.out.println(nl.getLength());
	        
	        PrintWriter writer = new PrintWriter("src/main/resources/libraries/senticnet.txt", "UTF-8");
	        for(int i=0; i<nl.getLength(); i++) {
		        Node n = nl.item(i);
		        Element eElement = (Element) n;
		        String name = eElement.getElementsByTagName("text").item(0).getTextContent();
		        String polarity = eElement.getElementsByTagName("polarity").item(0).getTextContent();
		        System.out.println(name+"\t"+polarity);	        	
		        writer.println(name+"\t"+polarity);
	        }	        		
	        writer.close();
		
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
