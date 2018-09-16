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
		
		JFrame SellMenu = new JFrame();
		SellMenu.setTitle("Sales managment menu");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		Point newLocation = new Point(middle.x - (600 / 2), 
		                              middle.y - (700 / 2));
		SellMenu.setLocation(newLocation);
		Image img = Toolkit.getDefaultToolkit().getImage("./files/SellIcon.png");
		SellMenu.setIconImage(img);
		SellMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JComboBox[] ItemQuantity = new JComboBox[4];
		JTextField[] ItemPrice = new JTextField[4] , ItemTotal = new JTextField[4];
		JPanel SellButtons = new JPanel();
		SpringLayout SellLayout = new SpringLayout();
		SellButtons.setLayout(SellLayout);			
		
		for (int i = 0; i<4 ; ++i)
		{
			ItemQuantity[i] = new JComboBox<>();
			ItemQuantity[i].setPrototypeDisplayValue("How many from this item whold you like to buy ?"); //for comboBox size
			ItemQuantity[i].setToolTipText("How many from this item whold you like to buy ?");
		}
		
		Shop shop = clientSideConnection.getShop();
		for (int i = 1; i<=4 ; ++i)
		{
			ItemPrice[i-1] = new JTextField(""+shop.getPrices(i), 5);
			for (int j = 0; j<= shop.getInventory(i) ;++j )
			{
				ItemQuantity[i-1].addItem(j);
			}
			ItemTotal[i-1] = new JTextField("",5);
			ItemTotal[i-1].setEditable(false);
			ItemTotal[i-1].setText("0");
			
		}
		
		
		JLabel shirt1 = new JLabel();
		shirt1.setIcon(new ImageIcon("./files/whiteshirtSell.jpg"));
		SellButtons.add(shirt1);
		JLabel shirt2 = new JLabel();
		shirt2.setIcon(new ImageIcon("./files/blackshirtSell.jpg"));
		SellButtons.add(shirt2);
		JLabel pants1 = new JLabel();
		pants1.setIcon(new ImageIcon("./files/whitepantsSell.jpg"));
		SellButtons.add(pants1);
		JLabel pants2 = new JLabel();
		pants2.setIcon(new ImageIcon("./files/blackpantsSell.jpg"));
		SellButtons.add(pants2);
		
		SellButtons.setBorder(BorderFactory.createTitledBorder("Sell Menu"));
		SellButtons.setBackground(Color.white);
		SellButtons.setPreferredSize(new Dimension (730 , 510));
		JLabel CusID = new JLabel("Customer ID:");
		SellButtons.add(CusID);
		SellLayout.putConstraint(SpringLayout.WEST, CusID, 0, SpringLayout.WEST, SellButtons);
		SellLayout.putConstraint(SpringLayout.NORTH, CusID, 10, SpringLayout.NORTH, SellButtons);
		
		JTextField CusIDText = new JTextField("", 20);
		SellButtons.add(CusIDText);
		SellLayout.putConstraint(SpringLayout.WEST, CusIDText, 20, SpringLayout.EAST, CusID);
		SellLayout.putConstraint(SpringLayout.NORTH, CusIDText, 10, SpringLayout.NORTH, SellButtons);

		
		JButton SearchCus = new JButton("Search Customer");
		SearchCus.setFont(font2);
		SellButtons.add(SearchCus);
		SellLayout.putConstraint(SpringLayout.WEST,SearchCus , 50, SpringLayout.EAST, CusIDText);
		SellLayout.putConstraint(SpringLayout.NORTH, SearchCus, 10, SpringLayout.NORTH, SellButtons);
		
		JLabel Type = new JLabel("Item Type:");
		Type.setFont(font1);
		SellButtons.add(Type);
		SellLayout.putConstraint(SpringLayout.WEST, Type, 20, SpringLayout.WEST, SellButtons);
		SellLayout.putConstraint(SpringLayout.NORTH, Type, 40, SpringLayout.SOUTH, CusID);
		SellLayout.putConstraint(SpringLayout.NORTH, shirt1, 20, SpringLayout.SOUTH, Type);
		SellLayout.putConstraint(SpringLayout.WEST, shirt1, 25, SpringLayout.WEST, SellButtons);
		JLabel Quantity = new JLabel("Quantity:");
		Quantity.setFont(font1);
		SellButtons.add(Quantity);
		SellLayout.putConstraint(SpringLayout.WEST, Quantity, 50, SpringLayout.EAST, Type);
		SellLayout.putConstraint(SpringLayout.NORTH, Quantity, 40, SpringLayout.SOUTH, CusID);
		ItemQuantity[0].setFont(font1);
		JLabel Price = new JLabel("Item Price:");
		Price.setFont(font1);
		SellButtons.add(Price);
		SellLayout.putConstraint(SpringLayout.WEST, Price, 250, SpringLayout.EAST, Quantity);
		SellLayout.putConstraint(SpringLayout.NORTH, Price, 40, SpringLayout.SOUTH, CusID);
		JLabel ItemTotalPrice = new JLabel("Item total price");
		ItemTotalPrice.setFont(font1);
		SellButtons.add(ItemTotalPrice);
		SellLayout.putConstraint(SpringLayout.WEST, ItemTotalPrice, 100, SpringLayout.EAST, Price);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemTotalPrice, 40, SpringLayout.SOUTH, CusID);
		JLabel Sum = new JLabel("Total price:");
		Sum.setFont(font2);
		SellButtons.add(Sum);
		SellLayout.putConstraint(SpringLayout.WEST, Sum, 20, SpringLayout.EAST, Price);
		SellLayout.putConstraint(SpringLayout.NORTH, Sum, 50, SpringLayout.SOUTH, ItemPrice[3]);
		JLabel DiscPrec = new JLabel("Discount precentage:");
		DiscPrec.setFont(font2);
		SellButtons.add(DiscPrec);
		SellLayout.putConstraint(SpringLayout.EAST, DiscPrec, 0, SpringLayout.WEST, Price);
		SellLayout.putConstraint(SpringLayout.NORTH, DiscPrec, 50, SpringLayout.SOUTH, ItemPrice[3]);
	

		SellButtons.add(ItemQuantity[0]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemQuantity[0], 50, SpringLayout.EAST, Type);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemQuantity[0], 20, SpringLayout.SOUTH, Quantity);
		ItemPrice[0].setEditable(false);
		SellButtons.add(ItemPrice[0]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemPrice[0], 250, SpringLayout.EAST, Quantity);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemPrice[0], 0, SpringLayout.NORTH, ItemQuantity[0]);
		SellButtons.add(ItemTotal[0]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemTotal[0], 0, SpringLayout.WEST, ItemTotalPrice);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemTotal[0], 20, SpringLayout.SOUTH, Price);

		SellLayout.putConstraint(SpringLayout.NORTH, shirt2, 30, SpringLayout.SOUTH, shirt1);
		SellLayout.putConstraint(SpringLayout.WEST, shirt2, 0, SpringLayout.WEST, shirt1);
		ItemQuantity[1].setFont(font1);
		SellButtons.add(ItemQuantity[1]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemQuantity[1], 50, SpringLayout.EAST, Type);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemQuantity[1], 50, SpringLayout.SOUTH, ItemQuantity[0]);
		ItemPrice[1].setEditable(false);
		SellButtons.add(ItemPrice[1]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemPrice[1], 250, SpringLayout.EAST, Quantity);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemPrice[1], 0, SpringLayout.NORTH, ItemQuantity[1]);
		SellButtons.add(ItemTotal[1]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemTotal[1], 0, SpringLayout.WEST, ItemTotalPrice);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemTotal[1], 50, SpringLayout.SOUTH, ItemPrice[0]);
		
		SellLayout.putConstraint(SpringLayout.NORTH, pants1, 30, SpringLayout.SOUTH, shirt2);
		SellLayout.putConstraint(SpringLayout.WEST, pants1, 10, SpringLayout.WEST, shirt2);
		ItemQuantity[2].setFont(font1);
		SellButtons.add(ItemQuantity[2]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemQuantity[2], 50, SpringLayout.EAST, Type);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemQuantity[2], 50, SpringLayout.SOUTH, ItemQuantity[1]);
		ItemPrice[2].setEditable(false);
		SellButtons.add(ItemPrice[2]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemPrice[2], 250, SpringLayout.EAST, Quantity);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemPrice[2], 0, SpringLayout.NORTH, ItemQuantity[2]);
		SellButtons.add(ItemTotal[2]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemTotal[2], 0, SpringLayout.WEST, ItemTotalPrice);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemTotal[2], 50, SpringLayout.SOUTH, ItemPrice[1]);
		
		SellLayout.putConstraint(SpringLayout.NORTH, pants2, 20, SpringLayout.SOUTH, pants1);
		SellLayout.putConstraint(SpringLayout.WEST, pants2, 0, SpringLayout.WEST, pants1);
		ItemQuantity[3].setFont(font1);
		SellButtons.add(ItemQuantity[3]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemQuantity[3], 50, SpringLayout.EAST, Type);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemQuantity[3], 50, SpringLayout.SOUTH, ItemQuantity[2]);
		ItemPrice[3].setEditable(false);
		SellButtons.add(ItemPrice[3]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemPrice[3], 250, SpringLayout.EAST, Quantity);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemPrice[3], 0, SpringLayout.NORTH, ItemQuantity[3]);
		SellButtons.add(ItemTotal[3]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemTotal[3], 0, SpringLayout.WEST, ItemTotalPrice);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemTotal[3], 50, SpringLayout.SOUTH, ItemPrice[2]);
		JTextField SumText = new JTextField("0" , 5);
		SumText.setEditable(false);
		SellLayout.putConstraint(SpringLayout.WEST, SumText, 0, SpringLayout.WEST, ItemTotalPrice);
		SellLayout.putConstraint(SpringLayout.NORTH, SumText, 50, SpringLayout.SOUTH, ItemPrice[3]);
		SellButtons.add(SumText);
		JTextField DiscPrecText = new JTextField("0.0%" , 5);
		DiscPrecText.setEditable(false);
		SellLayout.putConstraint(SpringLayout.WEST, DiscPrecText, 0, SpringLayout.WEST, ItemPrice[0]);
		SellLayout.putConstraint(SpringLayout.NORTH, DiscPrecText, 50, SpringLayout.SOUTH, ItemPrice[3]);
		SellButtons.add(DiscPrecText);

		
		
		JButton Sell = new JButton("Sell");
		Sell.setFont(font2);
		SellButtons.add(Sell);
		SellLayout.putConstraint(SpringLayout.WEST,Sell , 35, SpringLayout.WEST,ItemPrice[3] );
		SellLayout.putConstraint(SpringLayout.NORTH, Sell, 100, SpringLayout.SOUTH, ItemPrice[3]);
		Sell.setEnabled(false);
		
		JButton Back = new JButton("Back");
		Back.setFont(font2);
		SellButtons.add(Back);
		SellLayout.putConstraint(SpringLayout.WEST,Back , 50, SpringLayout.EAST, Sell);
		SellLayout.putConstraint(SpringLayout.NORTH, Back, 100, SpringLayout.SOUTH, ItemPrice[3]);
		Back.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				SellMenu.dispose();
				new	MainMenu(clientSideConnection);
			}
		});
		
		JLabel clientNotFound = new JLabel("Client not found");
		SellLayout.putConstraint(SpringLayout.WEST,clientNotFound , 150, SpringLayout.WEST, SellButtons);
		SellLayout.putConstraint(SpringLayout.NORTH, clientNotFound, 30, SpringLayout.NORTH, SellButtons);
		clientNotFound.setFont(font2);
		clientNotFound.setForeground(Color.RED);
		SellButtons.add(clientNotFound);
		clientNotFound.setVisible(false);
		
		for (int i =0; i < 4; i++) 
		{
		    final int index = i; // assign to temporary variable
		    ItemQuantity[index].addActionListener(new ActionListener() 
		    {
		        @Override
		        public void actionPerformed(ActionEvent e) 
		        {
		        	if (ItemQuantity[index].getItemCount() != 0)
		        	{
			    		int MyPrice = Integer.valueOf(ItemPrice[index].getText());
						int MyQuantity = Integer.parseInt(ItemQuantity[index].getSelectedItem().toString());
						ItemTotal[index].setText(""+MyPrice*MyQuantity);
						double sum = (Integer.parseInt(ItemTotal[0].getText()) + Integer.parseInt(ItemTotal[1].getText()) + Integer.parseInt(ItemTotal[2].getText()) + Integer.parseInt(ItemTotal[3].getText())) * (1-discount);
						SumText.setText(new String(""+sum));
						clientSideConnection.getShop().getCart().cartUpdate(index, MyQuantity);
						if (sum != 0.0)
							Sell.setEnabled(true);
						else
							Sell.setEnabled(false);
		        	}
					
		        }
		    });
		}

//////////////////////Get Customer for discount/////////////////////////
		SearchCus.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				Customer customer = null;
					try {
						System.out.println(CusIDText.getText());
						customer = clientSideConnection.findCustomer(CusIDText.getText());
					} catch (IOException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if (customer != null)
					{
						discount = clientSideConnection.getShop().getDiscountForCustomer(customer.getType());
						clientSideConnection.getShop().getCart().setCustomer(CusIDText.getText());
						clientNotFound.setVisible(false);
					}
					else 
					{
						discount = 0.0;
						clientNotFound.setVisible(true);
					}
					DiscPrecText.setText(discount+"%");
					double sum = (Integer.parseInt(ItemTotal[0].getText()) + Integer.parseInt(ItemTotal[1].getText()) + Integer.parseInt(ItemTotal[2].getText()) + Integer.parseInt(ItemTotal[3].getText())) * (1-discount);
					SumText.setText(new String(""+sum));

			}
		});
////////////////////End Get Customer for discount//////////////////////
		
		ActionListener sell = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Boolean quantityTest = true;
				try {
					clientSideConnection.updateInventory();
				} catch (IOException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (int i=1;i<=4;++i)
				{
					if (Integer.parseInt(ItemQuantity[i-1].getSelectedItem().toString()) > clientSideConnection.getShop().getInventory(i))
					{
						quantityTest = false;
						JOptionPane pane = new JOptionPane();
						JOptionPane.showMessageDialog(pane, "Can't sell more then you have at the shop\n the panel will update now.", "Error on item " + i, JOptionPane.PLAIN_MESSAGE);
						break;	
					}
				}
				if (quantityTest == true)
				{
					clientSideConnection.getShop().getCart().setTotalPrice(Double.parseDouble(SumText.getText().toString()));
					clientSideConnection.endSell();
					CusIDText.setText("");
				}
				for (int i = 0; i<4 ; ++i)
				{
					ItemQuantity[i].removeAllItems();
					for (int j = 0; j<= shop.getInventory(i+1) ;++j )
					{
						ItemQuantity[i].addItem(j);
					}
				}
			}
		};	
		
		Sell.addActionListener(sell);
		
		SellMenu.add(SellButtons);
		SellMenu.pack();
		SellMenu.setVisible(true);
		
	}
}

