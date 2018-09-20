package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.DefaultCaret;
import org.json.simple.parser.ParseException;
import chat.OpenConnection;
import chat.User;
import clientSide.ClientSideConnection;

public class ChatGui extends JPanel{
	
	private static final long serialVersionUID = 4188117811193390502L;
	private OpenConnection connection;
	private Socket socket;
	private User chatUser = null;
	
	public ChatGui(ClientSideConnection clientSideConnection, Boolean isConnectToChatAlready) throws IOException{
		
		JFrame chatFrame = new JFrame();
		chatFrame.setTitle("Chat with other branch");
		chatFrame.setSize(580, 220);
		chatFrame.setLocationRelativeTo(null);
		Image img = Toolkit.getDefaultToolkit().getImage("./files/ChatIcon.png");
		chatFrame.setIconImage(img);
		chatFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		chatFrame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {}
			
			@Override
			public void windowIconified(WindowEvent e) {}
			
			@Override
			public void windowDeiconified(WindowEvent e) {}
			
			@Override
			public void windowDeactivated(WindowEvent e) {}
			
			@Override
			public void windowClosing(WindowEvent e) {
				if (chatUser != null)
				{
					chatUser.sendMessage("left the chat.");
					chatUser.stopReceive(clientSideConnection);
					if (chatUser.isHost())
					{
						chatUser.hostDisconnect();
					}
					clientSideConnection.leftTheChat();
				}
				clientSideConnection.freeToChatStatusChange(true);
				chatFrame.dispose();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {}
			
			@Override
			public void windowActivated(WindowEvent e) {}
		});
		
		
		JPanel chatPanel = new JPanel();
		chatPanel.setBorder(BorderFactory.createTitledBorder("Chat with other branch"));
		chatPanel.setBackground(Color.WHITE);
		chatPanel.setPreferredSize(new Dimension (450 , 440));
		chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
		
		SpringLayout imcomingChatLayout = new SpringLayout();
		chatPanel.setLayout(imcomingChatLayout);
				
		JButton join = new JButton("Join ongoing chat");
		join.setEnabled(clientSideConnection.isManager());
		chatPanel.add(join);
		
		JButton saveLog = new JButton("Save chat log");
		saveLog.setEnabled(false);
		chatPanel.add(saveLog);
		
		JButton searchChat = new JButton("Start new chat");
		searchChat.setPreferredSize(new Dimension(join.getPreferredSize()));
		searchChat.setEnabled(!isConnectToChatAlready);
		chatPanel.add(searchChat);
		
		JButton send = new JButton("Send!");
		send.setEnabled(false);
		chatPanel.add(send);
		
		JButton leave = new JButton("Leave");
		chatPanel.add(leave);	
		
		JTextArea chatLog = new JTextArea();
		chatLog.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		chatLog.setPreferredSize(new Dimension(300, 300));
		chatLog.setLineWrap(true);
		chatLog.setWrapStyleWord(true);
		chatLog.setEditable(false);
		chatPanel.add(chatLog);
		
		JTextField sendMessage = new JTextField();
		sendMessage.setPreferredSize(new Dimension(300, 30));
		sendMessage.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		sendMessage.setEnabled(false);
		chatPanel.add(sendMessage);
		
		DefaultCaret caret = (DefaultCaret) chatLog.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);		
		
		imcomingChatLayout.putConstraint(SpringLayout.WEST, searchChat, 5, SpringLayout.EAST, chatLog);
		imcomingChatLayout.putConstraint(SpringLayout.NORTH, searchChat, 0, SpringLayout.NORTH, chatLog);
		
		imcomingChatLayout.putConstraint(SpringLayout.WEST, join, 0, SpringLayout.WEST, searchChat);
		imcomingChatLayout.putConstraint(SpringLayout.NORTH, join, 10, SpringLayout.SOUTH, searchChat);

		imcomingChatLayout.putConstraint(SpringLayout.WEST, saveLog, 0, SpringLayout.WEST, join);
		imcomingChatLayout.putConstraint(SpringLayout.NORTH, saveLog, 10, SpringLayout.SOUTH, join);
		
		imcomingChatLayout.putConstraint(SpringLayout.WEST, chatLog, 0, SpringLayout.WEST, chatFrame);
		imcomingChatLayout.putConstraint(SpringLayout.NORTH, chatLog, 10, SpringLayout.NORTH, chatFrame);
		
		imcomingChatLayout.putConstraint(SpringLayout.WEST, sendMessage, 0, SpringLayout.WEST, chatLog);
		imcomingChatLayout.putConstraint(SpringLayout.NORTH, sendMessage, 10, SpringLayout.SOUTH, chatLog);
		
		imcomingChatLayout.putConstraint(SpringLayout.WEST, send, 5, SpringLayout.EAST, sendMessage);
		imcomingChatLayout.putConstraint(SpringLayout.NORTH, send, 0, SpringLayout.NORTH, sendMessage);
		
		imcomingChatLayout.putConstraint(SpringLayout.EAST, leave, 0, SpringLayout.EAST, chatPanel);
		imcomingChatLayout.putConstraint(SpringLayout.SOUTH, leave, 0, SpringLayout.SOUTH, chatPanel);
		
		Thread sendingRefresh = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					chatUser.sendMessage("");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		searchChat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int port = 0;
				try {
					chatLog.setText("Try to connect..");
					String answer = clientSideConnection.startChat();
					if (!answer.equals("0"))
					{
						clientSideConnection.freeToChatStatusChange(false);
						port = Integer.parseInt(answer);
						connection = new OpenConnection(port, clientSideConnection);
						clientSideConnection.setChatSocket(connection.getSocket());
						socket = connection.getSocket();
						chatUser = connection.getUser();
						chatUser.receiveMessage(chatLog);
						searchChat.setEnabled(false);
						sendMessage.setEnabled(true);
						send.setEnabled(true);
						join.setEnabled(false);
						sendingRefresh.start();
					} else chatLog.append("\nNo one available.");
				} catch (ParseException | IOException e1) {e1.printStackTrace(); }
			}
		});
		
		join.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int port = 0;
				try {
					chatLog.setText("Looking for chat to join..");
					String answer = clientSideConnection.joinChat();
					if (!answer.equals("0"))
					{
						clientSideConnection.freeToChatStatusChange(false);
						port = Integer.parseInt(answer);
						connection = new OpenConnection(port, clientSideConnection);
						clientSideConnection.setChatSocket(connection.getSocket());
						socket = connection.getSocket();
						chatUser = connection.getUser();
						clientSideConnection.updateJoinChatListAfterOpenSocket(port, socket.getLocalPort());
						chatUser.receiveMessage(chatLog);
						searchChat.setEnabled(false);
						sendMessage.setEnabled(true);
						send.setEnabled(true);
						join.setEnabled(false);
						sendingRefresh.start();
						chatUser.sendMessage("Manager join the chat.");
						chatLog.append("\nManager join the chat.");
					} else chatLog.append("\nNo chat is on.");
				} catch (ParseException | IOException e1) {e1.printStackTrace(); }
			}
		});
		
		//if its the worker who get the chat
		if (isConnectToChatAlready)
		{
			chatUser = clientSideConnection.getConnection().getUser();
			chatUser.receiveMessage(chatLog);
			chatLog.setText("Start to chat..");
			sendMessage.setEnabled(true);
			send.setEnabled(true);
			join.setEnabled(false);
			sendingRefresh.start();
		}
		
		leave.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {			
				if (chatUser != null)
				{
					chatUser.sendMessage("left the chat.");
					chatUser.stopReceive(clientSideConnection);
					if (chatUser.isHost())
					{
						chatUser.hostDisconnect();
					}
					clientSideConnection.leftTheChat();
				}
				clientSideConnection.freeToChatStatusChange(true);
				chatFrame.dispose();
				
			}
		});
		
		
		ActionListener sendActions = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isConnectToChatAlready)
				{
					chatLog.append("\n" + clientSideConnection.getWorkerOnline().get("name") + " >> " +sendMessage.getText().toString());
					chatUser.sendMessage(sendMessage.getText().toString());
				} 
				else
					chatUser.sendMessage(sendMessage.getText().toString());
				sendMessage.setText("");
			}
		};
		
		saveLog.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientSideConnection.saveChatLog(chatLog.getText());
				saveLog.setEnabled(false);
			}
		});
		
		chatLog.addCaretListener(new CaretListener() {
			
			@Override
			public void caretUpdate(CaretEvent arg0) {
				saveLog.setEnabled(true);
			}
		});
		
		send.addActionListener(sendActions);
		sendMessage.addActionListener(sendActions);
		
		
		chatFrame.add(chatPanel);
		chatFrame.pack();
		chatFrame.setVisible(true);
		
	}
	
}
