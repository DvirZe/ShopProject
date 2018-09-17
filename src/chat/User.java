package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.naming.TimeLimitExceededException;
import javax.swing.JTextArea;
import org.junit.rules.Timeout;

import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.TimeLimiter;

import clientSide.ClientSideConnection;

public class User implements ChatUser {
	private String id;
	private String userName;
	private int port;
	private PrintWriter printWriter;
	private BufferedReader socketBufferedReader;
	private ArrayList<PrintWriter> printWriter_;
	private ArrayList<BufferedReader> socketBufferedReader_;
	private Thread inMessage;

	public User(String id, String userName, String port)
	{
		this.id = id;
		this.userName = userName;
		this.port = Integer.parseInt(port);
		printWriter_ = new ArrayList<PrintWriter>();
		socketBufferedReader_ = new ArrayList<BufferedReader>();
	}
	
	public User(String id, String userName, PrintWriter printWriter, BufferedReader socketBufferedReader)
	{
		printWriter_ = new ArrayList<PrintWriter>();
		socketBufferedReader_ = new ArrayList<BufferedReader>();
		this.id = id;
		this.userName = userName;
		this.printWriter = printWriter;
		this.socketBufferedReader = socketBufferedReader;
	}
	
	public void addToPrintWriter(PrintWriter printWriter) {
		printWriter_.add(printWriter);
	}
	
	public void addToBufferedReader(BufferedReader bufferedReader) {
		socketBufferedReader_.add(bufferedReader);
	}
	
	@Override
	public void sendMessage(String message) {
		printWriter.println(userName + " >> " + message);
		
	}
	
	public void sendMessage2(String message) {
		for (int i = 0; i < socketBufferedReader_.size() ; ++i)
		{	
			printWriter_.get(i).println(userName + " >> " + message);
		}
	}	
	
	@Override
	public void receiveMessage(JTextArea chatLog, Boolean firstUser) {
		inMessage = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String msg="";
					if (!firstUser)
					{
						for (int i = 0; i < socketBufferedReader_.size() ; ++i)
						{
							String str = socketBufferedReader_.get(i).readLine();
							if (!str.equals(""))
								msg=msg + "\n" + str;
						}
					}
					else msg = socketBufferedReader.readLine();
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
									for (int i = 0; i < socketBufferedReader_.size() ; ++i)
									{	
										printWriter_.get(i).println(msg);
									}
								}
							}
						if (!firstUser) {
							msg = "";
							System.out.println(msg);
								while (msg.equals("")) {
									for (int i = 0; i < socketBufferedReader_.size() ; ++i)
									{
										String str = socketBufferedReader_.get(i).readLine();
										if (!str.equals(""))
											msg=msg + "\n" + str;
									}
								}
							}
						 else {
							while ((msg = socketBufferedReader.readLine()) == null) {}
						}
					}
				} catch (IOException e) {
					System.out.println("Socket is close.");
				}
			}
		});
		inMessage.start();
	}
	
	
	
	
	
/*	@Override
	public void receiveMessage(JTextArea chatLog, Boolean firstUser) {
		// TODO Auto-generated method stub
		inMessage = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println(socketBufferedReader.readLine());
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
	}*/

	public void stopReceive(ClientSideConnection clientSideConnection) { 
		inMessage.interrupt();
		clientSideConnection.leftTheChat();
	}

	@Override
	public int getId() {
		return Integer.parseInt(id);
	}
	
}
