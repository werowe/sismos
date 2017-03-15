package com.spr.sismos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.util.Log;

 

public class Distance {

	float fromLat, fromLong, toLat, toLong;

	SortedArrayList<City> sortedCities = new SortedArrayList<City>();

	@SuppressWarnings("hiding")
	class SortedArrayList<City> extends ArrayList<City> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("unchecked")
		public void insertSorted(City value) {
			add(value);
			Comparable<City> cmp = (Comparable<City>) value;
			for (int i = size() - 1; i > 0 && cmp.compareTo(get(i - 1)) < 0; i--)
				Collections.swap(this, i, i - 1);
		}
	}

	static List<City> cities;

	Distance() {

	}

	Distance(double longitude, double latitude) {
		
  

		cities = new ArrayList<City>();

		City Arica = new City("Arica", -18.473795, -70.281876);
		cities.add(Arica);
		City Iquique = new City("Iquique", -20.225523, -70.141801);
		cities.add(Iquique);
		City Santiago = new City("Santiago", -33.474925, -70.651763);
		cities.add(Santiago);
		City Valparaiso = new City("Valparaiso", -33.043149, -71.635039);
		cities.add(Valparaiso);
		City Pichilemu = new City("Pichilemu", -34.391141, -71.994540);
		cities.add(Pichilemu);
		City SanPedrodeAtacama = new City("San Pedro de Atacama", -22.908241,
				-68.207562);
		cities.add(SanPedrodeAtacama);
		City Ollagoe = new City("Ollagoe", -21.224359, -68.262437);
		cities.add(Ollagoe);
		City Vallenar = new City("Vallenar", -28.565189, -70.792835);
		cities.add(Vallenar);
		City Calama = new City("Calama", -22.410613, -68.894218);
		cities.add(Calama);
		City Curico = new City("Curico", -34.996172, -71.221824);
		cities.add(Curico);
		City Concepcion = new City("Concepcion", -36.820494, -73.041404);
		cities.add(Concepcion);
		City Valdivia = new City("Valdivia", -39.818752, -73.230696);
		cities.add(Valdivia);
		City Pica = new City("Pica", -20.488852, -69.326924);
		cities.add(Pica);
		City Antofagasta = new City("Antofagasta", -23.595530, -70.339040);
		cities.add(Antofagasta);
		City Talca  = new City("Talca", -35.331428, -71.581916);
		cities.add(Talca);
		City Chillon  = new City("Chillon", -36.605942, -72.094927);
		cities.add(Chillon);
		City Coquimbo = new City("Coquimbo", -29.954979, -71.346351);
		cities.add(Coquimbo);
	
		closestFourCities(longitude, latitude);
	}


	public String getClosestCity() {
		City closestCity = sortedCities.get(0);

		return closestCity.getDistance() + "km al "
				+ closestCity.getBearingText() + " de " + closestCity.getName();
				

	}
	public String get2ndClosestCity() {
		City closestCity = sortedCities.get(1);

		return closestCity.getDistance() + "km al "
		+ closestCity.getBearingText() + " de " + closestCity.getName()
		+ " dirección " + dtoS(closestCity.getBearing()) + "°";

	}
	public String get3rdClosestCity() {
		City closestCity = sortedCities.get(2);

		return closestCity.getDistance() + "km al "
		+ closestCity.getBearingText() + " de " + closestCity.getName()
		+ " dirección " + dtoS(closestCity.getBearing()) + "°";

	}
	public String get4thClosestCity() {
		City closestCity = sortedCities.get(3);

		return closestCity.getDistance() + "km al "
		+ closestCity.getBearingText() + " de " + closestCity.getName()
		+ " dirección " + dtoS(closestCity.getBearing())  + "°";

	}
	
	private String dtoS(double d) {
		return Double.toString(Math.floor(d)).split("\\.")[0];
	}

	private int bearing( City to, City from  ) {

		double lat1 = from.getLongitude();
		double lon1 = from.getLatitude();
		double lat2 = to.getLongitude();
		double lon2 = to.getLatitude();

		lat1 = lat1 * Math.PI / 180;
		lat2 = lat2 * Math.PI / 180;
		double dLon = (lon2 - lon1) * Math.PI / 180;
		double y = Math.sin(dLon) * Math.cos(lat2);
		double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
				* Math.cos(lat2) * Math.cos(dLon);
		
		  

		double brng = Math.atan2(y, x) * 180 / Math.PI;
		if (brng < 0) {
			brng = brng + 360;
		}
             
		   
            return (int) Math.round(brng);
	}

	private void closestFourCities(double longitude, double latitude) {

		City point = new City("quake", latitude, longitude );

		int distance;
		double bearing;

		Distance d = new Distance();

		for (City c : cities) {
			distance = d.distance(point, c);
			bearing = d.bearing(point, c);
		 
			c.setDistance(distance);
			c.setBearing(bearing);
			c.setBearingText(bearingToText(bearing));
			sortedCities.insertSorted(c);

		}

		for (City sl : sortedCities) {
			 
		}

	}

	private String bearingToText(double bearing) {

		if (bearing <= 45) {
			return "NE";
		}

		if (bearing <= 90) {
			return "E";
		}

		if (bearing <= 135) {
			return "SE";
		}

		if (bearing <= 180) {
			return "S";
		}
 

		if (bearing <= 225) {
			return "SO";
		}

		if (bearing <= 270) {
			return "O";
		}

		if (bearing <= 315) {
			return "NO";
		}

		if (bearing <= 360) {
			return "N";
		}

		return "X";
	}

	

	private int distance(City from, City to) {

		double lat1 = from.getLongitude();
		double lon1 = from.getLatitude();
		double lat2 = to.getLongitude();
		double lon2 = to.getLatitude();

		String unit = "K";

		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == "K") {
			dist = dist * 1.609344;
		} else if (unit == "N") {
			dist = dist * 0.8684;
		}
		return (int) Math.round(dist);
	}

	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	/* :: This function converts decimal degrees to radians : */
	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/* :::::::::::::::::::::::::::::::::::::::: */
	/* :: This function converts radians to decimal degrees : */
	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	private double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

}
