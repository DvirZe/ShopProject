package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;
import javax.swing.text.DefaultCaret;

import org.json.simple.parser.ParseException;
import chat.OpenConnection;
import clientSide.ClientSideConnection;

public class ChatScreen extends JPanel {

	private static final long serialVersionUID = 4188117811193390502L;
	private OpenConnection connection;
	private Socket socket;
	private BufferedReader bufferedReader;
	
	public ChatScreen(ClientSideConnection clientSideConnection) throws IOException {
		JFrame chatFrame = new JFrame();
		chatFrame.setTitle("Store managment login");
		chatFrame.setSize(580, 220);
		chatFrame.setLocationRelativeTo(null);
		chatFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		
		JPanel chatPanel = new JPanel();
		chatPanel.setBorder(BorderFactory.createTitledBorder("Chat with others"));
		chatPanel.setBackground(Color.WHITE);
		chatPanel.setPreferredSize(new Dimension (400 , 500));
		chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
		
		SpringLayout imcomingChatLayout = new SpringLayout();
		chatPanel.setLayout(imcomingChatLayout);
		
		JButton searchChat = new JButton("Search Chat");
		chatPanel.add(searchChat);
		
		
		JTextArea chatLog = new JTextArea();
		chatLog.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		chatLog.setPreferredSize(new Dimension(200, 300));
		chatLog.setEditable(false);
		chatPanel.add(chatLog);
		
		JTextField sendMessage = new JTextField();
		sendMessage.setPreferredSize(new Dimension(200, 20));
		chatPanel.add(sendMessage);
		
		DefaultCaret caret = (DefaultCaret) chatLog.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		
		imcomingChatLayout.putConstraint(SpringLayout.WEST, searchChat, 10, SpringLayout.WEST, chatFrame);
		imcomingChatLayout.putConstraint(SpringLayout.NORTH, searchChat, 10, SpringLayout.NORTH, chatFrame);
		
		imcomingChatLayout.putConstraint(SpringLayout.WEST, chatLog, 0, SpringLayout.WEST, searchChat);
		imcomingChatLayout.putConstraint(SpringLayout.NORTH, chatLog, 10, SpringLayout.SOUTH, searchChat);
		
		imcomingChatLayout.putConstraint(SpringLayout.WEST, sendMessage, 0, SpringLayout.WEST, chatLog);
		imcomingChatLayout.putConstraint(SpringLayout.NORTH, sendMessage, 10, SpringLayout.SOUTH, chatLog);
		
		
		searchChat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int port = 0;
				try {
					chatLog.setText("Try to connect..");
					String answer = clientSideConnection.startChat();
					if (!answer.equals("0"))
						port = Integer.parseInt(answer);
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				if (port != 0)
				{
					try {
						connection = new OpenConnection(port, clientSideConnection);
						connection.start();
						clientSideConnection.setChatSocket(connection.getSocket());
						socket = connection.getSocket();
						bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						chatLog.setText(chatLog.getText() + "\nStart Chatting..");
						
						Thread chatText = new Thread(new Runnable() {
							public void run() {
								String msg = null;
								try {
									msg = bufferedReader.readLine();
									System.out.println("xxx: "+msg);
								} catch (IOException e) {e.printStackTrace();}
								while (true)
								{			
									if (!msg.equals(""))
									{
										System.out.println("xxx: "+msg);
										chatLog.setText(msg);
									}
									try {
										while ((msg = bufferedReader.readLine()) == null) {}
									} catch (IOException e) {e.printStackTrace();}
								}
							}
						});
						
						chatText.start();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else chatLog.append("\nNo one available.");
			}
		});
				
		
		sendMessage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//chatLog.setText(chatLog.getText() + "\n" + sendMessage.getText());
				//System.out.println(connection.getPort());
				//connection.sendMessege(sendMessage.getText().toString());
				chatLog.append("\n"+sendMessage.getText());
				sendMessage.setText("");
			}
		});
				
		chatFrame.add(chatPanel);
		chatFrame.pack();
		chatFrame.setVisible(true);
		
		
	}
	
}
