package displayManager.messageTranslationSystem;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

import org.jfugue.Pattern;
/**
 *	The Images class is a placeholder for image reading functions
 *
 *	@author Brandon Jarratt
 */
public class Images
{
	
	//the mapping between note pitches and their staff positions (bottom to top)
	public static HashMap<String, Integer> pitches = new HashMap<String, Integer>();
	
	private static void initialize()
	{
		pitches.put("E5", 164);
		pitches.put("E#5", 164);
		pitches.put("F5", 155);
		pitches.put("F#5", 155);
		pitches.put("G5", 140);
		pitches.put("G#5", 140);
		pitches.put("A5", 131);
		pitches.put("A#5", 131);
		pitches.put("B5", 142);
		pitches.put("C6", 131);
		pitches.put("C#6", 131);
		pitches.put("D6", 118);
		pitches.put("D#6", 118);
		pitches.put("E6", 107);
		pitches.put("E#6", 107);
		pitches.put("F6", 94);
		pitches.put("F#6", 94);
	}
	
	public static Image getStaffImage(Image image)
	{
		Image img = null;
		try
        {
			img = ImageIO.read(new File("staff.png"));
		}
        catch (IOException e)
        {
			e.printStackTrace();
		}
		return img;
	}
	
	public static Image getNoteImage(Image image, Note n)
	{
		Image img = null;	//the image to be returned
		Pattern p = n.getJFuguePattern();
		try {
			//check for the sixth octave, returning flipped images
			if(p.getMusicString().contains("6") || p.getMusicString().contains("B5")){
				if (p.getMusicString().contains("i"))
					img = ImageIO.read(new File("eighth_note_flipped.png"));
				else if (p.getMusicString().contains("q"))
					img = ImageIO.read(new File("quarter_note_flipped.png"));
				else if (p.getMusicString().contains("h"))
					img = ImageIO.read(new File("half_note_flipped.png"));
				else if (p.getMusicString().contains("w"))
					img = ImageIO.read(new File("whole_note_flipped.png"));
				else
					img = ImageIO.read(new File("whole_note_flipped.png"));
			}
			//check for the fifth octave, returning regular images
			else if(p.getMusicString().contains("5")){
				if (p.getMusicString().contains("i"))
					img = ImageIO.read(new File("eighth_note.png"));
				else if (p.getMusicString().contains("q"))
					img = ImageIO.read(new File("quarter_note.png"));
				else if (p.getMusicString().contains("h"))
					img = ImageIO.read(new File("half_note.png"));
				else if (p.getMusicString().contains("w"))
					img = ImageIO.read(new File("whole_note.png"));
				else
					img = ImageIO.read(new File("whole_note.png"));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return img;
	}
	
	public static int getNotePosition(Note n)
	{
		if(pitches.isEmpty())
		{
			initialize();
		}
		
		Pattern p = n.getJFuguePattern();
		String pitch = p.getMusicString();
		int place = pitches.get(pitch.substring(0, pitch.length()-1));
		
		return place;
	}
}