package displayManager;

import java.util.ArrayList;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 *	This class holds all of the connection information for each iPhone
 *	This could also be a private class of GameManager or could simply be done away with, this is just my take on the class
 *
 *	@author Travis Kosarek
 */
public class ConnectionManager implements ActionListener
{
	private DisplayManager myDisplayManager;
	private ArrayList<String> messages;
	private Timer timer;
	private static int delay = 1000;
	//set the timeout value to 300 delay periods (in this case 300 seconds = 5 min)
	private static int timeout = 300;
	private int ticks;
	private boolean timeout_to_menu;
	private boolean exit;

	//perhaps have some iPhone connection objects?
	//player 1
	//player 2
	//player 3
	//player 4
	//i am not completely sure how oodss needs to be set up here

	Player player1;
	Player player2;
	Player player3;
	Player player4;

	Thread p1;
	Thread p2;
	Thread p3;
	Thread p4;

	public ConnectionManager()
	{
		//set up the display to receive messages
		myDisplayManager = new DisplayManager();
		//message pool to be sent to the DisplayManager
		messages = new ArrayList<String>();
		//set up a timer
		timer = new Timer(delay,this);
		ticks = 0;

		timeout_to_menu = false;
		exit = false;
		
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
		
		this.run();
	}

	public void sendMessage(String message)
	{
		receiveMessage(message);
	}

	public void actionPerformed(ActionEvent event)
	{
		if(messages.size() == 0)
		{
			ticks++;
			if(ticks >= timeout)
			{
				timeout_to_menu = true;
				ticks = 0;
			}
		}
		else
		{
			ticks = 0;
		}
		this.sendMessages();
	}

	public void run()
	{
		try
		{
			Thread.sleep(1000);
		}
		catch(InterruptedException e)
		{

		}
		
		timer.start();

		while(true)
		{
			if(exit == true)
				break;
			if(timeout_to_menu == true)
			{
				System.out.println("No messages were sent for " + timeout + " ticks");
				timeout_to_menu = false;
			}
		}
		timer.stop();
		System.exit(0);
	}

	public void receiveMessage(String message)
	{
		String[] messageComponents = message.split("_");
		String p = messageComponents[0];
		if(messages.size()>0)
			for(String msg : messages)
				if(!(p.equals(msg.split("_")[0])&& messageComponents.length == 3))
					messages.add(message);
	}

	public void sendMessages()
	{
		myDisplayManager.receiveMessage(messages);
		messages = new ArrayList<String>();
	}
}
