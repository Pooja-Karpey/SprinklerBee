package view;
import controller.*;

import javax.swing.*;
import java.util.*;
import java.util.List;

import model.Sprinkler;
import model.SprinklerGroup;

import java.awt.*;

/**
 * This GUI class comprises of the right panel of the main GUI class. 
 * This class simulates the UI for the user that displays the sprinklers and its activation status.
 * @author Vibha, Shwetha
 *
 */

public class CenterPanelRight extends JPanel implements Observer {
	
	SprinklerGroup sprinklerGroup=null;
	ImageIcon icon;
	static SprinklerGroup northGroup,eastGroup,southGroup,westGroup;
	ImageIcon activeIcon=createImageIcon("active.png","Active Sprinkler");
	ImageIcon inactiveIcon=createImageIcon("inactive.png", "Sleepy sprinkler");
	static List<JLabel> northLabels = new ArrayList<JLabel>();
	static List<JLabel> eastLabels = new ArrayList<JLabel>();
	static List<JLabel> westLabels = new ArrayList<JLabel>();
	static List<JLabel> southLabels = new ArrayList<JLabel>();
	GardenController gc;
	boolean updated=false;
	//static boolean showStatus=false;
	
	/**
	 * This is a no argument constructor. 
	 */
	public CenterPanelRight()
	{
		this.setLayout(new BorderLayout());
		this.add(getNorthPanel(),BorderLayout.NORTH);
		this.add(getSouthPanel(),BorderLayout.SOUTH);
		this.add(getEastPanel(),BorderLayout.EAST);
		this.add(getWestPanel(),BorderLayout.WEST);
		//JLabel label=new JLabel(createImageIcon("garden1.jpg", "My green garden"));
		//this.add(label,BorderLayout.CENTER);
	}
	/**
	 * This is a constructor which adds the observers to the sprinklers
	 * @param sprinklerGroupToWatch
	 */
	public CenterPanelRight(SprinklerGroup sprinklerGroupToWatch)
	{
		this.sprinklerGroup=sprinklerGroupToWatch;
		gc=new GardenController(this.sprinklerGroup);
		List<Sprinkler> sprinklerList=sprinklerGroupToWatch.getSprinklersInGroup();
		Iterator<Sprinkler> i=sprinklerList.iterator();
		while(i.hasNext())
		{
			i.next().addObserver(this);
		}
	}
	/**
	 * When observable changes this method is called.
	 */

	@Override
	public void update(Observable o, Object obj)
	{
		updated=true;
		myPainter();
	}

/**
 * This method is used to get the North group sprinklers after deserializing and place an active or inactive icon based on the
 * status of the sprinklers.	
 * @return
 */
	public JPanel getNorthPanel()
	{
		Sprinkler sprinkler=null;
		ImageIcon icon=null;
		northGroup=ConfigureSprinkler.deSerialize("northgroup.ser");
		if(northGroup.getGroupStatus()) 
			icon=activeIcon;
		else
			icon=inactiveIcon;
		JPanel northPanel = new JPanel();
		GridLayout grid = new GridLayout(3,3);
		grid.setVgap(2);
		grid.setHgap(2);
		northPanel.setLayout(grid);
		Iterator<Sprinkler> itr=northGroup.getSprinklersInGroup().iterator();
		/*Add an iterator to the sprinkler list. */
		while(itr.hasNext())
		{
			sprinkler=itr.next();
			northPanel.add(makeNewLabel(sprinkler.getId()+" "+sprinkler.getFunctionalStatus(),icon)); //Display the status of the sprinkler. 
		}
		northPanel.setName("north");
		return northPanel;
	}
	/**
	 * This method is used to get the South group sprinklers after deserializing and place an active or inactive icon based on the
	 * status of the sprinklers.	
	 * @return
	 */
	public JPanel getSouthPanel()
	{
		Sprinkler sprinkler=null;
		ImageIcon icon=null;
		southGroup=ConfigureSprinkler.deSerialize("southgroup.ser");
		if(southGroup.getGroupStatus()) 
			icon=activeIcon;
		else
			icon=inactiveIcon;
		JPanel southPanel = new JPanel();
		GridLayout grid = new GridLayout(3,3);
		grid.setVgap(2);
		grid.setHgap(2);
		southPanel.setLayout(grid);
		/*Add an iterator to the sprinkler list. */
		Iterator<Sprinkler> itr=southGroup.getSprinklersInGroup().iterator();
		while(itr.hasNext())
		{
			sprinkler=itr.next();
			southPanel.add(makeNewLabel(sprinkler.getId()+" "+sprinkler.getFunctionalStatus(),icon));//Display the status of the sprinkler. 
		}
		southPanel.setName("south");
		return southPanel;
	}
	/**
	 * This method is used to get the East group sprinklers after deserializing and place an active or inactive icon based on the
	 * status of the sprinklers.	
	 * @return
	 */
	public JPanel getEastPanel()
	{
		Sprinkler sprinkler=null;
		ImageIcon icon=null;
		eastGroup=ConfigureSprinkler.deSerialize("eastgroup.ser");
		if(eastGroup.getGroupStatus()) 
			icon=activeIcon;
		else
			icon=inactiveIcon;
		JPanel eastPanel = new JPanel();
		GridLayout grid = new GridLayout(3,3);
		grid.setVgap(2);
		grid.setHgap(2);
		eastPanel.setLayout(grid);
		/*Add an iterator to the sprinkler list. */
		Iterator<Sprinkler> itr=eastGroup.getSprinklersInGroup().iterator();
		while(itr.hasNext())
		{
			sprinkler=itr.next();
			eastPanel.add(makeNewLabel(sprinkler.getId()+" "+sprinkler.getFunctionalStatus(),icon));//Display the status of the sprinkler. 
		}
		eastPanel.setName("east");
		return eastPanel;
	}
	/**
	 * This method is used to get the West group sprinklers after deserializing and place an active or inactive icon based on the
	 * status of the sprinklers.	
	 * @return
	 */
	public JPanel getWestPanel()
	{
		Sprinkler sprinkler=null;
		ImageIcon icon=null;
		westGroup=ConfigureSprinkler.deSerialize("westgroup.ser");
		if(westGroup.getGroupStatus()) 
			icon=activeIcon;
		else
			icon=inactiveIcon;
		JPanel westPanel = new JPanel();
		GridLayout grid = new GridLayout(3,3);
		grid.setVgap(2);
		grid.setHgap(2);
		westPanel.setLayout(grid);
		/*Add an iterator to the sprinkler list. */
		Iterator<Sprinkler> itr=westGroup.getSprinklersInGroup().iterator();
		while(itr.hasNext())
		{
			sprinkler=itr.next();
			westPanel.add(makeNewLabel(sprinkler.getId()+" "+sprinkler.getFunctionalStatus(),icon));//Display the status of the sprinkler. 
		}
		westPanel.setName("west");
		return westPanel;
	}
	
	  /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path,
                                               String description) 
    {
        java.net.URL imgURL = CenterPanelRight.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    /**
     * Sets the labels according to the sprinkler groups. 
     * @param text
     * @param icon
     * @return JLabel
     */
    
    JLabel makeNewLabel(String text, ImageIcon icon)
    {
    	JLabel label =new JLabel(text,icon,JLabel.CENTER);
    	//JLabel label =new JLabel(text,JLabel.CENTER);
    	if (label.getText().contains("N"))
    		northLabels.add(label);
    	else if (label.getText().contains("E"))
    		eastLabels.add(label);
    	else if (label.getText().contains("W"))
    		westLabels.add(label);
    	else if (label.getText().contains("S"))
    		southLabels.add(label);
    	
    	return label;
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
    	if(updated) myPainter();
    }
    /**
     * This method gets the sprinkler info and sets the image based on the status of the sprinklers.  
     */
    
    private void myPainter()
    {
    	ImageIcon icon=inactiveIcon;
		JLabel label;
		Sprinkler sprinkler=null;
		List<Sprinkler> sprinklerList=this.sprinklerGroup.getSprinklersInGroup();
		Iterator<Sprinkler> i=sprinklerList.iterator();
		//System.out.println("in cpr "+this.sprinklerGroup); vibha commented
		while(i.hasNext())
		{
			sprinkler=i.next();
			if(sprinkler.getGroup().equals("east"))
			{
				icon=(sprinkler.isCurrentStatus()?activeIcon:inactiveIcon);
				Iterator <JLabel> iLabel=eastLabels.iterator();
				while(iLabel.hasNext())
				{
					label=iLabel.next();
					label.setIcon(icon);
				}
			}
			 if(sprinkler.getGroup().equals("north"))
			{
				icon=(sprinkler.isCurrentStatus()?activeIcon:inactiveIcon);
				Iterator <JLabel> iLabel=northLabels.iterator();
				while(iLabel.hasNext())
				{
					label=iLabel.next();
					label.setIcon(icon);
				}
			}
			 if(sprinkler.getGroup().equals("west"))
			{
				icon=(sprinkler.isCurrentStatus()?activeIcon:inactiveIcon);
				Iterator <JLabel> iLabel=westLabels.iterator();
				while(iLabel.hasNext())
				{
					label=iLabel.next();
					label.setIcon(icon);
				}
			}
			 if(sprinkler.getGroup().equals("south"))
			{
				icon=(sprinkler.isCurrentStatus()?activeIcon:inactiveIcon);
				Iterator <JLabel> iLabel=southLabels.iterator();
				while(iLabel.hasNext())
				{
					label=iLabel.next();
					label.setIcon(icon);
				}
			}
		}
    }
}
