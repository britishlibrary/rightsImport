package com.bl.sami;

public class Note {
	String note = null;

	public Note () {
		
	}

	public Note (String text) {
		this.setNote(text);
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
}
