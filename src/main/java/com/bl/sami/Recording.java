package com.bl.sami;

import java.util.ArrayList;
import java.util.List;

public class Recording {
	List<Contributor> contributors = new ArrayList<Contributor> ();
	List<String> copyrightOwners = new ArrayList<String> ();
	String callNumber = "";
	String cKey = "";
	String collectionTitle = "";
	String itemTitle = "";
	String lArk = "";
	String originalSpecies = "";
	String recordingDate = "";
	String recordingDuration = "";
	String recordingPlace = "";
	String programmeTitle = "";
	String programmeTitleEpisode = "";
	String programmeTitleModifier = "";
	String broadcaster = "";
	String broadcastDate = "";
//	String language = "";
	String recordingLocation = "";
	String mdArk = "";
	String accessRestrictions = "";
	String previousReferenceNumber = "";
	String originalIssueNumber = "";
	String recordingLocationRegion = "";
	String recordingLocationDistrict= "";
	String recordingLocationSpecificLocality = "";
	String speciesName = "";
	String subSpeciesName = "";
	
	List<Contributor> authorNotes = new ArrayList<Contributor> ();
	List<Note> contentNotes = new ArrayList<Note> ();
	List<Note> itemNotes = new ArrayList<Note> ();
	List<Note> performanceNotes = new ArrayList<Note> ();
	List<Note> recordingNotes = new ArrayList<Note> ();
	List<Recordist> recordists = new ArrayList<Recordist> ();
	List<String> linkingWorkTitles = new ArrayList<String> ();
	List<String> shelfmarks = new ArrayList<String> ();
	List<String> ISRCCodes = new ArrayList<String> ();
	List<String> hubAuthors = new ArrayList<String> ();
	List<String> languages = new ArrayList<String> ();
	
	public Recording () {
		
	}
	
	public  List<Contributor> getContributors() {
		return contributors;
	}
	
	public  void setContributors(List<Contributor> contributors) {
		this.contributors = contributors;
	}
	
	public void addContributor (Contributor contributor) {
		this.getContributors().add(contributor);
	}
	
	public String getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(String callNumber) {
		this.callNumber = callNumber;
	}

	public String getcKey() {
		return cKey;
	}

	public void setcKey(String cKey) {
		this.cKey = cKey;
	}

	public String getCollectionTitle() {
		return collectionTitle;
	}

	public void setCollectionTitle(String collectionTitle) {
		this.collectionTitle = collectionTitle;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public String getlArk() {
		return lArk;
	}

	public void setlArk(String lArk) {
		this.lArk = lArk;
	}

	public String getOriginalSpecies() {
		return originalSpecies;
	}

	public void setOriginalSpecies(String originalSpecies) {
		this.originalSpecies = originalSpecies;
	}

	public String getRecordingDate() {
		return recordingDate;
	}

	public void setRecordingDate(String recordingDate) {
		this.recordingDate = recordingDate;
	}

	public String getRecordingDuration() {
		return recordingDuration;
	}

	public void setRecordingDuration(String recordingDuration) {
		this.recordingDuration = recordingDuration;
	}
	
	public String getPreviousReferenceNumber() {
		return previousReferenceNumber;
	}

	public void setPreviousReferenceNumber(String previousReferenceNumber) {
		this.previousReferenceNumber = previousReferenceNumber;
	}
	
	public List<String> getCopyrightOwners() {
		return copyrightOwners;
	}

	public void addCopyrightOwner(String copyrightOwner) {
		this.copyrightOwners.add(copyrightOwner);
	}
	
	public void setCopyrightOwners(List<String> copyrightOwners) {
		this.copyrightOwners = copyrightOwners;
	}	

	public String getOriginalIssueNumber() {
		return originalIssueNumber;
	}

	public void setOriginalIssueNumber(String originalIssueNumber) {
		this.originalIssueNumber = originalIssueNumber;
	}

	public List<String> getLinkingWorkTitle() {
		return linkingWorkTitles;
	}

	public void setLinkingWorkTitle(List<String> linkingWorkTitle) {
		this.linkingWorkTitles = linkingWorkTitle;
	}

/*	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
*/	

	public String getRecordingLocationRegion() {
		return recordingLocationRegion;
	}

	public void setRecordingLocationRegion(String recordingLocationRegion) {
		this.recordingLocationRegion = recordingLocationRegion;
	}

	public String getRecordingLocationDistrict() {
		return recordingLocationDistrict;
	}

	public void setRecordingLocationDistrict(String recordingLocationDistrict) {
		this.recordingLocationDistrict = recordingLocationDistrict;
	}

	public String getRecordingLocationSpecificLocality() {
		return recordingLocationSpecificLocality;
	}

	public void setRecordingLocationSpecificLocality(String recordingLocationSpecificLocality) {
		this.recordingLocationSpecificLocality = recordingLocationSpecificLocality;
	}

	public List<Note> getContentNote() {
		return contentNotes;
	}

	public void setContentNote(List<Note> contentNote) {
		this.contentNotes = contentNote;
	}

	public void addContentNote (String text) {
		this.getContentNote().add(new Note (text));
	}
	
	public List<Note> getItemNotes() {
		return itemNotes;
	}

	public void setItemNotes(List<Note> itemNotes) {
		this.itemNotes = itemNotes;
	}
	
	public void addItemNote (String text) {
		this.getItemNotes().add(new Note (text));
	}

	public List<Note> getPerformanceNotes() {
		return performanceNotes;
	}

	public void setPerformanceNotes(List<Note> performanceNotes) {
		this.performanceNotes = performanceNotes;
	}

	public void addPerformanceNote (String text) {
		this.getPerformanceNotes().add(new Note (text));
	}

	public List<Note> getRecordingNotes() {
		return recordingNotes;
	}

	public void setRecordingNote(List<Note> recordingNotes) {
		this.recordingNotes = recordingNotes;
	}
	
	public void addRecordingNote (String text) {
		this.getRecordingNotes().add(new Note (text));
	}

	public String getRecordingPlace() {
		return recordingPlace;
	}

	public void setRecordingPlace(String recordingPlace) {
		this.recordingPlace = recordingPlace;
	}

	public String getProgrammeTitle() {
		return programmeTitle;
	}

	public void setProgrammeTitle(String programmeTitle) {
		this.programmeTitle = programmeTitle;
	}

	
	public String getProgrammeTitleEpisode() {
		return programmeTitleEpisode;
	}

	public void setProgrammeTitleEpisode(String programmeTitleEpisode) {
		this.programmeTitleEpisode = programmeTitleEpisode;
	}

	public String getProgrammeTitleModifier() {
		return programmeTitleModifier;
	}

	public void setProgrammeTitleModifier(String programmeTitleModifier) {
		this.programmeTitleModifier = programmeTitleModifier;
	}

	public void setRecordingNotes(List<Note> recordingNotes) {
		this.recordingNotes = recordingNotes;
	}

	public String getBroadcaster() {
		return broadcaster;
	}

	public void setBroadcaster(String broadcaster) {
		this.broadcaster = broadcaster;
	}

	public String getBroadcastDate() {
		return broadcastDate;
	}

	public void setBroadcastDate(String broadcastDate) {
		this.broadcastDate = broadcastDate;
	}

	public List<Recordist> getRecordists() {
		return recordists;
	}

	public void setRecordists(List<Recordist> recordists) {
		this.recordists = recordists;
	}
	
	public void addRecordist (Recordist recordist) {
		this.getRecordists().add(recordist);
	}

	public List<String>  getLinkingWorkTitles() {
		return linkingWorkTitles;
	}
	
	public void setLinkingWorkTitles(List<String> linkingWorkTitle) {
		this.linkingWorkTitles = linkingWorkTitle;
	}
	
	public void addLinkingWorkTitle (String text) {
		this.getLinkingWorkTitles().add(text);
	}

	public List<Contributor> getAuthorNotes() {
		return authorNotes;
	}

	public void setAuthorNotes(List<Contributor> authorNotes) {
		this.authorNotes = authorNotes;
	}

	public void addAuthorNote (Contributor authorComposer) {
		this.getAuthorNotes().add(authorComposer);
	}

	public List<String> getISRCCodes() {
		return ISRCCodes;
	}

	public void setISRCCodes(List<String> iSRCCodes) {
		ISRCCodes = iSRCCodes;
	}

	public void addISRCCode (String ISRCCode) {
		this.ISRCCodes.add(ISRCCode);
	}

	public List<Note> getContentNotes() {
		return contentNotes;
	}

	public void setContentNotes(List<Note> contentNotes) {
		this.contentNotes = contentNotes;
	}

	public String getRecordingLocation() {
		return recordingLocation;
	}

	public void setRecordingLocation(String recordingLocation) {
		this.recordingLocation = recordingLocation;
	}

	public String getMdArk() {
		return mdArk;
	}

	public void setMdArk(String mdArk) {
		this.mdArk = mdArk;
	}

	public String getAccessRestrictions() {
		return accessRestrictions;
	}

	public void setAccessRestrictions(String accessRestrictions) {
		this.accessRestrictions = accessRestrictions;
	}
	
	public List<String> getShelfmarks() {
		return shelfmarks;
	}

	public void setShelfmarks(List<String> shelfmarks) {
		this.shelfmarks = shelfmarks;
	}
	
	public void addShelfmark (String shelfmark) {
		this.shelfmarks.add(shelfmark);
	}

	public List<String> getHubAuthors() {
		return hubAuthors;
	}

	public void setHubAuthors(List<String> hubAuthors) {
		this.hubAuthors = hubAuthors;
	}
	
	public void addHubAuthor (String hubAuthor) {
		this.hubAuthors.add(hubAuthor);
	}

	public String getSpeciesName() {
		return speciesName;
	}

	public void setSpeciesName(String speciesName) {
		this.speciesName = speciesName;
	}

	public String getSubSpeciesName() {
		return subSpeciesName;
	}

	public void setSubSpeciesName(String subSpeciesName) {
		this.subSpeciesName = subSpeciesName;
	}
	
	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}
	
	public void addLanguage (String language) {
		this.languages.add(language);
	}


	
	
	
	
}
