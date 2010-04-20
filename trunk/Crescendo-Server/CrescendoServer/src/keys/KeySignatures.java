package keys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KeySignatures 
{
	// 12 Major scales
	public static String FMajor = "FMajor";
	public static String CMajor = "CMajor";
	public static String GMajor = "GMajor";
	public static String DMajor = "DMajor";
	public static String AMajor = "AMajor";
	public static String EMajor = "EMajor";
	public static String BMajor = "BMajor";
	public static String FSharpMajor = "FSharpMajor";
	public static String BFlatMajor = "BFlatMajor";
	public static String EFlatMajor = "EFlatMajor";
	public static String AFlatMajor = "AFlatMajor";
	public static String DFlatMajor = "DFlatMajor";
	
	private static HashMap<String, String[]> keys = new HashMap<String, String[]>();
	
	static
	{
		keys.put(FMajor, new String[] {Pitches.BFlat5});
		keys.put(CMajor, new String[] {});
		keys.put(GMajor, new String[] {Pitches.FSharp6});
		keys.put(DMajor, new String[] {Pitches.FSharp6, Pitches.CSharp6});
		keys.put(AMajor, new String[] {Pitches.FSharp6, Pitches.CSharp6, Pitches.GSharp5});
		keys.put(EMajor, new String[] {Pitches.FSharp6, Pitches.CSharp6, Pitches.GSharp5, Pitches.DSharp6});
		keys.put(BMajor, new String[] {Pitches.FSharp6, Pitches.CSharp6, Pitches.GSharp5, Pitches.DSharp6, Pitches.ASharp5});
		keys.put(FSharpMajor, new String[] {Pitches.FSharp6, Pitches.CSharp6, Pitches.GSharp5, Pitches.DSharp6, Pitches.ASharp5, Pitches.ESharp6});
		keys.put(BFlatMajor, new String[] {Pitches.BFlat5, Pitches.EFlat6});
		keys.put(EFlatMajor, new String[] {Pitches.BFlat5, Pitches.EFlat6, Pitches.AFlat5});
		keys.put(AFlatMajor, new String[] {Pitches.BFlat5, Pitches.EFlat6, Pitches.AFlat5, Pitches.DFlat6});
		keys.put(DFlatMajor, new String[] {Pitches.BFlat5, Pitches.EFlat6, Pitches.AFlat5, Pitches.DFlat6, Pitches.GFlat5});
	}
	
	public static List<String> getKeySignature(String key)
	{
		ArrayList<String> keySig = null;
		
		if (keys.containsKey(key))
		{
			keySig = new ArrayList<String>();
			String[] accidentals = keys.get(key);
			
			for (String accidental : accidentals)
			{
				keySig.add(accidental);
			}
		}
		
		return keySig;
	}
}
