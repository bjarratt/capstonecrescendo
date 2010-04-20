package network.OODSS.messages;

import java.util.HashMap;

import keys.OODSS;
import ecologylab.collections.Scope;
import ecologylab.services.distributed.common.SessionObjects;
import ecologylab.services.distributed.server.clientsessionmanager.SessionHandle;
import ecologylab.services.messages.OkResponse;
import ecologylab.services.messages.RequestMessage;
import ecologylab.services.messages.ResponseMessage;
import gameManagement.GameManager;

public class GameTypeRequest extends RequestMessage 
{
	
	@xml_attribute private String gameType = "";
	
	public GameTypeRequest() {}
	
	public GameTypeRequest(final String gameType)
	{
		this.gameType = gameType;
	}

	@Override
	public ResponseMessage performService(Scope clientSessionScope) 
	{
		/* Send the gameType to the public display, so it can update the game state */
		GameManager manager = (GameManager)clientSessionScope.get(OODSS.GAME_MANAGER);
		HashMap<Object, SessionHandle> handleMap = (HashMap<Object, SessionHandle>)clientSessionScope.get(SessionObjects.SESSIONS_MAP);
		
		manager.addMessageToPool(gameType);
		
		GameTypeUpdate update = new GameTypeUpdate(gameType);
		
		for (SessionHandle handle : handleMap.values())
		{
			handle.sendUpdate(update);
		}
		
		return OkResponse.reusableInstance;
	}

}
