package network.OODSS.messages;

import keys.OODSS;
import ecologylab.collections.Scope;
import ecologylab.services.messages.OkResponse;
import ecologylab.services.messages.RequestMessage;
import ecologylab.services.messages.ResponseMessage;
import gameManagement.GameManager;

public class GameStateRequest extends RequestMessage 
{

	@xml_attribute private String gameState;
	
	@Override
	public ResponseMessage performService(Scope clientSessionScope) 
	{
		GameManager manager = (GameManager)clientSessionScope.get(OODSS.GAME_MANAGER);
		manager.addMessageToPool(gameState);
		
		return OkResponse.reusableInstance;
	}
}
