package com.bl.sami;

import java.util.ArrayList;
import java.util.List;

// Represents the product entry in the SAMI record
public class Product {
	List<Recording> recordings = new ArrayList<Recording> ();
	String callNumber = "";
	String cKey = "";
	String collectionTitle = "";
	String productTitle = "";
	String mdArk = "";
	String productDate = "";
	String accessRestrictions = "";
	String previousReferenceNumber = "";
	String releaseYear = "";
	String lArk = "";

	List<String> shelfmarks = new ArrayList<String> ();
	List<String> parentRecordCompanyNames = new ArrayList<String> ();
	List<String> originalPublicationDates = new ArrayList<String> ();
	List<String> recordLabelCatalogueNumbers = new ArrayList<String> ();
	List<String> labelMatchs = new ArrayList<String> ();
	List<String> pressingCodes = new ArrayList<String> ();
	List<String> copyrightOwners = new ArrayList<String> ();

	List<Note> contentNotes = new ArrayList<Note> ();
	List<Note> productNotes = new ArrayList<Note> ();
	
	public Product () {
		
	}

	public  List<Recording> getRecordings() {
		return recordings;
	}

	public  void setRecordings(List<Recording> recordings) {
		this.recordings = recordings;
	}
	
	public  void addRecording(Recording recording) {
		this.recordings.add(recording);
	}


	public  String getCallNumber() {
		return callNumber;
	}

	public  void setCallNumber(String callNumber) {
		this.callNumber = callNumber;
	}

	public  String getcKey() {
		return cKey;
	}

	public  void setcKey(String cKey) {
		this.cKey = cKey;
	}

	public  String getCollectionTitle() {
		return collectionTitle;
	}

	public  void setCollectionTitle(String collectionTitle) {
		this.collectionTitle = collectionTitle;
	}

	public  String getProductTitle() {
		return productTitle;
	}

	public  void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public String getMdArk() {
		return mdArk;
	}

	public void setMdArk(String mdArk) {
		this.mdArk = mdArk;
	}

	public String getlArk() {
		return lArk;
	}

	public void setlArk(String lArk) {
		this.lArk = lArk;
	}

	public List<Note> getContentNotes() {
		return contentNotes;
	}

	public void setContentNotes(List<Note> contentNote) {
		this.contentNotes = contentNote;
	}
	
	public void addContentNote (String text) {
		this.getContentNotes().add(new Note (text));
	}
	

	public List<Note> getProductNotes() {
		return productNotes;
	}

	public void setProductNotes(List<Note> productNote) {
		this.productNotes = productNote;
	}
	
	public void addProductNote (String text) {
		this.getProductNotes().add(new Note (text));
	}

	public String getProductDate() {
		return productDate;
	}

	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}



	public String getAccessRestrictions() {
		return accessRestrictions;
	}

	public void setAccessRestrictions(String ar) {
		this.accessRestrictions = ar;
	}

	public String getPreviousReferenceNumber() {
		return previousReferenceNumber;
	}

	public void setPreviousReferenceNumber(String previousReferenceNumber) {
		this.previousReferenceNumber = previousReferenceNumber;
	}

	public List<String> getShelfmarks() {
		return shelfmarks;
	}

	public String getShelfmark () {
		StringBuffer shelfmark = new StringBuffer ();
		for (int i=0; i<this.shelfmarks.size(); i++) {
			shelfmark.append(this.shelfmarks.get(i));
		}
		return shelfmark.toString();
	}
	public void setShelfmarks(List<String> shelfmarks) {
		this.shelfmarks = shelfmarks;
	}
	
	public void addShelfmark (String shelfmark) {
		this.shelfmarks.add(shelfmark);
	}

	public List<String> getParentRecordCompanyNames() {
		return parentRecordCompanyNames;
	}

	public void setParentRecordCompanyNames(List<String> parentRecordCompanyNames) {
		this.parentRecordCompanyNames = parentRecordCompanyNames;
	}

	public void addParentRecordCompanyName (String parentRecordCompanyName) {
		this.parentRecordCompanyNames.add(parentRecordCompanyName);
	}

	public List<String> getOriginalPublicationDates() {
		return originalPublicationDates;
	}

	public void setOriginalPublicationDates(List<String> originalPublicationDates) {
		this.originalPublicationDates = originalPublicationDates;
	}

	public void addOriginalPublicationDate (String originalPublicationDate) {
		this.originalPublicationDates.add(originalPublicationDate);
	}

	public List<String> getRecordLabelCatalogueNumbers() {
		return recordLabelCatalogueNumbers;
	}

	public void setRecordLabelCatalogueNumbers(List<String> recordLabelCatalogueNumbers) {
		this.recordLabelCatalogueNumbers = recordLabelCatalogueNumbers;
	}

	public void addRecordLabelCatalogueNumber (String recordLabelCatalogueNumber) {
		this.recordLabelCatalogueNumbers.add(recordLabelCatalogueNumber);
	}

	public List<String> getLabelMatchs() {
		return labelMatchs;
	}

	public void setLabelMatchs(List<String> labelMatchs) {
		this.labelMatchs = labelMatchs;
	}

	public void addLabelMatchs (String labelMatchs) {
		this.labelMatchs.add(labelMatchs);
	}

	public List<String> getPressingCodes() {
		return pressingCodes;
	}

	public void setPressingCodes(List<String> pressingCodes) {
		this.pressingCodes = pressingCodes;
	}

	public void addPressingCode (String pressingCode) {
		this.pressingCodes.add(pressingCode);
	}

	public List<String> getCopyrightOwners() {
		return copyrightOwners;
	}

	public void setCopyrightOwners(List<String> copyrightOwners) {
		this.copyrightOwners = copyrightOwners;
	}
	
	public void addCopyrightOwner (String copyrightOwner) {
		this.copyrightOwners.add(copyrightOwner);
	}

	public String getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
	}

	
}
