package chat;

import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.swing.JTextArea;

public interface ChatUser {
	public abstract void sendMessage(String message);
	 
	//public abstract void receiveMessage(JTextArea chatLog, Boolean firstUser);
	public abstract void receiveMessage(JTextArea chatLog, Boolean firstUser, BufferedReader socketBufferedReader, PrintWriter printWriter);
	public abstract int getId();
}
