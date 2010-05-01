package publicDisplay.staff;

import java.awt.image.BufferedImage;

public class DisplayNote 
{
	public DisplayNote(float staffPosition, int length, boolean tie, BufferedImage b)
	{
		this.staffPosition = staffPosition;
		numericalLength = length;
		hasTie = tie;
		image = b;
	}

	public float getStaffPosition()
	{
		return staffPosition;
	}
	
	public int getNumericalLength()
	{
		return numericalLength;
	}
	
	public BufferedImage getImage()
	{
		return image;
	}
	
	public boolean isInverted()
	{
		return (staffPosition <= 3);
	}
	
	public boolean hasTie()
	{
		return hasTie;
	}
	
	private int numericalLength = 0;
	private float staffPosition = 0;
	private boolean hasTie = false;
	private BufferedImage image = null;
}
