package displayManager;

import java.util.regex.Pattern;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.awt.Image;

public class Note
{
	private String _pitch;
	private String _length;

	private boolean _tiedLeft;
	private boolean _tiedRight;

	private ArrayList<Beat> _beats;

	private String _player;
	private ImageIcon _image;

	public Note(String pitch, String length, String player)
	{
		_pitch = pitch;
		_length = length;
		_player = player;
		_beats = new ArrayList<Beat>();

		for(int i = 0; i < this.getIntegerNoteLength(); i++)
			_beats.add(new Beat(_pitch));
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

	}

	public String getPlayer()
	{
		return _player;
	}

	public ArrayList<Beat> getBeats()
	{
		return _beats;
	}

	public int size()
	{
		return _beats.size();
	}

	public Image getImage()
	{
		return _image.getImage();
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

	public String getLength()
	{
		return _length;
	}

	public String getPitch()
	{
		return _pitch;
	}

	public boolean isTiedLeft()
	{
		return _tiedLeft;
	}

	public boolean isTiedRight()
	{
		return _tiedRight;
	}

	public void setTiedLeft(boolean tf)
	{
		_tiedLeft = tf;
	}

	public void setTiedRight(boolean tf)
	{
		_tiedRight = tf;
	}

	public Pattern getJFuguePattern()
	{
		Pattern p;
		if(_tiedLeft && _tiedRight)
			p = Pattern.compile(_pitch + "-" + _length + "-");
		else if(_tiedLeft)
			p = Pattern.compile(_pitch + "-" + _length);
		else if(_tiedRight)
			p = Pattern.compile(_pitch + _length + "-");
		else
			p = Pattern.compile(_pitch + _length);
		return p;
	}

	public String toString()
	{
		if(_tiedLeft && _tiedRight)
			return _player + " played " + _pitch + "-" + _length + "-";
		else if(_tiedLeft)
			return _player + " played " + _pitch + "-" + _length;
		else if(_tiedRight)
			return _player + " played " + _pitch + _length + "-";
		else
			return _player + " played " + _pitch + _length;
	}
}