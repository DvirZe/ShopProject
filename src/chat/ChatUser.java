package chat;

import javax.swing.JTextArea;

import clientSide.ClientSideConnection;

public interface ChatUser {
	public abstract void sendMessage(String message);
	public abstract void receiveMessage(JTextArea chatLog);
	public abstract void stopReceive(ClientSideConnection clientSideConnection);
	public abstract int getId();
	public abstract boolean isHost();
	public abstract String getUsername();
}
