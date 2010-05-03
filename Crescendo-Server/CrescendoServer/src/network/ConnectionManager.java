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
	
	@SuppressWarnings("finally")
	public ArrayList<String> cleanupPlayers()
	{
		lock.lock();
		ArrayList<String> playersDisconnected = new ArrayList<String>();
		try
		{
			HashMap<String, Object> newMap = new HashMap<String, Object>(playerMap);
			
			Set<String> keys = newMap.keySet();
			
			for (String key : keys)
			{
				SessionHandle sh = (SessionHandle)playerMap.get(key);
				
				if (sh != null && sh.getSessionManager().isInvalidating())
				{
					playersDisconnected.add(key);
					playerMap.remove(key);
					playerMap.put(key, null);
				}
			}

			newMap = new HashMap<String, Object>(playerMap);
			
			keys = newMap.keySet();
			
			ArrayList<String> playerIDs = new ArrayList<String>();
			int playerID = 0;
			
			for (String key : keys)
			{
				if ((playerMap.get(key) != null) && (playerIDs.size() > 0))
				{
					// Get current Session
					Object sessionHandle = playerMap.get(key);
					
					// Reset this player
					playerMap.remove(key);
					playerMap.put(key, null);
					
					// Place at first null position in the playerMap
					if (playerIDs.size()-1 >= playerID)
					{
						playerMap.put(playerIDs.get(playerID), sessionHandle);
						playerID++;
					}
				}
				if (playerMap.get(key) == null)
				{
					playerIDs.add(key);
				}
			}	
		}
		finally {
			lock.unlock();
			return playersDisconnected;
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
	 * Utility method for seeing the playerMap.
	 * @return - a <code>String</code> with all the player keys that have filled slots
	 */
	public HashMap<String, SessionHandle> listPlayersMap()
	{		
		HashMap<String, SessionHandle> newMap = new HashMap<String, SessionHandle>();
		for (String key : playerMap.keySet())
		{
			newMap.put(key, (SessionHandle) playerMap.get(key));
		}
		return newMap;
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
