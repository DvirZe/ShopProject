package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import clientSide.ClientSideConnection;

public class StoreManagment {
	
	public StoreManagment(ClientSideConnection clientSideConnection) {
		Font font2 = new Font("Ariel",Font.BOLD,20);
		JFrame StoreMgr = new JFrame();
		StoreMgr.setTitle("Store Managment");
		StoreMgr.setSize(800, 420);
		StoreMgr.setLocationRelativeTo(null);
		StoreMgr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridLayout Layout = new GridLayout(0,3);
		StoreMgr.setLayout(Layout);
		
	
		JButton DiscountMgr = new JButton("Manage Discounts");
		StoreMgr.add(DiscountMgr);
		DiscountMgr.setFont(font2);
		DiscountMgr.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				StoreMgr.dispose();
				new	StoreManagment(clientSideConnection);
			}
		});
		

		JButton Supply = new JButton("Manage Supplys");
		StoreMgr.add(Supply);
		Supply.setFont(font2);
		Supply.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				StoreMgr.dispose();
				new	StoreManagment(clientSideConnection);
			}
		});
		
		JButton Prices = new JButton("Manage Pricess");
		StoreMgr.add(Prices);
		Prices.setFont(font2);
		Prices.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				StoreMgr.dispose();
				new	StoreManagment(clientSideConnection);
			}
		});
		
		JButton Back = new JButton("Back to Main Menu");
		Back.setFont(font2);
		StoreMgr.add(Back);
		
		StoreMgr.setVisible(true);
	}

}
