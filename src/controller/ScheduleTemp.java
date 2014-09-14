package controller;
import view.CenterPanelRight;
import view.TabPanel;
import controller.GardenController;
import javax.swing.Timer;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import model.Sensor;

/**
 * The class ScheduleTemp is used to start the timer for the duration that the user chooses,day and the group when he selects the 
 * temp based program on the controlPanel
 * @author Vibha, shwetha
 *
 */
public class ScheduleTemp {
	int selectedTemp,duration;
	String dayOfWeek;
	List<String> selectedGroup;
	
	int currGardenTemp;  // current garden temperature
	Timer tempTimer;
	Timer overrideTimer;
	GardenController tempController = new GardenController();//create an object of type GardenController
	Sensor tmp = new Sensor(); // create an object of type Sensor
	int lowLimit;				
	int upLimit;
	int garTemp;
	List<String> osSpr = new ArrayList<String>();//create an arrayList of all the sprinklers that will be ON when the user
												// selects the override program in the control panel
	/**
	 * This is a no argument constructor
	 */
	public ScheduleTemp()
	{
		
	}
	
	/**
	 * This is a constructor of the class which takes in the day, temp, duration and the list of the sprinklers that 
	 * the users selected in the temp based program panel and set to 4 differnt variables
	 * @param day
	 * @param temp
	 * @param dur
	 * @param grp
	 */
	public ScheduleTemp(String day,int temp,int dur, List<String> grp)
	{
		dayOfWeek = day;
		selectedTemp = temp;
		duration = dur;
		selectedGroup = grp;
		
		currGardenTemp = tmp.getGardenTemperature(); // calling a method in the Sensor class that returns the current garden temp
														// store it in a variable
		if(tempTimer == null)		//If the timer is not running then it calls a method startTmpSprinklerAnimation()
		{
			startTmpSprinklerAnimation();		
		}			
	}

	/**
	 * This method overrides the current sprinkler program selection based on the upper limit and the lower limit temperatures
	 * @param llimit
	 * @param ulimit
	 */
	public void OverrideSprinkler(int llimit, int ulimit)
	{
		lowLimit = llimit;
		upLimit=ulimit;
		garTemp = tmp.getGardenTemperature(); 	//calling a method in the Sensor class that returns the current garden temp
												//store it in a variable
		osSpr.add("north");		//adding the north group to the override sprinkler ArrayList
		osSpr.add("south");		//adding the south group to the override sprinkler ArrayList
		osSpr.add("east");		//adding the east group to the override sprinkler ArrayList
		osSpr.add("west");		//adding the west group to the override sprinkler ArrayList				
		
		if(garTemp >= upLimit)	//If the current garden temp is greater than the upperlimit temp the user has set
		{
			duration=30; 	// set the sprinklers for a duration of 30 seconds
			startOverrideSprinklerAnimation(); //call the method startOverrideSprinklerAnimation
		}
		
		else if(garTemp <= lowLimit)  //If the current Garden temperature is less than the lower limit that the user has set
		{
			duration=0;
			tempController.deactivateSprinklers(osSpr); // deactivate the sprinklers that are ON
		}	
	}
	
	/**
	 * This method is used to start the timer and activate the override program timer
	 */
	public void startOverrideSprinklerAnimation(){
		if(overrideTimer==null)
		{
			overrideTimer= new Timer(1000, overrideListener);//add a action listener to the timer
			overrideTimer.start(); //start the timer
		}
	}

	/**
	 * This is an actionListener for the timer that is used for the override program
	 */
	ActionListener overrideListener = new ActionListener() 
	{
		public void actionPerformed(ActionEvent evt) //this is the default method that should be implemented and which says what 
		{											//timer should do
			tempController.activateSprinklers(osSpr);	//call the activate sprinkler method and activate all the sprinklers
			duration--;								//run till the specified duration
			if(duration == 0)						//check if teh duration is 0
			{
				overrideTimer.stop();				//stop the override timer
				overrideTimer=null;					//set the timer to null
				tempController.deactivateSprinklers(osSpr);//deactivate the sprinklers
			}	
		}
	};
	
	/**
	 * This method is used to start the timer that is used for the temp based program by the user in the control panel
	 */
	public void startTmpSprinklerAnimation()
	{
		if(tempTimer==null) // check if the temp based timer is not running
		{
			tempTimer= new Timer(1000, tempTimerListener); // timer for 1000 milliseconds
			tempTimer.start(); // starts the temp based timer
		}
	}
	
	/**
	 * This method stops the timer if it is running and deactivates the selected sprinklers in the temp based program 
	 * in control panel
	 */
	public void stopTmpSprinklerAnimation()
	{
		if(tempTimer!=null) // check if the temp based timer is running
		{
			tempTimer.stop(); // it stops the timer if it is running
			tempController.deactivateSprinklers(selectedGroup);//deactivates the selected sprinklers
			tempTimer = null;		// sets the temp based timer to null
		}
		
	}
	
	/**
	 * ActionListener for a temp based timer 
	 */
	ActionListener tempTimerListener = new ActionListener() 
	{
		public void actionPerformed(ActionEvent evt) //this is the default method that should be implemented and which says what 
		{											//timer should do
			if (currGardenTemp == selectedTemp) // check if the current Graden temp is equal to the user selected temp
			{
				
				tempController.activateSprinklers(selectedGroup);	// if true,then activate the sprinklers for a specified duration
			}
			duration--;
			if(duration == 0) //check if the sprinklers have run for the specified duration i.e. till duration becomes 0
			{
				stopTmpSprinklerAnimation();// calling the method that deactivates the sprinklers.
			}
		}
	};
}
