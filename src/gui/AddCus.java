package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import clientSide.ClientSideConnection;
import clientSide.Worker;

public class AddCus {
	public  AddCus(ClientSideConnection clientSideConnection) {
		Font font1 = new Font("Ariel",Font.PLAIN,10);
		Font font2 = new Font("Ariel",Font.BOLD,14);
		JFrame CusMenu = new JFrame();
		CusMenu.setTitle("Sales managment menu");
		CusMenu.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		CusMenu.setLocationRelativeTo(null);	
		CusMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel CusMgr = new JPanel();
		SpringLayout CusLayout = new SpringLayout();
		CusMgr.setLayout(CusLayout);
		
		JComboBox<String> CusTypeText;
		
		CusMgr.setBorder(BorderFactory.createTitledBorder("Customer Menu"));
		CusMgr.setBackground(Color.white);
		CusMgr.setPreferredSize(new Dimension (200 , 175));

		JButton Save = new JButton("Save");
		Save.setFont(font2);
		CusMgr.add(Save);
		Save.setEnabled(false);
		CusLayout.putConstraint(SpringLayout.WEST, Save, 100, SpringLayout.WEST, CusMgr);
		CusLayout.putConstraint(SpringLayout.NORTH, Save, 340, SpringLayout.NORTH, CusMgr);
		Save.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				CusMenu.dispose();
				new AddCus(clientSideConnection);

			}
		});
		
		JButton Back = new JButton("Back");
		Back.setFont(font2);
		CusMgr.add(Back);
		CusLayout.putConstraint(SpringLayout.WEST,Back , 12, SpringLayout.EAST, Save);
		CusLayout.putConstraint(SpringLayout.NORTH, Back, 340, SpringLayout.NORTH, CusMgr);
		Back.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				CusMenu.dispose();
				new	MainMenu(clientSideConnection);
			}
		});
		
		JButton Search = new JButton("Search Customer");
		Search.setFont(font2);
		Search.setEnabled(false);
		CusMgr.add(Search);
		CusLayout.putConstraint(SpringLayout.WEST, Search, 380, SpringLayout.WEST, CusMgr);
		
		JButton BackToSell = new JButton("Back to sell");
		BackToSell.setFont(font2);
		BackToSell.setPreferredSize(new Dimension(120,30));
		CusMgr.add(BackToSell);
		CusLayout.putConstraint(SpringLayout.WEST,BackToSell , 12, SpringLayout.EAST, Back);
		CusLayout.putConstraint(SpringLayout.NORTH, BackToSell, 340, SpringLayout.NORTH, CusMgr);
		BackToSell.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				CusMenu.dispose();
				new SellMain(clientSideConnection);
			}
		});
		
		
		
		
		
		
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
				Search.setEnabled(true);
			}
		};
			
		
		
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
				Save.setEnabled(true);
			}
			
		}; 
		
		JLabel ID = new JLabel("Customer ID:");
		ID.setFont(font2);
		CusMgr.add(ID);
		CusLayout.putConstraint(SpringLayout.WEST, ID, 20, SpringLayout.WEST, CusMgr);
		JTextField IDNum = new JTextField("",20);
		IDNum.setFont(font1);
		IDNum.getDocument().addDocumentListener(SearchEnabler);
		CusMgr.add(IDNum);
		CusLayout.putConstraint(SpringLayout.WEST, IDNum, 150, SpringLayout.WEST, ID);

		
		JLabel Fn = new JLabel("Full name:");
		Fn.setFont(font2);
		CusMgr.add(Fn);
		CusLayout.putConstraint(SpringLayout.WEST, Fn, 20, SpringLayout.WEST, CusMgr);
		CusLayout.putConstraint(SpringLayout.NORTH, Fn, 60, SpringLayout.SOUTH, ID);
		JTextField FnText = new JTextField("",20);
		FnText.setFont(font1);
		FnText.getDocument().addDocumentListener(SaveEnabler);
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
		PhnNumText.getDocument().addDocumentListener(SaveEnabler);
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
		((JTextField)CusTypeText.getEditor().getEditorComponent()).getDocument().addDocumentListener(SaveEnabler);
		CusMgr.add(CusTypeText);
		CusLayout.putConstraint(SpringLayout.WEST, CusTypeText, 150, SpringLayout.WEST, CusType);
		CusLayout.putConstraint(SpringLayout.NORTH, CusTypeText, 60, SpringLayout.SOUTH, PhnNumText);
		
		
////////////////////////ActionListener For finding worker/////////////////////////		
ActionListener findWorkerAction = new ActionListener() {
	public void actionPerformed (ActionEvent ae) {
		Worker worker = null;
		try {
			worker = clientSideConnection.findWorker(IDNum.getText());
		} catch ( IOException | org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		
		if (worker != null)
		{
			FnText.setText(worker.getName());
			PhnNumText.setText(worker.getPhoneNr());
			String workerId = Integer.toString(worker.getWorkerId());
		}
	}
};

FocusListener findWorkerFAction = new FocusListener() {

	@Override
	public void focusGained(FocusEvent arg0) {
		Worker worker = null;
		try {
			worker = clientSideConnection.findWorker(IDNum.getText());
		} catch ( IOException | org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		
		if (worker != null)
		{
			FnText.setText(worker.getName());
			PhnNumText.setText(worker.getPhoneNr());

			String workerId = Integer.toString(worker.getWorkerId());
			
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
};

////////////////////End of ActionListener For login panel/////////////////////		
		
		IDNum.addActionListener(findWorkerAction);
		IDNum.addFocusListener(findWorkerFAction);
		Search.addActionListener(findWorkerAction);
		
		CusMenu.add(CusMgr);
		CusMenu.pack();
		CusMenu.setVisible(true);
	}
}
