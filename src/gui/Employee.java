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
		JFrame empMenu = new JFrame();
		empMenu.setTitle("Employee managment menu");
		//Set the Panel on the middle
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		Point newLocation = new Point(middle.x - (600 / 2), 
		                              middle.y - (700 / 2));
		empMenu.setLocation(newLocation);
		Image img = Toolkit.getDefaultToolkit().getImage("./files/EmpIcon.png");
		empMenu.setIconImage(img);
		empMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

				
		JPanel empMgr = new JPanel();
		SpringLayout empLayout = new SpringLayout();
		empMgr.setLayout(empLayout);
		JComboBox<String> positionText;
		
		empMgr.setBorder(BorderFactory.createTitledBorder("Employee Menu"));
		empMgr.setBackground(Color.white);
		empMgr.setPreferredSize(new Dimension (550 , 650));

		JButton save = new JButton("save");
		save.setFont(font2);
		empMgr.add(save);
		save.setEnabled(false);
		empLayout.putConstraint(SpringLayout.WEST, save, 100, SpringLayout.WEST, empMgr);
		empLayout.putConstraint(SpringLayout.NORTH, save, 540, SpringLayout.NORTH, empMgr);
		
		JButton back = new JButton("back");
		back.setFont(font2);
		empMgr.add(back);
		empLayout.putConstraint(SpringLayout.WEST,back , 12, SpringLayout.EAST, save);
		empLayout.putConstraint(SpringLayout.NORTH, back, 540, SpringLayout.NORTH, empMgr);
		back.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				empMenu.dispose();
				new	MainMenu(clientSideConnection);
			}
		});
		
		JButton search = new JButton("search employee");
		search.setFont(font2);
		search.setEnabled(false);
		empMgr.add(search);
		empLayout.putConstraint(SpringLayout.WEST, search, 380, SpringLayout.WEST, empMgr);

		JLabel id = new JLabel("Employee id:");
		id.setFont(font2);
		empMgr.add(id);
		empLayout.putConstraint(SpringLayout.WEST, id, 20, SpringLayout.WEST, empMgr);
		
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
		DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
		decimalFormat.setGroupingUsed(false);
		
		JTextField idNum = new JFormattedTextField(decimalFormat);
		idNum.setColumns(20);
		idNum.setFont(font1);
		empMgr.add(idNum);
		empLayout.putConstraint(SpringLayout.WEST, idNum, 150, SpringLayout.WEST, id);

		
		JLabel fn = new JLabel("Full name:");
		fn.setFont(font2);
		empMgr.add(fn);
		empLayout.putConstraint(SpringLayout.WEST, fn, 20, SpringLayout.WEST, empMgr);
		empLayout.putConstraint(SpringLayout.NORTH, fn, 60, SpringLayout.SOUTH, id);
		
		JTextField fnText = new JTextField("",20);
		fnText.setFont(font1);

		empMgr.add(fnText);
		empLayout.putConstraint(SpringLayout.WEST, fnText, 150, SpringLayout.WEST, id);
		empLayout.putConstraint(SpringLayout.NORTH, fnText, 60, SpringLayout.SOUTH, idNum);
		

		
		JLabel pass = new JLabel("Password:");
		pass.setFont(font2);
		empMgr.add(pass);
		empLayout.putConstraint(SpringLayout.WEST, pass, 20, SpringLayout.WEST, empMgr);
		empLayout.putConstraint(SpringLayout.NORTH, pass, 60, SpringLayout.SOUTH, fn);
		JPasswordField passText = new JPasswordField("",20);
		passText.setFont(font1);

		empMgr.add(passText);
		empLayout.putConstraint(SpringLayout.WEST, passText, 150, SpringLayout.WEST, fn);
		empLayout.putConstraint(SpringLayout.NORTH, passText, 60, SpringLayout.SOUTH, fnText);
		
		JLabel phnNum = new JLabel("Phone number:");
		phnNum.setFont(font2);
		empMgr.add(phnNum);
		empLayout.putConstraint(SpringLayout.WEST, phnNum, 20, SpringLayout.WEST, empMgr);
		empLayout.putConstraint(SpringLayout.NORTH, phnNum, 60, SpringLayout.SOUTH, pass);
		JTextField phnNumText = new JTextField("",20);
		phnNumText.setFont(font1);
		empMgr.add(phnNumText);
		empLayout.putConstraint(SpringLayout.WEST, phnNumText, 150, SpringLayout.WEST, pass);
		empLayout.putConstraint(SpringLayout.NORTH, phnNumText, 60, SpringLayout.SOUTH, passText);
		
		JLabel accNum = new JLabel("Account number:");
		accNum.setFont(font2);
		empMgr.add(accNum);
		empLayout.putConstraint(SpringLayout.WEST, accNum, 20, SpringLayout.WEST, empMgr);
		empLayout.putConstraint(SpringLayout.NORTH, accNum, 60, SpringLayout.SOUTH, phnNum);
		JTextField accNumText = new JTextField("",20);
		accNumText.setFont(font1);

		empMgr.add(accNumText);
		empLayout.putConstraint(SpringLayout.WEST, accNumText, 150, SpringLayout.WEST, phnNum);
		empLayout.putConstraint(SpringLayout.NORTH, accNumText, 60, SpringLayout.SOUTH, phnNumText);

		JLabel empNum = new JLabel("Employee number:");
		empNum.setFont(font2);
		empMgr.add(empNum);
		empLayout.putConstraint(SpringLayout.WEST, empNum, 20, SpringLayout.WEST, empMgr);
		empLayout.putConstraint(SpringLayout.NORTH, empNum, 60, SpringLayout.SOUTH, accNum);
		JTextField empNumText = new JTextField("0"	,20);
		empNumText.setFont(font1);

		empNumText.setEditable(false);
		empMgr.add(empNumText);
		empLayout.putConstraint(SpringLayout.WEST, empNumText, 150, SpringLayout.WEST, empNum);
		empLayout.putConstraint(SpringLayout.NORTH, empNumText, 60, SpringLayout.SOUTH, accNumText);
		
		
		
		JLabel position = new JLabel("Position:");
		position.setFont(font2);
		empMgr.add(position);
		empLayout.putConstraint(SpringLayout.WEST, position, 20, SpringLayout.WEST, empMgr);
		empLayout.putConstraint(SpringLayout.NORTH, position, 60, SpringLayout.SOUTH, empNum);
		positionText = new JComboBox<String>(new String[] {"Manager","Seller","Cashier"} );
		positionText.setSelectedItem("Seller");
		positionText.setFont(font1);

		empMgr.add(positionText);
		empLayout.putConstraint(SpringLayout.WEST, positionText, 150, SpringLayout.WEST, position);
		empLayout.putConstraint(SpringLayout.NORTH, positionText, 60, SpringLayout.SOUTH, empNumText);
		
		isWorkerfound = false;
////////////////////////ActionListener For finding worker/////////////////////////		
ActionListener findWorkerAction = new ActionListener() {
	public void actionPerformed (ActionEvent ae) {
		Worker worker = null;
		try {
			worker = clientSideConnection.findWorker(idNum.getText()); //getting the workers id.
			
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		
		if (worker != null)	//if an id was typed get the employee data
		{
			fnText.setText(worker.getName());
			passText.setText(worker.getPassword());
			phnNumText.setText(worker.getPhoneNr());
			accNumText.setText(worker.getBankAcc());
			String workerId = Integer.toString(worker.getWorkerId());
			empNumText.setText(workerId);
			positionText.setSelectedItem(worker.getJob());
			isWorkerfound = true;
			workerFound = worker;
			passwordValidation = true;
		}
		else // if an id was'nt typed or employee was'nt found erase all of the text fields in the form.
		{
			empNumText.setText("");
			empNumText.setText(""+clientSideConnection.getNewWorkerID());
			fnText.setText("");
			passText.setText("");
			phnNumText.setText("");
			accNumText.setText("");
			positionText.setSelectedItem("Seller");
		}
	}
};

////////////////////End of ActionListener For login panel/////////////////////						



///////////////////save Enable Action/////////////////////////
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
	public void saveEnable(){ //checks if all of the following fields contains data.
		if (!idNum.getText().isEmpty()
				&& !fnText.getText().isEmpty()
				&& (passText.getPassword().length != 0)
				&& !phnNumText.getText().isEmpty()
				&& !accNumText.getText().isEmpty()
				&& passwordValidation)
			save.setEnabled(true);
		else
			save.setEnabled(false);
		}
	
};
///////////////////End of save Enable Action///////////////////

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
		search.setEnabled(true);
	}
};
///////////////////End of search Enable Action///////////////////


////////////password Rules///////////////////

passwordValidation = false;

JLabel[] passwordRules = new JLabel[3];
passwordRules[0] = new JLabel(new String("<html><u>password Rules:</u></html>"));
passwordRules[1] = new JLabel(new String("Length of 8 to 16 characters"));
passwordRules[2] = new JLabel(new String("At least 1 number, big letter and spacial character."));

empMgr.add(passwordRules[0]);
empLayout.putConstraint(SpringLayout.WEST, passwordRules[0], 150, SpringLayout.WEST, fn);
empLayout.putConstraint(SpringLayout.NORTH, passwordRules[0], 0, SpringLayout.SOUTH, passText);
empMgr.add(passwordRules[1]);
empLayout.putConstraint(SpringLayout.WEST, passwordRules[1], 150, SpringLayout.WEST, fn);
empLayout.putConstraint(SpringLayout.NORTH, passwordRules[1], 0, SpringLayout.SOUTH, passwordRules[0]);
empMgr.add(passwordRules[2]);
empLayout.putConstraint(SpringLayout.WEST, passwordRules[2], 150, SpringLayout.WEST, fn);
empLayout.putConstraint(SpringLayout.NORTH, passwordRules[2], 0, SpringLayout.SOUTH, passwordRules[1]);



passText.addFocusListener(new FocusListener() {
	
	@Override
	public void focusLost(FocusEvent e) {
		String pattern = "(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+!=])(?=\\S+$).{8,16}"; //determine the policy of the password
		String password = new String(passText.getPassword());
		if (password.matches(pattern) == false)
		{
			passText.setBackground(Color.red);
			save.setEnabled(false);
			passwordValidation = false;
		}
		else
		{
			passText.setBackground(Color.green);
			passwordValidation = true;
		}
		
	}
	
	@Override
	public void focusGained(FocusEvent e) {}
});



//////////End password Rules////////////////


		/////////////adding save and search enabler to the relevant text fields/////////////
		fnText.getDocument().addDocumentListener(saveEnabler);
		passText.getDocument().addDocumentListener(saveEnabler);
		empNumText.getDocument().addDocumentListener(saveEnabler);
		accNumText.getDocument().addDocumentListener(saveEnabler);
		phnNumText.getDocument().addDocumentListener(saveEnabler);
		positionText.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!idNum.getText().isEmpty()
						&& !fnText.getText().isEmpty()
						&& (passText.getPassword().length != 0)
						&& !phnNumText.getText().isEmpty()
						&& !accNumText.getText().isEmpty()
						&& passwordValidation)
					save.setEnabled(true);
			}
		});
		idNum.getDocument().addDocumentListener(searchEnabler);
		/////////////End of adding save and search enabler to the relevant text fields/////////////

		////////////save button listener///////////////////
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {// get the text from the fields and sends them to the worker constructor
				Worker worker = new Worker(idNum.getText(),
						Integer.parseInt(empNumText.getText()),
						fnText.getText(),
						phnNumText.getText(),
						accNumText.getText(),
						isWorkerfound?workerFound.getShopName():clientSideConnection.getShop().getShopName(),
						positionText.getSelectedItem().toString(),
						new String(passText.getPassword()));
				clientSideConnection.saveWorker(worker);
				//Clear all fields
				idNum.setText("");
				empNumText.setText("");
				fnText.setText("");
				passText.setText("");
				phnNumText.setText("");
				accNumText.setText("");
				passText.setBackground(null);
				positionText.setSelectedItem("Seller");
			}
		});
		
		
		////////////End of save button listener///////////////////

		idNum.addActionListener(findWorkerAction);
		search.addActionListener(findWorkerAction);

		empMenu.add(empMgr);
		empMenu.pack();
		empMenu.setVisible(true);
	}
	
	
}
