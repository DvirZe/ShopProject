package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import clientSide.ClientSideConnection;

public class OpenConnection {
	private Socket socket;
	private BufferedReader socketBufferedReader;
	private PrintWriter printWriter;
	private BufferedReader commandBufferedReader;	
	private ClientSideConnection clientSideConnection;
	private User chatUser;
	
	public OpenConnection(int port, ClientSideConnection clientSideConnection) throws UnknownHostException, IOException {
		socket = new Socket("localhost", port);
		commandBufferedReader = null;
		this.clientSideConnection = clientSideConnection;
		socketBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		printWriter = new PrintWriter(socket.getOutputStream(),true);
		chatUser = new User(clientSideConnection.getWorkerOnline().get("personalID"),clientSideConnection.getWorkerOnline().get("name"), printWriter, socketBufferedReader);
	}
	
	public User getUser() {
		return chatUser;
	}
	
	public void sendMessege(String message)
	{
		chatUser.sendMessage(message);
	}
	
	public int getPort() { return socket.getPort(); }
	
	public Socket getSocket() {
		return socket;
	}
}
