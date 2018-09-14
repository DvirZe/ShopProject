package serverSide;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JComboBox.KeySelectionManager;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class serverClass extends Thread {
	private Socket socket;
	private JSONObject workers, shop, customers;
	private serverConnection serverConnection;
	private int logedInUserID;
	private Logs logs;
	
	public serverClass() {}
	
	public serverClass(Socket socket, serverConnection serverConnection) throws FileNotFoundException, IOException, ParseException {
		this.socket = socket;
		this.serverConnection = serverConnection;
		workers = serverConnection.getWorkers();
		customers = serverConnection.getCustomers();
		logs = new Logs();
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
			updateWorker(json);
			break;	
		case "4":
			findCustomer(json);
			break;	
		case "5":
			updateCustomer(json);
			break;	
		case "6":
			saveSell(json);
			break;	
		case "7":
			sendInventory();
			break;	
		case "8":
			updateDiscounts(json);
			break;	
		case "9":
			sendDiscounts();
			break;
		case "10":
			updatePrices(json);
			break;	
		case "11":
			sendPrices();
			break;	
		case "12":
			sendSellsRepoet(json);
			break;
		case "13":
			chatLookup();
			break;
		case "14":
			sendStats();
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
		 answer.put("personalID", json.get("personalID"));
		 if ((workerDetails != null) && (workerDetails.get(6).equals(json.get("password"))) && (Integer.parseInt(workerDetails.get(7).toString()) == 0))
		 {
			 answer.put("Status", 1);
			 answer.put("Shop", workerDetails.get(4));
			 answer.put("Job", workerDetails.get(5));
			 System.out.println("Send ok to client!");
			 workerDetails.set(7, 1); //Set user as logged in
			 workerDetails.set(8, json.get("portForChat")); //Save connection port
			 workers.get(json.get("personalID"));
			 JSONParser parser = new JSONParser();
			 String shopName = workerDetails.get(4).toString();
			 if (shopName.equals("Shop1"))
				 shop = serverConnection.getShop1();
			 else
				 shop = serverConnection.getShop2();
		     answer.putAll(shop);
		     logedInUserID = Integer.parseInt(json.get("personalID").toString());
		 }
		 else
		 {
			 answer.put("Status", "0");
			 System.out.println("Fail!");
		 }
		 sendToClient(answer);
		 logs.loginsLog(answer);
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
	
	public void findCustomer(JSONObject json) throws IOException
	{
		if (customers.containsKey(json.get("customerId")))
		{
			JSONArray customersDetails = new JSONArray();
			customersDetails = (JSONArray) customers.get(json.get("customerId"));
			json.put("name", customersDetails.get(0));
			json.put("phoneNr", customersDetails.get(1));
			json.put("customerType", customersDetails.get(2));
		}
		else
		{
			json.put("customerId","not found");
		}
		sendToClient(json);
	}
	
	public void updateWorker(JSONObject json) throws IOException
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
			json.put("Status", "Update");
		}
		else { //new worker
			workers.put(json.get("personalID"), workerDetails);
			json.put("Status", "Create");
		}
		logs.workersLog(json);
	}
	
	public void updateCustomer(JSONObject json) throws IOException
	{
		JSONArray customerDetails = new JSONArray();
		customerDetails.add(0, json.get("name"));
		customerDetails.add(1, json.get("phoneNr"));
		customerDetails.add(2, json.get("customerType"));
		if (customers.containsKey(json.get("customerId")))
		{//Update customer
			customers.replace(json.get("customerId"), customerDetails);
			json.replace("Action", "Update");
		}
		else { //new customer
			customers.put(json.get("customerId"), customerDetails);
			json.replace("Action", "Create");
		}
		json.put("Worker", logedInUserID);
		logs.clientsLog(json);
	}
	
	public synchronized void saveSell(JSONObject json) throws IOException {
		JSONArray newInventory = new JSONArray();	
		JSONArray curInventory = (JSONArray) shop.get("Inventory");
		newInventory.add(Integer.parseInt(curInventory.get(0).toString())-Integer.parseInt(json.get("shirt1").toString()));
		newInventory.add(Integer.parseInt(curInventory.get(1).toString())-Integer.parseInt(json.get("shirt2").toString()));
		newInventory.add(Integer.parseInt(curInventory.get(2).toString())-Integer.parseInt(json.get("pants1").toString()));
		newInventory.add(Integer.parseInt(curInventory.get(3).toString())-Integer.parseInt(json.get("pants2").toString()));
		shop.replace("Inventory", newInventory);
		curInventory = newInventory;
		logs.selesLog(json);
		shop.replace("TotalSelles", Integer.parseInt(shop.get("TotalSelles").toString())+1);
		JSONArray sellesHistoryTmp =  (JSONArray) shop.get("sellesHistory");
		sellesHistoryTmp.set(0, Integer.parseInt(sellesHistoryTmp.get(0).toString()) + Integer.parseInt(json.get("shirt1").toString()));
		sellesHistoryTmp.set(1, Integer.parseInt(sellesHistoryTmp.get(1).toString()) + Integer.parseInt(json.get("shirt2").toString()));
		sellesHistoryTmp.set(2, Integer.parseInt(sellesHistoryTmp.get(2).toString()) + Integer.parseInt(json.get("pants1").toString()));
		sellesHistoryTmp.set(3, Integer.parseInt(sellesHistoryTmp.get(3).toString()) + Integer.parseInt(json.get("pants2").toString()));
		shop.replace("sellesHistory",sellesHistoryTmp);
		JSONArray coustomer = (JSONArray) customers.get(json.get("customerId"));
		if (coustomer != null && coustomer.get(2).equals("New")) //Change coustomer Type from new to return after the first buy
		{
			coustomer.set(2, "Return");
			customers.replace("customerId", coustomer);
		}
	}

	public void sendInventory() throws IOException {	
		JSONObject json = new JSONObject();
		JSONArray curInventory = (JSONArray) shop.get("Inventory");
		json.put("shirt1", curInventory.get(0));
		json.put("shirt2", curInventory.get(1));
		json.put("pants1", curInventory.get(2));
		json.put("pants2", curInventory.get(3));
		sendToClient(json);
	}
	
	public void updateDiscounts(JSONObject json)
	{
		shop.replace("customerTypeReturn", json.get("Return"));
		shop.replace("customerTypeVip", json.get("VIP"));
	}
	
	public void sendDiscounts() throws IOException
	{
		JSONObject discounts = new JSONObject();
		discounts.put("VIP", shop.get("customerTypeVip"));
		discounts.put("Return", shop.get("customerTypeReturn"));
		sendToClient(discounts);
	}
	
	public void updatePrices(JSONObject json)
	{
		shop.replace("Price", json.get("newPrices"));
	}
	
	public void sendPrices() throws IOException
	{
		JSONObject prices = new JSONObject();
		prices.put("Prices", shop.get("Price"));
		sendToClient(prices);
	}
	
	public void sendStats() throws IOException
	{
		JSONObject stats = new JSONObject();
		stats.put("TotalSelles", shop.get("TotalSelles").toString());
		JSONArray sellesHistory = (JSONArray)shop.get("sellesHistory");
		stats.put("sellesHistory",sellesHistory);
		sendToClient(stats);
	}
	
	public void logout() throws IOException {
		 JSONArray workerDetails = new JSONArray();
		 workerDetails = (JSONArray) workers.get(""+logedInUserID);
		 workerDetails.set(7, 0); //Set user as logged out 
		 workerDetails.set(8, 0); //Set user port as 0 (offline)
		 JSONObject logout = new JSONObject();
		 logout.put("shopName", workerDetails.get(4));
		 logout.put("personalID", logedInUserID);
		 logs.logoutsLog(logout);
		 workers.replace(logedInUserID, workerDetails);
	}

	public void sendToClient(JSONObject json) throws IOException
	{
		PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
		printWriter.println(json);
	}
	
	public void sendSellsRepoet(JSONObject json) throws FileNotFoundException, IOException, ParseException
	{
		GenerateReports gr = new GenerateReports();
		gr.createSellReport(this, shop.get("shopName").toString(), json);
	}
	
	public void chatLookup() throws IOException {
		Set<String> keys = workers.keySet();
		JSONObject answer = new JSONObject();
		JSONArray loginWorker = (JSONArray) workers.get(""+logedInUserID);
		for (String id : keys) {
			JSONArray worker = (JSONArray) workers.get(id);
			if ((Integer.parseInt(worker.get(7).toString()) == 1) && (Integer.parseInt(worker.get(9).toString()) == 0) && !(worker.equals(loginWorker)) && !(worker.get(4).equals(loginWorker.get(4)))) {
				answer.put("User2Port", worker.get(8));
				System.out.println(worker);
				System.out.println(loginWorker);
			} else answer.put("notFound", 1);
		}
		sendToClient(answer);
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
			try {
				endOfWork();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("End.");
			//e.printStackTrace();
		}
	}
	
	public void endOfWork() throws IOException {
		if (logedInUserID != 0)
			this.logout();
		serverConnection.saveData(this);
	}
	
}
