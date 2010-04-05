package displayManager;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.awt.Image;

public class Note
{
	private String myPitch;
	private String myLength;

	private boolean tied_left;
	private boolean tied_right;

	private ArrayList<Beat> myBeats;

	private String myPlayer;
	private ImageIcon myImage;

	public Note(String pitch, String length, String player)
	{
		myPitch = pitch;
		myLength = length; //we are currently assuming only x/4 time so a quarter note is the basic beat
		myPlayer = player;
		myBeats = new ArrayList<Beat>();

		for(int i = 0; i < this.getIntegerNoteLength(); i++)
			myBeats.add(new Beat(myPitch));
	}

	public Note(ArrayList<Beat> beats, String player)
	{
		if(beats.size()>0)
		{
			String originalPitch = beats.get(0).getPitch();
			//check to see if all beats have matching pitches
			for(Beat beat : beats)
				if(!originalPitch.equals(beat.getPitch()))
					System.err.println("Error in Note(ArrayList<Beat> beats) " + beat.getPitch() + " does not match original pitch " + originalPitch);
			myPitch = originalPitch;
			myLength = getStringNoteLength(beats.size());
			myPlayer = player;
			myBeats = beats;
		}
		else
		{
			System.err.println("Error in Note(ArrayList<Beat> beats) : beats.size() <= 0");
			myPitch = "R";
			myLength = "i";
			myPlayer = "unknown";
			myBeats = new ArrayList<Beat>();
		}

	}

	public String getPlayer()
	{
		return myPlayer;
	}
	
	public ArrayList<Beat> getBeats()
	{
		return myBeats;
	}

	public int size()
	{
		return myBeats.size();
	}

	public Image getImage()
	{
		return myImage.getImage();
	}

	/**
	 * Returns the given length of the note in terms of the
	 * number of beat subdivisions that the note would occupy.
	 * In 4/4 time, an eighth note takes up ONE of the eight
	 * subdivisions, a quarter takes up TWO, etc. The number of
	 * subdivisions is determined by the smallest supported note
	 * length.
	 *
	 * @return - the length of the note as a function of subdivisions
	 */
	private int getIntegerNoteLength()
	{
		if(myLength.equals("i"))
			return 1;
		else if(myLength.equals("q"))
			return 2;
		else if(myLength.equals("q."))
			return 3;
		else if(myLength.equals("h"))
			return 4;
		else if(myLength.equals("h-" + myPitch + "-i"))
			return 5;
		else if(myLength.equals("h."))
			return 6;
		else if(myLength.equals("h.-" + myPitch + "-i"))
			return 7;
		else if(myLength.equals("w"))
			return 8;
		return 0;
	}

	public String getStringNoteLength(int length)
	{
		switch(length)
		{
			case 1:
				return "i";
			case 2:
				return "q";
			case 3:
				return "q.";
			case 4:
				return "h";
			case 5:
				return "h-" + myPitch + "-i";
			case 6:
				return "h.";
			case 7:
				return "h.-" + myPitch + "-i";
			case 8:
				return "w";
			default:
				return "i";
		}
	}

	public String getNote()
	{
		if(tied_left && tied_right)
			return myPitch + "-" + myLength + "-";
		else if(tied_left)
			return myPitch + "-" + myLength;
		else if(tied_right)
			return myPitch + myLength + "-";
		else
			return myPitch + myLength;
	}

	public String toString()
	{
		if(tied_left && tied_right)
			return myPitch + "-" + myLength + "-";
		else if(tied_left)
			return myPitch + "-" + myLength;
		else if(tied_right)
			return myPitch + myLength + "-";
		else
			return myPitch + myLength;
	}
}