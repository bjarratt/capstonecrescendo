package keys;

import gameManagement.messageTranslationSystem.Note;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * This class currently holds the arrays representing all of the notes in a given 
 * key signature. Because we only created Sharp Note cards, all major flat keys feature
 * the corresponding sharps. 
 * @author Chris Aikens
 *
 */
public class Scales 
{
	private static ArrayList<Note> keyC = new ArrayList<Note>();
	private static ArrayList<Note> keyG = new ArrayList<Note>();
	private static ArrayList<Note> keyD = new ArrayList<Note>();
	private static ArrayList<Note> keyA = new ArrayList<Note>();
	private static ArrayList<Note> keyE = new ArrayList<Note>();
	private static ArrayList<Note> keyB = new ArrayList<Note>();
	private static ArrayList<Note> keyGFlat = new ArrayList<Note>();
	private static ArrayList<Note> keyDFlat = new ArrayList<Note>();
	private static ArrayList<Note> keyAFlat = new ArrayList<Note>();
	private static ArrayList<Note> keyEFlat = new ArrayList<Note>();
	private static ArrayList<Note> keyBFlat = new ArrayList<Note>();
	private static ArrayList<Note> keyF = new ArrayList<Note>();
	
	public static HashMap<String, ArrayList<Note>> allKeys = new HashMap<String, ArrayList<Note>>();
	
	static
	{
		//Key of C
		Note[] notes = new Note[]{new Note(keys.Pitches.E5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.F5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.G5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.A5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.B5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.C6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.D6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.E6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.F6,keys.Lengths.EIGHTH,keys.GameState.SCALES)};
		for(Note note : notes)
			keyC.add(note);
		allKeys.put(keys.KeySignatures.CMajor, keyC);
		
		//Key of G
		notes = new Note[]{new Note(keys.Pitches.E5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.FSharp5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.G5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.A5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.B5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.C6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.D6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.E6,keys.Lengths.EIGHTH,keys.GameState.SCALES),
				new Note(keys.Pitches.FSharp6,keys.Lengths.EIGHTH,keys.GameState.SCALES)};
		for(Note p : notes)
			keyG.add(p);
		allKeys.put(keys.KeySignatures.GMajor, keyG);
		
		//Key of D
		notes = new Note[]{new Note(keys.Pitches.E5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.FSharp5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.G5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.A5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.B5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.CSharp6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.D6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.E6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.FSharp6,keys.Lengths.EIGHTH,keys.GameState.SCALES)};
		for(Note p : notes)
			keyD.add(p);
		allKeys.put(keys.KeySignatures.DMajor, keyD);
		
		//Key of A
		notes = new Note[]{new Note(keys.Pitches.E5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.FSharp5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.GSharp5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.A5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.B5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.CSharp6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.D6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.E6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.FSharp6,keys.Lengths.EIGHTH,keys.GameState.SCALES)};
		for(Note p : notes)
			keyA.add(p);
		allKeys.put(keys.KeySignatures.AMajor, keyA);
		
		//Key of E
		notes = new Note[]{new Note(keys.Pitches.E5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.FSharp5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.GSharp5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.A5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.B5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.CSharp6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.DSharp6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.E6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.FSharp6,keys.Lengths.EIGHTH,keys.GameState.SCALES)};
		for(Note p : notes)
			keyE.add(p);
		allKeys.put(keys.KeySignatures.EMajor, keyE);
		
		//Key of B
		notes = new Note[]{new Note(keys.Pitches.E5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.FSharp5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.GSharp5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.ASharp5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.B5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.CSharp6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.DSharp6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.E6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.FSharp6,keys.Lengths.EIGHTH,keys.GameState.SCALES)};
		for(Note p : notes)
			keyB.add(p);
		allKeys.put(keys.KeySignatures.BMajor, keyB);
		
		//Key of G Flat (or F Sharp)
		notes = new Note[]{new Note(keys.Pitches.ESharp5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.FSharp5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.GSharp5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.ASharp5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.B5,keys.Lengths.EIGHTH,keys.GameState.SCALES),
				new Note(keys.Pitches.CSharp6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.DSharp6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.ESharp6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.FSharp6,keys.Lengths.EIGHTH,keys.GameState.SCALES)};
		for(Note p : notes)
			keyGFlat.add(p);
		allKeys.put(keys.KeySignatures.FSharpMajor, keyGFlat);
		
		//Key of D Flat (or C Sharp)
		notes = new Note[]{new Note(keys.Pitches.ESharp5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.FSharp5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.GSharp5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.ASharp5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.C6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.CSharp6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.DSharp6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.ESharp6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.FSharp6,keys.Lengths.EIGHTH,keys.GameState.SCALES)};
		for(Note p : notes)
			keyDFlat.add(p);
		allKeys.put(keys.KeySignatures.DFlatMajor, keyDFlat);
		
		//Key of A Flat
		//TODO: Determine lower E5 flat use!!
		notes = new Note[]{new Note(keys.Pitches.F5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.G5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.GSharp5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.ASharp5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.C6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.CSharp6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.DSharp6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.F6,keys.Lengths.EIGHTH,keys.GameState.SCALES)};
		for(Note p : notes)
			keyAFlat.add(p);
		allKeys.put(keys.KeySignatures.AFlatMajor, keyAFlat);
		
		//Key of E Flat
		//TODO: Determine lower E5 flat use!!
		notes = new Note[]{new Note(keys.Pitches.F5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.G5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.GSharp5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.ASharp5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.C6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.D6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.DSharp6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.F6,keys.Lengths.EIGHTH,keys.GameState.SCALES)};
		for(Note p : notes)
			keyEFlat.add(p);
		allKeys.put(keys.KeySignatures.EFlatMajor, keyEFlat);
		
		//Key of B Flat 
		//TODO: Determine lower E5 flat use!!
		notes = new Note[]{new Note(keys.Pitches.F5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.G5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.A5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.ASharp5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.C6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.D6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.DSharp6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.F6,keys.Lengths.EIGHTH,keys.GameState.SCALES)};
		for(Note p : notes)
			keyBFlat.add(p);
		allKeys.put(keys.KeySignatures.BFlatMajor, keyBFlat);
		
		//Key of F (B flat)
		notes = new Note[]{new Note(keys.Pitches.E5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.F5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.G5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.A5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.ASharp5,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.C6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.D6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.E6,keys.Lengths.EIGHTH,keys.GameState.SCALES), 
				new Note(keys.Pitches.F6,keys.Lengths.EIGHTH,keys.GameState.SCALES)};		
		for(Note p : notes)
			keyF.add(p);
		allKeys.put(keys.KeySignatures.FMajor, keyF);
	}
		
	/**
	 * Checks if the specified pitch is in the given key.
	 * @param note - note to check the pitch of
	 * @param key - the current key signature
	 * @return true if the Note is in the Key specified
	 */
	public static boolean isNoteInKey(Note note, String key)
	{
		if(allKeys.containsKey(key) && allKeys.get(key).contains(new Note(note.getPitch(),keys.Lengths.EIGHTH,keys.GameState.SCALES)))
			return true;
		return false;
	}
	
	/**
	 * Returns an ArrayList of the notes that make up the given key signature. 
	 * Returns null if the key cannot be found.
	 * @param wantedKey - the wanted key
	 * @return - the notes that are in that key signature
	 */
	public static ArrayList<Note> getNotes(String wantedKey)
	{
		if(allKeys.containsKey(wantedKey))
			return allKeys.get(wantedKey);
		return null;
	}
}
