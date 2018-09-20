package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import clientSide.ClientSideConnection;
import clientSide.Shop;
import clientSide.Worker;

public class SupplyManagment {
	
	
	public SupplyManagment(ClientSideConnection clientSideConnection) {
		JFrame supplyMenu = new JFrame();
		supplyMenu.setTitle("Manage Supplies");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		Point newLocation = new Point(middle.x - (600 / 2), 
		                              middle.y - (700 / 2));
		supplyMenu.setLocation(newLocation);
		Image img = Toolkit.getDefaultToolkit().getImage("./files/SupIcon.png");
		supplyMenu.setIconImage(img);
		supplyMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextField[] quantity = new JTextField[4];
		JLabel[] item = new JLabel[4];
		JPanel quantityMain = new JPanel();
		SpringLayout discLayout = new SpringLayout();
		quantityMain.setLayout(discLayout);
		
		JLabel shirt1 = new JLabel();
		shirt1.setIcon(new ImageIcon("./files/whiteshirt.jpg"));
		quantityMain.add(shirt1);
		JLabel shirt2 = new JLabel();
		shirt2.setIcon(new ImageIcon("./files/blackshirt.jpg"));
		quantityMain.add(shirt2);
		JLabel pants1 = new JLabel();
		pants1.setIcon(new ImageIcon("./files/whitepants.jpg"));
		quantityMain.add(pants1);
		JLabel pants2 = new JLabel();
		pants2.setIcon(new ImageIcon("./files/blackpants.jpg"));
		quantityMain.add(pants2);
		//creates 4 text fields and sets the values to the quantity of each item
		for (int i = 1; i<=4 ; ++i)
		{
			quantity[i-1] = new JTextField("" + clientSideConnection.getShop().getInventory(i), 4);
			quantityMain.add(quantity[i-1]);
			quantity[i-1].setEnabled(true);
			int place = i-1;
			quantity[i-1].addFocusListener(new FocusListener() { //Can't be less than 0 items to sell.
				
				@Override
				public void focusLost(FocusEvent arg0) {
					if (quantity[place].getText().contains("-"))
					{
						quantity[place].setText("0");
					}
				}
				
				@Override
				public void focusGained(FocusEvent arg0) {}
			});
			item[i-1] = new JLabel("Item Quantity:");
			quantityMain.add(item[i-1]);
		}
		quantityMain.setBorder(BorderFactory.createTitledBorder("Supply Menu"));
		quantityMain.setBackground(Color.white);
		quantityMain.setPreferredSize(new Dimension (730 , 510));
		
		JButton save = new JButton("save");
		save.setEnabled(false);
		quantityMain.add(save);
		
		JButton back = new JButton("back");
		quantityMain.add(back);
		
		
		discLayout.putConstraint(SpringLayout.WEST, pants2, 30, SpringLayout.WEST, quantityMain);
		discLayout.putConstraint(SpringLayout.NORTH, pants2, 30, SpringLayout.SOUTH, pants1);
		
		discLayout.putConstraint(SpringLayout.WEST, pants1, 30, SpringLayout.WEST, quantityMain);
		discLayout.putConstraint(SpringLayout.NORTH, pants1, 30, SpringLayout.SOUTH, shirt2);
	
		discLayout.putConstraint(SpringLayout.WEST, shirt2, 0, SpringLayout.WEST, quantityMain);
		discLayout.putConstraint(SpringLayout.NORTH, shirt2, 30, SpringLayout.SOUTH, shirt1);
		
		discLayout.putConstraint(SpringLayout.WEST, item[3], 0, SpringLayout.WEST, item[0]);
		discLayout.putConstraint(SpringLayout.NORTH, item[3], 50, SpringLayout.NORTH, pants2);
		
		discLayout.putConstraint(SpringLayout.WEST, item[2], 0, SpringLayout.WEST, item[0]);
		discLayout.putConstraint(SpringLayout.NORTH, item[2], 50, SpringLayout.NORTH, pants1);
		
		discLayout.putConstraint(SpringLayout.WEST, item[1], 20, SpringLayout.EAST, shirt2);
		discLayout.putConstraint(SpringLayout.NORTH, item[1], 50, SpringLayout.NORTH, shirt2);
		
		discLayout.putConstraint(SpringLayout.WEST, item[0], 20, SpringLayout.EAST, shirt1);
		discLayout.putConstraint(SpringLayout.NORTH, item[0], 50, SpringLayout.NORTH, shirt1);
		
		discLayout.putConstraint(SpringLayout.WEST, quantity[3], 15, SpringLayout.EAST, item[3]);
		discLayout.putConstraint(SpringLayout.NORTH, quantity[3], 0, SpringLayout.NORTH, item[3]);
		
		
		discLayout.putConstraint(SpringLayout.WEST, quantity[2], 15, SpringLayout.EAST, item[2]);
		discLayout.putConstraint(SpringLayout.NORTH, quantity[2], 0, SpringLayout.NORTH, item[2]);
		
		
		discLayout.putConstraint(SpringLayout.WEST, quantity[1], 15, SpringLayout.EAST, item[1]);
		discLayout.putConstraint(SpringLayout.NORTH, quantity[1], 0, SpringLayout.NORTH, item[1]);
		
		discLayout.putConstraint(SpringLayout.WEST, quantity[0], 15, SpringLayout.EAST, item[0]);
		discLayout.putConstraint(SpringLayout.NORTH, quantity[0], 0, SpringLayout.NORTH, item[0]);
		
		discLayout.putConstraint(SpringLayout.WEST, save, 0, SpringLayout.WEST, quantity[3]);
		discLayout.putConstraint(SpringLayout.NORTH, save, 0, SpringLayout.SOUTH, pants2);
		
		discLayout.putConstraint(SpringLayout.WEST, back, 5, SpringLayout.EAST, save);
		discLayout.putConstraint(SpringLayout.NORTH, back, 0, SpringLayout.NORTH, save);
		
		
		//////////////save button enabler////////////////
		DocumentListener saveEnabler = new DocumentListener(){ //Save enable only if nothing empty
	
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
			public void saveEnable(){
				if (!quantity[0].getText().isEmpty()
						&& !quantity[1].getText().isEmpty()
						&& !quantity[2].getText().isEmpty()
						&& !quantity[3].getText().isEmpty())
					save.setEnabled(true);
				else
					save.setEnabled(false);
				}
			
		};
		//////////////save button enabler////////////////
		
		quantity[0].getDocument().addDocumentListener(saveEnabler);
		quantity[1].getDocument().addDocumentListener(saveEnabler);
		quantity[2].getDocument().addDocumentListener(saveEnabler);
		quantity[3].getDocument().addDocumentListener(saveEnabler);
		
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				supplyMenu.dispose();
				new StoreManagment(clientSideConnection);
			}
		});
		
		
		
		save.addActionListener(new ActionListener() { //Send the correct status
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JSONObject items = new JSONObject();
				items.put("shirt1", quantity[0].getText().toString());
				items.put("shirt2", quantity[1].getText().toString());
				items.put("pants1", quantity[2].getText().toString());
				items.put("pants2", quantity[3].getText().toString());
				clientSideConnection.setInventory(items);
				save.setEnabled(false);
			}
		});
		
		
		
		supplyMenu.setPreferredSize(new Dimension(400,650));
		supplyMenu.add(quantityMain);
		supplyMenu.pack();
		supplyMenu.setVisible(true);												
		}

}
