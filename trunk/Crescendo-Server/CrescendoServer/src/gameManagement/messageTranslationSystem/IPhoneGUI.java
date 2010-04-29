package gameManagement.messageTranslationSystem;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;

import keys.GameState;

public class IPhoneGUI extends JPanel implements ActionListener
{
	private static final long serialVersionUID = -3339327851854047138L;
	
	private String player;
	private gameManagement.messageTranslationSystem.ConnectionManager myCM;
	private JButton connect;
	private JButton disconnect;
	private JButton playNote;
	private JButton splashScreen;
	private JButton gameOptions;
	private JButton setTempo;
	private JButton setKey;
	private JButton setTimeSignature;
	private JButton setNumberOfBars;
	private JButton play;
	private JButton pause;
	private JButton review;
	
	private Random rand;
	
	
	public IPhoneGUI(String player, gameManagement.messageTranslationSystem.ConnectionManager myCM)
	{
		super(true);
		
        this.setPreferredSize(new Dimension(220,450));
		this.setVisible(true);
	
		this.player = player;
		this.myCM = myCM;
		rand = new Random();
		
		disconnect = new JButton("Disconnect");
		disconnect.addActionListener(this);
		this.add(disconnect);
		
		connect = new JButton("Connect");
		connect.addActionListener(this);
		this.add(connect);
				
		splashScreen = new JButton("Go to Splash Screen State");
		splashScreen.addActionListener(this);
		this.add(splashScreen);

		gameOptions = new JButton("Got to Game Options State");
		gameOptions.addActionListener(this);
		this.add(gameOptions);

		setTempo = new JButton("Set Tempo (Random)");
		setTempo.addActionListener(this);
		this.add(setTempo);

		setKey = new JButton("Set Key (Random)");
		setKey.addActionListener(this);
		this.add(setKey);

		setTimeSignature = new JButton("Set Time Signature (Random)");
		setTimeSignature.addActionListener(this);
		this.add(setTimeSignature);
		
		setNumberOfBars = new JButton("Set Number of Bars (Random)");
		setNumberOfBars.addActionListener(this);
		this.add(setNumberOfBars);

		play = new JButton("Go to Play State");
		play.addActionListener(this);
		this.add(play);

		pause = new JButton("Go to Pause State");
		pause.addActionListener(this);
		this.add(pause);
		
		playNote = new JButton("Play a Random Note");
		playNote.addActionListener(this);
		this.add(playNote);

		review = new JButton("Review Game");
		review.addActionListener(this);
		this.add(review);
	}

	public void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == connect)
			myCM.sendMessage(player + "_" + GameState.CONNECT);
		if(e.getSource() == disconnect)
			myCM.sendMessage(player + "_" + GameState.DISCONNECT);
		if(e.getSource() == playNote)
			myCM.sendMessage(player + "_" + generatePitch() + "_" + generateLength());
		if(e.getSource() == splashScreen)
			myCM.sendMessage(player + "_" + GameState.SPLASH_SCREEN);
		if(e.getSource() == gameOptions)
			myCM.sendMessage(player + "_" + GameState.GAME_OPTIONS);
		if(e.getSource() == setTempo)
			myCM.sendMessage(player + "_" + GameState.SET_TEMPO + "_" + generateTempo());
		if(e.getSource() == setKey)
			myCM.sendMessage(player + "_" + GameState.SET_KEY + "_" + generateKey());
		if(e.getSource() == setTimeSignature)
			myCM.sendMessage(player + "_" + GameState.SET_TIME_SIGNATURE + "_" + generateTimeSignatureNumerator()+ "/4");
		if(e.getSource() == setNumberOfBars)
			myCM.sendMessage(player + "_" + GameState.SET_NUMBER_OF_BARS + "_" + generateNumberOfBars());
		if(e.getSource() == play)
			myCM.sendMessage(player + "_" + GameState.PLAY);
		if(e.getSource() == pause)
			myCM.sendMessage(player + "_" + GameState.PAUSE);
		if(e.getSource() == review)
			myCM.sendMessage(player + "_" + GameState.PLAY_SONG);
	}

	/**
	 *	Generates a random Note pitch to be used in a message sent to the server
	 *
	 *	@return a random Note pitch to be used in a message sent to the server
	 */
	private String generatePitch()
	{
		switch(rand.nextInt(16))
		{
			case 0:
				return "E5";
			case 1:
				return "F5";
			case 2:
				return "FSharp5";
			case 3:
				return "G5";
			case 4:
				return "GSharp5";
			case 5:
				return "A5";
			case 6:
				return "ASharp5";
			case 7:
				return "B5";
			case 8:
				return "C6";
			case 9:
				return "CSharp6";
			case 10:
				return "D6";
			case 11:
				return "DSharp6";
			case 12:
				return "E6";
			case 13:
				return "F6";
			case 14:
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
				return "eighthnote";
			case 1:
				return "quarternote";
			case 2:
				return "halfnote";
			default:
				return "wholenote";
		}
	}
	
	/**
	 *	Generates a random tempo from 60(Adagio) - 180(Presto)
	 *
	 *	@return a random tempo from 
	 */
	private String generateKey()
	{
		switch(rand.nextInt(12))
		{
			case 0:
				return "F";
			case 1:
				return "C";
			case 2:
				return "G";
			case 3:
				return "D";
			case 4:
				return "A";
			case 5:
				return "E";
			case 6:
				return "B";
			case 7:
				return "FSharp";
			case 8:
				return "BFlat";
			case 9:
				return "EFlat";
			case 10:
				return "AFlat";
			default:
				return "DFlat";
		}
	}
	
	/**
	 *	Generates a random tempo from 60(Adagio) - 180(Presto)
	 *
	 *	@return a random tempo from 
	 */
	private int generateTempo()
	{
		switch(rand.nextInt(9))
		{
			case 0:
				return 60;
			case 1:
				return 65;
			case 2:
				return 70;
			case 3:
				return 80;
			case 4:
				return 95;
			case 5:
				return 110;
			case 6:
				return 120;
			case 7:
				return 145;
			default:
				return 180;
		}
	}
	
	/**
	 *	Generates a random tempo from 60(Adagio) - 180(Presto)
	 *
	 *	@return a random tempo from 
	 */
	private int generateNumberOfBars()
	{
		return rand.nextInt(13)+4;
	}
	
	/**
	 *	Generates a random tempo from 60(Adagio) - 180(Presto)
	 *
	 *	@return a random tempo from 
	 */
	private int generateTimeSignatureNumerator()
	{
		return rand.nextInt(3)+2;
	}
	
	
}
