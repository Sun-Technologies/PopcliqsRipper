package com.suntechnologies.services.popcliqs.ripper;



import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JoesdataRipper {

	String url = "http://www.joesdata.com/companies/UbiSoft_Entertainment_7174640/4.html";	
	
	String  industry = "Internet & Software";
	String  vertical = "Games and Gaming";
	
	public static void main(String[] args) {
		try {
			new JoesdataRipper().processPage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void processPage() throws Exception{
		
		Document doc = Jsoup.connect( url ).get();
		Elements tokens = doc.getElementsByTag("a");
//		System.out.println(tokens);
		
		Iterator<Element> it = tokens.iterator();
		
		while(it.hasNext()){
			Element token = (Element)it.next();
			if(token.text().equals("Profile")){
			 String url = 	token.attr("abs:href");
			 processVcard(url);
			 Thread.sleep(1000 * 5);
			}
		}
		
	}


	private void processVcard(String url) throws Exception{
		
		//String url = "http://www.joesdata.com/executive/_Tony_Key_178027504.html";
		Document doc = Jsoup.connect( url ).get();
		
		Elements tokens = doc.getElementsByTag("span");
		
		Element name 		= tokens.get(0);
		Element title 		= tokens.get(1);
		Element company 	= tokens.get(2);
		Element address 	= tokens.get(3);
		Element address2	= tokens.get(4);
		Element tel 		= tokens.get(5);
		Element web 		= tokens.get(7);
		
		
		System.out.println(name.text() + "," + title.text() + "," + company.text() + "," 
				+ tel.text() + "," + address.text() + "," + address2.text() + ","   + web.text() + "," + industry + "," + vertical );
		
	}
	
}
