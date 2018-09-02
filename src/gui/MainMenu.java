package gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import clientSide.ClientSideConnection;

public class MainMenu {
	public MainMenu(ClientSideConnection clientSideConnection) {
		Font font2 = new Font("Ariel",Font.BOLD,20);
		JFrame Main = new JFrame();
		Main.setTitle("Store Managment");
		Main.setSize(800, 420);
		Main.setLocationRelativeTo(null);
		Main.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		GridLayout Layout = new GridLayout(3,2);
		Main.setLayout(Layout);
		
	
	
		JButton BuySell = new JButton("Buy and sell items");
		Main.add(BuySell);
		BuySell.setFont(font2);
		BuySell.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				Main.dispose();
				new	BuySell(clientSideConnection);
			}
		});
		
		JButton Supply = new JButton("Supply Managment");
		Main.add(Supply);
		Supply.setFont(font2);
		Supply.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				Main.dispose();
				new	MainMenu(clientSideConnection);
			}
		});
		
		JButton Customer = new JButton("Manage customers");
		Main.add(Customer);
		Customer.setFont(font2);
		Customer.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				Main.dispose();
				new	CustomerMain(clientSideConnection);
			}
		});
		
		JButton Employee = new JButton("Manage Employees");
		Main.add(Employee);
		Employee.setFont(font2);
		Employee.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				Main.dispose();
				new	Employee(clientSideConnection);
			}
		}); 
		
		
		JButton Reports = new JButton("Generate reports");
		Main.add(Reports);
		Reports.setFont(font2);
		Reports.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				Main.dispose();
				new	MainMenu(clientSideConnection);
			}
		});
		
				JButton Chat = new JButton("Start Chat with other branch");
		Main.add(Chat);
		Chat.setFont(font2);
		Chat.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				Main.dispose();
				new	MainMenu(clientSideConnection);
				
			}
		});
		Main.setVisible(true);
	}
}
