package com.rkit;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ExceptionHandler {
	String[][] classNameMethodNameExceptionname = new String[2][10];
    void handleException(String className, String methodName, Exception e) {
    	 loadAndParseXML("ExceptionConfig.xml");
    	//1. Get List of actions to be taken from config based on 3 args(className, methodName, Exception)
    	    //1. I will assume for my design that config is inside an xml 
    	    //1. Load that XML , parse XML and then get List actions for given 3 params.
    	    //Concatenating these 3 parameters and create a string
    	      String key = className+"#"+methodName+"#"+e.getClass().getSimpleName();
  	      
    	     String[] actions = searchActionsforAkey(key);
    	     for(String action : actions) {
    	    	 //call appropriate action method.
    	     }
    	     
    	     
    	// Using this string we get list of actions
    	//e.g.
    //	A#m1#MyException   [Email,Log]
    	  
    	//2. Iterate over the list of actions and take that action
    }

	private static void loadAndParseXML(String fileName) {
	
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.parse(fileName);//document is a Tree , Nodes
			Node rootNode = doc.getDocumentElement();
			System.out.println("Name of root node is "+rootNode.getNodeName());
			//Get All Classes
			NodeList classNodes = rootNode.getChildNodes();//Class Names
			int keyCounter=0;
			int actionCounter=0;
			for(int i=0; i< classNodes.getLength();i++) {
				
				Node classNode = classNodes.item(i);
				if(classNode.getNodeType()==Node.ELEMENT_NODE) {
					
					String className = ((Element)classNode).getAttribute("name");
					
					System.out.println("class Name is "+className);
					//getMethodNames
					NodeList methodNodes = classNode.getChildNodes();
					for(int j=0; j< methodNodes.getLength();j++) {
						Node methodNode = methodNodes.item(j);
						if(methodNode.getNodeType()==Node.ELEMENT_NODE) {
							String methodName = ((Element)methodNode).getAttribute("name");
							
							System.out.println("Method Name is "+methodName);
							NodeList exceptionNodes = methodNode.getChildNodes();
							
							for(int k=0; k< exceptionNodes.getLength();k++) {
								Node exceptionNode = exceptionNodes.item(k);
								StringBuilder key = new StringBuilder("");
								key.append(className+"#");
								key.append(methodName+"#");
								if(exceptionNode.getNodeType()==Node.ELEMENT_NODE) {
									String exceptionName = ((Element)exceptionNode).getAttribute("name");
									System.out.println("Method Name is "+exceptionName);
									key.append(exceptionName);
									System.out.println(key.toString());
									classNameMethodNameExceptionname[0][keyCounter++] = key.toString();
									
									//Get List of ACtions
									NodeList actionNodes = exceptionNode.getChildNodes();
									StringBuilder actions= new StringBuilder();
									for(int l=0; l< actionNodes.getLength();l++) {
										Node actionNode = actionNodes.item(l);
										if(actionNode.getNodeType()==Node.ELEMENT_NODE) {
											String actionNodeName = ((Element)actionNode).getNodeName();
										//	System.out.println("Action Name is "+actionNodeName);
											//Get List of ACtions
											if(l<actionNodes.getLength())
											actions.append(actionNodeName+",");
											
											System.out.println("Actions "+actions.toString());
										}
									}
									classNameMethodNameExceptionname[1][actionCounter++] = actions.toString();
								}
							}
						}
					}
				}
			}
		System.out.println("********** KEYS **********");
				for(int j=0; j<10;j++) {
					if(classNameMethodNameExceptionname[0][j] !=null) {
				       System.out.println(classNameMethodNameExceptionname[0][j]);
				       System.out.println("     "+classNameMethodNameExceptionname[1][j]);
					}
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
