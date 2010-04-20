package keys;

import java.util.ArrayList;
import java.util.List;

public class GameOptions 
{
	public static final String KEY = "Key";
	public static final String TIME = "Time";
	public static final String TEMPO = "Tempo";
	public static final String BARS = "Bars";
	
	private static ArrayList<String> options = new ArrayList<String>();
	
	static
	{
		options.add(KEY);
		options.add(TIME);
		options.add(TEMPO);
		options.add(BARS);
	}
	
	public static List<String> getOptions()
	{
		return new ArrayList<String>(options);
	}
}
