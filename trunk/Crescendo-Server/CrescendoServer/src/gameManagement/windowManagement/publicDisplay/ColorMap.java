package gameManagement.windowManagement.publicDisplay;

import java.awt.Color;
import java.util.HashMap;

import keys.Players;

public class ColorMap 
{
	private static HashMap<String, Color> colors = new HashMap<String, Color>();
	
	static
	{
		colors.put(Players.PLAYER_ONE, Color.getHSBColor(0f, 1f, 0.8f));
		colors.put(Players.PLAYER_TWO, Color.getHSBColor(0.36f, 0.9f, 0.6f));
		colors.put(Players.PLAYER_THREE, Color.getHSBColor(0.68f, 1f, 0.6f));
		colors.put(Players.PLAYER_FOUR, Color.getHSBColor(0f, 0.6f, 0.9f));
	}
	
	public static Color getPlayerColor(String key)
	{
		Color color = null;
		
		if (key != null && colors.containsKey(key))
		{
			color = colors.get(key);
		}
		
		return color;
	}

	public static HashMap<String, Color> getPlayerColorMap()
	{
		return new HashMap<String, Color>(colors);
	}
}
