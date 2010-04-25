package keys;

import java.util.ArrayList;
import java.util.HashMap;


public class ChordProgressions
{
	public static HashMap<String, ArrayList<String>> chordProgressions = new HashMap<String, ArrayList<String>>();
	
	static
	{
		ArrayList<String> k = new ArrayList<String>();
		
		// CMajor
		k.add(KeySignatures.CMajor);
		k.add(KeySignatures.FMajor);
		k.add(KeySignatures.GMajor);
		chordProgressions.put(KeySignatures.CMajor, k);
		
		// DFlatMajor
		k = new ArrayList<String>();
		k.add(KeySignatures.DFlatMajor);
		k.add(KeySignatures.FSharpMajor);
		k.add(KeySignatures.AFlatMajor);
		chordProgressions.put(KeySignatures.DFlatMajor, k);
		
		// DMajor
		k = new ArrayList<String>();
		k.add(KeySignatures.DMajor);
		k.add(KeySignatures.GMajor);
		k.add(KeySignatures.AMajor);
		chordProgressions.put(KeySignatures.DMajor, k);
		
		// EFlatMajor
		k = new ArrayList<String>();
		k.add(KeySignatures.EFlatMajor);
		k.add(KeySignatures.AFlatMajor);
		k.add(KeySignatures.BFlatMajor);
		chordProgressions.put(KeySignatures.EFlatMajor, k);
		
		// EMajor
		k = new ArrayList<String>();
		k.add(KeySignatures.EMajor);
		k.add(KeySignatures.AMajor);
		k.add(KeySignatures.BMajor);
		chordProgressions.put(KeySignatures.EMajor, k);
		
		// FMajor
		k = new ArrayList<String>();
		k.add(KeySignatures.FMajor);
		k.add(KeySignatures.BFlatMajor);
		k.add(KeySignatures.CMajor);
		chordProgressions.put(KeySignatures.FMajor, k);
		
		// FSharpMajor
		k = new ArrayList<String>();
		k.add(KeySignatures.FSharpMajor);
		k.add(KeySignatures.BMajor);
		k.add(KeySignatures.DFlatMajor);
		chordProgressions.put(KeySignatures.FSharpMajor, k);
		
		// GMajor
		k = new ArrayList<String>();
		k.add(KeySignatures.GMajor);
		k.add(KeySignatures.CMajor);
		k.add(KeySignatures.DMajor);
		chordProgressions.put(KeySignatures.GMajor, k);
		
		// AFlatMajor
		k = new ArrayList<String>();
		k.add(KeySignatures.AFlatMajor);
		k.add(KeySignatures.DFlatMajor);
		k.add(KeySignatures.EFlatMajor);
		chordProgressions.put(KeySignatures.AFlatMajor, k);
		
		// AMajor
		k = new ArrayList<String>();
		k.add(KeySignatures.AMajor);
		k.add(KeySignatures.DMajor);
		k.add(KeySignatures.EMajor);
		chordProgressions.put(KeySignatures.AMajor, k);
		
		// BFlatMajor
		k = new ArrayList<String>();
		k.add(KeySignatures.BFlatMajor);
		k.add(KeySignatures.EFlatMajor);
		k.add(KeySignatures.FMajor);
		chordProgressions.put(KeySignatures.BFlatMajor, k);
		
		// BMajor
		k = new ArrayList<String>();
		k.add(KeySignatures.BMajor);
		k.add(KeySignatures.EMajor);
		k.add(KeySignatures.FSharpMajor);
		chordProgressions.put(KeySignatures.BMajor, k);
	}
}
