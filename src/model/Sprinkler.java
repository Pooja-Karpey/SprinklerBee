package model;

import java.io.Serializable;
import java.util.Observable;
import java.util.TimerTask;

/**
 * This class extends the observable pattern since the status of the sprinklers change.
 * @author Vibha, Shwetha
 *
 */
public class Sprinkler extends Observable implements Serializable 
{
	String id;
	String group;
	String functionalStatus;
	boolean currentStatus;
	transient int waterUsage=0;
	transient java.util.Timer timer;
	
	/**
	 * This is a no argument constructor
	 */
	public Sprinkler()
	{
		
	}
	
	/**
	 * This is another constructor of sprinkler that takes in the id, group, functional status and current status of the 
	 * sprinkler groups
	 * @param id
	 * @param group
	 * @param functionalStatus
	 * @param currentStatus
	 */
	public Sprinkler(String id, String group, String functionalStatus, boolean currentStatus)
	{
		this.id=id;
		this.group=group;
		this.functionalStatus=functionalStatus;
		setCurrentStatus(currentStatus);
		}
	
	/**
	 * This method returns the sprinkler id
	 * @return String
	 */
	public String getId() 
	{
		return id;
	}
	
	/**
	 * This methodreturns the group of the sprinklers
	 * @return
	 */
	public String getGroup()
	{
		return this.group;
	}
	
	/**
	 * This method is used to set the ID of the sprinklers
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * This method returns the functional status of the sprinklers
	 * @return String
	 */
	public String getFunctionalStatus() {
		return functionalStatus;
	}
	
	/**
	 * This method sets the functional status of the sprinklers
	 * @param functionalStatus
	 */
	public void setFunctionalStatus(String functionalStatus)
	{
		this.functionalStatus=functionalStatus;
	}
	
	/**
	 * This methods returns the current status of the sprinklers
	 * @return boolean
	 */
	public boolean isCurrentStatus() {
		return currentStatus;
	}
	
	/**
	 * This method sets the current status of the sprinklers
	 * @param currentStatus
	 */
	public void setCurrentStatus(boolean currentStatus) {
		this.currentStatus = currentStatus;
		// must call setChanged before notifyObservers to
	      // indicate model has changed
	      setChanged();
	      // notify Observers that model has changed
	      notifyObservers();
	}
	
	@Override
	public String toString()
	{
		return getId();
	}
	
	/**
	 * This method is used to start the sprinklers
	 */
	public void startSprinkler()
	{
		if(!this.isCurrentStatus())//checks if the current status of the sprinklers is false
		{
			startWaterUsageTimer();	//starts the timer
			this.setCurrentStatus(true);//sets the current status of sprinklers to true
		}
	}
	
	/**
	 * This method is used to stop the sprinklers
	 */
	public void stopSprinkler()
	{
		if(this.isCurrentStatus())//checks if the status of the sprinkler is true
		{
			timer.cancel(); // cancels the water usuage timer
			this.setCurrentStatus(false);//set the surrent status of the sprinkler to false
		}
	}
	
	/**
	 * This method is used to start the water usage timer which runs until the sprinklers are set ON
	 */
	
	public void startWaterUsageTimer()
	{
		
		timer = new java.util.Timer(); // create a timer

	      timer.schedule(new TimerTask() // schedule the timer to run for a certain amout of time
	      {
	        public void run ()
	        {
	        
	        	setWaterUsage(); // call this method to calculate the water consumed
	        }
	        },0,1000);	   
	}
	
	/**
	 * This method sets the water usage of the sprinklers and it is 2 gallons per sprinkler
	 */
	private void setWaterUsage()
	 {
		this.waterUsage=this.waterUsage+2;
   	}
	
	/**
	 * This method returns the water used by each sprinkler
	 * @return
	 */
	public int getWaterUsage()
	{
		return this.waterUsage;
	}
}
