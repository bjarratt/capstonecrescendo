package server;

import java.io.IOException;
import display.DisplayManager;
import logging.LogManager;
import logging.constants.*;


public class EntryPoint {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// LogManager Example
		LogManager.getInstance().writeLogEntry(LogStrings.SERVER, LogStrings.SERVER_STARTED);
		
		DisplayManager display = new DisplayManager();
		
		display.Run();
		
		LogManager.getInstance().writeLogEntry(LogStrings.SERVER, LogStrings.SERVER_SHUTDOWN);
	}
}
