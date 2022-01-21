package com.txfashion.product;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;

public class ImportSimpleProduct {
	public static final String ZIP 			= "zip";
	public static final String HOODIE 		= "hoodie";
	public static final String FRONT 		= "front";
	public static final String BACK			= "back";
	public static final String SLEEVE 		= "sleeve";
	public static final String TEE			= "tee";
	public static final String BOMBER		= "bomber";
	public static final String ZIP_BACK 	= "zip_back";
	public static final String HOODIE_BACK 	= "hoodie_back";
	public static final String SLEEVE_BACK 	= "sleeve_back";
	public static final String TEE_BACK		= "tee_back";
	public static final String BOMBER_BACK	= "bomber_back";
	public static final String POSTER		= "poster";
	public static final String SHOES 		= "shoes";
	
	public static final String OUTPUT_FILE 	= "woo-pi-data.csv";
	
	
	
	public static void main(String[] args) throws IOException {
	
		String[] designCodes = readFolder(getProperty("image_folder"));
		generateImageFolder(designCodes, getProperty("image_folder"));
		generateProductImport(designCodes, getProperty("image_folder"));

	}
	
	/**
	 * Generate product import.
	 * @throws IOException 
	 * 
	 */
	public static void generateProductImport(String[] designCodes, String imageFolder) throws IOException {
		BufferedWriter writer = Files.newBufferedWriter(Paths.get("woo-product-data.csv"));

        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                .withHeader("post_title","post_name","ID","post_excerpt","post_content", "post_status" ,"menu_order", "post_date", "post_parent" ,"post_author" ,"comment_status", "sku", "downloadable", "virtual", "visibility", "stock", "stock_status", "backorders", "manage_stock", "regular_price", "sale_price", "weight", "length", "width", "height", "tax_status", "tax_class", "upsell_ids" ,"crosssell_ids", "featured" ,"sale_price_dates_from", "sale_price_dates_to", "download_limit", "download_expiry", "product_url", "button_text", "meta:_yoast_wpseo_focuskw", "meta:_yoast_wpseo_title", "meta:_yoast_wpseo_metadesc", "meta:_yoast_wpseo_metakeywords", "images", "downloadable_files", "tax:product_type", "tax:product_visibility", "tax:product_cat", "tax:product_tag", "tax:product_shipping_class", "meta:custom_page_title", "meta:laborator_meta_description", "meta:laborator_meta_keywords", "meta:laborator_meta_robots_follow", "meta:laborator_meta_robots_index", "meta:slide_template", "meta:total_sales", "attribute:Combo", "attribute_data:Combo", "attribute_default:Combo"));
		
        
        BufferedWriter variantWriter = Files.newBufferedWriter(Paths.get("woo-variant-data.csv"));

        CSVPrinter variantPrinter = new CSVPrinter(variantWriter, CSVFormat.DEFAULT
                .withHeader("Parent","parent_sku","post_parent","ID","post_status", "menu_order" ,"sku", "downloadable", "virtual" ,"stock" ,"stock_status", "regular_price", "sale_price", "weight", "length", "width", "height", "tax_class", "variation_description", "file_path", "file_paths", "download_limit", "images", "downloadable_files", "tax:product_visibility", "tax:product_shipping_class", "meta:attribute_pa_color", "meta:attribute_size", "meta:attribute_size-please-check-the-sizing-table", "meta:attribute_combo" ,"meta:custom_page_title", "meta:laborator_meta_description" ,"meta:laborator_meta_keywords", "meta:laborator_meta_robots_follow", "meta:laborator_meta_robots_index", "meta:total_sales"));
		
        
        
        for (String designCode : designCodes) {
        	// Get Post name
        	System.out.println("DESIGN CODE: " + designCode);
        	String temp[] = designCode.split("-");
        	String post_name = designCode;
        	if (temp.length >= 2) {
        		post_name = temp[1];
        	}
        	
        	
        	// Get product image
        	Map<String, String> productImages = getImageURL(designCode, imageFolder);

    		StringBuilder productImage = new StringBuilder();
    		
    		// Check image
    		if (productImages.get("poster") != null) {
    			productImage.append(productImages.get("poster"));
    			productImage.append("|");
    		} 
    		
    		// Check image
    		if (productImages.get("bomberFrontURL") != null) {
    			productImage.append(productImages.get("bomberFrontURL"));
    			productImage.append("|");
    		} 
    		
    		// Check image
    		if (productImages.get("bomberBackURL") != null) {
    			productImage.append(productImages.get("bomberBackURL"));
    			productImage.append("|");
    		} 
    		
    		// Check image
    		if (productImages.get("hoodieFrontURL") != null) {
    			productImage.append(productImages.get("hoodieFrontURL"));
    			productImage.append("|");
    		} 
    		
    	
    		// Check image
    		if (productImages.get("zipFrontURL") != null) {
    	
    			productImage.append(productImages.get("zipFrontURL"));
    			productImage.append("|");
    		}
    		
    		// Check image
    		if (productImages.get("hoodieBackURL") != null) {
    	
    			productImage.append(productImages.get("hoodieBackURL"));
    			productImage.append("|");
    		}
    		
    		
    		// Check image
    		if (productImages.get("sleeveFrontURL") != null) {
    			productImage.append(productImages.get("sleeveFrontURL"));
    			productImage.append("|");
    		}

    		// Check image
    		if (productImages.get("sleeveBackURL") != null) {

    			productImage.append(productImages.get("sleeveBackURL"));
    			productImage.append("|");
    		}

    		// Check image
    		if (productImages.get("teeFrontURL") != null) {
    			productImage.append(productImages.get("teeFrontURL"));
    			productImage.append("|");
    		}
    		    		
    		// Check image
    		if (productImages.get("teeBackURL") != null) {
    			productImage.append(productImages.get("teeBackURL"));

    		}
    		
    		
        	if (productImage.toString().substring(productImage.length() - 1).equals("|")) {
        		productImage.deleteCharAt(productImage.length() - 1);
        	}
        	
        	// get ID
        	long productId = (long) (Math.random() * 100000000000000L); // random 14 numbers
        	String des = "<div id=\"featurebullets_feature_div\" class=\"feature\" data-feature-name=\"featurebullets\" data-cel-widget=\"featurebullets_feature_div\"> <div id=\"feature-bullets\" class=\"a-section a-spacing-medium a-spacing-top-small\"> <ul class=\"a-unordered-list a-vertical a-spacing-none\"> <li><span class=\"a-list-item\">Effective Filtration : Our pollution mask has 5 layers, which can filter 95% of dust, vehicle and industry exhaust, pollen allergy, chemicals, fumes and particulates. Cleaner and healthier, improving the quality of the inhaled air.</span></li> <li><span class=\"a-list-item\">Materials: Lightweight, durable, soft and comfortable, outdoor mask is well built with diving fabric. The activated carbon filtration could protect against dust and keep warm. </span></li> <li><span class=\"a-list-item\">Multi-purpose: Fit for running, cycling, hiking, skiing, painting, cleaning, woodworking and other outdoor activities. Anti dust mask can keep you away from the pollution, make you enjoy outdoor sports and healthier life.</span></li> <li><span class=\"a-list-item\">Perfect design: Soft half face mask is designed with an emphasis on ergonomics. Nose chip can effectively prevent sliding, and the size can be adjusted by Velcro to meet most people. Fashionable and unique design makes you more attractive.</span></li> <li><span class=\"a-list-item\">Washable and Replaceable : the cycling mask’s activated carbon filter is replaceable and very easy and convenient to install. 5 special protective layers offer cleaner air to protect your mouth, nose and lung.</span></li> <li>Great Gift: Good gift for Family, Friends, Lovers. Masks for dust protection, Good quality mask for the one you love</li> <li><strong><span data-spm-anchor-id=\"a2g0o.detail.1000023.i0.7b03778ahYfrcc\">Period of use:</span></strong>Face mask can be used at least 2 years . It is recommended to replace the filter within 30 days and frequently use (Please replace , if the filter be black or damaged. )</li> </ul> </div> </div> <strong>SHIPPING &amp; HANDLING: </strong> <strong>Shipping method</strong>: Standard Shipping. Tracking and secure check out are provided for all orders. <strong>Processing Time</strong>: 2–4days* in regular seasons. <strong>Shipping Time</strong>: 7 –15 days* in regular seasons. <strong>(*)</strong> might be delayed 2-4 days due to the peak season, but we will try our best to fulfill orders as fast as we can. Average transit times are typically received in 3 weeks.";
        	String postEx = "Usually, one filter can be used for 30 days, It is recommended to change at any time during heavy pollution periods";
        	// For shoes
        	
        	String tagName = designCode.replace("DCC", "").replace(post_name, "").replace("-", " ").replace(",", "|").trim();
        	String categoryName = tagName;
        	// get keyword search and add to tag
        	if (getProperty("GG_keyword") != null) {
        		tagName = tagName + "|" + getProperty("GG_keyword");
        	}
        	
        	// Build product name.
        	String productName = "";
        	// add_tag_to_product_name=true
        	if (getProperty("add_tag_to_product_name") != null && Boolean.parseBoolean(getProperty("add_tag_to_product_name"))) {
        		String designName[] = tagName.replace("|", ",").split(",");
        		if (designName.length > 1) {
        			productName = designName[0] + " " + getProperty("product_name");
        		} else {
        			productName = tagName + " " + getProperty("product_name");
        		}
        		
        	} else {
        		productName = getProperty("product_name");
        	}
        	
        	
        	
        	
        	String types = getProductType("product_type").replace(",", "|");
        	
        	
        	
        	if (generateVariant(productImages, designCode, productId, post_name, variantPrinter)) {
        		// Print Product
            	csvPrinter.printRecord(productName, post_name.trim(), productId, postEx, des, "publish", 0, "03/03/2019  12:00:00 AM", 0, 1, "open", "", "no", "no", "visible", "", "instock", "no", "no", "", "", "", "", "", "", "taxable", "", "", "", "no", "", "", 0, 0, "", "", "", "", "", "", productImage, "", "variable", "exclude_from_catalog|exclude_from_search", categoryName, tagName, "", "", "", "", "", "", "", 0, types, "0|1|1", "", "");
            	
        	}
        	
        	

        }
       
        variantPrinter.flush();
        variantPrinter.close();
        csvPrinter.flush(); 
        csvPrinter.close();
		
	}
	
	/**
	 * Generate variant
	 * @throws IOException 
	 * 
	 */
	public static boolean generateVariant(Map<String, String> productImages, String designCode, long productId, String post_name, CSVPrinter variantPrinter) throws IOException {

		String productType = getProperty("product_type");
		String typesContents[] = productType.split("-");
		int menuOrder = 0;
		for (String typeContent : typesContents) {
			String temp[] = typeContent.split(",");
			String type = temp[0].trim();
			String code = temp[1];
			String salePrice = temp[2];
			String price = temp[3];
			String variantImage = "";
			
			if ("Bomber Jacket".equals(type)) {
				variantImage = productImages.get("bomberFrontURL");
				if (variantImage == null) {
					System.out.println("LACK OF BOMBER JACKET -- Ingore this product.");
					
					return false;
				}
			} else if ("Hoodie".equals(type)) {
				variantImage = productImages.get("hoodieFrontURL");
				if (variantImage == null) {
					System.out.println("LACK OF HOODIE FRONT IMAGE -- Ingore this product.");
					
					return false;
				}
			}  else if ("Zip Hoodie".equals(type)) {
				variantImage = productImages.get("zipFrontURL");
				if (variantImage == null) {
					System.out.println("LACK OF ZIP HOODIE FRONT IMAGE -- Ingore this product.");
					
					return false;
				}
			} else if ("Unisex Long Sleeve".equals(type)) {
				variantImage = productImages.get("sleeveFrontURL");
				if (variantImage == null) {
					System.out.println("LACK OF LONG SLEEV  FRONT IMAGE -- Ingore this product.");
					
					return false;
				}
			} else if ("Unisex Tee".equals(type)) {
				variantImage = productImages.get("teeFrontURL");
				if (variantImage == null) {
					System.out.println("LACK OF TEE FRONT IMAGE -- Ingore this product.");
					
					return false;
				}
			} else if ("Shoes".equals(type)) {
				variantImage = productImages.get("poster");
				if (variantImage == null) {
					System.out.println("LACK OF shoes image.");
					
					return false;
				}
			} 
			
			
			
			
				// get ID
	        	long variantId = (long) (Math.random() * 100000000000000L); // random 14 numbers
				// Print Product
				String sku = post_name + "-" + "AC" + code ;
				
				// END FO SHOES
				
				sku = sku.replaceAll("\\s","").trim();
				variantPrinter.printRecord(getProperty("product_name"), "", productId, variantId, "publish", menuOrder, sku, "no", "no", "", "instock", price, salePrice, "", "", "", "", "parent", "", "", "{\"d41d8cd98f00b204e9800998ecf8427e\":{\"name\":\"\",\"file\":\"\"}}", 0, variantImage, "::", "", "", "", "", "", type, "", "", "", "", "", 0); 
			
		}
		
		
	      
			
		
		return true;
	}
	
	
	public static Map<String, String> getImageURL(String designCode, String imageFolder) {
		String folderPath = folderPath(false);
		// Copy all neccessary design code to folder.
		File folder = new File(imageFolder + File.separator + designCode);
		File[] listOfFiles = folder.listFiles();
		
		Map<String, String> productImage = new HashedMap<String, String>();
		
		
		for (File file : listOfFiles) {
			
		    if (file.isFile()) {
		        // check each file
		    	if (!detectProductImage(file.getName()).equals("")) {
		    		
		    		String newImageName = designCode + "-" + file.getName();
		    		newImageName = newImageName.replaceAll("\\s","");
		    		String imageURL = getProperty("website_url") + "/" + folderPath + "/" +  newImageName ;
		    		if (detectProductImage(file.getName()).equals(ZIP)) {
		    			productImage.put("zipFrontURL", imageURL);

		    		} else if (detectProductImage(file.getName()).equals(HOODIE)) {
		    			productImage.put("hoodieFrontURL", imageURL);

		    		} else if (detectProductImage(file.getName()).equals(SLEEVE)) {
		    			productImage.put("sleeveFrontURL", imageURL);

		    		} else if (detectProductImage(file.getName()).equals(TEE)) {
		    			productImage.put("teeFrontURL", imageURL);

		    		} else if (detectProductImage(file.getName()).equals(HOODIE_BACK)) {
		    			productImage.put("hoodieBackURL", imageURL);

		    		} else if (detectProductImage(file.getName()).equals(SLEEVE_BACK)) {
		    			productImage.put("sleeveBackURL", imageURL);

		    		} else if (detectProductImage(file.getName()).equals(TEE_BACK)) {
		    			productImage.put("teeBackURL", imageURL);

		    		} else if (detectProductImage(file.getName()).equals(BOMBER)) {
		    			productImage.put("bomberFrontURL", imageURL);

		    		} else if (detectProductImage(file.getName()).equals(BOMBER_BACK)) {
		    			productImage.put("bomberBackURL", imageURL);

		    		} else if (detectProductImage(file.getName()).equals(POSTER)) {
		    			productImage.put("poster", imageURL);

		    		} 
		    		
		    	}
		    }
		}
		

		return productImage;
	}
	
	
	public static void generateImageFolder(String[] designCodes, String imageFolder) throws IOException {
		// Create directory
		String folderPath = folderPath(true);
		File directories = new File(folderPath);
		directories.mkdirs();
		
		// Loop for all design code.
		for (String designCode : designCodes) {
			// Copy all neccessary design code to folder.
			File folder = new File(imageFolder + File.separator + designCode);
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
			    if (file.isFile()) {
			        // check each file
			    	if (!detectProductImage(file.getName()).equals("")) {
			    		// copy this file to image directly
			    		File source = new File(imageFolder + File.separator + designCode +  File.separator + file.getName());
			    		String newImageName = designCode + "-" + file.getName();
			    		newImageName = newImageName.replaceAll("\\s","");
			    		File destination = new File(System.getProperty("user.dir") + File.separator + folderPath + File.separator +  newImageName );
			    		FileUtils.copyFile(source, destination);
			    	}
			    }
			}
		}

		
	}
	
	
	public static String folderPath(boolean useSeparator) {
		// Create folder
		LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		int day = now.getDayOfMonth();
		String slash = "/";
		if (useSeparator) {
			slash = File.separator;
		}
				
		StringBuilder folderPath = new StringBuilder();
		folderPath.append(year);
		
		folderPath.append(slash);
		folderPath.append(month);
		folderPath.append(slash);
		folderPath.append(day);
		
		return folderPath.toString();
	}
	
	
	
	public static String getProductType(String variant) {
		String productType = getProperty("product_type");
		String typesContents[] = productType.split("-");
		StringBuilder types = new StringBuilder();
		int count = 0;
		for (String typeContent : typesContents) {
			count ++;
			String temp[] = typeContent.split(",");
			String type = temp[0].trim();
			
			types.append(type);
			if (count < typesContents.length) {
				types.append(",");
			}
			
		}
		
		return types.toString();
	}
	
	
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static String detectProductImage(String fileName) { 
		
		if (fileName.toLowerCase().contains(ZIP)) {
			if (fileName.toLowerCase().contains(BACK)) {
				return ZIP_BACK;
			}
			
			return ZIP;
		} else if (fileName.toLowerCase().contains(HOODIE)) {
			if (fileName.toLowerCase().contains(BACK)) {
				return HOODIE_BACK;
			}
			return HOODIE;
		} else if (fileName.toLowerCase().contains(SLEEVE) && fileName.toLowerCase().contains("long")) {
			if (fileName.toLowerCase().contains(BACK)) {
				return SLEEVE_BACK;
			}
			return SLEEVE;
		} else if (fileName.toLowerCase().contains(TEE)) {
			if (fileName.toLowerCase().contains(BACK)) {
				return TEE_BACK;
			}
			
			return TEE;
		} else if (fileName.toLowerCase().contains(BOMBER)) {
			if (fileName.toLowerCase().contains(BACK)) {
				return BOMBER_BACK;
			}
			
			return BOMBER;
		} else if (fileName.toLowerCase().contains(POSTER)) {
			
			return POSTER;
		}
		
		return "";
	}
	
	
	
	public static String[] readFolder(String folder) {
		File file = new File(folder);
		String[] directories = file.list(new FilenameFilter() {
		  @Override
		  public boolean accept(File current, String name) {
		    return new File(current, name).isDirectory();
		  }
		});
		
		return directories;
	}
	
	
	public static String getProperty(String propName) {
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("config.properties");
			prop.load(input);
			
			// set the properties value
			return prop.get(propName) != null ? prop.get(propName).toString() : "";
			

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return "";
	}

}
