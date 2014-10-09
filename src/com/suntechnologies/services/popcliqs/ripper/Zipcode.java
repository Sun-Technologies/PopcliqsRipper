package com.suntechnologies.services.popcliqs.ripper;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Hashtable;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Zipcode extends DefaultHandler{

	boolean istdElement = false; 
	
	String zipCode 	= null;
	String county  	= null;
	String city    	= "Bangalore";
	String state   	= "KAR";
	String lat     	= null;
	String lon     	= null;
	boolean isName 	= false; 
	boolean skipRow	= false;
	
	static Hashtable<String,String> set = new Hashtable<String,String>();
	
	String line = null;
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if( qName.equalsIgnoreCase("td")) {
			istdElement = true;
			if(attributes.getValue("itemprop") != null){
				isName = true;
			}
			
		}
		
		if( qName.equalsIgnoreCase("div") && !skipRow ) {
			
			String geoId = attributes.getValue("id") ;
			try {
				readUrl("http://geopostcodes.com/inc/getpos.php?id="+geoId);
				Thread.sleep(1000*10);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void readUrl(String httpURl) throws Exception{
		
	    URL myurl = new URL(httpURl);
	    HttpURLConnection con = (HttpURLConnection)myurl.openConnection();
	    InputStream ins = con.getInputStream();
	    InputStreamReader isr = new InputStreamReader(ins);
	    BufferedReader in = new BufferedReader(isr);
	 
	    String inputLine;
	 
	    while ((inputLine = in.readLine()) != null)
	    {
	      String tokens[] = inputLine.split(";");
	      lat = tokens[0].substring(1, tokens[0].length() -1 );
	      lon = tokens[1];
	    }
	 
	    in.close();
	  }
	
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equalsIgnoreCase("tr") ) {
			if(skipRow){
				System.out.println(" Row skipped set size " + set.size());
				skipRow = false;
			}else{
				String line = zipCode + "," + city + "," +  state + "," + lat + "," + lon + "," + county;
				set.put( zipCode, line);
				System.out.println("set size " + set.size());
			}
		}
	}
	
	
	@Override
	public void characters(char ch[], int start, int length)
			throws SAXException {
		if(istdElement){
			String val = new String(ch, start, length);
			if(!val.trim().isEmpty()){
				
				if(isName){
					county = val;
					isName = false;
				}else{
					
					if( set.containsKey(val)){
						skipRow = true;
					}else{
						zipCode = val;
					}
				}
			}
			istdElement = false;
		}
	}
	
	public static void main(String[] args) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = saxParserFactory.newSAXParser();
			Zipcode handler = new Zipcode();
			saxParser
					.parse(new File(
							"/Users/Tahir/Documents/workspace/PopcliqsRipper/resource/Zipcode.xml"),
							handler);
			
			for( String line: set.values()){
				System.out.println(line);
			}
			System.out.println(" End of processing ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
