package network.OODSS.messages;

import keys.OODSS;
import network.ConnectionManager;
import ecologylab.collections.Scope;
import ecologylab.services.distributed.common.SessionObjects;
import ecologylab.services.distributed.server.clientsessionmanager.SessionHandle;
import ecologylab.services.messages.OkResponse;
import ecologylab.services.messages.RequestMessage;
import ecologylab.services.messages.ResponseMessage;
import gameManagement.GameManager;

public class ConnectionRequest extends RequestMessage 
{
	@Override
	public ResponseMessage performService(Scope clientSessionScope) 
	{
		SessionHandle handle = (SessionHandle)clientSessionScope.get(SessionObjects.SESSION_HANDLE);
		GameManager manager = (GameManager)clientSessionScope.get(OODSS.GAME_MANAGER);
		
		Object sessionHandle = handle;
		String playerID = ConnectionManager.getInstance().assignPlayerSlot(sessionHandle);
		
		manager.addMessageToPool(playerID + "_connect");
		
		ConnectionUpdate update = new ConnectionUpdate(playerID);
		
		handle.sendUpdate(update);
		
		return OkResponse.reusableInstance;
	}
	
	/**
	 * This allows the server to put a cap on connections for a game
	 * @return true for ignoring capabilities
	 */
	@Override
	public boolean isDisposable()
	{
		return false;
	}
}
