package view;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This class adds all the necessary panels to the left panel of our application
 * @author Vibha,shwetha
 *
 */
class ControlPanelDisplay extends JFrame
{
	private BannerPanel banner;
	private BottomPanel bottomPanel;
	private TabPanel tabPanel;
	private JPanel jPanelRight;
	private CenterPanelRight centerPanelRight;
	private TabPanel tp;
	private JPanel selection;
	
	/**
	 * this is a no argument constructor
	 */
	public ControlPanelDisplay()
	{
		//sets the title of out application page
		setTitle("SprinklerBee"); 
		setLayout(new BorderLayout());//sets the layout of the application
		Container contentPane = getContentPane();
	    
    	banner = new BannerPanel();//calling the constructor of the BannerPanel
    	contentPane.add(banner,BorderLayout.PAGE_START); //adding the banner to the contentPane
    	
    	bottomPanel = new BottomPanel(); 
    	contentPane.add(bottomPanel,BorderLayout.SOUTH);//adding the bottompanel to the south of the contentPane
    	
    	tabPanel = new TabPanel();
    	tabPanel.setLayout(new BoxLayout(tabPanel,BoxLayout.Y_AXIS));//sets the layout of the tabpanel
        contentPane.add(tabPanel,BorderLayout.WEST);//adding the tab panel to the content pane

        jPanelRight = new JPanel(); 
    	jPanelRight.setLayout(new BoxLayout(jPanelRight,BoxLayout.Y_AXIS));//sets the right panel layout
    	jPanelRight.setPreferredSize(new Dimension(600,100));//sets the size
    	jPanelRight.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));//sets teh border
        contentPane.add(jPanelRight,BorderLayout.EAST); //adding a right panel to the application
        centerPanelRight = new CenterPanelRight();
        jPanelRight.add(centerPanelRight);//adding the panel
	}
}
