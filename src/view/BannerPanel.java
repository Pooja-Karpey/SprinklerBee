package view;
import javax.swing.*;
import java.awt.*;

/**
 * This class is used to set the banner for the application
 * @author Vibha,Shwetha
 *
 */
class BannerPanel extends JPanel
{
	/**
	 * this is the constructor 
	 */
	public BannerPanel()
	{
		setPreferredSize(new Dimension(0,75));//Setting the size of the banner panel
		setBackground(Color.LIGHT_GRAY); //setting the BannerPanel background color
		setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black)); // setting the border
		JLabel bannerText = new JLabel("SprinklerBee System"); // creating the label
		Font bigFont = new Font("serif",Font.BOLD,25); // Making the font bigger
		bannerText.setFont(bigFont); // Adding the font to the BannerPanel
		add(bannerText); //Adding the text to the panel
	}
}
