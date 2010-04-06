package displayManager.messageTranslationSystem;


/**
 *	This class holds all of the connection information for each iPhone
 *	This could also be a private class of GameManager or could simply be done away with, this is just my take on the class
 *
 *	@author Travis Kosarek
 */
public class ConnectionManager
{
	private GameManager gameManager;

	/*****iPhone Emulators*****/
	Player player1;
	Player player2;
	Player player3;
	Player player4;

	Thread p1;
	Thread p2;
	Thread p3;
	Thread p4;
	/**************************/

	public ConnectionManager()
	{
		//set up the display to receive messages
		gameManager = new GameManager();

		/*******************Beginning of test code********************/
		//This section emulates four connected iPhones sending random mesages

		player1 = new Player("player1",this);
		player2 = new Player("player2",this);
		player3 = new Player("player3",this);
		player4 = new Player("player4",this);

		p1 = new Thread(player1);
		p2 = new Thread(player2);
		p3 = new Thread(player3);
		p4 = new Thread(player4);


		p1.start();
		p2.start();
		p3.start();
		p4.start();
		/**********************End of test code***********************/

		gameManager.run();
	}

	public void sendMessage(String message)
	{
		gameManager.addMessageToPool(message);
	}
}
