package gameManagement.messageTranslationSystem;

/**
 *	This classifies what is intended to be played at each beat
 *
 *	@author Travis Kosarek
 */
public class Beat
{
	String pitch;

	/**
	 *	The definiton of the smallest length a Note can be
	 *	Default pitch is a rest
	 */
	public Beat()
	{
		pitch = "rest";
	}

	/**
	 *	The definiton of the smallest length a Note can be
	 *
	 *	@param p the pitch representing the Beat
	 */
	public Beat(String p)
	{
		pitch = p;
	}

	/**
	 *	Get method to return the pitch
	 *
	 *	@return a pitch in the format of a String
	 */
	public String getPitch()
	{
		return pitch;
	}

	/**
	 *	The String representation of a Beat
	 *
	 *	@return the String representation of a Beat
	 */
	public String toString()
	{
		return pitch;
	}
}