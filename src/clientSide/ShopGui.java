package clientSide;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;



public class ShopGui extends JPanel{
	JSONObject json;
	
	public  ShopGui(ClientSideConnection clientSideConnection) {
		json = new JSONObject();
		Font font = new Font("Ariel",Font.PLAIN,20);
		Font font2 = new Font("Ariel",Font.BOLD,20);
		JFrame details = new JFrame();
		details.setTitle("Store managment login");
		details.setSize(580, 220);
		details.setLocationRelativeTo(null);
		details.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		//Login.setBorder(BorderFactory.createTitledBorder(Login, "Your Details", TitledBorder.LEADING, TitledBorder.TOP, new Font("Ariel", Font.BOLD,20), Color.BLACK));
		
		
		JPanel Login = new JPanel();
		Login.setBorder(BorderFactory.createTitledBorder("Your Details"));
		Login.setBackground(Color.LIGHT_GRAY);
		Login.setPreferredSize(new Dimension (400 , 175));
		Login.setLayout(new BoxLayout(Login, BoxLayout.Y_AXIS));
		JLabel Name = new JLabel("Username:");
		Name.setFont(font2);
		Login.add(Name, BorderLayout.NORTH);
		
		JTextField Ntext = new JTextField("" , 25);
		Ntext.setFont(font);
		Login.add(Ntext, BorderLayout.NORTH);
		Ntext.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent ae){
			      //String user = Ntext.getText();
			   }
			});
	
		
		
		
		JLabel Pass = new JLabel("Password:");
		Pass.setFont(font2);
		Login.add(Pass, BorderLayout.NORTH);
		
		JPasswordField Ptext = new JPasswordField("" , 20);
		Login.add(Ptext, BorderLayout.NORTH);
		
////////////////////////ActionListener For login panel/////////////////////////		
		ActionListener logAction = new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				int status = 0;
				try {
					status = clientSideConnection.login(Ntext.getText(), ""+new String(Ptext.getPassword()));
				} catch (ParseException | IOException e) {
					e.printStackTrace();
				}
				
				if (status == 1) {
				details.dispose();
				new MainMenu(); }
				else {
					JOptionPane.showMessageDialog(null,"Wrong username or password!");
					if (JOptionPane.OK_OPTION == 0) {
						Ntext.setText("");
						Ptext.setText("");
					}
				}
			}
		};
////////////////////End of ActionListener For login panel/////////////////////			
		
		
		Ptext.addActionListener(logAction);
		
		JButton Enter = new JButton("Login");
		Enter.setFont(font2);
		Login.add(Enter, BorderLayout.CENTER);

				
		details.add(Login);
		details.pack();
		details.setVisible(true);
				
		Enter.addActionListener(logAction);

	}
	
	public class MainMenu {
		
		public MainMenu() {
			Font font2 = new Font("Ariel",Font.BOLD,20);
			JFrame Main = new JFrame();
			Main.setTitle("Store Managment");
			Main.setSize(800, 420);
			Main.setLocationRelativeTo(null);
			Main.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			
		
			
			JButton Supply = new JButton("Supply Managment");
			Main.add(Supply, BorderLayout.NORTH);
			Supply.setFont(font2);
			Supply.addActionListener(new ActionListener() {
				public void actionPerformed (ActionEvent ae) {
					Main.dispose();
					new	SupplyMain();
				}
			});
			
					JButton Customer = new JButton("Manage customers");
			Main.add(Customer, BorderLayout.CENTER);
			Customer.setFont(font2);
			Customer.addActionListener(new ActionListener() {
				public void actionPerformed (ActionEvent ae) {
					Main.dispose();
					new	CustomerMain();
				}
			});
			
			
					JButton Reports = new JButton("Generate reports");
			Main.add(Reports, BorderLayout.WEST);
			Reports.setFont(font2);
			Reports.addActionListener(new ActionListener() {
				public void actionPerformed (ActionEvent ae) {
					Main.dispose();
					new	MainMenu();
				}
			});
			
					JButton Chat = new JButton("Start Chat with other branch");
			Main.add(Chat, BorderLayout.EAST);
			Chat.setFont(font2);
			Chat.addActionListener(new ActionListener() {
				public void actionPerformed (ActionEvent ae) {
					Main.dispose();
					new	MainMenu();
					
				}
			});
			Main.setVisible(true);
		}
	}
	public class  SupplyMain {
		
	public  SupplyMain() {
		JFrame SupplyMenu = new JFrame();
		SupplyMenu.setTitle("Supply managment menu");
		SupplyMenu.setSize(580, 220);
		SupplyMenu.setLocationRelativeTo(null);
		SupplyMenu.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		
		JPanel SupplyButtons = new JPanel();
		SupplyButtons.setBorder(BorderFactory.createTitledBorder("Your Supply Menu"));
		SupplyButtons.setBackground(Color.white);
		SupplyButtons.setPreferredSize(new Dimension (400 , 175));
		SupplyButtons.setLayout(new BoxLayout(SupplyButtons, BoxLayout.X_AXIS));
		
		JButton Back = new JButton("Back to Main Menu");
		SupplyButtons.add(Back, BorderLayout.CENTER);
		Back.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				SupplyMenu.dispose();
				new	MainMenu();
			}
		});
		
		JButton Sell = new JButton("Sell an item");
		SupplyButtons.add(Sell, BorderLayout.CENTER);
		Sell.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				SupplyMenu.dispose();
				new	SellMain();
			}
		});
		
		
		SupplyMenu.add(SupplyButtons);
		SupplyMenu.pack();
		SupplyMenu.setVisible(true);
	}
	}
	
	
	public class  SellMain {
 
	public  SellMain() {
		JComboBox<String> ItemType,ItemQuantity,ItemType2,ItemQuantity2, ItemType3, ItemQuantity3, ItemQuantity4, ItemType4;
		Font font1 = new Font("Ariel",Font.PLAIN,10);
		Font font2 = new Font("Ariel",Font.BOLD,14);
		JFrame SellMenu = new JFrame();
		SellMenu.setTitle("Sales managment menu");
		SellMenu.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		SellMenu.setLocationRelativeTo(null);	
		SellMenu.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		JPanel SellButtons = new JPanel();
		SpringLayout SellLayout = new SpringLayout();
		SellButtons.setLayout(SellLayout);
		
//		GridBagConstraints gc = new GridBagConstraints();
		
		SellButtons.setBorder(BorderFactory.createTitledBorder("Sell Menu"));
		SellButtons.setBackground(Color.white);
		SellButtons.setPreferredSize(new Dimension (200 , 175));
		//SellButtons.setLayout(new FlowLayout());
		JLabel Type = new JLabel("Item Type:");
		Type.setFont(font2);
		SellButtons.add(Type);
		SellLayout.putConstraint(SpringLayout.WEST, Type, 20, SpringLayout.WEST, SellButtons);
		ItemType = new JComboBox<String>(new String[] {"To be Connected to File","shirt","Pants"} );
		ItemType.setFont(font1);
		SellButtons.add(ItemType);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemType, 50, SpringLayout.SOUTH, Type);
		JLabel Quantity = new JLabel("Quantity:");
		Quantity.setFont(font2);
		SellButtons.add(Quantity);
		SellLayout.putConstraint(SpringLayout.WEST, Quantity, 100, SpringLayout.EAST, Type);
		ItemQuantity = new JComboBox<String>(new String[] {"The quantity of the item from the file from 1 to maximum"} );
		ItemQuantity.setFont(font1);
		SellButtons.add(ItemQuantity);
		SellLayout.putConstraint(SpringLayout.WEST, ItemQuantity, 100, SpringLayout.EAST, Type);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemQuantity, 50, SpringLayout.SOUTH, Quantity);
		JLabel Price = new JLabel("Item Price");
		Price.setFont(font2);
		SellButtons.add(Price);
		SellLayout.putConstraint(SpringLayout.WEST, Price, 200, SpringLayout.EAST, Quantity);
		JTextField ItemPrice = new JTextField("" , 20);
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
		ItemQuantity2 = new JComboBox<String>(new String[] {"The quantity of the item from the file from 1 to maximum"} );
		ItemQuantity2.setFont(font1);
		SellButtons.add(ItemQuantity2);
		SellLayout.putConstraint(SpringLayout.WEST, ItemQuantity2, 100, SpringLayout.EAST, Type);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemQuantity2, 50, SpringLayout.SOUTH, ItemQuantity);
		JTextField ItemPrice2 = new JTextField("" , 20);
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
		ItemQuantity3 = new JComboBox<String>(new String[] {"The quantity of the item from the file from 1 to maximum"} );
		ItemQuantity3.setFont(font1);
		SellButtons.add(ItemQuantity3);
		SellLayout.putConstraint(SpringLayout.WEST, ItemQuantity3, 100, SpringLayout.EAST, Type);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemQuantity3, 50, SpringLayout.SOUTH, ItemQuantity2);
		JTextField ItemPrice3 = new JTextField("" , 20);
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
		ItemQuantity4 = new JComboBox<String>(new String[] {"The quantity of the item from the file from 1 to maximum"} );
		ItemQuantity4.setFont(font1);
		SellButtons.add(ItemQuantity4);
		SellLayout.putConstraint(SpringLayout.WEST, ItemQuantity4, 100, SpringLayout.EAST, Type);
		SellLayout.putConstraint(SpringLayout.NORTH, ItemQuantity4, 50, SpringLayout.SOUTH, ItemQuantity3);
		JTextField ItemPrice4 = new JTextField("" , 20);
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
				new	SupplyMain();
			}
		});
		SellMenu.add(SellButtons);
		SellMenu.pack();
		SellMenu.setVisible(true);
	}
	}
	
	public class  CustomerMain {
		
	public  CustomerMain() {
		JFrame CustomerMenu = new JFrame();
		CustomerMenu.setTitle("Supply managment menu");
		CustomerMenu.setSize(580, 220);
		CustomerMenu.setLocationRelativeTo(null);
		CustomerMenu.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		
		JPanel CustomerButtons = new JPanel();
		CustomerButtons.setBorder(BorderFactory.createTitledBorder("Your Supply Menu"));
		CustomerButtons.setBackground(Color.white);
		CustomerButtons.setPreferredSize(new Dimension (400 , 175));
		CustomerButtons.setLayout(new BoxLayout(CustomerButtons, BoxLayout.X_AXIS));	
			
		JButton NewCus = new JButton("Add new customer");
		CustomerButtons.add(NewCus, BorderLayout.CENTER);
		NewCus.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				CustomerMenu.dispose();
				new	AddCus();
			}
		});
	
		JButton Back = new JButton("Back to Main Menu");
		CustomerButtons.add(Back, BorderLayout.CENTER);
		Back.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				CustomerMenu.dispose();
				new	MainMenu();
			}
		});

		
		
		CustomerMenu.add(CustomerButtons);
		CustomerMenu.pack();
		CustomerMenu.setVisible(true);
	}
	}
	public class  AddCus {
		 
		public  AddCus() {
			JComboBox<String> ItemType,ItemQuantity,ItemType2,ItemQuantity2, ItemType3, ItemQuantity3, ItemQuantity4, ItemType4;
			Font font = new Font("Ariel",Font.PLAIN,20);
			Font font2 = new Font("Ariel",Font.BOLD,20);
			JFrame AddMenu = new JFrame();
			AddMenu.setTitle("Sales managment menu");
			AddMenu.setExtendedState(JFrame.MAXIMIZED_BOTH); 
			//AddMenu.setUndecorated(true);
			AddMenu.setLocationRelativeTo(null);	
			AddMenu.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			
			
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
					new	CustomerMain();
				}
			});
			AddMenu.add(AddButtons);
			AddMenu.pack();
			AddMenu.setVisible(true);
		}
		}

	/*public static void main (String[] args) {
		createandrunjframe();

	}*/
	
	/*public static void createandrunjframe(){
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ShopGui shop = new ShopGui();
				shop.setVisible(true);
			
			}
		});
	}*/
}
