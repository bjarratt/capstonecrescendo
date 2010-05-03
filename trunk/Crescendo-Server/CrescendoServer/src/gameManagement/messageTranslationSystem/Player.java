package gameManagement.messageTranslationSystem;

import java.awt.Color;

import javax.swing.JFrame;

/**
 *	A class to simulate an iPhone connected and sending messages to the server
 *
 *	@author Travis Kosarek
 */
public class Player implements Runnable
{
	private String player;
	private ConnectionManager myCM;
	
	private JFrame playerFrame;

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

		int num = new Integer(player.charAt(player.length()-1)) - 48;
		
		playerFrame = new JFrame(player);
		playerFrame.setLocation(220*num, 100);
		playerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		playerFrame.setVisible(true);
		playerFrame.setResizable(false);
		playerFrame.setBackground(Color.DARK_GRAY);
		
//		playerFrame.add(new CHIPhoneGUI(player, myCM));
		playerFrame.add(new IPhoneGUI(player, myCM));
		
		playerFrame.pack();
	}

	/**
	 *	Starts the Player sending random messages to the server
	 */
	public void run()
	{

	}
}