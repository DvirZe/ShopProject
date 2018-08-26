package serverSide;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.io.Reader;
import java.net.Socket;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class serverClass extends Thread {
	private Socket socket;
	private JSONObject workers;
	
	
	public serverClass() {}
	
	public serverClass(Socket socket) throws FileNotFoundException, IOException, ParseException {
		this.socket = socket;
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("./files/Workers.json"));
		workers = (JSONObject) obj;
	}
	
	public void actionChoose(JSONObject json) throws IOException
	{
		switch (json.get("Action").toString()) {
		case "1":
			loginToApp(json);
			break;

		default:
			break;
		}
	}
	
	public void loginToApp(JSONObject json) throws IOException
	{
		 JSONArray workerDetails = new JSONArray();
		 workerDetails = (JSONArray) workers.get(json.get("WorkerID"));
		 if ((!workerDetails.isEmpty()) && (workerDetails.get(5).equals(json.get("password"))))
		 {
			 System.out.println("Nice!");
		 }
		 else
		 {
			 System.out.println("Fail! " + workerDetails.get(5) + " " + json.get("password"));
		 }
		/*System.out.println(json.get("username").toString()); //Just a test, not log in function
		try (FileWriter file = new FileWriter("./files/users.json", true)) {
			file.write(json.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + json);
		}*/
	}
	
	public void run() {
		JSONParser parser = new JSONParser();
		
		//JSONObject json = null;
		//PrintWriter printWriter;
		BufferedReader bufferedReader;
		//JSONParser parser = new JSONParser();
			try {
				//printWriter = new PrintWriter(socket.getOutputStream(),true);
				bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String msg = bufferedReader.readLine();
				while(true) {
					if (!msg.equals(""))
						{
							JSONObject json = (JSONObject)parser.parse(msg);
							actionChoose(json);
						}
					while ((msg = bufferedReader.readLine()) == null) {}
					//msg = bufferedReader.readLine();
				}
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
			
			//System.out.println("User " + bufferedReader.readLine() + " is connected to the server...");
	}
}
