package network;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import network.OODSS.base.Keys;
import network.OODSS.base.PublicServer;

public class ConnectionManager 
{
	
	public String assignPlayerSlot(Object sessionID)
	{
		Set<String> keys = playerMap.keySet();
		String playerID = "";
		
		for (String key : keys)
		{
			if (playerMap.get(key) == null)
			{
				playerMap.remove(key);
				playerMap.put(key, sessionID);
				playerID = key;
				break;
			}
		}
		
		return playerID;
	}
	
	public boolean removePlayer(String playerID)
	{
		return false;
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
		playerMap.put(Keys.PLAYER_1, null);
		playerMap.put(Keys.PLAYER_2, null);
		playerMap.put(Keys.PLAYER_3, null);
		playerMap.put(Keys.PLAYER_4, null);
	}
	
	private HashMap<String, Object> playerMap = new HashMap<String, Object>();
	private PublicServer server;
	private static ConnectionManager instance = new ConnectionManager();
	private boolean isInitialized = false;
}