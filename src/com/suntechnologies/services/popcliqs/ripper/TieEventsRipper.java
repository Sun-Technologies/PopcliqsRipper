package com.suntechnologies.services.popcliqs.ripper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.suntechnologies.services.popcliqs.ripper.util.RipperUtil;

public class TieEventsRipper {
	
	
	private String IT_EVENTS_URL = "http://itevents.co.in/robo.php";
	private void process(String url) throws Exception{
			
		Document doc = Jsoup.connect( url ).get();
		

		String eventTitle	 	= RipperUtil.getElementText(doc , "h1");
		
		String eventdate        = doc.getElementsByClass("date-display-single").text().split("-")[0].trim();
		String eventstarttime	= doc.getElementsByClass("date-display-start").text();
		String eventEndtime	 	=  doc.getElementsByClass("date-display-end").text();
		
		String address = doc.getElementsByClass("street-address").text() 
						+ " " + doc.getElementsByClass("locality").text()
						+ " " + doc.getElementsByClass("postal-code").text() ;
		
		String desc 	=  doc.getElementsByClass("content").get(3).text();
		
		
		String shortAddress =  doc.getElementsByClass("fn").text() +  " " +  doc.getElementsByClass("locality").text();
		
		
		
		String dateStr = eventdate.split(",")[1] + " "+ eventdate.split(",")[2];
		
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
		Date df = sdf.parse(dateStr.trim());
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		String startdate  = sdf1.format(df);
		
		System.out.println(eventTitle + " \n Date " +  startdate +  "\n  start time " + getTimeStr(eventstarttime)
				+ " \n end time" + getTimeStr(eventEndtime)  
				+  "\n addresss : " + address  + " \n short address " + shortAddress 
				+ " \n desc " + desc );
		
		/*
		 * 
		 * 
		 * event_title:event2
			event_type:1
			issingleday:1
			event_time:09:00:00
			end_time:00:00:00
			event_date:2014-12-12
			end_date:2014-12-12
			short_address:my short address
			short_name:Short Name
			image_url:img/event1.jpg
			website:http://codeforthekingdom.com/bangalore2014.html
			websitereg:http://in.bookmyshow.com/events/plugged-in/ET00023618
			full_address:my
			full_description:<p>My description</p>
		 */
		
	
		
		RipperUtil.sendPost( 	"event_title="  + eventTitle        
								+ "&event_type=5" 
								+ "&issingleday=1" 
								+ "&event_time="       + getTimeStr(eventstarttime)
								+ "&end_time="         + getTimeStr(eventEndtime)  
								+ "&event_date="       + startdate
								+ "&end_date="         + startdate
								+ "&short_address="    + shortAddress
								+ "&short_name="       + eventTitle
								+ "&image_url=img/tie-blr.jpg" 
								+ "&full_address="    	+ address
								+ "&full_description=" 	+ desc
								+ "&website="			+ url
								+ "&websitereg=" 			+ url
				 				, IT_EVENTS_URL );
		
	}

	private String getTimeStr(String eventstarttime) throws ParseException {
		SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mma");
		Date df1 = sdf2.parse(eventstarttime.trim());
		
		SimpleDateFormat sdf3 = new SimpleDateFormat("H:m:00");
		String s  = sdf3.format(df1);
		return s;
	}
	
	public static void main(String[] args) {
		
		String tieEventurl = "https://bangalore.tie.org/event/8/tie-hacknights";
		TieEventsRipper tie = new TieEventsRipper();
		try {
			tie.process(tieEventurl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
