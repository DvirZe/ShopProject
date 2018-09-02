package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SpringLayout;

import clientSide.ClientSideConnection;
import clientSide.Shop;

public class SellMain {

	public  SellMain(ClientSideConnection clientSideConnection) {
		JComboBox<String> ItemType,/*ItemQuantity[0],*/ItemType2/*,ItemQuantity[1]*/, ItemType3,/* ItemQuantity[2], ItemQuantity[3],*/ ItemType4;
		Font font1 = new Font("Ariel",Font.PLAIN,10);
		Font font2 = new Font("Ariel",Font.BOLD,14);
		JFrame SellMenu = new JFrame();
		SellMenu.setTitle("Sales managment menu");
		SellMenu.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		SellMenu.setLocationRelativeTo(null);	
		SellMenu.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JComboBox[] ItemQuantity = new JComboBox[4];
		JPanel SellButtons = new JPanel();
		SpringLayout SellLayout = new SpringLayout();
		SellButtons.setLayout(SellLayout);
		
//		GridBagConstraints gc = new GridBagConstraints();
			
		
		for (int i = 0; i<4 ; ++i)
		{
			ItemQuantity[i] = new JComboBox<>();
			ItemQuantity[i].setPrototypeDisplayValue("How many from this item whold you like to buy ?"); //for bombobox size
			ItemQuantity[i].setToolTipText("How many from this item whold you like to buy ?");
		}
		
		Shop shop = clientSideConnection.getShop();
		for (int i = 1; i<=4 ; ++i)
		{
			for (int j = 0; j< shop.getInventory(i) ;++j )
			{
				ItemQuantity[i-1].addItem(j);
			}
		}
		
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
		ItemQuantity[0].setFont(font1);
		SellButtons.add(ItemQuantity[0]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemQuantity[0], 100, SpringLayout.EAST, Type);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemQuantity[0], 50, SpringLayout.SOUTH, Quantity);
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
		ItemQuantity[1].setFont(font1);
		SellButtons.add(ItemQuantity[1]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemQuantity[1], 100, SpringLayout.EAST, Type);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemQuantity[1], 50, SpringLayout.SOUTH, ItemQuantity[0]);
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
		ItemQuantity[2].setFont(font1);
		SellButtons.add(ItemQuantity[2]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemQuantity[2], 100, SpringLayout.EAST, Type);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemQuantity[2], 50, SpringLayout.SOUTH, ItemQuantity[1]);
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
		ItemQuantity[3].setFont(font1);
		SellButtons.add(ItemQuantity[3]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemQuantity[3], 100, SpringLayout.EAST, Type);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemQuantity[3], 50, SpringLayout.SOUTH, ItemQuantity[2]);
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

