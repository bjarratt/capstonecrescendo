package network;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import ecologylab.services.distributed.server.clientsessionmanager.SessionHandle;

import network.OODSS.base.Keys;
import network.OODSS.base.PublicServer;

public class ConnectionManager 
{
	public String assignPlayerSlot(Object sessionID)
	{
		String playerID = "";
		
		lock.lock();
		try {
			Set<String> keys = playerMap.keySet();
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
		}
		finally {
			lock.unlock();
		}
		
		return playerID;
	}
	
	public void cleanupPlayers()
	{
		lock.lock();
		try
		{
			HashMap<String, Object> newMap = new HashMap<String, Object>(playerMap);
			
			Set<String> keys = newMap.keySet();
			
			for (String key : keys)
			{
				SessionHandle sh = (SessionHandle)playerMap.get(key);
				
				if (sh != null && sh.getSessionManager().isInvalidating())
				{
					playerMap.remove(key);
					playerMap.put(key, null);
				}
			}
		}
		finally {
			lock.unlock();
		}
	}
	
	/**
	 * Utility method for seeing which player slots are filled.
	 * @return - a <code>String</code> with all the player keys that have filled slots
	 */
	public String listPlayers()
	{
		String current = "Player List:\n";
		
		Set<String> keys = playerMap.keySet();
		
		for (String key : keys)
		{
			if (playerMap.get(key) != null)
			{
				current += " -- " + key + "\n";
			}
		}
		
		return current;
	}
	
	/**
	 * Call this method to initialized the OODSS server
	 */
	public void initServer()
	{
		lock.lock();
		try {
			this.server = new PublicServer();
			this.isInitialized = true;
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			System.err.println(e.getMessage());
			this.isInitialized = false;
		}
		finally
		{
			lock.unlock();
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
	
	private Lock lock = new ReentrantLock();
	
	private HashMap<String, Object> playerMap = new HashMap<String, Object>();
	private PublicServer server = null;
	private static ConnectionManager instance = new ConnectionManager();
	private boolean isInitialized = false;
}
