package publicDisplay.gameWindow;

public class Timer
{
	/**
	 * Create a new timer display.  Initial time is set to 3 minutes.
	 */
	public Timer()
	{
		currentTime = initialTime;
		getDisplayString();
	}
	
	/**
	 * Create a new timer display
	 * @param initialTime - The time that the display shows at start up.  The input is in seconds,
	 * 						but the display will convert to M:SS
	 */
	public Timer(int initialTime)
	{
		if (initialTime <= 0)
		{
			this.initialTime = initialTime;
		}
		currentTime = initialTime;
		getDisplayString();
	}
	
	/**
	 * Indicates whether time is still left on the timer
	 * @return - <code>true</code> if there is still time left on the timer
	 */
	public boolean hasTimeLeft()
	{
		return currentTime != 0;
	}
	
	/**
	 * Decrements the displayed time by a second
	 */
	public void decrementTime()
	{
		--currentTime;
		getDisplayString();
	}
	
	/**
	 * Sets the timer to the whatever the initial time is set to
	 */
	public void reset()
	{
		currentTime = initialTime;
		getDisplayString();
	}
	
	/**
	 * Sets the initial time to the specified time and resets the timer
	 * @param time - starting time in seconds, which is converted to M:SS
	 */
	public void setInitialTime(int time)
	{
		if (time >= 0)
		{
			initialTime = time;
			reset();
		}
	}
	
	public void addTime(int moreTime)
	{
		if (moreTime > 0)
		{
			currentTime += moreTime;
			getDisplayString();
		}
	}
	
	
	// formats the string in the text field
	private String getDisplayString()
	{
		Integer minutes = currentTime/60;
		Integer seconds = currentTime % 60;
		
		String displayString = minutes.toString() + ":";
		displayString += (seconds >= 10) ? seconds.toString() : "0" + seconds.toString(); 
		
		return displayString;
	}
	
	private int currentTime = 0;
	private int initialTime = 180;
}
