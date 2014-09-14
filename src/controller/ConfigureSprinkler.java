package controller;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import view.CenterPanelRight;
import model.Sprinkler;
import model.SprinklerGroup;

/**
 * Class ConfigureSprinkler is used to read the sprinkler's ID, Status and GroupName from a file and store 
 * it in different variables.
 * @author Vibha , Shwetha
 *
 */
public class ConfigureSprinkler 
{
	SprinklerGroup sprinklerGroup=new SprinklerGroup(); //Calling the constructor of the sprinklerGroup
	SprinklerGroup northGroup=new SprinklerGroup();
	SprinklerGroup southGroup=new SprinklerGroup();
	SprinklerGroup eastGroup=new SprinklerGroup();
	SprinklerGroup westGroup=new SprinklerGroup();

	/**
	 * This is a constructor with no arguments. This method is used  to read the sprinkler data from file and load
	 * the Sprinklers to the right panel and update the Sprinklers with their ID and Status
	 * 
	 */
	public void configureSprinklers()
	{
		Scanner inFile;               
		File file;
		String id, group,funStatus;
		boolean currStatus;              
		Sprinkler sprinkler;
		try {
			file= new File("sprinklerData.dat");
			inFile = new Scanner(file);
			try{
				while(inFile.hasNextLine())   		//While the end of file is not encountered
				{
				String line = inFile.nextLine();  	// read the next line
				String[] st = line.split("[\\s]+");  // splitting the words on space
				id=st[0];							// read the ID and storing it in ID
				group=st[1];						// read the groupname and storing it in a variable group
				funStatus=st[2];					// read the functional status to a variable currStatus
				currStatus=(st[3].equals("ON"))? (true):(false); // currStatus is set to true if it is ON or set to OFF it is false
				sprinkler=new Sprinkler(id,group,funStatus,currStatus); // calling the Sprinkler constructor
				sprinklerGroup.addSprinkler(sprinkler);  // adding the Sprinklers to the group 
				assignGroup(sprinkler);					// assign the sprinkler group 
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Exception occurred!");			
			e.printStackTrace();
		}
		CenterPanelRight cpr=new CenterPanelRight(sprinklerGroup); // Add the Sprinklers to the center panel right
		serialize(northGroup,"northgroup.ser"); //Serialize the north group to a file called northgroup.ser
		serialize(southGroup,"southgroup.ser"); // Serialize the south group to a file called northgroup.ser
		serialize(eastGroup,"eastGroup.ser"); //Serialize the east group to a file called northgroup.ser
		serialize(westGroup,"westGroup.ser"); //Serialize the west group to a file called northgroup.ser
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method returns the group that is read from the file
	 * @return SprinklerGroup
	 */
	SprinklerGroup getGroup()
	{
		return this.sprinklerGroup;
	}
	
	/**
	 * This method gets the group from the file and adds the Sprinklers to the respective groups
	 * @param sprinkler
	 */	
	private void assignGroup(Sprinkler sprinkler)
	{
		if(sprinkler.getGroup().equals("north")) 		//If the Sprinkler group is north, add it to the north group
			northGroup.addSprinkler(sprinkler);
		else if(sprinkler.getGroup().equals("east"))	//If the Sprinkler group is east, add it to the east group
			eastGroup.addSprinkler(sprinkler);
		else if(sprinkler.getGroup().equals("south"))	//If the Sprinkler group is south, add it to the south group
			southGroup.addSprinkler(sprinkler);
		else if(sprinkler.getGroup().equals("west"))	//If the Sprinkler group is west, add it to the west group
			westGroup.addSprinkler(sprinkler);
	}
	
	/**
	 * This method is used to serialize the spinklers based on the group.
	 * @param sprinklerGrpObj
	 * @param filename
	 */
	public static void serialize(SprinklerGroup sprinklerGrpObj, String filename){
		 FileOutputStream fout = null;
 		 ObjectOutputStream out = null;
		try {
 			fout = new FileOutputStream(filename);  //Connect to the filename
			out = new ObjectOutputStream(fout); // make an ObjectOutputStream chained to the connection stream
			out.writeObject(sprinklerGrpObj);// writes it to the object
			out.close(); 
 		}
		catch(IOException ex){ ex.printStackTrace();}		
	}
	
	/**
	 * This method is used to deserialize the Sprinklers
	 * @param filename
	 * @return
	 */
	public static SprinklerGroup deSerialize(String filename)
	{
		 SprinklerGroup sprinklerGrpObj = null; // declare a variable of the type SprinklerGroup
		 FileInputStream fis = null;
 		 ObjectInputStream fin = null;
		try {
 			fis = new FileInputStream(filename); //Make a fileInputStream object and connect to an existing file
			fin = new ObjectInputStream(fis);	//ObjectInputStream lets you read objects.
			sprinklerGrpObj = (SprinklerGroup)fin.readObject();// Gets the next object in the stream 			
			fin.close();										
 		}
		catch(IOException ex){ ex.printStackTrace();}
		catch(ClassNotFoundException e) {e.printStackTrace();}
		return sprinklerGrpObj;					// Returns an object
	}

	/**
	 * This method activates the sprinklers based on the location i.e. north,south,east,west
	 * @param location
	 */
	public void activateSprinklers(String location)
	{
		Sprinkler sprinkler;			
		List<Sprinkler> sprinklerList =sprinklerGroup.getSprinklersInGroup(); // Declare a sprinklerList of type List which adds the location of teh sprinklers selected
		Iterator<Sprinkler> i=sprinklerList.iterator(); // assign an iterator to this arrayList
		while(i.hasNext())  // While there are more elements in the arrayList
		{
			sprinkler=i.next();  // get the next element in the arrayList
			if(sprinkler.getGroup().equals(location)) // if the location matches with the sprinkler group
				sprinkler.startSprinkler();//start the sprinklers
		}
	}
}
