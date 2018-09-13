package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Stack;
import java.util.spi.CalendarDataProvider;

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

import com.toedter.calendar.JDateChooser;

import clientSide.ClientSideConnection;
import clientSide.Shop;
import clientSide.Worker;
public class Reports {

	Reports(ClientSideConnection clientSideConnection){
		
		JComboBox<String> ItemType , Item;
		ItemType = new JComboBox<String>(new String[] {"Shirt","Pants"} );
		Item = new JComboBox<String>(new String[] {"White Shirt", "Black Shirt","White Pants" , "Black Pants"} );
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
		
		JDateChooser Date = new JDateChooser();
		Date.setDateFormatString("dd-MM-yyyy");
		Date currDate = new Date();
		Date.setDate(currDate);
		Date.setPreferredSize(new Dimension(100, 20));
		JButton[] RepBtn = new JButton[4];
		JTextField[] DataField = new JTextField[7];
		JPanel RepMain = new JPanel();
		SpringLayout RepLayout = new SpringLayout();
		RepMain.setLayout(RepLayout);
		RepMain.add(Date);
		RepMain.add(ItemType);
		RepMain.add(Item);
		
		Date.addPropertyChangeListener(new PropertyChangeListener() {

		    @Override
		    public void propertyChange(PropertyChangeEvent e) {
		        RepBtn[3].setEnabled(true); 
		    }
		});
		
		
		for (int j =1 ; j<=7 ; ++j) {
			DataField[j-1] = new JTextField("",4);
			DataField[j-1].setEditable(false);
			RepMain.add(DataField[j-1]);
		}
		
		for (int i = 1; i<=4 ; ++i)
		{
			RepBtn[i-1] = new JButton("Export!");
			RepMain.add(RepBtn[i-1]);
		}
		RepMain.setBorder(BorderFactory.createTitledBorder("Reports Menu"));
		RepMain.setBackground(Color.white);
		RepMain.setPreferredSize(new Dimension (730 , 510));
		
		JLabel AllSell = new JLabel("Branch sells:");
		RepMain.add(AllSell);
		
		JLabel ShirtCat = new JLabel("Shirt Catergory:");
		RepMain.add(ShirtCat);
		
		JLabel WhiteShirt = new JLabel("White Shirts:");
		RepMain.add(WhiteShirt);
		
		JLabel BlackShirt = new JLabel("Black Shirts:");
		RepMain.add(BlackShirt);
		
		JLabel PantsCat = new JLabel("Pants Catergory:");
		RepMain.add(PantsCat);
		
		JLabel WhitePants = new JLabel("White Pants:");
		RepMain.add(WhitePants);
		
		JLabel BlackPants = new JLabel("Black Pants:");
		RepMain.add(BlackPants);
		
		JLabel branchRep = new JLabel("Branch sells report:");
		RepMain.add(branchRep);
		
		JLabel dateRep = new JLabel("Report by date:");
		RepMain.add(dateRep);
		
		JLabel catRep = new JLabel("Report by category:");
		RepMain.add(catRep);
		
		JLabel ItemRep = new JLabel ("Report by item:");
		RepMain.add(ItemRep);
		
		JButton Back = new JButton("Back");
		RepMain.add(Back);
	
		
		
		RepLayout.putConstraint(SpringLayout.WEST, AllSell, 0, SpringLayout.WEST, RepMain);
		RepLayout.putConstraint(SpringLayout.NORTH, AllSell, 20, SpringLayout.NORTH, RepMain);
		
		RepLayout.putConstraint(SpringLayout.WEST, DataField[0], 0, SpringLayout.WEST, RepBtn[0]);
		RepLayout.putConstraint(SpringLayout.NORTH, DataField[0], 0, SpringLayout.NORTH, AllSell);
		
		RepLayout.putConstraint(SpringLayout.WEST, ShirtCat, 0, SpringLayout.WEST, AllSell);
		RepLayout.putConstraint(SpringLayout.NORTH, ShirtCat, 30, SpringLayout.NORTH, AllSell);
		
		RepLayout.putConstraint(SpringLayout.WEST, DataField[1], 0, SpringLayout.WEST, DataField[0]);
		RepLayout.putConstraint(SpringLayout.NORTH, DataField[1], 0, SpringLayout.NORTH, ShirtCat);
		
		RepLayout.putConstraint(SpringLayout.WEST, WhiteShirt, 0, SpringLayout.WEST, AllSell);
		RepLayout.putConstraint(SpringLayout.NORTH, WhiteShirt, 30, SpringLayout.NORTH, ShirtCat);
		
		RepLayout.putConstraint(SpringLayout.WEST, DataField[2], 0, SpringLayout.WEST, DataField[0]);
		RepLayout.putConstraint(SpringLayout.NORTH, DataField[2], 0, SpringLayout.NORTH, WhiteShirt);
		
		RepLayout.putConstraint(SpringLayout.WEST, BlackShirt, 20, SpringLayout.EAST, DataField[2]);
		RepLayout.putConstraint(SpringLayout.NORTH, BlackShirt, 0, SpringLayout.NORTH, WhiteShirt);
		
		RepLayout.putConstraint(SpringLayout.WEST, DataField[3], 30, SpringLayout.EAST, BlackShirt);
		RepLayout.putConstraint(SpringLayout.NORTH, DataField[3], 0, SpringLayout.NORTH, BlackShirt);
		
		RepLayout.putConstraint(SpringLayout.WEST, PantsCat, 0, SpringLayout.WEST, AllSell);
		RepLayout.putConstraint(SpringLayout.NORTH, PantsCat, 30, SpringLayout.NORTH, BlackShirt);
		
		RepLayout.putConstraint(SpringLayout.WEST, DataField[4], 0, SpringLayout.WEST, DataField[0]);
		RepLayout.putConstraint(SpringLayout.NORTH, DataField[4], 0, SpringLayout.NORTH, PantsCat);
		
		RepLayout.putConstraint(SpringLayout.WEST, WhitePants, 0, SpringLayout.WEST, AllSell);
		RepLayout.putConstraint(SpringLayout.NORTH, WhitePants, 30, SpringLayout.NORTH, PantsCat);
		
		RepLayout.putConstraint(SpringLayout.WEST, DataField[5], 0, SpringLayout.WEST, DataField[0]);
		RepLayout.putConstraint(SpringLayout.NORTH, DataField[5], 0, SpringLayout.NORTH, WhitePants);
		
		RepLayout.putConstraint(SpringLayout.WEST, BlackPants, 20, SpringLayout.EAST, DataField[2]);
		RepLayout.putConstraint(SpringLayout.NORTH, BlackPants, 0, SpringLayout.NORTH, WhitePants);
		
		RepLayout.putConstraint(SpringLayout.WEST, DataField[6], 30, SpringLayout.EAST, BlackPants);
		RepLayout.putConstraint(SpringLayout.NORTH, DataField[6], 0, SpringLayout.NORTH, BlackPants);
		
		RepLayout.putConstraint(SpringLayout.WEST, ItemRep, 0, SpringLayout.WEST, RepMain);
		RepLayout.putConstraint(SpringLayout.NORTH, ItemRep, 20, SpringLayout.SOUTH, ItemType);	
		
		RepLayout.putConstraint(SpringLayout.WEST, catRep, 0, SpringLayout.WEST, RepMain);
		RepLayout.putConstraint(SpringLayout.NORTH, catRep, 20, SpringLayout.SOUTH, Date);		
		
		RepLayout.putConstraint(SpringLayout.WEST, dateRep, 0, SpringLayout.WEST, RepMain);
		RepLayout.putConstraint(SpringLayout.NORTH, dateRep, 20, SpringLayout.SOUTH, branchRep);
		
		RepLayout.putConstraint(SpringLayout.WEST, branchRep, 0, SpringLayout.WEST, RepMain);
		RepLayout.putConstraint(SpringLayout.NORTH, branchRep, 40, SpringLayout.SOUTH, BlackPants);
		
		RepLayout.putConstraint(SpringLayout.WEST, Date, 0, SpringLayout.WEST, RepMain);
		RepLayout.putConstraint(SpringLayout.NORTH, Date, 5 ,SpringLayout.SOUTH, dateRep);
		
		RepLayout.putConstraint(SpringLayout.WEST, ItemType, 0, SpringLayout.WEST, RepMain);
		RepLayout.putConstraint(SpringLayout.NORTH, ItemType, 5, SpringLayout.SOUTH, catRep);
		
		RepLayout.putConstraint(SpringLayout.WEST, Item, 0, SpringLayout.WEST, RepMain);
		RepLayout.putConstraint(SpringLayout.NORTH, Item, 5, SpringLayout.SOUTH, ItemRep);
		
		RepLayout.putConstraint(SpringLayout.WEST, RepBtn[3], 0, SpringLayout.WEST, RepBtn[2]);
		RepLayout.putConstraint(SpringLayout.NORTH, RepBtn[3], -5, SpringLayout.NORTH, Date);
		
		RepLayout.putConstraint(SpringLayout.WEST, RepBtn[2], 20, SpringLayout.EAST, catRep);
		RepLayout.putConstraint(SpringLayout.NORTH, RepBtn[2], -5, SpringLayout.NORTH, ItemType);
				
		RepLayout.putConstraint(SpringLayout.WEST, RepBtn[1], 0, SpringLayout.WEST, RepBtn[2]);
		RepLayout.putConstraint(SpringLayout.NORTH, RepBtn[1], -5, SpringLayout.NORTH, Item);
		
		RepLayout.putConstraint(SpringLayout.WEST, RepBtn[0], 0, SpringLayout.WEST, RepBtn[2]);
		RepLayout.putConstraint(SpringLayout.NORTH, RepBtn[0], -5, SpringLayout.NORTH, branchRep);
		
		RepLayout.putConstraint(SpringLayout.EAST, Back, 0, SpringLayout.EAST, RepMain);
		RepLayout.putConstraint(SpringLayout.SOUTH, Back, 0, SpringLayout.SOUTH, RepMain);
		
		Back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				RepMenu.dispose();
				new MainMenu(clientSideConnection);
			}
		});
		
		RepBtn[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					clientSideConnection.saveSimpleSaveReport();
				} catch (IOException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				RepBtn[0].setEnabled(false);
				
			}
		});
		
		RepBtn[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					clientSideConnection.saveReportByItem("Pants 1");
				} catch (IOException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				RepBtn[1].setEnabled(false);
				
			}
		});
		
		RepBtn[2].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					clientSideConnection.saveReportByType("Pants");
				} catch (IOException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				RepBtn[2].setEnabled(false);
				
			}
		});
		
		RepBtn[3].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					clientSideConnection.saveReportByDate("10-09-2018");
				} catch (IOException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				RepBtn[3].setEnabled(false);
				
			}
		});
		
		
		RepMenu.setPreferredSize(new Dimension(400,480));
		RepMenu.add(RepMain);
		RepMenu.pack();
		RepMenu.setVisible(true);
	}
}
