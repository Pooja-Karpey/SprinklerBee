package view;

import controller.ConfigureSprinkler;

/**
 * This class has the main method from where we invoke our application
 * @author Vibha,Shwetha
 *
 */
public class ControlPanel 
{
	/**
	 * This method is used to set the size of our application and call the method that displays all the panels
	 */
	private static void createAndShowGUI() 
	{
        // Create application frame.
		ControlPanelDisplay frame = new ControlPanelDisplay();   
        // Show frame.
        frame.setVisible(true);
        frame.setBounds(50, 50, 1250,600);
    } 
	/**
	 * This is the main method 
	 * @param args
	 */
	 public static void main(String[] args) 
	 {   	
		 ConfigureSprinkler config=new ConfigureSprinkler();
		 config.configureSprinklers(); //configuring the sprinklers for the right panel
		 createAndShowGUI();   
	    }
}
