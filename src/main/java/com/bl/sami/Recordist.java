package com.bl.sami;

public class Recordist {
	String name = "";
	String lifeDates = "";
	
	public Recordist () {		
	}
	
	public Recordist (String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLifeDates() {
		return lifeDates;
	}

	public void setLifeDates(String lifeDates) {
		this.lifeDates = lifeDates;
	}
}
