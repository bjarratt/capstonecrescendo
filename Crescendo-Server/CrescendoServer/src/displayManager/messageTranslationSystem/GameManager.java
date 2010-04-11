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

//	private DisplayGUI displayGUI;
	private ArrayList<String> messagePool;
	private ArrayList<String> messages;
	private Timer timer;

	//game options
	private static int tempo = 1000;

	//gamestate options
	private boolean exit;
	private boolean at_game_start;
	private boolean at_splash_screen;
	private boolean at_game_types;
	private boolean at_game_options;
	private boolean at_post_game;

	private ArrayList<Beat> player1Beats;
	private ArrayList<Beat> player2Beats;
	private ArrayList<Beat> player3Beats;
	private ArrayList<Beat> player4Beats;
	private ArrayList<Beat> metronomeBeats;
	private int currentBeat;

	private ArrayList<Note> player1Notes;
	private ArrayList<Note> player2Notes;
	private ArrayList<Note> player3Notes;
	private ArrayList<Note> player4Notes;
	private ArrayList<Note> metronomeNotes;
	private int player1CurrentNote;
	private int player2CurrentNote;
	private int player3CurrentNote;
	private int player4CurrentNote;
	private int metronomeCurrentNote;
	private int numberOfActivePlayers;

	private int numberOfBeatsPerMeasure;

	private ArrayList<Measure> player1Measures;
	private ArrayList<Measure> player2Measures;
	private ArrayList<Measure> player3Measures;
	private ArrayList<Measure> player4Measures;
	private ArrayList<Measure> metronomeMeasures;

	ArrayList<Note> notesToSend;


	/**
	 *	Constructs a GameManager object with upto 4 players
	 */
	public GameManager()
	{
		//set up the display to receive messages
//		displayGUI = new DisplayGUI();
		//message pool
		messagePool = new ArrayList<String>();
		//messages to be translated
		messages = new ArrayList<String>();
		//set up a timer
		timer = new Timer(tempo,this);

		player1CurrentNote = 0;
		player2CurrentNote = 0;
		player3CurrentNote = 0;
		player4CurrentNote = 0;
		metronomeCurrentNote = 0;

		currentBeat = 0;

		exit = false;
		at_splash_screen = false;
		at_game_types = false;
		at_game_options = false;
		at_game_start = false;
		at_post_game = false;

		player1Beats = new ArrayList<Beat>();
		player2Beats = new ArrayList<Beat>();
		player3Beats = new ArrayList<Beat>();
		player4Beats = new ArrayList<Beat>();
		metronomeBeats = new ArrayList<Beat>();

		player1Notes = new ArrayList<Note>();
		player2Notes = new ArrayList<Note>();
		player3Notes = new ArrayList<Note>();
		player4Notes = new ArrayList<Note>();
		metronomeNotes = new ArrayList<Note>();
		numberOfActivePlayers = 0;

		numberOfBeatsPerMeasure = 8;

		player1Measures = new ArrayList<Measure>();
		player1Measures.add(new Measure(numberOfBeatsPerMeasure));
		player2Measures = new ArrayList<Measure>();
		player2Measures.add(new Measure(numberOfBeatsPerMeasure));
		player3Measures = new ArrayList<Measure>();
		player3Measures.add(new Measure(numberOfBeatsPerMeasure));
		player4Measures = new ArrayList<Measure>();
		player4Measures.add(new Measure(numberOfBeatsPerMeasure));
		metronomeMeasures = new ArrayList<Measure>();
		metronomeMeasures.add(new Measure(numberOfBeatsPerMeasure));

		notesToSend = new ArrayList<Note>();

	}

	/**
	 *	Starts the game loop
	 */
	public void run()
	{
		timer.start();

		//create a new display window
		JFrame window = new JFrame("Crescendo");
		window.setBackground(Color.WHITE);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//window.getContentPane().add(displayGUI);
		window.pack();
		window.setVisible(false);

		while(true)
		{
			if(exit == true)
				break;
			if(at_splash_screen)
			{
				if(at_game_types)
				{
					if(at_game_options)
					{
						if(at_post_game)
						{

						}
					}
				}
			}
		}
		timer.stop();
	}

	/**
	 *	When a Timer ticks, this method is called and performs some action
	 */
	public void actionPerformed(ActionEvent event)
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

			if(at_game_start)
			{
				this.addRests();
				this.constructMeasures();
				this.sendNoteToDisplayGUI();
				currentBeat++;
			}
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
					//only add the new note if player1 is not currently playing a note
					if(player1Beats.size()<=currentBeat)
					{
						player1Beats.addAll(n.getBeats());
						player1Notes.add(n);
					}
				}
				else if(n.getPlayer().equals("player2"))
				{
					//only add the new note if player2 is not currently playing a note
					if(player2Beats.size()<=currentBeat)
					{
						player2Beats.addAll(n.getBeats());
						player2Notes.add(n);
					}
				}
				else if(n.getPlayer().equals("player3"))
				{
					//only add the new note if player3 is not currently playing a note
					if(player3Beats.size()<=currentBeat)
					{
						player3Beats.addAll(n.getBeats());
						player3Notes.add(n);
					}
				}
				else if(n.getPlayer().equals("player4"))
				{
					//only add the new note if player4 is not currently playing a note
					if(player4Beats.size()<=currentBeat)
					{
						player4Beats.addAll(n.getBeats());
						player4Notes.add(n);
					}
				}
				else if(n.getPlayer().equals("metronome"))
				{
					//only add the new note if the metronome is not currently playing a note
					if(metronomeBeats.size()<=currentBeat)
					{
						metronomeBeats.addAll(n.getBeats());
						metronomeNotes.add(n);
					}
				}
			}
			else
			{
				if(at_splash_screen && !at_game_types && m.getMessage().split("_")[1].equals("connect"))
					numberOfActivePlayers++;

				if(m.getMessage().split("_")[1].equals("disconnect"))
					numberOfActivePlayers--;

				//message is "player1_splashscreen"  (checking to make sure player 1 has sent the message)
				if(m.getMessage().split("_")[1].equals(GameStateKeys.SPLASH_SCREEN))
				{
					at_splash_screen = true;
					System.out.println("Now at the splash screen");
				}
				//message is "player1_gametypes"  (checking to make sure player 1 has sent the message)
				if(at_splash_screen && m.getMessage().split("_")[1].equals(GameStateKeys.GAME_TYPES))
				{
					at_game_types = true;
					System.out.println("Now choosing a game type");
				}
				//message is "player1_gameoptions"  (checking to make sure player 1 has sent the message)
				if(at_splash_screen && at_game_types && m.getMessage().split("_")[1].equals(GameStateKeys.GAME_OPTIONS))
				{
					at_game_options = true;
					System.out.println("Now selecting game options");
				}
				//message is "player1_startgame"  (checking to make sure player 1 has sent the message)
				if(at_splash_screen && at_game_types && at_game_options && m.getMessage().split("_")[1].equals(GameStateKeys.START_GAME))
				{
					at_game_start = true;
					System.out.println("Now starting the game");
				}
				//message is "player1_postgame"  (checking to make sure player 1 has sent the message)
				if(at_splash_screen && at_game_types && at_game_options && at_game_start && m.getMessage().split("_")[1].equals(GameStateKeys.POST_GAME))
				{
					at_post_game = true;
				}
				//message is "player1_exit"  (checking to make sure player 1 has sent the message)
				//maybe disconnect all iPhones?
				if(m.getMessage().split("_")[1].equals(GameStateKeys.EXIT))
					exit = true;

				//handle messages such as game type, connect, disconnect, etc.
				sendMessageToDisplayManager(m.getMessage());
			}
		}
	}

	/**
	 *	Adds rests for each tick that a player does not play a note.
	 *	Rests are not sent to the GUI
	 */
	private void addRests()
	{
		Note n;
		if(player1Beats.size()<currentBeat)
		{
			n = new Note("R","i","player1");
			player1Beats.addAll(n.getBeats());
			player1Notes.add(n);
			player1CurrentNote++;
		}
		else if(player2Beats.size()<currentBeat)
		{
			n = new Note("R","i","player2");
			player2Beats.addAll(n.getBeats());
			player2Notes.add(n);
			player2CurrentNote++;
		}
		else if(player3Beats.size()<currentBeat)
		{
			n = new Note("R","i","player3");
			player3Beats.addAll(n.getBeats());
			player3Notes.add(n);
			player3CurrentNote++;
		}
		else if(player4Beats.size()<currentBeat)
		{
			n = new Note("R","i","player4");
			player4Beats.addAll(n.getBeats());
			player4Notes.add(n);
			player4CurrentNote++;
		}
		else if(metronomeBeats.size()<currentBeat)
		{
			n = new Note("R","i","metronome");
			metronomeBeats.addAll(n.getBeats());
			metronomeNotes.add(n);
			metronomeCurrentNote++;
		}
	}


	/**
	 *	From the Note's played by each player, correctly formmated Measures are created
	 */
	private void constructMeasures()
	{
		Note lastNote;
		ArrayList<Beat> lastNoteBeats;
		Note firstNote;
		ArrayList<Beat> firstNoteBeats;


		////	Player 1	////
		//we have reached the end of a measure, so add a new one
		if(!(player1Measures.get(player1Measures.size()-1).getNumberOfAvailableBeats()>0))
		{
			player1Measures.add(new Measure(numberOfBeatsPerMeasure));
		}

		Measure measure = player1Measures.get(player1Measures.size()-1);

		if(player1Notes.size()>0 && player1Notes.size()>player1CurrentNote)
		{
			player1CurrentNote++;

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
				lastNote = new Note(lastNoteBeats, "player1");
				lastNote.setTiedRight(true);
				measure.addNote(lastNote);
				notesToSend.add(lastNote);

				//add a new measure and update the current measure
				player1Measures.add(new Measure(numberOfBeatsPerMeasure));
				measure = player1Measures.get(player1Measures.size()-1);

				//add the rest of the note carried over from the previous measure
				for(int i = 0; i < (player1Notes.get(player1Notes.size()-1).size() - lastNote.size()); i++)
					firstNoteBeats.add(new Beat(player1Beats.get(currentBeat).getPitch()));
				firstNote = new Note(firstNoteBeats, "player1");
				firstNote.setTiedLeft(true);
				measure.addNote(firstNote);
				notesToSend.add(firstNote);
			}
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
			player2CurrentNote++;

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
				lastNote = new Note(lastNoteBeats, "player2");
				lastNote.setTiedRight(true);
				measure.addNote(lastNote);
				notesToSend.add(lastNote);

				//add a new measure and update the current measure
				player2Measures.add(new Measure(numberOfBeatsPerMeasure));
				measure = player2Measures.get(player2Measures.size()-1);

				//add the rest of the note carried over from the previous measure
				for(int i = 0; i < (player2Notes.get(player2Notes.size()-1).size() - lastNote.size()); i++)
					firstNoteBeats.add(new Beat(player2Beats.get(currentBeat).getPitch()));
				firstNote = new Note(firstNoteBeats, "player2");
				firstNote.setTiedLeft(true);
				measure.addNote(firstNote);
				notesToSend.add(firstNote);
			}
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
			player3CurrentNote++;

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
				lastNote = new Note(lastNoteBeats, "player3");
				lastNote.setTiedRight(true);
				measure.addNote(lastNote);
				notesToSend.add(lastNote);

				//add a new measure and update the current measure
				player3Measures.add(new Measure(numberOfBeatsPerMeasure));
				measure = player3Measures.get(player3Measures.size()-1);

				//add the rest of the note carried over from the previous measure
				for(int i = 0; i < (player3Notes.get(player3Notes.size()-1).size() - lastNote.size()); i++)
					firstNoteBeats.add(new Beat(player3Beats.get(currentBeat).getPitch()));
				firstNote = new Note(firstNoteBeats, "player3");
				firstNote.setTiedLeft(true);
				measure.addNote(firstNote);
				notesToSend.add(firstNote);
			}
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
			player4CurrentNote++;

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
				lastNote = new Note(lastNoteBeats, "player4");
				lastNote.setTiedRight(true);
				measure.addNote(lastNote);
				notesToSend.add(lastNote);

				//add a new measure and update the current measure
				player4Measures.add(new Measure(numberOfBeatsPerMeasure));
				measure = player4Measures.get(player4Measures.size()-1);

				//add the rest of the note carried over from the previous measure
				for(int i = 0; i < (player4Notes.get(player4Notes.size()-1).size() - lastNote.size()); i++)
					firstNoteBeats.add(new Beat(player4Beats.get(currentBeat).getPitch()));
				firstNote = new Note(firstNoteBeats, "player4");
				firstNote.setTiedLeft(true);
				measure.addNote(firstNote);
				notesToSend.add(firstNote);
			}
		}
		////	Player 4	////

		////	Metronome	////
		//we have reached the end of a measure, so add a new one
		if(!(metronomeMeasures.get(metronomeMeasures.size()-1).getNumberOfAvailableBeats()>0))
		{
			metronomeMeasures.add(new Measure(numberOfBeatsPerMeasure));
		}

		measure = metronomeMeasures.get(metronomeMeasures.size()-1);

		if(metronomeNotes.size()>0 && metronomeNotes.size()>metronomeCurrentNote)
		{
			metronomeCurrentNote++;

			//if a note can be simply added to the measure, then do it
			if(measure.canAddNote(metronomeNotes.get(metronomeNotes.size()-1)))
			{
				measure.addNote(metronomeNotes.get(metronomeNotes.size()-1));
				notesToSend.add(metronomeNotes.get(metronomeNotes.size()-1));
			}
			else
			{
				lastNoteBeats = new ArrayList<Beat>();
				firstNoteBeats = new ArrayList<Beat>();

				//add a note for the rest of the measure
				for(int i = 0; i < measure.getNumberOfAvailableBeats(); i++)
					lastNoteBeats.add(new Beat(metronomeBeats.get(currentBeat).getPitch()));
				lastNote = new Note(lastNoteBeats, "metronome");
				lastNote.setTiedRight(true);
				measure.addNote(lastNote);
				notesToSend.add(lastNote);

				//add a new measure and update the current measure
				metronomeMeasures.add(new Measure(numberOfBeatsPerMeasure));
				measure = metronomeMeasures.get(metronomeMeasures.size()-1);

				//add the rest of the note carried over from the previous measure
				for(int i = 0; i < (metronomeNotes.get(metronomeNotes.size()-1).size() - lastNote.size()); i++)
					firstNoteBeats.add(new Beat(metronomeBeats.get(currentBeat).getPitch()));
				firstNote = new Note(firstNoteBeats, "metronome");
				firstNote.setTiedLeft(true);
				measure.addNote(firstNote);
				notesToSend.add(firstNote);
			}
		}
		////	Metronome	////

	}

	/**
	 *	Sends a message to the GUI
	 *
	 *	@param message the message to be sent to the GUI
	 */
	private void sendMessageToDisplayManager(String message)
	{
		// TODO This method still needs to be somewhere
		//displayGUI.receiveMessage(message);
	}

	/**
	 *	Sends a Note to the GUI
	 *
	 *	@param note the Note to be sent to the GUI
	 */
	private void sendNoteToDisplayGUI()
	{
		// TODO This method still needs to be somewhere
		//displayGUI.getNotes(notesToSend, currentBeat);
		notesToSend = new ArrayList<Note>();
	}

}