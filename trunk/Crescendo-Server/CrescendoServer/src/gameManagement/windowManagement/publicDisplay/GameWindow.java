package gameManagement.windowManagement.publicDisplay;

import gameManagement.windowManagement.publicDisplay.gameWindow.GameTimer;
import gameManagement.windowManagement.publicDisplay.gameWindow.ScoreBoard;
import gameManagement.windowManagement.publicDisplay.staff.AnimatedStaff;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 * This class encapsulates the primary screen for gameplay.  It displays the following information
 * <ul>
 *     <li>Staff with played notes, key signature, chords, and time</li>
 *     <li>Each active player's scores</li>
 *     <li>Timer for the game</li>
 * </ul>
 * 
 * @author Zach
 */
public class GameWindow extends JPanel 
{
	/**
	 * Make a game window using default member values
	 */
	public GameWindow() 
	{
		staff = new AnimatedStaff();
		initComponents();
	}
	
	/**
	 * Make a new game window set to the given parameters
	 * @param player - starting player
	 * @param key - starting key
	 * @param chords - a list of chords to appear over an equal number of measures
	 * @param beats - how many down-beats in a measure
	 * @param subdivision - which note gets the down beat
	 */
	public GameWindow(String player, String key, List<String> chords, int beats, int subdivision)
	{
		staff = new AnimatedStaff(player, key, chords, beats, subdivision);
		initComponents();
	}
	
	public void setTime(int seconds)
	{
		timer.setTime(seconds);
	}
	
	public void decrementTime()
	{
		timer.decrement();
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		// just draw the background image
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(background, 0, 0, getWidth(), getHeight(), null);
	}
	
	// set up to where it is only called once from paintComponent
	private void initComponents()
	{
		this.addAncestorListener(new AncestorListener() {
			
			@Override
			public void ancestorRemoved(AncestorEvent event) {}
			
			@Override
			public void ancestorMoved(AncestorEvent event) {}
			
			@Override
			public void ancestorAdded(AncestorEvent event) 
			{
				// I created a component listener because this code needs to run after the 
				// window becomes visible but before the drawing thread starts (i.e. the thread that calls
				// paintComponent().  
				try 
				{
					// Do some setup for the window
					background = ImageIO.read(new File("blackground.jpg"));
					setLayout(null);
					Dimension size = getSize();
					
					// Calculate size and placement for staff
					staffDimension.setSize((int)(size.width*0.8), (int)(size.height*0.28));
					staffPoint.setLocation((getWidth() - staffDimension.width)/2, (getHeight() - staffDimension.height)/2);
					
					// Create the staff, add it to the window, and tell the staff to repaint itself
					staff.setBounds(staffPoint.x, staffPoint.y, staffDimension.width, staffDimension.height);
					add(staff);
					staff.validate();
					
					Dimension timerDimension = new Dimension((int)(size.width*0.125), (int)(size.height*0.11));
					Point timerPoint = new Point((getWidth() - timerDimension.width)/2, (int)(getHeight()*0.15));
					timer.setBounds(timerPoint.x, timerPoint.y, timerDimension.width, timerDimension.height);
					add(timer);
					timer.validate();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	private AnimatedStaff staff = null;
	private BufferedImage background = null;
	private GameTimer timer = new GameTimer();
	private ScoreBoard scores = new ScoreBoard();
	private Point staffPoint = new Point();
	private Dimension staffDimension = new Dimension();
	
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		
		frame.setLayout(new GridLayout(1,1));
		GameWindow window = new GameWindow();
		frame.getContentPane().add(window);
		
		frame.setVisible(true);
		
		for (int i = 0; i < 60; ++i)
		{
			try 
			{
				Thread.sleep(1000);
				window.decrementTime();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
