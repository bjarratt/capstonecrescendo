package gameManagement.windowManagement.publicDisplay.gameWindow;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;

public class GameTimer extends JTextField 
{
	public GameTimer()
	{
		super();
		initSelf();
		setTime(60);
	}
	
	public GameTimer(int initTime)
	{
		super();
		initSelf();
		setTime(initTime);
	}
	
	public void setTime(int time)
	{
		if (time > 0)
		{
			initialTime = time;
			currentTime = initialTime;
			updateDisplay();
		}
	}
	
	public void decrement()
	{
		if (currentTime > 0)
		{
			--currentTime;
			updateDisplay();
		}
	}
	
	public void reset()
	{
		setTime(defaultInitialTime);
	}
	
	public String toTimeString()
	{
		Integer min = currentTime / 60;
		Integer sec = currentTime % 60;
		
		String timeString = min.toString() + ":";
		
		timeString += (sec < 10) ? "0" + sec.toString() : sec.toString();
		
		return timeString;
	}
	
	private void initSelf()
	{
		setEditable(false);
		setBackground(Color.darkGray);
		setForeground(Color.white);
		setBorder(null);
		setFont(new Font("Courier New", Font.PLAIN, 40));
		setHorizontalAlignment(JTextField.CENTER);
	}
	
	private void updateDisplay()
	{
		Color foreground = (currentTime <= 10) ? Color.red : Color.white;
		setForeground(foreground);
			
		setText(toTimeString());
	}
	
	private int initialTime = 0;
	private int currentTime = 0;
	private int defaultInitialTime = 0;
	
	private static final long serialVersionUID = 1L;
}
