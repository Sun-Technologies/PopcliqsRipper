package com.suntechnologies.services.popcliqs.ripper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Example extends DefaultHandler {
	boolean bAge = false;
	boolean bName = false;
	boolean bGender = false;
	boolean bRole = false;

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		if (qName.equalsIgnoreCase("Employee")) {
			// create a new Employee and put it in Map
			String id = attributes.getValue("id");
			// initialize Employee object and set id attribute

		} else if (qName.equalsIgnoreCase("name")) {
			// set boolean values for fields, will be used in setting Employee
			// variables

			bName = true;
			System.out.print(" name ");
		} else if (qName.equalsIgnoreCase("age")) {

		} else if (qName.equalsIgnoreCase("gender")) {

		} else if (qName.equalsIgnoreCase("role")) {

		}
	}

	@Override
	public void characters(char ch[], int start, int length)
			throws SAXException {
		if (bName) {
			String val = new String(ch, start, length);

			System.out.println(" : " + val);
			bName = false;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equalsIgnoreCase("Employee")) {

		}
	}

	public static void main(String[] args) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = saxParserFactory.newSAXParser();
			Example handler = new Example();
			saxParser
					.parse(new File(
							"/Users/Tahir/Documents/workspace/PopcliqsRipper/resource/example.xml"),
							handler);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
