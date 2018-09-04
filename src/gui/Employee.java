package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;

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

import org.json.simple.parser.ParseException;

import clientSide.ClientSideConnection;
import clientSide.Worker;

public class Employee {

	public  Employee(ClientSideConnection clientSideConnection) {
		Font font1 = new Font("Ariel",Font.PLAIN,10);
		Font font2 = new Font("Ariel",Font.BOLD,14);
		JFrame EmpMenu = new JFrame();
		EmpMenu.setTitle("Sales managment menu");
		EmpMenu.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		EmpMenu.setLocationRelativeTo(null);	
		EmpMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel EmpMgr = new JPanel();
		SpringLayout EmpLayout = new SpringLayout();
		EmpMgr.setLayout(EmpLayout);
		
		JComboBox<String> PositionText;
//		GridBagConstraints gc = new GridBagConstraints();
		
		EmpMgr.setBorder(BorderFactory.createTitledBorder("Employee Menu"));
		EmpMgr.setBackground(Color.white);
		EmpMgr.setPreferredSize(new Dimension (200 , 175));

		JButton Save = new JButton("Save");
		Save.setFont(font2);
		EmpMgr.add(Save);
		Save.setEnabled(false);
		EmpLayout.putConstraint(SpringLayout.WEST, Save, 100, SpringLayout.WEST, EmpMgr);
		EmpLayout.putConstraint(SpringLayout.NORTH, Save, 540, SpringLayout.NORTH, EmpMgr);
		Save.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				EmpMenu.dispose();
				new Employee(clientSideConnection);

			}
		});
		
		JButton Back = new JButton("Back");
		Back.setFont(font2);
		EmpMgr.add(Back);
		EmpLayout.putConstraint(SpringLayout.WEST,Back , 12, SpringLayout.EAST, Save);
		EmpLayout.putConstraint(SpringLayout.NORTH, Back, 540, SpringLayout.NORTH, EmpMgr);
		Back.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				EmpMenu.dispose();
				new	BuySell(clientSideConnection);
			}
		});
		
		
		
		DocumentListener Enabler = new DocumentListener(){

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
		JLabel ID = new JLabel("Employee ID:");
		ID.setFont(font2);
		EmpMgr.add(ID);
		EmpLayout.putConstraint(SpringLayout.WEST, ID, 20, SpringLayout.WEST, EmpMgr);
		JTextField IDNum = new JTextField("",10);
		IDNum.setFont(font1);
		EmpMgr.add(IDNum);
		EmpLayout.putConstraint(SpringLayout.WEST, IDNum, 150, SpringLayout.WEST, ID);
		
		JLabel Fn = new JLabel("First name:");
		Fn.setFont(font2);
		EmpMgr.add(Fn);
		EmpLayout.putConstraint(SpringLayout.WEST, Fn, 20, SpringLayout.WEST, EmpMgr);
		EmpLayout.putConstraint(SpringLayout.NORTH, Fn, 60, SpringLayout.SOUTH, ID);
		JTextField FnText = new JTextField("",10);
		FnText.setFont(font1);
		FnText.getDocument().addDocumentListener(Enabler);
		EmpMgr.add(FnText);
		EmpLayout.putConstraint(SpringLayout.WEST, FnText, 150, SpringLayout.WEST, ID);
		EmpLayout.putConstraint(SpringLayout.NORTH, FnText, 60, SpringLayout.SOUTH, IDNum);
		
		JLabel Ln = new JLabel("Last name:");
		Ln.setFont(font2);
		EmpMgr.add(Ln);
		EmpLayout.putConstraint(SpringLayout.WEST, Ln, 20, SpringLayout.WEST, EmpMgr);
		EmpLayout.putConstraint(SpringLayout.NORTH, Ln, 60, SpringLayout.SOUTH, Fn);
		JTextField LnText = new JTextField("",10);
		LnText.setFont(font1);
		LnText.getDocument().addDocumentListener(Enabler);
		EmpMgr.add(LnText);
		EmpLayout.putConstraint(SpringLayout.WEST, LnText, 150, SpringLayout.WEST, Fn);
		EmpLayout.putConstraint(SpringLayout.NORTH, LnText, 60, SpringLayout.SOUTH, FnText);
		
		JLabel PhnNum = new JLabel("Phone number:");
		PhnNum.setFont(font2);
		EmpMgr.add(PhnNum);
		EmpLayout.putConstraint(SpringLayout.WEST, PhnNum, 20, SpringLayout.WEST, EmpMgr);
		EmpLayout.putConstraint(SpringLayout.NORTH, PhnNum, 60, SpringLayout.SOUTH, Ln);
		JTextField PhnNumText = new JTextField("",10);
		PhnNumText.setFont(font1);
		PhnNumText.getDocument().addDocumentListener(Enabler);
		EmpMgr.add(PhnNumText);
		EmpLayout.putConstraint(SpringLayout.WEST, PhnNumText, 150, SpringLayout.WEST, Ln);
		EmpLayout.putConstraint(SpringLayout.NORTH, PhnNumText, 60, SpringLayout.SOUTH, LnText);
		
		JLabel AccNum = new JLabel("Account number:");
		AccNum.setFont(font2);
		EmpMgr.add(AccNum);
		EmpLayout.putConstraint(SpringLayout.WEST, AccNum, 20, SpringLayout.WEST, EmpMgr);
		EmpLayout.putConstraint(SpringLayout.NORTH, AccNum, 60, SpringLayout.SOUTH, PhnNum);
		JTextField AccNumText = new JTextField("",10);
		AccNumText.setFont(font1);
		AccNumText.getDocument().addDocumentListener(Enabler);
		EmpMgr.add(AccNumText);
		EmpLayout.putConstraint(SpringLayout.WEST, AccNumText, 150, SpringLayout.WEST, PhnNum);
		EmpLayout.putConstraint(SpringLayout.NORTH, AccNumText, 60, SpringLayout.SOUTH, PhnNumText);

		JLabel EmpNum = new JLabel("Employee number:");
		EmpNum.setFont(font2);
		EmpMgr.add(EmpNum);
		EmpLayout.putConstraint(SpringLayout.WEST, EmpNum, 20, SpringLayout.WEST, EmpMgr);
		EmpLayout.putConstraint(SpringLayout.NORTH, EmpNum, 60, SpringLayout.SOUTH, AccNum);
		JTextField EmpNumText = new JTextField("mispar"	,10);
		EmpNumText.setFont(font1);
		EmpNumText.getDocument().addDocumentListener(Enabler);
		EmpNumText.setEditable(false);
		EmpMgr.add(EmpNumText);
		EmpLayout.putConstraint(SpringLayout.WEST, EmpNumText, 150, SpringLayout.WEST, EmpNum);
		EmpLayout.putConstraint(SpringLayout.NORTH, EmpNumText, 60, SpringLayout.SOUTH, AccNumText);
		
		
		
		JLabel Position = new JLabel("Position:");
		Position.setFont(font2);
		EmpMgr.add(Position);
		EmpLayout.putConstraint(SpringLayout.WEST, Position, 20, SpringLayout.WEST, EmpMgr);
		EmpLayout.putConstraint(SpringLayout.NORTH, Position, 60, SpringLayout.SOUTH, EmpNum);
		PositionText = new JComboBox<String>(new String[] {"Manager","Seller","Cashier"} );
		PositionText.setFont(font1);
		((JTextField)PositionText.getEditor().getEditorComponent()).getDocument().addDocumentListener(Enabler);
		EmpMgr.add(PositionText);
		EmpLayout.putConstraint(SpringLayout.WEST, PositionText, 150, SpringLayout.WEST, Position);
		EmpLayout.putConstraint(SpringLayout.NORTH, PositionText, 60, SpringLayout.SOUTH, EmpNumText);
		
		
////////////////////////ActionListener For finding worker/////////////////////////		
ActionListener findWorkerAction = new ActionListener() {
	public void actionPerformed (ActionEvent ae) {
		Worker worker = null;
		try {
			worker = clientSideConnection.findWorker(IDNum.getText());
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		
		if (worker != null)
		{
			FnText.setText(worker.getName());
			PhnNumText.setText(worker.getPhoneNr());
			AccNumText.setText(worker.getbankAcc());
			String workerId = Integer.toString(worker.getWorkerId());
			EmpNumText.setText(workerId);
		}
	}
};

FocusListener findWorkerFAction = new FocusListener() {

	@Override
	public void focusGained(FocusEvent arg0) {
		Worker worker = null;
		try {
			worker = clientSideConnection.findWorker(IDNum.getText());
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		
		if (worker != null)
		{
			FnText.setText(worker.getName());
			PhnNumText.setText(worker.getPhoneNr());
			AccNumText.setText(worker.getbankAcc());
			String workerId = Integer.toString(worker.getWorkerId());
			EmpNumText.setText(workerId);
			
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
		
		EmpMenu.add(EmpMgr);
		EmpMenu.pack();
		EmpMenu.setVisible(true);
	}
	
	
}
