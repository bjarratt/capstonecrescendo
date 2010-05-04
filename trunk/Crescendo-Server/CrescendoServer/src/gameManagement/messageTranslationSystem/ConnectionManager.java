package gameManagement.messageTranslationSystem;

import gameManagement.GameManager;

/**
 *	This class holds all of the connection information for each iPhone
 *	This could also be a private class of GameManager or could simply be done away with, this is just my take on the class
 *
 *	@author Travis Kosarek
 */
public class ConnectionManager
{
	private GameManager gameManager;


	public ConnectionManager()
	{
		//set up the display to receive messages
		gameManager = new GameManager();

		/******************iPhone Emulators******************/
		Player player1;
		Thread p1;
		player1 = new Player("player1",this);
		p1 = new Thread(player1);
		p1.start();
//		
//		Player player2;
//		Thread p2;
//		player2 = new Player("player2",this);
//		p2 = new Thread(player2);
//		p2.start();
//		
//		Player player3;
//		Thread p3;
//		player3 = new Player("player3",this);
//		p3 = new Thread(player3);
//		p3.start();
//		
//		Player player4;
//		Thread p4;
//		player4 = new Player("player4",this);
//		p4 = new Thread(player4);
//		p4.start();
		/****************************************************/

		gameManager.run();
	}

	public void sendMessage(String message)
	{
		gameManager.addMessageToPool(message);
	}
}
