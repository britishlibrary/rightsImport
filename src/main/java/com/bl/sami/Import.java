package com.bl.sami;

/**
 * Last modified 11-01-2018: Added 730 and 731 to recording
 */

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class Import {
	static List<Product> products = new ArrayList<Product> ();
	static List<String> notPartOfCollection = new ArrayList<String> ();
	static Map<String, List<Integer>> productToRecordings = new HashMap<String, List<Integer>> ();
	static Map<String, Integer> shelfmarksToProductId = new HashMap<String, Integer> ();
	static int maxLinkingWorkTitles = 1;
	static int maxProductNotes = 1;
	static int maxAuthorNotes = 1;
	static int maxContentNotes = 1;
	static int maxItemNotes = 1;
	static int maxPerformanceNotes = 1;
	static int maxRecordingNotes = 1;
	static int maxContributors = 1;
	static int maxRecordists = 1;
	static int maxSoundRecordists = 1;
	static int maxHubAuthorNotes = 1;
	static int maxProductShelfmarks = 1;
	static int maxRecordingShelfmarks = 1;
	static int maxLanguages = 1;
	
	static int maxOriginalPublicationDates = 1;
	static int maxISRCCodes = 1;
	static int maxParentRecordCompanyNames = 1;
	static int maxRecordLabelCatalogueNumbers = 1;

	static int maxLabelMatchs = 1;
	static int maxPressingCodes = 1;
	static int maxProductCopyrightOwners = 0;
	static int maxRecordingCopyrightOwners = 0;
	static int maxReleaseYears = 1;
	static int maxOriginalIssueNumbers = 1;
	
	static List<String> outputLines = new ArrayList<String> ();
	final static Charset ENCODING = StandardCharsets.UTF_8;

	private static int processRecordingItem(List<String> lines, int index) {
		Recording recording = new Recording ();
		List<String> shelfmarks = new ArrayList<String> ();
		while (!lines.get(index).startsWith(".endblock") && index < lines.size()) {
			String marcField = lines.get(index);
			if (!marcField.contains(".bl")) {
				marcField = marcField.trim().substring(0, 3);
				String text = lines.get(index).trim().substring(5);
				switch (marcField) {
				case "001" :
					if (text.contains("|a")) {
						recording.setcKey(text.substring(text.indexOf("|a")+2));
					}
					break;
				case "021" :
					if (text.contains("|a")) {
						recording.addISRCCode(text.substring(text.indexOf("|a")+2));
					}					
					break;
				case "041" :
					if (text.contains("|a")) {
						String language = text.substring(text.indexOf("|a")+2);
						recording.addLanguage(language);
//						shelfmarks.add(shelfmark);						
					}
					// To be added
					break;
				case "087" :
					if (text.contains("|a")) {
						String shelfmark = text.substring(text.indexOf("|a")+2);
						// Bit of a kludge here but this checks to see if item
						// is in scope or not
						recording.addShelfmark(shelfmark);
						shelfmarks.add(shelfmark);						
					}
					break;
				case "091" :
					recording.setPreviousReferenceNumber(text.substring(text.indexOf("|a")+2));
					break;
				case "246" :
					if (text.contains("|a")) {
						recording.setItemTitle(text.substring(text.indexOf("|a")+2));
					}
					break;
				case "260" :
					if (text.contains("|c")) {
						recording.setRecordingDate(text.substring(text.indexOf("|c")+2));
					}
					break;
				case "299" :
					if (text.contains("|a")) {
						recording.addLinkingWorkTitle(text.substring(text.indexOf("|a")+2));
					}
					break;
				case "301" :
					if (text.contains("|a")) {
						recording.setRecordingDuration(text.substring(text.indexOf("|a")+2));
					}
					break;
				case "351" :
					StringTokenizer strTok = new StringTokenizer (text, "|");
					while (strTok.hasMoreElements()) {
						String str = ((String) strTok.nextElement()).trim();
						String pipe = str.substring(0, 1);
						str = str.substring(1, str.length()).trim();
						switch (pipe) {
						case "a" :
							recording.addCopyrightOwner (str);
//							recording.setCopyrightOwner (text.substring(text.indexOf("|a")+2));
							break;
						case "c" :
							break;
						default :
							System.out.println("Unrecognised pipe identifier ---"+pipe+" in "+text);
							break;
						}
					}
/*					if (text.contains("|a")) {
						recording.setAccessRestrictions(text.substring(text.indexOf("|a")+2));
					}
*/					break;
				case "470" :
					strTok = new StringTokenizer (text, "|");
					while (strTok.hasMoreElements()) {
						String str = ((String) strTok.nextElement()).trim();
						if (!str.equals(":")) {
							String pipe = str.substring(0, 1);
							str = str.substring(1, str.length()).trim();
							
							// Tidy up where may have |c(.....;|d.......)
							if (str.startsWith("(")) {
								str =str.substring(1, str.length()-1);
							}
							if (str.endsWith(")")) {
								str = str.substring(0, str.length()-1);
							}														
							switch (pipe) {
							case "a" :
								recording.setBroadcaster(str);
								break;
							case "c" :
								recording.setBroadcastDate(str);
								break;
							case "t" :
								break;
							case "?" :
							case "=" :
								break;
							default :
								System.out.println("Unrecognised pipe identifier ---"+pipe+" in "+text);
								break;
							}
						}
					}				
					break;
				case "474" :
					strTok = new StringTokenizer (text, "|");
					while (strTok.hasMoreElements()) {
						String str = ((String) strTok.nextElement()).trim();
						if (!str.equals(":")) {
							String pipe = str.substring(0, 1);
							str = str.substring(1, str.length()).trim();

							switch (pipe) {
							case "a" :
								recording.setProgrammeTitle(str);
								break;
							case "b" :
								recording.setProgrammeTitleEpisode(str);
								break;
							case "c" :
								recording.setProgrammeTitleModifier(str);
								break;
							default :
								System.out.println("Unrecognised pipe identifier ---"+pipe+" in "+text);
								break;
							}
						}
					}
					break;
				case "490" :
					if (text.contains("|a")) {
						recording.setCollectionTitle(text.substring(text.indexOf("|a")+2));
					}
					break;
				case "503" :
					strTok = new StringTokenizer (text, "|");
					while (strTok.hasMoreElements()) {
						String str = ((String) strTok.nextElement()).trim();
						String pipe = str.substring(0, 1);
						switch (pipe) {
						case "a" :
							recording.addHubAuthor(text.substring(text.indexOf("|a")+2));
							break;
						default :
							System.out.println("Unrecognised pipe identifier ---"+pipe+" in "+text);
							break;
						}
					}
/*					break;
				case "504" :
					strTok = new StringTokenizer (text, "|");
					Contributor authorComposer = new Contributor ();
					while (strTok.hasMoreElements()) {
						String str = ((String) strTok.nextElement()).trim();
						if (!str.equals(":")) {
							String pipe = str.substring(0, 1);
							str = str.substring(1, str.length()).trim();
							
							// Tidy up where may have |c(.....;|d.......)
							if (str.startsWith("(")) {
								str =str.substring(1, str.length()-1);
							}
							if (str.endsWith(")")) {
								str = str.substring(0, str.length()-1);
							}
							
							
							switch (pipe) {
							case "a" :
								authorComposer.setName(str);
								break;
							case "b" :
								authorComposer.setLifeDates(str);
								break;
							case "c" :
								authorComposer.setFunction(str);
								break;
							case "d" :
								authorComposer.setRole(str);
								break;
							case "?" :
							case "=" :
								break;
							default :
								System.out.println("Unrecognised pipe identifier --- "+pipe+" in "+marcField);
								break;
							}
						}
					}
					recording.addAuthorNote(authorComposer);
/*					if (text.contains("|a")) {
						recording.addAuthorNote(text.substring(text.indexOf("|a")+2));
					}
*/					break;
				case "506" :
					if (text.contains("|a")) {
						recording.addItemNote(text.substring(text.indexOf("|a")+2));
					}
					break;
				case "507" :
					if (text.contains("|a")) {
						recording.setOriginalSpecies(text.substring(text.indexOf("|a")+2));
					}
					break;
				case "508" :
					if (text.contains("|a")) {
						recording.addPerformanceNote(text.substring(text.indexOf("|a")+2));
					}
					break;
				case "509" :
					if (text.contains("|a")) {
						recording.addRecordingNote(text.substring(text.indexOf("|a")+2));
					}
					break;
				case "528" :
//					System.out.println("Text is ---"+text+"---");
					strTok = new StringTokenizer (text, "|");
					while (strTok.hasMoreElements()) {
						String str = ((String) strTok.nextElement()).trim();
						if (!str.equals(":")) {
							String pipe = str.substring(0, 1);
							str = str.substring(1, str.length()).trim();
							switch (pipe) {
							case "a" :
								recording.addCopyrightOwner (str);
								break;
							case "b" :
								break;
							default :
								System.out.println("Unrecognised pipe identifier ---"+pipe+" in "+text);
								break;
							}
						}
					}
					break;
				case "541" :
					System.out.println("541:"+text);
					break;
				case "551" :
					if (text.contains("|a")) {
						recording.setRecordingLocation(text.substring(text.indexOf("|")+2));
					}
					break;
				case "552" :
					if (text.contains("|a")) {
						recording.setRecordingLocationRegion(text.substring(text.indexOf("|a")+2));
					}
					break;					
				case "553" :
					if (text.contains("|a")) {
						recording.setRecordingLocationDistrict(text.substring(text.indexOf("|a")+2));
					}
					break;					
				case "554" :
					if (text.contains("|a")) {
						recording.setRecordingLocationSpecificLocality(text.substring(text.indexOf("|a")+2));
					}
					break;
				case "561" :
					System.out.println("561:"+text);
					break;
				case "631" :
					if (text.contains("|a")) {
						recording.setRecordingPlace(text.substring(text.indexOf("|a")+2));
					}
					break;
				case "702" :
//					System.out.println("Text is ---"+text+"---");
					strTok = new StringTokenizer (text, "|");
					Contributor contributor = new Contributor ();
					while (strTok.hasMoreElements()) {
						String str = ((String) strTok.nextElement()).trim();
//						System.out.println("str is ---"+str+"---");
						if (!str.equals(":")) {
							String pipe = str.substring(0, 1);
							str = str.substring(1, str.length()).trim();
							
							// Tidy up where may have |c(.....;|d.......)
							if (str.startsWith("(")) {
								str =str.substring(1, str.length()-1);
							}
							if (str.endsWith(")")) {
								str = str.substring(0, str.length()-1);
							}
							
							
							switch (pipe) {
							case "a" :
								contributor.setName(str);
								break;
							case "b" :
								contributor.setLifeDates(str);
								break;
							case "c" :
								contributor.setFunction(str);
								break;
							case "d" :
								contributor.setRole(str);
								break;
							case "?" :
							case "=" :
								break;
							default :
								System.out.println("Unrecognised pipe identifier --- "+pipe+" in "+marcField);
								break;
							}
						}
					}
					recording.addContributor(contributor);
					break;
				case "707" :
					strTok = new StringTokenizer (text, "|");
					Recordist recordist = new Recordist ();
					while (strTok.hasMoreElements()) {
						String str = ((String) strTok.nextElement()).trim();
						String pipe = str.substring(0, 1);
						switch (pipe) {
						case "a" :
							recordist.setName(text.substring(text.indexOf("|a")+2));
							break;
						case "b" :
							recordist.setLifeDates(text.substring(text.indexOf("|a")+2));
							break;
						default :
							System.out.println("Unrecognised pipe identifier ---"+pipe+" in "+text);
							break;
						}
					}
					recording.addRecordist (recordist);
					break;
				case "730" :
					strTok = new StringTokenizer (text, "|");
					while (strTok.hasMoreElements()) {
						String str = ((String) strTok.nextElement()).trim();
						if (!str.equals(":")) {
							String pipe = str.substring(0, 1);
							str = str.substring(1, str.length()).trim();
							
							switch (pipe) {
							case "a" :
								recording.setSpeciesName(str);
								break;
							case "?" :
							case "=" :
								break;
							default :
								System.out.println("Unrecognised pipe identifier --- "+pipe+" in "+marcField);
								break;
							}
						}
					}
					break;
				case "731" :
					strTok = new StringTokenizer (text, "|");
					while (strTok.hasMoreElements()) {
						String str = ((String) strTok.nextElement()).trim();
						if (!str.equals(":")) {
							String pipe = str.substring(0, 1);
							str = str.substring(1, str.length()).trim();
							
							switch (pipe) {
							case "a" :
								recording.setSubSpeciesName(str);
								break;
							case "?" :
							case "=" :
								break;
							default :
								System.out.println("Unrecognised pipe identifier --- "+pipe+" in "+marcField);
								break;
							}
						}
					}
					break;
				case "974" :
					if (text.contains("|a")) {
						recording.setMdArk(text.substring(text.indexOf("|a")+2));
					}					
					break;					
				case "975" :
					if (text.contains("|a")) {
						recording.setlArk(text.substring(text.indexOf("|a")+2));
					}
					break;
				default :
					System.out.println("Unrecognised pipe identifier --- "+marcField);
					//System.out.println(marcField);
						
				}
			} else { // If line is .block then next line is the ???
				index++;
				recording.setCallNumber(lines.get(index).trim());
			}
			index++;
		}
		
		for (int i=0; i<notPartOfCollection.size();i++) {
			String sm = notPartOfCollection.get(i);
			if (recording.getShelfmarks().contains(sm)) {
				System.out.println("Ignoring shelfmark "+sm+"!!!!!!!!!!!!!!");
				return index;
			}
		}
		
		if (recording.getLanguages().size() > maxLanguages) {
			maxLanguages = recording.getLanguages().size();
		}
			
		if (recording.getShelfmarks().size() > maxRecordingShelfmarks) {
			maxRecordingShelfmarks = recording.getShelfmarks().size();
		}
		
		if (recording.getContributors().size() > maxContributors) {
			maxContributors = recording.getContributors().size();
		}
		
		if (recording.getItemNotes().size() > maxItemNotes) {
			maxItemNotes = recording.getItemNotes().size();
		}
		
		if (recording.getRecordists().size() > maxRecordists) {
			maxRecordists = recording.getRecordists().size();
		}
		
		if (recording.getCopyrightOwners().size() > maxRecordingCopyrightOwners) {
			maxRecordingCopyrightOwners = recording.getCopyrightOwners().size();
		}

		Set<Integer> productsReferenced = new HashSet<Integer> ();
		for (int i=0; i<shelfmarks.size(); i++) {
			Integer prodRef = shelfmarksToProductId.get(shelfmarks.get(i));
			if (prodRef != null) {
//				System.out.println(prodRef+", "+shelfmarks.get(i));
				productsReferenced.add(prodRef);
			} else {
				System.out.println("Shelfmark "+shelfmarks.get(i)+" is not referenced in any product records");
			}
		}
		
		
		
		if (productsReferenced.size() == 1) {
			Integer p = productsReferenced.iterator().next();
			if (p != null) {
				products.get(productsReferenced.iterator().next()).addRecording(recording);
			} else {
				System.out.println("++++Shelfmark not found");
			}
		} else {
			Iterator<Integer> iter = productsReferenced.iterator();
			while (iter.hasNext()) {
				Product p = products.get(iter.next());
				System.out.println ("more than one product with this shelfmark "+p.getcKey());
				System.out.println("Shelfmarks is "+p.getShelfmarks().toString());
			}
		}		
		return index;
	}
	
	private static int processProductItem(List<String> lines, int index) {
		Product product = new Product ();
		while (!lines.get(index).startsWith(".endblock") && index < lines.size()) {
			String marcField = lines.get(index);
			if (!marcField.contains(".bl")) {
				marcField = marcField.trim().substring(0, 3);
				String text = lines.get(index).trim().substring(5);
				switch (marcField) {
				case "001" :
					if (text.contains("|a")) {
						product.setcKey(text.substring(text.indexOf("|a")+2));
					}
					break;
				case "031" :
					if (text.contains("|a")) {
						product.addParentRecordCompanyName(text.substring(text.indexOf("|a")+2));
					}
					break;
				case "032" :
					if (text.contains("|a")) {
						product.addRecordLabelCatalogueNumber(text.substring(text.indexOf("|a")+2));
					}
					break;
				case "035" :
					// To be added - Repeatable
					break;
				case "044" :
					if (text.contains("|a")) {
						product.addPressingCode(text.substring(text.indexOf("|a")+2));
					}
					break;
				case "087" :
					if (text.contains("|a")) {
						String shelfmark = text.substring(text.indexOf("|a")+2);
						product.addShelfmark(shelfmark);
						shelfmarksToProductId.put (shelfmark, products.size());
					}
					break;
				case "091" :
					if (text.contains("|a")) {
						product.setPreviousReferenceNumber(text.substring(text.indexOf("|a")+2));
					}
					break;
				case "260" :
					if (text.contains("|c")) {
						product.setProductDate(text.substring(text.indexOf("|c")+2));
					}
					break;
				case "266" :
					if (text.contains("|a")) {
						product.addOriginalPublicationDate(text.substring(text.indexOf("|a")+2));
					}
					break;
				case "351" :
					if (text.contains("|a")) {
						product.setAccessRestrictions(text.substring(text.indexOf("|a")+2));
					}
					break;
				case "490" :
					if (text.contains("|a")) {
						if (text.substring(text.indexOf("|a")+2).contains("|")) {
							product.setCollectionTitle(text.substring(text.indexOf("|a")+2, text.lastIndexOf("|")));
						} else {
							product.setCollectionTitle(text.substring(text.indexOf("|a")+2));
							
						}
					}
					break;
				case "499" :
					if (text.contains("|a")) {
						product.setProductTitle(text.substring(text.indexOf("|a")+2));
					}
					break;
				case "502" :
					if (text.contains("|a")) {
						product.addProductNote(text.substring(text.indexOf("|a")+2));
					}
					break;
				case "505" :
					if (text.contains("|a")) {
						product.addContentNote(text.substring(text.indexOf("|a")+2));
					}
					break;
				case "523" :
					System.out.println("523:"+text);
					break;
				case "536" :
					if (text.contains("|a")) {
						product.addCopyrightOwner(text.substring(text.indexOf("|a")+2));
					}
					break;
				case "541" :
					System.out.println("541:"+text);
					break;					
					
				case "702" :
					System.out.println("702:"+text);
					break;
				case "940" :
					System.out.println("940:"+text);
					break;
				case "974" :
					if (text.contains("|a")) {
						product.setMdArk(text.substring(text.indexOf("|a")+2));
					}
					break;
				case "975" :
					if (text.contains("|a")) {
						product.setlArk(text.substring(text.indexOf("|a")+2));
					}
					break;
				default :
					System.out.println("Unrecognised marc field in file |"+marcField);						
				}
			} else { // If line is .block then next line is the ???
				index++;
				product.setCallNumber(lines.get(index).trim());
			}
			index++;
		}
		
		if (product.getProductNotes().size() > maxProductNotes) {
			maxProductNotes = product.getProductNotes().size();
		}
		
		if (product.getShelfmarks().size() > maxProductShelfmarks) {
			maxProductShelfmarks = product.getShelfmarks().size();
		}
		
		if (product.getCopyrightOwners().size() > maxProductCopyrightOwners) {
			maxProductCopyrightOwners = product.getCopyrightOwners().size();
		}


		products.add(product);
		return index;
	}
	
	private static int processProductFile (List<String> lines) {
		int index = 0;
		
		while (index <lines.size()) {
			if (lines.get(index).contains("001:")) {
				index = processProductItem (lines, index);
			} else {
				index++;
			}		
		}		
/*		Iterator<String> iter = shelfmarksToProductId.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println(key+" contained in "+shelfmarksToProductId.get(key));
		}
		
*/		return index;
	}
	
	private static int processRecordingFile (List<String> lines) {
		int index = 0;
		
//    	maxContributors = -1;
//    	maxItemNotes = -1;
		while (index <lines.size()) {
			if (lines.get(index).contains("001:")) {
				index = processRecordingItem (lines, index);
			} else {
				index++;
			}		
		}
		return index;
	}
	
	/*
	 * 
	 * Added 09-12-2018 to identify which of the collection lines in the
	 * recording file do not need to be rights cleared as they are out of
	 * scope
	 */
	
	private static void processCollectionScopeFile (List<String> lines) {
		String identifier = "";
		for (int i=0;i<lines.size(); i++) {
			String text = lines.get(i);
			String[] x = text.split(",");
			for (int j=0; j< x.length; j++) {
				String str = x[j];
				if (j == 0) {
					identifier = new String (str);
				}
				if (j == 8 && str.equals("N/A")) {
					String parts[] = identifier.split("/");
					if (parts[1].length() == 1) {
						notPartOfCollection.add(parts[0]+"/00"+parts[1]);						
					} else if (parts[1].length() == 2) {
						notPartOfCollection.add(parts[0]+"/0"+parts[1]);						
					} else {
						notPartOfCollection.add(identifier);						
					}
				}
			}
		}
	}
	
	private static void processFile (Path filePath) {
		List<String> lines = new ArrayList<String> ();
		List<String> validLines = new ArrayList<String> ();
		try {
			lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
			System.out.println("Number of lines in the file is "+lines.size());
			for (int i=0; i<lines.size(); i++) {
				if (lines.get(i).length() != 0) {
					validLines.add(lines.get(i));
				}
			}
			System.out.println("Number of valid lines is "+validLines.size());
			
			if (filePath.toString().toLowerCase().contains("product")) {
				processProductFile(validLines);
			} else if (filePath.toString().toLowerCase().contains("record")) {
				if (validLines.get(0).startsWith("\uFEFF")) {
					validLines.get(0).replaceAll("\uFEFF", "");
				}
//				byte[] bytes = lines.get(0).getBytes(ENCODING);
//				lines.set(0, new String (bytes, 3, bytes.length-3));
/*				System.out.println("-------Number of lines is "+lines.size());
				for (int i=0; i<lines.size(); i++) {
					if (lines.get(i).length() != 0) {
						validLines.add(lines.get(i));
					}
				}
*/				System.out.println("number of recording valid lines is "+validLines.size());
				processRecordingFile(validLines);				
			} else {
				processCollectionScopeFile (validLines);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String createColumnHeaders () {
		String csvHeading       =  "Product"; 		// Collection Title
		csvHeading = csvHeading + "|Product";			// Call Number
		csvHeading = csvHeading + "|Recording";			// Call Number
		csvHeading = csvHeading + "|Product";			// CKEY
		csvHeading = csvHeading + "|Recording";			// CKEY
		for (int i=0; i<maxProductShelfmarks; i++) {
			csvHeading = csvHeading + "|Product";		// Product Shelfmarks
		}
		for (int i=0; i<maxRecordingShelfmarks; i++) {
			csvHeading = csvHeading + "|Recording";		// Recording Shelfmarks
		}
		csvHeading = csvHeading + "|Recording";			// MD-ARK
		csvHeading = csvHeading + "|Recording";			// L-ARK
		csvHeading = csvHeading + "|Product";			// L-ARK
		csvHeading = csvHeading + "|Product";			// Previous reference number
		csvHeading = csvHeading + "|Recording";			// Previous reference number
		csvHeading = csvHeading + "|Product";			// Product Title
		csvHeading = csvHeading + "|Recording";			// Item Title
		csvHeading = csvHeading + "|Recording";			// Original Species
		csvHeading = csvHeading + "|Recording";			// Species Name
		csvHeading = csvHeading + "|Recording";			// Sub Species Name
		csvHeading = csvHeading + "|Recording";			// Sound Recording Date
		csvHeading = csvHeading + "|Recording";			// Duration of Recording
		csvHeading = csvHeading + "|SP";				//
		csvHeading = csvHeading + "|SP";				//
		csvHeading = csvHeading + "|SP";				//
		for (int i=0; i<maxRecordingCopyrightOwners; i++) {
			csvHeading = csvHeading + "|Recording";			// Sound Recording Copyright Owner
		}
		
		// Broadcast Type Rights
		csvHeading = csvHeading + "|Recording";			// Programme Title
		csvHeading = csvHeading + "|Recording";			// Programme Title Numeration
		csvHeading = csvHeading + "|Recording";			// Programme Title Modifier
		csvHeading = csvHeading + "|Recording";			// Broadcaster
		csvHeading = csvHeading + "|Recording";			// Broadcast Date
		csvHeading = csvHeading + "|SP";				//
		csvHeading = csvHeading + "|SP";				//
		csvHeading = csvHeading + "|SP";				//

		// Contributor section
		for (int i=0; i<maxContributors; i++) {
			csvHeading = csvHeading + "|Recording ";	// Contributor Name		
			csvHeading = csvHeading + "|Recording ";	// Contributor Life Dates
			csvHeading = csvHeading + "|Recording ";	// Contributor Function
			csvHeading = csvHeading + "|Recording ";	// Contributor Role
			csvHeading = csvHeading + "|Recording ";	// Contributor Nationality
		}
		
		csvHeading = csvHeading + "|SP";
		csvHeading = csvHeading + "|SP";
		csvHeading = csvHeading + "|SP";
		csvHeading = csvHeading + "|SP";
		
		for (int i=0; i<maxSoundRecordists; i++) {		
			csvHeading = csvHeading + "|Recording ";	// Sound Recordist Name
			csvHeading = csvHeading + "|Recording ";	// Sound Recordist Life Dates
		}
		
		csvHeading = csvHeading + "|SP";
		csvHeading = csvHeading + "|SP";
		csvHeading = csvHeading + "|SP";
		csvHeading = csvHeading + "|SP";
		// Works Area
		for (int i=0; i<maxLinkingWorkTitles; i++) {
			csvHeading = csvHeading + "|Recording";		// Linking Work Title
		}
		csvHeading = csvHeading + "|SP";
		csvHeading = csvHeading + "|SP";
		csvHeading = csvHeading + "|SP";
		csvHeading = csvHeading + "|SP";
		csvHeading = csvHeading + "|SP";
		csvHeading = csvHeading + "|SP";
		csvHeading = csvHeading + "|SP";
		csvHeading = csvHeading + "|SP";
		csvHeading = csvHeading + "|SP";
		csvHeading = csvHeading + "|SP"; // Added for language?
		
		for (int i=0; i<maxLanguages; i++) {
			csvHeading = csvHeading + "|Recording";			// Language
		}
		
		// Notes section
		for (int i=0; i<maxProductNotes; i++) {
			csvHeading = csvHeading + "|Product";
		}
		for (int i=0; i<maxAuthorNotes; i++) {
			csvHeading = csvHeading + "|Recording";		// Name
			csvHeading = csvHeading + "|Recording";		// Life Dates
			csvHeading = csvHeading + "|Recording";		// Function
			csvHeading = csvHeading + "|Recording";		// Role
		}
		for (int i=0; i<maxContentNotes; i++) {
			csvHeading = csvHeading + "|Product";
		}
		for (int i=0; i<maxItemNotes; i++) {
			csvHeading = csvHeading + "|Recording";
		}
		for (int i=0; i<maxPerformanceNotes; i++) {
			csvHeading = csvHeading + "|Recording";
		}
		for (int i=0; i<maxRecordingNotes; i++) {
			csvHeading = csvHeading + "|Recording";
		}

		for (int i=0; i<maxHubAuthorNotes; i++) {
			csvHeading = csvHeading + "|Recording";		// Hub authors notes
		}

		// Sensitive content
		csvHeading = csvHeading + "|Product";
		csvHeading = csvHeading + "|Recording";
		csvHeading = csvHeading + "|SP";
		csvHeading = csvHeading + "|SP";
		csvHeading = csvHeading + "|SP";
		csvHeading = csvHeading + "|SP";
		
		// Rights
		csvHeading = csvHeading + "|Product [SP]";
		csvHeading = csvHeading + "|Recording [SP]";
		csvHeading = csvHeading + "|Recording [SP]";
		csvHeading = csvHeading + "|Recording [SP]";
		
		//Commercial Music Tracks
		for (int i=0; i<maxOriginalPublicationDates; i++) {
			csvHeading = csvHeading + "|Product";			
		}
		for (int i=0; i<maxISRCCodes; i++) {
			csvHeading = csvHeading + "|Recording";			
		}
		for (int i=0; i<maxParentRecordCompanyNames; i++) {
			csvHeading = csvHeading + "|Product";			
		}
		for (int i=0; i<maxRecordLabelCatalogueNumbers; i++) {
			csvHeading = csvHeading + "|Product";			
		}
		for (int i=0; i<maxLabelMatchs; i++) {
			csvHeading = csvHeading + "|Product";			
		}
		for (int i=0; i<maxPressingCodes; i++) {
			csvHeading = csvHeading + "|Product";			
		}

		for (int i=0; i<maxProductCopyrightOwners; i++) {
			csvHeading = csvHeading + "|Product";			
		}
		
		for (int i=0; i<maxReleaseYears; i++) {
			csvHeading = csvHeading + "|Product";			
		}
		
		for (int i=0; i<maxOriginalIssueNumbers; i++) {
			csvHeading = csvHeading + "|Recording";			
		}
		
		// Location
		csvHeading = csvHeading + "|Recording";			
		csvHeading = csvHeading + "|Recording";			
		csvHeading = csvHeading + "|Recording";			
		csvHeading = csvHeading + "|Recording";	
		csvHeading = csvHeading + "\n";
		
		// Field names - new row in the spreadhseet
		
		csvHeading = csvHeading + "Collection Title";
		csvHeading = csvHeading + "|Call Number";
		csvHeading = csvHeading + "|Call Number";
		csvHeading = csvHeading + "|C-KEY";
		csvHeading = csvHeading + "|C-KEY";
		for (int i=0; i<maxProductShelfmarks; i++) {
			csvHeading = csvHeading + "|Shelfmark";		// Product Shelfmarks
		}
		for (int i=0; i<maxRecordingShelfmarks; i++) {
			csvHeading = csvHeading + "|Shelfmark";		// Recording Shelfmarks
		}

/*		csvHeading = csvHeading + "|Shelfmark";
		csvHeading = csvHeading + "|Shelfmark";
*/		csvHeading = csvHeading + "|MD-ARK";
		csvHeading = csvHeading + "|L-ARK";
		csvHeading = csvHeading + "|L-ARK";
		csvHeading = csvHeading + "|Previous reference number";
		csvHeading = csvHeading + "|Previous reference number";
		csvHeading = csvHeading + "|Product Title";
		csvHeading = csvHeading + "|Item Title";
		csvHeading = csvHeading + "|Original Species";
		csvHeading = csvHeading + "|Species Name";
		csvHeading = csvHeading + "|Sub Species Name";		
		csvHeading = csvHeading + "|Sound Recording Date";
		csvHeading = csvHeading + "|Duration of Recoprding";
		csvHeading = csvHeading + "|[duration]";
		csvHeading = csvHeading + "|T/Code in";
		csvHeading = csvHeading + "|T/Code out";
		for (int i=0; i<=maxRecordingCopyrightOwners; i++) {
			csvHeading = csvHeading + "|Sound Recording Copyright Owner";
		}
		
		// Broadcast type rights
		csvHeading = csvHeading + "|Programme Title";
		csvHeading = csvHeading + "|Programme Title Episode";
		csvHeading = csvHeading + "|Programme Title Modifier";
		csvHeading = csvHeading + "|Broadcaster";
		csvHeading = csvHeading + "|Broadcast Date";
		csvHeading = csvHeading + "|Date Broadcast Rights Expire";
		csvHeading = csvHeading + "|Live/Pre-recorded";
		csvHeading = csvHeading + "|Off-Air";
		
		// Contributor section
		for (int i=0; i<maxContributors; i++) {
			csvHeading = csvHeading + "|Contributor " + (i+1) + " - Name";			
			csvHeading = csvHeading + "|Contributor " + (i+1) + " - Life Dates";			
			csvHeading = csvHeading + "|Contributor " + (i+1) + " - Function";			
			csvHeading = csvHeading + "|Contributor " + (i+1) + " - Role";			
			csvHeading = csvHeading + "|Contributor " + (i+1) + " - Nationality";			
		}
		
		csvHeading = csvHeading + "|Right Type";
		csvHeading = csvHeading + "|Published (orig) / Made Available / C2P";
		csvHeading = csvHeading + "|Date Published (orig) / Made Available / C2P";
		csvHeading = csvHeading + "|Expires";
		
		
		for (int i=0; i<maxRecordists; i++) {
			csvHeading = csvHeading + "|Sound Recordist Name ";
			csvHeading = csvHeading + "|Sound Recordist Life Dates ";
		}
		
		csvHeading = csvHeading + "|Right Type [SoundRecording]";
		csvHeading = csvHeading + "|Published (orig) / Made Available / C2P";
		csvHeading = csvHeading + "|Date Published (orig) / Made Available / C2P";
		csvHeading = csvHeading + "|Expires";

		
		csvHeading = csvHeading + "|Linking Work Title";
		csvHeading = csvHeading + "|Joint Work";
		csvHeading = csvHeading + "|Live Performance";
		csvHeading = csvHeading + "|Improvised";
		csvHeading = csvHeading + "|Published (orig) / Made Available / C2P";
		csvHeading = csvHeading + "|Author/Composer Name (1) etc";
		csvHeading = csvHeading + "|Life Dates";
		csvHeading = csvHeading + "|Role (eg composer; arranger; librettist";	
		csvHeading = csvHeading + "|Joint Work";
		csvHeading = csvHeading + "|Expiry Date [For Work)";
		
		for (int i=0; i<maxLanguages; i++) {
			csvHeading = csvHeading + "|Language "+ (i+1);
		}
		
		// Notes section		
		for (int i=0; i<maxProductNotes; i++) {
			csvHeading = csvHeading + "|Product Note " + (i+1);
		}
		for (int i=0; i<maxAuthorNotes; i++) {
			csvHeading = csvHeading + "|Author Note - Contributor Name " + (i+1);
			csvHeading = csvHeading + "|Author Note - Contributor Life Dates " + (i+1);
			csvHeading = csvHeading + "|Author Note - Contributor Function " + (i+1);
			csvHeading = csvHeading + "|Author Note - Contributor Role " + (i+1);
		}
		for (int i=0; i<maxContentNotes; i++) {
			csvHeading = csvHeading + "|Contents Note " + (i+1);
		}
		for (int i=0; i<maxItemNotes; i++) {
			csvHeading = csvHeading + "|Item Note " + (i+1);
		}
		for (int i=0; i<maxPerformanceNotes; i++) {
			csvHeading = csvHeading + "|Performance Note " + (i+1);
		}
		for (int i=0; i<maxRecordingNotes; i++) {
			csvHeading = csvHeading + "|Recording Note " + (i+1);
		}
		
		for (int i=0; i<maxHubAuthorNotes; i++) {
			csvHeading = csvHeading + "|Hub Specific";		// Hub authors notes
		}
		
		//Sensitive section
		csvHeading = csvHeading + "|Access Restrictions";
		csvHeading = csvHeading + "|Access Restrictions";
		csvHeading = csvHeading + "|Reason for AXS Restriction";
		csvHeading = csvHeading + "|Date Access Restriction Ends (Year)";
		csvHeading = csvHeading + "|Timecode In";
		csvHeading = csvHeading + "|Timecode Out";
		
		//Rights
		csvHeading = csvHeading + "|BLUR Term [TBC]";
		csvHeading = csvHeading + "|BLUR Term";
		csvHeading = csvHeading + "|Copyright Credit Line";
		csvHeading = csvHeading + "|Overall Expiry Rights Date";
		
		//Commercial Music Tracks
		for (int i=0; i<maxOriginalPublicationDates; i++) {
			csvHeading = csvHeading + "|Original Publication Date";			
		}
		for (int i=0; i<maxISRCCodes; i++) {
			csvHeading = csvHeading + "|ISRC Code";			
		}
		for (int i=0; i<maxParentRecordCompanyNames; i++) {
			csvHeading = csvHeading + "|Parent Record Company Name";			
		}
		for (int i=0; i<maxRecordLabelCatalogueNumbers; i++) {
			csvHeading = csvHeading + "|Record Label Catalogue Number";			
		}
		for (int i=0; i<maxLabelMatchs; i++) {
			csvHeading = csvHeading + "|Label Match";			
		}
		for (int i=0; i<maxPressingCodes; i++) {
			csvHeading = csvHeading + "|Pressing Code (Country of Publication)";			
		}

		for (int i=0; i<maxProductCopyrightOwners; i++) {
			csvHeading = csvHeading + "|Sound Recording Copyright Owner (published)";			
		}
		
		for (int i=0; i<maxReleaseYears; i++) {
			csvHeading = csvHeading + "|Release year (Commercial)";			
		}
		
		for (int i=0; i<maxOriginalIssueNumbers; i++) {
			csvHeading = csvHeading + "|Original Issue Number";			
		}
		
		// Location
		csvHeading = csvHeading + "|Recording Location [Wildlife = Country only]";			
		csvHeading = csvHeading + "|Recording Location (Region)";			
		csvHeading = csvHeading + "|Recording Location (District)";			
		csvHeading = csvHeading + "|Recording Location (Specific Locality)";			

		return csvHeading + "\n";
		
	}
	
	private static void createOutputFile (String filepath) throws IOException {
		Path outputPath = Paths.get(filepath);
		try (BufferedWriter writer = Files.newBufferedWriter(outputPath, ENCODING)){
			writer.write(createColumnHeaders());
			System.out.println("Number of products is "+products.size());
			for (int i=0; i<products.size(); i++) {
				Product product = products.get(i);
				List<Recording> recordings = product.getRecordings();
				for (int j=0; j<recordings.size(); j++) {
					String csvOutput = product.getCollectionTitle();
					Recording recording = recordings.get(j);
					csvOutput = csvOutput + "|" + product.getCallNumber();
					csvOutput = csvOutput + "|" + recording.getCallNumber();
					csvOutput = csvOutput + "|" + product.getcKey();
					csvOutput = csvOutput + "|" + recording.getcKey();
					for (int k=0; k<maxProductShelfmarks; k++) {
						if (k < product.getShelfmarks().size()) {
							csvOutput = csvOutput + "|" + product.getShelfmarks().get(k);							
						} else {
							csvOutput = csvOutput + "|";							
							
						}
					}
					for (int k=0; k<maxRecordingShelfmarks; k++) {
						if (k < recording.getShelfmarks().size()) {
							csvOutput = csvOutput + "|" + recording.getShelfmarks().get(k);							
						} else {
							csvOutput = csvOutput + "|";														
						}
					}
					csvOutput = csvOutput + "|" + recording.getMdArk();
					csvOutput = csvOutput + "|" + recording.getlArk();
					csvOutput = csvOutput + "|" + product.getlArk();
					csvOutput = csvOutput + "|" + product.getPreviousReferenceNumber();
					csvOutput = csvOutput + "|" + recording.getPreviousReferenceNumber();
					csvOutput = csvOutput + "|" + product.getProductTitle();
					csvOutput = csvOutput + "|" + recording.getItemTitle();
					csvOutput = csvOutput + "|" + recording.getOriginalSpecies();
					csvOutput = csvOutput + "|" + recording.getSpeciesName();
					csvOutput = csvOutput + "|" + recording.getSubSpeciesName();
					csvOutput = csvOutput + "|" + recording.getRecordingDate();
					csvOutput = csvOutput + "|" + recording.getRecordingDuration();
					csvOutput = csvOutput + "|||";
					for (int k=0; k<=maxRecordingCopyrightOwners; k++) {
						if (k < recording.getCopyrightOwners().size()) {
							csvOutput = csvOutput + "|" + recording.getCopyrightOwners().get(k);
						} else {
							csvOutput = csvOutput + "|";							
						}
					}
//					csvOutput = csvOutput + "|" + recording.getRecordingLocation();
					
					
					// Broadcast Type rights
					csvOutput = csvOutput + "|" + recording.getProgrammeTitle();
					csvOutput = csvOutput + "|" + recording.getProgrammeTitleEpisode();
					csvOutput = csvOutput + "|" + recording.getProgrammeTitleModifier();
					csvOutput = csvOutput + "|" + recording.getBroadcaster();
					csvOutput = csvOutput + "|" + recording.getBroadcastDate();
					csvOutput = csvOutput + "|||";
					
					// Contributors
					for (int k=0; k<maxContributors; k++) {
						if (k < recording.getContributors().size()) {
							Contributor contributor = recording.getContributors().get(k);
							csvOutput = csvOutput + "|" + contributor.getName();
							csvOutput = csvOutput + "|" + contributor.getLifeDates();
							csvOutput = csvOutput + "|" + contributor.getFunction();
							csvOutput = csvOutput + "|" + contributor.getRole();
							csvOutput = csvOutput + "|"; // nationality
						} else {
							csvOutput = csvOutput + "|||||";						
						}
					}				
					csvOutput = csvOutput + "||||";
					
					for (int k=0; k<maxRecordists; k++) {
						if (k < recording.getRecordists().size()) {
							csvOutput = csvOutput + "|" + recording.getRecordists().get(k).getName();
							csvOutput = csvOutput + "|" + recording.getRecordists().get(k).getLifeDates();
						} else {
							csvOutput = csvOutput + "||";
							
						}
					}
					
					csvOutput = csvOutput + "||||";
					
					// Works				
					for (int k=0; k<maxLinkingWorkTitles; k++) {
						if (k < recording.getLinkingWorkTitles().size()) {
							csvOutput = csvOutput + "|" + recording.getLinkingWorkTitles().get(k);
						} else {
							csvOutput = csvOutput + "|";
							
						}
					}
					csvOutput = csvOutput + "|||||||||";
					
					for (int k=0; k<maxLanguages; k++) {
						if (k < recording.getLanguages().size()) {
							csvOutput = csvOutput + "|" + recording.getLanguages().get(k);
						} else {
							csvOutput = csvOutput + "|";
							
						}
					}
//					csvOutput = csvOutput + "|" + recording.getLanguage();
					
					// Notes
					for (int k=0; k<maxProductNotes; k++) {
						if (k < product.getProductNotes().size()) {
							csvOutput = csvOutput + "|" + product.getProductNotes().get(k).getNote();
						} else {
							csvOutput = csvOutput + "|";
							
						}
					}
					
					for (int k=0; k<maxAuthorNotes; k++) {
						if (k < recording.getAuthorNotes().size()) {
							Contributor authorComposer = recording.getContributors().get(k);
							csvOutput = csvOutput + "|" + authorComposer.getName();
							csvOutput = csvOutput + "|" + authorComposer.getLifeDates();
							csvOutput = csvOutput + "|" + authorComposer.getFunction();
							csvOutput = csvOutput + "|" + authorComposer.getRole();
						} else {
							csvOutput = csvOutput + "||||";						
							
						}
					}
					
					for (int k=0; k<maxContentNotes; k++) {
						if (k < product.getContentNotes().size()) {
							csvOutput = csvOutput + "|" + product.getContentNotes().get(k).getNote();
						} else {
							csvOutput = csvOutput + "|";
							
						}
					}
					
					for (int k=0; k<maxItemNotes; k++) {
						if (k < recording.getItemNotes().size()) {
							csvOutput = csvOutput + "|" + recording.getItemNotes().get(k).getNote();
						} else {
							csvOutput = csvOutput + "|";
							
						}
					}
	
					for (int k=0; k<maxPerformanceNotes; k++) {
						if (k < recording.getPerformanceNotes().size()) {
							csvOutput = csvOutput + "|" + recording.getPerformanceNotes().get(k).getNote();
						} else {
							csvOutput = csvOutput + "|";
							
						}
					}
	
					for (int k=0; k<maxRecordingNotes; k++) {
						if (k < recording.getRecordingNotes().size()) {
							csvOutput = csvOutput + "|" + recording.getRecordingNotes().get(k).getNote();
						} else {
							csvOutput = csvOutput + "|";
							
						}
					}
					
					for (int k=0; k<maxHubAuthorNotes; k++) {
						if (k < recording.getHubAuthors().size()) {
							csvOutput = csvOutput + "|" + recording.getHubAuthors().get(k);
						} else {
							csvOutput = csvOutput + "|";
							
						}
					}

	
					// Sensitive section
					csvOutput = csvOutput + "|" + product.getAccessRestrictions();
					csvOutput = csvOutput + "|" + recording.getAccessRestrictions();
					csvOutput = csvOutput + "||||";
					
					// Rights
					csvOutput = csvOutput + "||||";
					
					//Commercial Music Tracks
					for (int k=0; k<maxOriginalPublicationDates; k++) {
						if (k < product.getOriginalPublicationDates().size()) {
							csvOutput = csvOutput + "|" + product.getOriginalPublicationDates().get(k);
						} else {
							csvOutput = csvOutput + "|";
							
						}
					}
					for (int k=0; k<maxISRCCodes;k++) {
						if (k < recording.getISRCCodes().size()) {
							csvOutput = csvOutput + "|" + recording.getISRCCodes().get(k);
						} else {
							csvOutput = csvOutput + "|";
							
						}
					}
					for (int k=0; k<maxParentRecordCompanyNames; k++) {
						if (k < product.getParentRecordCompanyNames().size()) {
							csvOutput = csvOutput + "|" + product.getParentRecordCompanyNames().get(k);
						} else {
							csvOutput = csvOutput + "|";
							
						}
					}
					
					for (int k=0; k<maxRecordLabelCatalogueNumbers; k++) {
						if (k < product.getRecordLabelCatalogueNumbers().size()) {
							csvOutput = csvOutput + "|" + product.getRecordLabelCatalogueNumbers().get(k);
						} else {
							csvOutput = csvOutput + "|";
							
						}
					}
					for (int k=0; k<maxLabelMatchs; k++) {
						if (k < product.getLabelMatchs().size()) {
							csvOutput = csvOutput + "|" + product.getLabelMatchs().get(k);
						} else {
							csvOutput = csvOutput + "|";
							
						}
					}
					for (int k=0; k<maxPressingCodes; k++) {
						if (k < product.getPressingCodes().size()) {
							csvOutput = csvOutput + "|" + product.getPressingCodes().get(k);
						} else {
							csvOutput = csvOutput + "|";
							
						}
					}

					for (int k=0; k<maxProductCopyrightOwners; k++) {
						if (k < product.getCopyrightOwners().size()) {
							csvOutput = csvOutput + "|" + product.getCopyrightOwners().get(k);
						} else {
							csvOutput = csvOutput + "|";							
						}
					}
					
					csvOutput = csvOutput + "|" + product.getReleaseYear () ;
					csvOutput = csvOutput + "|" + recording.getOriginalIssueNumber();
					
					// Location
					csvOutput = csvOutput + "|" + recording.getRecordingLocation();
					csvOutput = csvOutput + "|" + recording.getRecordingLocationRegion();
					csvOutput = csvOutput + "|" + recording.getRecordingLocationDistrict();
					csvOutput = csvOutput + "|" + recording.getRecordingLocationSpecificLocality();
					
					csvOutput = csvOutput + "\n";
	
					writer.write(csvOutput);					
				}
			}
			writer.close();
		}
	}
	
	private static boolean checkConsistency () {
		boolean consistent = true;
		
		// Check that Product record has Recording record(s) attached to it
		Iterator<String> iter = productToRecordings.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			if (productToRecordings.get(key).isEmpty()) {
				System.err.println("Product recording "+key+" has no recordings attached to it");
			}
		}
		
		// Check that Recording record has associated Product record attached to it
		
		return consistent;
	}
	
	public static void main(String[] args) throws IOException {
		String fileDirectory = args[0];
		String outputFile = args[1];
		System.out.println ("Reading directory "+fileDirectory);
		System.out.println ("Writing to file "+outputFile);
		
				
		try(Stream<Path> paths = Files.walk(Paths.get(fileDirectory))) {
		    paths.forEach(filePath -> {
		        if (Files.isRegularFile(filePath)) {
		        	System.out.println("File name is "+filePath.toString());
		            processFile (filePath);
		    		System.out.println("Maximum number of product notes is "+maxProductNotes);
		    		System.out.println("Maximum number of items notes is "+maxItemNotes);
		    		System.out.println("Maximum number of contributors is "+maxContributors);
		    		System.out.println("Maximum number of product copyright owners is "+maxProductCopyrightOwners);
		    		System.out.println("Maximum number of recording copyright owners is "+maxRecordingCopyrightOwners);
		    		
		    		
		        }
		    });
		}  catch (IOException e) {
			e.printStackTrace();
		}
		
		if (checkConsistency ()) {		
			createOutputFile (outputFile);
		}

		

	}

}
