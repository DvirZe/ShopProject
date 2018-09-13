package gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import clientSide.ClientSideConnection;

public class MainMenu {
	public MainMenu(ClientSideConnection clientSideConnection) {
		Font font2 = new Font("Ariel",Font.BOLD,20);
		JFrame Main = new JFrame();
		Main.setTitle("Store Managment");
		Main.setSize(800, 420);
		Main.setLocationRelativeTo(null);
		Image img = Toolkit.getDefaultToolkit().getImage("./files/store_icon.jpg");
		Main.setIconImage(img);
		Main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridLayout Layout = new GridLayout(3,2);
		Main.setLayout(Layout);

		
	
	
		JButton BuySell = new JButton("Sell items");
		Main.add(BuySell);
		BuySell.setFont(font2);
		BuySell.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				try {
					clientSideConnection.updateInventory();
					clientSideConnection.updateDicounts();
					clientSideConnection.updatePrices();
				} catch (IOException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Main.dispose();
				new	SellMain(clientSideConnection);
			}
		});
		
		JButton StoreMgr = new JButton("Store Managment");
		Main.add(StoreMgr);
		StoreMgr.setFont(font2);
		StoreMgr.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				Main.dispose();
				new	StoreManagment(clientSideConnection);
			}
		});
		
		JButton Customer = new JButton("Manage customers");
		Main.add(Customer);
		Customer.setFont(font2);
		Customer.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				Main.dispose();
				new	AddCus(clientSideConnection);
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
				new	Reports(clientSideConnection);
			}
		});
		
		JButton Chat = new JButton("Start Chat with other branch");
		Main.add(Chat);
		Chat.setFont(font2);
		Chat.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				//Main.dispose();
				//new	MainMenu(clientSideConnection);
				try {
					clientSideConnection.startChat();
				} catch (IOException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Main.setVisible(true);
	}
}
