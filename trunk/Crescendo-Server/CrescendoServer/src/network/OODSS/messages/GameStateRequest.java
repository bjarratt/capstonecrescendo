package network.OODSS.messages;

import ecologylab.collections.Scope;
import ecologylab.services.messages.RequestMessage;
import ecologylab.services.messages.ResponseMessage;

public class GameStateRequest extends RequestMessage 
{

	@xml_attribute private String gameState;
	
	@Override
	public ResponseMessage performService(Scope clientSessionScope) 
	{
		return null;
	}
}
