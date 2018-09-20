package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;

import chat.GetConnection;

public class PopupIncomingChat extends JPanel {

	private static final long serialVersionUID = 4188117811193390502L;
	
	public PopupIncomingChat(GetConnection connection) {
		JFrame popupFrame = new JFrame();
		popupFrame.setTitle("Incoming chat");
		popupFrame.setSize(580, 220);
		popupFrame.setLocationRelativeTo(null);
		popupFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			
		///////////Pop up window Listener//////////
		popupFrame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {}
			
			@Override
			public void windowIconified(WindowEvent arg0) {}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {}
			
			@Override//rejects the chat if the x button pressed
			public void windowClosing(WindowEvent arg0) {
				connection.chatRefuse();
				popupFrame.dispose();
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {}
			
			@Override
			public void windowActivated(WindowEvent arg0) {}
		});
		///////////End of Pop up window Listener//////////
		
		
		JPanel incomingChat = new JPanel();
		incomingChat.setBorder(BorderFactory.createTitledBorder("Incoming Chat"));
		incomingChat.setBackground(Color.WHITE);
		incomingChat.setPreferredSize(new Dimension (250 , 70));
		incomingChat.setLayout(new BoxLayout(incomingChat, BoxLayout.Y_AXIS));
		
		SpringLayout imcomingChatLayout = new SpringLayout();
		incomingChat.setLayout(imcomingChatLayout);
		
		JButton getChat = new JButton("Answer Chat");
		incomingChat.add(getChat);
		///////get chat actionListener////////
		getChat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				popupFrame.dispose();
				connection.newChatAccpet();
			}
		});
		///////End of get chat actionListener////////
		
		JButton refuseChat = new JButton("Refuse Chat");
		incomingChat.add(refuseChat);
		///////refuse chat actionListener////////
		refuseChat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				connection.chatRefuse();
				popupFrame.dispose();
			}
		});
		///////End of refuse chat actionListener////////
		
		imcomingChatLayout.putConstraint(SpringLayout.WEST, getChat, 10, SpringLayout.WEST, incomingChat);
		imcomingChatLayout.putConstraint(SpringLayout.NORTH, getChat, 10, SpringLayout.NORTH, incomingChat);
		
		imcomingChatLayout.putConstraint(SpringLayout.WEST, refuseChat, 10, SpringLayout.EAST, getChat);
		imcomingChatLayout.putConstraint(SpringLayout.NORTH, refuseChat, 0, SpringLayout.NORTH, getChat);
		
		
		popupFrame.add(incomingChat);
		popupFrame.pack();
		popupFrame.setVisible(true);
		
	}
	
}
