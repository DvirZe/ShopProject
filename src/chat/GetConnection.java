package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import javax.swing.JFrame;

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
	Socket curSocket;
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
						if (!sockets.isEmpty()/* && clientSideConnection.isFreeToChat()*/)
						{
							chatAlert();
							Thread.sleep(1000);
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
	
	public void chatAlert() throws InterruptedException, IOException {
		Socket socket = sockets.removeFirst();
		if (chatUser == null)
		{
			chatUser = new User(clientSideConnection.getWorkerOnline().get("personalID").toString(), clientSideConnection.getWorkerOnline().get("name").toString(), "" + port);
			chatUser.setIsServer(true);
			System.out.println("Client Created");
		}
		curSocket = socket;
		System.out.println("curSocket = socket");
		if (curSocket.isConnected())
		{
			chatUser.addToBufferedReader(new BufferedReader(new InputStreamReader(socket.getInputStream())));
			chatUser.addToPrintWriter(new PrintWriter(socket.getOutputStream(),true));
			//bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//printWriter = new PrintWriter(socket.getOutputStream(),true);
			if (clientSideConnection.isFreeToChat())
			{
				new PopupIncomingChat(this);
				clientSideConnection.freeToChatStatusChange(false);
			}
			else
				chatUser.sendMessage("Chat Accepted.");
			System.out.println("Take care of: " + socket);
		}
		else clientSideConnection.freeToChatStatusChange(true);	
	}
	
	
	public void newChatClose(JFrame openFrame) {
		openFrame.dispose();
		clientSideConnection.freeToChatStatusChange(true);
	}
	
	public void newChatAccpet() {
		//chatUser = new User(clientSideConnection.getWorkerOnline().get("personalID"),clientSideConnection.getWorkerOnline().get("name"), printWriter, bufferedReader);
		chatUser.sendMessage("Chat Accepted.");
		try {
			new ChatGui(clientSideConnection, true);
		} catch (IOException e) {
			System.out.println("Chat Screen is close now.");
		}
	}
	
	public void chatRefuse() {
		printWriter.println("Busy, Can\'t talk");
	}
	
	public ClientSideConnection getClientSideConnection() { return clientSideConnection; }
	
	public User getUser() {
		return chatUser;
	}
	
	
}
