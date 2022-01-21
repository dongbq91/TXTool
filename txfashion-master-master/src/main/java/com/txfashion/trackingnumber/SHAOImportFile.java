package com.txfashion.trackingnumber;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class SHAOImportFile {
	
	public static final String TRACKING_FOLDER = "tn_file";
	public static final String TRACKING_ORDER_FOLDER = "tn_order_file";
	public static final String TRACKING_OUTPUT_FILE = "tracking_number_BTL01.csv";

	public static void main(String[] args) throws IOException {
		List<List<String>> allContents = readTrackingNumberFolder();
		
		BufferedWriter variantWriter = Files.newBufferedWriter(Paths.get(TRACKING_OUTPUT_FILE));
		
		Map<String, String> orderIdMap = readOrderFolder();

        CSVPrinter csvPrinter = new CSVPrinter(variantWriter, CSVFormat.DEFAULT
                .withHeader("order_number", "order_id","order_status","force_email_notification","dispatch_date","custom_text", "tracking_info"));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date date = new Date();
    	String dispatchDate = dateFormat.format(date); 
    	
		for (List<String> rowContent : allContents) {
				String shippingCompany = "e-Packet"; // Change for this
				String trackingNumber = rowContent.get(0).trim();
				
				String trackingURL = "https://t.17track.net/en#nums="; // Default is E-package
				String tracking_info = "0:" + trackingNumber;
				if ("USPS".equalsIgnoreCase(shippingCompany)) {
					trackingURL = "https://tools.usps.com/go/TrackConfirmAction?tLabels=";
					tracking_info = "USPS:" + trackingNumber;
				} else if ("CNE".equalsIgnoreCase(shippingCompany)) {
					trackingURL = "https://track.aftership.com/cnexps/";
					tracking_info = "1:" + trackingNumber;
				} else if ("SF express".equalsIgnoreCase(shippingCompany) || "SF".equalsIgnoreCase(shippingCompany)) {
					trackingURL = "https://www.sf-express.com/us/en/dynamic_function/waybill/#search/bill-number/";
					tracking_info = "2:" + trackingNumber;
				}
				
				// Print Product
				String customText = "Your package has been shipped. You can track your package from this URL: " + trackingURL + trackingNumber;
				String orderNumber = rowContent.get(1).replace("01BLT", ""); // Need to change this
            	csvPrinter.printRecord(orderNumber, orderIdMap.get(orderNumber),  "wc-completed", "send_email_customer_completed_order", dispatchDate, customText, tracking_info);
            	
		}
		
		 csvPrinter.flush(); 
	      csvPrinter.close();
	}
	
	/**
	 * read order folder.
	 * 
	 * @return
	 * @throws IOException
	 */
	public static List<List<String>> readTrackingNumberFolder() throws IOException {
		File folder = new File(TRACKING_FOLDER);
		File[] listOfFiles = folder.listFiles();
		List<List<String>> allContents = new ArrayList<List<String>>();
		
		for (File file : listOfFiles) {
			if (file.isFile()) {
				String fileName = TRACKING_FOLDER + "/" + file.getName();
				allContents.addAll(readContent(fileName));
			}
		}

		return allContents;
	}
	
	
	/**
	 * Read excel files.
	 * 
	 * @param inputFile
	 * @return
	 */
	public static List<List<String>> readContent(String inputFile) {
		List<List<String>> contents = new ArrayList<List<String>>();
		try {
			FileInputStream inp = new FileInputStream(inputFile);
			Workbook wb = WorkbookFactory.create(inp);
			DataFormatter formatter = new DataFormatter();
			Sheet sheet = wb.getSheetAt(0);

			int rowsCount = sheet.getLastRowNum();
			for (int i = 1; i <= rowsCount; i++) {
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
	
	
	/**
	 * Read order file
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static Map<String, String> readOrderFile(String fileName) throws IOException {
		Charset charset = Charset.forName("UTF-8");
		Reader reader = Files.newBufferedReader(Paths.get(fileName), charset);
		CSVParser csvParser = new CSVParser(reader,
				CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
		
		Map<String, String> ordersMap = new HashedMap<String, String>();

		for (CSVRecord csvRecord : csvParser) {
				// Accessing values by Header names
				ordersMap.put(csvRecord.get("Order Number"), csvRecord.get("Order ID"));
		}

		csvParser.close();

		return ordersMap;

	}
	
	
	public static Map<String, String> readOrderFolder() throws IOException {
		File folder = new File(TRACKING_ORDER_FOLDER);
		File[] listOfFiles = folder.listFiles();
		
		Map<String, String> orderIDMap = new HashedMap<String, String>();

		for (File file : listOfFiles) {
			if (file.isFile()) {
				String fileName = TRACKING_ORDER_FOLDER + "/" + file.getName();
				orderIDMap.putAll(readOrderFile(fileName));
			}
		}

		return orderIDMap;

	}
	
}
