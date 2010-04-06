package displayManager;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 *	The Images class is a placeholder for image reading functions
 *
 *	@author Brandon Jarratt
 */
public class Images
{
	public static Image getNoteImage(Image image)
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
}