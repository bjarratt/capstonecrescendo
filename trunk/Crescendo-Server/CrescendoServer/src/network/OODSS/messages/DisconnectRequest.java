package network.OODSS.messages;

import server.TestDisplay;
import network.ConnectionManager;
import network.OODSS.base.PublicServer;
import ecologylab.collections.Scope;
import ecologylab.services.distributed.common.SessionObjects;
import ecologylab.services.distributed.server.clientsessionmanager.SessionHandle;
import ecologylab.services.messages.OkResponse;
import ecologylab.services.messages.RequestMessage;
import ecologylab.services.messages.ResponseMessage;

public class DisconnectRequest extends ecologylab.services.messages.DisconnectRequest {

//	@xml_attribute private String playerNumber = "";
	
	public DisconnectRequest() {}
	
	public DisconnectRequest(final String playerNumber)
	{
//		this.playerNumber = playerNumber;
	}
	
	@Override
	public ResponseMessage performService(Scope clientSessionScope) 
	{
		super.performService(clientSessionScope);
		
		ConnectionManager.getInstance().cleanupPlayers();
		
		//TestDisplay display = (TestDisplay)clientSessionScope.get(PublicServer.DISPLAY_HANDLE);
		
		//display.setText(ConnectionManager.getInstance().listPlayers());
		
		return OkResponse.reusableInstance;
	}

	

}
