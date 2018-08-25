package serverSide;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.io.Reader;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class serverClass extends Thread {
	private Socket socket;
	
	public serverClass() {}
	
	public serverClass(Socket socket) {
		this.socket = socket;
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
		System.out.println(json.get("username").toString()); //Just a test, not log in function
		try (FileWriter file = new FileWriter("./files/users.json", true)) {
			file.write(json.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + json);
		}
	}
	
	public void run() {
		//JSONObject json = null;
		//PrintWriter printWriter;
		BufferedReader bufferedReader;
		JSONParser parser = new JSONParser();
			try {
				//printWriter = new PrintWriter(socket.getOutputStream(),true);
				bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String msg = bufferedReader.readLine();
				while(true) {
					if (!msg.equals(""))
						{
							JSONObject json = (JSONObject)parser.parse(msg);
							actionChoose(json);
							System.out.println(json.get("password").toString());
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
