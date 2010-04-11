package gameManagement.gameModes;
import gameManagement.messageTranslationSystem.Note;

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
public class matchGM {

	ArrayList<Note> wantedNotes;	//the array of notes the game wants
	private Random rand;
	
	final int maxSubdivisions = 8;		//per measure
	int currentSubdivisions;
	
	matchGM(int wantedMeasures){
		
		wantedNotes = new ArrayList<Note>();
		rand = new Random();
		randomizeMeasures(wantedMeasures);
		
	}
	
	/**
	 * Randomizes the lengths added to each of the measures. If a length fits within a 
	 * measure, then a new Note is added to wantedNotes. If a length cannot fit, then 
	 * another random note is chosen. No tying across measures is allowed. After a 
	 * length is selected, a random pitch is assigned to it to create the new Note.
	 * @param measureCount - the number of measures to fill.
	 */
	void randomizeMeasures(int measureCount){
		
		int i = 0;
		int test = 0;
		while (i<measureCount){
			switch(rand.nextInt(4))
			{
				case 0:		//eighth note
					test = currentSubdivisions + 1;
					if(test < maxSubdivisions){				//random note will fit in measure
						wantedNotes.add(new Note(randomizePitch(), "i", "matchGM"));
						currentSubdivisions += 1;
						break;
					}
					else if(test == maxSubdivisions){		//end of measure, advance to next
						wantedNotes.add(new Note(randomizePitch(), "i", "matchGM"));
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
						wantedNotes.add(new Note(randomizePitch(), "q", "matchGM"));
						currentSubdivisions += 2;
						break;

					}
					else if(test == maxSubdivisions){		//end of measure, advance to next
						wantedNotes.add(new Note(randomizePitch(), "q", "matchGM"));
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
						wantedNotes.add(new Note(randomizePitch(), "h", "matchGM"));
						currentSubdivisions += 4;
						break;

					}
					else if(test == maxSubdivisions){		//end of measure, advance to next
						wantedNotes.add(new Note(randomizePitch(), "h", "matchGM"));
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
						wantedNotes.add(new Note(randomizePitch(), "w", "matchGM"));
						currentSubdivisions += 8;
						break;

					}
					else if(test == maxSubdivisions){		//end of measure, advance to next
						wantedNotes.add(new Note(randomizePitch(), "w", "matchGM"));
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
		switch(rand.nextInt(19))
		{
			case 0:
				return "D5";
			case 1:
				return "DSharp5";
			case 2:
				return "E5";
			case 3:
				return "ESharp5";
			case 4:
				return "F5";
			case 5:
				return "FSharp5";
			case 6:
				return "G5";
			case 7:
				return "GSharp5";
			case 8:
				return "A5";
			case 9:
				return "ASharp5";
			case 10:
				return "B5";
			case 11:
				return "C6";
			case 12:
				return "CSharp6";
			case 13:
				return "D6";
			case 14:
				return "DSharp6";
			case 15:
				return "E6";
			case 16:
				return "ESharp6";
			case 17:
				return "F6";
			case 18:
				return "rest";
			default:
				return "rest";
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
	boolean compare(String input, int index){
		if(input == wantedNotes.get(index).toString())
			return true;
		return false;
	}
	
	ArrayList<Note> getNotes(){
		return this.wantedNotes;
	}
	
}
