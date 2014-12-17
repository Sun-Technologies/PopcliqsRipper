package com.suntechnologies.services.popcliqs.ripper;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JoesdataRipper {

	
	static int noOfPages = 1; 
	
	
	String  industry = "Retail";
	String  vertical = "Clothing and Accessories";
	
	
	
	public static void main(String[] args) {
		
		HashMap<String,Integer> siteDate = new HashMap<String,Integer>();
		
		// Site data add here 
		siteDate.put("Hot_Topic_Inc_7158677" , 4 );
		siteDate.put("Claires_Stores_Inc_7158641" , 4 );
		
		try {
			
			Iterator it = siteDate.entrySet().iterator();
			while(it.hasNext()){

				Map.Entry pairs = (Map.Entry)it.next();
				
				String company = (String) pairs.getKey();
				int noOfPages = (Integer) pairs.getValue();
				
				System.out.println("Started   : "  + company  + " pages  : " + noOfPages);
				
				StringBuilder sb = new StringBuilder();
				for( int i = 1 ; i <= noOfPages ; i++){
							
					System.out.println("Start page  : " + i ); 
					String url = "http://www.joesdata.com/companies/"+ company +"/"+ i+".html";	
					sb.append(new JoesdataRipper().processPage(url));
					
					System.out.println("End page  : " + i ); 
				}
				writeToFile(sb.toString() , company);
				System.out.println(" End   : "  + company  + " pages  : " + noOfPages);
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static void writeToFile(String content , String company) throws Exception{
		try {
 
			File file = new File(company+".csv");
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
 
			System.out.println("Write Done Lines : "+ content.length() );
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private String  processPage(String url ) throws Exception{
		
		Document doc = Jsoup.connect( url ).timeout(1000 * 10).get();
		Elements tokens = doc.getElementsByTag("a");
//		System.out.println(tokens);
		
		Iterator<Element> it = tokens.iterator();
		
		StringBuilder sb = new StringBuilder(); 
		while(it.hasNext()){
			Element token = (Element)it.next();
			if(token.text().equals("Profile")){
			 String vCardurl = 	token.attr("abs:href");
			 String vcard =  processVcard(vCardurl);
			 sb.append( vcard + "\n");
			 Thread.sleep(1000 * 2);
			}
		}
		
		return sb.toString();
	}


	private String processVcard(String vCardurl) throws Exception{
		
		//String url = "http://www.joesdata.com/executive/_Tony_Key_178027504.html";
		Document doc = Jsoup.connect( vCardurl ).timeout(1000 * 10).get();
		
		Elements tokens = doc.getElementsByTag("span");
		
		Element name 		= tokens.get(0);
		Element title 		= tokens.get(1);
		Element company 	= tokens.get(2);
		Element address 	= tokens.get(3);
		Element address2	= tokens.get(4);
		Element tel 		= tokens.get(5);
		Element web 		= tokens.get(7);
		
		
		return  name.text().replace(",", " ")  + "," + title.text().replace(",", " ") + "," + company.text().replace(",", " ")  + "," 
				+ tel.text().replace(",", " ")  + "," + address.text().replace(",", " ")  + "," + address2.text().replace(",", " ")  + ","   + web.text() + "," + industry + "," + vertical ;
		
	}
	
}
