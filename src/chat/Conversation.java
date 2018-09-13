package chat;

public interface Conversation { //Mediator
	public abstract void sendMessage(String message, ChatUser chatUser);
	public abstract void addUser(ChatUser chatUser);
}
