package displayManager.messageTranslationSystem;

import java.util.HashMap;

public class MessageTranslationEngine
{
	private static HashMap<String, String> pitches = new HashMap<String, String>();
	private static HashMap<String, String> lengths = new HashMap<String, String>();

	/**
	 *	Initializes both the 'pitches' and the 'lengths' HashMaps used to translate a Note from a message
	 */
	private static void initialize()
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
		lengths.put("eighthnote", "i");
		lengths.put("quarternote", "q");
		lengths.put("halfnote", "h");
		lengths.put("wholenote", "w");
	}

	/**
	 * Translates a message from String and either sends a Message or constructs a note
	 * Possible message formats 	:	playerX_connect
	 *								:	playerX_disconnect
	 *								:	playerX_pitch_length
	 *
	 * Other messages for state management are in development
	 *
	 * @param message a message received within the last timer duration
	 */
	public static Message translateMessage(String message)
	{
		if(pitches.size()==0 || lengths.size()==0)
			MessageTranslationEngine.initialize();

		String[] messageComponents;
		String pitch;
		String length;

		messageComponents = message.split("_");

		Message newMessage = null;

		if(messageComponents.length == 2)
		{
			if(messageComponents[1].equals("connect"))
			{
				System.out.println(message);
				//send something to the DisplayGUI that animates the connection of some player
				//the player number is in messageComponents[0] as PlayerX
			}
			else if(messageComponents[1].equals("disconnect"))
			{
				System.out.println(message);
				//send something to the DisplayGUI that animates the disconnection of some player
				//the player number is in messageComponents[0] as PlayerX
			}
			else
			{
				System.err.println("Incorrect message format in MessageTranslationEngine.receiveMessage(ArrayList<String> messages) of length 2 : " + messageComponents[0] + " " + messageComponents[1]);
			}
		}
		else if(messageComponents.length == 3)
		{
			pitch = messageComponents[1];
			length = messageComponents[2];

			if(pitches.containsKey(pitch))
			{
				pitch = pitches.get(pitch);
			}
			else
			{
				System.err.println("Incorrect message format in MessageTranslationEngine.receiveMessage(ArrayList<String> messages) of length 3 in [1] : " + messageComponents[1]);
				pitch = "R";
			}

			if(lengths.containsKey(length))
			{
				length = lengths.get(length);
			}
			else
			{
				System.err.println("Incorrect message format in MessageTranslationEngine.receiveMessage(ArrayList<String> messages) of length 3 in [2] : " + messageComponents[2]);
				length = "i";
			}

			if(messageComponents[0].equals("player1"))
			{
				newMessage = new Message(messageComponents[0]);
				newMessage.setNote(new Note(pitch,length,messageComponents[0]));
			}
			else if(messageComponents[0].equals("player2"))
			{
				newMessage = new Message(messageComponents[0]);
				newMessage.setNote(new Note(pitch,length,messageComponents[0]));
			}
			else if(messageComponents[0].equals("player3"))
			{
				newMessage = new Message(messageComponents[0]);
				newMessage.setNote(new Note(pitch,length,messageComponents[0]));
			}
			else if(messageComponents[0].equals("player4"))
			{
				newMessage = new Message(messageComponents[0]);
				newMessage.setNote(new Note(pitch,length,messageComponents[0]));
			}
			else if(messageComponents[0].equals("metronome"))
			{
				newMessage = new Message(messageComponents[0]);
				newMessage.setNote(new Note(pitch,length,messageComponents[0]));
			}
			else
			{
				System.err.println("Incorrect message format in MessageTranslationEngine.receiveMessage(ArrayList<String> messages) of length 3 in [0] : " + messageComponents[0]);
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
		private String _message;
		private Note _note;

		public Message(String message)
		{
			_message = message;
			_note = null;
		}

		public String getMessage()
		{
			return _message;
		}

		protected void setNote(Note note)
		{
			_note = note;
		}

		public boolean isNote()
		{
			if(_note == null)
				return false;
			return true;
		}

		public Note getNote()
		{
			return _note;
		}
	}
}