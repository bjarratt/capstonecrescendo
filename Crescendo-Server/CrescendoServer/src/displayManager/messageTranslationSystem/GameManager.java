package displayManager.messageTranslationSystem;

import java.util.ArrayList;

import javax.swing.JFrame;
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
	public static String GAME_MANAGER = "GAME_MANAGER";
	
	private DisplayGUI displayGUI;
	private ArrayList<String> messagePool;
	private ArrayList<String> messages;
	private Timer timer;
	private static int delay = 500;
	//set the timeout value to 300 delay periods (in this case 300 seconds = 2.5 min)
	private static int timeout = 300;
	private int ticks;
	private int currentNote;
	private int currentBeat;
	private boolean timeout_to_menu;
	private boolean exit;

	private ArrayList<Note> player1;
	private ArrayList<Note> player2;
	private ArrayList<Note> player3;
	private ArrayList<Note> player4;
	private ArrayList<Note> metronome;
	private int numberOfActivePlayers;
	private int turn;

	private ArrayList<Measure> song;
	private int numberOfNotesPlayed;
	
	ArrayList<Note> songNotes;

	/**
	 *	Constructs a GameManager object with upto 4 players
	 */
	public GameManager()
	{
		//set up the display to receive messages
		displayGUI = new DisplayGUI();
		//message pool
		messagePool = new ArrayList<String>();
		//messages to be translated
		messages = new ArrayList<String>();
		//set up a timer
		timer = new Timer(delay,this);
		ticks = 0;
		currentNote = 0;
		currentBeat = 0;

		timeout_to_menu = false;
		exit = false;

		player1 = new ArrayList<Note>();
		player2 = new ArrayList<Note>();
		player3 = new ArrayList<Note>();
		player4 = new ArrayList<Note>();
		metronome = new ArrayList<Note>();
		numberOfActivePlayers = 1; //THIS NEEDS TO BE CHANGED TO THE ACTUAL NUMBER OF PLAYERS (not a static 4 value)
		turn = -1; //starts at turn 0, see 'constructMeasures()'

		song = new ArrayList<Measure>();
		song.add(new Measure());
		numberOfNotesPlayed = 0;
		
		songNotes = new ArrayList<Note>();
	}

	/**
	 *	When a Timer ticks, this method is called and performs some action
	 */
	public void actionPerformed(ActionEvent event)
	{
		if(messagePool.size() == 0)
		{
			ticks++;
			if(ticks >= timeout)
			{
				timeout_to_menu = true;
				ticks = 0;
			}
		}
		else
		{
			ticks = 0;
			messages = new ArrayList<String>(messagePool);
			messagePool = new ArrayList<String>();
			this.condenseMessagePool();
			this.translateMessagePool();
			this.constructMeasures();
			this.sendNoteToDisplayGUI();
			currentBeat++;
		}
	}

	/**
	 *	Starts the game loop
	 */
	public void run()
	{
		System.out.println("Starting Game Loop in...");
		System.out.println("Game Loop Initializing...");

		timer.start();
		
		//create a new display window
		JFrame window = new JFrame("Crescendo");
		window.setBackground(Color.WHITE);
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.getContentPane().add(displayGUI);
		window.pack();
		window.setVisible(true);

		while(true)
		{
			if(exit == true)
				break;
			if(timeout_to_menu == true)
			{
				System.out.println("No messages were sent for " + timeout + " ticks");
				//return to menu if no messages were sent
				timeout_to_menu = false;
				//for now, exit when timeout
				exit = true;
			}
		}
		timer.stop();
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
		boolean met = false;

		ArrayList<String> newMessages = new ArrayList<String>();

		for(String message : messages)
		{
			if(message.split("_")[0].equals("player1") && !p1)
			{
				p1 = true;
				newMessages.add(message);
			}
			if(message.split("_")[0].equals("player2") && !p2)
			{
				p2 = true;
				newMessages.add(message);
			}
			if(message.split("_")[0].equals("player3") && !p3)
			{
				p3 = true;
				newMessages.add(message);
			}
			if(message.split("_")[0].equals("player4") && !p4)
			{
				p4 = true;
				newMessages.add(message);
			}
			if(message.split("_")[0].equals("metronome") && !met)
			{
				met = true;
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
			if(m.isNote())
			{
				Note n = m.getNote();

				if(n.getPlayer().equals("player1"))
				{
					player1.add(n);
					songNotes.add(n);
					numberOfNotesPlayed++;
				}
				else if(n.getPlayer().equals("player2"))
				{
					player2.add(n);
					songNotes.add(n);
					numberOfNotesPlayed++;
				}
				else if(n.getPlayer().equals("player3"))
				{
					player3.add(n);
					songNotes.add(n);
					numberOfNotesPlayed++;
				}
				else if(n.getPlayer().equals("player4"))
				{
					player4.add(n);
					songNotes.add(n);
					numberOfNotesPlayed++;
				}
				else if(n.getPlayer().equals("metronome"))
				{
					metronome.add(n);
					songNotes.add(n);
					numberOfNotesPlayed++;
				}
			}
			else
			{
				//handle messages such as game type, connect, disconnect, etc.
				sendMessageToDisplayManager(m.getMessage());
			}
		}
	}

	/**
	 *	From the Note's played by each player, correctly formmated Measures are created
	 */
	private void constructMeasures()
	{
		//this is constructing measures based off of the turn
		//each player must have taken his/her turn before it continues
		//this needs to be fixed to handle a number of players other than strictly 4!!!!!!!

		//we have reached the end of a measure, so add a new one
		if(!(song.get(song.size()-1).getNumberOfAvailableBeats()>0))
		{
			song.add(new Measure());
		}
		Measure measure = song.get(song.size()-1);

		Note lastNote;
		ArrayList<Beat> lastNoteBeats = new ArrayList<Beat>();
		Note firstNote;
		ArrayList<Beat> firstNoteBeats = new ArrayList<Beat>();

		//the first player's turn
		if((numberOfNotesPlayed % numberOfActivePlayers) == 0)
		{
			turn++;	//all players have played the same number of notes, allow more notes to be played

			//if a note can be simply added to the measure, then do it
			if(measure.canAddNote(player1.get(turn)))
				measure.addNote(player1.get(turn));
			else
			{
				//add a note for the rest of the measure
				for(int i = 0; i < measure.getNumberOfAvailableBeats(); i++)
					lastNoteBeats.add(new Beat(player1.get(turn).getPitch()));
				lastNote = new Note(lastNoteBeats, player1.get(turn).getPlayer());
				lastNote.setTiedRight(true);
				measure.addNote(lastNote);

				//add a new measure and update the current measure
				song.add(new Measure());
				measure = song.get(song.size()-1);

				//add the rest of the note carried over from the previous measure
				for(int i = 0; i < (player1.get(turn).size() - lastNote.size()); i++)
					firstNoteBeats.add(new Beat(player1.get(turn).getPitch()));
				firstNote = new Note(firstNoteBeats, player1.get(turn).getPlayer());
				firstNote.setTiedLeft(true);
				measure.addNote(firstNote);
			}
		}
		else if((numberOfNotesPlayed % numberOfActivePlayers) == 1)
		{
			//if a note can be simply added to the measure, then do it
			if(measure.canAddNote(player2.get(turn)))
				measure.addNote(player2.get(turn));
			else
			{
				//add a note for the rest of the measure
				for(int i = 0; i < measure.getNumberOfAvailableBeats(); i++)
					lastNoteBeats.add(new Beat(player2.get(turn).getPitch()));
				lastNote = new Note(lastNoteBeats, player2.get(turn).getPlayer());
				lastNote.setTiedRight(true);
				measure.addNote(lastNote);

				//add a new measure and update the current measure
				song.add(new Measure());
				measure = song.get(song.size()-1);

				//add the rest of the note carried over from the previous measure
				for(int i = 0; i < (player2.get(turn).size() - lastNote.size()); i++)
					firstNoteBeats.add(new Beat(player2.get(turn).getPitch()));
				firstNote = new Note(firstNoteBeats, player2.get(turn).getPlayer());
				firstNote.setTiedLeft(true);
				measure.addNote(firstNote);
			}
		}
		else if((numberOfNotesPlayed % numberOfActivePlayers) == 2)
		{
			//if a note can be simply added to the measure, then do it
			if(measure.canAddNote(player3.get(turn)))
				measure.addNote(player3.get(turn));
			else
			{
				//add a note for the rest of the measure
				for(int i = 0; i < measure.getNumberOfAvailableBeats(); i++)
					lastNoteBeats.add(new Beat(player3.get(turn).getPitch()));
				lastNote = new Note(lastNoteBeats, player3.get(turn).getPlayer());
				lastNote.setTiedRight(true);
				measure.addNote(lastNote);

				//add a new measure and update the current measure
				song.add(new Measure());
				measure = song.get(song.size()-1);

				//add the rest of the note carried over from the previous measure
				for(int i = 0; i < (player3.get(turn).size() - lastNote.size()); i++)
					firstNoteBeats.add(new Beat(player3.get(turn).getPitch()));
				firstNote = new Note(firstNoteBeats, player3.get(turn).getPlayer());
				firstNote.setTiedLeft(true);
				measure.addNote(firstNote);
			}
		}
		else if((numberOfNotesPlayed % numberOfActivePlayers) == 3)
		{
			//if a note can be simply added to the measure, then do it
			if(measure.canAddNote(player4.get(turn)))
				measure.addNote(player4.get(turn));
			else
			{
				//add a note for the rest of the measure
				for(int i = 0; i < measure.getNumberOfAvailableBeats(); i++)
					lastNoteBeats.add(new Beat(player4.get(turn).getPitch()));
				lastNote = new Note(lastNoteBeats, player4.get(turn).getPlayer());
				lastNote.setTiedRight(true);
				measure.addNote(lastNote);

				//add a new measure and update the current measure
				song.add(new Measure());
				measure = song.get(song.size()-1);

				//add the rest of the note carried over from the previous measure
				for(int i = 0; i < (player4.get(turn).size() - lastNote.size()); i++)
					firstNoteBeats.add(new Beat(player4.get(turn).getPitch()));
				firstNote = new Note(firstNoteBeats, player4.get(turn).getPlayer());
				firstNote.setTiedLeft(true);
				measure.addNote(firstNote);
			}
		}
	}

	/**
	 *	Sends a message to the GUI
	 *
	 *	@param message the message to be sent to the GUI
	 */
	private void sendMessageToDisplayManager(String message)
	{
		displayGUI.receiveMessage(message);
	}

	/**
	 *	Sends a Note to the GUI
	 *
	 *	@param note the Note to be sent to the GUI
	 */
	private void sendNoteToDisplayGUI()
	{
/*		if(currentBeat == 0)
		{
			displayGUI.getNote(songNotes.get(currentNote));
		}
		else if(currentBeat == songNotes.get(currentNote).size())
		{
			currentBeat = -1;
			currentNote++;
		}
	*/
		if(songNotes.size()>currentNote)
		{
			displayGUI.getNote(songNotes.get(currentNote));
			currentNote++;
		}
		
		
	}

}