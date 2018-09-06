package serverSide;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class serverConnection {
	
	JSONObject workers;
	
	private serverConnection() throws IOException, ParseException {
		System.setProperty("javax.net.ssl.keyStore", "sp.store");
		System.setProperty("javax.net.ssl.keyStorePassword", "password");
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("./files/Workers.json"));
		workers = (JSONObject) obj;
	}
	
	public void saveData(serverClass sc) throws IOException {
		synchronized(sc)
		{
			FileWriter workersFile = new FileWriter("./files/Workers.json",false);
			workersFile.write(workers.toString());
			workersFile.close();
			System.out.println("Files Saved.");
		}
	}
	
	public JSONObject getWorkers() {
		return workers;
	}
	
	public static void main(String[] args) throws IOException, ParseException  {
			serverConnection serverConnection = new serverConnection();
			ServerSocket serverSocket = SSLServerSocketFactory.getDefault().createServerSocket(7000);
			System.out.println("Ready for connaction...");
			while (true) new serverClass(serverSocket.accept(), serverConnection).start();
	}
}
