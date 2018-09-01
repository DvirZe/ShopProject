package serverSide;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class serverClass extends Thread {
	private Socket socket;
	private JSONObject workers, shop;
	
	public serverClass() {}
	
	public serverClass(Socket socket) throws FileNotFoundException, IOException, ParseException {
		this.socket = socket;
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("./files/Workers.json"));
		workers = (JSONObject) obj;
		
	}
	
	public void actionChoose(JSONObject json) throws IOException, ParseException
	{
		switch (json.get("Action").toString()) {
		case "1":
			loginToApp(json);
			break;

		default:
			break;
		}
	}
	
	public void loginToApp(JSONObject json) throws IOException, ParseException
	{
		 JSONArray workerDetails = new JSONArray();
		 JSONObject answer = new JSONObject();
		 workerDetails = (JSONArray) workers.get(json.get("workerId"));
		 System.out.println(workerDetails);
		 if ((workerDetails != null) && (workerDetails.get(5).equals(json.get("password"))) && (Integer.parseInt(workerDetails.get(6).toString()) == 0))
		 {
			 answer.put("Status", 1);
			 answer.put("Shop", workerDetails.get(3));
			 answer.put("Job", workerDetails.get(4));
			 System.out.println("Send ok to client!");
			 workerDetails.set(6, 1); //Set user as logged in
			 System.out.println(workerDetails);
			 workers.get(json.get("workerId"));
			 JSONParser parser = new JSONParser();
			 Object obj = parser.parse(new FileReader("./files/"+workerDetails.get(3)+".json"));
		     shop = (JSONObject) obj;
		     System.out.println(shop.get("shopName"));
		     answer.putAll(shop);
		 }
		 else
		 {
			 answer.put("Status", "0");
			 System.out.println("Fail!");
		 }
		 sendToClient(answer);
		/*System.out.println(json.get("username").toString()); //Just a test, not log in function
		try (FileWriter file = new FileWriter("./files/users.json", true)) {
			file.write(json.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + json);
		}*/
	}
	
	public void sendToClient(JSONObject json) throws IOException
	{
		PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
		printWriter.println(json);
	}
	
	public void run() {
		JSONParser parser = new JSONParser();
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String msg = bufferedReader.readLine();
			while(true) {
				if (!msg.equals(""))
					{
						JSONObject messageIn = (JSONObject)parser.parse(msg);
						actionChoose(messageIn);
					}
				while ((msg = bufferedReader.readLine()) == null) {}
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		finally { endOfWork(); }
	}
	
	public void endOfWork() {
		System.out.println("All saved to files");
	}
	
}
