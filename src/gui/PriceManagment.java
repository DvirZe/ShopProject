package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import clientSide.ClientSideConnection;
import clientSide.Shop;
import clientSide.Worker;

public class PriceManagment {
	
		public PriceManagment(ClientSideConnection ClientSideConnection) {
		
		Font font2 = new Font("Ariel",Font.BOLD,14);
		JFrame PriceMenu = new JFrame();
		PriceMenu.setTitle("Sales managment menu");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		Point newLocation = new Point(middle.x - (600 / 2), 
		                              middle.y - (700 / 2));
		PriceMenu.setLocation(newLocation);
		PriceMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextField[] Price = new JTextField[4];
		JPanel PriceMain = new JPanel();
		SpringLayout DiscLayout = new SpringLayout();
		PriceMain.setLayout(DiscLayout);
		
		for (int i = 1; i<=2 ; ++i)
		{
			Price[i-1] = new JTextField("", 4);
			PriceMain.add(Price[i-1]);
		}
		PriceMain.setBorder(BorderFactory.createTitledBorder("Sell Menu"));
		PriceMain.setBackground(Color.white);
		PriceMain.setPreferredSize(new Dimension (730 , 510));
		JLabel Item = new JLabel("Item Price:");
		PriceMain.add(Item);
		
		JLabel ReturnDisc = new JLabel("Retuened Customer Discount:");
		PriceMain.add(ReturnDisc);
		
		JButton Save = new JButton("Save");
		Save.setEnabled(false);
		PriceMain.add(Save);
		
		JButton Back = new JButton("Back");
		PriceMain.add(Back);
		
		
		
		DiscLayout.putConstraint(SpringLayout.WEST, ReturnDisc, 0, SpringLayout.WEST, PriceMain);
		DiscLayout.putConstraint(SpringLayout.NORTH, ReturnDisc, 30, SpringLayout.NORTH, Item);
		
		DiscLayout.putConstraint(SpringLayout.WEST, Item, 0, SpringLayout.WEST, PriceMain);
		DiscLayout.putConstraint(SpringLayout.NORTH, Item, 10, SpringLayout.NORTH, PriceMain);
		
		DiscLayout.putConstraint(SpringLayout.WEST, Price[1], 15, SpringLayout.EAST, ReturnDisc);
		DiscLayout.putConstraint(SpringLayout.NORTH, Price[1], 0, SpringLayout.NORTH, ReturnDisc);
		
		DiscLayout.putConstraint(SpringLayout.WEST, Price[0], 0, SpringLayout.WEST, Price[1]);
		DiscLayout.putConstraint(SpringLayout.NORTH, Price[0], 0, SpringLayout.NORTH, Item);
		
		DiscLayout.putConstraint(SpringLayout.WEST, Save, 0, SpringLayout.WEST, Price[1]);
		DiscLayout.putConstraint(SpringLayout.NORTH, Save, 10, SpringLayout.SOUTH, Price[1]);
		
		DiscLayout.putConstraint(SpringLayout.WEST, Back, 5, SpringLayout.EAST, Save);
		DiscLayout.putConstraint(SpringLayout.NORTH, Back, 0, SpringLayout.NORTH, Save);
		
		DocumentListener SaveEnabler = new DocumentListener(){
	
			@Override
			public void changedUpdate(DocumentEvent e) {
				SaveEnable();
				
			}
	
			@Override
			public void insertUpdate(DocumentEvent e) {
				SaveEnable();
				
			}
	
			@Override
			public void removeUpdate(DocumentEvent e) {
				SaveEnable();
				
			}
			public void SaveEnable(){
				if (!Price[0].getText().isEmpty()
						&& !Price[1].getText().isEmpty())
					Save.setEnabled(true);
				else
					Save.setEnabled(false);
				}
			
		};
		
		Price[0].getDocument().addDocumentListener(SaveEnabler);
		Price[1].getDocument().addDocumentListener(SaveEnabler);
		
		Back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				PriceMenu.dispose();
				new StoreManagment(ClientSideConnection);
			}
		});
		
		
		
		Save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
	
			}
		});
		
		
		
		PriceMenu.setPreferredSize(new Dimension(340,160));
		PriceMenu.add(PriceMain);
		PriceMenu.pack();
		PriceMenu.setVisible(true);
		}
	

}
