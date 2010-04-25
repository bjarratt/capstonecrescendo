package keys;

/**
 *	GameState holds all of the messages that are possible from the iPhone that effect the game state
 *	For instance Players.PLAYER_ONE + "_" + GameState.SPLASH_SCREEN is a valid message
 *
 *	@author Travis Kosarek
 */
public class GameState
{
	public final static String DISCONNECT = "disconnect";
	
	public final static String SPLASH_SCREEN = "splashscreen";
	public final static String CONNECT = "connect";
		
	public final static String GAME_MODES = "gamemodes";
	
	// *******************************************************************************
	public final static String LENGTH_TRAINING = "lengthtraining";
	public final static String PITCH_TRAINING = "pitchtraining";
	public final static String NOTE_TRAINING = "notetraining";
	public final static String CONCERT_MASTER = "concertmaster";
	public final static String MUSICAL_IPHONES = "musicaliphones";
	public final static String NOTES_AROUND_THE_ROOM = "notesaroundtheroom";
	public final static String COMP_TIME = "comptime";
	public final static String COMPOSE = "compose";
	public final static String KEY_MASTER = "keymaster";

	public final static String SCALES = "scales";
	// *******************************************************************************
	
	public final static String GAME_INFO = "gameinfo";
	
	public final static String GAME_OPTIONS = "gameoptions";
	public final static String SET_TEMPO = "settempo";
	public final static String SET_KEY = "setkey";
	public final static String SET_TIME_SIGNATURE = "settimesignature";
	public final static String SET_NUMBER_OF_BARS = "setnumberofbars";
	
	public final static String PLAY = "play";
	
	public final static String PAUSE = "pause";
	
	public final static String POST_GAME = "postgame";
	public final static String REVIEW = "review";
	
	public final static String EXIT = "exit";
}