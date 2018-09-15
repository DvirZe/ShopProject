package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import clientSide.ClientSideConnection;

public class OpenConnection extends Thread {
	private Socket socket;
	private BufferedReader socketBufferedReader;
	private PrintWriter printWriter;
	private BufferedReader commandBufferedReader;	
	private ClientSideConnection clientSideConnection;
	
	public OpenConnection(int port, ClientSideConnection clientSideConnection) throws UnknownHostException, IOException {
		socket = new Socket("localhost", port);
		socketBufferedReader = null;
		printWriter = null;
		commandBufferedReader = null;
		this.clientSideConnection = clientSideConnection;
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
	
	public void sendMessege(String m)
	{
		printWriter.println(">> " + m);
	}
	
	public int getPort() { return socket.getPort(); }
	
	public Socket getSocket() {
		return socket;
	}
}
