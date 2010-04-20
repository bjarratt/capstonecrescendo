package gameManagement;

import gameManagement.messageTranslationSystem.MessageTranslationEngine;
import gameManagement.messageTranslationSystem.Beat;
import gameManagement.messageTranslationSystem.Note;
import gameManagement.messageTranslationSystem.Measure;
import gameManagement.windowManagement.WindowManager;
import gameManagement.windowManagement.base.Wrapper;
import gameManagement.windowManagement.windows.*;
import keys.GameState;
import keys.Players;
import logging.LogManager;

import java.util.ArrayList;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
 *	The GameManager houses the game loop, translates messages, and sends Notes to be displayed to the GUI
 *
 *	@author Travis Kosarek
 */
public class GameManager implements ActionListener
{
	private ArrayList<String> messagePool;
	private ArrayList<String> messages;
	private Timer timer;
	private Timer inGameTimer;

	//game options
	private static int tempo = 1000;
	private static String key = "CMajor";
	private static int numberOfBars = 3;
	private static int timeSignatureNumerator = 3;
	private static int timeSignatureDenominator = 4;
	private static int numberOfBeatsPerMeasure;;
	
	//gamestate options
	private boolean at_splash_screen;
	private boolean at_game_modes;
	private boolean at_game_info;
	private boolean at_game_options;
	private boolean at_play;
	private boolean at_pause;
	private boolean at_post_game;
	private boolean exit;

	private boolean player1IsDone;
	private boolean player2IsDone;
	private boolean player3IsDone;
	private boolean player4IsDone;
	
	//game
	private String gameMode;
	
	private String pausedPlayerId;
	
	private ArrayList<Beat> player1Beats;
	private ArrayList<Beat> player2Beats;
	private ArrayList<Beat> player3Beats;
	private ArrayList<Beat> player4Beats;
	private int currentTick;
	private int currentInGameTick;

	private ArrayList<Note> player1Notes;
	private ArrayList<Note> player2Notes;
	private ArrayList<Note> player3Notes;
	private ArrayList<Note> player4Notes;
	private ArrayList<Note> gameNotes;

	private int player1CurrentNote;
	private int player2CurrentNote;
	private int player3CurrentNote;
	private int player4CurrentNote;
	private int numberOfActivePlayers;

	private ArrayList<Measure> player1Measures;
	private ArrayList<Measure> player2Measures;
	private ArrayList<Measure> player3Measures;
	private ArrayList<Measure> player4Measures;

	ArrayList<Note> notesToSend; 
	
	Wrapper splash;
	Wrapper display;
	GameWindow myGameWindow;

	/**
	 *	Constructs a GameManager object with up to 4 players
	 */
	public GameManager()
	{
		//message pool
		messagePool = new ArrayList<String>();
		//messages to be translated
		messages = new ArrayList<String>();
		//set up a timer
		timer = new Timer(500,this);
		inGameTimer = new Timer(1000,this);

		player1CurrentNote = 0;
		player2CurrentNote = 0;
		player3CurrentNote = 0;
		player4CurrentNote = 0;

		currentTick = 0;
		currentInGameTick = 0;

		//at_splash_screen = true;
		at_game_modes = false;
		at_game_info = false;
		at_game_options = false;
		at_play = true;
		at_pause = false;
		at_post_game = false;
		exit = false;

		player1IsDone = false;
		player2IsDone = false;
		player3IsDone = false;
		player4IsDone = false;
		
		gameMode = GameState.PITCH_TRAINING;
		setGameMode(gameMode);
		
		pausedPlayerId = new String();
		
		player1Beats = new ArrayList<Beat>();
		player2Beats = new ArrayList<Beat>();
		player3Beats = new ArrayList<Beat>();
		player4Beats = new ArrayList<Beat>();

		player1Notes = new ArrayList<Note>();
		player2Notes = new ArrayList<Note>();
		player3Notes = new ArrayList<Note>();
		player4Notes = new ArrayList<Note>();
		numberOfActivePlayers = 2;

		setNumberOfBeatsPerMeasure();

		player1Measures = new ArrayList<Measure>();
		player1Measures.add(new Measure(numberOfBeatsPerMeasure));
		player2Measures = new ArrayList<Measure>();
		player2Measures.add(new Measure(numberOfBeatsPerMeasure));
		player3Measures = new ArrayList<Measure>();
		player3Measures.add(new Measure(numberOfBeatsPerMeasure));
		player4Measures = new ArrayList<Measure>();
		player4Measures.add(new Measure(numberOfBeatsPerMeasure));

		notesToSend = new ArrayList<Note>();

		//create a new display window
		splash = new Wrapper(new SplashScreen());
		splash.setBackground(Color.BLACK);
		splash.setSize(800, 600);
		WindowManager.getInstance().addWindow(keys.GameState.SPLASH_SCREEN, splash);
		
		myGameWindow = new GameWindow();
		myGameWindow.setPlayerCount(numberOfActivePlayers+1);
		
		
		display = new Wrapper(myGameWindow);
		display.setBackground(Color.BLACK);
		display.setSize(800, 600);
		WindowManager.getInstance().addWindow(gameMode, display);
		
		WindowManager.getInstance().run();
	}

	/**
	 *	Starts the game loop
	 */
	public void run()
	{
		System.out.println("*****\tAt the Splash Screen\t*****");
		try
		{
			WindowManager.getInstance().nextWindow(GameState.PITCH_TRAINING);
			myGameWindow.init();
			myGameWindow.start();
			for(Note note : gameNotes)
			{
				note.setPlayer(Players.PLAYER_ONE);
				myGameWindow.addNote(note);
			}
		}
		catch(InterruptedException e)
		{
			
		}
		timer.start();
	}

	/**
	 *	When a Timer ticks, this method is called and performs some action
	 */
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == timer)
		{
			if(exit)
				System.exit(0);
			if(messagePool.size() != 0)
			{
				
				//constrict message pool size every tick
				messages = new ArrayList<String>(messagePool);
				messagePool = new ArrayList<String>();
	
				//constrict one message per user per tick
				this.condenseMessagePool();
				//translate the messages
				this.translateMessagePool();
	
				if(at_play)
				{
					//if(currentBeat%2 == 0)
					//	metronomeNotes.add(new Note("CSharp7","eighth","metronome"));
					this.constructMeasures();
					this.sendNotesToDisplay();
					currentTick++;
				}
			}
		}
		else if(event.getSource() == inGameTimer)
		{
			sendTimeToDisplay(currentInGameTick);
			currentInGameTick++;
		}
	}

	/**
	 *	Adds messages to a pool waiting to be translated and sent to GUI
	 *
	 *	@param message the message to be translated
	 */
	public void addMessageToPool(String message)
	{
		messagePool.add(message);
	}

	/**
	 *	Limit the number of messages to be translated to one per player
	 */
	private void condenseMessagePool()
	{
		boolean p1 = false;
		boolean p2 = false;
		boolean p3 = false;
		boolean p4 = false;

		ArrayList<String> newMessages = new ArrayList<String>();

		for(String message : messages)
		{
			if(message.split("_")[0].equals(Players.PLAYER_ONE) && !p1)
			{
				p1 = true;
				newMessages.add(message);
			}
			if(message.split("_")[0].equals(Players.PLAYER_TWO) && !p2)
			{
				p2 = true;
				newMessages.add(message);
			}
			if(message.split("_")[0].equals(Players.PLAYER_THREE) && !p3)
			{
				p3 = true;
				newMessages.add(message);
			}
			if(message.split("_")[0].equals(Players.PLAYER_FOUR) && !p4)
			{
				p4 = true;
				newMessages.add(message);
			}
		}

		messages = newMessages;
	}

	/**
	 *	Translate the messages to Note objects and Message objects for easy manipulation
	 */
	private void translateMessagePool()
	{
		MessageTranslationEngine.Message m;

		for(String message : messages)
		{
			m = MessageTranslationEngine.translateMessage(message);

			if(at_play && m.isNote())
			{
				Note n = m.getNote();

				if(gameMode.equals(GameState.LENGTH_TRAINING))
				{
					if(n.getPlayer().equals(Players.PLAYER_ONE))
					{
						if(gameNotes.size()>player1CurrentNote)
						{
							if(n.getLength().equals(gameNotes.get(player1CurrentNote).getLength()))
							{
								player1Beats.addAll(n.getBeats());
								player1Notes.add(n);
							}
							else
							{
								//player1Beats.addAll(gameNotes.get(player1CurrentNote).getBeats());
								//player1Notes.add(new Note(n.getPitch(),gameNotes.get(player1CurrentNote).getLength(),Players.PLAYER_ONE));
								//player1Notes.get(player1Notes.size()-1).setCorrect(false);
								//send message to player that a wrong note was played
								//System.out.println("Player 1 played an incorrect note");
							}
						}
						else
						{
							player1IsDone = true;
						}
					}
					if(n.getPlayer().equals(Players.PLAYER_TWO))
					{
						if(gameNotes.size()>player2CurrentNote)
						{
							if(n.getLength().equals(gameNotes.get(player2CurrentNote).getLength()))
							{
								player2Beats.addAll(n.getBeats());
								player2Notes.add(new Note(n.getPitch(),n.getLength(),n.getPlayer()));
							}
							else
							{
								//player2Beats.addAll(gameNotes.get(player2CurrentNote).getBeats());
								//player2Notes.add(new Note(n.getPitch(),gameNotes.get(player2CurrentNote).getLength(),Players.PLAYER_TWO));
								//player2Notes.get(player2Notes.size()-1).setCorrect(false);
								//send message to player that a wrong note was played
								//System.out.println("Player 1 played an incorrect note");
							}
						}
						else
						{
							player2IsDone = true;
						}
					}
					if(n.getPlayer().equals(Players.PLAYER_THREE))
					{
						if(gameNotes.size()>player3CurrentNote)
						{
							if(n.getLength().equals(gameNotes.get(player3CurrentNote).getLength()))
							{
								player3Beats.addAll(n.getBeats());
								player3Notes.add(n);
							}
							else
							{
								player3Beats.addAll(gameNotes.get(player3CurrentNote).getBeats());
								player3Notes.add(new Note(n.getPitch(),gameNotes.get(player3CurrentNote).getLength(),Players.PLAYER_THREE));
								player3Notes.get(player3Notes.size()-1).setCorrect(false);
								//send message to player that a wrong note was played
								//System.out.println("Player 1 played an incorrect note");
							}
						}
						else
						{
							player3IsDone = true;
						}
					}
					if(n.getPlayer().equals(Players.PLAYER_FOUR))
					{
						if(gameNotes.size()>player4CurrentNote)
						{
							if(n.getLength().equals(gameNotes.get(player4CurrentNote).getLength()))
							{
								player4Beats.addAll(n.getBeats());
								player4Notes.add(n);
							}
							else
							{
								player4Beats.addAll(gameNotes.get(player4CurrentNote).getBeats());
								player4Notes.add(new Note(n.getPitch(),gameNotes.get(player4CurrentNote).getLength(),Players.PLAYER_FOUR));
								player4Notes.get(player4Notes.size()-1).setCorrect(false);
								//send message to player that a wrong note was played
								//System.out.println("Player 1 played an incorrect note");
							}
						}
						else
						{
							player4IsDone = true;
						}
					}
					
					/*if(n.getPlayer().equals(Players.PLAYER_ONE))
					{
						if(gameNotes.size()>player1CurrentNote)
						{
							if(n.getLength().equals(gameNotes.get(player1CurrentNote).getLength()))
							{
								player1Beats.addAll(n.getBeats());
								player1Notes.add(n);
							}
							else
							{
								player1Beats.addAll(gameNotes.get(player1CurrentNote).getBeats());
								player1Notes.add(new Note(n.getPitch(),gameNotes.get(player1CurrentNote).getLength(),Players.PLAYER_ONE));
								player1Notes.get(player1Notes.size()-1).setCorrect(false);
								//send message to player that a wrong note was played
								//System.out.println("Player 1 played an incorrect note");
							}
						}
						else
						{
							player1IsDone = true;
						}
					}
					if(n.getPlayer().equals(Players.PLAYER_TWO))
					{
						if(gameNotes.size()>player2CurrentNote)
						{
							if(n.getLength().equals(gameNotes.get(player2CurrentNote).getLength()))
							{
								player2Beats.addAll(n.getBeats());
								player2Notes.add(new Note(n.getPitch(),n.getLength(),n.getPlayer()));
							}
							else
							{
								player2Beats.addAll(gameNotes.get(player2CurrentNote).getBeats());
								player2Notes.add(new Note(n.getPitch(),gameNotes.get(player2CurrentNote).getLength(),Players.PLAYER_TWO));
								player2Notes.get(player2Notes.size()-1).setCorrect(false);
								//send message to player that a wrong note was played
								//System.out.println("Player 1 played an incorrect note");
							}
						}
						else
						{
							player2IsDone = true;
						}
					}
					if(n.getPlayer().equals(Players.PLAYER_THREE))
					{
						if(gameNotes.size()>player3CurrentNote)
						{
							if(n.getLength().equals(gameNotes.get(player3CurrentNote).getLength()))
							{
								player3Beats.addAll(n.getBeats());
								player3Notes.add(n);
							}
							else
							{
								player3Beats.addAll(gameNotes.get(player3CurrentNote).getBeats());
								player3Notes.add(new Note(n.getPitch(),gameNotes.get(player3CurrentNote).getLength(),Players.PLAYER_THREE));
								player3Notes.get(player3Notes.size()-1).setCorrect(false);
								//send message to player that a wrong note was played
								//System.out.println("Player 1 played an incorrect note");
							}
						}
						else
						{
							player3IsDone = true;
						}
					}
					if(n.getPlayer().equals(Players.PLAYER_FOUR))
					{
						if(gameNotes.size()>player4CurrentNote)
						{
							if(n.getLength().equals(gameNotes.get(player4CurrentNote).getLength()))
							{
								player4Beats.addAll(n.getBeats());
								player4Notes.add(n);
							}
							else
							{
								player4Beats.addAll(gameNotes.get(player4CurrentNote).getBeats());
								player4Notes.add(new Note(n.getPitch(),gameNotes.get(player4CurrentNote).getLength(),Players.PLAYER_FOUR));
								player4Notes.get(player4Notes.size()-1).setCorrect(false);
								//send message to player that a wrong note was played
								//System.out.println("Player 1 played an incorrect note");
							}
						}
						else
						{
							player4IsDone = true;
						}
					}*/
				}
				else if(gameMode.equals(GameState.PITCH_TRAINING))
				{
					if(n.getPlayer().equals(Players.PLAYER_ONE))
					{
						if(gameNotes.size()>player1CurrentNote)
						{
							if(n.getPitch().equals(gameNotes.get(player1CurrentNote).getPitch()))
							{
								player1Beats.addAll(n.getBeats());
								player1Notes.add(n);
							}
							else
							{
								//player1Beats.addAll(gameNotes.get(player1CurrentNote).getBeats());
								//player1Notes.add(new Note(n.getPitch(),gameNotes.get(player1CurrentNote).getLength(),Players.PLAYER_ONE));
								//player1Notes.get(player1Notes.size()-1).setCorrect(false);
								//send message to player that a wrong note was played
								//System.out.println("Player 1 played an incorrect note");
							}
						}
						else
						{
							player1IsDone = true;
						}
					}
					if(n.getPlayer().equals(Players.PLAYER_TWO))
					{
						if(gameNotes.size()>player2CurrentNote)
						{
							if(n.getPitch().equals(gameNotes.get(player2CurrentNote).getPitch()))
							{
								player2Beats.addAll(n.getBeats());
								player2Notes.add(n);
							}
							else
							{
								//player2Beats.addAll(gameNotes.get(player2CurrentNote).getBeats());
								//player2Notes.add(new Note(n.getPitch(),gameNotes.get(player2CurrentNote).getLength(),Players.PLAYER_TWO));
								//player2Notes.get(player2Notes.size()-1).setCorrect(false);
								//send message to player that a wrong note was played
								//System.out.println("Player 1 played an incorrect note");
							}
						}
						else
						{
							player2IsDone = true;
						}
					}
					if(n.getPlayer().equals(Players.PLAYER_THREE))
					{
						if(gameNotes.size()>player3CurrentNote)
						{
							if(n.getPitch().equals(gameNotes.get(player3CurrentNote).getPitch()))
							{
								player3Beats.addAll(n.getBeats());
								player3Notes.add(n);
							}
							else
							{
								player3Beats.addAll(gameNotes.get(player3CurrentNote).getBeats());
								player3Notes.add(new Note(n.getPitch(),gameNotes.get(player3CurrentNote).getLength(),Players.PLAYER_THREE));
								player3Notes.get(player3Notes.size()-1).setCorrect(false);
								//send message to player that a wrong note was played
								//System.out.println("Player 1 played an incorrect note");
							}
						}
						else
						{
							player3IsDone = true;
						}
					}
					if(n.getPlayer().equals(Players.PLAYER_FOUR))
					{
						if(gameNotes.size()>player4CurrentNote)
						{
							if(n.getPitch().equals(gameNotes.get(player4CurrentNote).getPitch()))
							{
								player4Beats.addAll(n.getBeats());
								player4Notes.add(n);
							}
							else
							{
								player4Beats.addAll(gameNotes.get(player4CurrentNote).getBeats());
								player4Notes.add(new Note(n.getPitch(),gameNotes.get(player4CurrentNote).getLength(),Players.PLAYER_FOUR));
								player4Notes.get(player4Notes.size()-1).setCorrect(false);
								//send message to player that a wrong note was played
								//System.out.println("Player 1 played an incorrect note");
							}
						}
						else
						{
							player4IsDone = true;
						}
					}
				}
				else if(gameMode.equals(GameState.NOTE_TRAINING))
				{
					if(n.getPlayer().equals(Players.PLAYER_ONE))
					{
						if(gameNotes.size()>player1CurrentNote)
						{
							if(n.equals(gameNotes.get(player1CurrentNote)))
							{
								player1Beats.addAll(n.getBeats());
								player1Notes.add(n);
							}
							else
							{
								//player1Beats.addAll(gameNotes.get(player1CurrentNote).getBeats());
								//player1Notes.add(new Note(n.getPitch(),gameNotes.get(player1CurrentNote).getLength(),Players.PLAYER_ONE));
								//player1Notes.get(player1Notes.size()-1).setCorrect(false);
								//send message to player that a wrong note was played
								//System.out.println("Player 1 played an incorrect note");
							}
						}
						else
						{
							player1IsDone = true;
						}
					}
					if(n.getPlayer().equals(Players.PLAYER_TWO))
					{
						if(gameNotes.size()>player2CurrentNote)
						{
							if(n.equals(gameNotes.get(player2CurrentNote)))
							{
								player2Beats.addAll(n.getBeats());
								player2Notes.add(n);
							}
							else
							{
								//player2Beats.addAll(gameNotes.get(player2CurrentNote).getBeats());
								//player2Notes.add(new Note(n.getPitch(),gameNotes.get(player2CurrentNote).getLength(),Players.PLAYER_TWO));
								//player2Notes.get(player2Notes.size()-1).setCorrect(false);
								//send message to player that a wrong note was played
								//System.out.println("Player 1 played an incorrect note");
							}
						}
						else
						{
							player2IsDone = true;
						}
					}
					if(n.getPlayer().equals(Players.PLAYER_THREE))
					{
						if(gameNotes.size()>player3CurrentNote)
						{
							if(n.equals(gameNotes.get(player3CurrentNote)))
							{
								player3Beats.addAll(n.getBeats());
								player3Notes.add(n);
							}
							else
							{
								player3Beats.addAll(gameNotes.get(player3CurrentNote).getBeats());
								player3Notes.add(new Note(n.getPitch(),gameNotes.get(player3CurrentNote).getLength(),Players.PLAYER_THREE));
								player3Notes.get(player3Notes.size()-1).setCorrect(false);
								//send message to player that a wrong note was played
								//System.out.println("Player 1 played an incorrect note");
							}
						}
						else
						{
							player3IsDone = true;
						}
					}
					if(n.getPlayer().equals(Players.PLAYER_FOUR))
					{
						if(gameNotes.size()>player4CurrentNote)
						{
							if(n.equals(gameNotes.get(player4CurrentNote)))
							{
								player4Beats.addAll(n.getBeats());
								player4Notes.add(n);
							}
							else
							{
								player4Beats.addAll(gameNotes.get(player4CurrentNote).getBeats());
								player4Notes.add(new Note(n.getPitch(),gameNotes.get(player4CurrentNote).getLength(),Players.PLAYER_FOUR));
								player4Notes.get(player4Notes.size()-1).setCorrect(false);
								//send message to player that a wrong note was played
								//System.out.println("Player 1 played an incorrect note");
							}
						}
						else
						{
							player4IsDone = true;
						}
					}
				}
					
					//TODO add other games here
			}
			else
			{	
				//     DISCONNECT     //
				if(m.getMessage().split("_")[1].equals("disconnect"))
				{
					at_splash_screen = true;
					at_game_modes = false;
					at_game_info = false;
					at_game_options = false;
					at_play = false;
					at_pause = false;
					at_post_game = false;
					exit = false;
					inGameTimer.stop();
					sendMessageToDisplay(m.getMessage());
					sendMessageToDisplay(Players.PLAYER_ONE + "_" + GameState.DISCONNECT);
					sendMessageToDisplay(Players.PLAYER_TWO + "_" + GameState.DISCONNECT);
					sendMessageToDisplay(Players.PLAYER_THREE + "_" + GameState.DISCONNECT);
					sendMessageToDisplay(Players.PLAYER_FOUR + "_" + GameState.DISCONNECT);
					numberOfActivePlayers=0;
					
					setNumberOfBeatsPerMeasure();
					
					//clear other game state
					player1Beats = new ArrayList<Beat>();
					player2Beats = new ArrayList<Beat>();
					player3Beats = new ArrayList<Beat>();
					player4Beats = new ArrayList<Beat>();
					player1Notes = new ArrayList<Note>();
					player2Notes = new ArrayList<Note>();
					player3Notes = new ArrayList<Note>();
					player4Notes = new ArrayList<Note>();
					player1Measures = new ArrayList<Measure>();
					player1Measures.add(new Measure(numberOfBeatsPerMeasure));
					player2Measures = new ArrayList<Measure>();
					player2Measures.add(new Measure(numberOfBeatsPerMeasure));
					player3Measures = new ArrayList<Measure>();
					player3Measures.add(new Measure(numberOfBeatsPerMeasure));
					player4Measures = new ArrayList<Measure>();
					player4Measures.add(new Measure(numberOfBeatsPerMeasure));
					player1CurrentNote = 0;
					player2CurrentNote = 0;
					player3CurrentNote = 0;
					player4CurrentNote = 0;
					
					player1IsDone = false;
					player2IsDone = false;
					player3IsDone = false;
					player4IsDone = false;
					
					messagePool = new ArrayList<String>();
					messages = new ArrayList<String>();

					currentTick = 0;
					currentInGameTick = 0;

					pausedPlayerId = new String();

					notesToSend = new ArrayList<Note>();
					
					try
					{
						WindowManager.getInstance().nextWindow(GameState.SPLASH_SCREEN);
					}
					catch(InterruptedException e)
					{
						
					}
					
					System.out.println("All players have been disconnected");
					System.out.println("*****\tAt the Splash Screen\t*****");
				}
				
				//     SPLASH SCREEN     //
				//message is "playerX_connect"
				else if(at_splash_screen && m.getMessage().split("_")[1].equals("connect"))
				{
					//numberOfActivePlayers++;
					sendMessageToDisplay(m.getMessage());
				}
				//message is "player1_gamemodes"  (checking to make sure player 1 has sent the message)
				else if(at_splash_screen && numberOfActivePlayers>0 && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.PLAY))
				{
					try
					{
						WindowManager.getInstance().nextWindow(GameState.PITCH_TRAINING);
						myGameWindow.init();
						myGameWindow.start();
						for(Note note : gameNotes)
						{
							note.setPlayer(Players.PLAYER_ONE);
							myGameWindow.addNote(note);
						}
					}
					catch(InterruptedException e)
					{
						
					}
					System.out.println("*****\tAt the Game Modes Screen\t*****");
					
					at_splash_screen = false;
					//at_game_modes = true;
					at_play = true;
					sendActivePlayers();
					sendMessageToDisplay(m.getMessage());
				}
			/*	
				//     GAME MODES     //
				//message is "player1_splashscreen"  (checking to make sure player 1 has sent the message)
				else if(at_game_modes && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.SPLASH_SCREEN))
				{
					System.out.println("*****\tAt the Splash Screen\t*****");
					at_game_modes = false;
					at_splash_screen = true;
					sendMessageToDisplay(m.getMessage());
				}
				//message is "player1_lengthtraining"  (checking to make sure player 1 has sent the message)
				else if(at_game_modes && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.LENGTH_TRAINING))
				{
					setGameMode(GameState.LENGTH_TRAINING);
					System.out.println("*****\tAt the Game Options Screen\t*****");
					//System.out.println("*****\tAt the Game Info Screen\t*****");
					at_game_modes = false;
					at_game_options = true;
					//temporary removal of game info
					//at_game_info = true;
					sendMessageToDisplay(m.getMessage());
				}
				//message is "player1_pitchtraining"  (checking to make sure player 1 has sent the message)
				else if(at_game_modes && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.PITCH_TRAINING))
				{
					setGameMode(GameState.PITCH_TRAINING);
					System.out.println("*****\tAt the Game Options Screen\t*****");
					//System.out.println("*****\tAt the Game Info Screen\t*****");
					at_game_modes = false;
					at_game_options = true;
					//temporary removal of game info
					//at_game_info = true;
					sendMessageToDisplay(m.getMessage());
				}
				//message is "player1_notetraining"  (checking to make sure player 1 has sent the message)
				else if(at_game_modes && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.NOTE_TRAINING))
				{
					setGameMode(GameState.NOTE_TRAINING);
					System.out.println("*****\tAt the Game Options Screen\t*****");
					//System.out.println("*****\tAt the Game Info Screen\t*****");
					at_game_modes = false;
					at_game_options = true;
					//temporary removal of game info
					//at_game_info = true;
					sendMessageToDisplay(m.getMessage());
				}
				//message is "player1_concertmaster"  (checking to make sure player 1 has sent the message)
				else if(at_game_modes && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.CONCERT_MASTER))
				{
					setGameMode(GameState.CONCERT_MASTER);
					System.out.println("*****\tAt the Game Options Screen\t*****");
					//System.out.println("*****\tAt the Game Info Screen\t*****");
					at_game_modes = false;
					at_game_options = true;
					//temporary removal of game info
					//at_game_info = true;
					sendMessageToDisplay(m.getMessage());
				}
				//message is "player1_musicaliphones"  (checking to make sure player 1 has sent the message)
				else if(at_game_modes && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.MUSICAL_IPHONES))
				{
					setGameMode(GameState.MUSICAL_IPHONES);
					System.out.println("*****\tAt the Game Options Screen\t*****");
					//System.out.println("*****\tAt the Game Info Screen\t*****");
					at_game_modes = false;
					at_game_options = true;
					//temporary removal of game info
					//at_game_info = true;
					sendMessageToDisplay(m.getMessage());
				}
				//message is "player1_notesaroundtheroom"  (checking to make sure player 1 has sent the message)
				else if(at_game_modes && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.NOTES_AROUND_THE_ROOM))
				{
					setGameMode(GameState.NOTES_AROUND_THE_ROOM);
					System.out.println("*****\tAt the Game Options Screen\t*****");
					//System.out.println("*****\tAt the Game Info Screen\t*****");
					at_game_modes = false;
					at_game_options = true;
					//temporary removal of game info
					//at_game_info = true;
					sendMessageToDisplay(m.getMessage());
				}
				//message is "player1_comptime"  (checking to make sure player 1 has sent the message)
				else if(at_game_modes && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.COMP_TIME))
				{
					setGameMode(GameState.COMP_TIME);
					System.out.println("*****\tAt the Game Options Screen\t*****");
					//System.out.println("*****\tAt the Game Info Screen\t*****");
					at_game_modes = false;
					at_game_options = true;
					//temporary removal of game info
					//at_game_info = true;
					sendMessageToDisplay(m.getMessage());
				}
				//message is "player1_compose"  (checking to make sure player 1 has sent the message)
				else if(at_game_modes && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.COMPOSE))
				{
					setGameMode(GameState.COMPOSE);
					System.out.println("*****\tAt the Game Options Screen\t*****");
					//System.out.println("*****\tAt the Game Info Screen\t*****");
					at_game_modes = false;
					at_game_options = true;
					//temporary removal of game info
					//at_game_info = true;
					sendMessageToDisplay(m.getMessage());
				}
				*/
				//     GAME INFO     //
				//message is "player1_gametypes"  (checking to make sure player 1 has sent the message)
				/*else if(at_game_info && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.GAME_MODES))
				{
					System.out.println("*****\tAt the Game Modes Screen\t*****");
					at_game_info = false;
					at_game_modes = true;
					sendMessageToDisplay(m.getMessage());
				}
				//message is "player1_gameoptions"  (checking to make sure player 1 has sent the message)
				else if(at_game_info && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.GAME_OPTIONS))
				{
					System.out.println("*****\tAt the Game Options Screen\t*****");
					at_game_info = false;
					at_game_options = true;
					sendMessageToDisplay(m.getMessage());
				}
				*/
				/*
				//     GAME OPTIONS     //
				//message is "player1_gameinfo"  (checking to make sure player 1 has sent the message)
				else if(at_game_options && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.GAME_MODES))//(GameState.GAME_INFO))
				{
					System.out.println("*****\tAt the Game Modes Screen\t*****");
					//System.out.println("*****\tAt the Game Info Screen\t*****");
					at_game_options = false;
					at_game_modes = true;
					//temporary removal of game info
					//at_game_info = true;
					sendMessageToDisplay(m.getMessage());
				}
				//message is "player1_play"  (checking to make sure player 1 has sent the message)
				else if(at_game_options && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.PLAY))
				{
					System.out.println("*****\tAt the Play Screen\t*****");
					sendGameNotes();
					at_game_options = false;
					at_play = true;
					inGameTimer.start();
					sendMessageToDisplay(m.getMessage());
				}
				*/
				//     PLAY     //
				//message is "playerX_pause"
			/*	else if(at_play && m.getMessage().split("_")[1].equals(GameState.PAUSE))
				{
					System.out.println("*****\tAt the Pause Screen\t*****");
					at_play = false;
					at_pause = true;
					inGameTimer.stop();
					pausedPlayerId = new String(m.getMessage().split("_")[0]);
					sendMessageToDisplay(m.getMessage());
				}*/
				//TODO Player 1 should not send this message it should just continue to Post Game
				//message is "player1_postgame"  (checking to make sure player 1 has sent the message)
				else if(at_play && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.POST_GAME))
				{
					//System.out.println("*****\tAt the Post Game Screen\t*****");
					System.out.println("*****\tAt the Splash Screen\t*****");
					try
					{
						myGameWindow.stop();
						WindowManager.getInstance().nextWindow(GameState.SPLASH_SCREEN);
					}
					catch(InterruptedException e)
					{
						
					}
					at_play = false;
					//at_post_game = true;
					at_splash_screen = true;
					inGameTimer.stop();
					sendMessageToDisplay(m.getMessage());
				}

				//     PAUSE     //
				//message is "playerX_play"  (checking to make sure player 1 has sent the message)
			/*	else if(at_pause && m.getMessage().split("_")[1].equals(GameState.PLAY) && m.getMessage().split("_")[0].equals(pausedPlayerId))
				{
					System.out.println("*****\tAt the Play Screen\t*****");
					at_play = true;
					at_pause = false;
					sendMessageToDisplay(m.getMessage());
					inGameTimer.start();
				}
				
				//     POST GAME     //
				//message is "player1_splashscreen"  (checking to make sure player 1 has sent the message)
				else if(at_post_game && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.SPLASH_SCREEN))
				{
					System.out.println("*****\tAt the Splash Screen\t*****");
					at_post_game = false;
					at_splash_screen = true;
					
					setNumberOfBeatsPerMeasure();
					
					//clear all records
					player1Beats = new ArrayList<Beat>();
					player2Beats = new ArrayList<Beat>();
					player3Beats = new ArrayList<Beat>();
					player4Beats = new ArrayList<Beat>();
					player1Notes = new ArrayList<Note>();
					player2Notes = new ArrayList<Note>();
					player3Notes = new ArrayList<Note>();
					player4Notes = new ArrayList<Note>();
					player1Measures = new ArrayList<Measure>();
					player1Measures.add(new Measure(numberOfBeatsPerMeasure));
					player2Measures = new ArrayList<Measure>();
					player2Measures.add(new Measure(numberOfBeatsPerMeasure));
					player3Measures = new ArrayList<Measure>();
					player3Measures.add(new Measure(numberOfBeatsPerMeasure));
					player4Measures = new ArrayList<Measure>();
					player4Measures.add(new Measure(numberOfBeatsPerMeasure));
					player1CurrentNote = 0;
					player2CurrentNote = 0;
					player3CurrentNote = 0;
					player4CurrentNote = 0;
					
					player1IsDone = false;
					player2IsDone = false;
					player3IsDone = false;
					player4IsDone = false;
					
					messagePool = new ArrayList<String>();
					messages = new ArrayList<String>();

					currentTick = 0;
					currentInGameTick = 0;

					pausedPlayerId = new String();
					
					int oldNumberOfPlayers = numberOfActivePlayers;
					numberOfActivePlayers = 0;

					notesToSend = new ArrayList<Note>();
					
					for(int i = 0; i<oldNumberOfPlayers; i++)
					{
						addMessageToPool("player" + (i+1) + "_" + GameState.CONNECT);
					}
					
					sendMessageToDisplay(m.getMessage());
				}
				*/
				else if(m.getMessage().split("_")[1].equals(GameState.EXIT))
				{
					exit = true;
					sendMessageToDisplay(m.getMessage());
				}
				else
				{
					System.out.println("GameManager ignored message: " + m.getMessage());
				}
			}
		}
	}



	/**
	 *	From the Note's played by each player, correctly formated Measures are created
	 */
	private void constructMeasures()
	{
		Note lastNote;
		ArrayList<Beat> lastNoteBeats;
		Note firstNote;
		ArrayList<Beat> firstNoteBeats;
		
		Measure measure;
		
		if(numberOfActivePlayers == 1 && player1IsDone)
		{
			addMessageToPool(Players.PLAYER_ONE + "_" + GameState.POST_GAME);
		}
		else if(numberOfActivePlayers == 2 && player1IsDone && player2IsDone)
		{
			addMessageToPool(Players.PLAYER_ONE + "_" + GameState.POST_GAME);
		}
		else if(numberOfActivePlayers == 3 && player1IsDone && player2IsDone && player3IsDone)
		{
			addMessageToPool(Players.PLAYER_ONE + "_" + GameState.POST_GAME);
		}
		else if(numberOfActivePlayers == 4 && player1IsDone && player2IsDone && player3IsDone && player4IsDone)
		{
			addMessageToPool(Players.PLAYER_ONE + "_" + GameState.POST_GAME);
		}
		
		
		notesToSend = new ArrayList<Note>();
		////	Player 1	////
		//we have reached the end of a measure, so add a new one
		if(!(player1Measures.get(player1Measures.size()-1).getNumberOfAvailableBeats()>0))
		{
			player1Measures.add(new Measure(numberOfBeatsPerMeasure));
		}

		measure = player1Measures.get(player1Measures.size()-1);
		
		if(player1Notes.size()>0 && player1Notes.size()>player1CurrentNote)
		{			
			measure.addNote(player1Notes.get(player1CurrentNote));
			notesToSend.add(player1Notes.get(player1CurrentNote));
			
			player1CurrentNote++;

			/* Taken out until note ties are added back in 
			//if a note can be simply added to the measure, then do it
			if(measure.canAddNote(player1Notes.get(player1Notes.size()-1)))
			{
				measure.addNote(player1Notes.get(player1Notes.size()-1));
				notesToSend.add(player1Notes.get(player1Notes.size()-1));
			}
			else
			{
				lastNoteBeats = new ArrayList<Beat>();
				firstNoteBeats = new ArrayList<Beat>();

				//add a note for the rest of the measure
				for(int i = 0; i < measure.getNumberOfAvailableBeats(); i++)
					lastNoteBeats.add(new Beat(player1Beats.get(currentBeat).getPitch()));
				lastNote = new Note(lastNoteBeats, Players.PLAYER_ONE);
				lastNote.setTiedRight(true);
				measure.addNote(lastNote);
				notesToSend.add(lastNote);

				//add a new measure and update the current measure
				player1Measures.add(new Measure(numberOfBeatsPerMeasure));
				measure = player1Measures.get(player1Measures.size()-1);

				//add the rest of the note carried over from the previous measure
				for(int i = 0; i < (player1Notes.get(player1Notes.size()-1).size() - lastNote.size()); i++)
					firstNoteBeats.add(new Beat(player1Beats.get(currentBeat).getPitch()));
				firstNote = new Note(firstNoteBeats, Players.PLAYER_ONE);
				firstNote.setTiedLeft(true);
				measure.addNote(firstNote);
				notesToSend.add(firstNote);
			}*/
		}
		////	Player 1	////

		////	Player 2	////
		//we have reached the end of a measure, so add a new one
		if(!(player2Measures.get(player2Measures.size()-1).getNumberOfAvailableBeats()>0))
		{
			player2Measures.add(new Measure(numberOfBeatsPerMeasure));
		}

		measure = player2Measures.get(player2Measures.size()-1);
		
		if(player2Notes.size()>0 && player2Notes.size()>player2CurrentNote)
		{			
			measure.addNote(player2Notes.get(player2CurrentNote));
			notesToSend.add(player2Notes.get(player2CurrentNote));
			
			player2CurrentNote++;
			
			/* Taken out until note ties are added back in 
			//if a note can be simply added to the measure, then do it
			if(measure.canAddNote(player2Notes.get(player2Notes.size()-1)))
			{
				measure.addNote(player2Notes.get(player2Notes.size()-1));
				notesToSend.add(player2Notes.get(player2Notes.size()-1));
			}
			else
			{
				lastNoteBeats = new ArrayList<Beat>();
				firstNoteBeats = new ArrayList<Beat>();

				//add a note for the rest of the measure
				for(int i = 0; i < measure.getNumberOfAvailableBeats(); i++)
					lastNoteBeats.add(new Beat(player2Beats.get(currentBeat).getPitch()));
				lastNote = new Note(lastNoteBeats, Players.PLAYER_TWO);
				lastNote.setTiedRight(true);
				measure.addNote(lastNote);
				notesToSend.add(lastNote);

				//add a new measure and update the current measure
				player2Measures.add(new Measure(numberOfBeatsPerMeasure));
				measure = player2Measures.get(player2Measures.size()-1);

				//add the rest of the note carried over from the previous measure
				for(int i = 0; i < (player2Notes.get(player2Notes.size()-1).size() - lastNote.size()); i++)
					firstNoteBeats.add(new Beat(player2Beats.get(currentBeat).getPitch()));
				firstNote = new Note(firstNoteBeats, Players.PLAYER_TWO);
				firstNote.setTiedLeft(true);
				measure.addNote(firstNote);
				notesToSend.add(firstNote);
			}*/
		}
		////	Player 2	////

		////	Player 3	////
		//we have reached the end of a measure, so add a new one
		if(!(player3Measures.get(player3Measures.size()-1).getNumberOfAvailableBeats()>0))
		{
			player3Measures.add(new Measure(numberOfBeatsPerMeasure));
		}

		measure = player3Measures.get(player3Measures.size()-1);

		if(player3Notes.size()>0 && player3Notes.size()>player3CurrentNote)
		{
			measure.addNote(player3Notes.get(player3CurrentNote));
			notesToSend.add(player3Notes.get(player3CurrentNote));

			player3CurrentNote++;
			
			/* Taken out until note ties are added back in 
			//if a note can be simply added to the measure, then do it
			if(measure.canAddNote(player3Notes.get(player3Notes.size()-1)))
			{
				measure.addNote(player3Notes.get(player3Notes.size()-1));
				notesToSend.add(player3Notes.get(player3Notes.size()-1));
			}
			else
			{
				lastNoteBeats = new ArrayList<Beat>();
				firstNoteBeats = new ArrayList<Beat>();

				//add a note for the rest of the measure
				for(int i = 0; i < measure.getNumberOfAvailableBeats(); i++)
					lastNoteBeats.add(new Beat(player3Beats.get(currentBeat).getPitch()));
				lastNote = new Note(lastNoteBeats, Players.PLAYER_THREE);
				lastNote.setTiedRight(true);
				measure.addNote(lastNote);
				notesToSend.add(lastNote);

				//add a new measure and update the current measure
				player3Measures.add(new Measure(numberOfBeatsPerMeasure));
				measure = player3Measures.get(player3Measures.size()-1);

				//add the rest of the note carried over from the previous measure
				for(int i = 0; i < (player3Notes.get(player3Notes.size()-1).size() - lastNote.size()); i++)
					firstNoteBeats.add(new Beat(player3Beats.get(currentBeat).getPitch()));
				firstNote = new Note(firstNoteBeats, Players.PLAYER_THREE);
				firstNote.setTiedLeft(true);
				measure.addNote(firstNote);
				notesToSend.add(firstNote);
			} */
		}
		////	Player 3	////

		////	Player 4	////
		//we have reached the end of a measure, so add a new one
		if(!(player4Measures.get(player4Measures.size()-1).getNumberOfAvailableBeats()>0))
		{
			player4Measures.add(new Measure(numberOfBeatsPerMeasure));
		}

		measure = player4Measures.get(player4Measures.size()-1);

		if(player4Notes.size()>0 && player4Notes.size()>player4CurrentNote)
		{			
			measure.addNote(player4Notes.get(player4CurrentNote));
			notesToSend.add(player4Notes.get(player4CurrentNote));

			player4CurrentNote++;
			
			/* Taken out until note ties are added back in 
			//if a note can be simply added to the measure, then do it
			if(measure.canAddNote(player4Notes.get(player4Notes.size()-1)))
			{
				measure.addNote(player4Notes.get(player4Notes.size()-1));
				notesToSend.add(player4Notes.get(player4Notes.size()-1));
			}
			else
			{
				lastNoteBeats = new ArrayList<Beat>();
				firstNoteBeats = new ArrayList<Beat>();

				//add a note for the rest of the measure
				for(int i = 0; i < measure.getNumberOfAvailableBeats(); i++)
					lastNoteBeats.add(new Beat(player4Beats.get(currentBeat).getPitch()));
				lastNote = new Note(lastNoteBeats, Players.PLAYER_FOUR);
				lastNote.setTiedRight(true);
				measure.addNote(lastNote);
				notesToSend.add(lastNote);

				//add a new measure and update the current measure
				player4Measures.add(new Measure(numberOfBeatsPerMeasure));
				measure = player4Measures.get(player4Measures.size()-1);

				//add the rest of the note carried over from the previous measure
				for(int i = 0; i < (player4Notes.get(player4Notes.size()-1).size() - lastNote.size()); i++)
					firstNoteBeats.add(new Beat(player4Beats.get(currentBeat).getPitch()));
				firstNote = new Note(firstNoteBeats, Players.PLAYER_FOUR);
				firstNote.setTiedLeft(true);
				measure.addNote(firstNote);
				notesToSend.add(firstNote);
			}*/
		}
		////	Player 4	////
	}
	
	private void setNumberOfBeatsPerMeasure()
	{
		if(timeSignatureDenominator == 2)
			numberOfBeatsPerMeasure = timeSignatureNumerator*4;
		else if(timeSignatureDenominator == 4)
			numberOfBeatsPerMeasure = timeSignatureNumerator*2;
		else if(timeSignatureDenominator == 8)
			numberOfBeatsPerMeasure = timeSignatureNumerator;
		else
			numberOfBeatsPerMeasure = 8;
	}
	
	private void setGameMode(String game)
	{
		gameMode = new String(game);
		//TODO add gameNotes for each different game mode that requires them		
		if(gameMode.equals(GameState.LENGTH_TRAINING))
			gameNotes = new gameManagement.gameModes.LengthTraining(timeSignatureNumerator*2,numberOfBars).getNotes();
		else if(gameMode.equals(GameState.PITCH_TRAINING))
			gameNotes = new gameManagement.gameModes.PitchTraining(timeSignatureNumerator*2,numberOfBars).getNotes();
		else if(gameMode.equals(GameState.NOTE_TRAINING))
			gameNotes = new gameManagement.gameModes.NoteTraining(timeSignatureNumerator*2,numberOfBars).getNotes();
		else
			gameNotes = null;
	}
	
	private void setTempo(int t)
	{
		tempo = t;
		//TODO send message to Display
	}
	
	private void setKey(String k)
	{
		key = k;
		//TODO send message to Display
	}
	
	private void setNumberOfBars(int n)
	{
		numberOfBars = n;
		//TODO send message to Display
	}
	
	private void setTimeSignature(int n, int d)
	{
		timeSignatureNumerator = n;
		timeSignatureDenominator = d;
		//TODO send message to Display
	}
	
	/**
	 * Sends the number of active players to the public display
	 */
	private void sendActivePlayers()
	{
		//numberofActivePlayers;
		//TODO send message to Display
	}

	/**
	 * Sends the gameNotes to the public display for visual reasons, notes are checked in the GameManager
	 */
	private void sendGameNotes()
	{
		//TODO send message to Display
		//send gameNotes to the public display
	}
	
	/**
	 *	Sends a message to the GUI
	 *
	 *	@param message the message to be sent to the GUI
	 */
	private void sendMessageToDisplay(String message)
	{
		System.out.println("GameManager sending message to GUI:\t" + message);
		// TODO This method still needs to be somewhere
		//displayGUI.receiveMessage(message);
	}

	private void sendTimeToDisplay(int time)
	{
	//	System.out.println(time + " seconds have passed in game");
		//TODO send message to Display
	}
	
	/**
	 *	Sends a Note to the GUI
	 *
	 *	@param note the Note to be sent to the GUI
	 */
	private void sendNotesToDisplay()
	{
		// TODO This method still needs to be somewhere
		//displayGUI.getNotes(notesToSend, currentBeat);
	//	for(Note note : notesToSend)
	//		System.out.println(note.getPlayer() + " played " + note);
	//	System.out.println();
		for(Note note : notesToSend)
		{
			if(note.getPlayer().equals(Players.PLAYER_TWO))
			{
				note.setPlayer(Players.PLAYER_THREE);
				myGameWindow.addNote(note);
				LogManager.getInstance().writeLogEntry(Players.PLAYER_THREE, note.toString());
			}
			else if(note.getPlayer().equals(Players.PLAYER_ONE))
			{
				note.setPlayer(Players.PLAYER_TWO);
				myGameWindow.addNote(note);
				LogManager.getInstance().writeLogEntry(Players.PLAYER_ONE, note.toString());
			}
			else if(note.getPlayer().equals(GameState.PITCH_TRAINING))
			{
				note.setPlayer(Players.PLAYER_ONE);
				myGameWindow.addNote(note);
				LogManager.getInstance().writeLogEntry(GameState.PITCH_TRAINING, note.toString());
			}
		}
		notesToSend = new ArrayList<Note>();
	}
	
}