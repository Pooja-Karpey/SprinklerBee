package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class adds the sprinklers group to a list and an iterator for this list traverses
 * through the list and sets the status of the group. This class is also used to calculate the water consumed by the 
 * sprinklers
 * @author Vibha,Shwetha
 *
 */
public class SprinklerGroup implements Serializable
{
	
	String groupName;
	String groupStatus;
	List <Sprinkler> sprinklerList=new ArrayList<Sprinkler>();
	//transient static File file= new File("waterusage.dat");
	
	/**
	 * This method adds the sprinklers to a sprinklerList
	 * @param sprinkler
	 */
	public void addSprinkler(Sprinkler sprinkler)
	{
			this.sprinklerList.add(sprinkler);
	}
	
	/**
	 * this method returns the sprinkler list
	 * @return List<Sprinkler>
	 */
	public List<Sprinkler> getSprinklersInGroup()
	{
		return this.sprinklerList;		//all the sprinklers
	}
	
	/**
	 * this method add the first element of the list to the sprinkler and returns the sprinkler group
	 * @return string
	 */
	public String getGroupName()
	{
		Sprinkler sprinkler=this.sprinklerList.get(0);//gets the first element
		return sprinkler.group;
	}
	
	/**
	 * this method returns the current status of the sprinklers
	 * @return
	 */
	public boolean getGroupStatus()
	{
	Sprinkler sprinkler=this.sprinklerList.get(0);//gets the first element
	return sprinkler.isCurrentStatus();//returns the current status
	}
	
	/**
	 * this method iterates through the sprinkler list and sets the current status of the sprinklers
	 * @param status
	 */
	public void setGroupStatus(boolean status)
	{
		Iterator<Sprinkler> i=this.sprinklerList.iterator();// iterator for the arrayList
		while(i.hasNext()) // while there are more elements in the array list
			i.next().setCurrentStatus(status);//get the next element and set the status
	}
	
	/**
	 * This method is used to get the total water consumed by all the sprinklers
	 * @return int
	 */
	public int getTotalWaterUsage()
	{
		int volume=0;
		List<Sprinkler> sList=this.getSprinklersInGroup();// assign the selected sprinklers to an arrayList
		Iterator<Sprinkler> i=sList.iterator();//an ieterator for the list
		while(i.hasNext()) //while there are more elements in teh list
			volume+=i.next().getWaterUsage();// add the total volume
		return volume;
	}
	
	/**
	 * This method is used to get the water consumed by each group
	 * @return Map<String,Integer>
	 */
	public Map<String,Integer> getGroupWaterUsage()
	{
		Map<String,Integer> map=new HashMap<String,Integer>();//create a hash map
		Sprinkler sprinkler=null;
		int northVolume=0,eastVolume=0,westVolume=0,southVolume=0;
		List<Sprinkler> sList=this.getSprinklersInGroup(); //selected sprinklers
		Iterator<Sprinkler> i=sList.iterator();
		while(i.hasNext())
		{
			sprinkler=i.next();
			if(sprinkler.getGroup().equals("north"))
				northVolume+=sprinkler.getWaterUsage();//call getWaterUsuage method if the northGroup is selected
			else if(sprinkler.getGroup().equals("east"))
				eastVolume+=sprinkler.getWaterUsage(); //call getWaterUsuage method if the eastGroup is selected
			else if(sprinkler.getGroup().equals("west"))
				westVolume+=sprinkler.getWaterUsage();//call getWaterUsuage method if the westGroup is selected
			else if(sprinkler.getGroup().equals("south"))
				southVolume+=sprinkler.getWaterUsage();//call getWaterUsuage method if the southGroup is selected
		}
		//inserting elements in to the map
		map.put("north", northVolume);
		map.put("east", eastVolume);
		map.put("west", westVolume);
		map.put("south",southVolume);
		return map;
	}
}
