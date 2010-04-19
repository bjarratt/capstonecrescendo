package gameManagement.messageTranslationSystem;

import java.util.HashMap;

import keys.GameState;
import keys.Players;

public class MessageTranslationEngine
{
	public static HashMap<String, String> pitches = new HashMap<String, String>();
	public static HashMap<String, String> lengths = new HashMap<String, String>();

	/**
	 *	Initializes both the 'pitches' and the 'lengths' HashMaps used to translate a Note from a message
	 */
	static
	{
		//set up the available note pitches
		pitches.put("E5", "E5");
		pitches.put("ESharp5", "E#5");
		pitches.put("F5", "F5");
		pitches.put("FSharp5", "F#5");
		pitches.put("G5", "G5");
		pitches.put("GSharp5", "G#5");
		pitches.put("A5", "A5");
		pitches.put("ASharp5", "A#5");
		pitches.put("B5", "B5");
		pitches.put("C6", "C6");
		pitches.put("CSharp6", "C#6");
		pitches.put("D6", "D6");
		pitches.put("DSharp6", "D#6");
		pitches.put("E6", "E6");
		pitches.put("ESharp6", "E#6");
		pitches.put("F6", "F6");
		pitches.put("FSharp6", "F#6");

		//A rest is also specified in pitches
		pitches.put("rest", "R");

		//set up the available note lengths
		lengths.put("eighth", "i");
		lengths.put("quarter", "q");
		lengths.put("half", "h");
		lengths.put("whole", "w");
	}

	/**
	 * Translates a message from String and either sends a Message or constructs a note
	 * @param message a message received within the last timer duration
	 */
	public static Message translateMessage(String message)
	{
		String[] messageComponents;
		String one;
		String two;
		
		messageComponents = message.split("_");

		Message newMessage = new Message(message);

		if(messageComponents.length == 2)
		{
			one = messageComponents[1];
			
			//I don't think these are absolutely necessary... maybe to check for bad messages?
			if(	one.equals(GameState.CONNECT) ||
				one.equals(GameState.DISCONNECT) ||
				one.equals(GameState.SPLASH_SCREEN) ||
				one.equals(GameState.GAME_MODES) ||
				one.equals(GameState.GAME_INFO) ||
				one.equals(GameState.GAME_OPTIONS) ||
				one.equals(GameState.PLAY) ||
				one.equals(GameState.PAUSE) ||
				one.equals(GameState.LENGTH_TRAINING) ||
				one.equals(GameState.PITCH_TRAINING) ||
				one.equals(GameState.NOTE_TRAINING) ||
				one.equals(GameState.CONCERT_MASTER) ||
				one.equals(GameState.MUSICAL_IPHONES) ||
				one.equals(GameState.NOTES_AROUND_THE_ROOM) ||
				one.equals(GameState.COMP_TIME) ||
				one.equals(GameState.POST_GAME) ||
				one.equals(GameState.REVIEW) ||
				one.equals(GameState.EXIT))
			{
				//all good!  :)
			}
			else
			{
				System.err.println("Incorrect message format in MessageTranslationEngine.receiveMessage(ArrayList<String> messages) of length 2 : " + message);
			}
		}
		else if(messageComponents.length == 3)
		{
			one = messageComponents[1];
			two = messageComponents[2];
			
			if(	one.equals(GameState.SET_TEMPO) ||
				one.equals(GameState.SET_KEY) ||
				one.equals(GameState.SET_NUMBER_OF_BARS))
			{
				//all good! :)
			}
			else if(pitches.containsKey(one) && lengths.containsKey(two) && (messageComponents[0].equals(Players.PLAYER_ONE) || messageComponents[0].equals(Players.PLAYER_TWO) || messageComponents[0].equals(Players.PLAYER_THREE) || messageComponents[0].equals(Players.PLAYER_FOUR)))
			{
				newMessage.setNote(new Note(one,two,messageComponents[0]));
			}
			else
			{
				System.err.println("Incorrect message format in MessageTranslationEngine.receiveMessage(ArrayList<String> messages) of length 3 : " + message);
			}
		}
		else if(messageComponents.length == 4)
		{
			one = messageComponents[1];
			
			if(one.equals(GameState.SET_TIME_SIGNATURE))
			{
				//all good :)
			}
			else
			{
				System.err.println("Incorrect message format in MessageTranslationEngine.receiveMessage(ArrayList<String> messages) of length 4 : " + message);
			}
		}
		else
		{
			System.err.println("Incorrect message format in MessageTranslationEngine.receiveMessage(ArrayList<String> messages) : " + message);
		}

		return newMessage;
	}

	/**
	 *	A static class that wraps a Note or a String message and sends it back to the GameManager
	 *
	 *	@author Travis Kosarek
	 */
	public static class Message
	{
		private String message;
		private Note note;

		public Message(String message)
		{
			this.message = new String(message);
			this.note = new Note("rest","eighth","unknown");
		}

		public String getMessage()
		{
			return message;
		}

		protected void setNote(Note note)
		{
			this.note = note;
		}

		public boolean isNote()
		{
			if(!note.getPlayer().equals(Players.PLAYER_ONE) && !note.getPlayer().equals(Players.PLAYER_TWO) &&!note.getPlayer().equals(Players.PLAYER_THREE) && !note.getPlayer().equals(Players.PLAYER_FOUR))
				return false;
			return true;
		}

		public Note getNote()
		{
			return note;
		}
	}
}