package displayManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *	The DisplayManager class is the general tie between the DisplayGUI and the audio from JFugue
 *
 *	@author Travis Kosarek
 */
public class DisplayManager
{
	private DisplayGUI myDisplayGUI;

	private HashMap<String, String> pitches = new HashMap<String, String>();
	private HashMap<String, String> lengths = new HashMap<String, String>();

	final int maxSubdivisions;
	int currentSubdivisions;

	ArrayList<Note> player1;
	ArrayList<Note> player2;
	ArrayList<Note> player3;
	ArrayList<Note> player4;
	ArrayList<Note> metronome;

	private static int ticks = 0;

	public DisplayManager()
	{
		//set up the DisplayGUI
		myDisplayGUI = new DisplayGUI();

		pitches.put("D5", "D5");
		pitches.put("DSharp5", "D#5");
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

		//A rest is also specified in pitches
		pitches.put("rest", "R");

		lengths.put("eighthnote", "i");
		lengths.put("quarternote", "q");
		lengths.put("halfnote", "h");
		lengths.put("wholenote", "w");

		maxSubdivisions = 8;
		currentSubdivisions = 0;

		player1 = new ArrayList<Note>();
		player2 = new ArrayList<Note>();
		player3 = new ArrayList<Note>();
		player4 = new ArrayList<Note>();
		metronome = new ArrayList<Note>();
	}

	/**
	 * Receive a message from the GameManager, translates the message, and inserts it correctly as a set of beats
	 * Possible message formats 	:	iPhoneX_connect
	 *								:	iPhoneX_disconnect
	 *								:	iPhoneX_pitch_length
	 *
	 * other messages? menu selection?
	 *
	 * @param messages the messages received within the last timer duration
	 */
	public void receiveMessage(ArrayList<String> messages)
	{
		String[] messageComponents;
		String pitch;
		String length;

		for(String message : messages)
		{
			messageComponents = message.split("_");

			if(messageComponents.length == 2)
			{
				if(messageComponents[1].equals("connect"))
				{
					//send something to the DisplayGUI that animates the connection of some player
					//the player number is in messageComponents[0] as PlayerX
				}
				else if(messageComponents[1].equals("disconnect"))
				{
					//send something to the DisplayGUI that animates the disconnection of some player
					//the player number is in messageComponents[0] as PlayerX
				}
				else
				{
					System.err.println("Incorrect message format in DisplayManager.receiveMessage(ArrayList<String> messages) of length 2 : " + messageComponents[0] + " " + messageComponents[1]);
				}
			}
			else if(messageComponents.length == 3)
			{
				pitch = messageComponents[1];
				length = messageComponents[2];

				if(isValidPitch(pitch))
				{
					pitch = pitches.get(pitch);
				}
				else
				{
					System.err.println("Incorrect message format in DisplayManager.receiveMessage(ArrayList<String> messages) of length 3 in [1] : " + messageComponents[1]);
					pitch = "R";
				}

				if(isValidLength(length))
				{
					length = lengths.get(length);
				}
				else
				{
					System.err.println("Incorrect message format in DisplayManager.receiveMessage(ArrayList<String> messages) of length 3 in [2] : " + messageComponents[2]);
					length = "i";
				}

				if(messageComponents[0].equals("player1"))
				{
					player1.add(new Note(pitch,length,"player1"));
					player2.add(new Note("R",length,"player2"));
					player3.add(new Note("R",length,"player3"));
					player4.add(new Note("R",length,"player4"));
				}
				else if(messageComponents[0].equals("player2"))
				{
					player1.add(new Note("R",length,"player1"));
					player2.add(new Note(pitch,length,"player2"));
					player3.add(new Note("R",length,"player3"));
					player4.add(new Note("R",length,"player4"));
				}
				else if(messageComponents[0].equals("player3"))
				{
					player1.add(new Note("R",length,"player1"));
					player2.add(new Note("R",length,"player2"));
					player3.add(new Note(pitch,length,"player3"));
					player4.add(new Note("R",length,"player4"));
				}
				else if(messageComponents[0].equals("player4"))
				{
					player1.add(new Note("R",length,"player1"));
					player2.add(new Note("R",length,"player2"));
					player3.add(new Note("R",length,"player3"));
					player4.add(new Note(pitch,length,"player4"));
				}
				else if(messageComponents[0].equals("metronome"))
				{
					metronome.add(new Note(pitch,length,"metronome"));
				}
				else
				{
					System.err.println("Incorrect message format in DisplayManager.receiveMessage(ArrayList<String> messages) of length 3 in [0] : " + messageComponents[0]);
				}
			}
			else
			{
				System.err.println("Incorrect message format in DisplayManager.receiveMessage(ArrayList<String> messages) : " + message);
			}
		}

		ticks++;

		printNotes();
	}

	private boolean isValidPitch(String p)
	{
		if(	p.equals("D5") ||
			p.equals("DSharp5") ||
			p.equals("E5") ||
			p.equals("ESharp5") ||
			p.equals("F5") ||
			p.equals("FSharp5") ||
			p.equals("G5") ||
			p.equals("GSharp5") ||
			p.equals("A5") ||
			p.equals("ASharp5") ||
			p.equals("B5") ||
			p.equals("C6") ||
			p.equals("CSharp6") ||
			p.equals("D6") ||
			p.equals("DSharp6") ||
			p.equals("E6") ||
			p.equals("ESharp6") ||
			p.equals("F6") ||
			p.equals("rest"))
				return true;
		return false;
	}

	private void printNotes()
	{
		System.out.println("Player 1:");
		for(Note note : player1)
			System.out.println("\t" + note);

		System.out.println("Player 2:");
		for(Note note : player2)
			System.out.println("\t" + note);

		System.out.println("Player 3:");
		for(Note note : player3)
			System.out.println("\t" + note);

		System.out.println("Player 4:");
		for(Note note : player4)
			System.out.println("\t" + note);

		System.out.println("\n--------------------\n");
	}

	private boolean isValidLength(String p)
	{
		if(	p.equals("eighthnote") ||
			p.equals("quarternote") ||
			p.equals("halfnote") ||
			p.equals("wholenote"))
				return true;
		return false;
	}
}
