package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * The class Schedule is used to serialize and deserialize the entries that the user has made(ScheduleEntry Object) for a day or for
 * the entire week. The user can have more than one schedule for the sprinkler in a day
 * @author Vibha, Shwetha
 *
 */
@SuppressWarnings("serial")
public class Schedule implements Serializable
{	
	static List<ScheduleEntry> scheduleList = new ArrayList<ScheduleEntry>();//Create an ArrayList of ScheduleEntry objects
	
	/**
	 * This method is used to add the ScheduleEntry objects to an ArrayList scheduleList
	 * @param scheduleEntryObject
	 */
	public void addScheduleEntry(ScheduleEntry scheduleEntryObject){
		this.scheduleList.add(scheduleEntryObject);
	}
	
	/**
	 * This method is used  to return the scheduleEntry Objects from the ArrayList 
	 * @return List
	 */
	public List<ScheduleEntry> getScheduleEntryObjects(){
		return this.scheduleList;
	}
	
	/**
	 * This method is used to serialize the ScheduleEntry object list 
	 * @param scheduleList
	 * @param filename
	 */
	public static void serializeScheduleEntryList(List<ScheduleEntry> scheduleList, String filename){
		FileOutputStream fout = null;
		 ObjectOutputStream out = null;
		 try {
	 			fout = new FileOutputStream(filename);  //Connect to the filename
				out = new ObjectOutputStream(fout);// make an ObjectOutputStream chained to the connection stream
				out.writeObject(scheduleList); // writes it to the object
				out.close();
	 		}
			catch(IOException ex)
			{ 
				ex.printStackTrace();
			}	
	}
	
	/**
	 * This method is used to deserialize schedule entry object list that were selected by the user
	 * @param filename
	 * @return List<ScheduleEntry>
	 */
	@SuppressWarnings("unchecked")
	public static List<ScheduleEntry> deSerializeScheduleEntry(String filename)
	{
	 List<ScheduleEntry> scheduleList = null;// declare a variable of type List
	 FileInputStream fis = null;
	 ObjectInputStream fin = null;
	 try {
 			fis = new FileInputStream(filename);  //Make a fileInputStream object and connect to an existing file
			fin = new ObjectInputStream(fis);		//ObjectInputStream lets you read objects.
			scheduleList = (List<ScheduleEntry>)fin.readObject(); // Gets the next object in the stream 
			fin.close();
 		}
	 catch(IOException ex)
	 {
		 ex.printStackTrace();
	 }
	 catch(ClassNotFoundException e) 
	 {
		e.printStackTrace();
	 }
	return scheduleList; //returns a list of schedule entry objects
	}
}
	
