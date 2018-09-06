package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
		PriceMenu.setTitle("Manage Prices");
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
		
		JLabel shirt1 = new JLabel();
		shirt1.setIcon(new ImageIcon("./files/whiteshirt.jpg"));
		PriceMain.add(shirt1);
		JLabel shirt2 = new JLabel();
		shirt2.setIcon(new ImageIcon("./files/blackshirt.jpg"));
		PriceMain.add(shirt2);
		
		for (int i = 1; i<=2 ; ++i)
		{
			Price[i-1] = new JTextField("", 4);
			PriceMain.add(Price[i-1]);
		}
		PriceMain.setBorder(BorderFactory.createTitledBorder("Price Menu"));
		PriceMain.setBackground(Color.white);
		PriceMain.setPreferredSize(new Dimension (730 , 510));
		JLabel Item = new JLabel("Item Price:");
		PriceMain.add(Item);
		
		JLabel Item2 = new JLabel("Item Price:");
		PriceMain.add(Item2);
		
		JButton Save = new JButton("Save");
		Save.setEnabled(false);
		PriceMain.add(Save);
		
		JButton Back = new JButton("Back");
		PriceMain.add(Back);
		

		DiscLayout.putConstraint(SpringLayout.WEST, shirt2, 0, SpringLayout.WEST, PriceMain);
		DiscLayout.putConstraint(SpringLayout.NORTH, shirt2, 30, SpringLayout.SOUTH, shirt1);
		
		DiscLayout.putConstraint(SpringLayout.WEST, Item2, 20, SpringLayout.EAST, shirt2);
		DiscLayout.putConstraint(SpringLayout.NORTH, Item2, 50, SpringLayout.NORTH, shirt2);
		
		DiscLayout.putConstraint(SpringLayout.WEST, Item, 20, SpringLayout.EAST, shirt1);
		DiscLayout.putConstraint(SpringLayout.NORTH, Item, 50, SpringLayout.NORTH, shirt1);
		
		DiscLayout.putConstraint(SpringLayout.WEST, Price[1], 15, SpringLayout.EAST, Item2);
		DiscLayout.putConstraint(SpringLayout.NORTH, Price[1], 0, SpringLayout.NORTH, Item2);
		
		DiscLayout.putConstraint(SpringLayout.WEST, Price[0], 15, SpringLayout.EAST, Item);
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
		
		
		
		PriceMenu.setPreferredSize(new Dimension(1200,860));
		PriceMenu.add(PriceMain);
		PriceMenu.pack();
		PriceMenu.setVisible(true);
		}
	

}
