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
		Image img = Toolkit.getDefaultToolkit().getImage("./files/PriceIcon.png");
		PriceMenu.setIconImage(img);
		PriceMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextField[] price = new JTextField[4];
		JLabel[] item = new JLabel[4];
		JPanel PriceMain = new JPanel();
		SpringLayout DiscLayout = new SpringLayout();
		PriceMain.setLayout(DiscLayout);
		
		JLabel shirt1 = new JLabel();
		shirt1.setIcon(new ImageIcon("./files/whiteshirt.jpg"));
		PriceMain.add(shirt1);
		JLabel shirt2 = new JLabel();
		shirt2.setIcon(new ImageIcon("./files/blackshirt.jpg"));
		PriceMain.add(shirt2);
		JLabel pants1 = new JLabel();
		pants1.setIcon(new ImageIcon("./files/whitepants.jpg"));
		PriceMain.add(pants1);
		JLabel pants2 = new JLabel();
		pants2.setIcon(new ImageIcon("./files/blackpants.jpg"));
		PriceMain.add(pants2);

		for (int i = 1; i<=4 ; ++i)
		{
			price[i-1] = new JTextField(""+ClientSideConnection.getShop().getPrices(i), 5);
			PriceMain.add(price[i-1]);
			item[i-1] = new JLabel("Item Price:");
			PriceMain.add(item[i-1]);
		}
		PriceMain.setBorder(BorderFactory.createTitledBorder("Price Menu"));
		PriceMain.setBackground(Color.white);
		PriceMain.setPreferredSize(new Dimension (730 , 510));
		
		JButton Save = new JButton("Save");
		Save.setEnabled(false);
		PriceMain.add(Save);
		
		JButton Back = new JButton("Back");
		PriceMain.add(Back);
		
		
		DiscLayout.putConstraint(SpringLayout.WEST, pants2, 30, SpringLayout.WEST, PriceMain);
		DiscLayout.putConstraint(SpringLayout.NORTH, pants2, 30, SpringLayout.SOUTH, pants1);
		
		DiscLayout.putConstraint(SpringLayout.WEST, pants1, 30, SpringLayout.WEST, PriceMain);
		DiscLayout.putConstraint(SpringLayout.NORTH, pants1, 30, SpringLayout.SOUTH, shirt2);

		DiscLayout.putConstraint(SpringLayout.WEST, shirt2, 0, SpringLayout.WEST, PriceMain);
		DiscLayout.putConstraint(SpringLayout.NORTH, shirt2, 30, SpringLayout.SOUTH, shirt1);
		
		DiscLayout.putConstraint(SpringLayout.WEST, item[3], 0, SpringLayout.WEST, item[0]);
		DiscLayout.putConstraint(SpringLayout.NORTH, item[3], 50, SpringLayout.NORTH, pants2);
		
		DiscLayout.putConstraint(SpringLayout.WEST, item[2], 0, SpringLayout.WEST, item[0]);
		DiscLayout.putConstraint(SpringLayout.NORTH, item[2], 50, SpringLayout.NORTH, pants1);
		
		DiscLayout.putConstraint(SpringLayout.WEST, item[1], 20, SpringLayout.EAST, shirt2);
		DiscLayout.putConstraint(SpringLayout.NORTH, item[1], 50, SpringLayout.NORTH, shirt2);
		
		DiscLayout.putConstraint(SpringLayout.WEST, item[0], 20, SpringLayout.EAST, shirt1);
		DiscLayout.putConstraint(SpringLayout.NORTH, item[0], 50, SpringLayout.NORTH, shirt1);
		
		DiscLayout.putConstraint(SpringLayout.WEST, price[3], 15, SpringLayout.EAST, item[3]);
		DiscLayout.putConstraint(SpringLayout.NORTH, price[3], 0, SpringLayout.NORTH, item[3]);
		
		
		DiscLayout.putConstraint(SpringLayout.WEST, price[2], 15, SpringLayout.EAST, item[2]);
		DiscLayout.putConstraint(SpringLayout.NORTH, price[2], 0, SpringLayout.NORTH, item[2]);
		
		
		DiscLayout.putConstraint(SpringLayout.WEST, price[1], 15, SpringLayout.EAST, item[1]);
		DiscLayout.putConstraint(SpringLayout.NORTH, price[1], 0, SpringLayout.NORTH, item[1]);
		
		DiscLayout.putConstraint(SpringLayout.WEST, price[0], 15, SpringLayout.EAST, item[0]);
		DiscLayout.putConstraint(SpringLayout.NORTH, price[0], 0, SpringLayout.NORTH, item[0]);
		
		DiscLayout.putConstraint(SpringLayout.WEST, Save, 0, SpringLayout.WEST, price[3]);
		DiscLayout.putConstraint(SpringLayout.NORTH, Save, 0, SpringLayout.SOUTH, pants2);
		
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
				if (!price[0].getText().isEmpty()
						&& !price[1].getText().isEmpty()
						&& !price[2].getText().isEmpty()
						&& !price[3].getText().isEmpty())
					Save.setEnabled(true);
				else
					Save.setEnabled(false);
				}
			
		};
		
		price[0].getDocument().addDocumentListener(SaveEnabler);
		price[1].getDocument().addDocumentListener(SaveEnabler);
		price[2].getDocument().addDocumentListener(SaveEnabler);
		price[3].getDocument().addDocumentListener(SaveEnabler);
		
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
				int[] prices = new int[4];
				for (int i = 0; i < 4 ; ++i )
				{
					prices[i] = Integer.parseInt(price[i].getText().toString());
				}
				ClientSideConnection.setPrices(prices);
				Save.setEnabled(false);
			}
		});
		
		
		
		PriceMenu.setPreferredSize(new Dimension(400,650));
		PriceMenu.add(PriceMain);
		PriceMenu.pack();
		PriceMenu.setVisible(true);
		}
	

}
