package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
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

public class ChatGui extends JPanel{
	
	
		private static final long serialVersionUID = 4188117811193390502L;
		private OpenConnection connection;
		private Socket socket;
	private BufferedReader bufferedReader;
	
	ChatGui(ClientSideConnection clientSideConnection) throws IOException{
		
		JFrame chatFrame = new JFrame();
		chatFrame.setTitle("Chat with other branch");
		chatFrame.setSize(580, 220);
		chatFrame.setLocationRelativeTo(null);
		Image img = Toolkit.getDefaultToolkit().getImage("./files/ChatIcon.png");
		chatFrame.setIconImage(img);
		chatFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		
		JPanel chatPanel = new JPanel();
		chatPanel.setBorder(BorderFactory.createTitledBorder("Chat with other branch"));
		chatPanel.setBackground(Color.WHITE);
		chatPanel.setPreferredSize(new Dimension (450 , 440));
		chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
		
		SpringLayout imcomingChatLayout = new SpringLayout();
		chatPanel.setLayout(imcomingChatLayout);
				
		JButton join = new JButton("Join ongoing chat");
		chatPanel.add(join);
		
		JButton searchChat = new JButton("Start new chat");
		searchChat.setPreferredSize(new Dimension(join.getPreferredSize()));
		chatPanel.add(searchChat);
		
		JButton send = new JButton("Send!");
		chatPanel.add(send);
		
		JButton back = new JButton("Back");
		chatPanel.add(back);
		

		
		
		
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
		chatPanel.add(sendMessage);
		
		DefaultCaret caret = (DefaultCaret) chatLog.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		
		imcomingChatLayout.putConstraint(SpringLayout.WEST, searchChat, 5, SpringLayout.EAST, chatLog);
		imcomingChatLayout.putConstraint(SpringLayout.NORTH, searchChat, 0, SpringLayout.NORTH, chatLog);
		
		imcomingChatLayout.putConstraint(SpringLayout.WEST, join, 0, SpringLayout.WEST, searchChat);
		imcomingChatLayout.putConstraint(SpringLayout.NORTH, join, 10, SpringLayout.SOUTH, searchChat);
		
		imcomingChatLayout.putConstraint(SpringLayout.WEST, chatLog, 0, SpringLayout.WEST, chatFrame);
		imcomingChatLayout.putConstraint(SpringLayout.NORTH, chatLog, 10, SpringLayout.NORTH, chatFrame);
		
		imcomingChatLayout.putConstraint(SpringLayout.WEST, sendMessage, 0, SpringLayout.WEST, chatLog);
		imcomingChatLayout.putConstraint(SpringLayout.NORTH, sendMessage, 10, SpringLayout.SOUTH, chatLog);
		
		imcomingChatLayout.putConstraint(SpringLayout.WEST, send, 5, SpringLayout.EAST, sendMessage);
		imcomingChatLayout.putConstraint(SpringLayout.NORTH, send, 0, SpringLayout.NORTH, sendMessage);
		
		imcomingChatLayout.putConstraint(SpringLayout.EAST, back, 0, SpringLayout.EAST, chatPanel);
		imcomingChatLayout.putConstraint(SpringLayout.SOUTH, back, 0, SpringLayout.SOUTH, chatPanel);
		
		
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
		
		
		back.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						chatFrame.dispose();
						new MainMenu(clientSideConnection);
					}
				});
				
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				chatLog.append("\n"+sendMessage.getText());
				sendMessage.setText("");
				
			}
		});
		
		join.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO
				
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
