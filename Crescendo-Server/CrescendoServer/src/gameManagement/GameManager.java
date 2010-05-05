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

import network.ConnectionManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
 *	The GameManager translates messages, and sends Notes to be displayed to the GUI
 *
 *	@author Travis Kosarek
 */
public class GameManager implements ActionListener
{
	//messages arrive and sit in the pool
	private ArrayList<String> messagePool;
	//every 200 milliseconds the messages get released from the pool to be translated
	private ArrayList<String> messages;
	
	//the count down timer in the game
	private Timer inGameTimer;

	//game options
	private static int tempo = 120;
	private static String key = "CMajor";
	private static int numberOfBars = 16;
	private static int timeSignatureNumerator = 4;
	private static int timeSignatureDenominator = 4;
	private static int numberOfBeatsPerMeasure;
	
	//game state options
	private boolean at_splash_screen;
	private boolean at_game_options;
	private boolean at_play;
	private boolean at_pause;
	private boolean at_post_game;

	//flags denoting the beginning and the ending of a game
	private boolean gameStart;
	private boolean gameIsDone;
	
	//the game type
	KeyMaster keyMaster;
	
	//the current game mode (slightly redundant, but leftover from when we had multiple game types)
	private String gameMode;
	//a list of each measure's keys
	private ArrayList<String> keyProgression;
	
	//IDs that are used for certain gamestate functions
	private String currentPlayerId;
	private String pausedPlayerId;
	
	//global previous note
	Note previousNote;
	
	//individual player's previous notes
	Note previousNotePlayed1;
	Note previousNotePlayed2;
	Note previousNotePlayed3;
	Note previousNotePlayed4;
	
	//each individual player's score
	private int player1Score;
	private int player2Score;
	private int player3Score;
	private int player4Score;
	
	//time variables
	//overall tick for "inGameTimer"
	private int currentInGameTick;
	//the number of seconds players start with in game
	private int initialInGameTime;
	//any additional time player's earn for playing correct notes
	private int additionalInGameTime;

	
	//the entire song played broken down into each beat
	private ArrayList<Beat> gameBeats;
	//the entire song played broken down into each individual note
	private ArrayList<Note> gameNotes;
	//the entire song played broken down into each measure
	private ArrayList<Measure> gameMeasures;

	//the index of the current note played
	private int gameCurrentNote;
	
	//the number of players connected to the game
	private int numberOfActivePlayers;

	//the list of recently played notes to be sent to the display
	ArrayList<Note> notesToSend; 
	
	//the different windows
	private SplashWindow splashWindow;
	private GameOptionsWindow gameOptionsWindow;
	private GameWindow gameWindow;
	private PauseWindow pauseWindow;

	/**
	 *	Constructs a GameManager object with up to 4 players
	 */
	public GameManager()
	{
		//initializing all variables
		messagePool = new ArrayList<String>();
		messages = new ArrayList<String>();
		
		inGameTimer = new Timer(1000,this);

		gameCurrentNote = 0;

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

		previousNote = null;
		
		previousNotePlayed1 = null;
		previousNotePlayed2 = null;
		previousNotePlayed3 = null;
		previousNotePlayed4 = null;

		player1Score = 1337;
		player2Score = 1337;
		player3Score = 1337;
		player4Score = 1337;
		
		gameBeats = new ArrayList<Beat>();
		gameNotes = new ArrayList<Note>();
		gameMeasures = new ArrayList<Measure>();

		numberOfActivePlayers = 0;

		splashWindow = new SplashWindow();
		gameOptionsWindow = new GameOptionsWindow();
		gameWindow = new GameWindow();
		pauseWindow = new PauseWindow();

		//adding all the windows to the WindowManager
		WindowManager.getInstance().addWindow(GameState.SPLASH_SCREEN, splashWindow);
		WindowManager.getInstance().addWindow(GameState.GAME_OPTIONS, gameOptionsWindow);
		WindowManager.getInstance().addWindow(GameState.PLAY, gameWindow);
		WindowManager.getInstance().addWindow(GameState.PAUSE, pauseWindow);
		WindowManager.getInstance().addWindow(GameState.POST_GAME, new JPanel());
		WindowManager.getInstance().run();
	}

	/**
	 *	Allows users to start sending messages to the game and brings up the splash screen
	 */
	public synchronized void run()
	{
		//tell the WindowManager to go to the splash screen
		WindowManager.getInstance().goToWindow(GameState.SPLASH_SCREEN);
	}

	/**
	 *	When a Timer ticks, this method is called and performs some action
	 */
	public synchronized void actionPerformed(ActionEvent event)
	{
		//if the inGameTimer ticks, this method is called
		if(event.getSource() == inGameTimer)
		{
			//if the game is passed the 5 second countdown timer
			if(!gameStart)
			{
				//if the in game timer equals zero or goes below zero
				if((initialInGameTime-currentInGameTick+additionalInGameTime)<=0)
				{
					//end the game and go to the post game screen
					WindowManager.getInstance().goToWindow(GameState.POST_GAME);
					
					//TODO send post game screen values it needs here
					//perhaps modify sendScoresToDisplay to send values to the post game screen too?
					
					//change the game state from play to the post game state
					at_play = false;
					at_post_game = true;
					
					//stop the in game timer from counting
					inGameTimer.stop();
				}
				//send whatever time has passed to the game window
				sendTimeToDisplay(initialInGameTime-currentInGameTick+additionalInGameTime);
			}
			else //start 5 second count down timer to allow players to prepare
			{
				//count down from 5 to 1
				if(currentInGameTick < 5)
				{
					sendTimeToDisplay(5 - currentInGameTick);
				}
				//if count down reaches 0, actually start the game
				if(5 - currentInGameTick <= 0)
				{
					//the 5 second count down has finished, allow players to play notes
					gameStart = false;
					currentInGameTick = -1;
				}
			}
			
			//increase the in game tick count every second
			currentInGameTick++;
		}
	}

	/**
	 *	Adds messages to a pool waiting to be translated and sent to GUI
	 *
	 *	@param message the message to be translated
	 */
	public synchronized void addMessageToPool(String message)
	{
		//this helps with synchronization issues
		messagePool.add(message);
		
		//if a message was received
		if(messagePool.size() > 0)
		{
			//constrict message pool size every tick
			messages = new ArrayList<String>(messagePool);
			messagePool = new ArrayList<String>();
			
			//check messages for correct format (initially create a Note if message is a Note)
			this.translateMessagePool();

			//if the game has started
			if(at_play)
			{
				//take the Note played and put it into a measure and split it up accordingly
				this.constructMeasures();
				
				//make sure the note is in the correct key for the measure
				this.checkNotesForCorrectness();
				
				//award the player for playing correct notes
				this.scoreNotes();
				
				//send the player's scores to the display
				this.sendScoresToDisplay();
				
				//send the notes played to the display
				this.sendNotesToDisplay();
				
				//if the game is not done
				if(!gameIsDone)
				{
					//if the number of bars allowed is exceeded or if the the number of bars is reached and the last measure is filled with notes
					if(gameMeasures.size()>numberOfBars || (gameMeasures.size()==numberOfBars && gameMeasures.get(gameMeasures.size()-1).getNumberOfAvailableBeats()==0))
					{
						//set the game to true
						gameIsDone = true;
					}
				}
				
				//if the game is done
				if(gameIsDone)
				{
					//tell WindowManager to go to the post game screen
					WindowManager.getInstance().goToWindow(GameState.POST_GAME);
					
					//TODO send post game screen values it needs here
					//perhaps modify sendScoresToDisplay to send values to the post game screen too?
					
					//change game state from play to post game
					at_play = false;
					at_post_game = true;
					
					//stop the in game timer from counting
					inGameTimer.stop();
				}		
			}
		}
	}

	/**
	 *	Translate the messages to Note objects and Message objects for easier manipulation
	 *
	 *	All of the game state changes in this method
	 */
	private synchronized void translateMessagePool()
	{
		//the Message object, either holds a Note or a message to be further translated
		//whatever this holds is guaranteed to be correctly formatted
		MessageTranslationEngine.Message m;

		//for all of the limited amount of messages
		for(String message : messages)
		{
			//translate the message, check for errors
			m = MessageTranslationEngine.translateMessage(message);
			
			//if at the play state, the initial 5 second timer is not ticking, and the message holds a note
			//this is first because this is the most common message sent
			if(at_play && !gameStart && m.isNote())
			{
				//TODO finish commenting this beast
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
				sendCurrentPlayerToDisplay();
			}
			else
			{	
				//     DISCONNECT     //
				if(m.getMessage().split("_")[1].equals("disconnect"))
				{
					inGameTimer.stop();
					
					numberOfActivePlayers = ConnectionManager.getInstance().listPlayers().size();
					
					//reset values only when player 1 disconnects
					if(m.getMessage().split("_")[0].equals(Players.PLAYER_ONE))
					{
						tempo = 120;
						key = "CMajor";
						numberOfBars = 16;
						timeSignatureNumerator = 4;
						timeSignatureDenominator = 4;
						this.setKey(key);
						this.setNumberOfBars(numberOfBars);
						this.setTempo(tempo);
						this.setTimeSignature(timeSignatureNumerator, timeSignatureDenominator);
					}
					
					
					//clear other game state
					gameBeats = new ArrayList<Beat>();
					gameNotes = new ArrayList<Note>();
					gameMeasures = new ArrayList<Measure>();
					gameCurrentNote = 0;
					
					gameStart = true;
					gameIsDone = false;
					
					messagePool = new ArrayList<String>();
					messages = new ArrayList<String>();

					currentInGameTick = 0;
					additionalInGameTime = 0;

					player1Score = 1337;
					player2Score = 1337;
					player3Score = 1337;
					player4Score = 1337;
					
					currentPlayerId = Players.PLAYER_ONE;
					sendCurrentPlayerToDisplay();
					pausedPlayerId = new String();

					notesToSend = new ArrayList<Note>();

					//show all players disconnecting
					splashWindow.disconnectPlayer(1);
					splashWindow.disconnectPlayer(2);
					splashWindow.disconnectPlayer(3);
					splashWindow.disconnectPlayer(4);
					
					//show all players that are still connected
					for(String plyr : ConnectionManager.getInstance().listPlayers())
						splashWindow.connectPlayer(Integer.parseInt(plyr.substring(plyr.length()-1,plyr.length())));
					
					gameWindow.reset();
					
					if(!at_splash_screen)
					{
						at_splash_screen = true;
						at_game_options = false;
						at_play = false;
						at_pause = false;
						at_post_game = false;
						
						WindowManager.getInstance().goToWindow(GameState.SPLASH_SCREEN);
					}
				}
				
				//     SPLASH SCREEN     //
				//message is "playerX_connect"
				else if(at_splash_screen && m.getMessage().split("_")[1].equals("connect"))
				{
					numberOfActivePlayers++;
					splashWindow.connectPlayer(m.getMessage().split("_")[0].charAt(m.getMessage().split("_")[0].length()-1)-48);
				}
				//message is "player1_gamemodes"  (checking to make sure player 1 has sent the message)
				else if(at_splash_screen && numberOfActivePlayers>0 && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.GAME_OPTIONS))
				{
					WindowManager.getInstance().goToWindow(GameState.GAME_OPTIONS);
					
					at_splash_screen = false;
					at_game_options = true;
					sendNumberOfPlayersToDisplay();
				}
				
				//     GAME OPTIONS     //
				//message is "player1_splashscreen"  (checking to make sure player 1 has sent the message)
				else if(at_game_options && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.SPLASH_SCREEN))
				{
					WindowManager.getInstance().goToWindow(GameState.SPLASH_SCREEN);
					at_game_options = false;
					at_splash_screen = true;
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
					gameWindow.setTime(5);
					
					WindowManager.getInstance().goToWindow(GameState.PLAY);
					
					at_game_options = false;
					at_play = true;
										
					inGameTimer.start();
				}
				
				//     PLAY     //
				//message is "playerX_pause"
				else if(at_play && m.getMessage().split("_")[1].equals(GameState.PAUSE))
				{
					WindowManager.getInstance().goToWindow(GameState.PAUSE);
					at_play = false;
					at_pause = true;
					inGameTimer.stop();
					pausedPlayerId = new String(m.getMessage().split("_")[0]);
				}

				//     PAUSE     //
				//message is "playerX_play"  (checking to make sure player 1 has sent the message)
				else if(at_pause && m.getMessage().split("_")[1].equals(GameState.PLAY) && m.getMessage().split("_")[0].equals(pausedPlayerId))
				{
					WindowManager.getInstance().goToWindow(GameState.PLAY);
					at_pause = false;
					at_play = true;
					inGameTimer.start();
				}
				//message is "player1_playsong"
				else if(at_pause && m.getMessage().split("_")[1].equals(GameState.PLAY_SONG))
				{
					org.jfugue.Player jfuguePlayer = new org.jfugue.Player();
					org.jfugue.Pattern s = new org.jfugue.Pattern();
					s.add("T"+tempo+" ");
					for(Measure measure : gameMeasures)
						for(Note note : measure.getNotes())
							s.add(note.getJFuguePattern());
					jfuguePlayer.play(s);
				}
				
				//     POST GAME     //
				//message is "player1_playsong"
				else if(at_post_game && m.getMessage().split("_")[1].equals(GameState.PLAY_SONG))
				{
					org.jfugue.Player jfuguePlayer = new org.jfugue.Player();
					org.jfugue.Pattern s = new org.jfugue.Pattern();
					s.add("T"+tempo+" ");
					for(Measure measure : gameMeasures)
						for(Note note : measure.getNotes())
							s.add(note.getJFuguePattern());
					jfuguePlayer.play(s);
				}
				//message is "player1_splashscreen"  (checking to make sure player 1 has sent the message)
				else if(at_post_game && m.getMessage().split("_")[0].equals(keys.Players.PLAYER_ONE) && m.getMessage().split("_")[1].equals(GameState.SPLASH_SCREEN))
				{
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

					currentInGameTick = 0;
					additionalInGameTime = 0;

					player1Score = 1337;
					player2Score = 1337;
					player3Score = 1337;
					player4Score = 1337;
					
					currentPlayerId = Players.PLAYER_ONE;
					sendCurrentPlayerToDisplay();
					pausedPlayerId = new String();
					
					notesToSend = new ArrayList<Note>();
					
					gameWindow.reset();
					
					int oldNumberOfPlayers = numberOfActivePlayers;
					numberOfActivePlayers = 0;

					for(int i = 0; i < oldNumberOfPlayers; i++)
					{
						addMessageToPool("player" + (i+1) + "_" + GameState.CONNECT);
					}
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
	private synchronized void constructMeasures()
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
	
	/**
	 * Sets notes to be sent to the display to correct or incorrect depending on what the current key is
	 */
	private synchronized void checkNotesForCorrectness()
	{
		//for all notes recently played
		for(Note note : notesToSend)
		{
			//if the note is not a rest
			if(!note.getPitch().equals("rest"))
			{
				//check the notes recently played to see if they match the note being tested
				for(int i = gameMeasures.size()-1; i >=0; i--)
				{
					//if this measure contains this note
					if(gameMeasures.get(i).getNotes().contains(note))
					{
						//if the measure the note is in is within the range of the game
						if(keyProgression.size()>i)
						{
							//if the note is in the key
							if(!(Scales.isNoteInKey(note, keyProgression.get(i))))
							{
								//set the note to be incorrect
								note.setCorrect(false);
							}
						}
						//found the note, so stop looking
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Updates the player's scores and the available in game time
	 * 
	 * Scores and available play time increases if the note is in the current key
	 * TODO change score increases and time increases for better game play, just needs to be played
	 */
	private synchronized void scoreNotes()
	{
		double score;
		Note previousNotePlayed = null;
		
		//for all recently played notes
		for(Note note : notesToSend)
		{
			//reinitialize score
			score = 0;
			
			if(note.getPlayer().equals(Players.PLAYER_ONE))
			{
				//convert score from an integer to a double
				score = player1Score;
				//the last note played by player 1 is stored in previousNotePlayed
				previousNotePlayed = previousNotePlayed1;
			}
			else if(note.getPlayer().equals(Players.PLAYER_TWO))
			{
				//convert score from an integer to a double
				score = player2Score;
				//the last note played by player 1 is stored in previousNotePlayed
				previousNotePlayed = previousNotePlayed2;
			}
			else if(note.getPlayer().equals(Players.PLAYER_THREE))
			{
				//convert score from an integer to a double
				score = player3Score;
				//the last note played by player 1 is stored in previousNotePlayed
				previousNotePlayed = previousNotePlayed3;
			}
			else if(note.getPlayer().equals(Players.PLAYER_FOUR))
			{
				//convert score from an integer to a double
				score = player4Score;
				//the last note played by player 1 is stored in previousNotePlayed
				previousNotePlayed = previousNotePlayed4;
			}
			
			//if the note is correct
			if(note.isCorrect())
			{
				//if the note is a rest
				if(note.getPitch().equals("rest"))
				{
					//give a small bonus for playing a rest
					score += Math.PI * 10;
					additionalInGameTime += 3;
				}
				else
				{
					//check the last note played overall players
					if(previousNote != null)
					{
						//if the pitch of the note being scored does not match the overall last note played
						if(!(note.getPitch().equals(previousNote.getPitch())))
						{
							//reward a small bonus
							score += Math.PI * 25;
							additionalInGameTime += 1;
						}
						//if the length of the note being scored does not match the overall last note played
						if(!(note.getLength().equals(previousNote.getLength())))
						{
							//reward a small bonus
							score += Math.PI * 25;
							additionalInGameTime += 1;
						}
						//if the note being scored does not match the overall last note played
						if(!note.equals(previousNote))
						{
							//reward a large bonus
							score += Math.PI * 100;
							additionalInGameTime += 6;
						}
					}
					else //if the first note played overall
					{
						score += Math.PI * 100;
						additionalInGameTime += 6;
					}
					
					//check the last note played by that player
					if(previousNotePlayed != null)
					{
						//if the pitch of the note being scored does not match the player's last note played
						if(!(note.getPitch().equals(previousNotePlayed.getPitch())))
						{
							//reward a small bonus
							score += Math.PI * 25;
							additionalInGameTime += 1;
						}
						//if the length of the note being scored does not match the player's last note played
						if(!(note.getLength().equals(previousNotePlayed.getLength())))
						{
							//reward a small bonus
							score += Math.PI * 25;
							additionalInGameTime += 1;
						}
						//if the note being scored does not match the player's last note
						if(!note.equals(previousNotePlayed))
						{
							//reward a large bonus
							score += Math.PI * 100;
							additionalInGameTime += 6;
						}
					}
					else //if the first note played by this player
					{
						score += Math.PI * 100;
						additionalInGameTime += 6;
					}
					
					//check to make sure the note that was just played is within the range of the game
					if(gameMeasures.size()<=keyProgression.size())
					{
						//award bonus points and time if the note is in the arpeggio of the key 
						if(Scales.isNoteInArpeggio(note, keyProgression.get(gameMeasures.size()-1)))
						{
							score += Math.PI * 50;
							additionalInGameTime += 3;
						}
					}
				}
			}
			else //a wrong note was played
			{
				//subtract points
				score -= Math.E * 100;
				
				//make sure a player's score cannot go below zero
				if(score<0)
					score = 0;
			}
			
			if(note.getPlayer().equals(Players.PLAYER_ONE))
			{
				//convert score back to an integer
				player1Score = new Double(score).intValue();
				//store the last note player 1 played
				previousNotePlayed1 = new Note(note.getPitch(),note.getLength(),note.getPlayer());
			}
			else if(note.getPlayer().equals(Players.PLAYER_TWO))
			{
				//convert score back to an integer
				player2Score = new Double(score).intValue();
				//store the last note player 2 played
				previousNotePlayed2 = new Note(note.getPitch(),note.getLength(),note.getPlayer());
			}
			else if(note.getPlayer().equals(Players.PLAYER_THREE))
			{
				//convert score back to an integer
				player3Score = new Double(score).intValue();
				//store the last note player 3 played
				previousNotePlayed3 = new Note(note.getPitch(),note.getLength(),note.getPlayer());
			}
			else if(note.getPlayer().equals(Players.PLAYER_FOUR))
			{
				//convert score back to an integer
				player4Score = new Double(score).intValue();
				//store the last note player 4 played
				previousNotePlayed4 = new Note(note.getPitch(),note.getLength(),note.getPlayer());
			}
			
			//store the last note played for any player
			previousNote = new Note(note.getPitch(),note.getLength(),note.getPlayer());
		}
	}
	
	private synchronized void setNumberOfBeatsPerMeasure()
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
	
	private synchronized void setGameMode(String game)
	{
		gameMode = new String(game);
		
		if(gameMode.equals(GameState.KEY_MASTER))
		{
			keyMaster = new gameManagement.gameModes.KeyMaster(key,numberOfBars,2);
			keyProgression = keyMaster.getKeyProgression();
			gameWindow.setChords(keyProgression);
		}
		else
		{
			keyProgression = null;
		}
	}
	
	private synchronized void setTempo(int t)
	{
		tempo = t;
		gameOptionsWindow.setTempo(tempo);
	}
	
	private synchronized void setKey(String k)
	{
		key = k;
		gameOptionsWindow.setKey(key);
		gameWindow.setKeySignature(key);
	}
	
	private synchronized void setNumberOfBars(int n)
	{
		numberOfBars = n;
		gameOptionsWindow.setMeasureCount(numberOfBars);
	}
	
	private synchronized void setTimeSignature(int n, int d)
	{
		timeSignatureNumerator = n;
		timeSignatureDenominator = d;
		setNumberOfBeatsPerMeasure();
		gameOptionsWindow.setTime(timeSignatureNumerator, timeSignatureDenominator);
	}

	private synchronized void sendScoresToDisplay()
	{
		gameWindow.setScore(Players.PLAYER_ONE, player1Score);
		gameWindow.setScore(Players.PLAYER_TWO, player2Score);
		gameWindow.setScore(Players.PLAYER_THREE, player3Score);
		gameWindow.setScore(Players.PLAYER_FOUR, player4Score);
	}
	
	private synchronized void sendNumberOfPlayersToDisplay()
	{
		gameWindow.setPlayerCount(numberOfActivePlayers);
	}
		
	private synchronized void sendTimeToDisplay(int time)
	{
		gameWindow.setTime(time);
	}
	
	private synchronized void sendCurrentPlayerToDisplay()
	{
		gameWindow.setCurrentPlayer(currentPlayerId);
	}
	
	/**
	 *	Sends a Note to the GUI
	 *
	 *	@param note the Note to be sent to the GUI
	 */
	private synchronized void sendNotesToDisplay()
	{		
		for(Note note : notesToSend)
		{
			gameWindow.addNote(note);
			//TODO add this back in
		}
		notesToSend = new ArrayList<Note>();
	}
	
}