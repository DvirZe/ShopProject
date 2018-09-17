package serverSide;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Set;

import javax.net.ssl.SSLServerSocketFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import chat.ChatRoom;

public class serverConnection {
	
	JSONObject workers, customers, shop1, shop2;
	ArrayList<serverClass> connections;
	private ArrayList<ChatRoom> rooms;
	
	private serverConnection() throws IOException, ParseException {
		System.setProperty("javax.net.ssl.keyStore", "sp.store");
		System.setProperty("javax.net.ssl.keyStorePassword", "password");
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("./files/Workers.json"));
		workers = (JSONObject) obj;
		obj = parser.parse(new FileReader("./files/Customers.json"));
		customers = (JSONObject) obj;
		obj = parser.parse(new FileReader("./files/shop1.json"));
		shop1 = (JSONObject) obj;
		obj = parser.parse(new FileReader("./files/shop2.json"));
		shop2 = (JSONObject) obj;
		connections = new ArrayList<serverClass>();
		rooms = new ArrayList<ChatRoom>();
	}
	
	public void saveData(serverClass sc) throws IOException {
		synchronized(sc)
		{
			FileWriter workersFile = new FileWriter("./files/Workers.json",false);
			workersFile.write(workers.toString());
			workersFile.close();
			FileWriter customersFile = new FileWriter("./files/Customers.json",false);
			customersFile.write(customers.toString());
			customersFile.close();
			FileWriter shop = new FileWriter("./files/shop1.json",false);
			shop.write(shop1.toString());
			shop.close();
			shop = new FileWriter("./files/shop2.json",false);
			shop.write(shop2.toString());
			shop.close();
			System.out.println("Files Saved.");
		}
	}
	
	public ArrayList<serverClass> getConnections() {
		return connections;
	}
	
	public ArrayList<ChatRoom> getRooms() {
		return rooms;
	}
	
	public JSONObject getWorkers() {
		return workers;
	}
	
	public JSONObject getCustomers() {
		return customers;
	}
	
	public JSONObject getShop1() {
		return shop1;
	}
	
	public JSONObject getShop2() {
		return shop2;
	}
	
	public static void main(String[] args) throws IOException, ParseException  {
		serverConnection serverConnection = new serverConnection();
		ServerSocket serverSocket = SSLServerSocketFactory.getDefault().createServerSocket(7000);
		System.out.println("Ready for connaction...");
		while (true) new serverClass(serverSocket.accept(), serverConnection).start();
	}
}
