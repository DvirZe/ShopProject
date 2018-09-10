package serverSide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GenerateReports {

	public void createSellReport(serverClass sc, String shop, JSONObject json) throws FileNotFoundException, IOException, ParseException
	{
		Scanner scanner = new Scanner(new File("./files/sells.log"));
		ArrayList<String> lines = new ArrayList<String>();
		
		while (scanner.hasNextLine()) {
		  lines.add(scanner.nextLine());
		}
		
		String[] sellsRecords = lines.toArray(new String[0]);
		
		if (json.containsKey("Item")) { reportByType(sc, shop, json, sellsRecords); } 
		else if (json.containsKey("type")) {}
		else { simpleReport(sc, shop, json, sellsRecords); }
	}
	
	
	public void simpleReport(serverClass sc, String shop, JSONObject json, String[] sellsRecords) throws IOException
	{
		JSONObject report = new JSONObject();
		int i = 1;
		for (String index : sellsRecords)
		{
			if (index.contains(shop) && index.contains("Sell"))
			{
				String sb = index.substring(index.indexOf("Customer:"));
				report.put(i++, sb);
			}
		}
		sc.sendToClient(report);
	}
	
	public void reportByType(serverClass sc, String shop, JSONObject json, String[] sellsRecords) throws IOException
	{
		JSONObject report = new JSONObject();
		int i = 1;
		for (String index : sellsRecords)
		{
			if (index.contains(shop) && index.contains("Sell") && !index.contains(json.get("Item").toString()+": 0"))
			{
				String sb = index.substring(index.indexOf("Customer:"));
				report.put(i++, sb);
			}
		}
		sc.sendToClient(report);
	}
}