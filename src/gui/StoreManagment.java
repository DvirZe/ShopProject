package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.json.simple.parser.ParseException;

import clientSide.ClientSideConnection;

public class StoreManagment {
	
	public StoreManagment(ClientSideConnection clientSideConnection) {
		Font font2 = new Font("Ariel",Font.BOLD,20);
		JFrame storeMgr = new JFrame();
		storeMgr.setTitle("Store Managment");
		storeMgr.setSize(800, 420);
		storeMgr.setLocationRelativeTo(null);
		Image img = Toolkit.getDefaultToolkit().getImage("./files/store_icon.jpg");
		storeMgr.setIconImage(img);
		storeMgr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		storeMgr.setLayout( new BorderLayout());
		
		
	
		JButton discountMgr = new JButton("Manage Discounts");
		storeMgr.add(discountMgr , BorderLayout.WEST);
		discountMgr.setFont(font2);
		////////////////////discount manager ActionListener///////////////
		discountMgr.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				storeMgr.dispose();
				try {
					clientSideConnection.updateDicounts();
				} catch (IOException | ParseException e) {
					e.printStackTrace();
				}
				new	Discount(clientSideConnection);
			}
		});
		//////////////////End of discount manager ActionListener///////////////

		JButton supply = new JButton("Manage supplys");
		storeMgr.add(supply,BorderLayout.CENTER);
		supply.setFont(font2);
		////////////////supply ActionListener///////////////
		supply.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				storeMgr.dispose();
				try {
					clientSideConnection.updateInventory();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new	SupplyManagment(clientSideConnection);
			}
		});
		////////////////End of supply ActionListener///////////////
		JButton prices = new JButton("Manage pricess");
		storeMgr.add(prices,BorderLayout.EAST);
		prices.setFont(font2);
		////////////////prices ActionListener///////////////
		prices.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				try {
					clientSideConnection.updatePrices();
				} catch (IOException | ParseException e) {
					e.printStackTrace();
				}
				storeMgr.dispose();
				new	PriceManagment(clientSideConnection);
			}
		});
		////////////////End of prices ActionListener///////////////
		JButton back = new JButton("back to Main Menu");
		back.setFont(font2);
		storeMgr.add(back,BorderLayout.SOUTH);
		////////////////Back ActionListener///////////////
		back.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				storeMgr.dispose();
				new	MainMenu(clientSideConnection);
			}
		});
		////////////////End of Back ActionListener///////////////
		
		storeMgr.setVisible(true);
	}

}
