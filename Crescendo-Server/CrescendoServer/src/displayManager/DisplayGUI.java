package displayManager;

import javax.swing.*;

import displayManager.messageTranslationSystem.Note;

import java.util.*;
import java.awt.*;


/**
 *	The DisplayGUI class handles all of the graphical aspects of the display
 *
 *	@author Brandon Jarratt
 */
public class DisplayGUI extends JPanel
{
	ArrayList<Note> playerNotes = new ArrayList<Note>();
    Image img = null;
    int offset = 0;

	public DisplayGUI()
	{
        this.setPreferredSize(new Dimension(800,600));
        this.invalidate();
    }
	
	public void getNote(Note n)
	{
		playerNotes.add(n);
		//update the screen with the new note
	}

    @Override
    public void paintComponent(Graphics graphics)
	{
		Graphics2D g = (Graphics2D) graphics;
        g.drawImage(Images.getNoteImage(img), 0, 50, this);
        g.drawImage(Images.getNoteImage(img), 200, 50, this);
        g.drawImage(Images.getNoteImage(img), 400, 50, this);
        g.drawImage(Images.getNoteImage(img), 600, 50, this);

        g.drawRoundRect(50, 450, 100, 100, 20, 20);
        g.drawRoundRect(250, 450, 100, 100, 20, 20);
        g.drawRoundRect(450, 450, 100, 100, 20, 20);
        g.drawRoundRect(650, 450, 100, 100, 20, 20);
    }
    
	public void reDraw()
	{
		for(int i=0; i<playerNotes.size(); i++)
        {
            //draw playerNotes[i] on the screen
            //3 measures on the screen at any one time
        }
	}
}