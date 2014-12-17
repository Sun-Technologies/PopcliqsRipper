package com.suntechnologies.services.popcliqs.ripper.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RipperUtil {

	private static final String USER_AGENT = "Mozilla/5.0";

	// HTTP POST request
	public static void sendPost(String urlParameters , String url) throws Exception {

		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());

	}
	
	
	public static String getElementText(Document doc , String selectString ){
		   
		   Elements elements	= doc.select(selectString);
		   return elements.text();
	   }
	   
	public static String getElementTextFirst(Document doc , String selectString ){
		   
		   Element element	= doc.select(selectString).first();
		   return element.text();
	   }
	   
	public static String getElementAttributeText(Document doc , String selectString, String attribute){
		   
		   Elements elements = doc.select(selectString);
		   return elements.attr(attribute);
	   }
}
