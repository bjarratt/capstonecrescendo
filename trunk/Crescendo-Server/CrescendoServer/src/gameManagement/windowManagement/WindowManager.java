package gameManagement.windowManagement;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Manages all windows in the game.  It will display whatever you tell it to.  It's like a dog in that 
 * sense.  But what you need to know is that it keeps a collection of windows (i.e. jpanels) that it can
 * display to the user via a fancy transition, idea courtesy of Travis Kosarek.
 * 
 * @author Zach
 */
public class WindowManager 
{
	/**
	 * Tells the window manager to show the start up frame.
	 */
	public void run()
	{
		this.mainFrame.setVisible(true);
	}
	
	/**
	 * Add a window to the Manager's collection.
	 * @param key - <code>String</code> key with which to reference the specified window
	 * @param window - <code>JPanel</code> that is associated with the given key and to be shown
	 * 				   when the key is fed into the method <code>goToWindow()</code>.
	 */
	public void addWindow(final String key, JPanel window)
	{
		aniWin.addWindow(key, window);
	}
	
	/**
	 * Get a reference to the window that the key references
	 * @param key - <code>String</code> that is associated with the desired reference.
	 * @return - a JPanel object if the key is being used, else it returns null
	 */
	public JPanel getWindow(String key)
	{
		return aniWin.getWindow(key);
	}
	
	/**
	 * Tells the window manager to slide the specified screen into view.  It does nothing if the key
	 * is not associated with a window.
	 * @param key - <code>String</code> key indicating to which window to transition
	 */
	public synchronized void goToWindow(String key)
	{
		try
		{
			Thread.sleep(1000);
			aniWin.goToWindow(key);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Get a reference to the window manager
	 * @return - return a reference the only instance of the <code>WindowManager</code> class
	 */
	public static WindowManager getInstance()
	{
		return instance;
	}
	
	// setup the JFrame
	private void initFrame()
	{
		// Set layout manager
		GridLayout manager = new GridLayout(1,1);
		mainFrame.setLayout(manager);
		
		// Set full-screen mode
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainFrame.setUndecorated(true);
//		mainFrame.setMinimumSize(new java.awt.Dimension(800,600));
		mainFrame.getContentPane().setBackground(Color.red);
		
		// Set default close operation
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Add animation window
		mainFrame.getContentPane().add(aniWin);
	}
	
	// Part of the singleton setup
	private WindowManager() 
	{
		initFrame();
	}
	
	private static WindowManager instance = new WindowManager();

	private JFrame mainFrame = new JFrame();
	private AnimationWindow aniWin = new AnimationWindow();
}
