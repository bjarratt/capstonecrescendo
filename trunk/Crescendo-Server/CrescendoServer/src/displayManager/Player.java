package displayManager;

import java.util.Random;

public class Player implements Runnable
{
	String player;
	ConnectionManager myCM;
	Random rand;

	public Player(String p, ConnectionManager cm)
	{
		player = p;
		myCM = cm;
		rand = new Random();
	}

	public void run()
	{
		while(true)
		{
			if(rand.nextInt(100000)==13)
			{
				try
				{
					myCM.sendMessage(new String(player + "_" + this.generatePitch() + "_" + generateLength()));
					Thread.sleep(500);
				}
				catch(InterruptedException e)
				{

				}
			}
		}
	}

	public String generatePitch()
	{
		switch(rand.nextInt(19))
		{
			case 0:
				return "D5";
			case 1:
				return "DSharp5";
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
			case 18:
				return "rest";
			default:
				return "rest";
		}
	}

	public String generateLength()
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