package model;

import java.util.Calendar;
/**
 * This class is used to set the outside garden temperature for certain time frames
 * @author Vibha, Shwetha
 *
 */
public class Sensor 
{
	int gardenTemp;
	Calendar tempCal;

	/**
	 * This is a no argument constructor
	 */
	public Sensor()
	{
		
	}
	
	/**
	 * This method is used to set the outside garden temperature
	 * @return
	 */
	public int getGardenTemperature() 
	{
		tempCal = Calendar.getInstance();// getting the current date and time
		int h = tempCal.get(Calendar.HOUR_OF_DAY); // getting the hour of the day
		if(h >=6 && h<12)  //sets the temp to 55 if the hour is greater than or equal to 6 and less than 12
			gardenTemp=55;
		else if(h>= 12 && h< 17) //sets the temp to 95 if the hour is greater than or equal to 12 and less than 17
			gardenTemp=95;
		else if(h>=17 && h<20)//sets the temp to 55 if the hour is greater than or equal to 17 and less than 20
			gardenTemp= 80;
		else if(h>=20 && h<24)//sets the temp to 55 if the hour is greater than or equal to 20 and less than 24
			gardenTemp=45;
		return gardenTemp; // returns the garden temerature based on the above conditions
	}

	
}