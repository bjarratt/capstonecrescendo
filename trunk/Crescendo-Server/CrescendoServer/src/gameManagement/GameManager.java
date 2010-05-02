package gameManagement;

import gameManagement.gameModes.KeyMaster;
import gameManagement.messageTranslationSystem.MessageTranslationEngine;
import gameManagement.messageTranslationSystem.Beat;
import gameManagement.messageTranslationSystem.Note;
import gameManagement.messageTranslationSystem.Measure;
import gameManagement.windowManagement.WindowManager;
import gameManagement.windowManagement.publicDisplay.GameOptionsWindow;
import gameManagement.windowManagement.publicDisplay.GameWindow;
import gameManagement.windowManagement.publicDisplay.PauseWindow;
import gameManagement.windowManagement.publicDisplay.SplashWindow;
import keys.GameState;
import keys.Lengths;
import keys.Players;
import keys.Scales;

import java.util.ArrayList;

import javax.swing.JPanel;
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
	private static int tempo = 120;
	private static String key = "CMajor";
	private static int numberOfBars = 16;
	private static int timeSignatureNumerator = 4;
	private static int timeSignatureDenominator = 4;
	private static int numberOfBeatsPerMeasure;
	
	//gamestate options
	private boolean at_splash_screen;
	private boolean at_game_options;
	private boolean at_play;
	private boolean at_pause;
	private boolean at_post_game;

	private boolean gameStart;
	private boolean gameIsDone;
	
	KeyMaster keyMaster;
	
	//game
	private String gameMode;
	private ArrayList<String> keyProgression;
	
	private String currentPlayerId;
	private String pausedPlayerId;
	
	Note previousNotePlayed;
	
	private int player1Score;
	private int player2Score;
	private int player3Score;
	private int player4Score;
	
	private ArrayList<Beat> gameBeats;
	
	private int currentTick;
	private int currentInGameTick;
	private int initialInGameTime;
	private int additionalInGameTime;

	private ArrayList<Note> gameNotes;

	private int gameCurrentNote;
	private int numberOfActivePlayers;

	private ArrayList<Measure> gameMeasures;

	ArrayList<Note> notesToSend; 
	
	private SplashWindow splashWindow;
	private GameOptionsWindow gameOptionsWindow;
	private GameWindow gameWindow;
	private PauseWindow pauseWindow;

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

		gameCurrentNote = 0;

		currentTick = 0;
		currentInGameTick = 0;
		initialInGameTime = 30;
		additionalInGameTime = 0;

		at_splash_screen = true;
		at_game_options = false;
		at_play = false;
		at_pause = false;
		at_post_game = false;

		gameStart = true;
		gameIsDone = false;
		
		notesToSend = new ArrayList<Note>();
		
		gameMode = GameState.KEY_MASTER;

		currentPlayerId = Players.PLAYER_ONE;
		pausedPlayerId = new String();
		
		previousNotePlayed = null;

		player1Score = 1337;
		player2Score = 1337;
		player3Score = 1337;
		player4Score = 1337;
		
		gameBeats = new ArrayList<Beat>();

		gameNotes = new ArrayList<Note>();

		numberOfActivePlayers = 0;

		gameMeasures = new ArrayList<Measure>();

		splashWindow = new SplashWindow();
		gameOptionsWindow = new GameOptionsWindow();
		gameWindow = new GameWindow();
		pauseWindow = new PauseWindow();

		//create a new splash screen
		WindowManager.getInstance().addWindow(GameState.SPLASH_SCREEN, splashWindow);
		WindowManager.getInstance().addWindow(GameState.GAME_OPTIONS, gameOptionsWindow);
		WindowManager.getInstance().addWindow(GameState.PLAY, gameWindow);
		WindowManager.getInstance().addWindow(GameState.PAUSE, pauseWindow);
		WindowManager.getInstance().addWindow(GameState.POST_GAME, new JPanel());
//		WindowManager.getInstance().run();
	}

	/**
	 *	Starts the game loop
	 */
	public void run()
	{
		System.out.println("*****\tAt the Splash Screen\t*****");
		WindowManager.getInstance().goToWindow(GameState.SPLASH_SCREEN);
		timer.start();
	}

	/**
	 *	When a Timer ticks, this method is called and performs some action
	 */
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == timer)
		{
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
					this.checkNotesForCorrectness();
					this.scoreNotes();
					this.sendScoresToDisplay();
					this.sendNotesToDisplay();
					
					if(!gameIsDone && (gameMeasures.size()>numberOfBars || (gameMeasures.size()==numberOfBars && gameMeasures.get(gameMeasures.size()-1).getNumberOfAvailableBeats()==0)))
						gameIsDone = true;
					
					if(gameIsDone)
					{
						System.out.println("*****\tAt the Post Game Screen\t*****");
						WindowManager.getInstance().goToWindow(GameState.POST_GAME);
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
			if(!gameStart)
			{
				if((initialInGameTime-currentInGameTick+additionalInGameTime)<=0)
				{
					System.out.println("*****\tAt the Post Game Screen\t*****");
					WindowManager.getInstance().goToWindow(GameState.POST_GAME);
					at_play = false;
					at_post_game = true;
					inGameTimer.stop();
				}
				sendTimeToDisplay(initialInGameTime-currentInGameTick+additionalInGameTime);
			}
			else
			{
				if(currentInGameTick < 5)
				{
					sendTimeToDisplay(5 - currentInGameTick);
				}
				if(5 - currentInGameTick <= 0)
				{
					gameStart = false;
					currentInGameTick = -1;
				}
			}
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
			
			if(at_play && !gameStart && m.isNote())
			{
				Note n = m.getNote();
				
				if(gameMode.equals(GameState.KEY_MASTER))
				{
					if(n.getPlayer().equals(Players.PLAYER_ONE) && (gameMeasures.size()<=numberOfBars) && (currentPlayerId.equals(Players.PLAYER_ONE)))
					{
						gameBeats.addAll(n.getBeats());
						gameNotes.add(n);
						if(numberOfActivePlayers>1)
							currentPlayerId = new String(Players.PLAYER_TWO);
					}
					else if(n.getPlayer().equals(Players.PLAYER_TWO) && (gameMeasures.size()<=numberOfBars) && (currentPlayerId.equals(Players.PLAYER_TWO)))
					{
						gameBeats.addAll(n.getBeats());
						gameNotes.add(n);
						if(numberOfActivePlayers>2)
							currentPlayerId = new String(Players.PLAYER_THREE);
						else
							currentPlayerId = new String(Players.PLAYER_ONE);
					}
					else if(n.getPlayer().equals(Players.PLAYER_THREE) && (gameMeasures.size()<=numberOfBars) && (currentPlayerId.equals(Players.PLAYER_THREE)))
					{
						gameBeats.addAll(n.getBeats());
						gameNotes.add(n);
						if(numberOfActivePlayers>3)
							currentPlayerId = new String(Players.PLAYER_FOUR);
						else
							currentPlayerId = new String(Players.PLAYER_ONE);
					}
					else if(n.getPlayer().equals(Players.PLAYER_FOUR) && (gameMeasures.size()<=numberOfBars) && (currentPlayerId.equals(Players.PLAYER_FOUR)))
					{
						gameBeats.addAll(n.getBeats());
						gameNotes.add(n);
						currentPlayerId = new String(Players.PLAYER_ONE);
					}
				}
			}
			else
			{	
				//     DISCONNECT     //
				if(m.getMessage().split("_")[1].equals("disconnect"))
				{
					at_splash_screen = true;
					at_game_options = false;
					at_play = false;
					at_pause = false;
					at_post_game = false;
					inGameTimer.stop();
					sendMessageToDisplay(m.getMessage());
					
					numberOfActivePlayers = 0;
					
					tempo = 120;
					key = "CMajor";
					numberOfBars = 16;
					timeSignatureNumerator = 4;
					timeSignatureDenominator = 4;
					
					//clear other game state
					gameBeats = new ArrayList<Beat>();
					gameNotes = new ArrayList<Note>();
					gameMeasures = new ArrayList<Measure>();
					gameCurrentNote = 0;
					
					gameStart = true;
					gameIsDone = false;
					
					messagePool = new ArrayList<String>();
					messages = new ArrayList<String>();

					currentTick = 0;
					currentInGameTick = 0;
					additionalInGameTime = 0;

					player1Score = 1337;
					player2Score = 1337;
					player3Score = 1337;
					player4Score = 1337;
					
					currentPlayerId = Players.PLAYER_ONE;
					pausedPlayerId = new String();

					notesToSend = new ArrayList<Note>();
					
					splashWindow.disconnectPlayer(1);
					splashWindow.disconnectPlayer(2);
					splashWindow.disconnectPlayer(3);
					splashWindow.disconnectPlayer(4);
					
					System.out.println("All players have been disconnected");
					System.out.println("*****\tAt the Splash Screen\t*****");
					WindowManager.getInstance().goToWindow(GameState.SPLASH_SCREEN);
				}
				
				//     SPLASH SCREEN     //
				//message is "playerX_connect"
				else if(at_splash_screen && m.getMessage().split("_")[1].equals("connect"))
				{
					numberOfActivePlayers++;
					splashWindow.connectPlayer(m.getMessage().split("_")[0].charAt(m.getMessage().split("_")[0].length()-1)-48);
					sendMessageToDisplay(m.getMessage());
				}
				//message is "player1_gamemodes"  (checking to make sure player 1 has sent the message)
				else if(at_splash_screen && numberOfActivePlayers>0 && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.GAME_OPTIONS))
				{
					System.out.println("*****\tAt the Game Options Screen\t*****");
					WindowManager.getInstance().goToWindow(GameState.GAME_OPTIONS);
					
					at_splash_screen = false;
					at_game_options = true;
//					myGameWindow.setPlayerCount(numberOfActivePlayers);
					sendMessageToDisplay(m.getMessage());
				}
				
				//     GAME OPTIONS     //
				//message is "player1_splashscreen"  (checking to make sure player 1 has sent the message)
				else if(at_game_options && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.SPLASH_SCREEN))
				{
					System.out.println("*****\tAt the Splash Screen\t*****");
					WindowManager.getInstance().goToWindow(GameState.SPLASH_SCREEN);
					at_game_options = false;
					at_splash_screen = true;
					sendMessageToDisplay(m.getMessage());
				}
				//message is "player1_setkey_X"  (checking to make sure player 1 has sent the message)
				else if(at_game_options && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.SET_KEY))
				{
					setKey(m.getMessage().split("_")[2] + "Major");
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
					setTimeSignature(Integer.parseInt(m.getMessage().split("_")[2].split("/")[0]),Integer.parseInt(m.getMessage().split("_")[2].split("/")[1]));
				}
				//message is "player1_play"  (checking to make sure player 1 has sent the message)
				else if(at_game_options && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.PLAY))
				{
					setNumberOfBeatsPerMeasure();
					gameMeasures.add(new Measure(numberOfBeatsPerMeasure));
					setGameMode(gameMode);
					System.out.println("*****\tAt the Play Screen\t*****");
					WindowManager.getInstance().goToWindow(GameState.PLAY);
					
					at_game_options = false;
					at_play = true;
										
					inGameTimer.start();
					sendMessageToDisplay(m.getMessage());
				}
				
				//     PLAY     //
				//message is "playerX_pause"
				else if(at_play && m.getMessage().split("_")[1].equals(GameState.PAUSE))
				{
					System.out.println("*****\tAt the Pause Screen\t*****");
					WindowManager.getInstance().goToWindow(GameState.PAUSE);
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
					System.out.println("*****\tAt the Play Screen\t*****");
					WindowManager.getInstance().goToWindow(GameState.PLAY);
					at_pause = false;
					at_play = true;
					sendMessageToDisplay(m.getMessage());
					inGameTimer.start();
				}
				
				//     POST GAME     //
				//message is "player1_playsong"
				else if(at_post_game && m.getMessage().split("_")[1].equals(GameState.PLAY_SONG))
				{
					org.jfugue.Player jfuguePlayer = new org.jfugue.Player();
					org.jfugue.Pattern s = new org.jfugue.Pattern();
					s.add("T"+tempo+" ");
					for(Note note : gameNotes)
						s.add(note.getJFuguePattern());
					jfuguePlayer.play(s);
					sendMessageToDisplay(m.getMessage());
				}
				//message is "player1_splashscreen"  (checking to make sure player 1 has sent the message)
				else if(at_post_game && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.SPLASH_SCREEN))
				{
					System.out.println("*****\tAt the Splash Screen\t*****");
					WindowManager.getInstance().goToWindow(GameState.SPLASH_SCREEN);
					
					at_post_game = false;
					at_splash_screen = true;
					
					//clear all records
					gameBeats = new ArrayList<Beat>();
					gameNotes = new ArrayList<Note>();
					gameMeasures = new ArrayList<Measure>();
					gameCurrentNote = 0;
					
					gameStart = true;
					gameIsDone = false;
					
					messagePool = new ArrayList<String>();
					messages = new ArrayList<String>();

					currentTick = 0;
					currentInGameTick = 0;
					additionalInGameTime = 0;

					player1Score = 1337;
					player2Score = 1337;
					player3Score = 1337;
					player4Score = 1337;
					
					currentPlayerId = Players.PLAYER_ONE;
					pausedPlayerId = new String();
					
					notesToSend = new ArrayList<Note>();
					
					int oldNumberOfPlayers = numberOfActivePlayers;
					numberOfActivePlayers = 0;

					for(int i = 0; i < oldNumberOfPlayers; i++)
					{
						addMessageToPool("player" + (i+1) + "_" + GameState.CONNECT);
					}
					
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
		
		//we have reached the end of a measure, so add a new one
		if(!(gameMeasures.get(gameMeasures.size()-1).getNumberOfAvailableBeats()>0))
		{
			gameMeasures.add(new Measure(numberOfBeatsPerMeasure));
		}

		measure = gameMeasures.get(gameMeasures.size()-1);
		//bad name, but is the current note that was just played
		if(gameNotes.size()>gameCurrentNote)
		{
			n = new Note(gameNotes.get(gameCurrentNote).getPitch(),gameNotes.get(gameCurrentNote).getLength(),gameNotes.get(gameCurrentNote).getPlayer());
			
			if(gameNotes.size()>0 && gameNotes.size()>gameCurrentNote)
			{					
				//if a note can be simply added to the measure, then do it
				if(measure.canAddNote(gameNotes.get(gameCurrentNote)))
				{
					measure.addNote(gameNotes.get(gameCurrentNote));
					notesToSend.add(gameNotes.get(gameCurrentNote));
					
					gameCurrentNote++;
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
							lastNote = new Note(n.getPitch(), Lengths.EIGHTH, n.getPlayer());
							lastNote.setTiedRight(true);
							gameNotes.remove(gameNotes.size()-1);
							gameNotes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							gameCurrentNote++;
						}
						else if(lastNoteBeats.size()==2)
						{
							lastNote = new Note(n.getPitch(), Lengths.QUARTER, n.getPlayer());
							lastNote.setTiedRight(true);
							gameNotes.remove(gameNotes.size()-1);
							gameNotes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							gameCurrentNote++;
						}
						else if(lastNoteBeats.size()==3)
						{
							lastNote = new Note(n.getPitch(), Lengths.EIGHTH, n.getPlayer());
							lastNote.setTiedRight(true);
							gameNotes.remove(gameNotes.size()-1);
							gameNotes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							lastNote = new Note(n.getPitch(), Lengths.QUARTER, n.getPlayer());
							lastNote.setTiedLeft(true);
							lastNote.setTiedRight(true);
							gameNotes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							gameCurrentNote+=2;
						}
						else if(lastNoteBeats.size()==4)
						{
							lastNote = new Note(n.getPitch(), Lengths.HALF, n.getPlayer());
							lastNote.setTiedRight(true);
							gameNotes.remove(gameNotes.size()-1);
							gameNotes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							gameCurrentNote++;
						}
						else if(lastNoteBeats.size()==5)
						{
							lastNote = new Note(n.getPitch(), Lengths.EIGHTH, n.getPlayer());
							lastNote.setTiedRight(true);
							gameNotes.remove(gameNotes.size()-1);
							gameNotes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							lastNote = new Note(n.getPitch(), Lengths.HALF, n.getPlayer());
							lastNote.setTiedLeft(true);
							lastNote.setTiedRight(true);
							gameNotes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							gameCurrentNote+=2;
						}
						else if(lastNoteBeats.size()==6)
						{
							lastNote = new Note(n.getPitch(), Lengths.QUARTER, n.getPlayer());
							lastNote.setTiedRight(true);
							gameNotes.remove(gameNotes.size()-1);
							gameNotes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							lastNote = new Note(n.getPitch(), Lengths.HALF, n.getPlayer());
							lastNote.setTiedLeft(true);
							lastNote.setTiedRight(true);
							gameNotes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							gameCurrentNote+=2;
						}
						else if(lastNoteBeats.size()==7)
						{
							lastNote = new Note(n.getPitch(), Lengths.EIGHTH, n.getPlayer());
							lastNote.setTiedRight(true);
							gameNotes.remove(gameNotes.size()-1);
							gameNotes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							lastNote = new Note(n.getPitch(), Lengths.QUARTER, n.getPlayer());
							lastNote.setTiedLeft(true);
							lastNote.setTiedRight(true);
							gameNotes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							lastNote = new Note(n.getPitch(), Lengths.HALF, n.getPlayer());
							lastNote.setTiedLeft(true);
							lastNote.setTiedRight(true);
							gameNotes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							gameCurrentNote+=3;
						}
						else
						{
							lastNote = new Note(n.getPitch(), Lengths.WHOLE, n.getPlayer());
							lastNote.setTiedRight(true);
							gameNotes.remove(gameNotes.size()-1);
							gameNotes.add(lastNote);
							measure.addNote(lastNote);
							notesToSend.add(lastNote);
							gameCurrentNote++;
						}
	
						//add a new measure and update the current measure
						gameMeasures.add(new Measure(numberOfBeatsPerMeasure));
						measure = gameMeasures.get(gameMeasures.size()-1);
		
						//add the rest of the note carried over from the previous measure
						for(int i = 0; i < (n.size() - lastNoteBeats.size()); i++)
							firstNoteBeats.add(new Beat(n.getPitch()));
						if(firstNoteBeats.size()>0)
						{
							if(firstNoteBeats.size()==1)
							{
								firstNote = new Note(n.getPitch(), Lengths.EIGHTH, n.getPlayer());
								firstNote.setTiedLeft(true);
								gameNotes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								gameCurrentNote++;
							}
							else if(firstNoteBeats.size()==2)
							{
								firstNote = new Note(n.getPitch(), Lengths.QUARTER, n.getPlayer());
								firstNote.setTiedLeft(true);
								gameNotes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								gameCurrentNote++;
							}
							else if(firstNoteBeats.size()==3)
							{
								firstNote = new Note(n.getPitch(), Lengths.QUARTER, n.getPlayer());
								firstNote.setTiedLeft(true);
								firstNote.setTiedRight(true);
								gameNotes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								firstNote = new Note(n.getPitch(), Lengths.EIGHTH, n.getPlayer());
								firstNote.setTiedLeft(true);
								gameNotes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								gameCurrentNote+=2;
							}
							else if(firstNoteBeats.size()==4)
							{
								firstNote = new Note(n.getPitch(), Lengths.HALF, n.getPlayer());
								firstNote.setTiedLeft(true);
								gameNotes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								gameCurrentNote++;
							}
							else if(firstNoteBeats.size()==5)
							{
								firstNote = new Note(n.getPitch(), Lengths.HALF, n.getPlayer());
								firstNote.setTiedLeft(true);
								firstNote.setTiedRight(true);
								gameNotes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								firstNote = new Note(n.getPitch(), Lengths.EIGHTH, n.getPlayer());
								firstNote.setTiedLeft(true);
								gameNotes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								gameCurrentNote+=2;
							}
							else if(firstNoteBeats.size()==6)
							{
								firstNote = new Note(n.getPitch(), Lengths.HALF, n.getPlayer());
								firstNote.setTiedLeft(true);
								firstNote.setTiedRight(true);
								gameNotes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								firstNote = new Note(n.getPitch(), Lengths.QUARTER, n.getPlayer());
								firstNote.setTiedLeft(true);
								gameNotes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								gameCurrentNote+=2;
							}
							else if(firstNoteBeats.size()==7)
							{
								firstNote = new Note(n.getPitch(), Lengths.HALF, n.getPlayer());
								firstNote.setTiedLeft(true);
								firstNote.setTiedRight(true);
								gameNotes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								firstNote = new Note(n.getPitch(), Lengths.QUARTER, n.getPlayer());
								firstNote.setTiedLeft(true);
								firstNote.setTiedRight(true);
								gameNotes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								firstNote = new Note(n.getPitch(), Lengths.EIGHTH, n.getPlayer());
								firstNote.setTiedLeft(true);
								gameNotes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								gameCurrentNote+=3;
							}
							else
							{
								firstNote = new Note(n.getPitch(), Lengths.WHOLE, n.getPlayer());
								firstNote.setTiedLeft(true);
								gameNotes.add(firstNote);
								measure.addNote(firstNote);
								notesToSend.add(firstNote);
								gameCurrentNote++;
							}
						}
					}
				}
			}
		}
	}
	
	private void checkNotesForCorrectness()
	{
		for(Note note : notesToSend)
		{
			if(note.getPlayer().equals(Players.PLAYER_ONE))
			{
				for(int i = gameMeasures.size()-1; i >=0; i--)
					if(gameMeasures.get(i).getNotes().contains(note))
					{
						if(keyProgression.size()>i)
							if(!(Scales.isNoteInKey(note, keyProgression.get(i))))
								note.setCorrect(false);
						break;
					}
			}
			else if(note.getPlayer().equals(Players.PLAYER_TWO))
			{
				for(int i = gameMeasures.size()-1; i >=0; i--)
					if(gameMeasures.get(i).getNotes().contains(note))
					{
						if(keyProgression.size()>i)
							if(!(Scales.isNoteInKey(note, keyProgression.get(i))))
								note.setCorrect(false);
						break;
					}
			}
			else if(note.getPlayer().equals(Players.PLAYER_THREE))
			{
				for(int i = gameMeasures.size()-1; i >=0; i--)
					if(gameMeasures.get(i).getNotes().contains(note))
					{
						if(keyProgression.size()>i)
							if(!(Scales.isNoteInKey(note, keyProgression.get(i))))
								note.setCorrect(false);
						break;
					}
			}
			else if(note.getPlayer().equals(Players.PLAYER_FOUR))
			{
				for(int i = gameMeasures.size()-1; i >=0; i--)
					if(gameMeasures.get(i).getNotes().contains(note))
					{
						if(keyProgression.size()>i)
							if(!(Scales.isNoteInKey(note, keyProgression.get(i))))
								note.setCorrect(false);
						break;
					}
			}
		}
	}
	
	private void scoreNotes()
	{
		double score;
		for(Note note : notesToSend)
		{
			score = 0;
			//convert score from an integer to a double
			if(note.getPlayer().equals(Players.PLAYER_ONE))
				score = player1Score;
			else if(note.getPlayer().equals(Players.PLAYER_TWO))
				score = player2Score;
			else if(note.getPlayer().equals(Players.PLAYER_THREE))
				score = player3Score;
			else if(note.getPlayer().equals(Players.PLAYER_FOUR))
				score = player4Score;
			
			if(note.isCorrect())
			{
				if(note.getPitch().equals("rest"))
				{
					score += Math.PI * 10;
					additionalInGameTime += 3;
				}
				else
				{
					//add points and time
					if(previousNotePlayed != null)
					{
						if(!(note.getPitch().equals(previousNotePlayed.getPitch())))
						{
							score += Math.PI * 25;
							additionalInGameTime += 1;
						}
						if(!(note.getLength().equals(previousNotePlayed.getLength())))
						{
							score += Math.PI * 25;
							additionalInGameTime += 1;
						}
						if((!(note.getLength().equals(previousNotePlayed.getLength())))&&(!(note.getPitch().equals(previousNotePlayed.getPitch()))))
						{
							score += Math.PI * 50;
							additionalInGameTime += 3;
						}
						if(!note.equals(previousNotePlayed))
						{
							score += Math.PI * 100;
							additionalInGameTime += 6;
						}
					}
					else
					{
						score += Math.PI * 100;
						additionalInGameTime += 6;
					}
					
					if(gameMeasures.size()<=keyProgression.size())
						if(Scales.isNoteInArpeggio(note, keyProgression.get(gameMeasures.size()-1)))
						{
							score += Math.PI * 50;
							additionalInGameTime += 3;
						}
				}
			}
			else
			{
				//subtract points and time
				score -= Math.E * 100;
				additionalInGameTime -= 3;
				if(score<0)
					score = 0;
			}
			
			//convert score back to an integer
			if(note.getPlayer().equals(Players.PLAYER_ONE))
				player1Score = new Double(score).intValue();
			else if(note.getPlayer().equals(Players.PLAYER_TWO))
				player2Score = new Double(score).intValue();
			else if(note.getPlayer().equals(Players.PLAYER_THREE))
				player3Score = new Double(score).intValue();
			else if(note.getPlayer().equals(Players.PLAYER_FOUR))
				player4Score = new Double(score).intValue();
			
			previousNotePlayed = note;
		}
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
		
		if(gameMode.equals(GameState.KEY_MASTER))
		{
			keyMaster = new gameManagement.gameModes.KeyMaster(key,numberOfBars,2);
			keyProgression = keyMaster.getKeyProgression();
		}
		else
		{
			keyProgression = null;
		}
	}
	
	private void setTempo(int t)
	{
		tempo = t;
		System.out.println("Tempo set at: " + tempo);
		gameOptionsWindow.setTempo(tempo);
	}
	
	private void setKey(String k)
	{
		key = k;
		System.out.println("Key set at: " + k);
		gameOptionsWindow.setKey(key);
	}
	
	private void setNumberOfBars(int n)
	{
		numberOfBars = n;
		System.out.println("Number of Bars set at: " + n);
		gameOptionsWindow.setMeasureCount(numberOfBars);
	}
	
	private void setTimeSignature(int n, int d)
	{
		timeSignatureNumerator = n;
		timeSignatureDenominator = d;
		setNumberOfBeatsPerMeasure();
		System.out.println("Time signature set at: " + n + "/" + d);
		gameOptionsWindow.setTime(timeSignatureNumerator, timeSignatureDenominator);
	}
	
	/**
	 *	Sends a message to the GUI
	 *
	 *	@param message the message to be sent to the GUI
	 */
	private void sendMessageToDisplay(String message)
	{
		System.out.println("GameManager sending message to GUI:\t" + message);
		//displayGUI.receiveMessage(message);
	}

	private void sendScoresToDisplay()
	{
		System.out.println("player 1 Score: " + player1Score);
		System.out.println("player 2 Score: " + player2Score);
		System.out.println("player 3 Score: " + player3Score);
		System.out.println("player 4 Score: " + player4Score);
		System.out.println();
		//TODO send to Display
	}
	
	private void sendTimeToDisplay(int time)
	{
		System.out.println(time + " seconds left in the game");
		//TODO send message to Display
	}
	
	/**
	 *	Sends a Note to the GUI
	 *
	 *	@param note the Note to be sent to the GUI
	 */
	private void sendNotesToDisplay()
	{
		for(Measure m : gameMeasures)
			System.out.print(m + " | ");
		System.out.println();
		System.out.println();
		
		for(Note note : notesToSend)
		{
			//TODO send notesToSend to display
		}
		
		notesToSend = new ArrayList<Note>();
	}
	
}