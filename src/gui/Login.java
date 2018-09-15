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
		
		JPanel Login = new JPanel();
		Login.setBorder(BorderFactory.createTitledBorder("Your Details"));
		Login.setBackground(Color.WHITE);
		Login.setPreferredSize(new Dimension (400 , 260));
		Login.setLayout(new BoxLayout(Login, BoxLayout.Y_AXIS));
		JLabel Name = new JLabel("Username:");
		Name.setFont(font2);
		Login.add(Name);
		SpringLayout LogLayout = new SpringLayout();
		Login.setLayout(LogLayout);
		
		JLabel LogIcon = new JLabel();
		LogIcon.setIcon(new ImageIcon("./files/login-icon.png"));
		Login.add(LogIcon);
		

		
		JTextField Ntext = new JTextField("" , 20);
		Ntext.setFont(font);
		Login.add(Ntext);
		
		
		JLabel Pass = new JLabel("Password:");
		Pass.setFont(font2);
		Login.add(Pass);
		
		JPasswordField Ptext = new JPasswordField("" , 20);
		Ptext.setFont(font);
		Login.add(Ptext);
		
		LogLayout.putConstraint(SpringLayout.WEST, Name, 10, SpringLayout.WEST, Login);
		LogLayout.putConstraint(SpringLayout.NORTH, Name, 10, SpringLayout.NORTH, Login);
		
		LogLayout.putConstraint(SpringLayout.WEST, Ntext, 0, SpringLayout.WEST, Name);
		LogLayout.putConstraint(SpringLayout.NORTH, Ntext, 10, SpringLayout.SOUTH, Name);
		
		LogLayout.putConstraint(SpringLayout.WEST, Pass, 10, SpringLayout.WEST, Login);
		LogLayout.putConstraint(SpringLayout.NORTH, Pass, 20, SpringLayout.SOUTH, Ntext);
		
		LogLayout.putConstraint(SpringLayout.WEST, Ptext, 0, SpringLayout.WEST, Pass);
		LogLayout.putConstraint(SpringLayout.NORTH, Ptext, 10, SpringLayout.SOUTH, Pass);
		
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
				new MainMenu(clientSideConnection); }
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
		LogLayout.putConstraint(SpringLayout.WEST, Enter, -40, SpringLayout.EAST, Ptext);
		LogLayout.putConstraint(SpringLayout.NORTH, Enter, 20, SpringLayout.SOUTH, Ptext);
		
		LogLayout.putConstraint(SpringLayout.WEST, LogIcon, 160, SpringLayout.WEST, Login);
		LogLayout.putConstraint(SpringLayout.NORTH, LogIcon, -10 , SpringLayout.NORTH, Enter);
		
		Login.add(Enter);

				
		details.add(Login);
		details.pack();
		details.setVisible(true);
				
		Enter.addActionListener(logAction);

	}
}
