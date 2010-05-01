package keys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Pitches 
{
	private static ArrayList<String> notes = new ArrayList<String>();
	private static HashMap<String, Float> positions = new HashMap<String, Float>();
	
	public static final String E5 = "E5";
	public static final String ESharp5 = "ESharp5";
	public static final String FFlat5 = "FFlat5";
	public static final String F5 = "F5";
	public static final String FSharp5 = "FSharp5";
	public static final String GFlat5 = "GFlat5";
	public static final String G5 = "G5";
	public static final String GSharp5 = "GSharp5";
	public static final String AFlat5 = "AFlat5";
	public static final String A5 = "A5";
	public static final String ASharp5 = "ASharp5";
	public static final String BFlat5 = "BFlat5";
	public static final String B5 = "B5";
	public static final String BSharp5 = "BSharp5";
	public static final String CFlat6 = "CFlat6";
	public static final String C6 = "C6";
	public static final String CSharp6 = "CSharp6";
	public static final String DFlat6 = "DFlat6";
	public static final String D6 = "D6";
	public static final String DSharp6 = "DSharp6";
	public static final String EFlat6 = "EFlat6";
	public static final String E6 = "E6";
	public static final String ESharp6 = "ESharp6";
	public static final String FFlat6 = "FFlat6";
	public static final String F6 = "F6";
	public static final String FSharp6 = "FSharp6";
	public static final String Rest = "Rest";
	
	public static final String Sharp = "Sharp";
	public static final String Flat = "Flat";
	
	static
	{
		notes.add(FSharp6);
		notes.add(F6);
		notes.add(FFlat6);
		notes.add(ESharp6);
		notes.add(E6);
		notes.add(EFlat6);
		notes.add(DSharp6);
		notes.add(D6);
		notes.add(DFlat6);
		notes.add(CSharp6);
		notes.add(C6);
		notes.add(CFlat6);
		notes.add(BSharp5);
		notes.add(B5);
		notes.add(BFlat5);
		notes.add(ASharp5);
		notes.add(A5);
		notes.add(AFlat5);
		notes.add(GSharp5);
		notes.add(G5);
		notes.add(GFlat5);
		notes.add(FSharp5);
		notes.add(F5);
		notes.add(FFlat5);
		notes.add(ESharp5);
		notes.add(E5);
		
		float position = 0.5f;
		for (int i = 0; i < notes.size(); ++i)
		{
			if (i % 3 == 0)
			{
				position += 0.5f;
			}
			
			positions.put(notes.get(i), position);
		}
	}
	
	public static float getStaffPosition(String pitch)
	{
		float position = -1f;
		
		if (positions.containsKey(pitch))
		{
			position = positions.get(pitch);
		}
		
		return position;
	}
	
	public static List<String> getAllNotes()
	{
		return new ArrayList<String>(notes);
	}
}
