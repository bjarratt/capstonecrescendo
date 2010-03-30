package server;

import java.io.IOException;
import network.ConnectionManager;
import display.DisplayManager;


public class EntryPoint {

	private static ConnectionManager connectionManager;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// OODSS Connection Example
		connectionManager = new ConnectionManager();
		
		/*
		// LogManager Example
		LogManager.getInstance().writeLogEntry(LogStrings.SERVER, LogStrings.SERVER_STARTED);
		
		DisplayManager display = new DisplayManager();
		
		display.Run();
		
		LogManager.getInstance().writeLogEntry(LogStrings.SERVER, LogStrings.SERVER_SHUTDOWN);
		*/
	}
}
