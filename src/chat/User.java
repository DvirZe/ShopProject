package chat;

import clientSide.ClientSideConnection;

public class User implements ChatUser {
	private ChatRoom chatRoom = new ChatRoom();
	private String userName;
	private ClientSideConnection clientSideConnection;
	
	public User(ChatRoom room, String userName, ClientSideConnection clientSideConnection)
	{
		chatRoom = room;
		this.userName = userName;
		chatRoom.addUser(this);
		this.clientSideConnection = clientSideConnection;
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
