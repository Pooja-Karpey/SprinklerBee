package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.Sprinkler;
import model.SprinklerGroup;
/**
 * The class GardenController is used to activate/deactivate the sprinklers based on the group selected
 * @author Vibha, Shwetha
 *
 */
public class GardenController 
{
	public static SprinklerGroup sprinklerGroup;  // class variable
	
	/**
	 * This is a no argument constructor
	 */
	public GardenController()
	{
		
	}
	/**
	 * This is a constructor that takes in the sprinkler group and assign it to the static variable sprinklerGroup
	 * @param group
	 */
	public GardenController(SprinklerGroup group)
	{
		sprinklerGroup=group; //assign the group to the sprinklerGroup
	}
	
	/**
	 * This method activates the sprinkler based on the selected Sprinkler's location
	 * @param location
	 */
	public void activateSprinklers(List<String> location) 
	{
		Sprinkler sprinkler;
		List<Sprinkler> sprinklerList=new ArrayList<Sprinkler>(); //Create an arrayList of the type Sprinkler
			sprinklerList=	sprinklerGroup.getSprinklersInGroup(); // put the groups in the sprinklerList 
		Iterator<Sprinkler> sprinklerItr=sprinklerList.iterator();// Assign an iterator to the arrayList
		while(sprinklerItr.hasNext()) // while there are more elements in the arrayList
		{
			sprinkler=sprinklerItr.next();  //get the next element of the array
			if(location.contains(sprinkler.getGroup())) //if the location matches with the sprinkler group
				sprinkler.startSprinkler();//Start the sprinklers
		}
	}
	
	/**
	 * This method deactivates the Sprinklers base on the selected Sprinkler's location
	 * @param location
	 */
	public void deactivateSprinklers(List<String> location) 
	{
		Sprinkler sprinkler;
		List<Sprinkler> sprinklerList=new ArrayList<Sprinkler>(); //Create an arrayList of the type Sprinkler
			sprinklerList=	sprinklerGroup.getSprinklersInGroup(); // put the groups in the sprinklerList
		Iterator<Sprinkler> sprinklerItr=sprinklerList.iterator(); // Assign an iterator to the arrayList
		while(sprinklerItr.hasNext()) // while there are more elements in the arrayList
		{
			sprinkler=sprinklerItr.next(); ////get the next element of the array
			if(location.contains(sprinkler.getGroup()))  ////if the location matches with the sprinkler group
				sprinkler.stopSprinkler();//Stop the sprinklers
		}
	}	
}

