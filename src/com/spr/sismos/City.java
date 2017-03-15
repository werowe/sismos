package com.spr.sismos;

class City implements Comparable<City>{
	String name;
	double longitude, latitude, bearing;
	int distance;
	String bearingText;
	
	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public double getBearing() {
		return bearing;
	}

	public void setBearing(double bearing) {
		this.bearing = bearing;
	}

	public String getBearingText() {
		return bearingText;
	}

	public void setBearingText(String bearingText) {
		this.bearingText = bearingText;
	}

	City(String name, double longitude, double latitude) {
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude ;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Override
	public int compareTo(City otherCity) {
		Double otherDistance = new Double(otherCity.getDistance());
		Double thisDistance = new Double(this.getDistance());
		return Double.compare(thisDistance, otherDistance  );
		 
	 
	}
	
	
	
	
	
}