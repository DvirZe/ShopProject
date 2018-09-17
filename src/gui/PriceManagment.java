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
		
		JFrame priceMenu = new JFrame();
		priceMenu.setTitle("Manage Prices");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		Point newLocation = new Point(middle.x - (600 / 2), 
		                              middle.y - (700 / 2));
		priceMenu.setLocation(newLocation);
		Image img = Toolkit.getDefaultToolkit().getImage("./files/PriceIcon.png");
		priceMenu.setIconImage(img);
		priceMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextField[] price = new JTextField[4];
		JLabel[] item = new JLabel[4];
		JPanel priceMain = new JPanel();
		SpringLayout discLayout = new SpringLayout();
		priceMain.setLayout(discLayout);
		
		JLabel shirt1 = new JLabel();
		shirt1.setIcon(new ImageIcon("./files/whiteshirt.jpg"));
		priceMain.add(shirt1);
		JLabel shirt2 = new JLabel();
		shirt2.setIcon(new ImageIcon("./files/blackshirt.jpg"));
		priceMain.add(shirt2);
		JLabel pants1 = new JLabel();
		pants1.setIcon(new ImageIcon("./files/whitepants.jpg"));
		priceMain.add(pants1);
		JLabel pants2 = new JLabel();
		pants2.setIcon(new ImageIcon("./files/blackpants.jpg"));
		priceMain.add(pants2);

		for (int i = 1; i<=4 ; ++i)
		{
			price[i-1] = new JTextField(""+ClientSideConnection.getShop().getPrices(i), 5);
			priceMain.add(price[i-1]);
			item[i-1] = new JLabel("Item Price:");
			priceMain.add(item[i-1]);
		}
		priceMain.setBorder(BorderFactory.createTitledBorder("Price Menu"));
		priceMain.setBackground(Color.white);
		priceMain.setPreferredSize(new Dimension (730 , 510));
		
		JButton save = new JButton("save");
		save.setEnabled(false);
		priceMain.add(save);
		
		JButton back = new JButton("back");
		priceMain.add(back);
		
		
		discLayout.putConstraint(SpringLayout.WEST, pants2, 30, SpringLayout.WEST, priceMain);
		discLayout.putConstraint(SpringLayout.NORTH, pants2, 30, SpringLayout.SOUTH, pants1);
		
		discLayout.putConstraint(SpringLayout.WEST, pants1, 30, SpringLayout.WEST, priceMain);
		discLayout.putConstraint(SpringLayout.NORTH, pants1, 30, SpringLayout.SOUTH, shirt2);

		discLayout.putConstraint(SpringLayout.WEST, shirt2, 0, SpringLayout.WEST, priceMain);
		discLayout.putConstraint(SpringLayout.NORTH, shirt2, 30, SpringLayout.SOUTH, shirt1);
		
		discLayout.putConstraint(SpringLayout.WEST, item[3], 0, SpringLayout.WEST, item[0]);
		discLayout.putConstraint(SpringLayout.NORTH, item[3], 50, SpringLayout.NORTH, pants2);
		
		discLayout.putConstraint(SpringLayout.WEST, item[2], 0, SpringLayout.WEST, item[0]);
		discLayout.putConstraint(SpringLayout.NORTH, item[2], 50, SpringLayout.NORTH, pants1);
		
		discLayout.putConstraint(SpringLayout.WEST, item[1], 20, SpringLayout.EAST, shirt2);
		discLayout.putConstraint(SpringLayout.NORTH, item[1], 50, SpringLayout.NORTH, shirt2);
		
		discLayout.putConstraint(SpringLayout.WEST, item[0], 20, SpringLayout.EAST, shirt1);
		discLayout.putConstraint(SpringLayout.NORTH, item[0], 50, SpringLayout.NORTH, shirt1);
		
		discLayout.putConstraint(SpringLayout.WEST, price[3], 15, SpringLayout.EAST, item[3]);
		discLayout.putConstraint(SpringLayout.NORTH, price[3], 0, SpringLayout.NORTH, item[3]);
		
		
		discLayout.putConstraint(SpringLayout.WEST, price[2], 15, SpringLayout.EAST, item[2]);
		discLayout.putConstraint(SpringLayout.NORTH, price[2], 0, SpringLayout.NORTH, item[2]);
		
		
		discLayout.putConstraint(SpringLayout.WEST, price[1], 15, SpringLayout.EAST, item[1]);
		discLayout.putConstraint(SpringLayout.NORTH, price[1], 0, SpringLayout.NORTH, item[1]);
		
		discLayout.putConstraint(SpringLayout.WEST, price[0], 15, SpringLayout.EAST, item[0]);
		discLayout.putConstraint(SpringLayout.NORTH, price[0], 0, SpringLayout.NORTH, item[0]);
		
		discLayout.putConstraint(SpringLayout.WEST, save, 0, SpringLayout.WEST, price[3]);
		discLayout.putConstraint(SpringLayout.NORTH, save, 0, SpringLayout.SOUTH, pants2);
		
		discLayout.putConstraint(SpringLayout.WEST, back, 5, SpringLayout.EAST, save);
		discLayout.putConstraint(SpringLayout.NORTH, back, 0, SpringLayout.NORTH, save);
		
		
		///////////////////save Enable ActionListener/////////////////////////
		DocumentListener saveEnabler = new DocumentListener(){
	
			@Override
			public void changedUpdate(DocumentEvent e) {
				saveEnable();
				
			}
	
			@Override
			public void insertUpdate(DocumentEvent e) {
				saveEnable();
				
			}
	
			@Override
			public void removeUpdate(DocumentEvent e) {
				saveEnable();
				
			}
			public void saveEnable(){//checks if all of the following fields contains data.
				if (!price[0].getText().isEmpty()
						&& !price[1].getText().isEmpty()
						&& !price[2].getText().isEmpty()
						&& !price[3].getText().isEmpty())
					save.setEnabled(true);
				else
					save.setEnabled(false);
				}
			
		};
		
		///////////////////End of save Enable ActionListener/////////////////////////
		
		//adding the enabler to the relevant fields
		price[0].getDocument().addDocumentListener(saveEnabler);
		price[1].getDocument().addDocumentListener(saveEnabler);
		price[2].getDocument().addDocumentListener(saveEnabler);
		price[3].getDocument().addDocumentListener(saveEnabler);
		
		
		///////////////////back button ActionListener/////////////////////////		
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				priceMenu.dispose();
				new StoreManagment(ClientSideConnection);
			}
		});
		///////////////////end of back button ActionListener/////////////////////////		

		
		
		///////////////////save button ActionListener/////////////////////////
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] prices = new int[4];
				for (int i = 0; i < 4 ; ++i )
				{
					prices[i] = Integer.parseInt(price[i].getText().toString());//gets the current prices from the server
				}
				ClientSideConnection.setPrices(prices);
				save.setEnabled(false);
			}
		});
		///////////////////end of save button ActionListener/////////////////////////	
		
		
		
		priceMenu.setPreferredSize(new Dimension(400,650));
		priceMenu.add(priceMain);
		priceMenu.pack();
		priceMenu.setVisible(true);
		}
	

}
