package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class OpenConnection extends Thread {
	private Socket socket;
	private BufferedReader socketBufferedReader;
	private PrintWriter printWriter;
	private BufferedReader commandBufferedReader;	
	
	public OpenConnection(int port) throws UnknownHostException, IOException {
		socket = new Socket("localhost", port);
		socketBufferedReader = null;
		printWriter = null;
		commandBufferedReader = null;
	}
	
	public void run()  {
		try {
			System.out.println("Work");
			socketBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			printWriter = new PrintWriter(socket.getOutputStream(),true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		commandBufferedReader = new BufferedReader(new InputStreamReader(System.in));
		
	}
	
}
