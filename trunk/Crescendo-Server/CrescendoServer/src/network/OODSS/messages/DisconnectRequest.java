package network.OODSS.messages;

import network.ConnectionManager;
import ecologylab.collections.Scope;
import ecologylab.services.messages.OkResponse;
import ecologylab.services.messages.ResponseMessage;

public class DisconnectRequest extends ecologylab.services.messages.DisconnectRequest {

	public DisconnectRequest() {}
	
	public DisconnectRequest(final String playerNumber) {}
	
	@Override
	public ResponseMessage performService(Scope clientSessionScope) 
	{
		super.performService(clientSessionScope);
		
		ConnectionManager.getInstance().cleanupPlayers();
		
		return OkResponse.reusableInstance;
	}

	

}
