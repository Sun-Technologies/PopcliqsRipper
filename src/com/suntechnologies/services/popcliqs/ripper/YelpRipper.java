package com.suntechnologies.services.popcliqs.ripper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xml.sax.helpers.DefaultHandler;

import com.suntechnologies.services.popcliqs.ripper.util.RipperUtil;

public class YelpRipper extends DefaultHandler {

	String popcliqsUrl = "http://localhost:8888/createevent.service.post.php";
	
	private void process(String url) throws Exception{
		
		Document doc = Jsoup.connect( url ).get();
		
		String eventTitle	 	= RipperUtil.getElementText(doc , "#event_name");
		String eventLoc 		= RipperUtil.getElementText(doc , "#location_name");
		String eventAddress		= RipperUtil.getElementText(doc , "[itemprop=streetAddress]");
		String eventCity 		= RipperUtil.getElementText(doc , "[itemprop=addressLocality]");
		String eventState 		= RipperUtil.getElementText(doc , "[itemprop=addressRegion]");
		String eventZip 		= RipperUtil.getElementText(doc , "[itemprop=postalCode]");
		String eventDescription	= RipperUtil.getElementAttributeText(doc , "meta[name=description]" , "content");
		String eventStartDt 	= RipperUtil.getElementAttributeText(doc , "[itemprop=startDate]" , "content");
		String eventEndDt 		= RipperUtil.getElementAttributeText(doc , "[itemprop=endDate]" , "content");
		String eventCategory	= RipperUtil.getElementTextFirst(doc , "dd > a");
		
		eventAddress = eventAddress + "," + eventCity + "," + eventState;
		System.out.println(eventCategory + " " +  eventDescription  + " " + eventTitle  + " " + eventLoc + " " +
				eventAddress + " " + eventCity + " " + eventState 
				+ " " + eventZip  + " " + eventStartDt+ " " + eventEndDt );
		
		String categorycd = null;
		if(eventCategory.equals("Festivals & Fairs")){
			categorycd = "6";
		}
		
		
		RipperUtil.sendPost( "event_title="  + eventTitle        + "&category_id=" + categorycd +
							 "&description=" + eventDescription  + "&location="    + eventLoc   + 
							 "&address="     + eventAddress      + "&postal_code=" + eventZip , 
							 popcliqsUrl );
	}

	public static void main(String[] args) {

		try {
			
			String url = "http://www.yelp.com/events/atlanta-chastain-park-arts-festival-2014";
			YelpRipper ripper = new YelpRipper();
			ripper.process( url );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

}
