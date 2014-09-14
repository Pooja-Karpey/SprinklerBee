package view;

import java.util.LinkedHashMap;
import java.util.Map;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.Graphics;
import java.awt.geom.Line2D;



import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * This class draws the bar graph for the water consumed by the sprinklers
 * @author Vibha,shwetha
 *
 */
public class BarChart extends JPanel{
	private JPanel graphPanel;
	private Map<Color, Integer> bars = new LinkedHashMap<Color, Integer>();
	final int PAD = 20;
	/**
	 * this is a constructor of the barGraph which sets the size 
	 */
	public BarChart()
	{
		setPreferredSize(new Dimension(500,500));
	}

	/**
	 * This method is used to color the bars
	 * @param color
	 * @param value
	 */
	public void addBar(Color color, int value)
	{
		bars.put(color, value);
		repaint();
	}
	
	/**
	 * This method is used to draw the graph based on the values of the 4 sprinkler groups
	 * @param val1
	 * @param val2
	 * @param val3
	 * @param val4
	 * @return JPanel
	 */
	public JPanel makeGraph(int val1,int val2,int val3,int val4)
	{
		graphPanel=new JPanel();
		graphPanel.setLayout(new BoxLayout(graphPanel,BoxLayout.X_AXIS));//setting the layout of the panel
		graphPanel.setPreferredSize(new Dimension(500,200));//setting the size
		graphPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));//setting a border
		
		BarChart chart = new BarChart();
		//calling the addBar method on the chart object and setting the color and value for each group
		chart.addBar(Color.blue.darker(), val1);
		chart.addBar(Color.cyan.darker(),val2);
		chart.addBar(Color.BLUE, val3);
		chart.addBar(Color.CYAN,val4);
		graphPanel.add(chart);
		return graphPanel;
	}
	
	/**
	 * This method returns the graphPanel
	 * @return JPanel
	 */
	public JPanel getGraphPanel()
	{
		return this.graphPanel;
	}
	
	/**
	 * This is an overridden method where we are setting the X and Y axis and the font, width, height and other things
	 * necessary to draw a graph
	 */
	@Override
	protected void paintComponent(Graphics gp)
	{
		super.paintComponent(gp);
		Graphics2D g = (Graphics2D) gp; 
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g.setStroke(new BasicStroke(3.0f));
		//xaxis corodinates
		double startX = 50;
		double startY = 300;
		double endX  = 300;
		double endY = 300;
		//set the color to black
		g.setPaint(Color.black);
		Font newFont1 = new Font("Courier",Font.BOLD,15);         
		g.setFont(newFont1);
		//draw the x axis name
		g.drawString( "Gallons", (int)30, (int)30);
		Line2D line2 = new Line2D.Double( 50,300,50,50);  //drawing y axis name  
		g.draw(line2);//drawing the line
		int max = Integer.MIN_VALUE;
		for (Integer value : bars.values())//setting the max value for the bar
		{
			max = Math.max(max, value);
		}
		int width = 30;
		int x=52;
		for(Color color:bars.keySet())
		{
			Integer value = bars.get(color);
			//setting the height of bar
			int height = (int)((getHeight()-100)* ((double)value)/max);
			gp.setColor(color);
			g.drawString(value.toString(),width,getHeight() - height);
			//filling the rectangle
			gp.fillRect(x, getHeight() - height, width, height);
			//drawing the bar
			gp.drawRect(x, getHeight() - height, width, height);
			//setting the width of eah bar
			x += (width + 2); 				
			g.setColor(color); 
			g.fillRect(150, 200, 50, 50);
			g.drawRect(150, 200, 30, 30);
		}
		g.setPaint(Color.black); //drawing the line      
	    Line2D line = new Line2D.Double(startX,startY,endX,endY); 
	    g.draw(line);
	    g.setPaint(Color.black);  
	    Font newFont = new Font("Courier",Font.BOLD,15);         
	    g.setFont(newFont);
	    g.drawString("Groups",300,180);	          
	}
	
	@Override
	public Dimension getPreferredSize() {
		System.out.println("inside dimension getPreferredSize!!!!!!");
		return new Dimension(bars.size() * 10 + 2, 50);
	}
}
