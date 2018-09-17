package gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

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
import clientSide.Shop;
import clientSide.Worker;


public class Discount {
	public Discount(ClientSideConnection clientSideConnection) {
		
		JFrame discMenu = new JFrame();
		discMenu.setTitle("Discount managment menu");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		Point newLocation = new Point(middle.x - (600 / 2), 
		                              middle.y - (700 / 2));
		discMenu.setLocation(newLocation);
		Image img = Toolkit.getDefaultToolkit().getImage("./files/DiscIcon.png");
		discMenu.setIconImage(img);
		discMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextField[] discPrec = new JTextField[4];
		JPanel discMain = new JPanel();
		SpringLayout discLayout = new SpringLayout();
		discMain.setLayout(discLayout);
		
		for (int i = 1; i<=2 ; ++i)
		{
			discPrec[i-1] = new JTextField("", 4);
			discMain.add(discPrec[i-1]);
		}
		discPrec[0].setText(clientSideConnection.getShop().getDiscountForCustomer("VIP").toString());
		discPrec[1].setText(clientSideConnection.getShop().getDiscountForCustomer("Return").toString());

		discMain.setBorder(BorderFactory.createTitledBorder("Discount Menu"));
		discMain.setBackground(Color.white);
		discMain.setPreferredSize(new Dimension (730 , 510));
		JLabel vipDisc = new JLabel("VIP Customer Discount:");
		discMain.add(vipDisc);
		
		JLabel returnDisc = new JLabel("Retuened Customer Discount:");
		discMain.add(returnDisc);
		
		JButton save = new JButton("save");
		save.setEnabled(false);
		discMain.add(save);
		
		JButton back = new JButton("back");
		discMain.add(back);
		
		
		discLayout.putConstraint(SpringLayout.WEST, returnDisc, 0, SpringLayout.WEST, discMain);
		discLayout.putConstraint(SpringLayout.NORTH, returnDisc, 30, SpringLayout.NORTH, vipDisc);
		
		discLayout.putConstraint(SpringLayout.WEST, vipDisc, 0, SpringLayout.WEST, discMain);
		discLayout.putConstraint(SpringLayout.NORTH, vipDisc, 10, SpringLayout.NORTH, discMain);
		
		discLayout.putConstraint(SpringLayout.WEST, discPrec[1], 15, SpringLayout.EAST, returnDisc);
		discLayout.putConstraint(SpringLayout.NORTH, discPrec[1], 0, SpringLayout.NORTH, returnDisc);
		
		discLayout.putConstraint(SpringLayout.WEST, discPrec[0], 0, SpringLayout.WEST, discPrec[1]);
		discLayout.putConstraint(SpringLayout.NORTH, discPrec[0], 0, SpringLayout.NORTH, vipDisc);
		
		discLayout.putConstraint(SpringLayout.WEST, save, 0, SpringLayout.WEST, discPrec[1]);
		discLayout.putConstraint(SpringLayout.NORTH, save, 10, SpringLayout.SOUTH, discPrec[1]);
		
		discLayout.putConstraint(SpringLayout.WEST, back, 5, SpringLayout.EAST, save);
		discLayout.putConstraint(SpringLayout.NORTH, back, 0, SpringLayout.NORTH, save);
		
		////////////////////Save Enable listener//////////////////
		
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
				if (!discPrec[0].getText().isEmpty()
						&& !discPrec[1].getText().isEmpty())
					save.setEnabled(true);
				else
					save.setEnabled(false);
				}
			
		};
		
		////////////////////End of Save Enable listener//////////////////
		
		discPrec[0].getDocument().addDocumentListener(saveEnabler);
		discPrec[1].getDocument().addDocumentListener(saveEnabler);
		
		////////////////////back button listener//////////////////
		
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				discMenu.dispose();
				new StoreManagment(clientSideConnection);
			}
		});
		////////////////////end of back button listener//////////////////
		
		
		////////////////////save button listener//////////////////
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientSideConnection.setDiscount(Double.parseDouble(discPrec[0].getText()), Double.parseDouble(discPrec[1].getText()));
				save.setEnabled(false);
			}
		});
		////////////////////end of save button listener//////////////////
		
		
		discMenu.setPreferredSize(new Dimension(340,160));
		discMenu.add(discMain);
		discMenu.pack();
		discMenu.setVisible(true);
		
		
	}

}
