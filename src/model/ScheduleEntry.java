package model;

import java.io.Serializable;
import java.util.*;

/**
 * This class is used to add the entries made by the user using time based program and temp based program.
 * @author Vibha, Shwetha
 *
 */
public class ScheduleEntry implements Serializable
{
	String timeBasedDayOfWeek,tempBasedDayOfWeek;
	int timeBasedDuration,tempBasedDuration;
	int convertedSelectedTemp,tempSelected;
	List<String> timeBasedGroup,tempBasedGroup;
	String timeBasedSelectedTime;

	/**
	 * This is a no argument constructor of the class
	 */
	public ScheduleEntry(){
		
	}
	
	/**
	 * This is a another constructor of the class which takes the selected day, time, duration and the sprinkler groups
	 * as arguments and assign them to 4 different variables.
	 * @param day
	 * @param time
	 * @param dur
	 * @param grp
	 */
	public ScheduleEntry(String day, String time,int dur, List<String> grp)
	{
		timeBasedSelectedTime = time;
		timeBasedDayOfWeek = day;
		timeBasedDuration = dur;
		timeBasedGroup=grp;
	}
 
	/**
	 * This is another constructor of the class which takes the selected day, temp, duration and the sprinkler groups
	 * as arguments and assign them to 4 different variables.
	 * @param day
	 * @param temp
	 * @param dur
	 * @param grp
	 */
	public ScheduleEntry(String day, int temp, int dur, List<String>grp){
		tempBasedDayOfWeek = day;
		tempSelected = temp;
		 tempBasedDuration = dur;
		 tempBasedGroup = grp;
	}
	
	/**
	 * This method is used to return the time that the sprinklers were set to go on in the time based panel
	 * @return String
	 */
	public String getTimebasedSelectedTime(){
		return timeBasedSelectedTime;	
	}
	
	/**
	 * This method is used to return the day that the sprinklers were set to go on in the time based panel
	 * @return String
	 */
	public  String getTimeBasedDayOfWeek(){
		return timeBasedDayOfWeek;
	}
	
	/**
	 * This method is used to return the duration that the sprinklers were set to go on in the time based panel
	 * @return int
	 */
	public int getTimeBasedDuration()
	{
		return timeBasedDuration;
	}
	
	/**
	 * This method is used to return the sprinkler groups that were selected in the time based panel
	 * @return List<String>
	 */
	public List<String> getTimeBasedGroup(){
		return timeBasedGroup;
	}
}