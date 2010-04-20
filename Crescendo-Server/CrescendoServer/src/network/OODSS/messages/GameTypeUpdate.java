package network.OODSS.messages;

import ecologylab.collections.Scope;
import ecologylab.services.messages.UpdateMessage;

public class GameTypeUpdate extends UpdateMessage 
{
	@xml_attribute String currentType = "";
	
	public GameTypeUpdate(String type)
	{
		currentType = type;
	}
	
	@Override
	public void processUpdate(Scope appObjScope)
	{
		
	}
}
