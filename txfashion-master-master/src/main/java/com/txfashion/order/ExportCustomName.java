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

public class ExportCustomName {

	private static final String FILE_TEMPLATE = "custom_template-v2.xlsx";
	public static final String ORDER_FOLDER = "order_files";
	
	
	public static String ORDER_CODE = "516";
	
	public static void main(String[] args) throws IOException, EncryptedDocumentException, InvalidFormatException {
		String outputFile = "LT" + ORDER_CODE +"-CN"+".xlsx";
		writeOrderFile(readOrderFolder(ORDER_CODE), outputFile);	
		System.out.println("GENERATE " + outputFile + " SUCCESSFULLY");
		
	}

	public static void writeOrderFile(List<List<String>> contens, String outputFile)
			throws EncryptedDocumentException, InvalidFormatException, IOException {

		System.out.println(contens);
		
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
			
				List<String> rowContent = new ArrayList<String>();
				// Accessing values by Header names
				rowContent.add(orderNo + "LT" + csvRecord.get("Order Number"));
				 
				String sku = csvRecord.get("SKU");
				String temp[] = sku.split("-");
				String designCode = temp[0];
				System.out.println(csvRecord.get("Order Number"));
				
				rowContent.add(designCode);
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
	
				contents.add(rowContent);
				
				String custom = csvRecord.get("Product Variation").replace("|", "//");
				if (custom.contains("Your Name")) {
					String[] tp = custom
							.replace(" _Your Name: ","")
							.replace(" _Your Number:","")
							.replace(" Your Name: ","")
							.replace("Your Number:", "")
							.replaceAll("Size: MEN US[0-9][0-9]-EU[0-9][0-9]","")
							.replaceAll("Size: MEN US[0-9]-EU[0-9][0-9]","")
							.replaceAll("Size: WOMEN US[0-9][0-9]-EU[0-9][0-9]","")
							.replaceAll("Size: WOMEN US[0-9]-EU[0-9][0-9]","")
							.replaceAll("US Size: [A-Z][A-Z][A-Z]","")
							.replaceAll("US Size: [A-Z][A-Z]","")
							.replaceAll("US Size: [A-Z]","")
							.replaceAll("size: Universal Fit","")
							.replaceAll("Size: [A-Z][A-Z][A-Z]","")
							.replaceAll("Size: [A-Z][A-Z]","")
							.replaceAll("Size: [A-Z]","")
							.replaceAll("_","")
							.split("//"); 
					if(tp.length == 3) {
						rowContent.add(designCode+"-"+ tp[tp.length - 1]);
					}else {
						rowContent.add(designCode+"-"+tp[tp.length - 2] +"-"+ tp[tp.length - 1]);
					}
					
				} 
				else if (custom.contains("Any Name")) {
					String[] tp = custom.replace(" Any Name: ","").replace("Any Number:","").replaceFirst("Size:", "").split("//"); 
					rowContent.add(designCode+"-"+tp[tp.length - 2] +"-"+ tp[tp.length - 1]);
				} 
				
				else if (custom.contains("Uploaded Media"))  {
					String[] tp = custom.split("//");
					rowContent.add("Uploaded Media : https://"+tp[tp.length - 2]);
				}else {
					rowContent.add(designCode);
				}
				}
		
		csvParser.close();

		return contents;

	}
	
	
}
	
	

	