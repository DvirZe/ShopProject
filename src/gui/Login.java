package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import clientSide.ClientSideConnection;



public class Login extends JPanel{
	private static final long serialVersionUID = 1604207079338835436L;
	JSONObject json;
	
	public Login(ClientSideConnection clientSideConnection) {
		json = new JSONObject();
		Font font = new Font("Ariel",Font.PLAIN,20);
		Font font2 = new Font("Ariel",Font.BOLD,20);
		JFrame details = new JFrame();
		details.setTitle("Store managment login");
		details.setSize(580, 220);
		details.setLocationRelativeTo(null);
		Image img = Toolkit.getDefaultToolkit().getImage("./files/store_icon.jpg");
		details.setIconImage(img);
		details.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		JPanel login = new JPanel();
		login.setBorder(BorderFactory.createTitledBorder("Your Details"));
		login.setBackground(Color.WHITE);
		login.setPreferredSize(new Dimension (400 , 260));
		login.setLayout(new BoxLayout(login, BoxLayout.Y_AXIS));
		JLabel name = new JLabel("Username:");
		name.setFont(font2);
		login.add(name);
		SpringLayout logLayout = new SpringLayout();
		login.setLayout(logLayout);
		
		JLabel logIcon = new JLabel();
		logIcon.setIcon(new ImageIcon("./files/login-icon.png"));
		login.add(logIcon);
		

		
		JTextField nText = new JTextField("" , 20);
		nText.setFont(font);
		login.add(nText);
		
		
		JLabel pass = new JLabel("password:");
		pass.setFont(font2);
		login.add(pass);
		
		JPasswordField pText = new JPasswordField("" , 20);
		pText.setFont(font);
		login.add(pText);
		
		logLayout.putConstraint(SpringLayout.WEST, name, 10, SpringLayout.WEST, login);
		logLayout.putConstraint(SpringLayout.NORTH, name, 10, SpringLayout.NORTH, login);
		
		logLayout.putConstraint(SpringLayout.WEST, nText, 0, SpringLayout.WEST, name);
		logLayout.putConstraint(SpringLayout.NORTH, nText, 10, SpringLayout.SOUTH, name);
		
		logLayout.putConstraint(SpringLayout.WEST, pass, 10, SpringLayout.WEST, login);
		logLayout.putConstraint(SpringLayout.NORTH, pass, 20, SpringLayout.SOUTH, nText);
		
		logLayout.putConstraint(SpringLayout.WEST, pText, 0, SpringLayout.WEST, pass);
		logLayout.putConstraint(SpringLayout.NORTH, pText, 10, SpringLayout.SOUTH, pass);
		
////////////////////////ActionListener For login panel/////////////////////////		
		ActionListener logAction = new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				int status = 0;
				try {
					status = clientSideConnection.login(nText.getText(), ""+new String(pText.getPassword()));//checks the credentials supplied by the user
				} catch (ParseException | IOException e) {
					e.printStackTrace();
				}
				
				if (status == 1) {
				details.dispose();
				new MainMenu(clientSideConnection); }
				else {
					JOptionPane.showMessageDialog(null,"Wrong username or password!");
					if (JOptionPane.OK_OPTION == 0) {
						nText.setText("");
						pText.setText("");
					}
				}
			}
		};
////////////////////End of ActionListener For login panel/////////////////////			
		
		
		JButton enter = new JButton("login");
		enter.setFont(font2);
		logLayout.putConstraint(SpringLayout.WEST, enter, -40, SpringLayout.EAST, pText);
		logLayout.putConstraint(SpringLayout.NORTH, enter, 20, SpringLayout.SOUTH, pText);
		
		logLayout.putConstraint(SpringLayout.WEST, logIcon, 160, SpringLayout.WEST, login);
		logLayout.putConstraint(SpringLayout.NORTH, logIcon, -10 , SpringLayout.NORTH, enter);
		login.add(enter);

		enter.addActionListener(logAction);
		pText.addActionListener(logAction);
		
		details.add(login);
		details.pack();
		details.setVisible(true);

	}
}
