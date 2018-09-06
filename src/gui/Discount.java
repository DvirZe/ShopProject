package gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
		
		Font font2 = new Font("Ariel",Font.BOLD,14);
		JFrame DiscMenu = new JFrame();
		DiscMenu.setTitle("Sales managment menu");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		Point newLocation = new Point(middle.x - (600 / 2), 
		                              middle.y - (700 / 2));
		DiscMenu.setLocation(newLocation);
		DiscMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextField[] DiscPrec = new JTextField[4];
		JPanel DiscMain = new JPanel();
		SpringLayout DiscLayout = new SpringLayout();
		DiscMain.setLayout(DiscLayout);
		
		for (int i = 1; i<=2 ; ++i)
		{
			DiscPrec[i-1] = new JTextField("", 4);
			DiscMain.add(DiscPrec[i-1]);
		}
		DiscMain.setBorder(BorderFactory.createTitledBorder("Discount Menu"));
		DiscMain.setBackground(Color.white);
		DiscMain.setPreferredSize(new Dimension (730 , 510));
		JLabel VIPDisc = new JLabel("VIP Customer Discount:");
		DiscMain.add(VIPDisc);
		
		JLabel ReturnDisc = new JLabel("Retuened Customer Discount:");
		DiscMain.add(ReturnDisc);
		
		JButton Save = new JButton("Save");
		Save.setEnabled(false);
		DiscMain.add(Save);
		
		JButton Back = new JButton("Back");
		DiscMain.add(Back);
		
		
		DiscLayout.putConstraint(SpringLayout.WEST, ReturnDisc, 0, SpringLayout.WEST, DiscMain);
		DiscLayout.putConstraint(SpringLayout.NORTH, ReturnDisc, 30, SpringLayout.NORTH, VIPDisc);
		
		DiscLayout.putConstraint(SpringLayout.WEST, VIPDisc, 0, SpringLayout.WEST, DiscMain);
		DiscLayout.putConstraint(SpringLayout.NORTH, VIPDisc, 10, SpringLayout.NORTH, DiscMain);
		
		DiscLayout.putConstraint(SpringLayout.WEST, DiscPrec[1], 15, SpringLayout.EAST, ReturnDisc);
		DiscLayout.putConstraint(SpringLayout.NORTH, DiscPrec[1], 0, SpringLayout.NORTH, ReturnDisc);
		
		DiscLayout.putConstraint(SpringLayout.WEST, DiscPrec[0], 0, SpringLayout.WEST, DiscPrec[1]);
		DiscLayout.putConstraint(SpringLayout.NORTH, DiscPrec[0], 0, SpringLayout.NORTH, VIPDisc);
		
		DiscLayout.putConstraint(SpringLayout.WEST, Save, 0, SpringLayout.WEST, DiscPrec[1]);
		DiscLayout.putConstraint(SpringLayout.NORTH, Save, 10, SpringLayout.SOUTH, DiscPrec[1]);
		
		DiscLayout.putConstraint(SpringLayout.WEST, Back, 5, SpringLayout.EAST, Save);
		DiscLayout.putConstraint(SpringLayout.NORTH, Back, 0, SpringLayout.NORTH, Save);
		
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
				if (!DiscPrec[0].getText().isEmpty()
						&& !DiscPrec[1].getText().isEmpty())
					Save.setEnabled(true);
				else
					Save.setEnabled(false);
				}
			
		};
		
		DiscPrec[0].getDocument().addDocumentListener(SaveEnabler);
		DiscPrec[1].getDocument().addDocumentListener(SaveEnabler);
		
		Back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				DiscMenu.dispose();
				new StoreManagment(clientSideConnection);
			}
		});
		
		
		
		Save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		
		
		
		DiscMenu.setPreferredSize(new Dimension(340,160));
		DiscMenu.add(DiscMain);
		DiscMenu.pack();
		DiscMenu.setVisible(true);
		
		
	}

}
