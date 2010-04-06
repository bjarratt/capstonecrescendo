package displayManager.messageTranslationSystem;

import javax.swing.*;
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
    int index = 0;
    JTextArea msgText = new JTextArea();

	public DisplayGUI()
	{
		super(true);
        this.setPreferredSize(new Dimension(800,600));
        this.add(msgText);
        this.revalidate();
    }
	
	public void getNote(Note n)
	{
		playerNotes.add(n);
		//update the screen with the new note
		//msgText.append("new msg" + n.toString());
		//System.out.println("new msg" + n.toString());
		//super.paintComponent(super.getGraphics());
		//super.validate();
		this.repaint();

		n.playNote();
		//this.revalidate();
	}

    @Override
    public void paintComponent(Graphics graphics)
	{
    	super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		
		//draw the staff
        g.drawImage(Images.getStaffImage(img), 0, 50, this);
        g.drawImage(Images.getStaffImage(img), 200, 50, this);
        g.drawImage(Images.getStaffImage(img), 400, 50, this);
        g.drawImage(Images.getStaffImage(img), 600, 50, this);
        
        //draw the player icons
        g.setColor(Color.RED);
        g.fillRoundRect(50, 450, 100, 100, 20, 20);
        g.setColor(Color.BLUE);
        g.fillRoundRect(250, 450, 100, 100, 20, 20);
        g.setColor(Color.GREEN);
        g.fillRoundRect(450, 450, 100, 100, 20, 20);
        g.setColor(Color.ORANGE);
        g.fillRoundRect(650, 450, 100, 100, 20, 20);
        
        if(playerNotes.size() > 0)
        {
            if(playerNotes.size() > 0)
            	g.drawImage(Images.getNoteImage(img, playerNotes.get(playerNotes.size() - 1)), 735, Images.getNotePosition(playerNotes.get(playerNotes.size() - 1)), this);
            if(playerNotes.size() > 1)
            	g.drawImage(Images.getNoteImage(img, playerNotes.get(playerNotes.size() - 2)), 670, Images.getNotePosition(playerNotes.get(playerNotes.size() - 2)), this);
            if(playerNotes.size() > 2)
            	g.drawImage(Images.getNoteImage(img, playerNotes.get(playerNotes.size() - 3)), 605, Images.getNotePosition(playerNotes.get(playerNotes.size() - 3)), this);
            if(playerNotes.size() > 3)
            	g.drawImage(Images.getNoteImage(img, playerNotes.get(playerNotes.size() - 4)), 540, Images.getNotePosition(playerNotes.get(playerNotes.size() - 4)), this);
            if(playerNotes.size() > 4)
            	g.drawImage(Images.getNoteImage(img, playerNotes.get(playerNotes.size() - 5)), 475, Images.getNotePosition(playerNotes.get(playerNotes.size() - 5)), this);
            if(playerNotes.size() > 5)
            	g.drawImage(Images.getNoteImage(img, playerNotes.get(playerNotes.size() - 6)), 410, Images.getNotePosition(playerNotes.get(playerNotes.size() - 6)), this);
            if(playerNotes.size() > 6)
            	g.drawImage(Images.getNoteImage(img, playerNotes.get(playerNotes.size() - 7)), 345, Images.getNotePosition(playerNotes.get(playerNotes.size() - 7)), this);
            if(playerNotes.size() > 7)
            	g.drawImage(Images.getNoteImage(img, playerNotes.get(playerNotes.size() - 8)), 280, Images.getNotePosition(playerNotes.get(playerNotes.size() - 8)), this);
            if(playerNotes.size() > 8)
            	g.drawImage(Images.getNoteImage(img, playerNotes.get(playerNotes.size() - 9)), 215, Images.getNotePosition(playerNotes.get(playerNotes.size() - 9)), this);
            if(playerNotes.size() > 9)
            	g.drawImage(Images.getNoteImage(img, playerNotes.get(playerNotes.size() - 10)), 150, Images.getNotePosition(playerNotes.get(playerNotes.size() - 10)), this);
            if(playerNotes.size() > 10)
            	g.drawImage(Images.getNoteImage(img, playerNotes.get(playerNotes.size() - 11)), 85, Images.getNotePosition(playerNotes.get(playerNotes.size() - 11)), this);
            if(playerNotes.size() > 11)
            	g.drawImage(Images.getNoteImage(img, playerNotes.get(playerNotes.size() - 12)), 20, Images.getNotePosition(playerNotes.get(playerNotes.size() - 12)), this);
        }
    }
	
	public void receiveMessage(String message)
	{

	}

}