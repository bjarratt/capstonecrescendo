package gameManagement.messageTranslationSystem;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DisplayGUI extends JPanel
{
	
	public DisplayGUI()
	{
		super(true);
        this.setPreferredSize(new Dimension(800,600));
		this.setVisible(true);
	}

	 public void paintComponent(Graphics graphics)
	 {
		 super.paintComponent(graphics);
	 }
	
	public void getNotes(ArrayList<Note> notes)
	{
		
	}
}
