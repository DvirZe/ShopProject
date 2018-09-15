package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import javax.swing.JFrame;

import clientSide.ClientSideConnection;
import gui.PopupIncomingChat;

public class GetConnection extends Thread {
	
	int port;
	ServerSocket serverSocket;
	LinkedList<Socket> sockets;
	boolean freeToChat = true;
	ClientSideConnection clientSideConnection;
	
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
						if (!sockets.isEmpty() && freeToChat)
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
		freeToChatStatusChange();
		Socket socket = sockets.removeFirst();
		/*Thread m = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					getMessage(socket);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		m.start();*/
		System.out.println("Start chat with: " + socket.getPort());
		PopupIncomingChat newChat = new PopupIncomingChat(this);
	}
	
	public void getMessage(Socket socket) throws IOException, InterruptedException
	{
		Thread.sleep(0);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String msg = bufferedReader.readLine();
		while(true) {
			if (!msg.equals(""))
				{
					System.out.println(msg);
				}
			while ((msg = bufferedReader.readLine()) == null) {}
		}
	}
	
	public void freeToChatStatusChange()
	{
		freeToChat = !freeToChat;
	}
	
	public void newChatClose(JFrame openFrame) {
		openFrame.dispose();
		freeToChatStatusChange();
	}
	
	public void newChatAccpet(JFrame openFrame) {
		openFrame.dispose();
	}
	
	public ClientSideConnection getClientSideConnection() { return clientSideConnection; }
	
}
