package display;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;

import logging.LogManager;


public class DisplayManager 
{
	public DisplayManager()
	{
	}
	
	public void Run()
	{
		mainFrame.setVisible(true);
	}

	private void initializeComponents()
	{
		mainFrame.setMinimumSize(new Dimension(600, 400));
		mainFrame.setLocationByPlatform(true);
	}
	
	private ServerDisplay mainFrame = new ServerDisplay();
}
