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
public class NoteTraining
{
	private ArrayList<Note> wantedNotes;	//the array of notes the game wants
	private Random rand;
	
	int maxSubdivisions;		//per measure
	int currentSubdivisions;
	
	public NoteTraining(int subdivisions, int wantedMeasures){
		
		wantedNotes = new ArrayList<Note>();
		rand = new Random();
		maxSubdivisions = subdivisions;
		currentSubdivisions = 0;
		randomizeMeasures(wantedMeasures);
		
	}
	
	public NoteTraining(int subdivisions, int wantedMeasures, String wantedKey)
	{
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

		int i = 0;
		int test = 0;
		while (i<measureCount){
			switch(rand.nextInt(4))
			{
				case 0:		//eighth note
					test = currentSubdivisions + 1;
					if(test < maxSubdivisions){				//random note will fit in measure
						wantedNotes.add(new Note(randomizePitchWithKey(key), Lengths.EIGHTH, GameState.NOTE_TRAINING));
						currentSubdivisions += 1;
						break;
					}
					else if(test == maxSubdivisions){		//end of measure, advance to next
						wantedNotes.add(new Note(randomizePitchWithKey(key), Lengths.EIGHTH, GameState.NOTE_TRAINING));
						currentSubdivisions = 0;
						i++;
						break;
					}
					
					else{									//test > max and you must do another rand
						break;
					}
				case 1:		//quarter note
					test = currentSubdivisions + 2;
					if(test < maxSubdivisions){				//random note will fit in measure
						wantedNotes.add(new Note(randomizePitchWithKey(key), Lengths.QUARTER, GameState.NOTE_TRAINING));
						currentSubdivisions += 2;
						break;

					}
					else if(test == maxSubdivisions){		//end of measure, advance to next
						wantedNotes.add(new Note(randomizePitchWithKey(key), Lengths.QUARTER, GameState.NOTE_TRAINING));
						currentSubdivisions = 0;
						i++;
						break;
					}
					
					else{									//test > max and you must do another rand
						break;
					}
				case 2:		//half note
					test = currentSubdivisions + 4;
					if(test < maxSubdivisions){				//random note will fit in measure
						wantedNotes.add(new Note(randomizePitchWithKey(key), Lengths.HALF, GameState.NOTE_TRAINING));
						currentSubdivisions += 4;
						break;

					}
					else if(test == maxSubdivisions){		//end of measure, advance to next
						wantedNotes.add(new Note(randomizePitchWithKey(key), Lengths.HALF, GameState.NOTE_TRAINING));
						currentSubdivisions = 0;
						i++;
						break;
					}
					
					else{									//test > max and you must do another rand
						break;
					}
				case 3:		//whole note
					test = currentSubdivisions + 8;
					if(test < maxSubdivisions){				//random note will fit in measure
						wantedNotes.add(new Note(randomizePitchWithKey(key), Lengths.WHOLE, GameState.NOTE_TRAINING));
						currentSubdivisions += 8;
						break;

					}
					else if(test == maxSubdivisions){		//end of measure, advance to next
						wantedNotes.add(new Note(randomizePitchWithKey(key), Lengths.WHOLE, GameState.NOTE_TRAINING));
						currentSubdivisions = 0;
						i++;
						break;
					}
					
					else{									//test > max and you must do another rand
						break;
					}
				default:
					//something bad happened!
					break;
			}
		}
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
	 * Randomizes the lengths added to each of the measures. If a length fits within a 
	 * measure, then a new Note is added to wantedNotes. If a length cannot fit, then 
	 * another random note is chosen. No tying across measures is allowed. After a 
	 * length is selected, a random pitch is assigned to it to create the new Note.
	 * @param measureCount - the number of measures to fill.
	 */
	private void randomizeMeasures(int measureCount){
		
		int i = 0;
		int test = 0;
		while (i<measureCount){
			switch(rand.nextInt(4))
			{
				case 0:		//eighth note
					test = currentSubdivisions + 1;
					if(test < maxSubdivisions){				//random note will fit in measure
						wantedNotes.add(new Note(randomizePitch(), Lengths.EIGHTH, GameState.NOTE_TRAINING));
						currentSubdivisions += 1;
						break;
					}
					else if(test == maxSubdivisions){		//end of measure, advance to next
						wantedNotes.add(new Note(randomizePitch(), Lengths.EIGHTH, GameState.NOTE_TRAINING));
						currentSubdivisions = 0;
						i++;
						break;
					}
					
					else{									//test > max and you must do another rand
						break;
					}
				case 1:		//quarter note
					test = currentSubdivisions + 2;
					if(test < maxSubdivisions){				//random note will fit in measure
						wantedNotes.add(new Note(randomizePitch(), Lengths.QUARTER, GameState.NOTE_TRAINING));
						currentSubdivisions += 2;
						break;

					}
					else if(test == maxSubdivisions){		//end of measure, advance to next
						wantedNotes.add(new Note(randomizePitch(), Lengths.QUARTER, GameState.NOTE_TRAINING));
						currentSubdivisions = 0;
						i++;
						break;
					}
					
					else{									//test > max and you must do another rand
						break;
					}
				case 2:		//half note
					test = currentSubdivisions + 4;
					if(test < maxSubdivisions){				//random note will fit in measure
						wantedNotes.add(new Note(randomizePitch(), Lengths.HALF, GameState.NOTE_TRAINING));
						currentSubdivisions += 4;
						break;

					}
					else if(test == maxSubdivisions){		//end of measure, advance to next
						wantedNotes.add(new Note(randomizePitch(), Lengths.HALF, GameState.NOTE_TRAINING));
						currentSubdivisions = 0;
						i++;
						break;
					}
					
					else{									//test > max and you must do another rand
						break;
					}
				case 3:		//whole note
					test = currentSubdivisions + 8;
					if(test < maxSubdivisions){				//random note will fit in measure
						wantedNotes.add(new Note(randomizePitch(), Lengths.WHOLE, GameState.NOTE_TRAINING));
						currentSubdivisions += 8;
						break;

					}
					else if(test == maxSubdivisions){		//end of measure, advance to next
						wantedNotes.add(new Note(randomizePitch(), Lengths.WHOLE, GameState.NOTE_TRAINING));
						currentSubdivisions = 0;
						i++;
						break;
					}
					
					else{									//test > max and you must do another rand
						break;
					}
				default:
					//something bad happened!
					break;
			}
		}
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
	
	/**
	 * Checks to see whether the input note matches the note at
	 * the given index position. Matches the entire note at once,
	 * in the pitch and then length format.
	 * @param input - the note from the player
	 * @param index - the position within wantedNotes
	 * @return - if the notes match or not
	 */
	// TODO:  Test this!!!!!!!!!!!
	public boolean compare(String input, int index){
		if(input == wantedNotes.get(index).toString())
			return true;
		return false;
	}
	
	public ArrayList<Note> getNotes(){
		return this.wantedNotes;
	}
	
}
