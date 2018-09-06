package serverSide;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;

public class Logs {
	public void selesLog(JSONObject json) throws IOException
	{
		FileWriter log = new FileWriter("./files/sales.log",true);
		log.write("#"+json.get("shopName") +" - "+
				"Customer: " + json.get("customerId") + " " +
				"Shirt 1: " + json.get("shirt1") + " " +
				"Shirt 2: " + json.get("shirt2") + " " +
				"Pants 1: " + json.get("pants1") + " " +
				"Pants 2: " + json.get("pants2") + " " +
				"Total price: " + json.get("totalPrice") + " " +
				 new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())
				 + "\n");
		log.close();
	}
	
	public void loginsLog(JSONObject json) throws IOException
	{
		FileWriter log = new FileWriter("./files/logins.log",true);
		log.write("#Login "+(json.containsKey("shopName")?json.get("shopName"):"Login Failed!") +" - "+
				"Worker ID: " + json.get("personalID") + " - " +
				 new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())
				 + "\n");
		log.close();
	}
	
	public void logoutsLog(JSONObject json) throws IOException
	{
		FileWriter log = new FileWriter("./files/logins.log",true);
		log.write("#Logout "+json.get("shopName") +" - "+
				"Worker ID: " + json.get("personalID") + " - " +
				 new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())
				 + "\n");
		log.close();
	}
}
