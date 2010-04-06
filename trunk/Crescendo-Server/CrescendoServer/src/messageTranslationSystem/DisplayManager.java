package displayManager;

import java.util.ArrayList;

/**
 *	The DisplayManager class is the general tie between the DisplayGUI and the audio from JFugue
 *
 *	@author Travis Kosarek
 */
public class DisplayManager
{
	private DisplayGUI myDisplayGUI;

	final int maxSubdivisions;
	int currentSubdivisions;


	private static int ticks = 0;

	public DisplayManager()
	{
		//set up the DisplayGUI
		//myDisplayGUI = new DisplayGUI();


		maxSubdivisions = 8;
		currentSubdivisions = 0;

	}

}
