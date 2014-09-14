package view;
import javax.swing.*;

import java.awt.*;


/**
 * This class is used to set the bottomPanel
 * @author Vibha,shwetha
 *
 */
class BottomPanel extends JPanel
{
	public BottomPanel()
	{
		setBackground(Color.LIGHT_GRAY); // sets the background color
    	setPreferredSize(new Dimension(0,30)); //sets the size of that panel
    	setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));//sets teh border
		JLabel bottomText = new JLabel("Copyright@Team15"); // creating the label
		Font bigFont = new Font("serif",Font.BOLD,15); // Making the font bigger
		bottomText.setFont(bigFont); // Adding the font to the BannerPanel
		add(bottomText);
	}
}
