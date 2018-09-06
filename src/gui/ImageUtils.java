package gui;

import java.awt.Toolkit;
import java.awt.*;
import javax.swing.*;

import javax.print.DocFlavor.URL;
import javax.swing.ImageIcon;

public class ImageUtils {
	public final static String IMAGES_FOLDER = "./files/";
	
	public static ImageIcon getImageIcon(String name) {
		java.net.URL imageURL = ImageUtils.class.getResource(IMAGES_FOLDER + name);
		Image image = Toolkit.getDefaultToolkit().createImage(imageURL);
		if (image == null) {
			return null;
		}
		return new ImageIcon(image);
		
	}

}
