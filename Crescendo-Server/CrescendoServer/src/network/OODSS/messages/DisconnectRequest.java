package network.OODSS.messages;

import java.util.HashMap;
import java.util.List;

import keys.OODSS;
import keys.Players;
import network.ConnectionManager;
import ecologylab.collections.Scope;
import ecologylab.services.distributed.common.SessionObjects;
import ecologylab.services.distributed.server.clientsessionmanager.SessionHandle;
import ecologylab.services.messages.OkResponse;
import ecologylab.services.messages.ResponseMessage;
import gameManagement.GameManager;

public class DisconnectRequest extends ecologylab.services.messages.DisconnectRequest {

	public DisconnectRequest() {}
	
	public DisconnectRequest(final String playerNumber) {}
	
	@Override
	public ResponseMessage performService(Scope clientSessionScope) 
	{
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		super.performService(clientSessionScope);
		
		List<String> wasConnected = ConnectionManager.getInstance().listPlayers();
		ConnectionManager.getInstance().cleanupPlayers();
		List<String> connected = ConnectionManager.getInstance().listPlayers();
		List<String> all = Players.getPlayers();
		
		GameManager manager = (GameManager)clientSessionScope.get(OODSS.GAME_MANAGER);
		
		// Disconnect the user that sent request
		for (String was : wasConnected)
		{
			if (!connected.contains(was))
			{
				for (String player : all)
				{
					if (was.contains(player))
					{
						manager.addMessageToPool(player + "_disconnect");
					}
				}
			}
		}
		
		// Get all of the handles for the sessions currently connected to the server
		HashMap<String, SessionHandle> sessionHandleMap = (HashMap<String, SessionHandle>)ConnectionManager.getInstance().listPlayersMap();

		// Get this client's SessionHandle
		SessionHandle mySessionHandle = (SessionHandle) clientSessionScope
				.get(SessionObjects.SESSION_HANDLE);
		
		// Loop through the other sessions.
		for (String key : sessionHandleMap.keySet())
		{
			// If this isn't me then send them an update.
			if (!mySessionHandle.equals(sessionHandleMap.get(key)) && sessionHandleMap.get(key) != null)
			{
				// Form a update message to send out to all of the other clients.
				ConnectionUpdate messageUpdate = new ConnectionUpdate(key);
				
				// Send message
				sessionHandleMap.get(key).sendUpdate(messageUpdate);
			}
		}
		
		return OkResponse.reusableInstance;
	}

	

}
