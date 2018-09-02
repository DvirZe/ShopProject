package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import clientSide.ClientSideConnection;

public class Employee {

	public  Employee(ClientSideConnection clientSideConnection) {
		JComboBox<String> ItemType,ItemQuantity,ItemType2,ItemQuantity2, ItemType3, ItemQuantity3, ItemQuantity4, ItemType4;
		Font font1 = new Font("Ariel",Font.PLAIN,10);
		Font font2 = new Font("Ariel",Font.BOLD,14);
		JFrame SellMenu = new JFrame();
		SellMenu.setTitle("Sales managment menu");
		SellMenu.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		SellMenu.setLocationRelativeTo(null);	
		SellMenu.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		JPanel SellButtons = new JPanel();
		SpringLayout SellLayout = new SpringLayout();
		SellButtons.setLayout(SellLayout);
		
//		GridBagConstraints gc = new GridBagConstraints();
		
		SellButtons.setBorder(BorderFactory.createTitledBorder("Sell Menu"));
		SellButtons.setBackground(Color.white);
		SellButtons.setPreferredSize(new Dimension (200 , 175));
		//SellButtons.setLayout(new FlowLayout());
		JLabel Type = new JLabel("Item Type:");
		Type.setFont(font1);
		SellButtons.add(Type);
		SellLayout.putConstraint(SpringLayout.WEST, Type, 20, SpringLayout.WEST, SellButtons);
		ItemType = new JComboBox<String>(new String[] {"To be Connected to File","shirt","Pants"} );
		ItemType.setFont(font1);
		SellButtons.add(ItemType);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemType, 50, SpringLayout.SOUTH, Type);
		JLabel Quantity = new JLabel("Quantity:");
		Quantity.setFont(font1);
		SellButtons.add(Quantity);
		SellLayout.putConstraint(SpringLayout.WEST, Quantity, 100, SpringLayout.EAST, Type);
		ItemQuantity = new JComboBox<String>(new String[] {"The quantity of the item from the file from 1 to maximum"} );
		ItemQuantity.setFont(font1);
		SellButtons.add(ItemQuantity);
		SellLayout.putConstraint(SpringLayout.WEST, ItemQuantity, 100, SpringLayout.EAST, Type);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemQuantity, 50, SpringLayout.SOUTH, Quantity);
		JLabel Price = new JLabel("Item Price");
		Price.setFont(font1);
		SellButtons.add(Price);
		SellLayout.putConstraint(SpringLayout.WEST, Price, 200, SpringLayout.EAST, Quantity);
		JTextField ItemPrice = new JTextField("" , 5);
		ItemPrice.setFont(font1);
		ItemPrice.setEditable(false);
		SellButtons.add(ItemPrice);
		SellLayout.putConstraint(SpringLayout.WEST, ItemPrice, 200, SpringLayout.EAST, Quantity);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemPrice, 50, SpringLayout.SOUTH, Price);
		
		ItemType2 = new JComboBox<String>(new String[] {"To be Connected to File","shirt","Pants"} );
		ItemType2.setFont(font1);
		SellButtons.add(ItemType2);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemType2, 50, SpringLayout.SOUTH, ItemType);
		SellLayout.putConstraint(SpringLayout.WEST, Quantity, 100, SpringLayout.EAST, ItemType);
		ItemQuantity2 = new JComboBox<String>(new String[] {"The quantity of the item from the file from 1 to maximum"} );
		ItemQuantity2.setFont(font1);
		SellButtons.add(ItemQuantity2);
		SellLayout.putConstraint(SpringLayout.WEST, ItemQuantity2, 100, SpringLayout.EAST, Type);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemQuantity2, 50, SpringLayout.SOUTH, ItemQuantity);
		JTextField ItemPrice2 = new JTextField("" , 5);
		ItemPrice2.setFont(font1);
		ItemPrice2.setEditable(false);
		SellButtons.add(ItemPrice2);
		SellLayout.putConstraint(SpringLayout.WEST, ItemPrice2, 200, SpringLayout.EAST, Quantity);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemPrice2, 50, SpringLayout.SOUTH, ItemPrice);
		
		ItemType3 = new JComboBox<String>(new String[] {"To be Connected to File","shirt","Pants"} );
		ItemType3.setFont(font1);
		SellButtons.add(ItemType3);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemType3, 50, SpringLayout.SOUTH, ItemType2);
		SellLayout.putConstraint(SpringLayout.WEST, Quantity, 100, SpringLayout.EAST, ItemType);
		ItemQuantity3 = new JComboBox<String>(new String[] {"The quantity of the item from the file from 1 to maximum"} );
		ItemQuantity3.setFont(font1);
		SellButtons.add(ItemQuantity3);
		SellLayout.putConstraint(SpringLayout.WEST, ItemQuantity3, 100, SpringLayout.EAST, Type);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemQuantity3, 50, SpringLayout.SOUTH, ItemQuantity2);
		JTextField ItemPrice3 = new JTextField("" , 5);
		ItemPrice3.setFont(font1);
		ItemPrice3.setEditable(false);
		SellButtons.add(ItemPrice3);
		SellLayout.putConstraint(SpringLayout.WEST, ItemPrice3, 200, SpringLayout.EAST, Quantity);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemPrice3, 50, SpringLayout.SOUTH, ItemPrice2);
		
		ItemType4 = new JComboBox<String>(new String[] {"To be Connected to File","shirt","Pants"} );
		ItemType4.setFont(font1);
		SellButtons.add(ItemType4);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemType4, 50, SpringLayout.SOUTH, ItemType3);
		SellLayout.putConstraint(SpringLayout.WEST, Quantity, 100 , SpringLayout.EAST, ItemType);
		ItemQuantity4 = new JComboBox<String>(new String[] {"The quantity of the item from the file from 1 to maximum"} );
		ItemQuantity4.setFont(font1);
		SellButtons.add(ItemQuantity4);
		SellLayout.putConstraint(SpringLayout.WEST, ItemQuantity4, 100, SpringLayout.EAST, Type);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemQuantity4, 50, SpringLayout.SOUTH, ItemQuantity3);
		JTextField ItemPrice4 = new JTextField("" , 5);
		ItemPrice4.setFont(font1);
		ItemPrice4.setEditable(false);
		SellButtons.add(ItemPrice4);
		SellLayout.putConstraint(SpringLayout.WEST, ItemPrice4, 200, SpringLayout.EAST, Quantity);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemPrice4, 50, SpringLayout.SOUTH, ItemPrice3);

		
		JButton Sell = new JButton("Sell");
		Sell.setFont(font2);
		SellButtons.add(Sell);
		SellLayout.putConstraint(SpringLayout.WEST,Sell , 100, SpringLayout.EAST, ItemPrice);
		SellLayout.putConstraint(SpringLayout.NORTH, Sell, 100, SpringLayout.SOUTH, ItemPrice4);
		Sell.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				SellMenu.dispose();
				new	SellMain(clientSideConnection);
			}
		});
		
		JButton Back = new JButton("Back");
		Back.setFont(font2);
		SellButtons.add(Back);
		SellLayout.putConstraint(SpringLayout.WEST,Back , 50, SpringLayout.EAST, Sell);
		SellLayout.putConstraint(SpringLayout.NORTH, Back, 100, SpringLayout.SOUTH, ItemPrice4);
		Back.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				SellMenu.dispose();
				new	BuySell(clientSideConnection);
			}
		});
		
		
		SellMenu.add(SellButtons);
		SellMenu.pack();
		SellMenu.setVisible(true);
	}
	
}
