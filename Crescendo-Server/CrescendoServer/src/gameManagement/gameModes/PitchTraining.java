package gameManagement.gameModes;

import gameManagement.messageTranslationSystem.Note;
import keys.GameState;
import keys.Lengths;

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
public class PitchTraining {

	private ArrayList<Note> wantedNotes;	//the array of notes the game wants
	private Random rand;
	
	int maxSubdivisions;		//per measure
	int currentSubdivisions;
	
	public PitchTraining(int subdivisions, int wantedMeasures){
		
		wantedNotes = new ArrayList<Note>();
		rand = new Random();
		maxSubdivisions = subdivisions;
		currentSubdivisions = 0;
		randomizeMeasures(wantedMeasures);
		
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
		while(i<measureCount)
		{
			test = currentSubdivisions + 2;
		
			if(test < maxSubdivisions){				//random note will fit in measure
				wantedNotes.add(new Note(randomizePitch(), Lengths.QUARTER, GameState.PITCH_TRAINING));
				currentSubdivisions += 2;
				break;
			}
			else if(test == maxSubdivisions){		//end of measure, advance to next
				wantedNotes.add(new Note(randomizePitch(), Lengths.QUARTER, GameState.PITCH_TRAINING));
				currentSubdivisions = 0;
				i++;
				break;
			}
		}
	}
	
	public String randomizePitch()
	{
		switch(rand.nextInt(17))
		{
			case 0:
				return "E5";
			case 1:
				return "ESharp5";
			case 2:
				return "F5";
			case 3:
				return "FSharp5";
			case 4:
				return "G5";
			case 5:
				return "GSharp5";
			case 6:
				return "A5";
			case 7:
				return "ASharp5";
			case 8:
				return "B5";
			case 9:
				return "C6";
			case 10:
				return "CSharp6";
			case 11:
				return "D6";
			case 12:
				return "DSharp6";
			case 13:
				return "E6";
			case 14:
				return "ESharp6";
			case 15:
				return "F6";
			case 16:
				return "FSharp6";
			default:
				return "rest";
		}
	}
	
	public ArrayList<Note> getNotes(){
		return this.wantedNotes;
	}
	
}
