package com.example.TimeCommunityVaadin.scheduleView;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import javax.xml.parsers.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class File {

	private final String path = "DataFile.xml";
	private final XStream xs;
	
	public File(){
		xs = new XStream(new DomDriver());
		LocalDateTimeConverter ldtc = new LocalDateTimeConverter();
		xs.registerConverter(ldtc);
	}
	
	private void initialize() throws ParserConfigurationException, SAXException, IOException {
		//Create a DocumentBuilder
//		DocumentBuilderFactory factory =
//		DocumentBuilderFactory.newInstance();
//		DocumentBuilder builder = factory.newDocumentBuilder();
		
		//Create a Document from a file or stream
		//StringBuilder xmlStringBuilder = new StringBuilder();
		//xmlStringBuilder.append("<?xml version=\"1.0\"?> <class> </class>");
		//ByteArrayInputStream input =  new ByteArrayInputStream(
		//   xmlStringBuilder.toString().getBytes("UTF-8"));
		//Document doc = builder.parse(input);
		
		//????
		
//		//Extract the root element
//		Element root = document.getDocumentElement();
//		
//		//Examine attributes
//		//returns specific attribute
//		getAttribute("attributeName"); 
//		//returns a Map (table) of names/values
//		getAttributes(); 
//		
//		//Examine sub-elements
//		//returns a list of subelements of specified name
//		getElementsByTagName("subelementName"); 
//		//returns a list of all child nodes
//		getChildNodes(); 
	}
	
	public void saveData(DatabaseProxy db){
		Course[] courses = db.getCourses();
		String xml = xs.toXML(courses);
		System.out.println("Saving: " + xml);
		try{
			write(xml);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String retrieveDataString(){
		try {
			return read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Error occurred when reading from file.";
	}
	
	//MUTTETTAVA MYÖHEMMIN
	public Course[] retrieveAllData(){
		String xml = retrieveDataString();
		//DatabaseProxy dbp = (DatabaseProxy)xs.fromXML(xml);
		//return dbp;
		return (Course[])xs.fromXML(xml);
	}
	
	public Course[] retrieveCourses(){
		String xml = retrieveDataString();
		return (Course[])xs.fromXML(xml);
	}
	
	private void write(String s) throws UnsupportedEncodingException,
			FileNotFoundException, IOException{
		String path = this.path;
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(path), "utf-8"))) {
			writer.write(s);
		}
	}
	
	private String read() throws IOException{
		String path = this.path;
		Charset encoding = StandardCharsets.UTF_8;
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
}
