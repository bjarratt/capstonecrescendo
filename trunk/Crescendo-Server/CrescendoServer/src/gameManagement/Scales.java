package gameManagement;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * This class currently holds the arrays representing all of the pitches in a given 
 * key signature. Because we only created Sharp Note cards, all major flat keys feature
 * the corresponding sharps. 
 * @author Chris Aikens
 *
 */
public class Scales 
{
	private ArrayList<String> keyC = new ArrayList<String>();
	private ArrayList<String> keyG = new ArrayList<String>();
	private ArrayList<String> keyD = new ArrayList<String>();
	private ArrayList<String> keyA = new ArrayList<String>();
	private ArrayList<String> keyE = new ArrayList<String>();
	private ArrayList<String> keyB = new ArrayList<String>();
	private ArrayList<String> keyGFlat = new ArrayList<String>();
	private ArrayList<String> keyDFlat = new ArrayList<String>();
	private ArrayList<String> keyAFlat = new ArrayList<String>();
	private ArrayList<String> keyEFlat = new ArrayList<String>();
	private ArrayList<String> keyBFlat = new ArrayList<String>();
	private ArrayList<String> keyF = new ArrayList<String>();
	
	private HashMap<String, ArrayList<String>> allKeys = new HashMap<String, ArrayList<String>>();
	
	public Scales()
	{
		//Key of C
		String[] pitches = new String[]{"E5", "F5", "G5", "A5", "B5", "C6", "D6", "E6", "F6"};
		for(String p : pitches)
			keyC.add(p);
		allKeys.put("keyC", keyC);
		
		//Key of G
		pitches = new String[]{"E5", "FSharp5", "G5", "A5", "B5", "C6", "D6", "E6", "FSharp6"};
		for(String p : pitches)
			keyG.add(p);
		allKeys.put("keyG", keyG);
		
		//Key of D
		pitches = new String[]{"E5", "FSharp5", "G5", "A5", "B5", "CSharp6", "D6", "E6", "FSharp6"};
		for(String p : pitches)
			keyD.add(p);
		allKeys.put("keyD", keyD);
		
		//Key of A
		pitches = new String[]{"E5", "FSharp5", "GSharp5", "A5", "B5", "CSharp6", "D6", "E6", "FSharp6"};
		for(String p : pitches)
			keyA.add(p);
		allKeys.put("keyA", keyA);
		
		//Key of E
		pitches = new String[]{"E5", "FSharp5", "GSharp5", "A5", "B5", "CSharp6", "DSharp6", "E6", "FSharp6"};
		for(String p : pitches)
			keyE.add(p);
		allKeys.put("keyE", keyE);
		
		//Key of B
		pitches = new String[]{"E5", "FSharp5", "GSharp5", "ASharp5", "B5", "CSharp6", "DSharp6", "E6", "FSharp6"};
		for(String p : pitches)
			keyB.add(p);
		allKeys.put("keyB", keyB);
		
		//Key of G Flat (or F Sharp
		pitches = new String[]{"ESharp5", "FSharp5", "GSharp5", "ASharp5", "B5", "CSharp6", "DSharp6", "ESharp6", "FSharp6"};
		for(String p : pitches)
			keyGFlat.add(p);
		allKeys.put("keyGFlat", keyGFlat);
		
		//Key of D Flat (or C Sharp)
		pitches = new String[]{"ESharp5", "FSharp5", "GSharp5", "ASharp5", "C6", "CSharp6", "DSharp6", "ESharp6", "FSharp6"};
		for(String p : pitches)
			keyDFlat.add(p);
		allKeys.put("keyDFlat", keyDFlat);
		
		//Key of A Flat
		//TODO: Determine lower E5 flat use!!
		pitches = new String[]{"F5", "G5", "GSharp5", "ASharp5", "C6", "CSharp6", "DSharp6", "F6"};
		for(String p : pitches)
			keyAFlat.add(p);
		allKeys.put("keyAFlat", keyAFlat);
		
		//Key of E Flat
		//TODO: Determine lower E5 flat use!!
		pitches = new String[]{"F5", "G5", "GSharp5", "ASharp5", "C6", "D6", "DSharp6", "F6"};
		for(String p : pitches)
			keyEFlat.add(p);
		allKeys.put("keyEFlat", keyEFlat);
		
		//Key of B Flat 
		//TODO: Determine lower E5 flat use!!
		pitches = new String[]{"F5", "G5", "A5", "ASharp5", "C6", "D6", "DSharp6", "F6"};
		for(String p : pitches)
			keyBFlat.add(p);
		allKeys.put("keyBFlat", keyBFlat);
		
		//Key of F (B flat)
		pitches = new String[]{"E5", "F5", "G5", "A5", "ASharp5", "C6", "D6", "E6", "F6"};		
		for(String p : pitches)
			keyF.add(p);
		allKeys.put("keyF", keyF);
	}
	
	/**
	 * Returns an arraylist of the pitches that make up the given key signature. 
	 * Returns null if the key cannot be found.
	 * @param wantedKey - the wanted key
	 * @return - the pitches that are in that key signature
	 */
	public ArrayList<String> getNotes(String wantedKey)
	{
		if(allKeys.containsKey(wantedKey))
		{
			return allKeys.get(wantedKey);
		}
		return null;
	}
}
