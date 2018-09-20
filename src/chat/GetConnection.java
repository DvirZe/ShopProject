package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import javax.swing.JFrame;

import org.json.simple.parser.ParseException;

import clientSide.ClientSideConnection;
import gui.ChatGui;
import gui.PopupIncomingChat;

public class GetConnection extends Thread {
	
	int port;
	ServerSocket serverSocket;
	LinkedList<Socket> sockets;
	ClientSideConnection clientSideConnection;
	BufferedReader bufferedReader;
	PrintWriter printWriter;
	private User chatUser;	
	
	public GetConnection (int port, ClientSideConnection clientSideConnection)
	{
		this.port = port;
		sockets = new LinkedList<Socket>();
		this.clientSideConnection = clientSideConnection;
		chatUser = null;
	}
	
	public void run() {
		//open chat screen
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Thread chat = new Thread(new Runnable() {
			public void run() {
				try {
					while (true)
					{
						Thread.sleep(0);
						if (!sockets.isEmpty())
						{
							chatAlert();
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		chat.start();
		
		while (true)
		{
			try {
				sockets.add(serverSocket.accept());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void chatAlert() throws InterruptedException, IOException {
		
		Socket socket = null; 
		
		if (chatUser == null)
		{
			chatUser = new User(clientSideConnection.getWorkerOnline().get("personalID").toString(), clientSideConnection.getWorkerOnline().get("name").toString(), "" + port);
			chatUser.setIsHost(true);
		}
		
		if (clientSideConnection.isFreeToChat()) {
			socket = sockets.removeFirst();
			if (socket.isConnected())
			{
				chatUser.addToBufferedReader(new BufferedReader(new InputStreamReader(socket.getInputStream())));
				chatUser.addToPrintWriter(new PrintWriter(socket.getOutputStream(),true));
				new PopupIncomingChat(this);
				clientSideConnection.freeToChatStatusChange(false);
			}
		}
		else { //Check if manager want to join the chat
			int managerPort;
			try {
				managerPort = clientSideConnection.managerPortToJoinChat();
			} catch (ParseException e) {
				managerPort = 0;
			}
			if (managerPort != 0)
			{
				for (int i = 0; i< sockets.size(); ++i)
				{
					if (sockets.get(i).getPort() == managerPort)
					{

						socket = sockets.remove(i);
						chatUser.addToBufferedReader(new BufferedReader(new InputStreamReader(socket.getInputStream())));
						chatUser.addToPrintWriter(new PrintWriter(socket.getOutputStream(),true));
						break;
					}
				}
			}
		}
	}
	
	
	public void newChatAccpet() {
		chatUser.sendMessage("Chat Accepted.");
		try {
			new ChatGui(clientSideConnection, true);
		} catch (IOException e) {
			System.out.println("Chat Screen is close now.");
		}
	}
	
	public void chatRefuse() {
		chatUser.sendMessage("Busy, Can\'t talk");
		clientSideConnection.leftTheChat();
		clientSideConnection.freeToChatStatusChange(true);
	}
	
	public ClientSideConnection getClientSideConnection() { return clientSideConnection; }
	
	public User getUser() {
		return chatUser;
	}
	
	public int getPort() {
		return port;
	}
	
	
}
