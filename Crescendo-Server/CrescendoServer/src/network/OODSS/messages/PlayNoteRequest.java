package network.OODSS.messages;

import keys.OODSS;
import logging.LogManager;
import network.OODSS.base.PublicServer;
import ecologylab.collections.Scope;
import ecologylab.services.messages.OkResponse;
import ecologylab.services.messages.RequestMessage;
import ecologylab.services.messages.ResponseMessage;
import gameManagement.GameManager;

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
		GameManager manager = (GameManager)clientSessionScope.get(OODSS.GAME_MANAGER);
		
		manager.addMessageToPool(jfuguePattern);
		
		//write the played note to the log
		String[] info = jfuguePattern.split("_");
		String player = info[0];
		String note = keys.Actions.PLAYED_NOTE + ":: " + info[1] + "_" + info[2];
		LogManager.getInstance().writeLogEntry(player, note);
		
		
		return OkResponse.reusableInstance;
	}

}
