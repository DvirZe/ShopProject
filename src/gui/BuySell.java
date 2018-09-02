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

public class BuySell {

	public  BuySell(ClientSideConnection clientSideConnection) {
		JFrame SupplyMenu = new JFrame();
		SupplyMenu.setTitle("Supply managment menu");
		SupplyMenu.setSize(580, 220);
		SupplyMenu.setLocationRelativeTo(null);
		SupplyMenu.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		
		JPanel SupplyButtons = new JPanel();
		SupplyButtons.setBorder(BorderFactory.createTitledBorder("Your Supply Menu"));
		SupplyButtons.setBackground(Color.white);
		SupplyButtons.setPreferredSize(new Dimension (400 , 175));
		SupplyButtons.setLayout(new BoxLayout(SupplyButtons, BoxLayout.X_AXIS));
		
		JButton Sell = new JButton("Sell an item");
		SupplyButtons.add(Sell, BorderLayout.CENTER);
		Sell.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				SupplyMenu.dispose();
				new	SellMain(clientSideConnection);
			}
		});
		
		JButton Back = new JButton("Back to main menu");
		SupplyButtons.add(Back, BorderLayout.CENTER);
		Back.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				SupplyMenu.dispose();
				new	MainMenu(clientSideConnection);
			}
		});
		

		
		
		
		SupplyMenu.add(SupplyButtons);
		SupplyMenu.pack();
		SupplyMenu.setVisible(true);
	}
	
}
