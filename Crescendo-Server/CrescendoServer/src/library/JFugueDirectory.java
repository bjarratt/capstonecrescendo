package library;

import org.jfugue.*;
import java.util.*;

/**
 * This class is used to translate messages from the Connection Manager into JFugue patterns. 
 * Patterns in JFugue act as segments of the music, and can be combined to form songs. This
 * class parses a string from the Connection Manager, looks up the requested note pitch and length, 
 * and then creates a JFugue pattern from that information. The patterns will be used to display and 
 * playback the compositions.
 * @author Chris Aikens
 *
 */
public class JFugueDirectory {
	
	
	//the message received from the server
	public String serverMessage;
	
	//the new pattern to be created upon request
	public Pattern newPattern = new Pattern();
	
	
	//the potential sets of pitches and lengths are stored in hash maps
	public HashMap<String, String> pitches = new HashMap<String, String>();
	public HashMap<String, String> lengths = new HashMap<String, String>();
	
	
	/**
	 * Upon construction, populate the HashMaps for pitches and lengths
	 * with the supported values. These will later be used for changing
	 * requested strings into JFugue Patterns.
	 */
	public JFugueDirectory()
	{
		pitches.put("D5", "D5");
		pitches.put("DSharp5", "D#5");
		pitches.put("E5", "E5");
		pitches.put("ESharp5", "E#5");
		pitches.put("F5", "F5");
		pitches.put("FSharp5", "F#5");
		pitches.put("G5", "G5");
		pitches.put("GSharp5", "G#5");
		pitches.put("A5", "A5");
		pitches.put("ASharp5", "A#5");
		pitches.put("B5", "B5");
		pitches.put("C6", "C6");
		pitches.put("CSharp6", "C#6");
		pitches.put("D6", "D6");
		pitches.put("DSharp6", "D65");
		pitches.put("E6", "E6");
		pitches.put("ESharp6", "E#6");
		pitches.put("F6", "F6");
		
		//A rest is also specified in pitches
		pitches.put("rest", "R");
		
		lengths.put("eighthnote", "i");
		lengths.put("quarternote", "q");
		lengths.put("halfnote", "h");
		lengths.put("wholenote", "w");
		
		
	}
	
	/**
	 * getPattern parses a string to get the requested note pitch and length.
	 * The pitch and length are then converted to a JFugue pattern by searching 
	 * through the supported sets of notes and pitches. The resulting pattern is
	 * returned.
	 * @param serverMessage
	 * @return JFugue Pattern
	 */
	public Pattern getPattern(String request)
	{
		String foundLength;
		String foundPitch;
		this.serverMessage = request;
		
		//begin parsing the string
		String[] tokens = serverMessage.split("/");
		for(int i = 0; i < tokens.length; i++)
		{
			System.out.println(tokens[i]);
		}
		foundLength = getLength(tokens[0]);
		foundPitch = getPitch(tokens[1]);
		
		System.out.println(foundLength + " " + foundPitch);
		
		String result = foundPitch + foundLength;
		this.newPattern = new Pattern(result);
		
		return newPattern;
		
	}
	
	/**
	 * Takes the desired note length and finds the corresponding
	 * JFugue length pattern. For prototype 1, eight, quarter, half, 
	 * and whole notes will be supported. 
	 * @param length
	 * @return JFugue length pattern
	 */
	public String getLength(String length){
		return lengths.get(length);
		
	}
	/**
	 * Takes the desired note pitch and finds the corresponding 
	 * JFugue pitch pattern. The supported pitches are all on the
	 * staff (from D# up to F). Other pitches can be supported as needed/wanted.
	 * @param pitch
	 * @return JFugue pitch pattern
	 */
	public String getPitch(String pitch){
		return pitches.get(pitch);

	}
	
}

