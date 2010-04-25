package gameManagement.messageTranslationSystem;


import java.util.ArrayList;
import org.jfugue.Pattern;

public class Measure
{
	private ArrayList<Note> notes;
	private int beatsPerMeasure;
	private int currentBeatsInMeasure;

	public Measure(int beatsPerMeasure)
	{
		notes = new ArrayList<Note>();
		this.beatsPerMeasure = beatsPerMeasure;
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

		p = new Pattern(regex);

		return p;
	}

	public String toString()
	{
		String s = new String();
		for(Note note : notes)
		{
			s += note + " ";
		}
		return s;
	}
}