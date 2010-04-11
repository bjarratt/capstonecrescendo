package displayManager.windowManagement;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JFrame;
import java.awt.Panel;


import processing.core.*;
import displayManager.windowManagement.listeners.WindowHandler;
import displayManager.windowManagement.windows.Window;

public class WindowManager 
{
	public void run()
	{
		this.mainFrame.setVisible(true);
		// DO NOT MOVE THIS!  This is here because you will get sync errors otherwise.
		mainWindow.init();
		mainWindow.start();
	}
	
	public void addPane(final String key, final Panel window)
	{
		if (key != null && !windows.containsKey(key) && window != null)
		{
			windows.put(key, window);
		}
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
	
	private HashMap<String, Panel> windows = new HashMap<String, Panel>();
	private JFrame mainFrame = new JFrame();
	private Window mainWindow = new Window();
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
