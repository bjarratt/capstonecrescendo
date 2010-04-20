package network.OODSS.messages;

import keys.OODSS;
import ecologylab.collections.Scope;
import ecologylab.services.messages.RequestMessage;
import ecologylab.services.messages.ResponseMessage;
import gameManagement.GameManager;

public class GameOptionsRequest extends RequestMessage {

	@xml_attribute private String gameOption;
	
	public GameOptionsRequest() {}
	
	@Override
	public ResponseMessage performService(Scope clientSessionScope) 
	{
		GameManager manager = (GameManager)clientSessionScope.get(OODSS.GAME_MANAGER);
		manager.addMessageToPool(gameOption);
		
		return null;
	}
}
