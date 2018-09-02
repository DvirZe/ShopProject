package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import clientSide.ClientSideConnection;

public class CustomerMain {
	public  CustomerMain(ClientSideConnection clientSideConnection) {
		JFrame CustomerMenu = new JFrame();
		CustomerMenu.setTitle("Supply managment menu");
		CustomerMenu.setSize(580, 220);
		CustomerMenu.setLocationRelativeTo(null);
		CustomerMenu.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		
		JPanel CustomerButtons = new JPanel();
		CustomerButtons.setBorder(BorderFactory.createTitledBorder("Your Supply Menu"));
		CustomerButtons.setBackground(Color.white);
		CustomerButtons.setPreferredSize(new Dimension (400 , 175));
		CustomerButtons.setLayout(new BoxLayout(CustomerButtons, BoxLayout.X_AXIS));	
			
		JButton NewCus = new JButton("Add new customer");
		CustomerButtons.add(NewCus, BorderLayout.CENTER);
		NewCus.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				CustomerMenu.dispose();
				new	AddCus(clientSideConnection);
			}
		});
	
		JButton Back = new JButton("Back to Main Menu");
		CustomerButtons.add(Back, BorderLayout.CENTER);
		Back.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				CustomerMenu.dispose();
				new	MainMenu(clientSideConnection);
			}
		});

		
		
		CustomerMenu.add(CustomerButtons);
		CustomerMenu.pack();
		CustomerMenu.setVisible(true);
	}
}
