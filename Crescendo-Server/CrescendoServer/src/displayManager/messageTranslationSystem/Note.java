package displayManager.messageTranslationSystem;


import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.awt.Image;

import org.jfugue.*;

public class Note
{
	//The basics of a note
	private String _pitch;
	private String _length;

	//is the note tied to the left or the right
	private boolean _tiedLeft;
	private boolean _tiedRight;

	//a representation of the Note as a list of Beats
	private ArrayList<Beat> _beats;

	//the player that sent the note
	private String _player;

	//the image associated with the the note to be played
	private ImageIcon _image;

	/**
	 *	A constructor that sets up a Note from String variables
	 *
	 *	@param pitch the desired pitch of the Note in a String format
	 *	@param length the desired length of the Note in a String format
	 *	@param player the iPhone that played the Note
	 */
	public Note(String pitch, String length, String player)
	{
		_pitch = pitch;
		_length = length;
		_player = player;
		_beats = new ArrayList<Beat>();

		for(int i = 0; i < this.getIntegerNoteLength(); i++)
			_beats.add(new Beat(_pitch));

		//the format of the image is playerX_Y, for example: player2_4 means player 2 half note
		_image = new ImageIcon(_player + "_" + getIntegerNoteLength() + ".png");
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
			_pitch = originalPitch;
			_length = getStringNoteLength(beats.size());
			_player = player;
			_beats = beats;
		}
		else
		{
			System.err.println("Error in Note(ArrayList<Beat> beats) : beats.size() <= 0");
			_pitch = "R";
			_length = "i";
			_player = "unknown";
			_beats = new ArrayList<Beat>();
		}
		_image = new ImageIcon(_player + "_" + getIntegerNoteLength() + ".png");
	}

	/**
	 *	Returns the iPhone that played the note
	 *
	 *	@return the iPhone that played the note
	 */
	public String getPlayer()
	{
		return _player;
	}

	/**
	 *	Returns the Beat representation of the Note
	 *
	 *	@return the list of Beat objects representing a Note
	 */
	public ArrayList<Beat> getBeats()
	{
		return _beats;
	}


	/**
	 *	Returns the length of the Note as an integer
	 *
	 *	@return the length of the Note as an integer
	 */
	public int size()
	{
		return _beats.size();
	}


	/**
	 *	Returns the Image to be displayed on the screen associated with this Note
	 *
	 *	@return the Image to be displayed on the screen associated with this Note
	 */
	public Image getImage()
	{
		playNote();
		return _image.getImage();
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
		if(_length.equals("i"))
			return 1;
		else if(_length.equals("q"))
			return 2;
		else if(_length.equals("q."))
			return 3;
		else if(_length.equals("h"))
			return 4;
		else if(_length.equals("h-" + _pitch + "-i"))
			return 5;
		else if(_length.equals("h."))
			return 6;
		else if(_length.equals("h.-" + _pitch + "-i"))
			return 7;
		else if(_length.equals("w"))
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
				return "i";
			case 2:
				return "q";
			case 3:
				return "q.";
			case 4:
				return "h";
			case 5:
				return "h-" + _pitch + "-i";
			case 6:
				return "h.";
			case 7:
				return "h.-" + _pitch + "-i";
			case 8:
				return "w";
			default:
				return "i";
		}
	}

	/**
	 *	Returns the length of the Note
	 *
	 *	@return the length of the Note
	 */
	public String getLength()
	{
		return _length;
	}

	/**
	 *	Returns the pitch of the Note
	 *
	 *	@return the pitch of the Note
	 */
	public String getPitch()
	{
		return _pitch;
	}

	/**
	 *	Returns true if the Note is tied to another Note from the left
	 *
	 *	@return true if the Note is tied to another Note from the left
	 */
	public boolean isTiedLeft()
	{
		return _tiedLeft;
	}

	/**
	 *	Returns true if the Note is tied to another Note from the right
	 *
	 *	@return true if the Note is tied to another Note from the right
	 */
	public boolean isTiedRight()
	{
		return _tiedRight;
	}

	/**
	 *	Sets if the Note is tied to another Note from the left
	 *
	 *	@param tf if true, the note is tied from the left, otherwise false
	 */
	public void setTiedLeft(boolean tf)
	{
		_tiedLeft = tf;
	}

	/**
	 *	Sets if the Note is tied to another Note from the right
	 *
	 *	@param tf if true, the note is tied from the right, otherwise false
	 */
	public void setTiedRight(boolean tf)
	{
		_tiedRight = tf;
	}

	/**
	 *	Returns the JFuguePattern needed for the Note to be played in JFugue
	 *
	 *	@return the JFuguePattern needed for the Note to be played in JFugue
	 */
	public Pattern getJFuguePattern()
	{
		Pattern p;
		if(_tiedLeft && _tiedRight)
			p = new Pattern(_pitch + "-" + _length + "- ");
		else if(_tiedLeft)
			p = new Pattern(_pitch + "-" + _length);
		else if(_tiedRight)
			p = new Pattern(_pitch + _length + "- ");
		else
			p = new Pattern(_pitch + _length);
		return p;
	}

	/**
	 *	Returns a String representation of the note for an easy text version of the Note
	 *
	 *	@return a String representation of the note for an easy text version of the Note
	 */
	public String toString()
	{
		if(_tiedLeft && _tiedRight)
			return _pitch + "- " + _length + "-";
		else if(_tiedLeft)
			return _pitch + "- " + _length;
		else if(_tiedRight)
			return _pitch + _length + "-";
		else
			return _pitch + _length;
	}
}