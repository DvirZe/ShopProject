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

import clientSide.Person;
import clientSide.Worker;


public class serverClass extends Thread {
	private Socket socket;
	private JSONObject workers, shop;
	private serverConnection serverConnection;
	private int logedInUserID;
	
	public serverClass() {}
	
	public serverClass(Socket socket, serverConnection serverConnection) throws FileNotFoundException, IOException, ParseException {
		this.socket = socket;
		/*JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("./files/Workers.json"));
		workers = (JSONObject) obj;*/
		this.serverConnection = serverConnection;
		workers = serverConnection.getWorkers();
	}
	
	public void actionChoose(JSONObject json) throws IOException, ParseException
	{
		switch (json.get("Action").toString()) {
		case "1":
			loginToApp(json);
			break;
		case "2":
			findWorker(json);
			break;
		case "3":
			updateWorkers(json);
			break;	
		default:
			break;
		}
	}
	
	public void loginToApp(JSONObject json) throws IOException, ParseException
	{
		 JSONArray workerDetails = new JSONArray();
		 JSONObject answer = new JSONObject();
		 workerDetails = (JSONArray) workers.get(json.get("personalID"));
		 if ((workerDetails != null) && (workerDetails.get(6).equals(json.get("password"))) && (Integer.parseInt(workerDetails.get(7).toString()) == 0))
		 {
			 answer.put("Status", 1);
			 answer.put("Shop", workerDetails.get(4));
			 answer.put("Job", workerDetails.get(5));
			 System.out.println("Send ok to client!");
			 workerDetails.set(7, 1); //Set user as logged in
			 workers.get(json.get("personalID"));
			 JSONParser parser = new JSONParser();
			 Object obj = parser.parse(new FileReader("./files/"+workerDetails.get(4)+".json"));
		     shop = (JSONObject) obj;
		     answer.putAll(shop);
		     logedInUserID = Integer.parseInt(json.get("personalID").toString());
		 }
		 else
		 {
			 answer.put("Status", "0");
			 System.out.println("Fail!");
		 }
		 sendToClient(answer);
	}
	
	public void findWorker(JSONObject json) throws IOException
	{
		if (workers.containsKey(json.get("personalID")))
		{
			JSONArray workerDetails = new JSONArray();
			workerDetails = (JSONArray) workers.get(json.get("personalID"));
			json.put("name", workerDetails.get(0));
			json.put("workerID", workerDetails.get(1));
			json.put("phoneNr", workerDetails.get(2));
			json.put("bankAcc", workerDetails.get(3));
			json.put("shop", workerDetails.get(4));
			json.put("job", workerDetails.get(5));
			json.put("password", workerDetails.get(6));
			json.put("login", workerDetails.get(7));
		}
		else
		{
			json.put("workersNumber",workers.size());
		}
		sendToClient(json);
	}
	
	public void updateWorkers(JSONObject json)
	{
		JSONArray workerDetails = new JSONArray();
		workerDetails.add(0, json.get("name"));
		workerDetails.add(1, json.get("workerID"));
		workerDetails.add(2, json.get("phoneNr"));
		workerDetails.add(3, json.get("bankAcc"));
		workerDetails.add(4, json.get("shop"));
		workerDetails.add(5, json.get("job"));
		workerDetails.add(6, json.get("password"));
		workerDetails.add(7, json.get("login"));
		if (workers.containsKey(json.get("personalID")))
		{//Update worker
			workers.replace(json.get("personalID"), workerDetails);
		}
		else { //new worker
			workers.put(json.get("personalID"), workerDetails);
		}
	}
	
	public void logout() {
		 JSONArray workerDetails = new JSONArray();
		 workerDetails = (JSONArray) workers.get(""+logedInUserID);
		 workerDetails.set(7, 0); //Set user as logged out 
		 workers.replace(logedInUserID, workerDetails);
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
			endOfWork();
			System.out.println("End.");
			//e.printStackTrace();
		}
	}
	
	public void endOfWork() {
		if (logedInUserID != 0)
			this.logout();
		System.out.println("All saved to files");
	}
	
}
