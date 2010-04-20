package gameManagement.windowManagement;

import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JFrame;
import java.awt.Panel;

import gameManagement.windowManagement.base.WindowDisplay;
import gameManagement.windowManagement.base.Wrapper;
import gameManagement.windowManagement.listeners.WindowHandler;

public class WindowManager 
{
	public void run()
	{
		this.mainFrame.setVisible(true);
		// DO NOT MOVE THIS!  This is here because you will get sync errors otherwise.
		mainWindow.init();
		mainWindow.start();
	}
	
	public void addWindow(final String key, final Wrapper window)
	{
		if (key != null && window != null)
		{
			if (windows.containsKey(key))
			{
				windows.remove(key);
			}
			windows.put(key, window);
		}
	}
	
	public Wrapper getWindow(String key)
	{
		Wrapper w = null;
		if (windows.containsKey(key))
		{
			w = windows.get(key);
		}
		return w;
	}
	
	public void nextWindow(String key) throws InterruptedException
	{
		if (windows.containsKey(key))
		{
			mainWindow.nextPanel(windows.get(key));
		}
	}
	
	public void previousWindow(String key) throws InterruptedException
	{
		if (windows.containsKey(key))
		{
			mainWindow.previousPanel(windows.get(key));
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
	
	private HashMap<String, Wrapper> windows = new HashMap<String, Wrapper>();
	private JFrame mainFrame = new JFrame();
	private WindowDisplay mainWindow = new WindowDisplay();
	private WindowHandler handler = new WindowHandler();
	
	private void initFrame()
	{
		// Set layout manager
		GridLayout manager = new GridLayout(1,1);
		mainFrame.setLayout(manager);
		
		// Set full-screen mode
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainFrame.setUndecorated(true);
		
		// Setup display
		mainFrame.addWindowListener(handler);
		mainFrame.getContentPane().add(mainWindow);
		
		// Set default close operation
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// Part of the singleton setup
	private WindowManager() 
	{
		initFrame();
	}
	
	private static WindowManager instance = new WindowManager();
}
