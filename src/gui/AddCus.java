package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import clientSide.ClientSideConnection;
import clientSide.Customer;
import clientSide.Worker;

public class AddCus {
	public  AddCus(ClientSideConnection clientSideConnection) {
		Font font1 = new Font("Ariel",Font.PLAIN,10);
		Font font2 = new Font("Ariel",Font.BOLD,14);
		JFrame cusMenu = new JFrame();
		cusMenu.setTitle("Customer managment menu");
		//Set the Panel on the middle
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
				Point newLocation = new Point(middle.x - (600 / 2), 
				                              middle.y - (370 / 2));
				cusMenu.setLocation(newLocation);
		cusMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Image img = Toolkit.getDefaultToolkit().getImage("./files/ClientIcon.png");
		cusMenu.setIconImage(img);
		
		JPanel cusMgr = new JPanel();
		SpringLayout cusLayout = new SpringLayout();
		cusMgr.setLayout(cusLayout);
		
		JComboBox<String> cusTypeText;
		
		cusMgr.setBorder(BorderFactory.createTitledBorder("Customer Menu"));
		cusMgr.setBackground(Color.white);
		cusMgr.setPreferredSize(new Dimension (550 , 370));		
		
		JLabel id = new JLabel("Customer id:");
		id.setFont(font2);
		cusMgr.add(id);
		cusLayout.putConstraint(SpringLayout.WEST, id, 20, SpringLayout.WEST, cusMgr);
		JTextField idNum = new JTextField("",20);
		idNum.setFont(font1);

		cusMgr.add(idNum);
		cusLayout.putConstraint(SpringLayout.WEST, idNum, 150, SpringLayout.WEST, id);

		
		JLabel fn = new JLabel("Full name:");
		fn.setFont(font2);
		cusMgr.add(fn);
		cusLayout.putConstraint(SpringLayout.WEST, fn, 20, SpringLayout.WEST, cusMgr);
		cusLayout.putConstraint(SpringLayout.NORTH, fn, 60, SpringLayout.SOUTH, id);
		JTextField fnText = new JTextField("",20);
		fnText.setFont(font1);
		cusMgr.add(fnText);
		cusLayout.putConstraint(SpringLayout.WEST, fnText, 150, SpringLayout.WEST, id);
		cusLayout.putConstraint(SpringLayout.NORTH, fnText, 60, SpringLayout.SOUTH, idNum);
		
		JLabel phnNum = new JLabel("Phone number:");
		phnNum.setFont(font2);
		cusMgr.add(phnNum);
		cusLayout.putConstraint(SpringLayout.WEST, phnNum, 20, SpringLayout.WEST, cusMgr);
		cusLayout.putConstraint(SpringLayout.NORTH, phnNum, 60, SpringLayout.SOUTH, fnText);
		JTextField phnNumText = new JTextField("",20);
		phnNumText.setFont(font1);
		cusMgr.add(phnNumText);
		cusLayout.putConstraint(SpringLayout.WEST, phnNumText, 150, SpringLayout.WEST, phnNum);
		cusLayout.putConstraint(SpringLayout.NORTH, phnNumText, 60, SpringLayout.SOUTH, fnText);
		
		JLabel cusType = new JLabel("Customer Type:");
		cusType.setFont(font2);
		cusMgr.add(cusType);
		cusLayout.putConstraint(SpringLayout.WEST, cusType, 20, SpringLayout.WEST, cusMgr);
		cusLayout.putConstraint(SpringLayout.NORTH, cusType, 60, SpringLayout.SOUTH, phnNumText);
		cusTypeText = new JComboBox<String>(new String[] {"New","Return","VIP"} );
		cusTypeText.setFont(font1);
		cusMgr.add(cusTypeText);
		cusLayout.putConstraint(SpringLayout.WEST, cusTypeText, 150, SpringLayout.WEST, cusType);
		cusLayout.putConstraint(SpringLayout.NORTH, cusTypeText, 60, SpringLayout.SOUTH, phnNumText);
		
		
		JButton save = new JButton("save");
		save.setFont(font2);
		cusMgr.add(save);
		save.setEnabled(false);
		cusLayout.putConstraint(SpringLayout.WEST, save, 100, SpringLayout.WEST, cusMgr);
		cusLayout.putConstraint(SpringLayout.NORTH, save, 50, SpringLayout.NORTH, cusType);
		
		JButton back = new JButton("back");
		back.setFont(font2);
		cusMgr.add(back);
		cusLayout.putConstraint(SpringLayout.WEST,back , 12, SpringLayout.EAST, save);
		cusLayout.putConstraint(SpringLayout.NORTH, back, 50, SpringLayout.NORTH, cusType);
		///////////back ActionListener///////////
		back.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				cusMenu.dispose();
				new	MainMenu(clientSideConnection);
			}
		});
		///////////back ActionListener///////////
		
		JButton search = new JButton("Search Customer");
		search.setFont(font2);
		search.setEnabled(false);
		cusMgr.add(search);
		cusLayout.putConstraint(SpringLayout.WEST, search, 380, SpringLayout.WEST, cusMgr);
		
		JButton backToSell = new JButton("Go to sell");
		backToSell.setFont(font2);
		backToSell.setPreferredSize(new Dimension(120,30));
		cusMgr.add(backToSell);
		cusLayout.putConstraint(SpringLayout.WEST,backToSell , 12, SpringLayout.EAST, back);
		cusLayout.putConstraint(SpringLayout.NORTH, backToSell, 50, SpringLayout.NORTH, cusType);
		///////////back to sell ActionListener///////////
		backToSell.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				cusMenu.dispose();
				new SellMain(clientSideConnection);
			}
		});
		///////////end of back to sell ActionListener///////////
		
	////////////////////////ActionListener For finding customer/////////////////////////		
	ActionListener findCustomererAction = new ActionListener() {
		public void actionPerformed (ActionEvent ae) {
			Customer customer = null;
			try {
				customer = clientSideConnection.findCustomer(idNum.getText());//gets the customer id to search
				search.setEnabled(false);
			} catch ( IOException | org.json.simple.parser.ParseException e) {
				e.printStackTrace();
			}
			
			if (customer != null)//if the customer exists, gets his details
			{
				fnText.setText(customer.getName());
				phnNumText.setText(customer.getPhoneNr());
				System.out.println(customer.getType());
				cusTypeText.setSelectedItem(customer.getType());
			}
			else //if the customer dos'nt exists cleaning the first name and phone fields and sets the default customer type to new
			{
				fnText.setText("");
				phnNumText.setText("");
				cusTypeText.setSelectedItem("New");
			}
		}
	};
	////////////////////////End of ActionListener For finding customer/////////////////////////	
	
	///////////idNum ActionListener//////////
	idNum.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			search.setEnabled(true);
			
		}
	});
	///////////end of idNum ActionListener//////////
	
		search.addActionListener(findCustomererAction);
		idNum.addActionListener(findCustomererAction);
		
	
	///////////////////Enable save button/////////////////////////
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
	
		public void saveEnable(){
			if (!idNum.getText().isEmpty()
				&& !fnText.getText().isEmpty()
				&& !phnNumText.getText().isEmpty())
			save.setEnabled(true);
			else
			save.setEnabled(false);
		}
	
	};
	///////////////////End of save Enable Action///////////////////
	
	
	///////////////////Enable Search button/////////////////////////
		DocumentListener SearchEnabler = new DocumentListener(){
	
	@Override
		public void changedUpdate(DocumentEvent e) {
			SearchEnable();
		
		}
	
	@Override
		public void insertUpdate(DocumentEvent e) {
			SearchEnable();
		
		}
	
	@Override
		public void removeUpdate(DocumentEvent e) {
		SearchEnable();
	
		}
		public void SearchEnable(){
			search.setEnabled(true);
		}
	};
	
	///////////////////End of search Enable Action///////////////////
	
	
	////////////////////save ActionListener//////////////////
	
	save.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {//gets the values from the fields and saves them in the customer, then cleans the fields
			// TODO Auto-generated method stub
			Customer customer = new Customer(idNum.getText(),
											fnText.getText(),
											phnNumText.getText(),
											cusTypeText.getSelectedItem().toString());
			clientSideConnection.saveCustomer(customer);
			idNum.setText("");
			fnText.setText("");
			phnNumText.setText("");
			cusTypeText.setSelectedIndex(0);
		}
	});
	//////////////////End of save ActionListener//////////////////
	
	idNum.getDocument().addDocumentListener(SearchEnabler);
	fnText.getDocument().addDocumentListener(saveEnabler);
	phnNumText.getDocument().addDocumentListener(saveEnabler);
	((JTextField)cusTypeText.getEditor().getEditorComponent()).getDocument().addDocumentListener(saveEnabler);
	
	
			idNum.addActionListener(findCustomererAction);
			search.addActionListener(findCustomererAction);
			
			cusMenu.add(cusMgr);
			cusMenu.pack();
			cusMenu.setVisible(true);
		}
}
