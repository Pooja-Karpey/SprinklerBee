package view;
import javax.swing.*;

import java.awt.*;

/**
 * This class is used to set the left panel of the application
 * @author Vibha
 *
 */
public class CenterPanelLeft extends JPanel
{
	private JPanel buttonsPanel;
	JPanel displayFieldPanel;
	BarChart chart;
	
	/**
	 * No argument constructor that sets the panel elements
	 */
	public CenterPanelLeft()
	{
		setPreferredSize(new Dimension(600,100)); // sets the size
		setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));//sets the border
	}	
}
