package network;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import keys.Players;

import ecologylab.services.distributed.server.clientsessionmanager.SessionHandle;

import network.OODSS.base.PublicServer;

public class ConnectionManager 
{
	public String assignPlayerSlot(Object sessionID)
	{
		lock.lock();
		String playerID = "";
		
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
	public List<String> listPlayers()
	{
		ArrayList<String> currentPlayers = new ArrayList<String>();
		
		Set<String> keys = playerMap.keySet();
		
		for (String key : keys)
		{
			if (playerMap.get(key) != null)
			{
				currentPlayers.add(key);
			}
		}
		
		return currentPlayers;
	}
	
	/**
	 * Call this method to initialized the OODSS server
	 */
	public void initServer()
	{
		lock.lock();
		try {
			new PublicServer();
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
		playerMap.put(Players.PLAYER_ONE, null);
		playerMap.put(Players.PLAYER_TWO, null);
		playerMap.put(Players.PLAYER_THREE, null);
		playerMap.put(Players.PLAYER_FOUR, null);
	}
	
	private Lock lock = new ReentrantLock();
	
	private HashMap<String, Object> playerMap = new HashMap<String, Object>();
	private static ConnectionManager instance = new ConnectionManager();
	private boolean isInitialized = false;
}
