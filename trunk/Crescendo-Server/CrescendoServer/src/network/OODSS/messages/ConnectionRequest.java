package network.OODSS.messages;

import server.TestDisplay;
import network.OODSS.base.PublicServer;
import ecologylab.collections.Scope;
import ecologylab.services.distributed.common.SessionObjects;
import ecologylab.services.distributed.server.clientsessionmanager.SessionHandle;
import ecologylab.services.messages.OkResponse;
import ecologylab.services.messages.RequestMessage;
import ecologylab.services.messages.ResponseMessage;

public class ConnectionRequest extends RequestMessage 
{
	@Override
	public ResponseMessage performService(Scope clientSessionScope) 
	{
		SessionHandle handle = (SessionHandle)clientSessionScope.get(SessionObjects.SESSION_HANDLE);
		
		handle.getSessionId();
		
		ConnectionUpdate update = new ConnectionUpdate("player1");
		
		TestDisplay display = (TestDisplay)clientSessionScope.get(PublicServer.DISPLAY_HANDLE);
		display.setText("player1");
		
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
