package com.suntechnologies.services.popcliqs.ripper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	public static void main(String[] args) {
		try {
			
			String t = "6:00pm ";
			
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mma");
			Date df = sdf.parse(t.trim());
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("H:m:00");
			String s  = sdf1.format(df);
			
			System.out.println(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
