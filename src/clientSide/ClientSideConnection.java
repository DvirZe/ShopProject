package clientSide;

import java.awt.Desktop.Action;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocketFactory;

import org.json.simple.JSONObject;


public class ClientSideConnection extends Thread {
	private Socket socket;
	private BufferedReader socketBufferedReader;
	private PrintWriter printWriter;
	private BufferedReader commandBufferedReader;
	private Actions action;
	
	
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
		// open Gui, Gui send to log in page
		this.login();
	}
	
	public void login()
	{
		//String username = "Admin", password = "Admin";
		JSONObject json = new JSONObject();
		json.put("Action",action.loginAction());
		json.putIfAbsent("WorkerID", "1");
		json.put("password", "ABC123!");
		SendToServer(json);
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
