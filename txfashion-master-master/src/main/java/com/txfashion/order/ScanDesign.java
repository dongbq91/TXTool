package com.txfashion.order;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ScanDesign {
	
	public static final String DESIGN_FILE = "design.txt";
	
	public static void main(String[] args) throws IOException {
		List<List<String>> contents = ExportOrder.readOrderFolder("/Users/langthuan/Google Drive/TX-Fashion/Product/2020 Mar/10"); // We don't need args here but we ultilize from other class.
		
		List<String> missingDesigns = findMissingDesign(contents);
		
		FileWriter fw = new FileWriter(DESIGN_FILE, true);
	    BufferedWriter bw = new BufferedWriter(fw);
	   
	    System.out.println("MISSING DESIGN: " + missingDesigns);
	    StringBuilder designCodeString = new StringBuilder();
		for (String desingCode : missingDesigns) {
			designCodeString.append(desingCode);
			designCodeString.append(",");
		}
		
		bw.write(designCodeString.toString());
	    bw.close();
	}
	
	public static List<String> findMissingDesign(List<List<String>> contents) throws IOException {
		List<String> missingDesigns = new ArrayList<String>();
		String uploadedDesign = new String(Files.readAllBytes(Paths.get(DESIGN_FILE)));
		for (int i = 0; i < contents.size(); i++) {
			String designCode = contents.get(i).get(1);
			
			if (!uploadedDesign.contains(designCode) && !missingDesigns.contains(designCode)) {
				missingDesigns.add(designCode);
			}
		}
		
		
		return missingDesigns;
		
	}
	
	

}
