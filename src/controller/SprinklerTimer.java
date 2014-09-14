package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.Timer;

import model.Schedule;
import model.ScheduleEntry;

/**
 * This Class is used to set timer and start the sprinklers at designated times(multiple entries for the same day) 
 * and at designated days and run the sprinklers for the specified duration.
 * @author Vibha
 *
 */
public class SprinklerTimer 
{
	Schedule clockSchedule;
	static Timer clockTimer; // added static
	String weekDay,currentTime,inputTime,userSelectedTime;
	int duration,systemWeekDay,selectedNumberWeekday;
	List<String> group;
	Date extractedSystemTime,convertedSelectedTime;
	GardenController timerController = new GardenController(); // creating a GardenController object
	int x=0;
	List<ScheduleEntry> deSerializedScheduleEntryList1 = new ArrayList<ScheduleEntry>(); //Creating an arrayList
	String subSysTime,subUserTime,subSysTime1,subUserTime1;
	
	/**
	 * This is a no argument constructor
	 */
	public SprinklerTimer()
	{
		
	}
	
	/**
	 * This is a constructor that takes a serialized arrayList and deserializes it	
	 * @param deSerializedScheduleEntryList12
	 */
	public SprinklerTimer(List<ScheduleEntry> deSerializedScheduleEntryList12){
		deSerializedScheduleEntryList1 = deSerializedScheduleEntryList12;
		CheckIfAnyOtherUserInput(); // Call this function to check if there are any more inputs for the same day.
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss"); // converting the String to a Date format
		try {
				convertedSelectedTime = sdf.parse(inputTime);//converting to Date type
			} 
		catch (ParseException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userSelectedTime = sdf.format(convertedSelectedTime); //Extracting just the time from the Date format
		if(clockTimer == null) 
		{
			startSprinklerAnimation(); // Call this method if the timer is null i.e. if te timer is not started.
		}
	}
	
	/**
	 * This method is used to assign numbers to the weekday that the user selected so as to compare with the current system time.
	 * @return
	 */
	public int getWeekDay()
	{
		if (weekDay.contains("Sunday")) //If the user selected day is Sunday, then assign 1
			selectedNumberWeekday = 1;
		else if(weekDay.contains("Monday")) //If the user selected day is Monday, then assign 2
			selectedNumberWeekday = 2;
		else if(weekDay.contains("Tuesday"))//If the user selected day is Tuesday, then assign 3
			selectedNumberWeekday = 3;
		else if(weekDay.contains("Wednesday"))//If the user selected day is Wednesday, then assign 4
			selectedNumberWeekday = 4;
		else if(weekDay .contains("Thursday"))//If the user selected day is Thursday, then assign 5
			selectedNumberWeekday = 5;
		else if(weekDay.contains("Friday"))//If the user selected day is Friday, then assign 6
			selectedNumberWeekday = 6;
		else if(weekDay.contains("Saturday"))//If the user selected day is Saturday, then assign 7
			selectedNumberWeekday = 7;
		return selectedNumberWeekday; // return the selected weekday number
	}
		
	/**
	 * This method is used to start the timer that runs the selected sprinklers for a duration that the user has selected
	 */
	void startSprinklerAnimation()
	{
		if(clockTimer==null)
		{
			clockTimer = new Timer(1000,new ClockListener()); // add a eventListener to the timer
			clockTimer.start(); //starts the timer
			System.out.println("After timer.start!!!");
		}
	}
	
	/**
	 * This method stops the sprinklers
	 */
	void stopSprinklerAnimation()
	{
		if(clockTimer!=null)//if the timer is running
		{
			timerController.deactivateSprinklers(group);//deactivate the selected sprinklers

		}
	}
	
	/**
	 * This method stops the started timer and sets it to null
	 */
	public void stopRunningTimer()
	{
		if(clockTimer!=null) // if the timer is running
		{
			clockTimer.stop(); // stop the clockTimer
			clockTimer = null; //sets the timer to null
			timerController.deactivateSprinklers(group); //Deactivates the sprinklers

		}
	}
	
	class ClockListener implements ActionListener 
	{
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		Calendar now = Calendar.getInstance();
	    		extractedSystemTime = now.getTime();
	    		systemWeekDay=now.get(Calendar.DAY_OF_WEEK);
	    		SimpleDateFormat sdf_1 = new SimpleDateFormat("hh:mm:ss");
	    		currentTime = sdf_1.format(extractedSystemTime);
	    		
	    		System.out.println("System Week day!!!!!"+systemWeekDay);
	    		System.out.println("converted user Week day!!!!!"+getWeekDay());
	    		System.out.println("duration --->"+duration);
	    		subSysTime=currentTime.substring(0, 5);
	    		subUserTime=userSelectedTime.substring(0, 5);
	    		if((subSysTime.contains(subUserTime)) && (systemWeekDay == selectedNumberWeekday) && (duration!=0))
	    		{
	    			System.out.println("Inside the listenerrrrrrrrrrrrrr!!!!");
	    			x=1;
	    			timerController.activateSprinklers(group);	
	    		}
	    		
	    		if((x==1)&&(duration!=0))
	    		{
	    			duration--;
	    			if(duration == 1)
	    			{
	    				//group.clear();////////vd//////
	    			}
	    		}
	    		//stop sprinkler at end of duration.
	    		if(duration == 0)
	    		{
	    			System.out.println("Inside the stop!!!!");
    				stopSprinklerAnimation();
    				//group.clear();////////vd//////
    				
    				CheckIfAnyOtherUserInput();
    			}
	    	}
		}
	
	void CheckIfAnyOtherUserInput()
	{
		int listSize = deSerializedScheduleEntryList1.size();
		int i=0,selectedNumberWeekday = 0;
		String deSerializedDayOfWeek ;
		ScheduleEntry deSerializedScheduleEntryObject = new ScheduleEntry();
		
		while(listSize != i)
		{
			deSerializedScheduleEntryObject = deSerializedScheduleEntryList1.get(i);
			
			deSerializedDayOfWeek = deSerializedScheduleEntryObject.getTimeBasedDayOfWeek();
			System.out.println("deserialized week of day"+deSerializedDayOfWeek);
			
			if (deSerializedDayOfWeek.contains("Sunday"))
				{
					System.out.println("deserialized Sunday");
					selectedNumberWeekday = 1;
				}
			else if(deSerializedDayOfWeek.contains("Monday"))
				{
					selectedNumberWeekday = 2;
				}
			else if(deSerializedDayOfWeek.contains("Tuesday"))
				{
					selectedNumberWeekday = 3;
				}
			else if(deSerializedDayOfWeek.contains("Wednesday"))
				{
				selectedNumberWeekday = 4;
				}
			else if(deSerializedDayOfWeek.contains("Thursday"))
				{
				selectedNumberWeekday = 5;
				}
			else if(deSerializedDayOfWeek.contains("Friday"))
				{
					selectedNumberWeekday = 6;
				}
			else if(deSerializedDayOfWeek.contains("Saturday"))
				{
				selectedNumberWeekday = 7;
				}
			
			inputTime = deSerializedScheduleEntryObject.getTimebasedSelectedTime();
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
			try {
				convertedSelectedTime = sdf.parse(inputTime);//converting to Date type
				System.out.println("converted selected time is: " +convertedSelectedTime);
				} 
			catch (ParseException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Calendar now = Calendar.getInstance();
    		extractedSystemTime = now.getTime();
    		systemWeekDay=now.get(Calendar.DAY_OF_WEEK);
    		SimpleDateFormat sdf_1 = new SimpleDateFormat("hh:mm:ss");
    		currentTime = sdf_1.format(extractedSystemTime);
			
			userSelectedTime = sdf.format(convertedSelectedTime);
			subSysTime1=currentTime.substring(0, 5);
    		subUserTime1=userSelectedTime.substring(0, 5);
    		
    		group = deSerializedScheduleEntryObject.getTimeBasedGroup();
			weekDay = deSerializedDayOfWeek;
			
				
			Calendar cal = Calendar.getInstance();
			if((cal.get(Calendar.DAY_OF_WEEK)==selectedNumberWeekday) &&  (subSysTime1.contains(subUserTime1)))
				{
					System.out.println("Inside CheckAnyUserInfo:"+deSerializedDayOfWeek);
					duration = deSerializedScheduleEntryObject.getTimeBasedDuration();
					break;
				}
			i++;
				
			}	
		if(listSize == 0){
			clockTimer.stop();//added now
		}
	}
}









