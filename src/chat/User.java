package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JTextArea;

import clientSide.ClientSideConnection;

public class User implements ChatUser {
	private ChatRoom chatRoom = new ChatRoom();
	private String userName;
	private int port;
	private PrintWriter printWriter;
	private BufferedReader socketBufferedReader;
	private Thread inMessage;

	public User(ChatRoom room, String userName, String port)
	{
		chatRoom = room;
		this.userName = userName;
		chatRoom.addUser(this);
		this.port = Integer.parseInt(port);
	}
	
	public User(String userName, PrintWriter printWriter, BufferedReader socketBufferedReader)
	{
		this.userName = userName;
		this.printWriter = printWriter;
		this.socketBufferedReader = socketBufferedReader;
	}
	
	@Override
	public void sendMessage(String message) {
		// TODO Auto-generated method stub
		printWriter.println(userName + " >> " + message);
		
	}
	@Override
	public void receiveMessage(JTextArea chatLog, Boolean firstUser) {
		// TODO Auto-generated method stub
		inMessage = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String msg = socketBufferedReader.readLine();
					try {
						Thread.sleep(0);
					} catch (InterruptedException e1) {
						new Exception("Chat stoppet");
					}
					while(true) {
						if (!msg.equals(""))
							{
								chatLog.append("\n"+msg);
								if (!firstUser)
								{
									printWriter.println(msg);
								}
							}
						while ((msg = socketBufferedReader.readLine()) == null) {}
					}
				} catch (IOException e) {
					System.out.println("Socket is close.");
				}
			}
		});
		inMessage.start();
	}

	public void stopReceive() { 
		inMessage.interrupt();
		}
	
}
