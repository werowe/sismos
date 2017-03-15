package com.spr.sismos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.text.format.DateFormat;

public class Earthquake {

	private double magnitude;
	private double longitude;
	private String title;
	private double latitude;
	private double depth;
	private String source;
	private String closetCity;
	private String SecondClosetCity;
	private String SubjectLine;

	public String getSubjectLine() {
		return SubjectLine;
	}

	public void setSubjectLine(String subjectLine) {
		SubjectLine = subjectLine;
	}

	public String getClosetCity() {
		return closetCity;
	}

	public void setClosetCity(String closetCity) {
		this.closetCity = closetCity;
	}

	public String getSecondClosetCity() {
		return SecondClosetCity;
	}

	public void setSecondClosetCity(String secondClosetCity) {
		SecondClosetCity = secondClosetCity;
	}

	public String getThirdClosetCity() {
		return ThirdClosetCity;
	}

	public void setThirdClosetCity(String thirdClosetCity) {
		ThirdClosetCity = thirdClosetCity;
	}

	public String getFourthClosetCity() {
		return FourthClosetCity;
	}

	public void setFourthClosetCity(String fourthClosetCity) {
		FourthClosetCity = fourthClosetCity;
	}

	private String ThirdClosetCity;
	private String FourthClosetCity;

	String firstLine;

	public String getFirstLine() {
		return firstLine;
	}

	public void setFirstLine(String firstLine) {
		this.firstLine = firstLine;
	}

	public String getSecondLine() {
		return secondLine;
	}

	public void setSecondLine(String secondLine) {
		this.secondLine = secondLine;
	}

	public String getThirdLine() {
		return thirdLine;
	}

	public void setThirdLine(String thirdLine) {
		this.thirdLine = thirdLine;
	}

	public String getFourthLine() {
		return fourthLine;
	}

	public void setFourthLine(String fourthLine) {
		this.fourthLine = fourthLine;
	}

	String secondLine;
	String thirdLine;
	String fourthLine;

	private String time;
	private String link;

	public double getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(double s) {
		this.magnitude = s;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double setDepth() {
		return depth;
	}

	public void setDepth(double depth) {
		this.depth = depth;
	}

	public String getTime() {

		String t = time.substring(0, time.length() - 4);

		Date myDate, fDate = null;
		String formattedDate = null;

 
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		if (source.contentEquals("XML")) {
			try {
				myDate = simpleDateFormat.parse(t);
				SimpleDateFormat sDF = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				formattedDate = sDF.format(myDate);
				return formattedDate;
			} catch (ParseException e) {

			}
		}

		if (source.contentEquals("JSON")) {
			 
			try {
				Date date = new Date(Long.valueOf(time).longValue());
				SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				formattedDate = sDF.format(date);
				return formattedDate;
			} catch (NumberFormatException e) {
				
				e.printStackTrace();
			}
			 
		}

		return formattedDate;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public double getDepth() {
		return depth;
	}

}
