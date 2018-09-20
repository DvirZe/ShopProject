package clientSide;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import javax.net.ssl.SSLSocketFactory;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import chat.GetConnection;
import gui.Login;


public class ClientSideConnection extends Thread {
	private Socket socket;
	private BufferedReader socketBufferedReader;
	private PrintWriter printWriter;
	private Actions action;
	private Shop shop;
	public int nextWorkerCounter;
	private Map<String, String> workerOnline;
	private GetConnection getConnection;
	private Boolean freeToChat = true;
	
	public ClientSideConnection() {
		socket = null;
		socketBufferedReader = null;
		printWriter = null;
		action = new Actions();
	}
	
	@Override
	public void run()  { //every client connection get new thread
		System.setProperty("javax.net.ssl.trustStore", "sp.store"); //use certificate
		try {
			socket = ((SSLSocketFactory)SSLSocketFactory.getDefault()).createSocket("localhost", 7000); //open ssl connection with the server
			socketBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			printWriter = new PrintWriter(socket.getOutputStream(),true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Login(this); //open first screen - login
	}
	
	public int login(String id, String password) throws ParseException, IOException
	{
		JSONObject json = new JSONObject();
		json.put("Action",action.loginAction());
		json.put("personalID", id);
		json.put("password", password);
		
		try { //get random free port for chat
			ServerSocket newPort = new ServerSocket(0);
			int portForChat = newPort.getLocalPort()+2000;
			while (portForChat >= 65535) // make sure keep the port below the max port
			{
				portForChat-= 1500; 
			}
			getConnection = new GetConnection(portForChat, this); //Get new Random free port for chat
			json.put("portForChat", portForChat);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sendToServer(json);
		String msg = socketBufferedReader.readLine(); //wait for answer if user allow to enter
		JSONParser parser = new JSONParser();
		json = (JSONObject)parser.parse(msg);
		int status = Integer.parseInt(json.get("Status").toString());
		if (status == 1)
		{
			shop = new Shop(json);	
			workerOnline = new HashMap<String,String>();
			workerOnline.put("personalID", id);
			workerOnline.put("name", json.get("name").toString());
			workerOnline.put("Job", json.get("Job").toString());
			getConnection.start(); //start thread to wait for incoming chat requests
		}
		
		return Integer.parseInt(json.get("Status").toString());
	}
	
	public void sendToServer(JSONObject json)
	{
		printWriter.println(json);
	}
	
	public JSONObject getFromServer() throws IOException, ParseException
	{
		JSONObject json = new JSONObject();
		String msg = socketBufferedReader.readLine();
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
		sendToServer(json);
		json = getFromServer();
		if (json.containsKey("workersNumber"))
		{ //worker nor found
			nextWorkerCounter = (Integer.parseInt(json.get("workersNumber").toString()) + 1);
			return null;
		} //worker found
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
		sendToServer(json);
		json = getFromServer();
		if (!json.containsKey("name"))
		{ //customer not found
			return null;
		} //customer found
		return new Customer(id,
						  json.get("name").toString(),
						  json.get("phoneNr").toString(),
						  (json.get("customerType").toString()));
	}

	public int getNewWorkerID() {
		return nextWorkerCounter;
	}
	
	public void saveWorker(Worker worker) { //convert to json and send to server
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
		sendToServer(workerJson);
	}
	
	public void saveCustomer(Customer customer) { //convert to json and send to server
		JSONObject customerJson = new JSONObject();
		customerJson.put("Action", action.saveCustomerAction());
		customerJson.put("customerId", customer.getId());
		customerJson.put("name", customer.getName());
		customerJson.put("phoneNr", customer.getPhoneNr());
		customerJson.put("customerType", customer.getType());
		sendToServer(customerJson);
	}
	
	public void updateInventory() throws IOException, ParseException { //convert to json and send to server
		JSONObject json = new JSONObject();
		json.put("Action", action.updateInventory());
		sendToServer(json);
		JSONObject newInventory = getFromServer();
		shop.updateInventory(1, Integer.parseInt(newInventory.get("shirt1").toString()));
		shop.updateInventory(2, Integer.parseInt(newInventory.get("shirt2").toString()));
		shop.updateInventory(3, Integer.parseInt(newInventory.get("pants1").toString()));
		shop.updateInventory(4, Integer.parseInt(newInventory.get("pants2").toString()));
	}
	
	public void setDiscount(double vip, double returned) { //convert to json and send to server
		shop.setDiscount(vip, returned);
		JSONObject json = new JSONObject();
		json.put("Action", action.setDiscounts());
		json.put("VIP", vip);
		json.put("Return", returned);
		sendToServer(json);
	}

	public void updateDicounts() throws IOException, ParseException { //get updated discounts from server and update shop
		JSONObject json = new JSONObject();
		json.put("Action", action.updateDiscounts());
		sendToServer(json); //send request for update from the server
		JSONObject updatedDiscounts = getFromServer();
		shop.setDiscount(Double.parseDouble(updatedDiscounts.get("VIP").toString()), Double.parseDouble(updatedDiscounts.get("Return").toString()));
	}
	
	public void setPrices(int[] prices) { //convert to json and send to server
		JSONObject json = new JSONObject();
		json.put("Action", action.setPrices());
		JSONArray pricesArr = new JSONArray();
		for (int i = 0; i<4 ; ++i)
		{
			pricesArr.add(prices[i]);
		}
		json.put("newPrices", pricesArr);
		sendToServer(json);
	}
	
	public void updatePrices() throws IOException, ParseException { //get updated prices from server and update shop
		JSONObject json = new JSONObject();
		json.put("Action", action.updatePrices());
		sendToServer(json);
		JSONObject updatedPrices = getFromServer();
		JSONArray pricesJson = new JSONArray();
		pricesJson = (JSONArray) updatedPrices.get("Prices");
		int[] priceArr = new int[4];
		for (int i = 0; i < 4; ++i )
		{
			priceArr[i] = Integer.parseInt(pricesJson.get(i).toString());
		}
		shop.setPrices(priceArr);
	}
	
	public void saveSimpleSaveReport() throws IOException, ParseException { //request from server for general shop sells report 
		JSONObject json = new JSONObject();
		json.put("Action", action.sellsRepoet());
		sendToServer(json);
		JSONObject simpleReport = getFromServer();
		simpleReport.put("fileName", "SimpleReport");
		simpleReport.put("title", "Simple Sell Report");
		createWordFile(simpleReport);
	}

	public LinkedHashMap<String, String> getStats() throws IOException, ParseException { //get info from server about shop sells
		LinkedHashMap<String, String> stats = new LinkedHashMap<String, String>();
		JSONObject request = new JSONObject();
		request.put("Action", action.getStats());
		sendToServer(request);
		JSONObject answer = getFromServer();
		stats.put("TotalSelles", answer.get("TotalSelles").toString());
		JSONArray sellesHistory = (JSONArray) answer.get("sellesHistory");
		stats.put("Shirt 1",sellesHistory.get(0).toString());
		stats.put("Shirt 2",sellesHistory.get(1).toString());
		stats.put("Pants 1",sellesHistory.get(2).toString());
		stats.put("Pants 2",sellesHistory.get(3).toString());
		return stats;
	}
	
	public void saveReportByItem(String item) throws IOException, ParseException { //request from server for item sells records
		JSONObject json = new JSONObject();
		json.put("Action", action.sellsRepoet());
		json.put("Item", item);
		sendToServer(json);
		JSONObject Report = getFromServer();
		Report.put("title", item + " Sell Report");
		item = item.replace(" ", "_");
		Report.put("fileName", item +"_Report");
		createWordFile(Report);
	}
	
	public void saveReportByType(String type) throws IOException, ParseException { //request from server for sells records by type
		JSONObject json = new JSONObject();
		json.put("Action", action.sellsRepoet());
		json.put("Type", type);
		sendToServer(json);
		JSONObject Report = getFromServer();
		Report.put("title", type + " Sell Report");
		Report.put("fileName", type +"_Report");
		createWordFile(Report);
	}
	
	public void saveReportByDate(String date) throws IOException, ParseException { //request from server for sells records by date
		JSONObject json = new JSONObject();
		json.put("Action", action.sellsRepoet());
		json.put("Date", date);
		sendToServer(json);
		JSONObject Report = getFromServer();
		Report.put("title", date + " Sell Report");
		Report.put("fileName", date +"_Report");
		createWordFile(Report);
	}
	
	public void createWordFile(JSONObject json) throws IOException { //create doc report file
		String fileName = json.get("fileName").toString() + "_" + new Random().nextInt(Integer.MAX_VALUE); //create file name with random value
		json.remove("fileName");
		XWPFDocument document = new XWPFDocument(); 
		FileOutputStream out = new FileOutputStream( new File(".\\"+ fileName +".docx")); //create doc file
		
	    XWPFParagraph title = document.createParagraph();
	    XWPFRun titleText = title.createRun();
	    
	    titleText.setText(json.get("title").toString());
	    titleText.setBold(true);
	    title.setAlignment(ParagraphAlignment.CENTER);
	    titleText.addBreak();
	    titleText.setUnderline(UnderlinePatterns.SINGLE);
	    titleText.setFontSize(20);
	    
		XWPFParagraph paragraph = document.createParagraph();
		XWPFRun run = paragraph.createRun();	    
	    
	    paragraph.setAlignment(ParagraphAlignment.RIGHT);

	    for (int i = 1 ; i< json.size() ; ++i) //add every sell record to the file
	    {
	    	 run.setText("#"+ i + " - " + json.get(""+i).toString());
	    	 run.addBreak();
	    }
	    
	    document.write(out);
	    out.close();
	    
	    Desktop.getDesktop().open(new File(".\\"+ fileName + ".docx")); //open the file after created
	}
	
	public void endSell() { //send update for server for record and update inventory
		JSONObject sell = shop.endSell();
		sell.put("Action", action.sellAction());
		sendToServer(sell);
	}
	
	public String startChat() throws IOException, ParseException { //Sent request for server to get port of user to chat connection
		JSONObject json = new JSONObject();
		json.put("Action", action.startChat());
		sendToServer(json);
		JSONObject details = getFromServer();
		if (details.containsKey("User2Port")) {
			return details.get("User2Port").toString(); //return port to open connection
		} else return "0";
		
	}
	
	public String joinChat() throws IOException, ParseException { //Sent request for server to get port of host to chat connection
		JSONObject json = new JSONObject();
		json.put("Action", action.joinChat());
		sendToServer(json);
		JSONObject details = getFromServer();
		if (details.containsKey("User2Port")) { 
			return details.get("User2Port").toString(); //return port to open connection		
		} else return "0";
		
	}
	
	public Map<String, String> getWorkerOnline() {
		return workerOnline;
	}
	
	public GetConnection getConnection() {
		return getConnection;
	}
	
	public void freeToChatStatusChange(Boolean changeTo) {
		freeToChat = changeTo;
	}
	
	public Boolean isFreeToChat() {
		return freeToChat;
	}

	public void setInventory(JSONObject json) { //send updated inventory to server after supply update
		json.put("Action", action.setInventory());
		json.put("shopName", shop.getShopName());
		sendToServer(json);
	}
	
	public void leftTheChat() { //update char room at server
		JSONObject json = new JSONObject();
		json.put("Action", action.leftTheChat());
		sendToServer(json);
		freeToChat = true;
	}
	
	public int managerPortToJoinChat() throws IOException, ParseException { //send request to server to find if the port is port of a manager
		JSONObject json = new JSONObject();
		json.put("Action", action.managerPortToJoinChat());
		json.put("myPort", getConnection.getPort());
		sendToServer(json);
		JSONObject details = getFromServer();
		if (details.containsKey("portToCheck")) {
			return Integer.parseInt(details.get("portToCheck").toString());
			
		} else return 0;
	}
	
	public void updateJoinChatListAfterOpenSocket(int hostPort, int myPort) { //update record on join waiting list on server after open connection
		JSONObject json = new JSONObject();
		json.put("Action", action.updateJoinChatListAfterOpenSocket());
		json.put("hostPort", hostPort);
		json.put("myPort", myPort);
		sendToServer(json);
	}
	
	public void saveChatLog(String log) { //send logs to server to save
		JSONObject json = new JSONObject();
		json.put("Action", action.saveChatLog());
		json.put("chatAction", "Save");
		json.put("chatLog", log);
		sendToServer(json);
	}
	
	public boolean isManager() { //for testing permissions to screens and buttons
		if (workerOnline.get("Job").equals("Manager"))
			return true;
		else
			return false;
	}
	
	public boolean isSeller() { //for testing permissions to screens and buttons
		if (workerOnline.get("Job").equals("Seller"))
			return true;
		else
			return false;
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException
	{
		ClientSideConnection clientSideConnection = new ClientSideConnection();
		clientSideConnection.start(); //start new tread for each running
	}
	
}

