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
		details.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		Login.add(Enter, BorderLayout.CENTER);

				
		details.add(Login);
		details.pack();
		details.setVisible(true);
				
		Enter.addActionListener(logAction);

	}
}
