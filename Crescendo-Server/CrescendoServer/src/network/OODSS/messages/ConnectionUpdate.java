package network.OODSS.messages;

import ecologylab.services.messages.ResponseMessage;
import ecologylab.services.messages.UpdateMessage;
import ecologylab.collections.Scope;

public class ConnectionUpdate extends UpdateMessage 
{
	@xml_attribute private String playerNumber = "";
	
	/**
	 * To be used on the client
	 */
	public ConnectionUpdate() {}
	
	/**
	 * To be used on the server
	 * @param clientID - <code>String</code> that identifies what number the player is assigned
	 */
	public ConnectionUpdate(String playerNumber)
	{
		this.playerNumber = playerNumber;
	}
	
	@Override
	public void processUpdate(Scope s)
	{
		
	}
	
}
