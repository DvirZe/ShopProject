package chat;

import java.util.ArrayList;

public interface Conversation { //Mediator
	public abstract Boolean isThisUserInChat(int id);
	public abstract void addUser(ChatUser chatUser);
	public abstract ArrayList<ChatUser> getChatUsers();
	public abstract int getPlaceOfUser(int idToFind);
	public abstract boolean isHostStillOnChat();
}
