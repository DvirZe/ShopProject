package serverSide;

import java.io.FileWriter;
import java.io.IOException;
import java.security.Timestamp;

import org.json.simple.JSONObject;

public class Logs {
	public void selesLog(JSONObject json) throws IOException
	{
		FileWriter log = new FileWriter("./files/sales.log",true);
		log.write(
				new Timestamp(null, null) + "dd"
				
				
				);
		log.close();
	}
}
