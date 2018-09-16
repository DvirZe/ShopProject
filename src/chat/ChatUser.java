package chat;

import javax.swing.JTextArea;

public interface ChatUser {
	public abstract void sendMessage(String message);
	 
	public abstract void receiveMessage(JTextArea chatLog, Boolean firstUser);
}
