package com.txfashion.order;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//import sun.awt.image.BadDepthException;

public class ExportOrder {

	private static final String FILE_TEMPLATE = "order_template.xlsx";
	public static final String ORDER_FOLDER = "order_files";
	public static int hoodieCount = 0;
	public static int longSleeveCount = 0;
	public static int tshirtCount = 0;
	public static int zipHoodieCount = 0;
	public static int shoesCount = 0;
	public static int dressCount = 0;
	public static int ssCount = 0;
	public static int AutossCount = 0;// Ma cua HPM
	public static int bomberJacketCount = 0;
	public static int tumblerCount = 0;
	public static int baseballJerseyCount = 0;
	public static int maskCount = 0;
	public static int mask1M3FCount = 0;
	public static int mask3M9FCount = 0;
	public static int mask5M15FCount = 0;
	public static int mask10M30FCount = 0;
	public static int totalItems = 0;
	public static int sportMaskCount 	= 0;
	public static int sportMask1M2F 	= 0;
	public static int sportMask3M6F 	= 0;
	public static int sportMask5M10F 	= 0;
	public static int sportMask10M20F 	= 0;
	//public static int puzzle500Count	= 0;
	//public static int puzzle1000Count	= 0;
	//public static int puzzleCount		= 0;
	public static int rugCount			= 0;
	public static int rugSmallCount		= 0;
	public static int rugMediumCount	= 0;
	public static int rugLagreCount		= 0;
	public static int blanketCount 	= 0;
	public static int blanketKingCount 	= 0;
	public static int blanketQueenCount = 0;
	public static int blanketTwinCount 	= 0;
	public static int beddingSetCount 		= 0;
	public static int beddingSetTwinCount		= 0;
	public static int beddingSetFullCount		= 0;
	public static int beddingSetQueenCount		= 0;
	public static int beddingSetKingCount		= 0;
	public static int woolsweaterCount  = 0;//d
	public static int hoodiemaskCount = 0;//d
	public static int capCount = 0; //d
	public static int SneakersCount = 0;
	public static int kidhoodieCount = 0;
	public static int jordan11Count = 0;
	public static int poloCount = 0;
	
	public static double OrderAmount = 0;
	
	public static double hoodiePrice 			= 25;
	public static double longSleevePrice 		= 21;
	public static double tshirtPrice 			= 15;
	public static double zipHoodiePrice 		= 27;
	public static double shoesPrice 			= 28;
	public static double dressPrice 			= 15;
	public static double bomberJacketPrice 		= 33;
	public static double tumblerPrice 			= 17;
	public static double baseballJerseyPrice 	= 23;
	public static double mask1M3FPrice 			= 8.5;
	public static double mask3M9FPrice 			= 12;
	public static double mask5M15Price 			= 17;
	public static double mask10M30Price 		= 28.5;
	//public static double puzzle500Price 		= 18;
	//public static double puzzle1000Price		= 26;
	public static double woolsweaterPrice  = 10;//d
	public static double hoodiemaskPrice = 11;//d
	public static double capPrice = 11;
	public static double SneakersPrice = 11;
	public static double kidhoodiePrice = 10;
	public static double jordan11Price = 10;
	
	public static double poloPrice = 10;
	
	public static String HOODIE = "Hoodie";
	public static String LONG_SLEEVE = "Sweatshirt";
	public static String T_SHIRT = "T-shirt";
	public static String SHOES = "AJ13W"; //d
	public static String ZIP_HOODIE = "Zip Hoodie";
	public static String DRESS = "Dress";
	public static String BLANKET = "Blanket";
	public static String BOMBER_JACKET = "Bomber Jacket";
	public static String SUNSHADE = "AUTO SUNSHARE";
	public static String AUTOSHUNSHADE = "AUTO SUNSHARE"; // Ma cua HPM
	public static String TUMBLER = "Tumbler";
	public static String BASEBALL_JERSEY = "Baseball Jersey";
	public static String MASK = "Mask";
	public static String SPORT_MASK = "Sport Mask";
	//public static String PUZZLE = "Puzzle";
	public static String RUG	= "Rug";
	public static String BENDDING_SET	= "Bedding Set";
	public static String WOOl_SWEATER = "Wool Sweater"; //d
	public static String HOODIE_MASK = "Hoodie Mask"; //d
	public static String Cap = "HCC";
	public static String SNEAKERS = "SFCS/W/Shoes";
	public static String Kid_Hoodie = "Kid Hoodie";
	//public static String Tank_Top = "Tank_Top";
	public static String Jordan11 = "AJ11/W";
	public static String  Polo = "Polo";
	
	public static String ORDER_CODE = "516";
	
	public static void main(String[] args) throws IOException, EncryptedDocumentException, InvalidFormatException {
		String outputFile = "LT" + ORDER_CODE + ".xlsx";
		writeOrderFile(readOrderFolder(ORDER_CODE), outputFile);	
		System.out.println("GENERATE " + outputFile + " SUCCESSFULLY");
		System.out.println("HOODIE: " + hoodieCount 
				+ ", ZIP HOODIE: " + zipHoodieCount 
				+ ", SWEATSHIRT: " + longSleeveCount 
				+ ", T-SHIRT: " + tshirtCount 
				+ ", Baseball Jersey: " + baseballJerseyCount 
				+ ", SHOES: " + shoesCount 
				+ ", Sneakers: " +  SneakersCount 
				+ ", Polo: " + poloCount
				+ ", Cap: " + capCount //d
				+ ", WOOL SWEATER: " + woolsweaterCount //d 
				+ ", Shoes Jordan 11 :" +jordan11Count
				);
				System.out.println(
				"HOODIE MASK: " + hoodiemaskCount
				+", Bomber Jacket: " + bomberJacketCount 
				+ ", RUG: " + rugCount  + " (" + "LAGRE: " + rugLagreCount + ", MEDIUM: " + rugMediumCount + ", SMALL: " + rugSmallCount + ")"
				+ ", AUTO SUNSHADE: " + (ssCount+AutossCount)
				+ ", Kid Hoodie: " + kidhoodieCount
				);
				System.out.println(
				"TOTAL MASK: "  + maskCount 
				+ " : NORMAL MASK ( "
				+ "1M3F: " + mask1M3FCount
				+ " ,3M9F: " + mask3M9FCount
				+ " ,5M15F: " + mask5M15FCount
				+ " ,10M30F: " + mask10M30FCount
				+ ")"
				+ "," 
				+ " SPORT MASK ( "
				+ "1M2F: " + sportMask1M2F
				+ " ,3M6F: " + sportMask3M6F
				+ " ,5M10F: " + sportMask5M10F
				+ " ,10M20F: " + sportMask10M20F
				+ ")"
				//+ " , PUZZLE: " + puzzleCount  + " ( " + "500 Pieces: " + puzzle500Count + ", 1000 Pieces: " + puzzle1000Count + " )" 
				+ ", DRESS: " + dressCount 
				+ ", Tumbler: " + tumblerCount
				
				);System.out.println(
				 "QUILT: " + blanketCount  + "(" + "KING: " + blanketKingCount + ", QUEEN: " + blanketQueenCount + ", TWIN: " + blanketTwinCount + ")"
				 + ", BEDDING SET: " + beddingSetCount  + " (" + "KING: " + beddingSetKingCount + ", QUEEN: " + beddingSetQueenCount + ", FULL: " + beddingSetFullCount +", TWIN: " + beddingSetTwinCount + ")"
				);
				
		
		
		double price = (hoodieCount * hoodiePrice) 
				+ (zipHoodieCount * zipHoodiePrice) 
				+ (tshirtCount * tshirtPrice)
				+ (longSleeveCount * longSleevePrice) 
				+ (shoesCount * shoesPrice) 
				+ (dressCount * dressPrice) 
				+ (bomberJacketCount * bomberJacketPrice) 
				+ (tumblerCount * tumblerPrice) 
				+ (baseballJerseyCount * baseballJerseyPrice) 
				+ (mask1M3FCount * mask1M3FPrice) // 1M3F
				+ (mask3M9FCount * mask3M9FPrice) // 3M9F
				+ (mask5M15FCount * mask5M15Price) // 5M15F
				+ (mask10M30FCount * mask10M30Price)  // 10M30F
				+ (hoodiemaskCount * hoodiemaskPrice) //d
				+(capCount*capPrice)//d
				+ (woolsweaterCount * woolsweaterPrice); //d

		
		System.out.println("TOTAL ITEM: " + totalItems);
		System.out.println("PRICE: " + price);

	}

	public static void writeOrderFile(List<List<String>> contens, String outputFile)
			throws EncryptedDocumentException, InvalidFormatException, IOException {

		System.out.println(contens);
		// Obtain a workbook from the excel file
		FileInputStream fileInputStream = new FileInputStream(FILE_TEMPLATE);
		Workbook wb = new XSSFWorkbook(fileInputStream);

		// Get Sheet at index 0
		Sheet sheet = wb.getSheetAt(0);
		int rowCount = sheet.getPhysicalNumberOfRows();
		for (int i = 0; i < contens.size(); i++) {
			Row row = sheet.createRow(rowCount + 1);
			for (int j = 0; j < contens.get(i).size(); j++) {
				row.createCell(j).setCellValue(contens.get(i).get(j));
			}

			rowCount++;
		}

		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(outputFile, true);
			wb.write(fileOut);

			fileOut.close();
			wb.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static List<List<String>> readOrderFolder(String orderNo) throws IOException {
		File folder = new File(ORDER_FOLDER);
		File[] listOfFiles = folder.listFiles();

		List<List<String>> allContents = new ArrayList<List<String>>();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				String fileName = ORDER_FOLDER + "/" + file.getName();
				allContents.addAll(readOrderFile(fileName, orderNo));
			}
		}

		return allContents;

	}

	/**
	 * Calculate Product type
	 * 
	 * @param variantTitle
	 * @param quality
	 */
	public static void calculateProductType(String variantTitle, int quantity) {
		if (variantTitle.contains(HOODIE_MASK)) {
			hoodiemaskCount = hoodiemaskCount + quantity; 
		}else if(variantTitle.contains(Kid_Hoodie)) {
			kidhoodieCount = kidhoodieCount + quantity;
		}
		else if (variantTitle.contains(ZIP_HOODIE)) {
			zipHoodieCount = zipHoodieCount + quantity;
		} else if (variantTitle.contains(LONG_SLEEVE)) {
			longSleeveCount = longSleeveCount + quantity;
		} else if (variantTitle.contains(T_SHIRT)) {
			tshirtCount = tshirtCount + quantity;
		} else if (variantTitle.contains(HOODIE)) {
			hoodieCount = hoodieCount + quantity;
		} else if (variantTitle.contains(SHOES)) {
			shoesCount = shoesCount + quantity;
		}else if (variantTitle.contains(SNEAKERS)) {
			SneakersCount = SneakersCount + quantity;
		}else if (variantTitle.contains(DRESS)) {
			dressCount = dressCount + quantity;
		} else if (variantTitle.contains("Quilt")) {
			blanketCount = blanketCount + quantity;
		} else if (variantTitle.contains(SUNSHADE)) {
			ssCount = ssCount + quantity;
		}else if (variantTitle.contains(AUTOSHUNSHADE)) {
			AutossCount = AutossCount + quantity;
		}
		else if (variantTitle.contains(BOMBER_JACKET)) {
			bomberJacketCount = bomberJacketCount + quantity;
		} else if (variantTitle.contains(TUMBLER)) {
			tumblerCount = tumblerCount + quantity;
		} else if (variantTitle.contains(BASEBALL_JERSEY)) {
			baseballJerseyCount = baseballJerseyCount + quantity;
		} else if (variantTitle.contains(MASK)) {
			maskCount = maskCount + quantity;
		} /*else if (variantTitle.contains(PUZZLE)) {
			puzzleCount = puzzleCount + quantity;
		} */else if (variantTitle.contains(RUG)) {
			rugCount = rugCount + quantity;
		} else if (variantTitle.contains(BENDDING_SET)) {
			beddingSetCount = beddingSetCount + quantity;
		} else if (variantTitle.contains(WOOl_SWEATER)) {
			woolsweaterCount = woolsweaterCount +quantity; 
		}else if(variantTitle.contains(Cap)){
			capCount = capCount +quantity;
		}//d
		else if (variantTitle.contains(Polo)) {
			poloCount = poloCount +quantity;
		}else if (variantTitle.contains(Jordan11)) {
			jordan11Count = jordan11Count +quantity; 
		}
			
		
		totalItems = totalItems + quantity;
	}
	


	/**
	 * Read order file
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static List<List<String>> readOrderFile(String fileName, String orderNo) throws IOException {
		Charset charset = Charset.forName("UTF-8");
		Reader reader = Files.newBufferedReader(Paths.get(fileName), charset);
		CSVParser csvParser = new CSVParser(reader,
				CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

		List<List<String>> contents = new ArrayList<List<String>>();
		for (CSVRecord csvRecord : csvParser) {
			if ("Processing".equalsIgnoreCase(csvRecord.get("Order Status")) || "Completed".equalsIgnoreCase(csvRecord.get("Order Status")) || "On Hold".equalsIgnoreCase(csvRecord.get("Order Status")) ) {
				
		
				List<String> rowContent = new ArrayList<String>();
				// Accessing values by Header names
				rowContent.add(orderNo + "LT" + csvRecord.get("Order Number"));
				String sku = csvRecord.get("SKU");
				String temp[] = sku.split("-");
				String designCode = temp[0];
				System.out.println(csvRecord.get("Order Number"));
				String prodyctType = getProductType(temp[1]);
				String productSize = "";
				// get size for SHOES
				if (MASK.equals(prodyctType)) {
					// get size
					String productSizeCode = temp[1].replace("MK", "");
					productSize = getMaskCombo(productSizeCode, Integer.parseInt(csvRecord.get("Quantity")));
					
				} else if (BENDDING_SET.equals(prodyctType)) {
					// get size
					productSize = getBeddingSetSize(temp[2], Integer.parseInt(csvRecord.get("Quantity")));
					
				} else if (RUG.equals(prodyctType)) {
					// get size
					productSize = getRugSize(temp[2], Integer.parseInt(csvRecord.get("Quantity")));
					
				} 
				/* else if (PUZZLE.equals(prodyctType)) {
					// get size
					String productSizeCode = temp[1].replace("P", "");
					productSize = getPuzzleType(productSizeCode, Integer.parseInt(csvRecord.get("Quantity")));
					
				}*/ else if (SPORT_MASK.equals(prodyctType)) { // Sport MASK
					// get size
					String productSizeCode = temp[1].replace("AC", "");
					productSize = getSportMaskCombo(productSizeCode, Integer.parseInt(csvRecord.get("Quantity")));
					
				} else if (SHOES.equals(prodyctType) || DRESS.equals(prodyctType)) {
					// get size - Type: Shoes | Size: MEN US9-EU42 | _WCPA_order_meta_data:
					String productVariant = csvRecord.get("Product Variation");
					productVariant = productVariant.replace("|", "-");
					productSize = productVariant.split("- ")[1].replaceAll("Size:", "").trim().replaceAll(" US[0-9][0-9]-","/").replaceAll(" US[0-9]-","/").trim();
					
				}else if (SNEAKERS.equals(prodyctType) || DRESS.equals(prodyctType)) {
					// get size - Type: Shoes | Size: MEN US9-EU42 | _WCPA_order_meta_data:
					String productVariant = csvRecord.get("Product Variation");
					productVariant = productVariant.replace("|", "-");
					productSize = productVariant.split("- ")[1].replaceAll("Size:", "").trim().replaceAll(" US[0-9][0-9]-","/").replaceAll(" US[0-9]-","/").trim();
					
				}
				else if (Jordan11.equals(prodyctType)) {
					// get size - Type: Shoes | Size: MEN US9-EU42 | _WCPA_order_meta_data:
					String productVariant = csvRecord.get("Product Variation");
					productVariant = productVariant.replace("|", "-");
					productSize = productVariant.split("- ")[1].replaceAll("Size:", "").trim().replaceAll(" US[0-9][0-9]-","/").replaceAll(" US[0-9]-","/").trim();
					
				}
				else if (WOOl_SWEATER.equals(prodyctType)) {
					String productVariant = csvRecord.get("Product Variation");
					productVariant = productVariant.replace("|", "-");
					productSize = productVariant.split("-")[1].replace("Size:", "").trim();
				}else if (HOODIE_MASK.equals(prodyctType)) {
					String productVariant = csvRecord.get("Product Variation");
					productVariant = productVariant.replace("|", "-");
					productSize = productVariant.split("-")[0].replace("US Size:", "").trim();
				}
				else if (Cap.equals(prodyctType)) {
					String productVariant = csvRecord.get("Product Variation");
					productVariant = productVariant.replace("|", "-");
					productSize = productVariant.split("-")[0].replace("size:", "").trim();
					}
				else if  (SUNSHADE.equals(prodyctType)) {
					// get size
					productSize = "UF";
					
				} else  if (BLANKET.equals(prodyctType)) {
					
					productSize = getBlanketSize(temp[2], Integer.parseInt(csvRecord.get("Quantity")));
					
					prodyctType = "Quilt";
				} else {
					productSize = temp[2];
				}
				
				
				String variantTitle = prodyctType + " / " + productSize;
				rowContent.add(designCode);
				rowContent.add(variantTitle); // attribue
				rowContent.add(csvRecord.get("Quantity"));
				rowContent.add("");
				rowContent.add(""); // empty data
				
				String address 	= csvRecord.get("Address 1&2 (Shipping)");
				String city 	= csvRecord.get("City (Shipping)");
				String stateCode= csvRecord.get("State Code (Shipping)");
				String zipCode 	= csvRecord.get("Postcode (Shipping)");
				String countryCode 	= csvRecord.get("Country Code (Shipping)");
				
				rowContent.add(csvRecord.get("First Name (Shipping)") + " " + csvRecord.get("Last Name (Shipping)")); // Name
				rowContent.add(address);
				rowContent.add(""); // Address 2
				rowContent.add(city);
	
				if (stateCode == null || stateCode.equals("")) {
					stateCode = countryCode;
				}
	
				rowContent.add(stateCode);
				rowContent.add(countryCode);
				rowContent.add(zipCode); // Zip code
				rowContent.add(""); // Post Image
				rowContent.add(""); // Something
				rowContent.add(csvRecord.get("Phone (Billing)"));
				rowContent.add(csvRecord.get("Email (Billing)"));
				rowContent.add(""); // Something
				rowContent.add(csvRecord.get("Image URL"));
				
				String productvari = csvRecord.get("Product Variation").replace("|", "//");
				if (productvari.contains("Your Name")) {
					String[] tp = productvari.split("//"); 
					rowContent.add(tp[tp.length - 2] + " | " + tp[tp.length - 1]);
				} 
				else if (productvari.contains("Any Name")) {
					String[] tp = productvari.split("//"); 
					rowContent.add(tp[tp.length - 2] + " | " + tp[tp.length - 1]);
				} 
				else if (productvari.contains("Uploaded Media"))  {
					String[] tp = productvari.split("//");
					rowContent.add("Uploaded Media : https://"+tp[tp.length - 2]);//d
				}else {
					rowContent.add("");
				}
				
				String amout = csvRecord.get("Order Total Amount");
				 double amoutcv = Double.parseDouble(amout);
					if(amoutcv >150) {
						rowContent.add(orderNo + "LT" + csvRecord.get("Order Number") + "- Ship DHL-Ecom");
					}else {
						rowContent.add("");
					}
				
				
				// Calculate product type
				calculateProductType(variantTitle, Integer.parseInt(csvRecord.get("Quantity")));
	
				contents.add(rowContent);
			}
		}

		csvParser.close();

		return contents;

	}
	
	public static String getProductType(String productTypeCode) {
		if ("LMS".equals(productTypeCode)) {
			return HOODIE; 
		} else if ("ZIP".equals(productTypeCode)) {
			return ZIP_HOODIE;
		} else if ("WY".equals(productTypeCode)) {
			return LONG_SLEEVE;
		} else if ("TX".equals(productTypeCode)) {
			return T_SHIRT;
		} else if ("SH".equals(productTypeCode)) {
			return SHOES;
		} else if ("SFCS".equals(productTypeCode)) {
			return SNEAKERS;
		} else if ("DR".equals(productTypeCode)) {
			return DRESS;
		} else if ("BLANKET".equals(productTypeCode)) {
			return BLANKET;
		} else if ("SUNSHADE".equals(productTypeCode)) {
			return SUNSHADE;
		}else if ("AUTOSHUNSHADE".equals(productTypeCode)) {
			return AUTOSHUNSHADE;
		}// Ma cua HPM
		else if ("BJ".equals(productTypeCode)) {
			return BOMBER_JACKET;
		} else if ("TB".equals(productTypeCode)) {
			return TUMBLER;
		} else if ("JR".equals(productTypeCode)) {
			return BASEBALL_JERSEY;
		} else if (productTypeCode.contains("MK")) {
			return MASK;
		} else if (productTypeCode.contains("AC")) {
			return SPORT_MASK;
		} /*else if (productTypeCode.contains("P")) {
			return PUZZLE;
		} */else if (productTypeCode.contains("RUG")) {
			return RUG;
		} else if ("BDS".equals(productTypeCode)) {
			return BENDDING_SET;
		} else if ("WS".equals(productTypeCode)) {
			return WOOl_SWEATER; //d
		}else if ("HOODIEMASK".equals(productTypeCode)) {
			return HOODIE_MASK; //d
		}else if("CAP".equals(productTypeCode)) {
			return Cap;
		}else if("KH".equals(productTypeCode)) {
			return Kid_Hoodie;
		}else if ("POLO".equals(productTypeCode)) {
			return Polo;
		}else if ("SAJ11".equals(productTypeCode)){
			return Jordan11 ;
		}
		//d
		
		return "";
	}
	
	
	
	public static String getMaskCombo(String code, int quantity) {
		String maskCombo = "";
		if (code.contains("1M3F")) {
			maskCombo = "1 MASK & 3 FILTERS";
			mask1M3FCount = mask1M3FCount + quantity; 
		} else if (code.contains("3M9F")) {
			maskCombo = "3 MASK & 9 FILTERS";
			mask3M9FCount = mask3M9FCount + quantity; // 3M 9F
		} else if (code.contains("5M15F")) {
			maskCombo = "5 MASK & 15 FILTERS";
			mask5M15FCount = mask5M15FCount + quantity; // 5M 15 F
		} else  if (code.contains("10M30F")) {
			maskCombo = "10 MASK & 30 FILTERS";
			mask10M30FCount = mask10M30FCount + quantity; // 10M * 30F
		}
		
		return maskCombo;
	}
	
	
	/*public static String getPuzzleType(String code, int quantity) {
		String type = "";
		if (code.contains("500")) {
			type = "500 pieces";
			puzzle500Count = puzzle500Count + quantity;
		} else if (code.contains("1000")) {
			type = "1000 pieces";
			puzzle1000Count = puzzle1000Count + quantity;
		} 
		
		return type;
	}*/
	
	public static String getRugSize(String code, int quantity) {
		String size = "";
		if (code.equalsIgnoreCase("SMALL")) {
			size = "150x90cm";
			rugSmallCount = rugSmallCount + quantity;
		} else if (code.equalsIgnoreCase("MEDIUM")) {
			size = "180x120cm";
			rugMediumCount = rugMediumCount + quantity;
		} else if (code.equalsIgnoreCase("LARGE") ||  code.equalsIgnoreCase("LAGRE")) {
			size = "240x150cm";
			rugLagreCount = rugLagreCount + quantity;
		}
		
		return size;
	}
	
	public static String getSportMaskCombo(String code, int quantity) {
		String maskCombo = "";
		if (code.contains("1M2F")) {
			maskCombo = "1 MASK & 2 FILTERS";
			sportMask1M2F = sportMask1M2F + quantity; 
		} else if (code.contains("3M6F")) {
			maskCombo = "3 MASK & 6 FILTERS";
			sportMask3M6F = sportMask3M6F + quantity; // 3M 9F
		} else if (code.contains("5M10F")) {
			maskCombo = "5 MASK & 10 FILTERS";
			sportMask5M10F = sportMask5M10F + quantity; // 5M 15 F
		} else  if (code.contains("10M20F")) {
			maskCombo = "10 MASK & 20 FILTERS";
			sportMask10M20F = sportMask10M20F + quantity; // 10M * 20F
		}
		
		return maskCombo;
	}
	
	
	public static String getBlanketSize(String type, int quantity) {
		String size = "";
		
		if ("TWIN".equalsIgnoreCase(type)) {
			size =  "150 x 180cm";
			blanketTwinCount = blanketTwinCount + quantity;
		} else if ("QUEEN".equalsIgnoreCase(type)) {
			size =  "178 x 203cm";
			blanketQueenCount = blanketQueenCount + quantity;
		} else if ("KING".equalsIgnoreCase(type)) {
			size =  "203 x 228cm";
			blanketKingCount = blanketKingCount + quantity;
		}
		return size;
	}
	
	public static String getBeddingSetSize(String type, int quantity) {
		if ("TWIN".equalsIgnoreCase(type)) {
			type ="173x218cm 3pcs";
			beddingSetTwinCount = beddingSetTwinCount + quantity;
		} else if ("QUEEN".equalsIgnoreCase(type)) {
			type = "228x228cm 3pcs";
			beddingSetQueenCount = beddingSetQueenCount + quantity;
		} else if ("KING".equalsIgnoreCase(type)) {
			type ="228x264cm 3pcs";
			beddingSetKingCount = beddingSetKingCount + quantity;
		} else if ("FULL".equalsIgnoreCase(type)) {
			type ="203x228cm 3pcs";
			beddingSetFullCount = beddingSetFullCount + quantity;
		}
		
		return type;
	}

}
