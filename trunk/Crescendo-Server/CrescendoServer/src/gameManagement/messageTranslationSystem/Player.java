package gameManagement.messageTranslationSystem;

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
		switch(rand.nextInt(17))
		{
			case 0:
				return "E5";
			case 1:
				return "ESharp5";
			case 2:
				return "F5";
			case 3:
				return "FSharp5";
			case 4:
				return "G5";
			case 5:
				return "GSharp5";
			case 6:
				return "A5";
			case 7:
				return "ASharp5";
			case 8:
				return "B5";
			case 9:
				return "C6";
			case 10:
				return "CSharp6";
			case 11:
				return "D6";
			case 12:
				return "DSharp6";
			case 13:
				return "E6";
			case 14:
				return "ESharp6";
			case 15:
				return "F6";
			case 16:
				return "FSharp6";
			default:
				return "rest";
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
				return "eighth";
			case 1:
				return "quarter";
			case 2:
				return "half";
			default:
				return "whole";
		}
	}
}