package gameManagement.windowManagement.help;

import keys.Players;
import processing.core.PImage;
import gameManagement.messageTranslationSystem.Note;

/**
 * This class is a watered down version of the <code>Note</code> class in messageTranslationSystem
 * with only the information needed to display to the user.
 * @author Zach
 *
 */
public class DisplayNote 
{
	public DisplayNote() {}

	public DisplayNote(Note note)
	{
		if (note != null)
		{
			player = Players.getPlayers().indexOf(note.getPlayer());
			length = note.size();
			pitch = note.getPitch();
			isTied = note.isTied();
			strLength = note.getStringNoteLength(length);
		}
	}
	
	public int getPlayer()
	{
		return player;
	}
	
	public void setPlayer(int index)
	{
		player = index;
	}
	
	public int getLength()
	{
		return length;
	}
	
	public void setLength(int l)
	{
		length = l;
	}
	
	public float getStaffPosition()
	{
		return staffPosition;
	}
	
	public void setStaffPosition(float position)
	{
		staffPosition = position;
	}
	
	public String getPitch()
	{
		return pitch;
	}
	
	public void setPitch(String p)
	{
		if (p != null)
		{
			pitch = p;
		}
	}
	
	public boolean isTied()
	{
		return isTied;
	}
	
	public void setTie(boolean tied)
	{
		isTied = tied;
	}
	
	public PImage getImage()
	{
		return image;
	}
	
	public void setImage(PImage i)
	{
		if (i != null)
		{
			image = i;
		}
	}
	
	public String getStringLength()
	{
		return strLength;
	}
	
	public void setStringLength(String length)
	{
		if (length != null)
		{
			strLength = length;
		}
	}
	
	private String strLength = "";
	private int player = 0;
	private int length = 0;
	private float staffPosition = 0;
	private String pitch = "";
	private boolean isTied = false;
	private PImage image = null;
}
