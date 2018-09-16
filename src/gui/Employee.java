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
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.json.simple.parser.ParseException;

import clientSide.ClientSideConnection;
import clientSide.Shop;
import clientSide.Worker;

public class Employee {
	Boolean isWorkerfound, passwordValidation;
	Worker workerFound;
	public  Employee(ClientSideConnection clientSideConnection) {
		Font font1 = new Font("Ariel",Font.PLAIN,10);
		Font font2 = new Font("Ariel",Font.BOLD,14);
		JFrame EmpMenu = new JFrame();
		EmpMenu.setTitle("Employee managment menu");
		//Set the Panel on the middle
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		Point newLocation = new Point(middle.x - (600 / 2), 
		                              middle.y - (700 / 2));
		EmpMenu.setLocation(newLocation);
		Image img = Toolkit.getDefaultToolkit().getImage("./files/EmpIcon.png");
		EmpMenu.setIconImage(img);
		EmpMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

				
		JPanel EmpMgr = new JPanel();
		SpringLayout EmpLayout = new SpringLayout();
		EmpMgr.setLayout(EmpLayout);
		JComboBox<String> PositionText;
		
		EmpMgr.setBorder(BorderFactory.createTitledBorder("Employee Menu"));
		EmpMgr.setBackground(Color.white);
		EmpMgr.setPreferredSize(new Dimension (550 , 650));

		JButton Save = new JButton("Save");
		Save.setFont(font2);
		EmpMgr.add(Save);
		Save.setEnabled(false);
		EmpLayout.putConstraint(SpringLayout.WEST, Save, 100, SpringLayout.WEST, EmpMgr);
		EmpLayout.putConstraint(SpringLayout.NORTH, Save, 540, SpringLayout.NORTH, EmpMgr);
		
		JButton Back = new JButton("Back");
		Back.setFont(font2);
		EmpMgr.add(Back);
		EmpLayout.putConstraint(SpringLayout.WEST,Back , 12, SpringLayout.EAST, Save);
		EmpLayout.putConstraint(SpringLayout.NORTH, Back, 540, SpringLayout.NORTH, EmpMgr);
		Back.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				EmpMenu.dispose();
				new	MainMenu(clientSideConnection);
			}
		});
		
		JButton Search = new JButton("Search employee");
		Search.setFont(font2);
		Search.setEnabled(false);
		EmpMgr.add(Search);
		EmpLayout.putConstraint(SpringLayout.WEST, Search, 380, SpringLayout.WEST, EmpMgr);

		JLabel ID = new JLabel("Employee ID:");
		ID.setFont(font2);
		EmpMgr.add(ID);
		EmpLayout.putConstraint(SpringLayout.WEST, ID, 20, SpringLayout.WEST, EmpMgr);
		
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
		DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
		decimalFormat.setGroupingUsed(false);
		
		JTextField IDNum = new JFormattedTextField(decimalFormat);
		IDNum.setColumns(20);
		IDNum.setFont(font1);
		EmpMgr.add(IDNum);
		EmpLayout.putConstraint(SpringLayout.WEST, IDNum, 150, SpringLayout.WEST, ID);

		
		JLabel Fn = new JLabel("Full name:");
		Fn.setFont(font2);
		EmpMgr.add(Fn);
		EmpLayout.putConstraint(SpringLayout.WEST, Fn, 20, SpringLayout.WEST, EmpMgr);
		EmpLayout.putConstraint(SpringLayout.NORTH, Fn, 60, SpringLayout.SOUTH, ID);
		
		JTextField FnText = new JTextField("",20);
		FnText.setFont(font1);

		EmpMgr.add(FnText);
		EmpLayout.putConstraint(SpringLayout.WEST, FnText, 150, SpringLayout.WEST, ID);
		EmpLayout.putConstraint(SpringLayout.NORTH, FnText, 60, SpringLayout.SOUTH, IDNum);
		

		
		JLabel Pass = new JLabel("Password:");
		Pass.setFont(font2);
		EmpMgr.add(Pass);
		EmpLayout.putConstraint(SpringLayout.WEST, Pass, 20, SpringLayout.WEST, EmpMgr);
		EmpLayout.putConstraint(SpringLayout.NORTH, Pass, 60, SpringLayout.SOUTH, Fn);
		JPasswordField PassText = new JPasswordField("",20);
		PassText.setFont(font1);

		EmpMgr.add(PassText);
		EmpLayout.putConstraint(SpringLayout.WEST, PassText, 150, SpringLayout.WEST, Fn);
		EmpLayout.putConstraint(SpringLayout.NORTH, PassText, 60, SpringLayout.SOUTH, FnText);
		
		JLabel PhnNum = new JLabel("Phone number:");
		PhnNum.setFont(font2);
		EmpMgr.add(PhnNum);
		EmpLayout.putConstraint(SpringLayout.WEST, PhnNum, 20, SpringLayout.WEST, EmpMgr);
		EmpLayout.putConstraint(SpringLayout.NORTH, PhnNum, 60, SpringLayout.SOUTH, Pass);
		JTextField PhnNumText = new JTextField("",20);
		PhnNumText.setFont(font1);
		EmpMgr.add(PhnNumText);
		EmpLayout.putConstraint(SpringLayout.WEST, PhnNumText, 150, SpringLayout.WEST, Pass);
		EmpLayout.putConstraint(SpringLayout.NORTH, PhnNumText, 60, SpringLayout.SOUTH, PassText);
		
		JLabel AccNum = new JLabel("Account number:");
		AccNum.setFont(font2);
		EmpMgr.add(AccNum);
		EmpLayout.putConstraint(SpringLayout.WEST, AccNum, 20, SpringLayout.WEST, EmpMgr);
		EmpLayout.putConstraint(SpringLayout.NORTH, AccNum, 60, SpringLayout.SOUTH, PhnNum);
		JTextField AccNumText = new JTextField("",20);
		AccNumText.setFont(font1);

		EmpMgr.add(AccNumText);
		EmpLayout.putConstraint(SpringLayout.WEST, AccNumText, 150, SpringLayout.WEST, PhnNum);
		EmpLayout.putConstraint(SpringLayout.NORTH, AccNumText, 60, SpringLayout.SOUTH, PhnNumText);

		JLabel EmpNum = new JLabel("Employee number:");
		EmpNum.setFont(font2);
		EmpMgr.add(EmpNum);
		EmpLayout.putConstraint(SpringLayout.WEST, EmpNum, 20, SpringLayout.WEST, EmpMgr);
		EmpLayout.putConstraint(SpringLayout.NORTH, EmpNum, 60, SpringLayout.SOUTH, AccNum);
		JTextField EmpNumText = new JTextField("0"	,20);
		EmpNumText.setFont(font1);

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
		PositionText.setSelectedItem("Seller");
		PositionText.setFont(font1);

		EmpMgr.add(PositionText);
		EmpLayout.putConstraint(SpringLayout.WEST, PositionText, 150, SpringLayout.WEST, Position);
		EmpLayout.putConstraint(SpringLayout.NORTH, PositionText, 60, SpringLayout.SOUTH, EmpNumText);
		
		isWorkerfound = false;
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
			PassText.setText(worker.getPassword());
			PhnNumText.setText(worker.getPhoneNr());
			AccNumText.setText(worker.getBankAcc());
			String workerId = Integer.toString(worker.getWorkerId());
			EmpNumText.setText(workerId);
			PositionText.setSelectedItem(worker.getJob());
			isWorkerfound = true;
			workerFound = worker;
			passwordValidation = true;
		}
		else
		{
			EmpNumText.setText("");
			EmpNumText.setText(""+clientSideConnection.getNewWorkerID());
			FnText.setText("");
			PassText.setText("");
			PhnNumText.setText("");
			AccNumText.setText("");
			PositionText.setSelectedItem("Seller");
		}
	}
};

////////////////////End of ActionListener For login panel/////////////////////						



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
				&& (PassText.getPassword().length != 0)
				&& !PhnNumText.getText().isEmpty()
				&& !AccNumText.getText().isEmpty()
				&& passwordValidation)
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
		Search.setEnabled(true);
	}
};
///////////////////End Save Enable Action///////////////////


////////////////////Clear fields on save//////////////////

Save.addActionListener(new ActionListener() {
	public void actionPerformed (ActionEvent ae) {
		IDNum.setText("");
		EmpNumText.setText("");
		FnText.setText("");
		PassText.setText("");
		PhnNumText.setText("");
		AccNumText.setText("");
		PositionText.setSelectedItem("Seller");
	}
});

//////////////////End Clear fields on save//////////////////


////////////Password Rules///////////////////

passwordValidation = false;

JLabel[] passwordRules = new JLabel[3];
passwordRules[0] = new JLabel(new String("<html><u>Password Rules:</u></html>"));
passwordRules[1] = new JLabel(new String("Length of 8 charcters"));
passwordRules[2] = new JLabel(new String("At least 1 number, big letter and spacial charcter."));

EmpMgr.add(passwordRules[0]);
EmpLayout.putConstraint(SpringLayout.WEST, passwordRules[0], 150, SpringLayout.WEST, Fn);
EmpLayout.putConstraint(SpringLayout.NORTH, passwordRules[0], 0, SpringLayout.SOUTH, PassText);
EmpMgr.add(passwordRules[1]);
EmpLayout.putConstraint(SpringLayout.WEST, passwordRules[1], 150, SpringLayout.WEST, Fn);
EmpLayout.putConstraint(SpringLayout.NORTH, passwordRules[1], 0, SpringLayout.SOUTH, passwordRules[0]);
EmpMgr.add(passwordRules[2]);
EmpLayout.putConstraint(SpringLayout.WEST, passwordRules[2], 150, SpringLayout.WEST, Fn);
EmpLayout.putConstraint(SpringLayout.NORTH, passwordRules[2], 0, SpringLayout.SOUTH, passwordRules[1]);

PassText.addFocusListener(new FocusListener() {
	
	@Override
	public void focusLost(FocusEvent e) {
		String pattern = "(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+!=])(?=\\S+$).{8,}";
		System.out.println(new String(PassText.getPassword()));
		String password = new String(PassText.getPassword());
		if (password.matches(pattern) == false)
		{
			PassText.setBackground(Color.red);
			Save.setEnabled(false);
			passwordValidation = false;
		}
		else
		{
			PassText.setBackground(Color.green);
			passwordValidation = true;
		}
		
	}
	
	@Override
	public void focusGained(FocusEvent e) {}
});



//////////End Password Rules////////////////

		FnText.getDocument().addDocumentListener(SaveEnabler);
		PassText.getDocument().addDocumentListener(SaveEnabler);
		EmpNumText.getDocument().addDocumentListener(SaveEnabler);
		AccNumText.getDocument().addDocumentListener(SaveEnabler);
		PhnNumText.getDocument().addDocumentListener(SaveEnabler);
		IDNum.getDocument().addDocumentListener(SearchEnabler);


		Save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Worker worker = new Worker(IDNum.getText(),
						Integer.parseInt(EmpNumText.getText()),
						FnText.getText(),
						PhnNumText.getText(),
						AccNumText.getText(),
						isWorkerfound?workerFound.getShopName():clientSideConnection.getShop().getShopName(),
						PositionText.getSelectedItem().toString(),
						new String(PassText.getPassword()));
				clientSideConnection.saveWorker(worker);
			}
		});
		

		IDNum.addActionListener(findWorkerAction);
		Search.addActionListener(findWorkerAction);

		EmpMenu.add(EmpMgr);
		EmpMenu.pack();
		EmpMenu.setVisible(true);
	}
	
	
}
