package server;

import java.io.IOException;
import network.ConnectionManager;
//import display.DisplayManager;


public class EntryPoint {

	private static ConnectionManager connectionManager;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// OODSS Connection Example
		ConnectionManager.getInstance().initServer();
	}
}
