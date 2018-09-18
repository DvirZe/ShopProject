package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.xml.bind.Marshaller.Listener;

import org.json.simple.parser.ParseException;

import clientSide.ClientSideConnection;
import clientSide.Customer;
import clientSide.Shop;

public class SellMain {

	double discount;
	
	public  SellMain(ClientSideConnection clientSideConnection) {
		discount = 0; //Full price
		Font font1 = new Font("Ariel",Font.PLAIN,10);
		Font font2 = new Font("Ariel",Font.BOLD,14);
		
		JFrame sellMenu = new JFrame();
		sellMenu.setTitle("Sales managment menu");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		Point newLocation = new Point(middle.x - (600 / 2), 
		                              middle.y - (700 / 2));
		sellMenu.setLocation(newLocation);
		Image img = Toolkit.getDefaultToolkit().getImage("./files/sellIcon.png");
		sellMenu.setIconImage(img);
		sellMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JComboBox[] itemQuantity = new JComboBox[4];
		JTextField[] itemprice = new JTextField[4] , itemTotal = new JTextField[4];
		JPanel sellButtons = new JPanel();
		SpringLayout sellLayout = new SpringLayout();
		sellButtons.setLayout(sellLayout);			
		
		for (int i = 0; i<4 ; ++i)
		{
			itemQuantity[i] = new JComboBox<>();
			itemQuantity[i].setPrototypeDisplayValue("How many from this item would you like to buy ?"); //for comboBox size
			itemQuantity[i].setToolTipText("How many from this item would you like to buy ?");
		}
		
		Shop shop = clientSideConnection.getShop();
		for (int i = 1; i<=4 ; ++i)
		{
			itemprice[i-1] = new JTextField(""+shop.getPrices(i), 5);//gets the list of the prices
			for (int j = 0; j<= shop.getInventory(i) ;++j )
			{
				itemQuantity[i-1].addItem(j);//gets the quantity of each item
			}
			itemTotal[i-1] = new JTextField("",5);
			itemTotal[i-1].setEditable(false);
			itemTotal[i-1].setText("0");
			
		}
		
		
		JLabel shirt1 = new JLabel();
		shirt1.setIcon(new ImageIcon("./files/whiteshirtsell.jpg"));
		sellButtons.add(shirt1);
		JLabel shirt2 = new JLabel();
		shirt2.setIcon(new ImageIcon("./files/blackshirtsell.jpg"));
		sellButtons.add(shirt2);
		JLabel pants1 = new JLabel();
		pants1.setIcon(new ImageIcon("./files/whitepantssell.jpg"));
		sellButtons.add(pants1);
		JLabel pants2 = new JLabel();
		pants2.setIcon(new ImageIcon("./files/blackpantssell.jpg"));
		sellButtons.add(pants2);
		
		sellButtons.setBorder(BorderFactory.createTitledBorder("sell Menu"));
		sellButtons.setBackground(Color.white);
		sellButtons.setPreferredSize(new Dimension (730 , 510));
		JLabel cusID = new JLabel("Customer ID:");
		sellButtons.add(cusID);
		sellLayout.putConstraint(SpringLayout.WEST, cusID, 0, SpringLayout.WEST, sellButtons);
		sellLayout.putConstraint(SpringLayout.NORTH, cusID, 10, SpringLayout.NORTH, sellButtons);
		
		JTextField cusIDText = new JTextField("", 20);
		sellButtons.add(cusIDText);
		sellLayout.putConstraint(SpringLayout.WEST, cusIDText, 20, SpringLayout.EAST, cusID);
		sellLayout.putConstraint(SpringLayout.NORTH, cusIDText, 10, SpringLayout.NORTH, sellButtons);
	
		JButton searchCus = new JButton("Search Customer");
		searchCus.setFont(font2);
		searchCus.setEnabled(false);
		sellButtons.add(searchCus);
		sellLayout.putConstraint(SpringLayout.WEST,searchCus , 50, SpringLayout.EAST, cusIDText);
		sellLayout.putConstraint(SpringLayout.NORTH, searchCus, 10, SpringLayout.NORTH, sellButtons);
		
		JLabel type = new JLabel("Item type:");
		type.setFont(font1);
		sellButtons.add(type);
		sellLayout.putConstraint(SpringLayout.WEST, type, 20, SpringLayout.WEST, sellButtons);
		sellLayout.putConstraint(SpringLayout.NORTH, type, 40, SpringLayout.SOUTH, cusID);
		sellLayout.putConstraint(SpringLayout.NORTH, shirt1, 20, SpringLayout.SOUTH, type);
		sellLayout.putConstraint(SpringLayout.WEST, shirt1, 25, SpringLayout.WEST, sellButtons);
		JLabel quantity = new JLabel("quantity:");
		quantity.setFont(font1);
		sellButtons.add(quantity);
		sellLayout.putConstraint(SpringLayout.WEST, quantity, 50, SpringLayout.EAST, type);
		sellLayout.putConstraint(SpringLayout.NORTH, quantity, 40, SpringLayout.SOUTH, cusID);
		itemQuantity[0].setFont(font1);
		JLabel price = new JLabel("Item price:");
		price.setFont(font1);
		sellButtons.add(price);
		sellLayout.putConstraint(SpringLayout.WEST, price, 250, SpringLayout.EAST, quantity);
		sellLayout.putConstraint(SpringLayout.NORTH, price, 40, SpringLayout.SOUTH, cusID);
		JLabel itemTotalPrice = new JLabel("Item total price");
		itemTotalPrice.setFont(font1);
		sellButtons.add(itemTotalPrice);
		sellLayout.putConstraint(SpringLayout.WEST, itemTotalPrice, 100, SpringLayout.EAST, price);
		sellLayout.putConstraint(SpringLayout.NORTH, itemTotalPrice, 40, SpringLayout.SOUTH, cusID);
		JLabel sum = new JLabel("Total price:");
		sum.setFont(font2);
		sellButtons.add(sum);
		sellLayout.putConstraint(SpringLayout.WEST, sum, 20, SpringLayout.EAST, price);
		sellLayout.putConstraint(SpringLayout.NORTH, sum, 50, SpringLayout.SOUTH, itemprice[3]);
		JLabel discPrec = new JLabel("Discount precentage:");
		discPrec.setFont(font2);
		sellButtons.add(discPrec);
		sellLayout.putConstraint(SpringLayout.EAST, discPrec, 0, SpringLayout.WEST, price);
		sellLayout.putConstraint(SpringLayout.NORTH, discPrec, 50, SpringLayout.SOUTH, itemprice[3]);
	
		sellButtons.add(itemQuantity[0]);
		sellLayout.putConstraint(SpringLayout.WEST, itemQuantity[0], 50, SpringLayout.EAST, type);
		sellLayout.putConstraint(SpringLayout.NORTH, itemQuantity[0], 20, SpringLayout.SOUTH, quantity);
		itemprice[0].setEditable(false);
		sellButtons.add(itemprice[0]);
		sellLayout.putConstraint(SpringLayout.WEST, itemprice[0], 250, SpringLayout.EAST, quantity);
		sellLayout.putConstraint(SpringLayout.NORTH, itemprice[0], 0, SpringLayout.NORTH, itemQuantity[0]);
		sellButtons.add(itemTotal[0]);
		sellLayout.putConstraint(SpringLayout.WEST, itemTotal[0], 0, SpringLayout.WEST, itemTotalPrice);
		sellLayout.putConstraint(SpringLayout.NORTH, itemTotal[0], 20, SpringLayout.SOUTH, price);

		sellLayout.putConstraint(SpringLayout.NORTH, shirt2, 30, SpringLayout.SOUTH, shirt1);
		sellLayout.putConstraint(SpringLayout.WEST, shirt2, 0, SpringLayout.WEST, shirt1);
		itemQuantity[1].setFont(font1);
		sellButtons.add(itemQuantity[1]);
		sellLayout.putConstraint(SpringLayout.WEST, itemQuantity[1], 50, SpringLayout.EAST, type);
		sellLayout.putConstraint(SpringLayout.NORTH, itemQuantity[1], 50, SpringLayout.SOUTH, itemQuantity[0]);
		itemprice[1].setEditable(false);
		sellButtons.add(itemprice[1]);
		sellLayout.putConstraint(SpringLayout.WEST, itemprice[1], 250, SpringLayout.EAST, quantity);
		sellLayout.putConstraint(SpringLayout.NORTH, itemprice[1], 0, SpringLayout.NORTH, itemQuantity[1]);
		sellButtons.add(itemTotal[1]);
		sellLayout.putConstraint(SpringLayout.WEST, itemTotal[1], 0, SpringLayout.WEST, itemTotalPrice);
		sellLayout.putConstraint(SpringLayout.NORTH, itemTotal[1], 50, SpringLayout.SOUTH, itemprice[0]);
		
		sellLayout.putConstraint(SpringLayout.NORTH, pants1, 30, SpringLayout.SOUTH, shirt2);
		sellLayout.putConstraint(SpringLayout.WEST, pants1, 10, SpringLayout.WEST, shirt2);
		itemQuantity[2].setFont(font1);
		sellButtons.add(itemQuantity[2]);
		sellLayout.putConstraint(SpringLayout.WEST, itemQuantity[2], 50, SpringLayout.EAST, type);
		sellLayout.putConstraint(SpringLayout.NORTH, itemQuantity[2], 50, SpringLayout.SOUTH, itemQuantity[1]);
		itemprice[2].setEditable(false);
		sellButtons.add(itemprice[2]);
		sellLayout.putConstraint(SpringLayout.WEST, itemprice[2], 250, SpringLayout.EAST, quantity);
		sellLayout.putConstraint(SpringLayout.NORTH, itemprice[2], 0, SpringLayout.NORTH, itemQuantity[2]);
		sellButtons.add(itemTotal[2]);
		sellLayout.putConstraint(SpringLayout.WEST, itemTotal[2], 0, SpringLayout.WEST, itemTotalPrice);
		sellLayout.putConstraint(SpringLayout.NORTH, itemTotal[2], 50, SpringLayout.SOUTH, itemprice[1]);
		
		sellLayout.putConstraint(SpringLayout.NORTH, pants2, 20, SpringLayout.SOUTH, pants1);
		sellLayout.putConstraint(SpringLayout.WEST, pants2, 0, SpringLayout.WEST, pants1);
		itemQuantity[3].setFont(font1);
		sellButtons.add(itemQuantity[3]);
		sellLayout.putConstraint(SpringLayout.WEST, itemQuantity[3], 50, SpringLayout.EAST, type);
		sellLayout.putConstraint(SpringLayout.NORTH, itemQuantity[3], 50, SpringLayout.SOUTH, itemQuantity[2]);
		itemprice[3].setEditable(false);
		sellButtons.add(itemprice[3]);
		sellLayout.putConstraint(SpringLayout.WEST, itemprice[3], 250, SpringLayout.EAST, quantity);
		sellLayout.putConstraint(SpringLayout.NORTH, itemprice[3], 0, SpringLayout.NORTH, itemQuantity[3]);
		sellButtons.add(itemTotal[3]);
		sellLayout.putConstraint(SpringLayout.WEST, itemTotal[3], 0, SpringLayout.WEST, itemTotalPrice);
		sellLayout.putConstraint(SpringLayout.NORTH, itemTotal[3], 50, SpringLayout.SOUTH, itemprice[2]);
		JTextField sumText = new JTextField("0" , 5);
		sumText.setEditable(false);
		sellLayout.putConstraint(SpringLayout.WEST, sumText, 0, SpringLayout.WEST, itemTotalPrice);
		sellLayout.putConstraint(SpringLayout.NORTH, sumText, 50, SpringLayout.SOUTH, itemprice[3]);
		sellButtons.add(sumText);
		JTextField discPrecText = new JTextField("0.0%" , 5);
		discPrecText.setEditable(false);
		sellLayout.putConstraint(SpringLayout.WEST, discPrecText, 0, SpringLayout.WEST, itemprice[0]);
		sellLayout.putConstraint(SpringLayout.NORTH, discPrecText, 50, SpringLayout.SOUTH, itemprice[3]);
		sellButtons.add(discPrecText);
		
		JButton sell = new JButton("sell");
		sell.setFont(font2);
		sellButtons.add(sell);
		sellLayout.putConstraint(SpringLayout.WEST,sell , 35, SpringLayout.WEST,itemprice[3] );
		sellLayout.putConstraint(SpringLayout.NORTH, sell, 100, SpringLayout.SOUTH, itemprice[3]);
		sell.setEnabled(false);
		
		JButton Back = new JButton("Back");
		Back.setFont(font2);
		sellButtons.add(Back);
		sellLayout.putConstraint(SpringLayout.WEST,Back , 50, SpringLayout.EAST, sell);
		sellLayout.putConstraint(SpringLayout.NORTH, Back, 100, SpringLayout.SOUTH, itemprice[3]);
		//////////////////back ActionListener////////////////
		Back.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				sellMenu.dispose();
				new	MainMenu(clientSideConnection);
			}
		});
		//////////////////End of back ActionListener////////////////
		
		JLabel clientNotFound = new JLabel("Client not found");
		sellLayout.putConstraint(SpringLayout.WEST,clientNotFound , 150, SpringLayout.WEST, sellButtons);
		sellLayout.putConstraint(SpringLayout.NORTH, clientNotFound, 30, SpringLayout.NORTH, sellButtons);
		clientNotFound.setFont(font2);
		clientNotFound.setForeground(Color.RED);
		sellButtons.add(clientNotFound);
		clientNotFound.setVisible(false);
		
		for (int i =0; i < 4; i++) 
		{
		    final int index = i; // assign to temporary variable
			//////////////////itemQuantity ActionListener////////////////
		    itemQuantity[index].addActionListener(new ActionListener() 
		    {
		        @Override
		        public void actionPerformed(ActionEvent e) 
		        {
		        	if (itemQuantity[index].getItemCount() != 0)//calculates the price, total item price and total sell price
		        	{
			    		int Myprice = Integer.valueOf(itemprice[index].getText());
						int Myquantity = Integer.parseInt(itemQuantity[index].getSelectedItem().toString());
						itemTotal[index].setText(""+Myprice*Myquantity);
						double sum = (Integer.parseInt(itemTotal[0].getText()) + Integer.parseInt(itemTotal[1].getText()) + Integer.parseInt(itemTotal[2].getText()) + Integer.parseInt(itemTotal[3].getText())) * (1-discount);
						sumText.setText(new String(""+sum));
						clientSideConnection.getShop().getCart().cartUpdate(index, Myquantity);
						if (sum != 0.0)
							sell.setEnabled(true);
						else
							sell.setEnabled(false);
		        	}
					
		        }
		    });
			//////////////////End of itemQuantity ActionListener////////////////
		}

		//////////////////////Get Customer for discount/////////////////////////
		searchCus.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				Customer customer = null;
					try {
						System.out.println(cusIDText.getText());
						customer = clientSideConnection.findCustomer(cusIDText.getText());
					} catch (IOException | ParseException e) {
						e.printStackTrace();
					}
					
					if (customer != null)//if the client is already exists gets the discount percentage
					{
						discount = clientSideConnection.getShop().getDiscountForCustomer(customer.getType());
						clientSideConnection.getShop().getCart().setCustomer(cusIDText.getText());
						clientNotFound.setVisible(false);
					}
					else //if the client is not exists sets the discount percentage to 0
					{
						discount = 0.0;
						clientNotFound.setVisible(true);
					}
					discPrecText.setText(discount+"%");
					double sum = (Integer.parseInt(itemTotal[0].getText()) + Integer.parseInt(itemTotal[1].getText()) + Integer.parseInt(itemTotal[2].getText()) + Integer.parseInt(itemTotal[3].getText())) * (1-discount);
					sumText.setText(new String(""+sum));

			}
		});
		////////////////////End Get Customer for discount//////////////////////
		
		
		
		//////////////////////sellActionListener/////////////////////////
		ActionListener sellActionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Boolean quantityTest = true;
				try {
					clientSideConnection.updateInventory();
				} catch (IOException | ParseException e1) {
					e1.printStackTrace();
				}
				for (int i=1;i<=4;++i)
				{
					if (Integer.parseInt(itemQuantity[i-1].getSelectedItem().toString()) > clientSideConnection.getShop().getInventory(i))//checks if you try to sell more items than you have in the supply
					{
						quantityTest = false;
						JOptionPane pane = new JOptionPane();
						JOptionPane.showMessageDialog(pane, "Can't sell more then you have at the shop\n the panel will update now.", "Error on item " + i, JOptionPane.PLAIN_MESSAGE);
						break;	
					}
				}
				if (quantityTest == true)
				{
					clientSideConnection.getShop().getCart().setTotalPrice(Double.parseDouble(sumText.getText().toString()));
					clientSideConnection.endSell();
					cusIDText.setText("");
				}
				for (int i = 0; i<4 ; ++i)//rebuilding the itemQuantity array after the sell
				{
					itemQuantity[i].removeAllItems();
					for (int j = 0; j<= shop.getInventory(i+1) ;++j )
					{
						itemQuantity[i].addItem(j);
					}
				}
			}
		};	
		
		//////////////////////End of sellActionListener/////////////////////////
		
		///////////////////search Enable Action/////////////////////////
		DocumentListener searchEnabler = new DocumentListener(){
		
		@Override
		public void changedUpdate(DocumentEvent e) {
		searchEnable();
		
		}
		
		@Override
		public void insertUpdate(DocumentEvent e) {
		searchEnable();
		
		}
		
		@Override
		public void removeUpdate(DocumentEvent e) {
		searchEnable();
		
		}
		public void searchEnable(){
		searchCus.setEnabled(true);
		}
		};
		///////////////////End of search Enable Action///////////////////
		cusIDText.getDocument().addDocumentListener(searchEnabler);
		sell.addActionListener(sellActionListener);
		
		sellMenu.add(sellButtons);
		sellMenu.pack();
		sellMenu.setVisible(true);
		
	}
}

