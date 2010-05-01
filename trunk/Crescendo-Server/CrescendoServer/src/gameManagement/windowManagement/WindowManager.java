package gameManagement.windowManagement;

import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import keys.GameState;

public class WindowManager 
{
	public void run()
	{
		this.mainFrame.setVisible(true);
	}
	
	public void addWindow(final String key, JPanel window)
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
	
	public JPanel getWindow(String key)
	{
		JPanel w = null;
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
		}
	}
	
	public void previousWindow(String key) throws InterruptedException
	{
		if (windows.containsKey(key))
		{
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
	
	private HashMap<String, JPanel> windows = new HashMap<String, JPanel>();
	private JFrame mainFrame = new JFrame();
	
	private void initFrame()
	{
		// Set layout manager
		GridLayout manager = new GridLayout(1,1);
		mainFrame.setLayout(manager);
		
		// Set full-screen mode
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainFrame.setUndecorated(true);
		
		// Setup display
//		mainFrame.getContentPane().add(mainWindow);
		
		// Set default close operation
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// Part of the singleton setup
	private WindowManager() 
	{
		initFrame();
	}
	
	private static WindowManager instance = new WindowManager();
	
	public static void main(String[] args)
	{
//		WindowManager.getInstance().addWindow(GameState.SPLASH_SCREEN, window)
		WindowManager.getInstance().run();
	}
}
