package chat;

import java.util.ArrayList;

public class ChatRoom implements Conversation {

	private ArrayList<ChatUser> chatUsers = new ArrayList<ChatUser>();
	
	@Override
	public void sendMessage(String message, ChatUser chatUser) {
		for(ChatUser u : chatUsers){
			if(u != chatUser){
				//u.receiveMessage(message);
			}
		}
	}

	@Override
	public void addUser(ChatUser chatUser) {
		chatUsers.add(chatUser);
	}
	
}
