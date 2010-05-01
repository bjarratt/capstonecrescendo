package gameManagement.windowManagement;

import gameManagement.windowManagement.publicDisplay.GameOptionsWindow;
import gameManagement.windowManagement.publicDisplay.GameWindow;
import gameManagement.windowManagement.publicDisplay.PauseWindow;
import gameManagement.windowManagement.publicDisplay.SplashWindow;

import java.awt.Color;
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
	
	public void goToWindow(String key)
	{
		if (windows.containsKey(key))
		{
			if (currentWindowKey != null)
			{
				mainFrame.getContentPane().remove(windows.get(currentWindowKey));
			}
			mainFrame.getContentPane().add(windows.get(key));
			currentWindowKey = key;
			windows.get(key).revalidate();
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
	
	private void initFrame()
	{
		// Set layout manager
//		GridLayout manager = new GridLayout(1,1);
		mainFrame.setLayout(null);
		
		// Set full-screen mode
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainFrame.setUndecorated(true);
		mainFrame.setSize(1366, 768/*Toolkit.getDefaultToolkit().getScreenSize()*/);
		mainFrame.getContentPane().setBackground(Color.BLACK);
		
		// Set default close operation
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// Part of the singleton setup
	private WindowManager() 
	{
		initFrame();
	}
	
	private static WindowManager instance = new WindowManager();

	private HashMap<String, JPanel> windows = new HashMap<String, JPanel>();
	private JFrame mainFrame = new JFrame();
	private String currentWindowKey = null;
	
	
	public static void main(String[] args)
	{
		WindowManager.getInstance().addWindow(GameState.SPLASH_SCREEN, new SplashWindow());
		WindowManager.getInstance().addWindow(GameState.GAME_OPTIONS, new GameOptionsWindow());
		WindowManager.getInstance().addWindow(GameState.PLAY, new GameWindow());
		WindowManager.getInstance().addWindow(GameState.PAUSE, new PauseWindow());
		WindowManager.getInstance().addWindow(GameState.POST_GAME, new JPanel());
		WindowManager.getInstance().run();
		
		try 
		{
//			WindowManager.getInstance().goToWindow(GameState.SPLASH_SCREEN);
//			Thread.sleep(1000);
			WindowManager.getInstance().goToWindow(GameState.GAME_OPTIONS);
			Thread.sleep(1000);
			WindowManager.getInstance().goToWindow(GameState.PLAY);
			Thread.sleep(1000);
//			WindowManager.getInstance().goToWindow(GameState.PAUSE);
//			Thread.sleep(1000);
//			WindowManager.getInstance().goToWindow(GameState.POST_GAME);
//			Thread.sleep(100);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
