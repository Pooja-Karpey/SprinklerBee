package view;

import model.ScheduleEntry;
import model.Schedule;
import controller.ScheduleTemp;
//import model.ScheduleEntry;
import controller.SprinklerTimer;
import javax.swing.*;

import controller.GardenController;
//import controller.SprinklerTimer;
import model.Sensor;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
//import java.awt.Component;
import java.util.ArrayList;
import java.util.Calendar;
//import java.util.Collections;
//import java.util.Date;
import java.util.List;

/**
 * This class is used to make the Tab panel.  
 * @author Vibha, Shwetha
 *
 */
public class TabPanel extends JPanel 
{
	JPanel panel_4,graphPanel4;
	JPanel displayFieldPanel;
	JPanel panelListBoxPanel_1;
	BarChart chart;
	private SprinklerCheckBox checkPanel_1, checkPanel_2, checkPanel_3,checkPanel_5,checkPanel_6;
	JButton startStopButtonPanel_1,startStopButtonPanel_2,startStopButtonPanel_3,overridePanel_3,getUsageButton,
			addEntryButtonPanel_1,addEntryButtonPanel_2,okButtonPanel_5,activateButtonPanel_6,
			viewGraphButton,stopButtonPanel_1,clearButton;
	JLabel waterUsageLabel,viewScheduleLabel,displayField;
	String startTime,selectedDayPanel_1,selectedDayPanel_2,selectedUpperLimit,selectedLowerLimit;
	int selectedTemp = 0;
	int selectedStartTime = 0;	int sprinklerDurationPanel_1 = 0;
	int sprinklerDurationPanel_2 = 0;
	int selectedUpperLimit_1 = 0;
	int selectedLowerLimit_1 = 0;
	List<ScheduleEntry> scheduleInput= new ArrayList<ScheduleEntry>();
	List<ScheduleEntry> deSerializedScheduleEntryList = new ArrayList<ScheduleEntry>();
	ScheduleEntry userScheduledEntryObject = new ScheduleEntry();
	ScheduleEntry deSerializedScheduleEntryObject = new ScheduleEntry();
	SprinklerTimer st;
	private Schedule scheduleSprinkler;
	private ScheduleTemp tempBasedSchedule;
	ScheduleTemp tempBasedSchedule_1 = new ScheduleTemp();
	public ArrayList<String> selectedSprinklerGroupList;
	String deSerializedDayOfWeek ;
	String deSerializedSelectedTime;
	int deSerializedDuration; 
	List<String> deSerializedGroup = new ArrayList<String>(); 
	CenterPanelLeft graphPanel;
	BarChart barChart;
	JTabbedPane tabbedPane;
	GardenController control=new GardenController();
	Sensor tempSensor = new Sensor();
	JButton enableButton;
	
	JComboBox daysList, timeList,durationList;
	/**
	 * This is a no argument constructor. 
	 */
	public TabPanel()
	{
		super(new GridLayout(1, 1));
		setPreferredSize(new Dimension(600,100));
		setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
		enableButton = new JButton("Enable");
		displayFieldPanel = new JPanel();
    	displayField = new JLabel(Calendar.getInstance().getTime().toString());
		displayFieldPanel.add(displayField);
		displayFieldPanel.add(enableButton);
		enableButton.addActionListener(new enableListener());
		enableButton.setActionCommand("enable");
		add(displayFieldPanel);
		
        tabbedPane = new JTabbedPane();
        checkPanel_1 = new SprinklerCheckBox();
        checkPanel_2 = new SprinklerCheckBox();
        checkPanel_3 = new SprinklerCheckBox();
        checkPanel_5 = new SprinklerCheckBox();
        checkPanel_6 = new SprinklerCheckBox();
        
        JComponent panel1 = makeTextPanel_1();
        tabbedPane.addTab("Time Based Pgm",panel1);
        JComponent panel2 = makeTextPanel_2();
        tabbedPane.addTab("Temp based Pgm",panel2);
        JComponent panel3 = makeTextPanel_3();
        tabbedPane.addTab("Override",panel3);
        JComponent panel4 = makeTextPanel_4();
        tabbedPane.addTab("Water Usage",panel4);
        JComponent panel5 = makeTextPanel_5();
        tabbedPane.addTab("View Schedule",panel5);
        JComponent panel6 = makeTextPanel_6();
        tabbedPane.addTab("Activate/Deactivate Sprinklers",panel6);
        add(tabbedPane,BorderLayout.CENTER);
        tabbedPane.setEnabled(false);
	}
	
	public void setSelectedList(ArrayList<String> sprinklerGroupList){
		this.selectedSprinklerGroupList=sprinklerGroupList;
	}
	/**
	 * This panel is used for the water usage panel. This attaches an event handler. 
	 * @return
	 */
	protected JComponent makeTextPanel_4(){
		panel_4 = new JPanel();
		JPanel buttongraphPanel4= new JPanel();
		
		barChart=new BarChart();
		getUsageButton =new JButton("Get Water Usage");
		getUsageButton.addActionListener(new WaterUsageListener());
		getUsageButton.setActionCommand("getusage");
		waterUsageLabel =new JLabel("             ");
		
		viewGraphButton=new JButton("View Graph");
		viewGraphButton.addActionListener(new GraphListener());
		viewGraphButton.setActionCommand("makegraph");
		
		buttongraphPanel4.add(waterUsageLabel);
		buttongraphPanel4.add(getUsageButton);
		buttongraphPanel4.add(viewGraphButton);
		panel_4.add(buttongraphPanel4);
		return panel_4;
	}
	
	/**
	 * This panel is used to view the Schedules entered by the user.
	 *  It displays the existing schedules if any when we click on the view button
	 * @return
	 */
	protected JComponent makeTextPanel_5(){
		JPanel panel_5 = new JPanel();
		panel_5.setLayout(new BoxLayout(panel_5,BoxLayout.Y_AXIS));
		
		JPanel buttons = new JPanel();	
		okButtonPanel_5 = new JButton("View Schedule");
	    okButtonPanel_5.setActionCommand("view");
	    okButtonPanel_5.addActionListener(new ViewScheduleListener()); 
	    viewScheduleLabel=new JLabel(" ");
	    buttons.add(viewScheduleLabel);
	    buttons.add(okButtonPanel_5);
	    panel_5.add(buttons);
		
		return panel_5;
	}
	/**
	 * This panel is used to activate the sprinklers by selecting the groups on clicking the activate button.
	 * 
	 * @return
	 */
	protected JComponent makeTextPanel_6(){
		JPanel panel_6 = new JPanel();
		panel_6.setLayout(new BoxLayout(panel_6,BoxLayout.Y_AXIS));
		
		JPanel buttons = new JPanel();
		activateButtonPanel_6 = new JButton("Activate");//toggle button
		activateButtonPanel_6.setActionCommand("activate");
		activateButtonPanel_6.addActionListener(new activateButtonListenerPanel_6());
		
		panel_6.add(checkPanel_6);
		buttons.add(activateButtonPanel_6);
		panel_6.add(buttons);
		return panel_6;
	}
	/**
	 * This panel adds an entry based on the time schedule and day of the week. 
	 * We can select the duration for which the sprinkler needs to be ON. 
	 * It calls a listener which activates the sprinkler based on the schedule entered. 
	 * @return
	 */
	
	protected JComponent makeTextPanel_1(){
        JPanel panel_1 = new JPanel();
        panel_1.setLayout(new BoxLayout(panel_1,BoxLayout.Y_AXIS));
            
        String[] days ={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        daysList = new JComboBox(days);
        daysList.setSelectedIndex(0); // Default selected value
        daysList.addActionListener(new DaysListener());
        daysList.setEnabled(false);
        
        String[] time ={"05:00:00","06:00:00","07:00:00"};
        timeList = new JComboBox(time);
        timeList.setSelectedIndex(0); // Default selected value
        timeList.addActionListener(new StartTimeListener());
        timeList.setEnabled(false);
        
        String[] duration = {"60","120"};
        durationList = new JComboBox(duration);
        durationList.setSelectedIndex(0); // Default selected value
        durationList.addActionListener(new DurationListener());
        durationList.setEnabled(false);
        
        addEntryButtonPanel_1 = new JButton("Add Entry");
        addEntryButtonPanel_1.setActionCommand("addEntry");
        addEntryButtonPanel_1.addActionListener(new AddEntryListenerPanel_1());
        addEntryButtonPanel_1.setEnabled(false);
        
        startStopButtonPanel_1 = new JButton("Start");
        startStopButtonPanel_1.setActionCommand("Start");
        startStopButtonPanel_1.addActionListener(new StartStopListenerPanel_1());
        startStopButtonPanel_1.setEnabled(false);
        
        stopButtonPanel_1 = new JButton("Stop");
        stopButtonPanel_1.setActionCommand("stop");
        stopButtonPanel_1.addActionListener(new StopListenerPanel_1());
        stopButtonPanel_1.setEnabled(false);
         
        JPanel panelListBoxPanel_1 = new JPanel();       
        panelListBoxPanel_1.setLayout(new FlowLayout());
        
        panelListBoxPanel_1.add(new Label("Days:"));
        panelListBoxPanel_1.add(daysList);
        panelListBoxPanel_1.add(new Label("Start Time:"));
        panelListBoxPanel_1.add(timeList);
        panelListBoxPanel_1.add(new Label("Duration:"));
        panelListBoxPanel_1.add(durationList);
        panelListBoxPanel_1.add(new Label("secs"));
        panel_1.add(panelListBoxPanel_1);
        checkPanel_1.setEnabled(false);
        panel_1.add(checkPanel_1);
        
        JPanel buttons = new JPanel();
        buttons.add(addEntryButtonPanel_1);
        buttons.add(startStopButtonPanel_1);
        buttons.add(stopButtonPanel_1);
        panel_1.add(buttons);
        return panel_1;
    }
	/**
	 * This tab panel is used to set the temperature limit based on the day of the week.
	 * When the temperature exceeds the limit set by the user. The sprinkler are activated for the groups selected.
	 * 
	 * @return
	 */
	
	protected JComponent makeTextPanel_2(){
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(new BoxLayout(panel_2,BoxLayout.Y_AXIS));
		
		String[] days ={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
	    JComboBox daysList = new JComboBox(days);
	    daysList.setSelectedIndex(0); // Default selected value
	    daysList.addActionListener(new DaysListener());
		
		String[] setTemp ={"55","65","85","80","90","95","100"};
        JComboBox setTempList = new JComboBox(setTemp);
        setTempList.setSelectedIndex(0);
        setTempList.addActionListener(new setTempListener());
        
        String[] duration = {"10","20","30","40"};
        JComboBox durationList = new JComboBox(duration);
        durationList.setSelectedIndex(0); // Default selected value
        durationList.addActionListener(new DurationListener());
        
       /* addEntryButtonPanel_2 = new JButton("Add Entry");
        addEntryButtonPanel_2.setActionCommand("addEntry");
        addEntryButtonPanel_2.addActionListener(new AddEntryListenerPanel_2());*/
        
		startStopButtonPanel_2 = new JButton("Start"); //make it a toggle button
		startStopButtonPanel_2.setActionCommand("start");
		startStopButtonPanel_2.addActionListener(new StartStopListenerPanel_2());
               
        JPanel panelListBoxPanel_2 = new JPanel();
        panelListBoxPanel_2.setLayout(new FlowLayout());
        panelListBoxPanel_2.add(new Label("Days:"));
        panelListBoxPanel_2.add(daysList);
        panelListBoxPanel_2.add(new Label("Set Temp:"));
        panelListBoxPanel_2.add(setTempList);
        panelListBoxPanel_2.add(new Label("Duration:"));
        panelListBoxPanel_2.add(durationList);
        panelListBoxPanel_2.add(new Label("secs"));
        panel_2.add(panelListBoxPanel_2);
        panel_2.add(checkPanel_2); 
        
		JPanel buttons = new JPanel();
        //buttons.add(addEntryButtonPanel_2);
        buttons.add(startStopButtonPanel_2);
        panel_2.add(buttons);
		return panel_2;
	}
	
	/**
	 * This panel is used for the Override function. It gives the option to override any existing entry if the
	 * temperature falls above or below the mentioned limit. 
	 * @return
	 */
	protected JComponent makeTextPanel_3(){
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(new BoxLayout(panel_3,BoxLayout.Y_AXIS));
		
		String[] upperLimit ={"95","100","105","110","115"};
        JComboBox upperLimitList = new JComboBox(upperLimit);
        upperLimitList.setSelectedIndex(0);
        upperLimitList.addActionListener(new upperLimitListener());
        
        String[] lowerLimit ={"40","45","50","55"};
        JComboBox lowerLimitList = new JComboBox(lowerLimit);
        lowerLimitList.setSelectedIndex(0);
        lowerLimitList.addActionListener(new lowerLimitListener());
        
        JPanel panelUpperListBoxPanel_3 = new JPanel();
        panelUpperListBoxPanel_3.setLayout(new FlowLayout());
        panelUpperListBoxPanel_3.add(new Label(" Set UpperLimit:"));
        panelUpperListBoxPanel_3.add(upperLimitList);
        
        JPanel panelLowerListBoxPanel_3 = new JPanel();
        panelLowerListBoxPanel_3.setLayout(new FlowLayout());
        panelLowerListBoxPanel_3.add(new Label(" Set LowerLimit:"));
        panelLowerListBoxPanel_3.add(lowerLimitList);
        
        JPanel buttons = new JPanel();
        overridePanel_3 = new JButton("Override"); //make it a toggle button
        overridePanel_3.setActionCommand("Override");
        overridePanel_3.addActionListener(new overrideListenerPanel_3());
        buttons.add(overridePanel_3);
        
        panel_3.add(panelUpperListBoxPanel_3);
        panel_3.add(panelLowerListBoxPanel_3);
        panel_3.add(checkPanel_3);
        panel_3.add(buttons);
        return panel_3;
	}
	
	/**
	 * This class is used to enable or disable the panels. 
	 * @author Vibha, Shwetha
	 *
	 */
	class enableListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if("enable".equals(e.getActionCommand())){
				if(enableButton.getText()=="Enable"){
					enableButton.setText("Disable");
					daysList.setEnabled(true);
					checkPanel_1.setEnabled(true);
					durationList.setEnabled(true);
					timeList.setEnabled(true);
					addEntryButtonPanel_1.setEnabled(true);
					startStopButtonPanel_1.setEnabled(true);
					stopButtonPanel_1.setEnabled(true);
					tabbedPane.setEnabled(true);
				}
				else if(enableButton.getText()=="Disable"){
					enableButton.setText("Enable");
					daysList.setEnabled(false);
					checkPanel_1.setEnabled(false);
					durationList.setEnabled(false);
					timeList.setEnabled(false);
					addEntryButtonPanel_1.setEnabled(false);
					startStopButtonPanel_1.setEnabled(false);
					stopButtonPanel_1.setEnabled(false);
					tabbedPane.setEnabled(false);
				}
				
			}
		}
	}
	
	/**
	 * This tab panel has the Disable button, which is used to disable all the panels. 
	 * @author Vibha, Shwetha
	 *
	 */
	class disableListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if("disable".equals(e.getActionCommand())){
				System.out.println("Inside disable");
				tabbedPane.removeAll();
			}
		}
	}
	/** 
	 * This class is used to execute the stop button. It stops the running timers. 
	 * @author Vibha, Shwetha
	 *
	 */
	
	class StopListenerPanel_1 implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if("stop".equals(e.getActionCommand())){
				st.stopRunningTimer();
			}
		}
	}
	/**
	 * This class is used to check if the override button is pressed. On pressing the button
	 *  the lower limit and the upper limit values are passed to the OverrideSprinkler. 
	 * 
	 * @author Vibha, Shwetha
	 *
	 */
	class overrideListenerPanel_3 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if("Override".equals(e.getActionCommand())){
					//startStopButtonPanel_3.setText("Stop");
					tempBasedSchedule_1.OverrideSprinkler(selectedLowerLimit_1,selectedUpperLimit_1);
				}
			}
		}
	/**
	 * This class is used to record the lower limit set by the user. 
	 * @author Vibha, Shwetha
	 *
	 */
		
	class lowerLimitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JComboBox cb = (JComboBox)e.getSource();
			selectedLowerLimit = (String)cb.getSelectedItem();
			selectedLowerLimit_1 = Integer.parseInt(selectedLowerLimit);
		}
	}
	/**
	 * This class is used to record the upper limit set by the user. 
	 * @author Vibha, Shwetha
	 *
	 */
	
	class upperLimitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JComboBox cb = (JComboBox)e.getSource();
			selectedUpperLimit = (String)cb.getSelectedItem();
			selectedUpperLimit_1 = Integer.parseInt(selectedUpperLimit);
		}
	}
	/**
	 * This class is used to get the day selected by the user. It is an action listener
	 * @author Vibha, Shwetha
	 *
	 */
	
	class DaysListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JComboBox cb = (JComboBox)e.getSource();
			selectedDayPanel_1 = (String)cb.getSelectedItem();
		}
	}
		
	/*class AddEntryListenerPanel_2 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if("addEntry".equals(e.getActionCommand()))
			{	
				//scheduleInput = new ScheduleEntry(selectedDayPanel_1,selectedTemp,sprinklerDurationPanel_1,checkPanel_2.sprinklerGroupList);
			}
		}
	}*/
	
	/**
	 * This class, an action listener, is used to get the total water usage in gallons. 
	 * @author Vibha, Shwetha
	 *
	 */
	
	class WaterUsageListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if("getusage".equals(e.getActionCommand()))
			{
				if(getUsageButton.getText()=="Get Water Usage")
				{
					System.out.println("in wateruse action lis");

					Integer text=GardenController.sprinklerGroup.getTotalWaterUsage();
					waterUsageLabel.setText(text.toString()+" gallons");
				}	
			}
			
		}
	}
	
	/**
	 * This class which is an action listener is used to send the water usage values for the make graph for each sprinkler group and then display it. 
	 * @author Vibha, Shwetha
	 *
	 */
	class GraphListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if("makegraph".equals(e.getActionCommand()))
			{
				System.out.println("in view graph action lis");
				Map<String,Integer> map=GardenController.sprinklerGroup.getGroupWaterUsage();
				panel_4.add(barChart.makeGraph(map.get("north"),map.get("east"),map.get("west"),map.get("south")));
				barChart.repaint();
				repaint();		
			}
		}	
	}
	/**
	 * This class is an action listener used to display the schedule if any entered by the user. 
	 * This is executed on the press of the View Schedule button. 
	 * @author Vibha, Shwetha
	 *
	 */
	class ViewScheduleListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if("view".equals(e.getActionCommand()))
			{
				if(okButtonPanel_5.getText().equals("View Schedule"))
				{
					StringBuilder text=new StringBuilder(" ");
					//String text=" ";
					List<ScheduleEntry> list;
					list=Schedule.deSerializeScheduleEntry("entryobject.ser");
					Iterator<ScheduleEntry>	i=list.iterator();
					displayField.setText(" ");
					while(i.hasNext())
					{
						ScheduleEntry se=i.next();
						text.append(se.getTimeBasedDayOfWeek()+" "+se.getTimebasedSelectedTime()+" "+se.getTimeBasedDuration()+" ");
					}
					displayField.setText(text.toString());
					okButtonPanel_5.setText("Clear");
				}
				else if(okButtonPanel_5.getText().equals("Clear"))
				{
					 okButtonPanel_5.setText("View Schedule");
					 displayField.setText(Calendar.getInstance().getTime().toString());
				}
			}
		}
	}
	/**
	 * This class is an action listener which is called when the Add entry button is clicked.
	 * It saves the input values as an entry into a .ser file after serializing it. 
	 * @author Vibha, Shwetha
	 *
	 */
	class AddEntryListenerPanel_1 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if("addEntry".equals(e.getActionCommand()))
			{	
				System.out.println("action listener grp is "+checkPanel_1.sprinklerGroupList);
				userScheduledEntryObject = new ScheduleEntry(selectedDayPanel_1,startTime,sprinklerDurationPanel_1,checkPanel_1.sprinklerGroupList);
				scheduleInput.add(userScheduledEntryObject);
				Schedule.serializeScheduleEntryList(scheduleInput,"entryobject.ser");
				
			}
		}
	}
	
	/**
	 * This class is an action listener which on the press of the start button calls the scheduleTemp function with the values
	 * set by the user.  
	 * @author Vibha, Shwetha
	 *
	 */
	
	class StartStopListenerPanel_2 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if("start".equals(e.getActionCommand()))
			{
				tempBasedSchedule = new ScheduleTemp(selectedDayPanel_1,selectedTemp,sprinklerDurationPanel_1,checkPanel_2.sprinklerGroupList);
				
			}
		}
	}
	
	/**
	 * This class is an action listener which on the press of a start button de serializes the 
	 * entries and checks if any entry matches the day of the week got from system time. 
	 * If an entry matches the SprinklerTimer class is called. 
	 * @author Vibha, Shwetha
	 *
	 */
	class StartStopListenerPanel_1 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if("Start".equals(e.getActionCommand()))
			{
				if(startStopButtonPanel_1.getText()=="Start")
				{	
					deSerializedScheduleEntryList=Schedule.deSerializeScheduleEntry("entryobject.ser");	
					int listSize = deSerializedScheduleEntryList.size();	
					int i=0,selectedNumberWeekday = 0;
					System.out.println("list size = "+listSize);
					while(listSize != i)
					{
						deSerializedScheduleEntryObject = deSerializedScheduleEntryList.get(i);
						deSerializedDayOfWeek = deSerializedScheduleEntryObject.getTimeBasedDayOfWeek();
						System.out.println("deserialized week of day"+deSerializedDayOfWeek);
						deSerializedSelectedTime = deSerializedScheduleEntryObject.getTimebasedSelectedTime();
						deSerializedDuration = deSerializedScheduleEntryObject.getTimeBasedDuration();
						deSerializedGroup = deSerializedScheduleEntryObject.getTimeBasedGroup();	
						if (deSerializedDayOfWeek.contains("Sunday"))
							selectedNumberWeekday = 1;
						else if(deSerializedDayOfWeek.contains("Monday"))
							selectedNumberWeekday = 2;
						else if(deSerializedDayOfWeek.contains("Tuesday"))
							selectedNumberWeekday = 3;
						else if(deSerializedDayOfWeek.contains("Wednesday"))
							selectedNumberWeekday = 4;
						else if(deSerializedDayOfWeek.contains("Thursday"))
							selectedNumberWeekday = 5;
						else if(deSerializedDayOfWeek.contains("Friday"))
							selectedNumberWeekday = 6;
						else if(deSerializedDayOfWeek.contains("Saturday"))
							selectedNumberWeekday = 7;
						
						Calendar cal = Calendar.getInstance();
						if(cal.get(Calendar.DAY_OF_WEEK)==selectedNumberWeekday)
						{
							System.out.println("SprinklerTimer Sunday:"+i);
							st = new SprinklerTimer(deSerializedScheduleEntryList);//take only this out and check.
						}
						i++;		
					}	
				}
			}
			else if(startStopButtonPanel_1.getText()=="Stop")
			{
				startStopButtonPanel_1.setText("Start");
			}
		}
	}
	/**
	 * This class is an action listener which activates or deactivates the selected sprinkler on pressing the button.
	 * @author Vibha, Shwetha
	 *
	 */
	
	class activateButtonListenerPanel_6 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if("activate".equals(e.getActionCommand()))
			{
				if(activateButtonPanel_6.getText()=="Activate")
				{
					activateButtonPanel_6.setText("Deactivate");
					control.activateSprinklers(checkPanel_6.sprinklerGroupList);
					System.out.println("Activate button clicked");
				}
				else if(activateButtonPanel_6.getText()=="Deactivate")
				{
					activateButtonPanel_6.setText("Activate");
					control.deactivateSprinklers(checkPanel_6.sprinklerGroupList);
				}
			}
		}
	}
	/**
	 * This class is an action listener which is used to get the time entered or selected by the user. 
	 * @author Vibha, Shwetha
	 *
	 */
	class StartTimeListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JComboBox cb = (JComboBox)e.getSource();
			startTime = (String)cb.getSelectedItem();
		}
	}
	/**
	 * This class is an action listener which is used to get the value of the duration set. 
	 * @author Vibha, Shwetha
	 *
	 */
	class DurationListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JComboBox cb = (JComboBox)e.getSource();
			String duration = (String)cb.getSelectedItem();
			sprinklerDurationPanel_1 = Integer.parseInt(duration);
		}
	}
	/**
	 * This class is an action listener which is used to get the values of the temperature value set. 
	 * @author Vibha, Shwetha
	 *
	 */
	class setTempListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JComboBox cb = (JComboBox)e.getSource();
			String temp = (String)cb.getSelectedItem();
			selectedTemp = Integer.parseInt(temp);
		}
	}
}


