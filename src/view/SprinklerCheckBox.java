package view;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.Component;
import java.util.ArrayList;

/**
 * This class adds 4 check boxes and listeners to each checkbox
 * @author Vibha
 *
 */
class SprinklerCheckBox extends JPanel implements ItemListener
{
	JCheckBox northGroup;
	JCheckBox southGroup;
	JCheckBox eastGroup;
	JCheckBox westGroup;
	JPanel checkPanel;
	String sprinklerThatIsSet;
	ArrayList<String> sprinklerGroupList;
	TabPanel tabCheckBox;
	
	/**
	 * this is a noargument constructor
	 */
	public SprinklerCheckBox()
	{
		//creating 4 checkboxes
		northGroup = new JCheckBox("North Group");
        southGroup = new JCheckBox("South Group");
        eastGroup = new JCheckBox("East Group");
        westGroup = new JCheckBox("West Group");
        
       //Register a listener  for check boxes
        northGroup.addItemListener(this);
        southGroup.addItemListener(this);
        eastGroup.addItemListener(this);
        westGroup.addItemListener(this);
        
        //add the checkboxes to the panel in a gridlayout
        checkPanel = new JPanel(new GridLayout(0, 1));
        add(northGroup);
        add(southGroup);
        add(eastGroup);
        add(westGroup);
        add(checkPanel);
	}
	
	/**
	 * This method adds the sprinkler checkboxes when clicked to an arrayList
	 */
	public void itemStateChanged(ItemEvent e)
	{
		sprinklerGroupList= new ArrayList<String>();
		//adds the group to the arraylist that is being selected in the checkbox
		if (northGroup.isSelected())
		{
			sprinklerGroupList.add("north");
		}
		else{
			sprinklerGroupList.remove("north");	
		}
		
		if(southGroup.isSelected()){
			sprinklerGroupList.add("south");
		}
		else{
			sprinklerGroupList.remove("south");	
		}
		
		if(eastGroup.isSelected()){
			sprinklerGroupList.add("east");
		}
		else{
			sprinklerGroupList.remove("east");	
		}
		
		if(westGroup.isSelected()){
			sprinklerGroupList.add("west");
		}
		else{
			sprinklerGroupList.remove("west");	
		}
	}
}
