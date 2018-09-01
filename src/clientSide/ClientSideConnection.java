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


public class ClientSideConnection extends Thread {
	private Socket socket;
	private BufferedReader socketBufferedReader;
	private PrintWriter printWriter;
	@SuppressWarnings("unused")
	private BufferedReader commandBufferedReader;
	private Actions action;
	@SuppressWarnings("unused")
	private ShopGui shopGui;
	private Shop shop;
	
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
		shopGui = new ShopGui(this);
	}
	
	@SuppressWarnings("unchecked")
	public int login(String workerId, String password) throws ParseException, IOException
	{
		JSONObject json = new JSONObject();
		json.put("Action",action.loginAction());
		json.putIfAbsent("workerId", workerId);
		json.put("password", password);
		SendToServer(json);
		String msg = socketBufferedReader.readLine();
		System.out.println(msg);
		JSONParser parser = new JSONParser();
		json = (JSONObject)parser.parse(msg);
		int status = Integer.parseInt(json.get("Status").toString());
		if (status == 1)
			shop = new Shop(json);
		System.out.println("xxx");
		return Integer.parseInt(json.get("Status").toString());
	}
	
	public void SendToServer(JSONObject json)
	{
		printWriter.println(json);
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException
	{
		ClientSideConnection clientSideConnection = new ClientSideConnection();
		clientSideConnection.start();
		//Shop sp = new Shop("Store 1");
		/*System.setProperty("javax.net.ssl.trustStore", "sp.store");
		Socket socket = ((SSLSocketFactory)SSLSocketFactory.getDefault()).createSocket("localhost", 7000);
		BufferedReader socketBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
		BufferedReader commandBufferedReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter test: ");
		printWriter.println(commandBufferedReader.readLine());
		String message = null;
		//sp.saveInfo(printWriter,socketBufferedReader);
		while (true)
		{
			System.out.println("Enter message: ");
			message = commandBufferedReader.readLine();
			if (message.equals("end"))
			{
				printWriter.println(message);
				socket.close();
				break;
			}
			printWriter.println(message);
			System.out.println("Replay from server: ");
			System.out.println(socketBufferedReader.readLine());
		}*/
	}
}
