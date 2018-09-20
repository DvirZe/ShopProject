package serverSide;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.common.base.Functions;

import chat.ChatRoom;
import chat.ChatUser;
import chat.User;

public class serverClass extends Thread {
	private Socket socket;
	private JSONObject workers, shop, customers;
	private serverConnection serverConnection;
	private int logedInUserID;
	private Logs logs;
	private ArrayList<ChatRoom> rooms;
		
	public serverClass(Socket socket, serverConnection serverConnection) throws FileNotFoundException, IOException, ParseException {
		this.socket = socket;
		this.serverConnection = serverConnection;
		workers = serverConnection.getWorkers();
		customers = serverConnection.getCustomers();
		logs = new Logs();
		rooms = serverConnection.getRooms();
	}
	
	public void actionChoose(JSONObject json) throws IOException, ParseException
	{ //Action menu for client requests
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
		case "15":
			updateInventory(json);
			break;
		case "16":
			workerLeftChat();
			break;
		case "17":
			findChatToJoin();
			break;
		case "18":
			joinManagerToChat(json);
			break;
		case "19":
			updateJoinChatList(json);
			break;
		case "20":
			saveLogChat(json);
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
		 { //if user exist, not online already and input the right password.
			 answer.put("Status", 1);
			 answer.put("name",workerDetails.get(0));
			 answer.put("Shop", workerDetails.get(4));
			 answer.put("Job", workerDetails.get(5));
			 System.out.println("Send ok to client!");
			 workerDetails.set(7, 1); //Set user as logged in
			 workerDetails.set(8, json.get("portForChat")); //Save connection port
			 workers.get(json.get("personalID"));
			 JSONParser parser = new JSONParser();
			 String shopName = workerDetails.get(4).toString();
			 if (shopName.equals("Shop1")) //load the correct shop for the logged in user
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
		 sendToClient(answer); //Send answer to client
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
			JSONArray oldDetails = (JSONArray) workers.get(json.get("personalID"));
			workerDetails.add(8, oldDetails.get(8));
			workerDetails.add(9, oldDetails.get(9));
			workers.replace(json.get("personalID"), workerDetails);
			json.put("Status", "Update");
		}
		else { //new worker
			workerDetails.add(8, 0); //workerPort
			workerDetails.add(9, 0); //is free to chat
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
	
	public void updateInventory(JSONObject json) {
		JSONArray updatedInventory = new JSONArray();
		updatedInventory.add(json.get("shirt1").toString());
		updatedInventory.add(json.get("shirt2").toString());
		updatedInventory.add(json.get("pants1").toString());
		updatedInventory.add(json.get("pants2").toString());
		shop.replace("Inventory", updatedInventory);
		try {
			logs.updateInventoryLog(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	public synchronized void chatLookup() throws IOException {
		Set<String> keys = workers.keySet();
		ArrayList<String> workersToCheck = new ArrayList<String>();
		JSONObject answer = new JSONObject();
		JSONArray loginWorker = (JSONArray) workers.get(""+logedInUserID);
		String sentToWorker = null;
		
		for  (String id : keys) { //find relevant users to chat with
			JSONArray worker = (JSONArray) workers.get(id);
			System.out.println(worker);
			if ((Integer.parseInt(worker.get(7).toString()) == 1) //Worker is online
					&& !(worker.get(4).equals(loginWorker.get(4)))) //Different shop 
			{
				workersToCheck.add(id);
			}
		}
		System.out.println("workersToCheck.size()" + workersToCheck.size());
		if (workersToCheck.size() == 0)
		{
			answer.put("notFound", 1);
		} else {
			sentToWorker = workersToCheck.remove(0);
			if (Integer.parseInt(((JSONArray)workers.get(sentToWorker)).get(9).toString()) != 0) //if not free to chat
			{
				for (String workeriD : workersToCheck)
				{
					if (Integer.parseInt(((JSONArray)workers.get(workeriD)).get(9).toString()) == 0)
					{
						sentToWorker = workeriD;
					}
				}
			}
		}
		
		if (sentToWorker != null) {
			JSONArray workerForChat = (JSONArray) workers.get(sentToWorker);
			answer.put("User2Port", workerForChat.get(8));
			ChatRoom room = new ChatRoom();
			User tryUser = new User(""+logedInUserID,loginWorker.get(0).toString(), loginWorker.get(8).toString());
			User connectUser = new User(sentToWorker,workerForChat.get(0).toString(), workerForChat.get(8).toString());
			connectUser.setIsHost(true);
			room.addUser(tryUser);
			room.addUser(connectUser);
			rooms.add(room);
			JSONObject toLog = new JSONObject();
			toLog.put("Action", "Open");
			toLog.put("openUser", loginWorker.get(0));
			toLog.put("with",workerForChat.get(0));
			logs.chatConncetion(toLog);
			////////////////////////////////////////////////////
			//Set the 2 members of the chat as "Not free to chat"
			workerForChat.set(9, 1);
			loginWorker.set(9, 1);
			workers.replace(sentToWorker, workerForChat);
			workers.replace(logedInUserID, loginWorker);
			///////////////////////////////////////////////////
		}
		sendToClient(answer);
	}
	
	public void findChatToJoin() throws IOException {
		JSONObject answer = new JSONObject();
		if (!rooms.isEmpty())
		{
			for (ChatRoom room : rooms)
			{
				if (room.isHostStillOnChat() && room.getChatUsers().size() == 2)
				{
					ArrayList<ChatUser> users = room.getChatUsers();
					JSONArray hostUser = (JSONArray) workers.get(""+users.get(1).getId());
					answer.put("User2Port", hostUser.get(8).toString());
					JSONArray loginWorker = (JSONArray) workers.get(""+logedInUserID);
					User joinUser = new User(""+logedInUserID,loginWorker.get(0).toString(), loginWorker.get(8).toString());
					room.addUser(joinUser);
					////////////////////////////////////////////////////
					//Set the member as "Not free to chat"
					loginWorker.set(9, 1);
					workers.replace(logedInUserID, loginWorker);
					////////////////////////////////////////////////////
					
					JSONObject toLog = new JSONObject();
					toLog.put("openUser", loginWorker.get(0));
					toLog.put("with",users.get(0).getUsername() + " and " + users.get(1).getUsername());
					logs.chatConncetion(toLog);
					
				}
				else answer.put("notFound", 1);
			}

		}
		else {
			answer.put("notFound", 1);
		}
		sendToClient(answer);
	}
	
	public void updateJoinChatList(JSONObject json) {
		serverConnection.getJoinChatList().put(json.get("hostPort").toString(), json.get("myPort").toString());
	}
	
	public void joinManagerToChat(JSONObject json) throws IOException {
		JSONObject answer = new JSONObject();
		if (serverConnection.getJoinChatList().containsKey(json.get("myPort").toString()))
		{
			answer.put("portToCheck", serverConnection.getJoinChatList().get(json.get("myPort").toString()));
			serverConnection.getJoinChatList().remove(json.get("myPort").toString());
		}
		else answer.put("notFound", 1);
		sendToClient(answer);
	}
	
	public synchronized void workerLeftChat()
	{
		for (int i = 0; i< rooms.size() ; ++i)
		{
			if (rooms.get(i).isThisUserInChat(logedInUserID))
			{
				int userWhoLeftPlace = rooms.get(i).getPlaceOfUser(logedInUserID);
				ChatUser user = rooms.get(i).getChatUsers().remove(userWhoLeftPlace);
				if (rooms.get(i).getChatUsers().isEmpty())
				{
					rooms.remove(i);
				}
				else {
					rooms.get(i).addLeftUser(user);
				}
				break;
			}
		}
		////////////////////////////////////////////////////
		//Set the member as "free to chat"
		JSONArray loginWorker = (JSONArray) workers.get(""+logedInUserID);
		loginWorker.set(9, 0);
		workers.replace(logedInUserID, loginWorker);
		////////////////////////////////////////////////////
	}
	
	public void saveLogChat(JSONObject log) throws IOException {
		logs.chatConncetion(log);
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
				e1.printStackTrace();
			}
			System.out.println("End.");
		}
	}
	
	public void logout() throws IOException {
		 JSONArray workerDetails = new JSONArray();
		 workerDetails = (JSONArray) workers.get(""+logedInUserID);
		 workerDetails.set(7, 0); //Set user as logged out 
		 workerDetails.set(8, 0); //Set user port as 0 (offline)
		 workerDetails.set(9, 0); //Set user chat as free for next time
		 JSONObject logout = new JSONObject();
		 logout.put("shopName", workerDetails.get(4));
		 logout.put("personalID", logedInUserID);
		 logs.logoutsLog(logout);
		 workers.replace(logedInUserID, workerDetails);
	}
	
	public void endOfWork() throws IOException {
		if (logedInUserID != 0)
			this.logout();
		serverConnection.saveData(this);
	}
	
}
