package gameManagement.windowManagement;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.HashMap;

import javax.swing.JPanel;

public class AnimationWindow extends JPanel 
{
	public AnimationWindow()
	{
		setLayout(null);
		setBackground(Color.BLACK);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
	}
	
	public void addWindow(String key, JPanel window)
	{
		if (key != null && window != null)
		{
			windows.put(key, window);
		}
	}
	
	public JPanel getWindow(String key)
	{
		JPanel returnWindow = null;
		if (key != null && windows.containsKey(key))
		{
			returnWindow = windows.get(key);
		}
		return returnWindow;
	}
	
	public void goToWindow(String key)
	{
		if (key != null && windows.containsKey(key))
		{
			animateWindow = true;
			currentKey = key;
			
			JPanel window = windows.get(key);
			window.setBounds(0, getHeight(), getWidth(), getHeight());
			
			removeAll();
			add(windows.get(key));
			
			repaint();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		shiftWindowUp();
	}
	
	private void shiftWindowUp()
	{
		if (animateWindow)
		{
			JPanel window = windows.get(currentKey);
			Rectangle r = window.getBounds();
			window.setBounds(0, r.y + transition, r.width, r.height);
			window.validate();
			if (r.y + transition <= 0)
			{
				window.setBounds(0, 0, r.width, r.height);
				animateWindow = false;
			}
			else
			{
				repaint();
			}
		}
	}
	
	private HashMap<String, JPanel> windows = new HashMap<String, JPanel>();
	private String currentKey = null;
	private boolean animateWindow = false;
	private static final int transition = -10;
	
	private static final long serialVersionUID = 1L;
}
