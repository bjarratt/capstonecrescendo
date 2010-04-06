package displayManager;

/**
 *	This classifies what is intended to be played at each beat
 *
 *	@author Travis Kosarek
 */
public class Beat
{
	String pitch;

	public Beat()
	{
		pitch = "R";
	}

	public Beat(String p)
	{
		pitch = p;
	}

	public String getPitch()
	{
		return pitch;
	}

	public String toString()
	{
		return pitch;
	}
}