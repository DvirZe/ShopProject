package gui;

import java.awt.*;
import javax.swing.*;

public class ImageGetter extends JLabel {
	public ImageGetter(String name , String IconName)
	{
		super();
		this.setText(name);
		this.setIcon(ImageUtils.getImageIcon(IconName));
		this.setHorizontalAlignment(SwingConstants.CENTER);
		setBorder(BorderFactory.createEtchedBorder());
		setVerticalTextPosition(JLabel.CENTER);
		setPreferredSize(new Dimension(50,60));
	}

}
