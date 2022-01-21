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

public class SHAOExportOrder {

	private static final String FILE_TEMPLATE = "order_template.xlsx";
	public static final String ORDER_FOLDER = "order_files";
	public static int hoodieCount = 0;
	public static int longSleeveCount = 0;
	public static int tshirtCount = 0;
	public static int zipHoodieCount = 0;
	public static int shoesCount = 0;

	public static double hoodiePrice = 18.49;
	public static double longSleevePrice = 14;
	public static double tshirtPrice = 8;
	public static double zipHoodiePrice = 19.99;
	public static double shoesPrice = 33;

	public static String HOODIE = "Hoodie";
	public static String LONG_SLEEVE = "Sweatshirt";
	public static String T_SHIRT = "T-shirt";
	public static String SHOES = "Shoes";
	public static String ZIP_HOODIE = "Zip Hoodie";
	
	
	public static String ORDER_CODE = "01";

	public static void main(String[] args) throws IOException, EncryptedDocumentException, InvalidFormatException {
		String outputFile = "BTL-" + ORDER_CODE + ".xlsx";
		writeOrderFile(readOrderFolder(ORDER_CODE), outputFile);
		System.out.println("GENERATE " + outputFile + " SUCCESSFULLY");
		System.out.println("HOODIE:" + hoodieCount + " , ZIP HOODIE: " + zipHoodieCount + " , SWEATSHIRT: "
				+ longSleeveCount + " , T-SHIRT: " + tshirtCount + " , SHOES: " + shoesCount);
		double price = (hoodieCount * hoodiePrice) + (zipHoodieCount * zipHoodiePrice) + (tshirtCount * tshirtPrice)
				+ (longSleeveCount * longSleevePrice) + (shoesCount * shoesPrice);
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
		if (variantTitle.contains(ZIP_HOODIE)) {
			zipHoodieCount = zipHoodieCount + quantity;
		} else if (variantTitle.contains(LONG_SLEEVE)) {
			longSleeveCount = longSleeveCount + quantity;
		} else if (variantTitle.contains(T_SHIRT)) {
			tshirtCount = tshirtCount + quantity;
		} else if (variantTitle.contains(HOODIE)) {
			hoodieCount = hoodieCount + quantity;
		} else if (variantTitle.contains(SHOES)) {
			shoesCount = shoesCount + quantity;
		}
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
			if ("Processing".equalsIgnoreCase(csvRecord.get("Order Status"))) {
				
		
				List<String> rowContent = new ArrayList<String>();
				// Accessing values by Header names
				rowContent.add(orderNo + "BTL" + csvRecord.get("Order Number"));
				String sku = csvRecord.get("SKU");
				String temp[] = sku.split("-");
				String designCode = temp[0];
				String prodyctType = getProductType(temp[1]);
				String productSize = "";
				// get size for SHOES
				if (SHOES.equals(prodyctType)) {
					// get size
					String productVariant = csvRecord.get("Product Variation");
					productSize = productVariant.split(":")[1].replace("US-", "").trim();
					
					if (productVariant.contains("MAN") || productVariant.contains("WOMAN")) {
						productSize = productVariant.replace("TYPE:", "").replace("|", "/");
					} else {
						productSize = "MAN / " + productVariant.split(":")[1].replace("US-", "US Size ").trim();
					}
					
				} else {
					productSize = temp[temp.length - 1];
				}
				
				
				String variantTitle = prodyctType + " / " + productSize;
				rowContent.add(designCode);
				rowContent.add(variantTitle); // attribue
				rowContent.add(csvRecord.get("Quantity"));
				rowContent.add("");
				rowContent.add(""); // empty data
				
				String address 	= csvRecord.get("Address 1&2 (Shipping)");
				String city 	= csvRecord.get("City (Shipping)");
				String stateCode 	= csvRecord.get("State Code (Shipping)");
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
		} else if ("SH".equals(productTypeCode) || "MAN".equals(productTypeCode) || "WOMAN".equals(productTypeCode)) {
			return SHOES;
		}
		
		return "";
	}

}
