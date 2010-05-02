package gameManagement.windowManagement.publicDisplay;

import gameManagement.windowManagement.publicDisplay.gameWindow.ScoreFields;
import gameManagement.windowManagement.publicDisplay.gameWindow.Timer;
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
	public GameWindow() {}
	
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
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		// just draw the background image
		super.paintComponent(g);
		initComponents();
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(background, 0, 0, getWidth(), getHeight(), null);
	}
	
	// set up to where it is only called once from paintComponent
	private void initComponents()
	{
		if (notInitialized)
		{
			try 
			{
				// Create the staff and place in the panel
				staff = new AnimatedStaff();
				double scale = 0.2;
				background = ImageIO.read(new File("blackground.jpg"));
				Dimension size = getSize();
				staffDimension.setSize((int)(size.width*(1-scale)), (int)(size.height*0.28));
				staffPoint.setLocation((getWidth() - staffDimension.width)/2, (getHeight() - staffDimension.height)/2);
				
				// Set up the GameWindow for absolute positioning
				setLayout(null);
				add(staff);
				
				// Set where and how big the staff will be and repaint it
				staff.setBounds(staffPoint.x, staffPoint.y, staffDimension.width, staffDimension.height);
				staff.repaint();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			finally
			{
				notInitialized = false;
			}
		}
	}
	
	private AnimatedStaff staff = null;
	private BufferedImage background = null;
	private Timer timer = new Timer();
	private ScoreFields scores = new ScoreFields();
	private Point staffPoint = new Point();
	private Dimension staffDimension = new Dimension();
	
	private boolean notInitialized = true;
	
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setSize(1366, 768);
		
		frame.setLayout(new GridLayout(1,1));
		GameWindow window = new GameWindow();
		frame.getContentPane().add(window);
		
		frame.setVisible(true);
	}
}
