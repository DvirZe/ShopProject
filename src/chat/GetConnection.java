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
						if (!sockets.isEmpty() && clientSideConnection.isFreeToChat())
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
			System.out.println(sockets.size());
		}
	}
	
	public void chatAlert() throws InterruptedException, IOException {
		clientSideConnection.freeToChatStatusChange(false);		
		Socket socket = sockets.removeFirst();
		curSocket = socket;
		System.out.println(socket);
		if (curSocket.isConnected())
		{
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			printWriter = new PrintWriter(socket.getOutputStream(),true);
			System.out.println("Start chat with: " + socket.getPort());
			PopupIncomingChat newChat = new PopupIncomingChat(this);
		}
		else clientSideConnection.freeToChatStatusChange(true);	
	}
	
	
	public void newChatClose(JFrame openFrame) {
		openFrame.dispose();
		clientSideConnection.freeToChatStatusChange(true);
	}
	
	public void newChatAccpet() {
		printWriter.println("Chat Accepted.");
		chatUser = new User(clientSideConnection.getWorkerOnline().get("name"), printWriter, bufferedReader);
		System.out.println(chatUser);
		try {
			new ChatGui(clientSideConnection, true);
		} catch (IOException e) {
			System.out.println("Chat Screen is close now.");
		}
	}
	
	public ClientSideConnection getClientSideConnection() { return clientSideConnection; }
	
	public User getUser() {
		return chatUser;
	}
	
	public void sendMessege(String message)
	{
		chatUser.sendMessage(message);
	}
	
}
