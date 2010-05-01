package keys;

import java.util.HashMap;

public class Lengths 
{
	public static final String WHOLE = "whole";
	public static final String HALF = "half";
	public static final String QUARTER = "quarter";
	public static final String EIGHTH = "eighth";
	
	private static HashMap<Integer, Integer> numericalLengths = new HashMap<Integer, Integer>();
	
	static
	{
		numericalLengths.put(8, 1);
		numericalLengths.put(4, 2);
		numericalLengths.put(2, 4);
		numericalLengths.put(1, 8);
	}
	
	public static int getNumericalLength(int i)
	{
		int length = 0;
		if (numericalLengths.containsKey(i))
		{
			length = numericalLengths.get(i);
		}
		return length;
	}
}
