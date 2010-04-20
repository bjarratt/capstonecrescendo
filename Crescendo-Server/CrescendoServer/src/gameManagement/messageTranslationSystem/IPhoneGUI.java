package gameManagement.messageTranslationSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import keys.GameState;

public class IPhoneGUI extends JPanel implements ActionListener
{
	private String player;
	private gameManagement.messageTranslationSystem.ConnectionManager myCM;
	private JButton connect;
	private JButton disconnect;
	private JButton playNote;
	private JButton splashScreen;
	private JButton gameTypes;
	private JButton lengthTraining;
	private JButton pitchTraining;
	private JButton noteTraining;
	private JButton concertMaster;
	private JButton musicalIphones;
	private JButton notesAroundTheRoom;
	private JButton compTime;
	private JButton compose;
	private JButton gameInfo;
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
		
        this.setPreferredSize(new Dimension(220,720));
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

		gameTypes = new JButton("Got to Game Modes State");
		gameTypes.addActionListener(this);
		this.add(gameTypes);
		
		lengthTraining = new JButton("Length Training");
		lengthTraining.addActionListener(this);
		this.add(lengthTraining);

		pitchTraining = new JButton("Pitch Training");
		pitchTraining.addActionListener(this);
		this.add(pitchTraining);

		noteTraining = new JButton("Note Training");
		noteTraining.addActionListener(this);
		this.add(noteTraining);

		concertMaster = new JButton("Concert Master");
		concertMaster.addActionListener(this);
		this.add(concertMaster);

		musicalIphones = new JButton("Musical iPhones");
		musicalIphones.addActionListener(this);
		this.add(musicalIphones);

		notesAroundTheRoom = new JButton("Notes Around the Room");
		notesAroundTheRoom.addActionListener(this);
		this.add(notesAroundTheRoom);

		compTime = new JButton("Comp Time");
		compTime.addActionListener(this);
		this.add(compTime);

		compose = new JButton("Compose");
		compose.addActionListener(this);
		this.add(compose);

		gameInfo = new JButton("Go to Game Info State");
		gameInfo.addActionListener(this);
		this.add(gameInfo);

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
		if(e.getSource() == gameTypes)
			myCM.sendMessage(player + "_" + GameState.GAME_MODES);
		if(e.getSource() == pitchTraining)
			myCM.sendMessage(player + "_" + GameState.PITCH_TRAINING);
		if(e.getSource() == lengthTraining)
			myCM.sendMessage(player + "_" + GameState.LENGTH_TRAINING);
		if(e.getSource() == noteTraining)
			myCM.sendMessage(player + "_" + GameState.NOTE_TRAINING);
		if(e.getSource() == concertMaster)
			myCM.sendMessage(player + "_" + GameState.CONCERT_MASTER);
		if(e.getSource() == musicalIphones)
			myCM.sendMessage(player + "_" + GameState.MUSICAL_IPHONES);
		if(e.getSource() == notesAroundTheRoom)
			myCM.sendMessage(player + "_" + GameState.NOTES_AROUND_THE_ROOM);
		if(e.getSource() == compTime)
			myCM.sendMessage(player + "_" + GameState.COMP_TIME);
		if(e.getSource() == compose)
			myCM.sendMessage(player + "_" + GameState.COMPOSE);
		if(e.getSource() == gameInfo)
			myCM.sendMessage(player + "_" + GameState.GAME_INFO);
		if(e.getSource() == setTempo)
			myCM.sendMessage(player + "_" + GameState.SET_TEMPO);
		if(e.getSource() == setKey)
			myCM.sendMessage(player + "_" + GameState.SET_KEY);
		if(e.getSource() == setTimeSignature)
			myCM.sendMessage(player + "_" + GameState.SET_TIME_SIGNATURE);
		if(e.getSource() == setNumberOfBars)
			myCM.sendMessage(player + "_" + GameState.SET_NUMBER_OF_BARS);
		if(e.getSource() == play)
			myCM.sendMessage(player + "_" + GameState.PLAY);
		if(e.getSource() == pause)
			myCM.sendMessage(player + "_" + GameState.PAUSE);
		if(e.getSource() == review)
			myCM.sendMessage(player + "_" + GameState.REVIEW);
	}

	/**
	 *	Generates a random Note pitch to be used in a message sent to the server
	 *
	 *	@return a random Note pitch to be used in a message sent to the server
	 */
	private String generatePitch()
	{
		switch(rand.nextInt(15))
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
	
	
}
