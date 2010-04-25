package gameManagement;

import gameManagement.messageTranslationSystem.MessageTranslationEngine;
import gameManagement.messageTranslationSystem.Beat;
import gameManagement.messageTranslationSystem.Note;
import gameManagement.messageTranslationSystem.Measure;
import gameManagement.windowManagement.WindowManager;
import gameManagement.windowManagement.base.Wrapper;
import gameManagement.windowManagement.windows.*;
import keys.GameState;
import keys.Lengths;
import keys.Players;

import java.util.ArrayList;
import javax.swing.Timer;

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
	private static int numberOfBars = 4;
	private static int timeSignatureNumerator = 4;
	private static int timeSignatureDenominator = 4;
	private static int numberOfBeatsPerMeasure;
	
	//gamestate options
	private boolean at_splash_screen;
	private boolean at_game_modes;
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
	private ArrayList<String> keyProgression;
	
	private String pausedPlayerId;
	
	private ArrayList<Beat> player1Beats;
	private ArrayList<Beat> player2Beats;
	private ArrayList<Beat> player3Beats;
	private ArrayList<Beat> player4Beats;
	private int currentTick;
	private int currentInGameTick;
	private int initialGameTime;

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
	
//	Wrapper display;
//	GameWindow myGameWindow;
//	
//	Wrapper modes;
//	GameModesWindow myGameModesWindow;
//	
//	Wrapper pause;
//	PauseScreen myPauseScreen;
//	
//	Wrapper splash;
//	SplashScreen mySplashScreen;

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
		timer = new Timer(250,this);
		inGameTimer = new Timer(1000,this);

		player1CurrentNote = 0;
		player2CurrentNote = 0;
		player3CurrentNote = 0;
		player4CurrentNote = 0;

		currentTick = 0;
		currentInGameTick = 0;
		initialGameTime = 30;

		at_splash_screen = true;
		at_game_modes = false;
		at_game_options = false;
		at_play = false;
		at_pause = false;
		at_post_game = false;
		exit = false;

		player1IsDone = false;
		player2IsDone = false;
		player3IsDone = false;
		player4IsDone = false;
		
		notesToSend = new ArrayList<Note>();
		
		gameMode = GameState.NOTE_TRAINING;
		
		pausedPlayerId = new String();
		
		player1Beats = new ArrayList<Beat>();
		player2Beats = new ArrayList<Beat>();
		player3Beats = new ArrayList<Beat>();
		player4Beats = new ArrayList<Beat>();

		player1Notes = new ArrayList<Note>();
		player2Notes = new ArrayList<Note>();
		player3Notes = new ArrayList<Note>();
		player4Notes = new ArrayList<Note>();
		numberOfActivePlayers = 0;

		player1Measures = new ArrayList<Measure>();
		player2Measures = new ArrayList<Measure>();
		player3Measures = new ArrayList<Measure>();
		player4Measures = new ArrayList<Measure>();


		//create a new splash screen
//		mySplashScreen = new SplashScreen();
//		splash = new Wrapper(mySplashScreen);
//		splash.setBackground(Color.BLACK);
//		splash.setSize(800, 600);
//		WindowManager.getInstance().addWindow(keys.GameState.SPLASH_SCREEN, splash);
//		
//		//create a new game modes screen
//		myGameModesWindow = new GameModesWindow();
//		//myGameModesWindow.addGameType(GameState.LENGTH_TRAINING);
//		//myGameModesWindow.addGameType(GameState.PITCH_TRAINING);
//		//myGameModesWindow.addGameType(GameState.NOTE_TRAINING);
//		modes = new Wrapper(myGameModesWindow);
//		modes.setBackground(Color.BLACK);
//		modes.setSize(800, 600);
//		WindowManager.getInstance().addWindow(keys.GameState.GAME_MODES, modes);
//
//		//create a new pause screen
//		myPauseScreen = new PauseScreen();
//		pause = new Wrapper(myPauseScreen);
//		pause.setBackground(Color.BLACK);
//		pause.setSize(800, 600);
//		WindowManager.getInstance().addWindow(keys.GameState.PAUSE, pause);
//		
//		myGameWindow = new GameWindow();
//		display = new Wrapper(myGameWindow);
//		display.setBackground(Color.BLACK);
//		display.setSize(800, 600);
//		WindowManager.getInstance().addWindow(keys.GameState.PLAY, display);
//		
//		WindowManager.getInstance().run();
	}

	/**
	 *	Starts the game loop
	 */
	public void run()
	{
		
		System.out.println("*****\tAt the Splash Screen\t*****");
//		try
//		{
//			WindowManager.getInstance().nextWindow(GameState.SPLASH_SCREEN);
//			//myGameWindow.init();
//			//myGameWindow.start();
//		}
//		catch(InterruptedException e)
//		{
//			
//		}
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
					this.constructMeasures();
					this.sendNotesToDisplay();
					
					if(!player1IsDone && (player1Measures.size()>numberOfBars || (player1Measures.size()==numberOfBars && player1Measures.get(player1Measures.size()-1).getNumberOfAvailableBeats()==0)))
						player1IsDone = true;
					if(!player2IsDone && (player2Measures.size()>numberOfBars || (player2Measures.size()==numberOfBars && player2Measures.get(player2Measures.size()-1).getNumberOfAvailableBeats()==0)))
						player2IsDone = true;
					if(!player3IsDone && (player3Measures.size()>numberOfBars || (player3Measures.size()==numberOfBars && player3Measures.get(player3Measures.size()-1).getNumberOfAvailableBeats()==0)))
						player3IsDone = true;
					if(!player4IsDone && (player4Measures.size()>numberOfBars || (player4Measures.size()==numberOfBars && player4Measures.get(player4Measures.size()-1).getNumberOfAvailableBeats()==0)))
						player4IsDone = true;
					
					if(numberOfActivePlayers == 1 && player1IsDone)
					{
						System.out.println("*****\tAt the Post Game Screen\t*****");
						at_play = false;
						at_post_game = true;
						inGameTimer.stop();
					}
					else if(numberOfActivePlayers == 2 && player1IsDone && player2IsDone)
					{
						System.out.println("*****\tAt the Post Game Screen\t*****");
						at_play = false;
						at_post_game = true;
						inGameTimer.stop();
					}
					else if(numberOfActivePlayers == 3 && player1IsDone && player2IsDone && player3IsDone)
					{
						System.out.println("*****\tAt the Post Game Screen\t*****");
						at_play = false;
						at_post_game = true;
						inGameTimer.stop();
					}
					else if(numberOfActivePlayers == 4 && player1IsDone && player2IsDone && player3IsDone && player4IsDone)
					{
						System.out.println("*****\tAt the Post Game Screen\t*****");
						at_play = false;
						at_post_game = true;
						inGameTimer.stop();
					}
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
		System.out.println("GameManager received the message: " + message);
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
			else if(message.split("_")[0].equals(Players.PLAYER_TWO) && !p2)
			{
				p2 = true;
				newMessages.add(message);
			}
			else if(message.split("_")[0].equals(Players.PLAYER_THREE) && !p3)
			{
				p3 = true;
				newMessages.add(message);
			}
			else if(message.split("_")[0].equals(Players.PLAYER_FOUR) && !p4)
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
								player1Beats.addAll(gameNotes.get(player1CurrentNote).getBeats());
								player1Notes.add(new Note(n.getPitch(),gameNotes.get(player1CurrentNote).getLength(),Players.PLAYER_ONE));
								player1Notes.get(player1Notes.size()-1).setCorrect(false);
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
							}
						}
						else
						{
							player4IsDone = true;
						}
					}
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
								player1Beats.addAll(gameNotes.get(player1CurrentNote).getBeats());
								player1Notes.add(new Note(n.getPitch(),gameNotes.get(player1CurrentNote).getLength(),Players.PLAYER_ONE));
								player1Notes.get(player1Notes.size()-1).setCorrect(false);
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
								player2Beats.addAll(gameNotes.get(player2CurrentNote).getBeats());
								player2Notes.add(new Note(n.getPitch(),gameNotes.get(player2CurrentNote).getLength(),Players.PLAYER_TWO));
								player2Notes.get(player2Notes.size()-1).setCorrect(false);
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
								player1Beats.addAll(gameNotes.get(player1CurrentNote).getBeats());
								player1Notes.add(new Note(n.getPitch(),gameNotes.get(player1CurrentNote).getLength(),Players.PLAYER_ONE));
								player1Notes.get(player1Notes.size()-1).setCorrect(false);
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
								player2Beats.addAll(gameNotes.get(player2CurrentNote).getBeats());
								player2Notes.add(new Note(n.getPitch(),gameNotes.get(player2CurrentNote).getLength(),Players.PLAYER_TWO));
								player2Notes.get(player2Notes.size()-1).setCorrect(false);
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
							}
						}
						else
						{
							player4IsDone = true;
						}
					}
				}
				else if(gameMode.equals(GameState.KEY_MASTER))
				{
					if(n.getPlayer().equals(Players.PLAYER_ONE))
					{
						if((player1Measures.size()<=numberOfBars) && (n.size()>0))
						{
							player1Beats.addAll(n.getBeats());
							player1Notes.add(n);
						}
						else
						{
							player1IsDone = true;
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
					at_game_options = false;
					at_play = false;
					at_pause = false;
					at_post_game = false;
					exit = false;
					inGameTimer.stop();
					sendMessageToDisplay(m.getMessage());
					
//					mySplashScreen.setConnected(1,false);
//					mySplashScreen.setConnected(2,false);
//					mySplashScreen.setConnected(3,false);
//					mySplashScreen.setConnected(4,false);
					numberOfActivePlayers=0;
					
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
					player2Measures = new ArrayList<Measure>();
					player3Measures = new ArrayList<Measure>();
					player4Measures = new ArrayList<Measure>();
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
					
//					try
//					{
//						myGameWindow.stop();
//						myGameWindow.destroy();
//						myGameWindow = new GameWindow();
//						display = new Wrapper(myGameWindow);
//						display.setBackground(Color.BLACK);
//						display.setSize(800, 600);
//						WindowManager.getInstance().addWindow(keys.GameState.PLAY, display);
//						myGameWindow.init();
//						myGameWindow.start();
//						
//						WindowManager.getInstance().nextWindow(GameState.SPLASH_SCREEN);
//					}
//					catch(InterruptedException e)
//					{
//						
//					}
					
					System.out.println("All players have been disconnected");
					System.out.println("*****\tAt the Splash Screen\t*****");
				}
				
				//     SPLASH SCREEN     //
				//message is "playerX_connect"
				else if(at_splash_screen && m.getMessage().split("_")[1].equals("connect"))
				{
					numberOfActivePlayers++;
//					mySplashScreen.setConnected(m.getMessage().split("_")[0].charAt(m.getMessage().split("_")[0].length()-1)-48,true);
					sendMessageToDisplay(m.getMessage());
				}
				//message is "player1_gamemodes"  (checking to make sure player 1 has sent the message)
				else if(at_splash_screen && numberOfActivePlayers>0 && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.GAME_MODES))
				{
//					try
//					{
//						WindowManager.getInstance().nextWindow(GameState.GAME_MODES);
//					}
//					catch(InterruptedException e)
//					{
//						
//					}
					System.out.println("*****\tAt the Game Modes Screen\t*****");
					
					at_splash_screen = false;
					at_game_modes = true;
//					myGameWindow.setPlayerCount(numberOfActivePlayers);
					sendMessageToDisplay(m.getMessage());
				}
				
				//     GAME MODES     //
				//message is "player1_splashscreen"  (checking to make sure player 1 has sent the message)
				else if(at_game_modes && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.SPLASH_SCREEN))
				{
//					try
//					{
//						WindowManager.getInstance().previousWindow(GameState.SPLASH_SCREEN);
//					}
//					catch(InterruptedException e)
//					{
//						
//					}
					System.out.println("*****\tAt the Splash Screen\t*****");
					at_game_modes = false;
					at_splash_screen = true;
					sendMessageToDisplay(m.getMessage());
				}
				//message is "player1_lengthtraining"  (checking to make sure player 1 has sent the message)
				else if(at_game_modes && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.LENGTH_TRAINING))
				{
//					try
//					{
//						WindowManager.getInstance().nextWindow(GameState.GAME_OPTIONS);
//					}
//					catch(InterruptedException e)
//					{
//						
//					}
					gameMode = GameState.LENGTH_TRAINING;
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
//					try
//					{
//						WindowManager.getInstance().nextWindow(GameState.GAME_OPTIONS);
//					}
//					catch(InterruptedException e)
//					{
//						
//					}
					gameMode = GameState.PITCH_TRAINING;
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
//					try
//					{
//						WindowManager.getInstance().nextWindow(GameState.GAME_OPTIONS);
//					}
//					catch(InterruptedException e)
//					{
//						
//					}
					gameMode = GameState.NOTE_TRAINING;
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
//					try
//					{
//						WindowManager.getInstance().nextWindow(GameState.GAME_OPTIONS);
//					}
//					catch(InterruptedException e)
//					{
//						
//					}
					gameMode = GameState.CONCERT_MASTER;
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
//					try
//					{
//						WindowManager.getInstance().nextWindow(GameState.GAME_OPTIONS);
//					}
//					catch(InterruptedException e)
//					{
//						
//					}
					gameMode = GameState.MUSICAL_IPHONES;
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
//					try
//					{
//						WindowManager.getInstance().nextWindow(GameState.GAME_OPTIONS);
//					}
//					catch(InterruptedException e)
//					{
//						
//					}
					gameMode = GameState.NOTES_AROUND_THE_ROOM;
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
//					try
//					{
//						WindowManager.getInstance().nextWindow(GameState.GAME_OPTIONS);
//					}
//					catch(InterruptedException e)
//					{
//						
//					}
					gameMode = GameState.COMP_TIME;
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
//					try
//					{
//						WindowManager.getInstance().nextWindow(GameState.GAME_OPTIONS);
//					}
//					catch(InterruptedException e)
//					{
//						
//					}
					gameMode = GameState.COMPOSE;
					System.out.println("*****\tAt the Game Options Screen\t*****");
					//System.out.println("*****\tAt the Game Info Screen\t*****");
					at_game_modes = false;
					at_game_options = true;
					//temporary removal of game info
					//at_game_info = true;
					sendMessageToDisplay(m.getMessage());
				}
				//message is "player1_compose"  (checking to make sure player 1 has sent the message)
				else if(at_game_modes && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.KEY_MASTER))
				{
//					try
//					{
//						WindowManager.getInstance().nextWindow(GameState.GAME_OPTIONS);
//					}
//					catch(InterruptedException e)
//					{
//						
//					}
					gameMode = GameState.KEY_MASTER;
					System.out.println("*****\tAt the Game Options Screen\t*****");
					at_game_modes = false;
					at_game_options = true;
					sendMessageToDisplay(m.getMessage());
				}
				
				//     GAME OPTIONS     //
				//message is "player1_gameinfo"  (checking to make sure player 1 has sent the message)
				else if(at_game_options && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.GAME_MODES))
				{
//					try
//					{
//						WindowManager.getInstance().previousWindow(GameState.GAME_MODES);
//					}
//					catch(InterruptedException e)
//					{
//						
//					}
					System.out.println("*****\tAt the Game Modes Screen\t*****");
					//System.out.println("*****\tAt the Game Info Screen\t*****");
					at_game_options = false;
					at_game_modes = true;
					sendMessageToDisplay(m.getMessage());
				}
				//message is "player1_setkey_X"  (checking to make sure player 1 has sent the message)
				else if(at_game_options && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.SET_KEY))
				{
					setKey(m.getMessage().split("_")[2]);
				}
				//message is "player1_settempo_X"  (checking to make sure player 1 has sent the message)
				else if(at_game_options && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.SET_TEMPO))
				{
					setTempo(Integer.parseInt(m.getMessage().split("_")[2]));
				}
				//message is "player1_setnumberofbars_X"  (checking to make sure player 1 has sent the message)
				else if(at_game_options && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.SET_NUMBER_OF_BARS))
				{
					setNumberOfBars(Integer.parseInt(m.getMessage().split("_")[2]));
				}
				//message is "player1_settimesignature_X_Y"  (checking to make sure player 1 has sent the message)
				else if(at_game_options && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.SET_TIME_SIGNATURE))
				{
					setTimeSignature(Integer.parseInt(m.getMessage().split("_")[2]),Integer.parseInt(m.getMessage().split("_")[3]));
				}
				//message is "player1_play"  (checking to make sure player 1 has sent the message)
				else if(at_game_options && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.PLAY))
				{
//					try
//					{	
//						myGameWindow.init();
//						myGameWindow.start();			
//						WindowManager.getInstance().nextWindow(GameState.PLAY);
//					}
//					catch(InterruptedException e)
//					{
//						
//					}
					sendNumberOfActivePlayers();
					setNumberOfBeatsPerMeasure();
					player1Measures.add(new Measure(numberOfBeatsPerMeasure));
					player2Measures.add(new Measure(numberOfBeatsPerMeasure));
					player3Measures.add(new Measure(numberOfBeatsPerMeasure));
					player4Measures.add(new Measure(numberOfBeatsPerMeasure));
					setGameMode(gameMode);
					sendGameNotes();
					System.out.println("*****\tAt the Play Screen\t*****");

					at_game_options = false;
					at_play = true;
										
					inGameTimer.start();
					sendMessageToDisplay(m.getMessage());
				}
				
				//     PLAY     //
				//message is "playerX_pause"
				else if(at_play && m.getMessage().split("_")[1].equals(GameState.PAUSE))
				{
//					try
//					{
//						WindowManager.getInstance().nextWindow(GameState.PAUSE);
//					}
//					catch(InterruptedException e)
//					{
//						
//					}
					System.out.println("*****\tAt the Pause Screen\t*****");
					at_play = false;
					at_pause = true;
					inGameTimer.stop();
					pausedPlayerId = new String(m.getMessage().split("_")[0]);
					sendMessageToDisplay(m.getMessage());
				}

				//     PAUSE     //
				//message is "playerX_play"  (checking to make sure player 1 has sent the message)
				else if(at_pause && m.getMessage().split("_")[1].equals(GameState.PLAY) && m.getMessage().split("_")[0].equals(pausedPlayerId))
				{
//					try
//					{
//						WindowManager.getInstance().previousWindow(GameState.PLAY);
//					}
//					catch(InterruptedException e)
//					{
//						
//					}
					System.out.println("*****\tAt the Play Screen\t*****");
					at_pause = false;
					at_play = true;
					sendMessageToDisplay(m.getMessage());
					inGameTimer.start();
				}
				
				//     POST GAME     //
				//message is "player1_splashscreen"  (checking to make sure player 1 has sent the message)
				else if(at_post_game && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.SPLASH_SCREEN))
				{
//					try
//					{
//						myGameWindow.stop();
//						myGameWindow.destroy();
//						myGameWindow = new GameWindow();
//						display = new Wrapper(myGameWindow);
//						display.setBackground(Color.BLACK);
//						display.setSize(800, 600);
//						WindowManager.getInstance().addWindow(keys.GameState.PLAY, display);
//						myGameWindow.init();
//						myGameWindow.start();
//						
//						WindowManager.getInstance().nextWindow(GameState.SPLASH_SCREEN);
//					}
//					catch(InterruptedException e)
//					{
//						
//					}
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
					player2Measures = new ArrayList<Measure>();
					player3Measures = new ArrayList<Measure>();
					player4Measures = new ArrayList<Measure>();
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
		Note n;
		Note lastNote;
		ArrayList<Beat> lastNoteBeats;
		Note firstNote;
		ArrayList<Beat> firstNoteBeats;
		
		Measure measure;
				
		notesToSend = new ArrayList<Note>();
		
		////	Player 1	////
		//we have reached the end of a measure, so add a new one
		if(!(player1Measures.get(player1Measures.size()-1).getNumberOfAvailableBeats()>0))
		{
			player1Measures.add(new Measure(numberOfBeatsPerMeasure));
		}

		measure = player1Measures.get(player1Measures.size()-1);
		//bad name, but is the current note that was just played
		if(player1Notes.size()>player1CurrentNote)
		{
			n = new Note(player1Notes.get(player1CurrentNote).getPitch(),player1Notes.get(player1CurrentNote).getLength(),player1Notes.get(player1CurrentNote).getPlayer());
			
			if((player1Notes.size()>0) && (player1Notes.size()>player1CurrentNote) && (n.getPlayer().equals(Players.PLAYER_ONE)))
			{					
				//if a note can be simply added to the measure, then do it
				if(measure.canAddNote(player1Notes.get(player1CurrentNote)))
				{
					measure.addNote(player1Notes.get(player1CurrentNote));
					notesToSend.add(player1Notes.get(player1CurrentNote));
					
					player1CurrentNote++;
				}
				else
				{

					lastNoteBeats = new ArrayList<Beat>();
					firstNoteBeats = new ArrayList<Beat>();
						
					//add a note for the rest of the measure
					for(int i = 0; i < measure.getNumberOfAvailableBeats(); i++)
						lastNoteBeats.add(new Beat(n.getPitch()));
					//check what the length of the Note is
					if(lastNoteBeats.size()>0)
					{
						if(lastNoteBeats.size()==1)
						{
							lastNote = new Note(n.getPitch(), Lengths.EIGHTH, Players.PLAYER_ONE);
							lastNote.setTiedRight(true);
							player1Notes.remove(player1Notes.size()-1);
							player1Notes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							player1CurrentNote++;
						}
						else if(lastNoteBeats.size()==2)
						{
							lastNote = new Note(n.getPitch(), Lengths.QUARTER, Players.PLAYER_ONE);
							lastNote.setTiedRight(true);
							player1Notes.remove(player1Notes.size()-1);
							player1Notes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							player1CurrentNote++;
						}
						else if(lastNoteBeats.size()==3)
						{
							lastNote = new Note(n.getPitch(), Lengths.EIGHTH, Players.PLAYER_ONE);
							lastNote.setTiedRight(true);
							player1Notes.remove(player1Notes.size()-1);
							player1Notes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							lastNote = new Note(n.getPitch(), Lengths.QUARTER, Players.PLAYER_ONE);
							lastNote.setTiedLeft(true);
							lastNote.setTiedRight(true);
							player1Notes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							player1CurrentNote+=2;
						}
						else if(lastNoteBeats.size()==4)
						{
							lastNote = new Note(n.getPitch(), Lengths.HALF, Players.PLAYER_ONE);
							lastNote.setTiedRight(true);
							player1Notes.remove(player1Notes.size()-1);
							player1Notes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							player1CurrentNote++;
						}
						else if(lastNoteBeats.size()==5)
						{
							lastNote = new Note(n.getPitch(), Lengths.EIGHTH, Players.PLAYER_ONE);
							lastNote.setTiedRight(true);
							player1Notes.remove(player1Notes.size()-1);
							player1Notes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							lastNote = new Note(n.getPitch(), Lengths.HALF, Players.PLAYER_ONE);
							lastNote.setTiedLeft(true);
							lastNote.setTiedRight(true);
							player1Notes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							player1CurrentNote+=2;
						}
						else if(lastNoteBeats.size()==6)
						{
							lastNote = new Note(n.getPitch(), Lengths.QUARTER, Players.PLAYER_ONE);
							lastNote.setTiedRight(true);
							player1Notes.remove(player1Notes.size()-1);
							player1Notes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							lastNote = new Note(n.getPitch(), Lengths.HALF, Players.PLAYER_ONE);
							lastNote.setTiedLeft(true);
							lastNote.setTiedRight(true);
							player1Notes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							player1CurrentNote+=2;
						}
						else if(lastNoteBeats.size()==7)
						{
							lastNote = new Note(n.getPitch(), Lengths.EIGHTH, Players.PLAYER_ONE);
							lastNote.setTiedRight(true);
							player1Notes.remove(player1Notes.size()-1);
							player1Notes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							lastNote = new Note(n.getPitch(), Lengths.QUARTER, Players.PLAYER_ONE);
							lastNote.setTiedLeft(true);
							lastNote.setTiedRight(true);
							player1Notes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							lastNote = new Note(n.getPitch(), Lengths.HALF, Players.PLAYER_ONE);
							lastNote.setTiedLeft(true);
							lastNote.setTiedRight(true);
							player1Notes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							player1CurrentNote+=3;
						}
						else
						{
							lastNote = new Note(n.getPitch(), Lengths.WHOLE, Players.PLAYER_ONE);
							lastNote.setTiedRight(true);
							player1Notes.remove(player1Notes.size()-1);
							player1Notes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							player1CurrentNote++;
						}
	
						//add a new measure and update the current measure
						player1Measures.add(new Measure(numberOfBeatsPerMeasure));
						measure = player1Measures.get(player1Measures.size()-1);
		
						//add the rest of the note carried over from the previous measure
						for(int i = 0; i < (n.size() - lastNoteBeats.size()); i++)
							firstNoteBeats.add(new Beat(n.getPitch()));
						if(firstNoteBeats.size()>0)
						{
							if(firstNoteBeats.size()==1)
							{
								firstNote = new Note(n.getPitch(), Lengths.EIGHTH, Players.PLAYER_ONE);
								firstNote.setTiedLeft(true);
								player1Notes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								player1CurrentNote++;
							}
							else if(firstNoteBeats.size()==2)
							{
								firstNote = new Note(n.getPitch(), Lengths.QUARTER, Players.PLAYER_ONE);
								firstNote.setTiedLeft(true);
								player1Notes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								player1CurrentNote++;
							}
							else if(firstNoteBeats.size()==3)
							{
								firstNote = new Note(n.getPitch(), Lengths.QUARTER, Players.PLAYER_ONE);
								firstNote.setTiedLeft(true);
								firstNote.setTiedRight(true);
								player1Notes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								firstNote = new Note(n.getPitch(), Lengths.EIGHTH, Players.PLAYER_ONE);
								firstNote.setTiedLeft(true);
								player1Notes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								player1CurrentNote+=2;
							}
							else if(firstNoteBeats.size()==4)
							{
								firstNote = new Note(n.getPitch(), Lengths.HALF, Players.PLAYER_ONE);
								firstNote.setTiedLeft(true);
								player1Notes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								player1CurrentNote++;
							}
							else if(firstNoteBeats.size()==5)
							{
								firstNote = new Note(n.getPitch(), Lengths.HALF, Players.PLAYER_ONE);
								firstNote.setTiedLeft(true);
								firstNote.setTiedRight(true);
								player1Notes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								firstNote = new Note(n.getPitch(), Lengths.EIGHTH, Players.PLAYER_ONE);
								firstNote.setTiedLeft(true);
								player1Notes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								player1CurrentNote+=2;
							}
							else if(firstNoteBeats.size()==6)
							{
								firstNote = new Note(n.getPitch(), Lengths.HALF, Players.PLAYER_ONE);
								firstNote.setTiedLeft(true);
								firstNote.setTiedRight(true);
								player1Notes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								firstNote = new Note(n.getPitch(), Lengths.QUARTER, Players.PLAYER_ONE);
								firstNote.setTiedLeft(true);
								player1Notes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								player1CurrentNote+=2;
							}
							else if(firstNoteBeats.size()==7)
							{
								firstNote = new Note(n.getPitch(), Lengths.HALF, Players.PLAYER_ONE);
								firstNote.setTiedLeft(true);
								firstNote.setTiedRight(true);
								player1Notes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								firstNote = new Note(n.getPitch(), Lengths.QUARTER, Players.PLAYER_ONE);
								firstNote.setTiedLeft(true);
								firstNote.setTiedRight(true);
								player1Notes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								firstNote = new Note(n.getPitch(), Lengths.EIGHTH, Players.PLAYER_ONE);
								firstNote.setTiedLeft(true);
								player1Notes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								player1CurrentNote+=3;
							}
							else
							{
								firstNote = new Note(n.getPitch(), Lengths.WHOLE, Players.PLAYER_ONE);
								firstNote.setTiedLeft(true);
								player1Notes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								player1CurrentNote++;
							}
						}
					}
				}
			}
		}
		////	Player 1	////


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
		{
			gameNotes = new gameManagement.gameModes.LengthTraining(numberOfBeatsPerMeasure,numberOfBars).getNotes();
		}
		else if(gameMode.equals(GameState.PITCH_TRAINING))
		{
			gameNotes = new gameManagement.gameModes.PitchTraining(numberOfBeatsPerMeasure,numberOfBars).getNotes();
		}
		else if(gameMode.equals(GameState.NOTE_TRAINING))
		{
			gameNotes = new gameManagement.gameModes.NoteTraining(numberOfBeatsPerMeasure,numberOfBars).getNotes();
		}
		else
			gameNotes = null;
		
		if(gameMode.equals(GameState.KEY_MASTER))
			keyProgression = new gameManagement.gameModes.KeyMaster(key,numberOfBars,2).getKeyProgression();
	}
	
	private void setTempo(int t)
	{
		tempo = t;
		System.out.println("Tempo set at: " + tempo);
		//TODO send message to Display
	}
	
	private void setKey(String k)
	{
		key = k;
		System.out.println("Key set at: " + k);
		//TODO send message to Display
	}
	
	private void setNumberOfBars(int n)
	{
		numberOfBars = n;
		System.out.println("Number of Bars set at: " + n);
		//TODO send message to Display
	}
	
	private void setTimeSignature(int n, int d)
	{
		timeSignatureNumerator = n;
		timeSignatureDenominator = d;
		setNumberOfBeatsPerMeasure();
		System.out.println("Time signature set at: " + n + "/" + d);
		//TODO send message to Display
	}
	
	/**
	 * Sends the number of active players to the public display
	 */
	private void sendNumberOfActivePlayers()
	{
		System.out.println("Number of players set at: " + numberOfActivePlayers);
		//TODO send message to Display
	}

	/**
	 * Sends the gameNotes to the public display for visual reasons, notes are checked in the GameManager
	 */
	private void sendGameNotes()
	{		
//		//TODO this is ghetto
//		myGameWindow.setPlayerCount(2);
//		//...in the ghetto!
//		for(Note note : gameNotes)
//		{
//			note.setPlayer(Players.PLAYER_ONE);
//			myGameWindow.addNote(note);
//		}
//		
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
		System.out.println(time);
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
//		for(Note note : notesToSend)
//		{
//			System.out.println(note + " " + note.getPlayer());
//			note.playNote();
//		}
		for(Measure m : player1Measures)
			System.out.print(m + " | ");
		System.out.println();
		
		notesToSend = new ArrayList<Note>();

	}
	
}