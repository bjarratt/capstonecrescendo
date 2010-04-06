package network.OODSS.messages;

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

	private String jfuguePattern = "";  	// This should be a pattern that JFugue recognizes to play a note
	
	@Override
	public ResponseMessage performService(Scope clientSessionScope) 
	{
		
		
		return OkResponse.reusableInstance;
	}

}
