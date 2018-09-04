package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import clientSide.ClientSideConnection;

public class AddCus {
	public  AddCus(ClientSideConnection clientSideConnection) {
		JComboBox<String> ItemType,ItemQuantity,ItemType2,ItemQuantity2, ItemType3, ItemQuantity3, ItemQuantity4, ItemType4;
		Font font = new Font("Ariel",Font.PLAIN,20);
		Font font2 = new Font("Ariel",Font.BOLD,20);
		JFrame AddMenu = new JFrame();
		AddMenu.setTitle("Sales managment menu");
		AddMenu.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//AddMenu.setUndecorated(true);
		AddMenu.setLocationRelativeTo(null);	
		AddMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JPanel AddButtons = new JPanel();
		AddButtons.setBorder(BorderFactory.createTitledBorder("Add Menu"));
		AddButtons.setBackground(Color.white);
		AddButtons.setPreferredSize(new Dimension (400 , 175));
		AddButtons.setLayout(new FlowLayout());	
		ItemType = new JComboBox<String>(new String[] {"To be Connected to File","shirt","Pants"} );
		AddButtons.add(ItemType);
		ItemQuantity = new JComboBox<String>(new String[] {"The quantity of the item from the file from 1 to maximum"} );
		AddButtons.add(ItemQuantity);
		JLabel Price = new JLabel("Item Price");
		AddButtons.add(Price);
		JTextField ItemPrice = new JTextField("" , 20);
		ItemPrice.setFont(font2);
		ItemPrice.setEditable(false);
		AddButtons.add(ItemPrice);
		ItemType2 = new JComboBox<String>(new String[] {"To be Connected to File","shirt","Pants"} );
		AddButtons.add(ItemType2);
		ItemQuantity2 = new JComboBox<String>(new String[] {"The quantity of the item from the file from 1 to maximum"} );
		AddButtons.add(ItemQuantity2);
		JLabel Price2 = new JLabel("Item Price2");
		AddButtons.add(Price2);
		JTextField ItemPrice2 = new JTextField("" , 20);
		ItemPrice2.setFont(font2);
		ItemPrice2.setEditable(false);
		AddButtons.add(ItemPrice2);
		ItemType3 = new JComboBox<String>(new String[] {"To be Connected to File","shirt","Pants"} );
		AddButtons.add(ItemType3);
		ItemQuantity3 = new JComboBox<String>(new String[] {"The quantity of the item from the file from 1 to maximum"} );
		AddButtons.add(ItemQuantity3);
		JLabel Price3 = new JLabel("Item Price3");
		AddButtons.add(Price3);
		JTextField ItemPrice3 = new JTextField("" , 20);
		ItemPrice3.setFont(font2);
		ItemPrice3.setEditable(false);
		AddButtons.add(ItemPrice3);
		ItemType3 = new JComboBox<String>(new String[] {"To be Connected to File","shirt","Pants"} );
		AddButtons.add(ItemType3);
		ItemQuantity4 = new JComboBox<String>(new String[] {"The quantity of the item from the file from 1 to maximum"} );
		AddButtons.add(ItemQuantity4);
		JLabel Price4 = new JLabel("Item Price4");
		AddButtons.add(Price4);
		JTextField ItemPrice4 = new JTextField("" , 20);
		ItemPrice4.setFont(font2);
		ItemPrice4.setEditable(false);
		AddButtons.add(ItemPrice4);
		
		JButton Add = new JButton("Add");
		AddButtons.add(Add, BorderLayout.CENTER);
		Add.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				AddMenu.dispose();
				new	CustomerMain(clientSideConnection);
			}
		});
		AddMenu.add(AddButtons);
		AddMenu.pack();
		AddMenu.setVisible(true);
	}
}
