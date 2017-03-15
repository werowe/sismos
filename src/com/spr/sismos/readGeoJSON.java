package com.spr.sismos;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

/**
 * @param args
 */
public class readGeoJSON {
	
	URL url;
	URLConnection c;

	public List<Earthquake> getJsonEarthquakes()     {

	
		
		try {
			url = new URL(
					"http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.geojson");
			c = url.openConnection();
		} catch (MalformedURLException e ) {
			Log.d("json", "MalformedURLException" + e.getMessage());
		}	
			catch (IOException e2) {
				Log.d("json", "IOException" + e2.getMessage());
		} 

	 

		c.setConnectTimeout(5000);
		c.setReadTimeout(10000);

		BufferedReader jsonStream = null;
		try {
			jsonStream = new BufferedReader(new InputStreamReader(
					url.openStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 		
	 

		List<Earthquake> eList = new ArrayList<Earthquake>();

		try {
			eList = parseJSON(jsonStream);
		} catch (Exception e) {
			 
			e.printStackTrace();
		} finally {
			try {
				jsonStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
       
		
		return eList;

	}

	private List<Earthquake> parseJSON(BufferedReader jsonStream)
			throws Exception {

		List<Earthquake> eList = new ArrayList<Earthquake>();

		StringBuilder builder = new StringBuilder();
		for (String line = null; (line = jsonStream.readLine()) != null;) {
			builder.append(line).append("\n");
		}
		JSONTokener tokener = new JSONTokener(builder.toString());

		JSONObject geoJSON = new JSONObject(tokener);
		JSONArray features = geoJSON.getJSONArray("features");
		String country = "";

		for (int i = 0; i < features.length(); i++) {

			Earthquake e = new Earthquake();
			e.setSource("JSON");
			JSONObject jsonObject = features.getJSONObject(i);
			JSONObject properties = jsonObject.getJSONObject("properties");
			JSONObject geometry = jsonObject.getJSONObject("geometry");
			JSONArray coordinates = geometry.getJSONArray("coordinates");

			e.setTitle(properties.getString("place"));

			country = properties.getString("place");

			e.setTime((String.valueOf(properties.getLong("time"))));

			e.setLink(properties.getString("url"));

			e.setMagnitude(properties.getDouble("mag"));
			e.setLongitude(coordinates.getDouble(0));
			e.setLatitude(coordinates.getDouble(1));
			e.setDepth(coordinates.getDouble(2));

		 	if ((country.toUpperCase().indexOf("CHILE") > -1)
					|| ((country.toUpperCase().indexOf("ARGENTINA")) > -1)) {
				eList.add(e);
			} 
			 
		}

		return eList;
	}

}
