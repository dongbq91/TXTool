package com.txfashion.trackingnumber;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Matching {
	public static final String ORDER_FILE = "LT002-ZIP.xlsx";
	public static final String TRACKING_NUMBER_FILE = "LT002-2-27.xlsx";
	public static final String OUTPUT_FILE = "lt002_tracking_number.xlsx";

	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException {
		
		
		String myString = "10000LTAD123.mp3";
		myString = myString.replaceFirst("^.*?LT", "");
		// OR myString = myString.replaceFirst("_[^.]*", "");
		System.out.println(myString);

//		List<List<String>> orderFileContent = readContent(ORDER_FILE);
//		List<List<String>> trackingNumberFile = readContent(TRACKING_NUMBER_FILE);
//		List<List<String>> fullMapping = new ArrayList<List<String>>();
//		
//		for (List<String> trackingNumber : trackingNumberFile) {
//			String orderID = trackingNumber.get(0);
//			for (List<String> orderNumber : orderFileContent) {
//				if (orderID.contains(orderNumber.get(0))) {
//					trackingNumber.add(orderNumber.get(16));
//				}
//			}
//			
//			System.out.println(trackingNumber);
//			fullMapping.add(trackingNumber);
//		}
//		
//		
//		
//		writeOrderFile(fullMapping, OUTPUT_FILE);
		

	}
	
	
	
	public static void writeOrderFile(List<List<String>> contens, String outputFile)
			throws EncryptedDocumentException, InvalidFormatException, IOException {

		
		// Obtain a workbook from the excel file
		FileInputStream fileInputStream = new FileInputStream("tracking_template.xlsx");
		Workbook wb = new XSSFWorkbook(fileInputStream);

		// Get Sheet at index 0
		Sheet sheet = wb.getSheetAt(0);
		int rowCount = sheet.getPhysicalNumberOfRows();
		for (int i = 0; i < contens.size(); i++) {
			Row row = sheet.createRow(rowCount + 1);
			row.createCell(0).setCellValue(contens.get(i).get(0)); // Order ID
			row.createCell(1).setCellValue(contens.get(i).get(1)); // Customer Name
			if (contens.get(i).size() > 6) {
				row.createCell(2).setCellValue(contens.get(i).get(6)); // Email
			}
			
			row.createCell(3).setCellValue(contens.get(i).get(3)); // Tracking Number
			row.createCell(4).setCellValue("https://t.17track.net/en#nums=" + contens.get(i).get(3)); // Link
			

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

	public static List<List<String>> readContent(String inputFile) {

		List<List<String>> contents = new ArrayList<List<String>>();
		

		try {
			FileInputStream inp = new FileInputStream(inputFile);
			Workbook wb = WorkbookFactory.create(inp);
			DataFormatter formatter = new DataFormatter();

			Sheet sheet = wb.getSheetAt(0);

			int rowsCount = sheet.getLastRowNum();
			for (int i = 0; i <= rowsCount; i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					List<String> rowContent = new ArrayList<String>();
					int colCounts = row.getLastCellNum();
					for (int j = 0; j < colCounts; j++) {
						Cell cell = row.getCell(j);
						
						String val = formatter.formatCellValue(cell);
						rowContent.add(val);
					}

					contents.add(rowContent);
				}

			}

		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}

		return contents;
	}

}
