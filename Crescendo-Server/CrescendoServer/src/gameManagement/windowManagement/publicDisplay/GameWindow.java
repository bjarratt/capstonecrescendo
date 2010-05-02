package gameManagement.windowManagement.publicDisplay;

import gameManagement.messageTranslationSystem.Note;
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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
// TODO Make this window a little bit prettier
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
	
	public void setInitTime(int seconds)
	{
//		timer
	}
	
	public void setTime(int seconds)
	{
		timer.setTime(seconds);
	}
	
	public void decrementTime()
	{
		timer.decrement();
	}
	
	public void addNote(Note note)
	{
		if (note != null)
		{
			staff.addNote(note);
		}
	}
	
	public void addNotes(Collection<? extends Note> notes)
	{
		if (notes != null && !notes.isEmpty())
		{
			for (Note n : notes)
			{
				addNote(n);
			}
		}
	}
	
	public void setChords(List<String> changes)
	{
		staff.setKeyChanges(changes);
	}
	
	public void setKeySignature(String key)
	{
		staff.setKey(key);
	}
	
	public void setScore(String player, int score)
	{
		scores.setScore(player, score);
	}
	
	public void setScores(Map<String, Integer> scores)
	{
		if (scores != null && !scores.isEmpty())
		{
			Set<String> keys = scores.keySet();
			for (String key : keys)
			{
				setScore(key, scores.get(key));
			}
		}
	}
	
	public void setPlayerCount(int count)
	{
		scores.setPlayerCount(count);
	}
	
	public void getScore(String player)
	{
		scores.getScore(player);
	}
	
	public void setCurrentPlayer(String player)
	{
		staff.setPlayer(player);
	}
	
	public void reset()
	{
		timer.reset();
		staff.reset();
		scores.reset();
		validate();
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
					
					// Create the staff, add it to the window, and tell the staff to repaint itself
					Dimension staffDimension = new Dimension((int)(size.width*0.8), (int)(size.height*0.28));
					Point staffPoint = new Point((getWidth() - staffDimension.width)/2, (getHeight() - staffDimension.height)/2);
					staff.setBounds(staffPoint.x, staffPoint.y, staffDimension.width, staffDimension.height);
					add(staff);
					staff.validate();
					
					// Set up bounds for timer, add it to the window, and tell it to validate
					Dimension timerDimension = new Dimension((int)(size.width*0.125), (int)(size.height*0.11));
					Point timerPoint = new Point((getWidth() - timerDimension.width)/2, (int)(getHeight()*0.15));
					timer.setBounds(timerPoint.x, timerPoint.y, timerDimension.width, timerDimension.height);
					add(timer);
					timer.validate();
					
					// Set up bounds for timer, add it to the window, and tell it to validate
					Dimension sbDimension = new Dimension((int)(getWidth()*0.6), (int)(getHeight()*0.2));
					Point sbPoint = new Point((getWidth() - sbDimension.width)/2, (int)(getHeight()*0.7));
					scores.setBounds(sbPoint.x, sbPoint.y, sbDimension.width, sbDimension.height);
					add(scores);
					scores.validate();
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
		
//		window.setTime(12);
		window.setPlayerCount(4);
//		
//		for (int i = 0; i < 12; ++i)
//		{
//			try 
//			{
//				Thread.sleep(1000);
//				window.decrementTime();
//			} 
//			catch (InterruptedException e) 
//			{
//				e.printStackTrace();
//			}
//		}
	}
}
