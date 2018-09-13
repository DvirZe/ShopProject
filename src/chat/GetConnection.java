package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GetConnection extends Thread {
	
	int port;
	ServerSocket serverSocket;
	Socket socket;
	private ServerSocket s;
	
	public GetConnection (int port)
	{
		this.port = port;
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
				socket = serverSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("test");
		}
	}
	
}
