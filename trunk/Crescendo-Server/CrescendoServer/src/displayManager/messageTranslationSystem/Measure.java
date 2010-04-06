package displayManager.messageTranslationSystem;

import java.util.regex.Pattern;
import java.util.ArrayList;

public class Measure
{
	private ArrayList<Note> notes;
	private int beatsPerMeasure;
	private int currentBeatsInMeasure;

	public Measure()
	{
		notes = new ArrayList<Note>();
		beatsPerMeasure = 8; //we are only implementing 4/4 time at the moment
		currentBeatsInMeasure = 0;
	}

	public boolean canAddNote(Note note)
	{
		if((note.size()+currentBeatsInMeasure) <= beatsPerMeasure)
			return true;
		return false;
	}

	public boolean addNote(Note note)
	{
		if(this.canAddNote(note))
		{
			notes.add(note);
			currentBeatsInMeasure += note.size();
			return true;
		}
		else
			return false;
	}

	public int getNumberOfAvailableBeats()
	{
		return beatsPerMeasure - currentBeatsInMeasure;
	}

	public ArrayList<Note> getNotes()
	{
		return notes;
	}

	public Pattern getJFuguePattern()
	{
		Pattern p;
		String regex = new String();
		for(Note note : notes)
			regex += note.getJFuguePattern().pattern() + " ";

		if(getNumberOfAvailableBeats() > 0)
		{
			ArrayList<Beat> rest = new ArrayList<Beat>();
			for(int i = 0; i < getNumberOfAvailableBeats(); i++)
				rest.add(new Beat());
			regex += new Note(rest, "unknown").getJFuguePattern().pattern() + " ";
		}

		regex += "| ";
		p = Pattern.compile(regex);

		return p;
	}

	public String toString()
	{
		return getJFuguePattern().pattern();
	}
}