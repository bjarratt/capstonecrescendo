package keys;

import gameManagement.messageTranslationSystem.Note;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * This class currently holds the arrays representing all of the notes in a given 
 * key signature. Because we only created Sharp String cards, all major flat keys feature
 * the corresponding sharps. 
 * @author Chris Aikens
 *
 */
public class Scales 
{
	private static ArrayList<String> keyC = new ArrayList<String>();
	private static ArrayList<String> keyG = new ArrayList<String>();
	private static ArrayList<String> keyD = new ArrayList<String>();
	private static ArrayList<String> keyA = new ArrayList<String>();
	private static ArrayList<String> keyE = new ArrayList<String>();
	private static ArrayList<String> keyB = new ArrayList<String>();
	private static ArrayList<String> keyGFlat = new ArrayList<String>();
	private static ArrayList<String> keyDFlat = new ArrayList<String>();
	private static ArrayList<String> keyAFlat = new ArrayList<String>();
	private static ArrayList<String> keyEFlat = new ArrayList<String>();
	private static ArrayList<String> keyBFlat = new ArrayList<String>();
	private static ArrayList<String> keyF = new ArrayList<String>();
	
	public static HashMap<String, ArrayList<String>> allKeys = new HashMap<String, ArrayList<String>>();
	
	static
	{
		//Key of C
		String[] notes = new String[]{new String(keys.Pitches.E5), 
				new String(keys.Pitches.F5), 
				new String(keys.Pitches.G5), 
				new String(keys.Pitches.A5), 
				new String(keys.Pitches.B5), 
				new String(keys.Pitches.C6), 
				new String(keys.Pitches.D6), 
				new String(keys.Pitches.E6), 
				new String(keys.Pitches.F6)};
		for(String note : notes)
			keyC.add(note);
		allKeys.put(keys.KeySignatures.CMajor, keyC);
		
		//Key of G
		notes = new String[]{new String(keys.Pitches.E5), 
				new String(keys.Pitches.FSharp5), 
				new String(keys.Pitches.G5), 
				new String(keys.Pitches.A5), 
				new String(keys.Pitches.B5), 
				new String(keys.Pitches.C6), 
				new String(keys.Pitches.D6), 
				new String(keys.Pitches.E6),
				new String(keys.Pitches.FSharp6)};
		for(String p : notes)
			keyG.add(p);
		allKeys.put(keys.KeySignatures.GMajor, keyG);
		
		//Key of D
		notes = new String[]{new String(keys.Pitches.E5), 
				new String(keys.Pitches.FSharp5), 
				new String(keys.Pitches.G5), 
				new String(keys.Pitches.A5), 
				new String(keys.Pitches.B5), 
				new String(keys.Pitches.CSharp6), 
				new String(keys.Pitches.D6), 
				new String(keys.Pitches.E6), 
				new String(keys.Pitches.FSharp6)};
		for(String p : notes)
			keyD.add(p);
		allKeys.put(keys.KeySignatures.DMajor, keyD);
		
		//Key of A
		notes = new String[]{new String(keys.Pitches.E5), 
				new String(keys.Pitches.FSharp5), 
				new String(keys.Pitches.GSharp5), 
				new String(keys.Pitches.A5), 
				new String(keys.Pitches.B5), 
				new String(keys.Pitches.CSharp6), 
				new String(keys.Pitches.D6), 
				new String(keys.Pitches.E6), 
				new String(keys.Pitches.FSharp6)};
		for(String p : notes)
			keyA.add(p);
		allKeys.put(keys.KeySignatures.AMajor, keyA);
		
		//Key of E
		notes = new String[]{new String(keys.Pitches.E5), 
				new String(keys.Pitches.FSharp5), 
				new String(keys.Pitches.GSharp5), 
				new String(keys.Pitches.A5), 
				new String(keys.Pitches.B5), 
				new String(keys.Pitches.CSharp6), 
				new String(keys.Pitches.DSharp6), 
				new String(keys.Pitches.E6), 
				new String(keys.Pitches.FSharp6)};
		for(String p : notes)
			keyE.add(p);
		allKeys.put(keys.KeySignatures.EMajor, keyE);
		
		//Key of B
		notes = new String[]{new String(keys.Pitches.E5), 
				new String(keys.Pitches.FSharp5), 
				new String(keys.Pitches.GSharp5), 
				new String(keys.Pitches.ASharp5), 
				new String(keys.Pitches.B5), 
				new String(keys.Pitches.CSharp6), 
				new String(keys.Pitches.DSharp6), 
				new String(keys.Pitches.E6), 
				new String(keys.Pitches.FSharp6)};
		for(String p : notes)
			keyB.add(p);
		allKeys.put(keys.KeySignatures.BMajor, keyB);
		
		//Key of G Flat (or F Sharp)
		notes = new String[]{new String(keys.Pitches.ESharp5), 
				new String(keys.Pitches.FSharp5), 
				new String(keys.Pitches.GSharp5), 
				new String(keys.Pitches.ASharp5), 
				new String(keys.Pitches.B5),
				new String(keys.Pitches.CSharp6), 
				new String(keys.Pitches.DSharp6), 
				new String(keys.Pitches.ESharp6), 
				new String(keys.Pitches.FSharp6)};
		for(String p : notes)
			keyGFlat.add(p);
		allKeys.put(keys.KeySignatures.FSharpMajor, keyGFlat);
		
		//Key of D Flat (or C Sharp)
		notes = new String[]{new String(keys.Pitches.ESharp5), 
				new String(keys.Pitches.FSharp5), 
				new String(keys.Pitches.GSharp5), 
				new String(keys.Pitches.ASharp5), 
				new String(keys.Pitches.C6), 
				new String(keys.Pitches.CSharp6), 
				new String(keys.Pitches.DSharp6), 
				new String(keys.Pitches.ESharp6), 
				new String(keys.Pitches.FSharp6)};
		for(String p : notes)
			keyDFlat.add(p);
		allKeys.put(keys.KeySignatures.DFlatMajor, keyDFlat);
		
		//Key of A Flat
		//TODO: Determine lower E5 flat use!!
		notes = new String[]{new String(keys.Pitches.F5), 
				new String(keys.Pitches.G5), 
				new String(keys.Pitches.GSharp5), 
				new String(keys.Pitches.ASharp5), 
				new String(keys.Pitches.C6), 
				new String(keys.Pitches.CSharp6), 
				new String(keys.Pitches.DSharp6), 
				new String(keys.Pitches.F6)};
		for(String p : notes)
			keyAFlat.add(p);
		allKeys.put(keys.KeySignatures.AFlatMajor, keyAFlat);
		
		//Key of E Flat
		//TODO: Determine lower E5 flat use!!
		notes = new String[]{new String(keys.Pitches.F5), 
				new String(keys.Pitches.G5), 
				new String(keys.Pitches.GSharp5), 
				new String(keys.Pitches.ASharp5), 
				new String(keys.Pitches.C6), 
				new String(keys.Pitches.D6), 
				new String(keys.Pitches.DSharp6), 
				new String(keys.Pitches.F6)};
		for(String p : notes)
			keyEFlat.add(p);
		allKeys.put(keys.KeySignatures.EFlatMajor, keyEFlat);
		
		//Key of B Flat 
		//TODO: Determine lower E5 flat use!!
		notes = new String[]{new String(keys.Pitches.F5), 
				new String(keys.Pitches.G5), 
				new String(keys.Pitches.A5), 
				new String(keys.Pitches.ASharp5), 
				new String(keys.Pitches.C6), 
				new String(keys.Pitches.D6), 
				new String(keys.Pitches.DSharp6), 
				new String(keys.Pitches.F6)};
		for(String p : notes)
			keyBFlat.add(p);
		allKeys.put(keys.KeySignatures.BFlatMajor, keyBFlat);
		
		//Key of F (B flat)
		notes = new String[]{new String(keys.Pitches.E5), 
				new String(keys.Pitches.F5), 
				new String(keys.Pitches.G5), 
				new String(keys.Pitches.A5), 
				new String(keys.Pitches.ASharp5), 
				new String(keys.Pitches.C6), 
				new String(keys.Pitches.D6), 
				new String(keys.Pitches.E6), 
				new String(keys.Pitches.F6)};		
		for(String p : notes)
			keyF.add(p);
		allKeys.put(keys.KeySignatures.FMajor, keyF);
	}
		
	/**
	 * Checks if the specified pitch is in the given key.
	 * @param note - note to check the pitch of
	 * @param key - the current key signature
	 * @return true if the String is in the Key specified
	 */
	public static boolean isNoteInKey(Note note, String key)
	{
		if(allKeys.containsKey(key) && allKeys.get(key).contains(note.getPitch()))
			return true;
		return false;
	}

	/**
	 * Checks if the specified pitch is in the given key.
	 * @param note - note to check the pitch of
	 * @param key - the current key signature
	 * @return true if the String is in the Key specified
	 */
	public static boolean isNoteInArpeggio(Note note, String key)
	{
		if(allKeys.containsKey(key) && allKeys.get(key).contains(note.getPitch()))
		{	
			if(key.equals(KeySignatures.AFlatMajor))
				if(note.getPitch().equals(Pitches.GSharp5) || note.getPitch().equals(Pitches.C6) || note.getPitch().equals(Pitches.DSharp6))
					return true;
			else if(key.equals(KeySignatures.AMajor))
				if(note.getPitch().equals(Pitches.A5) || note.getPitch().equals(Pitches.CSharp6) || note.getPitch().equals(Pitches.E5) || note.getPitch().equals(Pitches.E6))
					return true;
			else if(key.equals(KeySignatures.BFlatMajor))
				if(note.getPitch().equals(Pitches.ASharp5) || note.getPitch().equals(Pitches.D6) || note.getPitch().equals(Pitches.F5) || note.getPitch().equals(Pitches.F6))
					return true;
			else if(key.equals(KeySignatures.BMajor))
				if(note.getPitch().equals(Pitches.B5) || note.getPitch().equals(Pitches.DSharp6) || note.getPitch().equals(Pitches.FSharp5) || note.getPitch().equals(Pitches.FSharp6))
					return true;
			else if(key.equals(KeySignatures.CMajor))
				if(note.getPitch().equals(Pitches.C6) || note.getPitch().equals(Pitches.E5)|| note.getPitch().equals(Pitches.E6) || note.getPitch().equals(Pitches.G5))
					return true;
			else if(key.equals(KeySignatures.DFlatMajor))
				if(note.getPitch().equals(Pitches.CSharp6) || note.getPitch().equals(Pitches.ESharp5) || note.getPitch().equals(Pitches.ESharp6) || note.getPitch().equals(Pitches.GSharp5))
					return true;
			else if(key.equals(KeySignatures.DMajor))
				if(note.getPitch().equals(Pitches.D6) || note.getPitch().equals(Pitches.FSharp5) || note.getPitch().equals(Pitches.FSharp6) || note.getPitch().equals(Pitches.A5))
					return true;
			else if(key.equals(KeySignatures.EFlatMajor))
				if(note.getPitch().equals(Pitches.DSharp6) || note.getPitch().equals(Pitches.G5) || note.getPitch().equals(Pitches.ASharp5))
					return true;
			else if(key.equals(KeySignatures.EMajor))
				if(note.getPitch().equals(Pitches.E5) || note.getPitch().equals(Pitches.E6) || note.getPitch().equals(Pitches.GSharp5) || note.getPitch().equals(Pitches.B5))
					return true;
			else if(key.equals(KeySignatures.FMajor))
				if(note.getPitch().equals(Pitches.F5) || note.getPitch().equals(Pitches.F6) || note.getPitch().equals(Pitches.A5) || note.getPitch().equals(Pitches.C6))
					return true;
			else if(key.equals(KeySignatures.FSharpMajor))
				if(note.getPitch().equals(Pitches.FSharp5) || note.getPitch().equals(Pitches.FSharp6) || note.getPitch().equals(Pitches.ASharp5) || note.getPitch().equals(Pitches.CSharp6))
					return true;
			else if(key.equals(KeySignatures.GMajor))
				if(note.getPitch().equals(Pitches.G5) || note.getPitch().equals(Pitches.B5) || note.getPitch().equals(Pitches.D6))
					return true;
		}
		return false;
	}
	
	
	/**
	 * Returns an ArrayList of the notes that make up the given key signature. 
	 * Returns null if the key cannot be found.
	 * @param wantedKey - the wanted key
	 * @return - the notes that are in that key signature
	 */
	public static ArrayList<String> getNotes(String wantedKey)
	{
		if(allKeys.containsKey(wantedKey))
			return allKeys.get(wantedKey);
		return null;
	}
}
