package chat;

public interface ChatUser {
	public abstract void sendMessage(String message);
	 
	public abstract void receiveMessage(String message);
}
