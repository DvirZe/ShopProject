package chat;

import clientSide.ClientSideConnection;

public class User implements ChatUser {
	private ChatRoom chatRoom = new ChatRoom();
	private String userName;
	private String port;
	
	public User(ChatRoom room, String userName, String port)
	{
		chatRoom = room;
		this.userName = userName;
		chatRoom.addUser(this);
	}
	
	@Override
	public void sendMessage(String message) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void receiveMessage(String message) {
		// TODO Auto-generated method stub
		
	}
}
