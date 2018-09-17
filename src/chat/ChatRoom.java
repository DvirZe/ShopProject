package chat;

import java.util.ArrayList;

public class ChatRoom implements Conversation {

	private ArrayList<ChatUser> chatUsers = new ArrayList<ChatUser>();
	
	@Override
	public void addUser(ChatUser chatUser) {
		chatUsers.add(chatUser);
	}

	@Override
	public Boolean isThisUserInChat(int id) {
		for (ChatUser user : chatUsers)
		{
			if (user.getId() == id)
				return true;
		}
		return false;
	}
	
	public ArrayList<ChatUser> getChatUsers() {
		return chatUsers;
	}
	
}
