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
		JFrame CusMenu = new JFrame();
		CusMenu.setTitle("Customer managment menu");
		//Set the Panel on the middle
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
				Point newLocation = new Point(middle.x - (600 / 2), 
				                              middle.y - (370 / 2));
				CusMenu.setLocation(newLocation);
		CusMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Image img = Toolkit.getDefaultToolkit().getImage("./files/ClientIcon.png");
		CusMenu.setIconImage(img);
		
		JPanel CusMgr = new JPanel();
		SpringLayout CusLayout = new SpringLayout();
		CusMgr.setLayout(CusLayout);
		
		JComboBox<String> CusTypeText;
		
		CusMgr.setBorder(BorderFactory.createTitledBorder("Customer Menu"));
		CusMgr.setBackground(Color.white);
		CusMgr.setPreferredSize(new Dimension (550 , 370));		
		
		JLabel ID = new JLabel("Customer ID:");
		ID.setFont(font2);
		CusMgr.add(ID);
		CusLayout.putConstraint(SpringLayout.WEST, ID, 20, SpringLayout.WEST, CusMgr);
		JTextField IDNum = new JTextField("",20);
		IDNum.setFont(font1);

		CusMgr.add(IDNum);
		CusLayout.putConstraint(SpringLayout.WEST, IDNum, 150, SpringLayout.WEST, ID);

		
		JLabel Fn = new JLabel("Full name:");
		Fn.setFont(font2);
		CusMgr.add(Fn);
		CusLayout.putConstraint(SpringLayout.WEST, Fn, 20, SpringLayout.WEST, CusMgr);
		CusLayout.putConstraint(SpringLayout.NORTH, Fn, 60, SpringLayout.SOUTH, ID);
		JTextField FnText = new JTextField("",20);
		FnText.setFont(font1);
		CusMgr.add(FnText);
		CusLayout.putConstraint(SpringLayout.WEST, FnText, 150, SpringLayout.WEST, ID);
		CusLayout.putConstraint(SpringLayout.NORTH, FnText, 60, SpringLayout.SOUTH, IDNum);
		
		JLabel PhnNum = new JLabel("Phone number:");
		PhnNum.setFont(font2);
		CusMgr.add(PhnNum);
		CusLayout.putConstraint(SpringLayout.WEST, PhnNum, 20, SpringLayout.WEST, CusMgr);
		CusLayout.putConstraint(SpringLayout.NORTH, PhnNum, 60, SpringLayout.SOUTH, FnText);
		JTextField PhnNumText = new JTextField("",20);
		PhnNumText.setFont(font1);
		CusMgr.add(PhnNumText);
		CusLayout.putConstraint(SpringLayout.WEST, PhnNumText, 150, SpringLayout.WEST, PhnNum);
		CusLayout.putConstraint(SpringLayout.NORTH, PhnNumText, 60, SpringLayout.SOUTH, FnText);
		
		JLabel CusType = new JLabel("Customer Type:");
		CusType.setFont(font2);
		CusMgr.add(CusType);
		CusLayout.putConstraint(SpringLayout.WEST, CusType, 20, SpringLayout.WEST, CusMgr);
		CusLayout.putConstraint(SpringLayout.NORTH, CusType, 60, SpringLayout.SOUTH, PhnNumText);
		CusTypeText = new JComboBox<String>(new String[] {"New","Return","VIP"} );
		CusTypeText.setFont(font1);
		CusMgr.add(CusTypeText);
		CusLayout.putConstraint(SpringLayout.WEST, CusTypeText, 150, SpringLayout.WEST, CusType);
		CusLayout.putConstraint(SpringLayout.NORTH, CusTypeText, 60, SpringLayout.SOUTH, PhnNumText);
		
		
		JButton Save = new JButton("Save");
		Save.setFont(font2);
		CusMgr.add(Save);
		Save.setEnabled(false);
		CusLayout.putConstraint(SpringLayout.WEST, Save, 100, SpringLayout.WEST, CusMgr);
		CusLayout.putConstraint(SpringLayout.NORTH, Save, 50, SpringLayout.NORTH, CusType);
		
		JButton Back = new JButton("Back");
		Back.setFont(font2);
		CusMgr.add(Back);
		CusLayout.putConstraint(SpringLayout.WEST,Back , 12, SpringLayout.EAST, Save);
		CusLayout.putConstraint(SpringLayout.NORTH, Back, 50, SpringLayout.NORTH, CusType);
		Back.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				CusMenu.dispose();
				new	MainMenu(clientSideConnection);
			}
		});
		
		JButton search = new JButton("Search Customer");
		search.setFont(font2);
		search.setEnabled(false);
		CusMgr.add(search);
		CusLayout.putConstraint(SpringLayout.WEST, search, 380, SpringLayout.WEST, CusMgr);
		
		JButton BackToSell = new JButton("Go to sell");
		BackToSell.setFont(font2);
		BackToSell.setPreferredSize(new Dimension(120,30));
		CusMgr.add(BackToSell);
		CusLayout.putConstraint(SpringLayout.WEST,BackToSell , 12, SpringLayout.EAST, Back);
		CusLayout.putConstraint(SpringLayout.NORTH, BackToSell, 50, SpringLayout.NORTH, CusType);
		BackToSell.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				CusMenu.dispose();
				new SellMain(clientSideConnection);
			}
		});
		
////////////////////////ActionListener For finding customer/////////////////////////		
ActionListener findCustomererAction = new ActionListener() {
	public void actionPerformed (ActionEvent ae) {
		Customer customer = null;
		try {
			customer = clientSideConnection.findCustomer(IDNum.getText());
			search.setEnabled(false);
		} catch ( IOException | org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		
		if (customer != null)
		{
			FnText.setText(customer.getName());
			PhnNumText.setText(customer.getPhoneNr());
			System.out.println(customer.getType());
			CusTypeText.setSelectedItem(customer.getType());
		}
		else 
		{
			FnText.setText("");
			PhnNumText.setText("");
			CusTypeText.setSelectedItem("New");
		}
	}
};

IDNum.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		search.setEnabled(true);
		
	}
});

////////////////////End of ActionListener For login panel/////////////////////		
	search.addActionListener(findCustomererAction);
	IDNum.addActionListener(findCustomererAction);
	

///////////////////Save Enable Action/////////////////////////
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
		if (!IDNum.getText().isEmpty()
			&& !FnText.getText().isEmpty()
			&& !PhnNumText.getText().isEmpty())
		Save.setEnabled(true);
		else
		Save.setEnabled(false);
	}

};

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
///////////////////End Save Enable Action///////////////////


////////////////////Save//////////////////

Save.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Customer customer = new Customer(IDNum.getText(),
										FnText.getText(),
										PhnNumText.getText(),
										CusTypeText.getSelectedItem().toString());
		clientSideConnection.saveCustomer(customer);
		IDNum.setText("");
		FnText.setText("");
		PhnNumText.setText("");
		CusTypeText.setSelectedIndex(0);
	}
});

//////////////////End save//////////////////

IDNum.getDocument().addDocumentListener(SearchEnabler);
FnText.getDocument().addDocumentListener(SaveEnabler);
PhnNumText.getDocument().addDocumentListener(SaveEnabler);
((JTextField)CusTypeText.getEditor().getEditorComponent()).getDocument().addDocumentListener(SaveEnabler);


		IDNum.addActionListener(findCustomererAction);
		search.addActionListener(findCustomererAction);
		
		CusMenu.add(CusMgr);
		CusMenu.pack();
		CusMenu.setVisible(true);
	}
}
