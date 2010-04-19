package gameManagement.gameModes;

import gameManagement.messageTranslationSystem.Note;
import keys.GameState;

import java.util.ArrayList;
import java.util.Random;



/**
 * This class is used for instantiations of the "Match Pitch" training
 * mode. A set of pitches is created with random note pitches 
 * that the user must match. The notes received as input can be of any 
 * desired length, until all of the pitches are matched. The players 
 * will be prompted to enter the correct notes, and given clues and 
 * feedback on their selections to help them learn the pitches. 
 * @author Chris Aikens
 *
 */
public class PitchTraining {
	
	public ArrayList<Note> wantedNotes;	//the array of notes the trainer wants
	private Random rand;
	
	/**
	 * Creates a new pitchTraining instantiation of the given number of pitches
	 * @param numberPitches - the number of pitches wanted 
	 */
	public PitchTraining(int numberPitches){
		
		wantedNotes = new ArrayList<Note>();
		rand = new Random();
		randomizePitches(numberPitches);
		
	}
	
	/**
	 * Randomizes the pitches added to wantedNotes. Notes are constructed
	 * from the random pitches and a half note value, which could be changed
	 * or overwritten as needed/desired.
	 * @param pitchesWanted - the number of pitches wanted
	 */
	public void randomizePitches(int pitchesWanted){
		int i = 0;
		while(i < pitchesWanted){
			switch(rand.nextInt(19))
			{
				case 0:		//D5
					wantedNotes.add(new Note("D5", null, GameState.PITCH_TRAINING));
					i++;
					break;
				case 1:		//DSharp5
					wantedNotes.add(new Note("DSharp5", null, GameState.PITCH_TRAINING));
					i++;
					break;
				case 2:		//E5
					wantedNotes.add(new Note("E5", null, GameState.PITCH_TRAINING));
					i++;
					break;
				case 3:		//ESharp5
					wantedNotes.add(new Note("ESharp5", null, GameState.PITCH_TRAINING));
					i++;
					break;
				case 4:		//F5
					wantedNotes.add(new Note("F5", null, GameState.PITCH_TRAINING));
					i++;
					break;
				case 5:		//FSharp5
					wantedNotes.add(new Note("FSharp5", null, GameState.PITCH_TRAINING));
					i++;
					break;
				case 6:		//G5
					wantedNotes.add(new Note("G5", null, GameState.PITCH_TRAINING));
					i++;
					break;
				case 7:		//GSharp5
					wantedNotes.add(new Note("GSharp5", null, GameState.PITCH_TRAINING));
					i++;
					break;
				case 8:		//A5
					wantedNotes.add(new Note("A5", null, GameState.PITCH_TRAINING));
					i++;
					break;
				case 9:		//ASharp5
					wantedNotes.add(new Note("ASharp5", null, GameState.PITCH_TRAINING));
					i++;
					break;
				case 10:	//B5
					wantedNotes.add(new Note("B5", null, GameState.PITCH_TRAINING));
					i++;
					break;
				case 11:	//C6
					wantedNotes.add(new Note("C6", null, GameState.PITCH_TRAINING));
					i++;
					break;
				case 12:	//CSharp6
					wantedNotes.add(new Note("CSharp6", null, GameState.PITCH_TRAINING));
					i++;
					break;
				case 13:	//D6
					wantedNotes.add(new Note("D6", null, GameState.PITCH_TRAINING));
					i++;
					break;
				case 14:	//DSharp6
					wantedNotes.add(new Note("DSharp6", null, GameState.PITCH_TRAINING));
					i++;
					break;
				case 15:	//E6
					wantedNotes.add(new Note("E6", null, GameState.PITCH_TRAINING));
					i++;
					break;
				case 16:	//ESharp6
					wantedNotes.add(new Note("ESharp6", null, GameState.PITCH_TRAINING));
					i++;
					break;
				case 17:	//F6
					wantedNotes.add(new Note("F6", null, GameState.PITCH_TRAINING));
					i++;
					break;
				case 18:
					//fail!
					break;
				default:
					//fail!
					break;
			}
		}
	}
	
	/**
	 * Check that the input pitch is equal to the pitch of the
	 * note at the given index. Maintain the index externally.
	 * @param input - the pitch from the player
	 * @param index - the position within wantedNotes
	 * @return
	 */
	public boolean compare(String input, int index){
		if(input == wantedNotes.get(index).getPitch())
			//matched pitch!
			return true;
		return false;
	}
	
	public ArrayList<Note> getNotes(){
		return this.wantedNotes;
	}
}
