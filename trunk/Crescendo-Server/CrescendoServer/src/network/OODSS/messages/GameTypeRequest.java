package network.OODSS.messages;

import network.OODSS.base.PublicServer;
import ecologylab.collections.Scope;
import ecologylab.services.messages.OkResponse;
import ecologylab.services.messages.RequestMessage;
import ecologylab.services.messages.ResponseMessage;

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
		
		return OkResponse.reusableInstance;
	}

}
