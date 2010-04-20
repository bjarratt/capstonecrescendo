package network.OODSS.messages;

import ecologylab.collections.Scope;
import ecologylab.services.messages.RequestMessage;
import ecologylab.services.messages.ResponseMessage;

public class GameOptionsRequest extends RequestMessage {

	@xml_attribute private String gameOptions;
	
	@Override
	public ResponseMessage performService(Scope clientSessionScope) {
		// TODO Auto-generated method stub
		return null;
	}
}
