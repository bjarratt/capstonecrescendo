package gameManagement.messageTranslationSystem;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jfugue.*;

public class Note
{
	//The basics of a note in string format as read in from the message
	private String pitch;
	private String length;
	
	//These hold the JFugue Pattern of the pitch and length
	private String jPitch;
	private String jLength;
	
	//is the note tied to the left or the right
	private boolean tiedLeft;
	private boolean tiedRight;

	//a representation of the Note as a list of Beats
	private ArrayList<Beat> beats;

	//the player that sent the note
	private String player;

	//the image associated with the the note to be played
	private BufferedImage image;
	
	private boolean correct;

	/**
	 *	A constructor that sets up a Note from String variables
	 *
	 *	@param pitch the desired pitch of the Note in a String format
	 *	@param length the desired length of the Note in a String format
	 *	@param player the iPhone that played the Note
	 */
	public Note(String pitch, String length, String player)
	{
		this.pitch = pitch;
		this.jPitch = MessageTranslationEngine.pitches.get(pitch);
		this.length = length;
		this.jLength = MessageTranslationEngine.lengths.get(length);
		this.player = player;
		beats = new ArrayList<Beat>();
		correct = true;

		for(int i = 0; i < this.getIntegerNoteLength(); i++)
			beats.add(new Beat(this.pitch));
		
		try
		{
			image = ImageIO.read(new File(this.length + ".png"));
		}
		catch(IOException e)
		{
			//TODO ignore for now
			//e.printStackTrace();
		}
	}

	/**
	 *	A constructor that creates a Note from a list of beats and what iPhone played the Note
	 *
	 *	@param beats a representation of the Note as a list of Beat objects
	 *	@param player the iPhone that played the Note
	 */
	public Note(ArrayList<Beat> beats, String player)
	{
		if(beats.size()>0)
		{
			String originalPitch = beats.get(0).getPitch();
			
			//check to see if all beats have matching pitches
			for(Beat beat : beats)
				if(!originalPitch.equals(beat.getPitch()))
					System.err.println("Error in Note(ArrayList<Beat> beats) " + beat.getPitch() + " does not match original pitch " + originalPitch);
			
			this.pitch = originalPitch;
			this.jPitch = MessageTranslationEngine.pitches.get(pitch);
			this.length = getStringNoteLength(beats.size());
			this.jLength = MessageTranslationEngine.lengths.get(length);
			this.player = player;
			this.beats = beats;
		}
		else
		{
			System.err.println("Error in Note(ArrayList<Beat> beats) : beats.size() <= 0");
			this.pitch = "rest";
			this.jPitch = MessageTranslationEngine.pitches.get(pitch);
			this.length = "eighth";
			this.jLength = MessageTranslationEngine.lengths.get(length);
			this.player = "unknown";
			this.beats = new ArrayList<Beat>();
		}
		
		correct = true;
		
		try
		{
			image = ImageIO.read(new File(this.length + ".png"));
		}
		catch(IOException e)
		{
			//TODO ignore for now
			//e.printStackTrace();
		}
	}

	/**
	 *	Sets if the Note played is correct or not
	 *
	 *	@return if the Note played is correct or not
	 */
	public void setCorrect(boolean c)
	{
		correct = c;
	}
	
	/**
	 *	Returns if the Note played is correct or not
	 *
	 *	@return if the Note played is correct or not
	 */
	public boolean isCorrect()
	{
		return correct;
	}
	
	/**
	 *	Returns the iPhone that played the note
	 *
	 *	@return the iPhone that played the note
	 */
	public String getPlayer()
	{
		return player;
	}

	/**
	 *	Returns the Beat representation of the Note
	 *
	 *	@return the list of Beat objects representing a Note
	 */
	public ArrayList<Beat> getBeats()
	{
		return beats;
	}

	/**
	 *	Returns the length of the Note as an integer
	 *
	 *	@return the length of the Note as an integer
	 */
	public int size()
	{
		return beats.size();
	}

	/**
	 *	Returns the Image to be displayed on the screen associated with this Note
	 *
	 *	@return the Image to be displayed on the screen associated with this Note
	 */
	public BufferedImage getImage()
	{
		return image;
	}

	/**
	 *	This method send the JFugue Pattern to JFugue to be played audibly
	 */
	public void playNote()
	{
		org.jfugue.Player jfuguePlayer = new org.jfugue.Player();
		org.jfugue.Pattern p = this.getJFuguePattern();
		jfuguePlayer.play(p);
	}

	/**
	 *	Returns the length of the Note as parsed by the JFugue Pattern
	 *
	 *	@return - the number of Beats in a Note
	 */
	private int getIntegerNoteLength()
	{
		if(length.equals("eighth"))
			return 1;
		else if(length.equals("quarter"))
			return 2;
		else if(length.equals("quarter_dot"))
			return 3;
		else if(length.equals("half"))
			return 4;
		else if(length.equals("half_tie_eighth"))
			return 5;
		else if(length.equals("half_dot"))
			return 6;
		else if(length.equals("half_dot_tie_eighth"))
			return 7;
		else if(length.equals("whole"))
			return 8;
		return 0;
	}

	/**
	 *	Returns length of the note as the String representation needed by JFugue
	 *
	 *	@param length the number of Beats in a Note
	 *	@return the String representation of the Note as needed by JFugue
	 */
	public String getStringNoteLength(int length)
	{
		switch(length)
		{
			case 1:
				return "eighth";
			case 2:
				return "quarter";
			case 3:
				return "quarter_dot";
			case 4:
				return "half";
			case 5:
				return "half_tie_eighth";
			case 6:
				return "half_dot";
			case 7:
				return "half_dot_tie_eighth";
			case 8:
				return "whole";
			default:
				return "eighth";
		}
	}

	/**
	 *	Returns the length of the Note
	 *
	 *	@return the length of the Note
	 */
	public String getLength()
	{
		return length;
	}

	/**
	 *	Returns the pitch of the Note
	 *
	 *	@return the pitch of the Note
	 */
	public String getPitch()
	{
		return pitch;
	}
	
	/**
	 *	Returns the JFugue String representing the length of the Note
	 *
	 *	@return the JFugue String representing the length of the Note
	 */
	public String getJFugueLength()
	{
		return jLength;
	}

	/**
	 *	Returns the JFugue String representing the pitch of the Note
	 *
	 *	@return the JFugue String representing the pitch of the Note
	 */
	public String getJFuguePitch()
	{
		return jPitch;
	}

	/**
	 *	Returns true if the Note is tied to another Note from the left
	 *
	 *	@return true if the Note is tied to another Note from the left
	 */
	public boolean isTiedLeft()
	{
		return tiedLeft;
	}

	/**
	 *	Returns true if the Note is tied to another Note from the right
	 *
	 *	@return true if the Note is tied to another Note from the right
	 */
	public boolean isTiedRight()
	{
		return tiedRight;
	}

	/**
	 *	Sets if the Note is tied to another Note from the left
	 *
	 *	@param tf if true, the note is tied from the left, otherwise false
	 */
	public void setTiedLeft(boolean tf)
	{
		tiedLeft = tf;
	}

	/**
	 *	Sets if the Note is tied to another Note from the right
	 *
	 *	@param tf if true, the note is tied from the right, otherwise false
	 */
	public void setTiedRight(boolean tf)
	{
		tiedRight = tf;
	}
	
	/**
	 * Checks if the inputed note has the same pitch and length of this note
	 * 
	 * @param note the note to be compared
	 * @return if the Notes are equal or not
	 */
	public boolean equals(Note note)
	{
		if(this.getPitch().equals(note.getPitch()) && this.getLength().equals(note.getLength()))
			return true;
		return false;
	}

	/**
	 *	Returns the JFuguePattern needed for the Note to be played in JFugue
	 *
	 *	@return the JFuguePattern needed for the Note to be played in JFugue
	 */
	public Pattern getJFuguePattern()
	{
		Pattern p;
		if(tiedLeft && tiedRight)
			p = new Pattern(jPitch + "-" + jLength + "- ");
		else if(tiedLeft)
			p = new Pattern(jPitch + "-" + jLength);
		else if(tiedRight)
			p = new Pattern(jPitch + jLength + "- ");
		else
			p = new Pattern(jPitch + jLength);
		return p;
	}

	/**
	 *	Returns a String representation of the note for an easy text version of the Note
	 *
	 *	@return a String representation of the note for an easy text version of the Note
	 */
	public String toString()
	{
		if(tiedLeft && tiedRight)
			return "tie_" + pitch + "_" + length + "_tie";
		else if(tiedLeft)
			return "tie_" + pitch + "_" + length;
		else if(tiedRight)
			return pitch + "_" + length + "_tie";
		else
			return pitch + "_" + length;
	}
}