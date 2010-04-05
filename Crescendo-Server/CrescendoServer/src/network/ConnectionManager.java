package network;

import java.io.IOException;
import java.util.HashMap;

import network.OODSS.base.PublicServer;

public class ConnectionManager {
	
	public String assignPlayerSlot(Object sessionID)
	{
		
		
		return "";
	}
	
	public void initServer()
	{
		try {
			this.server = new PublicServer();
			//playerMap.
			this.isInitialized = true;
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			System.err.println(e.getMessage());
			this.isInitialized = false;
		}

	}
	
	public static ConnectionManager getInstance()
	{
		return instance;
	}
	
	private ConnectionManager() 
	{
	}
	
	private HashMap<String, Object> playerMap = new HashMap<String, Object>();
	private PublicServer server;
	private static ConnectionManager instance = new ConnectionManager();
	private boolean isInitialized = false;
}
