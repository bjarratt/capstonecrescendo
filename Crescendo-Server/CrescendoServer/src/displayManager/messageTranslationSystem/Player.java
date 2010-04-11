package displayManager.messageTranslationSystem;

import java.util.Random;

/**
 *	A class to simulate an iPhone connected and sending messages to the server
 *
 *	@author Travis Kosarek
 */
public class Player implements Runnable
{
	String player;
	ConnectionManager myCM;
	Random rand;

	/**
	 *	Constructs a Player from a String id and a ConnectionManager
	 *
	 *	@param id a String that the player will be identified with
	 *	@param cm the ConnectionManager object calling this Player
	 */
	public Player(String id, ConnectionManager cm)
	{
		player = id;
		myCM = cm;
		rand = new Random();
	}

	/**
	 *	Starts the Player sending random messages to the server
	 */
	public void run()
	{
		try
		{
			myCM.sendMessage(new String(player + "_connect"));
			Thread.sleep(500);

			if(player.equals("player1"))
			{
				System.out.println("Player 1 is waiting 1 second to start the game");
				Thread.sleep(1000);
				myCM.sendMessage(new String(player + "_start"));
			}
		}
		catch(InterruptedException e)
		{

		}
		while(true)
		{
			if(rand.nextInt(100000)==13)
			{
				try
				{
					myCM.sendMessage(new String(player + "_" + this.generatePitch() + "_" + this.generateLength()));
					Thread.sleep(500);
				}
				catch(InterruptedException e)
				{

				}
			}
		}

	}

	/**
	 *	Generates a random Note pitch to be used in a message sent to the server
	 *
	 *	@return a random Note pitch to be used in a message sent to the server
	 */
	private String generatePitch()
	{
		switch(rand.nextInt(19))
		{
			case 0:
				return "FSharp6";
			case 2:
				return "E5";
			case 3:
				return "ESharp5";
			case 4:
				return "F5";
			case 5:
				return "FSharp5";
			case 6:
				return "G5";
			case 7:
				return "GSharp5";
			case 8:
				return "A5";
			case 9:
				return "ASharp5";
			case 10:
				return "B5";
			case 11:
				return "C6";
			case 12:
				return "CSharp6";
			case 13:
				return "D6";
			case 14:
				return "DSharp6";
			case 15:
				return "E6";
			case 16:
				return "ESharp6";
			case 17:
				return "F6";
			default:
				return "B5";
		}
	}

	/**
	 *	Generates a random Note length to be used in a message sent to the server
	 *
	 *	@return a random Note length to be used in a message sent to the server
	 */
	private String generateLength()
	{
		switch(rand.nextInt(4))
		{
			case 0:
				return "eighthnote";
			case 1:
				return "quarternote";
			case 2:
				return "halfnote";
			case 3:
				return "wholenote";
			default:
				return "eighthnote";
		}
	}
}