package network.OODSS.messages;

import ecologylab.collections.Scope;
import ecologylab.services.messages.RequestMessage;
import ecologylab.services.messages.ResponseMessage;

/**
 * A message to be sent during gameplay only that indicates the player's note selection
 * 
 * @author Zach
 */
public class PlayNoteRequest extends RequestMessage {

	private String jfuguePattern = "";  	// This should be a pattern that JFugue recognizes to play a note
	private String player = "";  			// This should be a uniqe String that identifies the player
	
	@Override
	public ResponseMessage performService(Scope clientSessionScope) {
		// TODO Auto-generated method stub
		return null;
	}

}
