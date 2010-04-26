package gameManagement.gameModes;

import gameManagement.messageTranslationSystem.Note;
import keys.GameState;
import keys.Lengths;
import keys.Scales;

import java.util.ArrayList;
import java.util.Random;




/**
 * This game mode is a variation of the pitch and length training modes. 
 * Given a set number of measures, random lengths and pitches are found to 
 * create an array of notes that the players must match. This class could be 
 * combined with a timer to make a time-based game (thus raising the difficulty).
 * @author Chris Aikens
 *
 */
public class PitchTraining
{
	private ArrayList<Note> wantedNotes;	//the array of notes the game wants
	private Random rand;
	
	int maxSubdivisions;		//per measure
	int currentSubdivisions;
	
	//Default constructor
	public PitchTraining(int subdivisions, int wantedMeasures){
		
		wantedNotes = new ArrayList<Note>();
		rand = new Random();
		maxSubdivisions = subdivisions;
		currentSubdivisions = 0;
		randomizeMeasures(wantedMeasures);
		
	}
	
	/**
	 * This constructor allows for the pitches to be randomized based on the specified 
	 * key signature. 
	 * @param subdivisions
	 * @param wantedMeasures
	 * @param wantedKey
	 */
	public PitchTraining(int subdivisions, int wantedMeasures, String wantedKey){
		
		wantedNotes = new ArrayList<Note>();
		rand = new Random();
		maxSubdivisions = subdivisions;
		currentSubdivisions = 0;
		randomizeWithKey(wantedMeasures, wantedKey);
	}
	
	/**
	 * Randomizes the pitches added to each of the measures. The pitches are selected
	 * only from those notes that are a part of the specified key signature.
	 * @param measureCount - the number of measures to fill.
	 * @param key - the key signature from which notes are selected
	 */
	private void randomizeWithKey(int measureCount, String key){
		for(int i = 0; i < (maxSubdivisions/2)*measureCount; i++)
			wantedNotes.add(new Note(randomizePitchWithKey(key), Lengths.QUARTER, GameState.PITCH_TRAINING));
	}
	
	/**
	 * Returns a note randomly selected from those that are part of the specified 
	 * key signature. The keys (found in class Scales) have either 8 or 9 notes in
	 * them. As such, this method checks the length of the Array for that key before
	 * it randomly chooses a note for it.
	 * @param key - the key to get notes from
	 * @return - the randomized note within that key signature
	 */
	public String randomizePitchWithKey(String key)
	{
		ArrayList<String> keyNotes = Scales.getNotes(key);
		if(keyNotes.size() == 8)
		{
			switch(rand.nextInt(8))
			{
				case 0:
					return keyNotes.get(0);
				case 1:
					return keyNotes.get(1);
				case 2:
					return keyNotes.get(2);
				case 3:
					return keyNotes.get(3);
				case 4:
					return keyNotes.get(4);
				case 5:
					return keyNotes.get(5);
				case 6:
					return keyNotes.get(6);
				default:
					return keyNotes.get(7);
			}
		}
		else if(keyNotes.size() == 9)
		{
			switch(rand.nextInt(9))
			{
				case 0:
					return keyNotes.get(0);
				case 1:
					return keyNotes.get(1);
				case 2:
					return keyNotes.get(2);
				case 3:
					return keyNotes.get(3);
				case 4:
					return keyNotes.get(4);
				case 5:
					return keyNotes.get(5);
				case 6:
					return keyNotes.get(6);
				case 7:
					return keyNotes.get(7);
				default:
					return keyNotes.get(8);
			}	
		}
		return keyNotes.get(0);
		
	}
	
	/**
	 * Randomizes the pitches added to each of the measures.
	 * @param measureCount - the number of measures to fill.
	 */
	private void randomizeMeasures(int measureCount)
	{
		for(int i = 0; i < (maxSubdivisions/2)*measureCount; i++)
			wantedNotes.add(new Note(randomizePitch(), Lengths.QUARTER, GameState.PITCH_TRAINING));
	}
	
	public String randomizePitch()
	{
		switch(rand.nextInt(15))
		{
			case 0:
				return "E5";
			case 1:
				return "F5";
			case 2:
				return "FSharp5";
			case 3:
				return "G5";
			case 4:
				return "GSharp5";
			case 5:
				return "A5";
			case 6:
				return "ASharp5";
			case 7:
				return "B5";
			case 8:
				return "C6";
			case 9:
				return "CSharp6";
			case 10:
				return "D6";
			case 11:
				return "DSharp6";
			case 12:
				return "E6";
			case 13:
				return "F6";
			default:
				return "FSharp6";
		}
	}
	
	public ArrayList<Note> getNotes(){
		return this.wantedNotes;
	}
	
}
