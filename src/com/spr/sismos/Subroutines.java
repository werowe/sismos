package com.spr.sismos;

public class Subroutines {
	
	String firstLine;
	String secondLine;
	String subjectLine;
	
	Subroutines(Earthquake e) {
		
		Distance d = new Distance (e.getLongitude(), e.getLatitude());
		
		if (e.getMagnitude() >= 5) {
			firstLine = e.getTime() + " " 
					+ d.getClosestCity() + "<font color=\"red\"><b> "
					+ e.getMagnitude() + "</b></font> mag";
			
		} else {
			
		
		firstLine = e.getTime() + " " 
				+ d.getClosestCity() + "<font color=\"blue\"><b> "
				+ e.getMagnitude() + "</b></font> mag";
		}

		secondLine = "latitud " + e.getLatitude()+ 
				" longitud "+ e.getLongitude() 
				 
				     + " profundidad " + Double.toString(Math.floor(e.getDepth())).split("\\.")[0]
				    		  + " km";
		
	e.setSubjectLine(e.getTime() + " " 
			+ d.getClosestCity() + " "
			+ e.getMagnitude() + " mag");
	e.setFirstLine(firstLine);
	e.setSecondLine(secondLine);
	e.setClosetCity(d.getClosestCity());
	e.setSecondClosetCity(d.get2ndClosestCity());
	e.setThirdClosetCity(d.get3rdClosestCity());
	e.setFourthClosetCity(d.get4thClosestCity());
	
	
		
		 
	}

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

}
