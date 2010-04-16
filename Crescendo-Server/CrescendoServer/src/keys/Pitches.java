package keys;

import java.util.ArrayList;
import java.util.List;

public class Pitches 
{
	private static ArrayList<String> notes = new ArrayList<String>();
	
	public static final String E5 = "E5";
	public static final String F5 = "F5";
	public static final String FSharp5 = "FSharp5";
	public static final String G5 = "G5";
	public static final String GSharp5 = "GSharp5";
	public static final String A5 = "A5";
	public static final String ASharp5 = "ASharp5";
	public static final String B5 = "B5";
	public static final String BSharp5 = "BSharp5";
	public static final String C6 = "C6";
	public static final String CSharp6 = "CSharp6";
	public static final String D6 = "D6";
	public static final String DSharp6 = "DSharp6";
	public static final String E6 = "E6";
	public static final String ESharp6 = "ESharp6";
	public static final String F6 = "F6";
	public static final String FSharp6 = "FSharp6";
	public static final String Rest = "Rest";
	
	static
	{
		notes.add(FSharp6);
		notes.add(F6);
		notes.add(ESharp6);
		notes.add(E6);
		notes.add(DSharp6);
		notes.add(D6);
		notes.add(CSharp6);
		notes.add(C6);
		notes.add(BSharp5);
		notes.add(B5);
		notes.add(ASharp5);
		notes.add(A5);
		notes.add(GSharp5);
		notes.add(G5);
		notes.add(FSharp5);
		notes.add(F5);
		notes.add(E5);
	}
	
	public static List<String> getAllNotes()
	{
		return new ArrayList<String>(notes);
	}
}