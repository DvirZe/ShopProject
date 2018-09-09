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
public class Reports {

	Reports(ClientSideConnection clientSideConnection){
		
		Font font2 = new Font("Ariel",Font.BOLD,14);
		JFrame RepMenu = new JFrame();
		RepMenu.setTitle("Generatre reports");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		Point newLocation = new Point(middle.x - (600 / 2), 
		                              middle.y - (700 / 2));
		RepMenu.setLocation(newLocation);
		Image img = Toolkit.getDefaultToolkit().getImage("./files/RepIcon.png");
		RepMenu.setIconImage(img);
		RepMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton[] RepBtn = new JButton[4];
		JPanel RepMain = new JPanel();
		SpringLayout RepLayout = new SpringLayout();
		RepMain.setLayout(RepLayout);
		
		for (int i = 1; i<=4 ; ++i)
		{
			RepBtn[i-1] = new JButton("Generate!");
			RepMain.add(RepBtn[i-1]);
		}
		RepMain.setBorder(BorderFactory.createTitledBorder("Reports Menu"));
		RepMain.setBackground(Color.white);
		RepMain.setPreferredSize(new Dimension (730 , 510));
		JLabel ChainRep = new JLabel("Chain sells report:");
		RepMain.add(ChainRep);
		
		JLabel BranchRep = new JLabel("Branch sells report:");
		RepMain.add(BranchRep);
		
		JLabel EmpRep = new JLabel("Employee sells report:");
		RepMain.add(EmpRep);
		
		JLabel ItemRep = new JLabel ("Item sells report:");
		RepMain.add(ItemRep);
		
		JButton Back = new JButton("Back");
		RepMain.add(Back);
		
		RepLayout.putConstraint(SpringLayout.WEST, ItemRep, 0, SpringLayout.WEST, RepMain);
		RepLayout.putConstraint(SpringLayout.NORTH, ItemRep, 20, SpringLayout.SOUTH, EmpRep);	
		
		RepLayout.putConstraint(SpringLayout.WEST, EmpRep, 0, SpringLayout.WEST, RepMain);
		RepLayout.putConstraint(SpringLayout.NORTH, EmpRep, 20, SpringLayout.SOUTH, BranchRep);		
		
		RepLayout.putConstraint(SpringLayout.WEST, BranchRep, 0, SpringLayout.WEST, RepMain);
		RepLayout.putConstraint(SpringLayout.NORTH, BranchRep, 20, SpringLayout.SOUTH, ChainRep);
		
		RepLayout.putConstraint(SpringLayout.WEST, ChainRep, 0, SpringLayout.WEST, RepMain);
		RepLayout.putConstraint(SpringLayout.NORTH, ChainRep, 20, SpringLayout.NORTH, RepMain);
		
		RepLayout.putConstraint(SpringLayout.WEST, RepBtn[3], 0, SpringLayout.WEST, RepBtn[2]);
		RepLayout.putConstraint(SpringLayout.NORTH, RepBtn[3], -5, SpringLayout.NORTH, BranchRep);
		
		RepLayout.putConstraint(SpringLayout.WEST, RepBtn[2], 20, SpringLayout.EAST, EmpRep);
		RepLayout.putConstraint(SpringLayout.NORTH, RepBtn[2], -5, SpringLayout.NORTH, EmpRep);
				
		RepLayout.putConstraint(SpringLayout.WEST, RepBtn[1], 0, SpringLayout.WEST, RepBtn[2]);
		RepLayout.putConstraint(SpringLayout.NORTH, RepBtn[1], -5, SpringLayout.NORTH, ItemRep);
		
		RepLayout.putConstraint(SpringLayout.WEST, RepBtn[0], 0, SpringLayout.WEST, RepBtn[2]);
		RepLayout.putConstraint(SpringLayout.NORTH, RepBtn[0], -5, SpringLayout.NORTH, ChainRep);
		
		RepLayout.putConstraint(SpringLayout.WEST, Back, 0, SpringLayout.EAST, RepBtn[1]);
		RepLayout.putConstraint(SpringLayout.NORTH, Back, 20, SpringLayout.SOUTH, RepBtn[1]);
		
		Back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				RepMenu.dispose();
				new StoreManagment(clientSideConnection);
			}
		});
		
		
		RepMenu.setPreferredSize(new Dimension(400,260));
		RepMenu.add(RepMain);
		RepMenu.pack();
		RepMenu.setVisible(true);
	}
}
