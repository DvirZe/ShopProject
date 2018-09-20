package gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultCaret;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import clientSide.ClientSideConnection;
import clientSide.Worker;

public class MainMenu {
	public MainMenu(ClientSideConnection clientSideConnection) {
		Font font2 = new Font("Ariel",Font.BOLD,20);
		JFrame main = new JFrame();
		main.setTitle("Store Managment");
		main.setSize(800, 420);
		main.setLocationRelativeTo(null);
		Image img = Toolkit.getDefaultToolkit().getImage("./files/store_icon.jpg");
		main.setIconImage(img);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridLayout layout = new GridLayout(3,2);
		main.setLayout(layout);
		JButton buySell = new JButton("Sell items");
		main.add(buySell);
		buySell.setFont(font2);
////////////////////////ActionListener For buySell/////////////////////////		
		buySell.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				try {//request from the server the  variables that are necessary for the sell menu
					clientSideConnection.updateInventory();
					clientSideConnection.updateDicounts();
					clientSideConnection.updatePrices();
				} catch (IOException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				main.dispose();
				new	SellMain(clientSideConnection);
			}
		});
////////////////////////End of ActionListener For buySell////////////////////		
		
		JButton storeMgr = new JButton("Store Managment");
		main.add(storeMgr);
		storeMgr.setEnabled(clientSideConnection.isManager());//enabling the Button only for managers 
		storeMgr.setFont(font2);
////////////////////////ActionListener For store management/////////////////////////		
		storeMgr.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				main.dispose();
				new	StoreManagment(clientSideConnection);
			}
		});
////////////////////////End of ActionListener For store management//////////////////
		
		JButton customer = new JButton("Manage customers");
		customer.setEnabled(!clientSideConnection.isSeller());//enabling the Button only for managers or cashiers
		main.add(customer);
		customer.setFont(font2);
////////////////////////ActionListener For customer/////////////////////////	
		customer.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				main.dispose();
				new	AddCus(clientSideConnection);
			}
		});
////////////////////////End of ActionListener For customer//////////////////
		
		JButton employee = new JButton("Manage employees");
		employee.setEnabled(clientSideConnection.isManager());//enabling the Button only for managers 
		main.add(employee);
		employee.setFont(font2);
////////////////////////ActionListener For employee/////////////////////////	
		employee.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				main.dispose();
				new	Employee(clientSideConnection);
			}
		}); 
////////////////////////End of ActionListener For employee//////////////////
		
		JButton reports = new JButton("Generate reports");
		reports.setEnabled(clientSideConnection.isManager());//enabling the Button only for managers 
		main.add(reports);
		reports.setFont(font2);	
////////////////////////ActionListener For reports/////////////////////////	
		reports.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				main.dispose();
				new	Reports(clientSideConnection);
			}
		});
////////////////////////End of ActionListener For reports//////////////////
		
		JButton chat = new JButton("Start chat with other branch");
		
		main.addWindowFocusListener(new WindowFocusListener() {
			
			@Override
			public void windowLostFocus(WindowEvent arg0) {
				// TODO Auto-generated method stub
				chat.setEnabled(clientSideConnection.isFreeToChat());
			}
			
			@Override
			public void windowGainedFocus(WindowEvent arg0) {
				// TODO Auto-generated method stub
				chat.setEnabled(clientSideConnection.isFreeToChat());
			}
		});
		main.add(chat);
		chat.setFont(font2);
////////////////////////ActionListener For Chat/////////////////////////	
		chat.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				try {
					new ChatGui(clientSideConnection, false);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		////////////////////////End of ActionListener For Chat//////////////////
		
		main.setVisible(true);
	}
}
