package displayManager.gameModes;


import java.util.*;

import displayManager.messageTranslationSystem.Note;


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
public class lengthTraining {
	
	public ArrayList<Note> wantedNotes;	//the array of notes the trainer wants
	private Random rand;
	
	final int maxSubdivisions = 8;		//per measure
	int currentSubdivisions;
	
	/**
	 * Creates a new lengthTraining instantiation of the given measure length.
	 * @param wantedMeasures - measures in this training session
	 */
	public lengthTraining(int wantedMeasures){
		
		wantedNotes = new ArrayList<Note>();
		rand = new Random();
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
						wantedNotes.add(new Note(null, "i", "lengthTraining"));
						currentSubdivisions += 1;
						break;
					}
					else if(test == maxSubdivisions){		//end of measure, advance to next
						wantedNotes.add(new Note(null, "i", "lengthTraining"));
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
						wantedNotes.add(new Note(null, "q", "lengthTraining"));
						currentSubdivisions += 2;
						break;

					}
					else if(test == maxSubdivisions){		//end of measure, advance to next
						wantedNotes.add(new Note(null, "q", "lengthTraining"));
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
						wantedNotes.add(new Note(null, "h", "lengthTraining"));
						currentSubdivisions += 4;
						break;

					}
					else if(test == maxSubdivisions){		//end of measure, advance to next
						wantedNotes.add(new Note(null, "h", "lengthTraining"));
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
						wantedNotes.add(new Note(null, "w", "lengthTraining"));
						currentSubdivisions += 8;
						break;

					}
					else if(test == maxSubdivisions){		//end of measure, advance to next
						wantedNotes.add(new Note(null, "w", "lengthTraining"));
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
	
	/**Check that the input length is equal to the length of the
	 * note at the given index. Maintain the index externally.
	 * @param input - the length from the player
	 * @param index - the position within wantedNotes
	 * @return
	 */
	public boolean compare(String input, int index){
		if(input == wantedNotes.get(index).getLength()){
			//correct note
			return true;
		}
		else{
			//incorrect note
			return false;
		}
	}
	
	public ArrayList<Note> getNotes(){
		return this.wantedNotes;
	}
		
	
}

