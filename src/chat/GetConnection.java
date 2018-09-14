package chat;

import java.awt.List;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import clientSide.ClientSideConnection;
import gui.Login;
import gui.MainMenu;

public class GetConnection extends Thread {
	
	int port;
	ServerSocket serverSocket;
	LinkedList<Socket> sockets;
	boolean freeToChat = true;
	
	public GetConnection (int port)
	{
		this.port = port;
		sockets = new LinkedList<Socket>();
	}
	
	public void run() {
		//open chat screen
		try {
			serverSocket = new ServerSocket(port);
			System.out.println(serverSocket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true)
		{
			try {
				sockets.add(serverSocket.accept());
				Thread chat = new Thread(new Runnable() {
					public void run() {
						try {
							chatAlert();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				});
				chat.start();
				//sockets.add(socket);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(sockets.size());
		}
	}
	
	public synchronized void chatAlert() throws InterruptedException {
		if (freeToChat)
		{
			Socket socket = sockets.removeFirst();
			freeToChat = false;
			System.out.println("Start chat with: " + socket.getPort());
			sleep(5000);
			freeToChat = true;
		}
	}
	
}
