package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JTextArea;

import clientSide.ClientSideConnection;

public class User implements ChatUser {
	private String id;
	private String userName;
	private int port;
	private PrintWriter printWriter;
	private BufferedReader socketBufferedReader;
	private ArrayList<PrintWriter> printWriterArray;
	private ArrayList<BufferedReader> socketBufferedReaderArray;
	private Thread inMessage;
	private Boolean isHost = false;

	public User(String id, String userName, String port)
	{
		this.id = id;
		this.userName = userName;
		this.port = Integer.parseInt(port);
		printWriterArray = new ArrayList<PrintWriter>();
		socketBufferedReaderArray = new ArrayList<BufferedReader>();
	}
	
	public User(String id, String userName, PrintWriter printWriter, BufferedReader socketBufferedReader)
	{
		this.id = id;
		this.userName = userName;
		this.printWriter = printWriter;
		this.socketBufferedReader = socketBufferedReader;
	}
	
	public void addToPrintWriter(PrintWriter printWriter) {
		printWriterArray.add(printWriter);
	}
	
	public void addToBufferedReader(BufferedReader bufferedReader) {
		socketBufferedReaderArray.add(bufferedReader);
	}
	
	@Override
	public void sendMessage(String message) {
		if (!message.equals(""))
		{
			if (isHost)
			{
				for (int i = 0; i < socketBufferedReaderArray.size() ; ++i)
				{	
					printWriterArray.get(i).println(userName + " >> " + message);
				}
			}
			else
				printWriter.println(userName + " >> " + message);	
		} else {
			if (isHost)
			{
				for (int i = 0; i < socketBufferedReaderArray.size() ; ++i)
				{	
					printWriterArray.get(i).println("");
				}
			}
			else
				printWriter.println("");	
		}
	}	
	
	@Override
	public void receiveMessage(JTextArea chatLog) {
		inMessage = new Thread(new Runnable() {

			@Override
			public void run() {
				String msg = "";
				try {
					if (isHost)
					{
						for (int i = 0; i < socketBufferedReaderArray.size() ; ++i)
						{
							String str = socketBufferedReaderArray.get(i).readLine();
							if (!str.equals(""))
								msg=msg + "\n" + str;
						}
					} else {
					msg = socketBufferedReader.readLine();
					}
					try {
						Thread.sleep(0);
					} catch (InterruptedException e1) {
						new Exception("Chat stoppet");
					}
					while(true) {
						if (!msg.equals(""))
							{
								chatLog.append("\n"+msg);
								if (isHost)
								{
									for (int i = 0; i < socketBufferedReaderArray.size() ; ++i)
									{	
										printWriterArray.get(i).println(msg);
									}
								}
							}
						msg="";
						while (msg.equals("")) {		
							if (isHost)
							{
								for (int i = 0; i < socketBufferedReaderArray.size() ; ++i)
								{
									String str = socketBufferedReaderArray.get(i).readLine();
									if (!str.equals(""))
										if (msg.equals(""))
											msg = str;
										else
											msg = msg + "\n" + str;
								}
							} else {
							msg = socketBufferedReader.readLine();
							}	
						}
					}
				} catch (IOException e) {
					System.out.println("Socket is close.");
				}
			}
		});
		inMessage.start();
	}

	public void stopReceive(ClientSideConnection clientSideConnection) { 
		inMessage.interrupt();
		clientSideConnection.leftTheChat();
	}

	@Override
	public int getId() {
		return Integer.parseInt(id);
	}
	
	public PrintWriter getLastPrintWriter() { return printWriterArray.get(printWriterArray.size()-1); }
	public BufferedReader getLastBufferedReader() { return socketBufferedReaderArray.get(socketBufferedReaderArray.size()-1); }
	public void setIsHost(Boolean bool) { isHost = bool; }
	
	public void hostDisconnect() {
		printWriterArray.clear();
		socketBufferedReaderArray.clear();
	}
	
	@Override
	public boolean isHost() { return isHost; }

	@Override
	public String getUsername() {
		return userName;
	}
}
