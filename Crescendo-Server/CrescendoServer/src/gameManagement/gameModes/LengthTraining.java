package gameManagement.gameModes;


import gameManagement.messageTranslationSystem.Note;
import keys.GameState;
import keys.Lengths;
import keys.Pitches;

import java.util.*;




/**
 * This class is used for instantiations of the "Match Length" training 
 * mode. A set of measures is created with random note lengths 
 * that the user must match. The notes received as input can be of any 
 * desired pitch. The players will be prompted to enter the correct notes,
 * and given clues and feedback on their selections to help them learn the
 * lengths. 
 * @author Chris Aikens
 *
 */
public class LengthTraining {
	
	private ArrayList<Note> wantedNotes;	//the array of notes the trainer wants
	private Random rand;
	
	int maxSubdivisions;		//per measure
	int currentSubdivisions;
	
	/**
	 * Creates a new lengthTraining instantiation of the given measure length.
	 * @param wantedMeasures - measures in this training session
	 */
	public LengthTraining(int subdivisions, int wantedMeasures){
		
		wantedNotes = new ArrayList<Note>();
		rand = new Random();
		maxSubdivisions = subdivisions;
		currentSubdivisions = 0;
		randomizeMeasures(wantedMeasures);
	}
	
	/**
	 * Randomizes the lengths added to each of the measures. If a length fits within a 
	 * measure, then a new Note is added to wantedNotes. If a length cannot fit, then 
	 * another random note is chosen. To tying across measures is allowed.
	 * @param measureCount - the number of measures to fill.
	 */
	public void randomizeMeasures(int measureCount){
		
		int i = 0;
		int test = 0;
		while (i<measureCount){
			switch(rand.nextInt(4))
			{
				case 0:		//eighth note
					test = currentSubdivisions + 1;
					if(test < maxSubdivisions){				//random note will fit in measure
						wantedNotes.add(new Note(Pitches.B5, Lengths.EIGHTH, GameState.LENGTH_TRAINING));
						currentSubdivisions += 1;
						break;
					}
					else if(test == maxSubdivisions){		//end of measure, advance to next
						wantedNotes.add(new Note(Pitches.B5, Lengths.EIGHTH, GameState.LENGTH_TRAINING));
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
						wantedNotes.add(new Note(Pitches.B5, Lengths.QUARTER, GameState.LENGTH_TRAINING));
						currentSubdivisions += 2;
						break;

					}
					else if(test == maxSubdivisions){		//end of measure, advance to next
						wantedNotes.add(new Note(Pitches.B5, Lengths.QUARTER, GameState.LENGTH_TRAINING));
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
						wantedNotes.add(new Note(Pitches.B5, Lengths.HALF, GameState.LENGTH_TRAINING));
						currentSubdivisions += 4;
						break;

					}
					else if(test == maxSubdivisions){		//end of measure, advance to next
						wantedNotes.add(new Note(Pitches.B5, Lengths.HALF, GameState.LENGTH_TRAINING));
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
						wantedNotes.add(new Note(Pitches.B5, Lengths.WHOLE, GameState.LENGTH_TRAINING));
						currentSubdivisions += 8;
						break;

					}
					else if(test == maxSubdivisions){		//end of measure, advance to next
						wantedNotes.add(new Note(Pitches.B5, Lengths.WHOLE, GameState.LENGTH_TRAINING));
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
	
	public ArrayList<Note> getNotes(){
		return this.wantedNotes;
	}
		
}

