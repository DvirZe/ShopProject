package clientSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocketFactory;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import gui.Login;


public class ClientSideConnection extends Thread {
	private Socket socket;
	private BufferedReader socketBufferedReader;
	private PrintWriter printWriter;
	@SuppressWarnings("unused")
	private BufferedReader commandBufferedReader;
	private Actions action;
	@SuppressWarnings("unused")
	private Login shopGui;
	private Shop shop;
	public int NextWorkerCounter;
	
	public ClientSideConnection() {
		socket = null;
		socketBufferedReader = null;
		printWriter = null;
		commandBufferedReader = null;
		action = new Actions();
	}
	
	@Override
	public void run()  {
		System.setProperty("javax.net.ssl.trustStore", "sp.store");
		try {
			socket = ((SSLSocketFactory)SSLSocketFactory.getDefault()).createSocket("localhost", 7000);
			socketBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			printWriter = new PrintWriter(socket.getOutputStream(),true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		commandBufferedReader = new BufferedReader(new InputStreamReader(System.in));
		shopGui = new Login(this);
	}
	
	@SuppressWarnings("unchecked")
	public int login(String id, String password) throws ParseException, IOException
	{
		JSONObject json = new JSONObject();
		json.put("Action",action.loginAction());
		json.put("personalID", id);
		json.put("password", password);
		SendToServer(json);
		String msg = socketBufferedReader.readLine();
		//System.out.println(msg);
		JSONParser parser = new JSONParser();
		json = (JSONObject)parser.parse(msg);
		int status = Integer.parseInt(json.get("Status").toString());
		if (status == 1)
			shop = new Shop(json);
		return Integer.parseInt(json.get("Status").toString());
	}
	
	public void SendToServer(JSONObject json)
	{
		printWriter.println(json);
	}
	
	public JSONObject getFromServer() throws IOException, ParseException
	{
		JSONObject json = new JSONObject();
		String msg = socketBufferedReader.readLine();
		//System.out.println(msg);
		JSONParser parser = new JSONParser();
		json = (JSONObject)parser.parse(msg);
		return json;
	}
	
	public Shop getShop() {
		return shop;
	}

	public Worker findWorker(String id) throws IOException, ParseException
	{
		JSONObject json = new JSONObject();
		json.put("personalID", id);
		json.put("Action",action.findWorkerAction());
		SendToServer(json);
		json = getFromServer();
		if (json.containsKey("workersNumber"))
		{
			NextWorkerCounter = (Integer.parseInt(json.get("workersNumber").toString()) + 1);
			return null;
		}
		return new Worker(json.get("workerID").toString(),
						  Integer.parseInt(id),
						  json.get("name").toString(),
						  json.get("phoneNr").toString(),
						  json.get("bankAcc").toString(),
					   	  json.get("shop").toString(),
						  json.get("job").toString(),
						  json.get("password").toString());
	}
	
	public Customer findCustomer(String id) throws IOException, ParseException
	{
		JSONObject json = new JSONObject();
		json.put("customerId", id);
		json.put("Action",action.findCustomerAction());
		SendToServer(json);
		json = getFromServer();
		if (!json.containsKey("name"))
		{
			return null;
		}
		System.out.println(json);
		return new Customer(id,
						  json.get("name").toString(),
						  json.get("phoneNr").toString(),
						  (json.get("customerType").toString()));
	}

	public int getNewWorkerID() {
		return NextWorkerCounter;
	}
	
	public void saveWorker(Worker worker) {
		JSONObject workerJson = new JSONObject();
		workerJson.put("Action", action.saveWorkerAction());
		workerJson.put("personalID", worker.getId());
		workerJson.put("workerID", worker.getWorkerId());
		workerJson.put("name", worker.getName());
		workerJson.put("bankAcc", worker.getBankAcc());
		workerJson.put("phoneNr", worker.getPhoneNr());
		workerJson.put("shop", worker.getShopName());
		workerJson.put("job", worker.getJob());
		workerJson.put("password", worker.getPassword());
		workerJson.put("login", worker.getLoginStatus());
		System.out.println(workerJson);
		SendToServer(workerJson);
	}
	
	public void saveCustomer(Customer customer) {
		JSONObject customerJson = new JSONObject();
		customerJson.put("Action", action.saveCustomerAction());
		customerJson.put("customerId", customer.getId());
		customerJson.put("name", customer.getName());
		customerJson.put("phoneNr", customer.getPhoneNr());
		customerJson.put("customerType", customer.getType());
		System.out.println(customerJson);
		SendToServer(customerJson);
	}
	
	public void endSell() { 
		JSONObject sell = shop.endSell();
		sell.put("Action", action.sellAction());
		System.out.println(sell);
		SendToServer(sell);
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException
	{
		ClientSideConnection clientSideConnection = new ClientSideConnection();
		clientSideConnection.start();
	}
}
