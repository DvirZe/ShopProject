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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
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
		
		LinkedHashMap<String, String> stats = new LinkedHashMap<String, String>();
		try { //Get the info for the screen
			stats = clientSideConnection.getStats();
		} catch (IOException e2) {
			e2.printStackTrace();
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		
		System.out.println(stats);
		
		JComboBox<String> itemType , item;
		itemType = new JComboBox<String>(new String[] {"Shirt","Pants"} );
		item = new JComboBox<String>(new String[] {"Shirt 1", "Shirt 2","Pants 1" , "Pants 2"} );
		JFrame repMenu = new JFrame();
		repMenu.setTitle("Generatre reports");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		Point newLocation = new Point(middle.x - (600 / 2), 
		                              middle.y - (700 / 2));
		repMenu.setLocation(newLocation);
		Image img = Toolkit.getDefaultToolkit().getImage("./files/RepIcon.png");
		repMenu.setIconImage(img);
		repMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JDateChooser Date = new JDateChooser();
		Date.setDateFormatString("dd-MM-yyyy");
		Date currDate = new Date();
		Date.setDate(currDate);
		Date.setPreferredSize(new Dimension(100, 20));
		Date.setMaxSelectableDate(currDate);
		JButton[] RepBtn = new JButton[4];
		JTextField[] DataField = new JTextField[7];
		JPanel RepMain = new JPanel();
		SpringLayout RepLayout = new SpringLayout();
		RepMain.setLayout(RepLayout);
		RepMain.add(Date);
		RepMain.add(itemType);
		RepMain.add(item);
		
		//creates 7 text fields for the menu
		for (int j =1 ; j<=7 ; ++j) {
			DataField[j-1] = new JTextField("",4);
			DataField[j-1].setEditable(false);
			RepMain.add(DataField[j-1]);
		}
		
		//creates 4 export buttons
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
		
		JLabel itemRep = new JLabel ("Report by item:");
		RepMain.add(itemRep);
		
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
		
		RepLayout.putConstraint(SpringLayout.WEST, itemRep, 0, SpringLayout.WEST, RepMain);
		RepLayout.putConstraint(SpringLayout.NORTH, itemRep, 20, SpringLayout.SOUTH, itemType);	
		
		RepLayout.putConstraint(SpringLayout.WEST, catRep, 0, SpringLayout.WEST, RepMain);
		RepLayout.putConstraint(SpringLayout.NORTH, catRep, 20, SpringLayout.SOUTH, Date);		
		
		RepLayout.putConstraint(SpringLayout.WEST, dateRep, 0, SpringLayout.WEST, RepMain);
		RepLayout.putConstraint(SpringLayout.NORTH, dateRep, 20, SpringLayout.SOUTH, branchRep);
		
		RepLayout.putConstraint(SpringLayout.WEST, branchRep, 0, SpringLayout.WEST, RepMain);
		RepLayout.putConstraint(SpringLayout.NORTH, branchRep, 40, SpringLayout.SOUTH, BlackPants);
		
		RepLayout.putConstraint(SpringLayout.WEST, Date, 0, SpringLayout.WEST, RepMain);
		RepLayout.putConstraint(SpringLayout.NORTH, Date, 5 ,SpringLayout.SOUTH, dateRep);
		
		RepLayout.putConstraint(SpringLayout.WEST, itemType, 0, SpringLayout.WEST, RepMain);
		RepLayout.putConstraint(SpringLayout.NORTH, itemType, 5, SpringLayout.SOUTH, catRep);
		
		RepLayout.putConstraint(SpringLayout.WEST, item, 0, SpringLayout.WEST, RepMain);
		RepLayout.putConstraint(SpringLayout.NORTH, item, 5, SpringLayout.SOUTH, itemRep);
		
		RepLayout.putConstraint(SpringLayout.WEST, RepBtn[3], 0, SpringLayout.WEST, RepBtn[2]);
		RepLayout.putConstraint(SpringLayout.NORTH, RepBtn[3], -5, SpringLayout.NORTH, Date);
		
		RepLayout.putConstraint(SpringLayout.WEST, RepBtn[2], 20, SpringLayout.EAST, catRep);
		RepLayout.putConstraint(SpringLayout.NORTH, RepBtn[2], -5, SpringLayout.NORTH, itemType);
				
		RepLayout.putConstraint(SpringLayout.WEST, RepBtn[1], 0, SpringLayout.WEST, RepBtn[2]);
		RepLayout.putConstraint(SpringLayout.NORTH, RepBtn[1], -5, SpringLayout.NORTH, item);
		
		RepLayout.putConstraint(SpringLayout.WEST, RepBtn[0], 0, SpringLayout.WEST, RepBtn[2]);
		RepLayout.putConstraint(SpringLayout.NORTH, RepBtn[0], -5, SpringLayout.NORTH, branchRep);
		
		RepLayout.putConstraint(SpringLayout.EAST, Back, 0, SpringLayout.EAST, RepMain);
		RepLayout.putConstraint(SpringLayout.SOUTH, Back, 0, SpringLayout.SOUTH, RepMain);
		
		
		/////////Back ActionListener//////////
		Back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				repMenu.dispose();
				new MainMenu(clientSideConnection);
			}
		});
		/////////Back ActionListener//////////
		
		RepBtn[0].addActionListener(new ActionListener() { //General reports ActionListener
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					clientSideConnection.saveSimpleSaveReport();
				} catch (IOException | ParseException e1) {
					e1.printStackTrace();
				}
				RepBtn[0].setEnabled(false);
				
			}
		});
		
		RepBtn[1].addActionListener(new ActionListener() { //Report by item ActionListener
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					clientSideConnection.saveReportByItem(item.getSelectedItem().toString());
				} catch (IOException | ParseException e1) {
					e1.printStackTrace();
				}
				RepBtn[1].setEnabled(false);
				
			}
		});
		
		RepBtn[2].addActionListener(new ActionListener() { //Report by Type ActionListener
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					clientSideConnection.saveReportByType(itemType.getSelectedItem().toString());
				} catch (IOException | ParseException e1) {
					e1.printStackTrace();
				}
				RepBtn[2].setEnabled(false);
				
			}
		});
		
		RepBtn[3].addActionListener(new ActionListener() { //Report by Date ActionListener
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					clientSideConnection.saveReportByDate(new SimpleDateFormat("dd-MM-yyyy").format(Date.getDate()));
				} catch (IOException | ParseException e1) {
					e1.printStackTrace();
				}
				RepBtn[3].setEnabled(false);
				
			}
		});
		
		////Update text fields
		DataField[0].setText(stats.get("TotalSelles"));
		DataField[1].setText(""+(Integer.parseInt(stats.get("Shirt 1").toString()) + Integer.parseInt(stats.get("Shirt 2").toString())));
		DataField[2].setText(stats.get("Shirt 1"));
		DataField[3].setText(stats.get("Shirt 2"));
		DataField[4].setText(""+(Integer.parseInt(stats.get("Pants 1").toString()) + Integer.parseInt(stats.get("Pants 2").toString())));
		DataField[5].setText(stats.get("Pants 1"));
		DataField[6].setText(stats.get("Pants 2"));
		
		/////////Reactivate Export Button/////////////
		Date.addPropertyChangeListener(new PropertyChangeListener() { //When change date

		    @Override
		    public void propertyChange(PropertyChangeEvent e) {
		        RepBtn[3].setEnabled(true); 
		    }
		});
		/////////End of Reactivate Export Button/////////////
		
		/////////Item Button ActionListener/////////////
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RepBtn[1].setEnabled(true);
			}
		});
		/////////End of item Button ActionListener/////////////
		
		/////////Item type Button ActionListener/////////////
		itemType.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RepBtn[2].setEnabled(true);
			}
		});
		/////////End of Item type Button ActionListener/////////////

		repMenu.setPreferredSize(new Dimension(400,480));
		repMenu.add(RepMain);
		repMenu.pack();
		repMenu.setVisible(true);
	}
}
