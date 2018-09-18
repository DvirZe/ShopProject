package chat;

import java.util.ArrayList;

public class ChatRoom implements Conversation {

	private ArrayList<ChatUser> chatUsers = new ArrayList<ChatUser>();
	private ArrayList<ChatUser> leftChatUsers = new ArrayList<ChatUser>();
	
	@Override
	public void addUser(ChatUser chatUser) {
		chatUsers.add(chatUsers.size(),chatUser);
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
	
	public int getPlaceOfUser(int idToFind) { //on chatUsers list
		for (int i = 0 ; i < chatUsers.size() ; ++i) {
			if (chatUsers.get(i).getId() == idToFind)
				return i;
		}
		return Integer.MAX_VALUE;
	}
	
	public ArrayList<ChatUser> getChatUsers() {
		return chatUsers;
	}
	
	public void addLeftUser(ChatUser chatUser) {
		leftChatUsers.add(chatUser);
	}
	
	public ArrayList<ChatUser> getLeftChatUsers() {
		return leftChatUsers;
	}
	
	public boolean isHostStillOnChat() {
		for (ChatUser user : chatUsers)
		{
			if (user.isHost() == true)
				return true;
		}
		return false;
	}
	
}
