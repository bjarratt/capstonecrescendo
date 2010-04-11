package server;

import network.ConnectionManager;


public class EntryPoint {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// Start the server
		ConnectionManager.getInstance().initServer();
	}
}
