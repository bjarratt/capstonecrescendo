package network.OODSS.messages;

import java.util.List;

import keys.OODSS;
import keys.Players;
import network.ConnectionManager;
import ecologylab.collections.Scope;
import ecologylab.services.messages.OkResponse;
import ecologylab.services.messages.ResponseMessage;
import gameManagement.GameManager;

public class DisconnectRequest extends ecologylab.services.messages.DisconnectRequest {

	public DisconnectRequest() {}
	
	public DisconnectRequest(final String playerNumber) {}
	
	@Override
	public ResponseMessage performService(Scope clientSessionScope) 
	{
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		super.performService(clientSessionScope);
		
		ConnectionManager.getInstance().cleanupPlayers();
		List<String> connected = ConnectionManager.getInstance().listPlayers();
		List<String> all = Players.getPlayers();
		
		GameManager manager = (GameManager)clientSessionScope.get(OODSS.GAME_MANAGER);
		
		manager.addMessageToPool("player1_disconnect");
		/*
		for (String player : all)
		{
			if (!connected.contains(player))
			{
				manager.addMessageToPool(player + "_disconnect");
			}
		}
		*/
		
		return OkResponse.reusableInstance;
	}

	

}
