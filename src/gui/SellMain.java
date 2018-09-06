package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.json.simple.parser.ParseException;

import clientSide.ClientSideConnection;
import clientSide.Customer;
import clientSide.Shop;

public class SellMain {

	public  SellMain(ClientSideConnection clientSideConnection) {
		JComboBox<String> ItemType,/*ItemQuantity[0],*/ItemType2/*,ItemQuantity[1]*/, ItemType3,/* ItemQuantity[2], ItemQuantity[3],*/ ItemType4;
		Font font1 = new Font("Ariel",Font.PLAIN,10);
		Font font2 = new Font("Ariel",Font.BOLD,14);
		JFrame SellMenu = new JFrame();
		SellMenu.setTitle("Sales managment menu");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		Point newLocation = new Point(middle.x - (600 / 2), 
		                              middle.y - (700 / 2));
		SellMenu.setLocation(newLocation);
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
		ItemType = new JComboBox<String>(new String[] {"To be Connected to File","shirt","Pants"} );
		ItemType.setFont(font1);
		SellButtons.add(ItemType);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemType, 20, SpringLayout.SOUTH, Type);
		JLabel Quantity = new JLabel("Quantity:");
		Quantity.setFont(font1);
		SellButtons.add(Quantity);
		SellLayout.putConstraint(SpringLayout.WEST, Quantity, 100, SpringLayout.EAST, Type);
		SellLayout.putConstraint(SpringLayout.NORTH, Quantity, 40, SpringLayout.SOUTH, CusID);
		ItemQuantity[0].setFont(font1);
		JLabel Price = new JLabel("Item Price:");
		Price.setFont(font1);
		SellButtons.add(Price);
		SellLayout.putConstraint(SpringLayout.WEST, Price, 200, SpringLayout.EAST, Quantity);
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
		SellLayout.putConstraint(SpringLayout.WEST, ItemQuantity[0], 100, SpringLayout.EAST, Type);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemQuantity[0], 20, SpringLayout.SOUTH, Quantity);
		ItemPrice[0].setEditable(false);
		SellButtons.add(ItemPrice[0]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemPrice[0], 200, SpringLayout.EAST, Quantity);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemPrice[0], 20, SpringLayout.SOUTH, Price);
		SellButtons.add(ItemTotal[0]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemTotal[0], 0, SpringLayout.WEST, ItemTotalPrice);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemTotal[0], 20, SpringLayout.SOUTH, Price);

		
		ItemType2 = new JComboBox<String>(new String[] {"To be Connected to File","shirt","Pants"} );
		ItemType2.setFont(font1);
		SellButtons.add(ItemType2);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemType2, 50, SpringLayout.SOUTH, ItemType);
		SellLayout.putConstraint(SpringLayout.WEST, Quantity, 100, SpringLayout.EAST, ItemType);
		ItemQuantity[1].setFont(font1);
		SellButtons.add(ItemQuantity[1]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemQuantity[1], 100, SpringLayout.EAST, Type);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemQuantity[1], 50, SpringLayout.SOUTH, ItemQuantity[0]);
		ItemPrice[1].setEditable(false);
		SellButtons.add(ItemPrice[1]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemPrice[1], 200, SpringLayout.EAST, Quantity);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemPrice[1], 50, SpringLayout.SOUTH, ItemPrice[0]);
		SellButtons.add(ItemTotal[1]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemTotal[1], 0, SpringLayout.WEST, ItemTotalPrice);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemTotal[1], 50, SpringLayout.SOUTH, ItemPrice[0]);
		
		ItemType3 = new JComboBox<String>(new String[] {"To be Connected to File","shirt","Pants"} );
		ItemType3.setFont(font1);
		SellButtons.add(ItemType3);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemType3, 50, SpringLayout.SOUTH, ItemType2);
		SellLayout.putConstraint(SpringLayout.WEST, Quantity, 100, SpringLayout.EAST, ItemType);
		ItemQuantity[2].setFont(font1);
		SellButtons.add(ItemQuantity[2]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemQuantity[2], 100, SpringLayout.EAST, Type);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemQuantity[2], 50, SpringLayout.SOUTH, ItemQuantity[1]);
		ItemPrice[2].setEditable(false);
		SellButtons.add(ItemPrice[2]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemPrice[2], 200, SpringLayout.EAST, Quantity);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemPrice[2], 50, SpringLayout.SOUTH, ItemPrice[1]);
		SellButtons.add(ItemTotal[2]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemTotal[2], 0, SpringLayout.WEST, ItemTotalPrice);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemTotal[2], 50, SpringLayout.SOUTH, ItemPrice[1]);
		
		ItemType4 = new JComboBox<String>(new String[] {"To be Connected to File","shirt","Pants"} );
		ItemType4.setFont(font1);
		SellButtons.add(ItemType4);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemType4, 50, SpringLayout.SOUTH, ItemType3);
		SellLayout.putConstraint(SpringLayout.WEST, Quantity, 100 , SpringLayout.EAST, ItemType);
		ItemQuantity[3].setFont(font1);
		SellButtons.add(ItemQuantity[3]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemQuantity[3], 100, SpringLayout.EAST, Type);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemQuantity[3], 50, SpringLayout.SOUTH, ItemQuantity[2]);
		ItemPrice[3].setEditable(false);
		SellButtons.add(ItemPrice[3]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemPrice[3], 200, SpringLayout.EAST, Quantity);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemPrice[3], 50, SpringLayout.SOUTH, ItemPrice[2]);
		SellButtons.add(ItemTotal[3]);
		SellLayout.putConstraint(SpringLayout.WEST, ItemTotal[3], 0, SpringLayout.WEST, ItemTotalPrice);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemTotal[3], 50, SpringLayout.SOUTH, ItemPrice[2]);
		JTextField SumText = new JTextField("0" , 5);
		SumText.setEditable(false);
		SellLayout.putConstraint(SpringLayout.WEST, SumText, 0, SpringLayout.WEST, ItemTotalPrice);
		SellLayout.putConstraint(SpringLayout.NORTH, SumText, 50, SpringLayout.SOUTH, ItemPrice[3]);
		SellButtons.add(SumText);
		JTextField DiscPrecText = new JTextField("0%" , 5);
		DiscPrecText.setEditable(false);
		SellLayout.putConstraint(SpringLayout.WEST, DiscPrecText, 0, SpringLayout.WEST, ItemPrice[0]);
		SellLayout.putConstraint(SpringLayout.NORTH, DiscPrecText, 50, SpringLayout.SOUTH, ItemPrice[3]);
		SellButtons.add(DiscPrecText);

		
		
		JButton Sell = new JButton("Sell");
		Sell.setFont(font2);
		SellButtons.add(Sell);
		SellLayout.putConstraint(SpringLayout.WEST,Sell , 35, SpringLayout.WEST,ItemPrice[3] );
		SellLayout.putConstraint(SpringLayout.NORTH, Sell, 100, SpringLayout.SOUTH, ItemPrice[3]);
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
		SellLayout.putConstraint(SpringLayout.NORTH, Back, 100, SpringLayout.SOUTH, ItemPrice[3]);
		Back.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				SellMenu.dispose();
				new	MainMenu(clientSideConnection);
			}
		});
		
		for (int i =0; i < 4; i++) 
		{
		    final int index = i; // assign to temporary variable
		    ItemQuantity[index].addActionListener(new ActionListener() 
		    {
		        @Override
		        public void actionPerformed(ActionEvent e) 
		        {
		    		int MyPrice = Integer.valueOf(ItemPrice[index].getText());
					int MyQuantity = Integer.parseInt(ItemQuantity[index].getSelectedItem().toString());
					ItemTotal[index].setText(""+MyPrice*MyQuantity);
					int sum = Integer.parseInt(ItemTotal[0].getText()) + Integer.parseInt(ItemTotal[1].getText()) + Integer.parseInt(ItemTotal[2].getText()) + Integer.parseInt(ItemTotal[3].getText());
					SumText.setText(new String(""+sum));

		        }
		    });
		}

//////////////////////Get Customer for discount/////////////////////////
		SearchCus.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				Customer customer = null;
					try {
						customer = clientSideConnection.findCustomer(SearchCus.getText());
					} catch (IOException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if (customer != null)
					{
						double discount = clientSideConnection.getShop().getDiscountForCustomer(customer.getType());
						
					}

			}
		});
////////////////////End Get Customer for discount//////////////////////
		
		
		
		
		SellMenu.add(SellButtons);
		SellMenu.pack();
		SellMenu.setVisible(true);
		
	}
}

