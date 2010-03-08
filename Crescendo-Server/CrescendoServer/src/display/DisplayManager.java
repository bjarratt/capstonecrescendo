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
		mainFrame.setLayout(new GridLayout(3,3));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initializeComponents();
	}
	
	public void Run()
	{
		mainFrame.setVisible(true);
	}

	private void initializeComponents()
	{
		mainFrame.setMinimumSize(new Dimension(600, 400));
		mainFrame.setLocationByPlatform(true);
		mainFrame.getContentPane().add(new JButton("Button"));
	}
	
	private JFrame mainFrame = new JFrame();
}
