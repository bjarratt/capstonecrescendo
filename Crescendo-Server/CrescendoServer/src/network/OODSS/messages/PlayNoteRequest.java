package network.OODSS.messages;

import network.OODSS.base.PublicServer;
import server.TestDisplay;
import ecologylab.collections.Scope;
import ecologylab.services.messages.OkResponse;
import ecologylab.services.messages.RequestMessage;
import ecologylab.services.messages.ResponseMessage;

/**
 * A message to be sent during gameplay only that indicates the player's note selection
 * 
 * @author Zach
 */
public class PlayNoteRequest extends RequestMessage {

	@xml_attribute private String jfuguePattern = "";  	// This should be a pattern that JFugue recognizes to play a note
	
	public PlayNoteRequest()
	{
		
	}
	
	@Override
	public ResponseMessage performService(Scope clientSessionScope) 
	{
		TestDisplay display = (TestDisplay)clientSessionScope.get(PublicServer.DISPLAY_HANDLE);
		
		display.setText(this.jfuguePattern);
		
		return OkResponse.reusableInstance;
	}

}
