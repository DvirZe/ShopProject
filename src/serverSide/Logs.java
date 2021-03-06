package serverSide;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONObject;

public class Logs {
	public void selesLog(JSONObject json) throws IOException {
		FileWriter log = new FileWriter("./files/sells.log",true);
		log.write("#Sell - "+json.get("shopName") +" - "+
				"Customer: " + json.get("customerId") + ", " +
				"Shirt 1: " + json.get("shirt1") + ", " +
				"Shirt 2: " + json.get("shirt2") + ", " +
				"Pants 1: " + json.get("pants1") + ", " +
				"Pants 2: " + json.get("pants2") + ", " +
				"Total price: " + json.get("totalPrice") + " " +
				 new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date())
				 + "\n");
		log.close();
	}
	
	public void workersLog(JSONObject json) throws IOException {
		FileWriter log = new FileWriter("./files/workers.log",true);
		log.write("#"+json.get("Status")+" - "+
				"Worker ID: " + json.get("personalID") + ", " +
				"Worker name: " + json.get("name") + ", " +
				"Worker phone: " + json.get("phoneNr") + ", " +
				"Shop name: " + json.get("shop") + " " +
				 new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date())
				 + "\n");
		log.close();
	}
	
	
	public void loginsLog(JSONObject json) throws IOException {
		FileWriter log = new FileWriter("./files/logins.log",true);
		log.write("#Login "+(json.containsKey("shopName")?json.get("shopName"):"Login Failed!") +" - "+
				"Worker ID: " + json.get("personalID") + " - " +
				 new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date())
				 + "\n");
		log.close();
	}
	
	public void logoutsLog(JSONObject json) throws IOException {
		FileWriter log = new FileWriter("./files/logins.log",true);
		log.write("#Logout "+json.get("shopName") +" - "+
				"Worker ID: " + json.get("personalID") + " - " +
				 new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date())
				 + "\n");
		log.close();
	}
	
	public void clientsLog(JSONObject json) throws IOException {
		FileWriter log = new FileWriter("./files/Customers.log",true);
		log.write("#" + json.get("Action") + " - " +
				"Client ID: " + json.get("customerId") + ", " +
				"Name: " + json.get("name") + ", " +
				"Phone: " + json.get("phoneNr") + ", " +
				"Type: " + json.get("customerType") + ", " +
				"Worker Updated: " + json.get("Worker") + " - " +
				 new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date())
				 + "\n");
		log.close();
	}
	
	public void updateInventoryLog(JSONObject json) throws IOException {
		FileWriter log = new FileWriter("./files/sells.log",true);
		log.write("#Update Inventory - "+json.get("shopName") +" - "+
				"Shirt 1: " + json.get("shirt1") + ", " +
				"Shirt 2: " + json.get("shirt2") + ", " +
				"Pants 1: " + json.get("pants1") + ", " +
				"Pants 2: " + json.get("pants2") + " " +
				 new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date())
				 + "\n");
		log.close();
	}
	
	public void chatConncetion(JSONObject json) throws IOException {
		FileWriter log = new FileWriter("./files/chats.log",true);
		if (json.containsValue("Save")) {
			log.write("#Chat log save:\n" + json.get("chatLog") + "\n" + 
					 new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) + "\n");
		} else if (json.containsValue("Open")) {
			log.write("#Open chat - " + json.get("openUser") + " open connection with " + json.get("with")
					 + " " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) + "\n");
		} else {
			log.write("#Join chat - " + json.get("openUser") + " Join to chat with " + json.get("with")
			 + " " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) + "\n");
		}
		log.close();
	}
	
	public void noAvaliableToChat(ArrayList<String> unableToChat, String tryingWorker) throws IOException {
		FileWriter log = new FileWriter("./files/chats.log",true);
		log.write("#Fail - " + tryingWorker + " tried to connect with:\n");
		for (String trying : unableToChat) {
			log.write("-"+trying+"\n");
		}
		log.write(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) + "\n");
		log.close();
	}
	
}
