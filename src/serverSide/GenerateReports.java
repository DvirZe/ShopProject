package serverSide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.sampled.Line;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GenerateReports {

	public void createSellReport(serverClass sc, String shop, JSONObject json) throws FileNotFoundException, IOException, ParseException {
		Scanner scanner = new Scanner(new File("./files/sells.log"));
		ArrayList<String> lines = new ArrayList<String>();
		
		while (scanner.hasNextLine()) { //read from file
		  lines.add(scanner.nextLine());
		}
		
		String[] sellsRecords = lines.toArray(new String[0]);
		
		//Menu to return report by client request
		if (json.containsKey("Item")) { reportByItem(sc, shop, json, sellsRecords); } 
		else if (json.containsKey("Type")) { reportByType(sc, shop, json, sellsRecords); }
		else if (json.containsKey("Date")) {reportByDate(sc, shop, json, sellsRecords); }
		else { simpleReport(sc, shop, json, sellsRecords); }
	}
	
	
	public void simpleReport(serverClass sc, String shop, JSONObject json, String[] sellsRecords) throws IOException {
		JSONObject report = new JSONObject();
		int i = 1;
		for (String index : sellsRecords) {
			if (index.contains(shop) && index.contains("Sell")) {
				String sb = index.substring(index.indexOf("Customer:"));
				report.put(i++, sb);
			}
		}
		sc.sendToClient(report);
	}
	
	public void reportByItem(serverClass sc, String shop, JSONObject json, String[] sellsRecords) throws IOException {
		JSONObject report = new JSONObject();
		int i = 1;
		for (String index : sellsRecords) {
			if (index.contains(shop) && index.contains("Sell") && !index.contains(json.get("Item").toString()+": 0")) {
				//Filters: Same shop as the client connection, record for sell and has record for the item 
				String sb = index.substring(index.indexOf("Customer:"), index.indexOf(" Shirt 1:"));
				String[] spliting = index.split(",");
				for (String s : spliting) {
					if (s.contains(json.get("Item").toString())) {
						sb = sb.concat(s + " - ");
					}
				}
				sb = sb.concat(index.substring(index.length()-19));
				report.put(i++, sb);
			}
		}
		sc.sendToClient(report);
	}
	
	public void reportByType(serverClass sc, String shop, JSONObject json, String[] sellsRecords) throws IOException {
		JSONObject report = new JSONObject();
		String[] inTypes = new String[2];
		int i = 1;
		if (json.get("Type") == "Shirt") {
			inTypes[0] = "Shirt 1";
			inTypes[1] = "Shirt 2";
		}
		else {
			inTypes[0] = "Pants 1";
			inTypes[1] = "Pants 2";
		}
		for (String index : sellsRecords) {
			if (index.contains(shop) && index.contains("Sell") && (!index.contains(inTypes[0]+": 0") || !index.contains(inTypes[1]+": 0"))) {
				//Filters: Same shop as the client connection, record for sell and has record for the type
				String sb = index.substring(index.indexOf("Customer:"), index.indexOf(" Shirt 1:"));
				String[] spliting = index.split(",");
				for (String s : spliting) {
					if (s.contains(json.get("Type").toString())) {
						sb = sb.concat(s + " - ");
					}
				}
				sb = sb.concat(index.substring(index.length()-19));
				sb = sb.replace(" -  ", ", ");
				report.put(i++, sb);
			}
		}
		sc.sendToClient(report);
	}
	
	public void reportByDate(serverClass sc, String shop, JSONObject json, String[] sellsRecords) throws IOException {
		JSONObject report = new JSONObject();
		int i = 1;
		for (String index : sellsRecords) {
			if (index.contains(shop) && index.contains("Sell") && index.contains(json.get("Date").toString())) {
				//Filters: Same shop as the client connection, record for sell and has record for the chosen date
				report.put(i++, index);
			}
		}
		sc.sendToClient(report);
	}
}