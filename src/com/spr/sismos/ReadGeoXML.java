package com.spr.sismos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class ReadGeoXML {

	List<Earthquake> eList = new ArrayList<Earthquake>();

	Earthquake e = null;
	
	InputStream ic;
	
	URLConnection c;
	
	SAXParser saxParser;
	
	URL url;

	List<Earthquake> getJsonEarthquakes()   {
 

			SAXParserFactory factory = SAXParserFactory.newInstance();
			
			try {
				saxParser = factory.newSAXParser();
			} catch (ParserConfigurationException | SAXException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			

			
			try {
				url = new URL(
						"http://www.emsc-csem.org/service/rss/rss.php?typ=emsc");
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		
			try {
				c = url.openConnection();
			} catch (  IOException e ) {
				 Log.d("readxml", "URL SAXException" + e.getMessage());
			}

			c.setConnectTimeout(5000);
			c.setReadTimeout(10000);

			try {
			 ic = c.getInputStream();
			} catch (IOException e) {
				e.printStackTrace(); 
			} 
			
			DefaultHandler handler = new DefaultHandler() {

				boolean item = false;
				boolean title = false;
				boolean link = false;
				boolean geolat = false;
				boolean geolong = false;
				boolean emscdepth = false;
				boolean emscmagnitude = false;
				boolean emsctime = false;
				boolean pubDate = false;
				boolean status = false;
				boolean guid = false;
				boolean comments = false;
				boolean description = false;
				boolean chile = false;

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

					if (qName.equalsIgnoreCase("item")) {
						e = new Earthquake();
						item = true;
					}

					if (qName.equalsIgnoreCase("title")) {
						e = new Earthquake();
						title = true;
					}

					if (qName.equalsIgnoreCase("link")) {
						link = true;
					}

					if (qName.equalsIgnoreCase("geo:lat")) {
						geolat = true;
					}

					if (qName.equalsIgnoreCase("geo:long")) {
						geolong = true;
					}
					if (qName.equalsIgnoreCase("emsc:depth")) {
						emscdepth = true;
					}
					if (qName.equalsIgnoreCase("emsc:magnitude")) {
						emscmagnitude = true;
					}
					if (qName.equalsIgnoreCase("emsc:time")) {
						emsctime = true;
					}
					if (qName.equalsIgnoreCase("emsc:time")) {
						emsctime = true;
					}
					if (qName.equalsIgnoreCase("pubDate")) {
						pubDate = true;
					}
					if (qName.equalsIgnoreCase("status")) {
						status = true;
					}
					if (qName.equalsIgnoreCase("guid")) {
						guid = true;
					}
					if (qName.equalsIgnoreCase("comments")) {
						comments = true;
					}
					if (qName.equalsIgnoreCase("description")) {
						description = true;

					}

				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

				}

				public void characters(char ch[], int start, int length)
						throws SAXException {

					if (item) {

						item = false;
					}

					if (title) {
					try {
						String s = new String(ch, start, length);
						chile = false;
						if (s.toUpperCase().indexOf("CHILE") > -1) {
							chile = true;
						}
						e.setTitle(s);
						log("title", s);
						e.setSource("XML");
						title = false;
					} catch (Exception e) {
						Log.d("title", e.getMessage());
					}
					
					}

					if (link) {
						try {
						String s = new String(ch, start, length);
						e.setLink(s);
						log("link", s);
						link = false;
					} catch (Exception e) {
						Log.d("link", e.getMessage());
					}
					}

					if (geolat) {
						try {
						String s = new String(ch, start, length);
						e.setLatitude(Double.parseDouble(s));
						log("latitude", s);
						geolat = false;
					} catch (Exception e) {
						Log.d("geolat", e.getMessage());
					}
					}
					if (geolong) {
						try {
						String s = new String(ch, start, length);
					
						e.setLongitude(Double.parseDouble(s));
						log("longitude", s);
						geolong = false;
						}
						catch (NumberFormatException e) {
							//Log.d ("readxml", s + " is not a valid geolong" );
							Log.d ("geolong",  e.getMessage() );
						}
					}
					if (emscdepth) {
						try {
						String s = new String(ch, start, length);
						
							e.setDepth(Double.parseDouble(s));
							log("depth", s);
							emscdepth = false;
						} catch (Exception e) {
							Log.d ("emscdepth",  e.getMessage() );
						}
					
					}
					if (emscmagnitude) {
						try {
						String s = new String(ch, start, length);

						e.setMagnitude(Double.parseDouble(s.substring(2)));
						log("magnitude", s);
						emscmagnitude = false;
					} catch (Exception e) {
						Log.d ("emscmagnitude",  e.getMessage() );
					}
						
					}
					if (emsctime) {
						try {
						String s = new String(ch, start, length);
						e.setTime(s);
						log("time", s);
						emsctime = false;
					} catch (Exception e) {
						Log.d ("emsctime",  e.getMessage() );
					}
					}
					if (pubDate) {
						try {
						System.out.println("pubDate   : "
								+ new String(ch, start, length));
						pubDate = false;
					} catch (Exception e) {
						Log.d ("pubDate",  e.getMessage() );
					}
					}
					if (status) {
						status = false;
					}
					if (guid) {
						guid = false;
					}
					if (comments) {
						comments = false;
					}
					if (description) {
						try {
						description = false;
						if (chile) {
							eList.add(e);
						}
						} catch (Exception e) {
							Log.d ("description",  e.getMessage() );
						}
					}

				}

			};

			try {
				saxParser.parse(ic, handler);
			} catch (SAXException e) {
				 Log.d("readxml", "SAXException" + e.getMessage());
					return eList;
                
             } catch (IOException e) {
            	 	Log.d("readxml", "IOException" + e.getMessage());
            		return eList;
            		 
			}

		 
				try {
					ic.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 
			
		 
		return eList;

	}

	void log(String type, String value) {
      
	}
}
