package com.bl.sami;

public class Contributor {
	String name = "";
	String lifeDates = "";
	String function = "";
	String role = "";
	
	public Contributor () {		
	}
	
	public Contributor (String name) {
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

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}
