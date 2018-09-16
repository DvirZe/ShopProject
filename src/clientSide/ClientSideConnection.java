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
	private BufferedReader commandBufferedReader;
	private Actions action;
	private Login shopGui;
	private Shop shop;
	public int NextWorkerCounter;
	private Map<String, String> workerOnline;
	private Socket chatSocket = null;
	private GetConnection getConnection;
	private Boolean freeToChat = true;
	
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
		
		try {
			ServerSocket newPort = new ServerSocket(0);
			int portForChat = newPort.getLocalPort()+2000;
			while (portForChat >= 65535) // make sure keep the port below the max port
			{
				portForChat-= 1500; 
			}
			getConnection = new GetConnection(portForChat, this); //Get new Random free port for chat
			getConnection.start();
			json.put("portForChat", portForChat);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SendToServer(json);
		String msg = socketBufferedReader.readLine();
		JSONParser parser = new JSONParser();
		json = (JSONObject)parser.parse(msg);
		int status = Integer.parseInt(json.get("Status").toString());
		if (status == 1)
		{
			shop = new Shop(json);	
			workerOnline = new HashMap<String,String>();
			workerOnline.put("personalID", id);
			workerOnline.put("name", json.get("name").toString());
		}
		
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
		SendToServer(workerJson);
	}
	
	public void saveCustomer(Customer customer) {
		JSONObject customerJson = new JSONObject();
		customerJson.put("Action", action.saveCustomerAction());
		customerJson.put("customerId", customer.getId());
		customerJson.put("name", customer.getName());
		customerJson.put("phoneNr", customer.getPhoneNr());
		customerJson.put("customerType", customer.getType());
		SendToServer(customerJson);
	}
	
	public void updateInventory() throws IOException, ParseException {
		JSONObject json = new JSONObject();
		json.put("Action", action.updateInventory());
		SendToServer(json);
		JSONObject newInventory = getFromServer();
		shop.UpdateInventory(1, Integer.parseInt(newInventory.get("shirt1").toString()));
		shop.UpdateInventory(2, Integer.parseInt(newInventory.get("shirt2").toString()));
		shop.UpdateInventory(3, Integer.parseInt(newInventory.get("pants1").toString()));
		shop.UpdateInventory(4, Integer.parseInt(newInventory.get("pants2").toString()));
	}
	
	public void setDiscount(double vip, double returned)
	{
		shop.setDiscount(vip, returned);
		JSONObject json = new JSONObject();
		json.put("Action", action.setDiscounts());
		json.put("VIP", vip);
		json.put("Return", returned);
		SendToServer(json);
	}
	
	public void updateDicounts() throws IOException, ParseException
	{
		JSONObject json = new JSONObject();
		json.put("Action", action.updateDiscounts());
		SendToServer(json);
		JSONObject updatedDiscounts = getFromServer();
		shop.setDiscount(Double.parseDouble(updatedDiscounts.get("VIP").toString()), Double.parseDouble(updatedDiscounts.get("Return").toString()));
	}
	
	public void setPrices(int[] prices)
	{
		JSONObject json = new JSONObject();
		json.put("Action", action.setPrices());
		JSONArray pricesArr = new JSONArray();
		for (int i = 0; i<4 ; ++i)
		{
			pricesArr.add(prices[i]);
		}
		json.put("newPrices", pricesArr);
		SendToServer(json);
	}
	
	public void updatePrices() throws IOException, ParseException
	{
		JSONObject json = new JSONObject();
		json.put("Action", action.updatePrices());
		SendToServer(json);
		JSONObject updatedPrices = getFromServer();
		JSONArray tmpJsonArr = new JSONArray();
		tmpJsonArr = (JSONArray) updatedPrices.get("Prices");
		int[] tmpPriceArr = new int[4];
		for (int i = 0; i < 4; ++i )
		{
			tmpPriceArr[i] = Integer.parseInt(tmpJsonArr.get(i).toString());
		}
		shop.setPrices(tmpPriceArr);
	}
	
	public void saveSimpleSaveReport() throws IOException, ParseException
	{
		JSONObject json = new JSONObject();
		json.put("Action", action.sellsRepoet());
		SendToServer(json);
		JSONObject simpleReport = getFromServer();
		simpleReport.put("fileName", "SimpleReport");
		simpleReport.put("title", "Simple Sell Report");
		createWordFile(simpleReport);
	}

	public LinkedHashMap<String, String> getStats() throws IOException, ParseException {
		LinkedHashMap<String, String> stats = new LinkedHashMap<String, String>();
		JSONObject request = new JSONObject();
		request.put("Action", action.getStats());
		SendToServer(request);
		JSONObject answer = getFromServer();
		stats.put("TotalSelles", answer.get("TotalSelles").toString());
		JSONArray sellesHistory = (JSONArray) answer.get("sellesHistory");
		stats.put("Shirt 1",sellesHistory.get(0).toString());
		stats.put("Shirt 2",sellesHistory.get(1).toString());
		stats.put("Pants 1",sellesHistory.get(2).toString());
		stats.put("Pants 2",sellesHistory.get(3).toString());
		return stats;
	}
	
	public void saveReportByItem(String item) throws IOException, ParseException
	{
		JSONObject json = new JSONObject();
		json.put("Action", action.sellsRepoet());
		json.put("Item", item);
		SendToServer(json);
		JSONObject Report = getFromServer();
		Report.put("title", item + " Sell Report");
		item = item.replace(" ", "_");
		Report.put("fileName", item +"_Report");
		createWordFile(Report);
	}
	
	public void saveReportByType(String type) throws IOException, ParseException
	{
		JSONObject json = new JSONObject();
		json.put("Action", action.sellsRepoet());
		json.put("Type", type);
		SendToServer(json);
		JSONObject Report = getFromServer();
		Report.put("title", type + " Sell Report");
		Report.put("fileName", type +"_Report");
		createWordFile(Report);
	}
	
	public void saveReportByDate(String date) throws IOException, ParseException
	{
		JSONObject json = new JSONObject();
		json.put("Action", action.sellsRepoet());
		json.put("Date", date);
		SendToServer(json);
		JSONObject Report = getFromServer();
		Report.put("title", date + " Sell Report");
		Report.put("fileName", date +"_Report");
		createWordFile(Report);
	}
	
	public void createWordFile(JSONObject json) throws IOException
	{
		String fileName = json.get("fileName").toString() + "_" + new Random().nextInt(Integer.MAX_VALUE);		
		json.remove("fileName");
		XWPFDocument document = new XWPFDocument(); 
		FileOutputStream out = new FileOutputStream( new File(".\\"+ fileName +".docx"));
		
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

	    for (int i = 1 ; i< json.size() ; ++i)
	    {
	    	 run.setText("#"+ i + " - " + json.get(""+i).toString());
	    	 run.addBreak();
	    }
	    
	    document.write(out);
	    out.close();
	    
	    Desktop.getDesktop().open(new File(".\\"+ fileName + ".docx"));
	}
	
	public void endSell() {
		JSONObject sell = shop.endSell();
		sell.put("Action", action.sellAction());
		SendToServer(sell);
	}
	
	public String startChat() throws IOException, ParseException {
		JSONObject json = new JSONObject();
		json.put("Action", action.startChat());
		SendToServer(json);
		JSONObject details = getFromServer();
		if (details.containsKey("User2Port")) {
			return details.get("User2Port").toString();
		} else return "0";
		
	}
	
	public void agreeChat() throws IOException {
		ServerSocket chatAgree = new ServerSocket(socket.getPort());
		System.out.println("Ready for connaction...");
	}
	
	public void setChatSocket(Socket socket) {
		chatSocket = socket;
	}
	
	public Socket getChatSocket() {
		return chatSocket;
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException
	{
		ClientSideConnection clientSideConnection = new ClientSideConnection();
		clientSideConnection.start();
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

	public void setInventory(JSONObject json) {
		json.put("Action", action.setInventory());
		SendToServer(json);
	}
}

