package com.txfashion.trackingnumber;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

public class FindTrackingNumber {
	
	public static final String TRACKING_FOLDER = "tn_file";
	public static final String TRACKING_ORDER_FOLDER = "tn_order_file";
	public static final String TRACKING_OUTPUT_FILE = "tracking_number_LT-18-Enzotee.csv";
	public static final int SHIPMENT_COMPANY_COLUMN = 8;//8
	public static final int TRACKING_NUMBER_COLUMN 	= 6;//6
	public static final int ORDER_ID_COLUMN 		= 0;
	
	public static final int tracking_f = 2;
	
	public static void main(String[] args) throws IOException {
		
		Map<String, List<String>> allContents = readTrackingNumberFolder();
		BufferedWriter variantWriter = Files.newBufferedWriter(Paths.get(TRACKING_OUTPUT_FILE));
		Map<String, String> orderIdMap = readOrderFolder();
        CSVPrinter csvPrinter = new CSVPrinter(variantWriter, CSVFormat.DEFAULT
                .withHeader("order_number", "order_id","order_status","force_email_notification","dispatch_date","custom_text", "tracking_info"));
        
        Set<String> set = orderIdMap.keySet(); // set of order number.
        for (String key : set) {
        	String orderNumber 		= key;
        	String orderID 			= orderIdMap.get(key);
        	String trackingNumber 	= "";
        	String shippingCompany 	= "";
        	
        	// Find tracking number 
        	List<String> tnSC = allContents.get(orderNumber); // Tracking Number:0 - Shipment company: 1
        	if (tnSC != null) {
        		trackingNumber = tnSC.get(0);
        		shippingCompany = tnSC.get(1);
        	}
        	
        	String trackingURL = "https://t.17track.net/en#nums="; // Default is E-package
			String tracking_info = "0:" + trackingNumber;
			if ("USPS".equalsIgnoreCase(shippingCompany)) {
				trackingURL = "https://tools.usps.com/go/TrackConfirmAction?tLabels=";
				tracking_info = "USPS:" + trackingNumber;
			} else if ("CNE".equalsIgnoreCase(shippingCompany)) {
				trackingURL = "https://track.aftership.com/cnexps/";
				tracking_info = "1:" + trackingNumber;
			} else if ("SF-EXPRESS".equalsIgnoreCase(shippingCompany) || "SF-EXPRESS".equalsIgnoreCase(shippingCompany)) {
				trackingURL = "https://www.sf-express.com/cn/en/dynamic_function/waybill/#search/bill-number/";
				tracking_info = "2:" + trackingNumber;
			} else if ("DHL".equalsIgnoreCase(shippingCompany)) {
				trackingURL = "https://www.dhl.com/en/express/tracking.html?AWB=";
				tracking_info = "3:" + trackingNumber;
			} else if ("UBI".equalsIgnoreCase(shippingCompany)) {
				trackingURL = "https://track.aftership.com/ubi-logistics/";
				tracking_info = "4:" + trackingNumber;
			} if ("4PX".equalsIgnoreCase(shippingCompany) || "4px-express".equalsIgnoreCase(shippingCompany)) {
				trackingURL = "https://track.aftership.com/4px/";
				tracking_info = "5:" + trackingNumber;
			} if ("YUN EXPRESS".equalsIgnoreCase(shippingCompany) || "YUN EXPRESS".equalsIgnoreCase(shippingCompany)) {
				trackingURL = "https://track.aftership.com/yunexpress/";
				tracking_info = "6:" + trackingNumber;
			}
			
			// Print Product
			String customText = "Our shipment channels were influenced by the current global epidemic situations. "
					+ "This time not only the domestic flights and even overseas flights were seriously attacked by the novel corona virus."
					+ "This is a difficult time for everyone around the world but together we will get through this crisis. "
					+ "From our family to yours. We wish that you stay healthy and safe. "
					+ "Please allow few more days to wait for tracking number is live. Here is your tracking number: " + trackingURL + trackingNumber;
			
			
			
			if ("".equals(trackingNumber)) {
				continue;
			}
			
			csvPrinter.printRecord(orderNumber, orderID,  "wc-completed", "send_email_customer_completed_order", "", customText, tracking_info);
        	
        	
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
	public static Map<String, List<String>> readTrackingNumberFolder() throws IOException {
		
		
		File folder = new File(TRACKING_FOLDER);
		File[] listOfFiles = folder.listFiles();
		Map<String, List<String>> allContents = new HashedMap<String, List<String>>();
		
		for (File file : listOfFiles) {
			if (file.isFile()) {
				String fileName = TRACKING_FOLDER + "/" + file.getName();
				allContents.putAll(readContent(fileName));
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
	public static Map<String, List<String>> readContent(String inputFile) {
		Map<String, List<String>> orderIDTrackingNumbers = new HashedMap<String, List<String>>(); // orderID - List <TN, Shipment Company>
		try {
			FileInputStream inp = new FileInputStream(inputFile);
			Workbook wb = WorkbookFactory.create(inp);
			DataFormatter formatter = new DataFormatter();
			Sheet sheet = wb.getSheetAt(0);

			int rowsCount = sheet.getLastRowNum();
			
			for (int i = 1; i <= rowsCount; i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					List<String> trackingNumbers = new ArrayList<String>();
					// Get Order ID
					Cell cellOrderID = row.getCell(ORDER_ID_COLUMN);
					String orderID = formatter.formatCellValue(cellOrderID)
							.replace("-1", "")
							.replace("-2", "")
							.replace("-3", "")
							.replace("-4", "")
							.replace("-5", "")
							.replace(" ", "")
							.replace("ID-", "")
							.replace("PH-", "")
							.replaceFirst("^.*?LT", ""); // Need to change this;
					
					// get tracking Number
					Cell cellTN = row.getCell(TRACKING_NUMBER_COLUMN);
					String tn = formatter.formatCellValue(cellTN);
					
					// get shipment company
					Cell cellShipmentCompany = row.getCell(SHIPMENT_COMPANY_COLUMN);
					String sc = formatter.formatCellValue(cellShipmentCompany);
					
					trackingNumbers.add(tn);
					trackingNumbers.add(sc);
					
					orderIDTrackingNumbers.put(orderID, trackingNumbers);
					
					// 
				}
				
				

			}

		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}

		return orderIDTrackingNumbers;
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
