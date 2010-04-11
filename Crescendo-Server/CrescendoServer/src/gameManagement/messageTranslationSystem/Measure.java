package gameManagement.messageTranslationSystem;


import java.util.ArrayList;

import org.jfugue.Pattern;

public class Measure
{
	private ArrayList<Note> notes;
	private int beatsPerMeasure;
	private int currentBeatsInMeasure;

	public Measure(int beats)
	{
		notes = new ArrayList<Note>();
		beatsPerMeasure = beats;
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
			regex += note.getJFuguePattern().toString() + " ";

		if(getNumberOfAvailableBeats() > 0)
		{
			ArrayList<Beat> rest = new ArrayList<Beat>();
			for(int i = 0; i < getNumberOfAvailableBeats(); i++)
				rest.add(new Beat());
			regex += new Note(rest, "unknown").getJFuguePattern().toString() + " ";
		}

		regex += "| ";
		p = new Pattern(regex);

		return p;
	}

	public String toString()
	{
		return getJFuguePattern().toString();
	}
}