package gameManagement.windowManagement.windows;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import keys.Pitches;

import processing.core.PApplet;
import processing.core.PImage;

public class GameWindow extends PApplet 
{
	/**
	 * Sets the number of players for the game
	 * @param count
	 */
	public void setPlayerCount(int count)
	{
		noLoop();
		
		playerCount = count;
		if (playerCount <= 0 || playerCount >= 5)
		{
			playerCount = 1;
		}
		
		loop();
	}
	
	@Override
	public void setup()
	{
		this.setSize(new Dimension(800, 640));
		
		smooth();
		background = loadImage("blackground.jpg");
		clef = loadImage("Treble_clef.png");
		
		initPlayerBackgrounds();
		initStaffPositions();
	}
	
	@Override
	public void draw()
	{
		image(background, 0, 0, this.width, this.height);
		for (int i = 0; i < playerCount; i++)
		{
			drawPlayerStaff(i);
			drawPlayerNotes(i);
		}
	}
	
	private void drawPlayerNotes(int playerIndex)
	{
		
	}

	// To be called in setup()
	private void initPlayerBackgrounds()
	{
		String filetype = ".png";
		for (int i = 0; i < 4; i++)
		{
			String image = "capsule";
			String capsule = image + (i + 1) + filetype;
			capsules.add(loadImage(capsule));
		}
	}
	
	// To be called in setup()
	private void initStaffPositions()
	{
		List<String> allNotes = Pitches.getAllNotes();
		
		float place = 0.5f;
		for (int i = 0; i < allNotes.size(); ++i)
		{
			if (i % 2 == 0)
			{
				place += 0.5f;
			}
			staffPositions.put(allNotes.get(i), place);
		}
//		staffPositions.put(allNotes.get(0), 1f);
//		staffPositions.put(allNotes.get(1), 1f);
//		staffPositions.put(allNotes.get(2), 1.5f);
//		staffPositions.put(allNotes.get(3), 1.5f);
//		staffPositions.put(allNotes.get(4), 2f);
//		staffPositions.put(allNotes.get(5), 2f);
//		staffPositions.put(allNotes.get(6), 2.5f);
//		staffPositions.put(allNotes.get(7), 2.5f);
//		staffPositions.put(allNotes.get(8), 3f);
//		staffPositions.put(allNotes.get(9), 3f);
//		staffPositions.put(allNotes.get(10), 3.5f);
//		staffPositions.put(allNotes.get(11), 3.5f);
//		staffPositions.put(allNotes.get(12), 4f);
//		staffPositions.put(allNotes.get(13), 4f);
//		staffPositions.put(allNotes.get(14), 4.5f);
//		staffPositions.put(allNotes.get(15), 4.5f);
//		staffPositions.put(allNotes.get(16), 5f);
		System.out.println(place);
	}

	// Draws the staff for the given player
	private void drawPlayerStaff(int playerIndex)
	{
		// calculate height and width of each player's staff
		float height = (BASE_HEIGHT_SCALE - playerCount * DECREMENT) * this.height;
		float width = BASE_WIDTH_SCALE * this.width;
		
		// use height and width to calculate where to place staffs
		float x = Math.abs(this.width - width)/2;
		float regionHeight = this.height / playerCount;
		float y = (regionHeight - height)/2 + playerIndex * regionHeight;
		
		// draw backgrounds for player's staffs
		image(capsules.get(playerIndex), x, y, width, height);
		
		// draw semi-transparent background for staffs
	    roundRect(x + width/10, y + height/11, width - width/5, height - height/5);
	    
	    // draw staff lines
	    drawStaffLines(x, y, width, height);
	    drawClef(x, y, width, height);
	}
	
	private void roundRect(float x, float y, float w, float h)
	{
	    fill(255,255,255,200);
	    stroke(0);

		float corner = w/30.0f;
		float midDisp = w/20.0f;
  
		beginShape();  
		curveVertex(x+corner,y);
		curveVertex(x+w-corner,y);
		curveVertex(x+w+midDisp,y+h/2.0f);
		curveVertex(x+w-corner,y+h);
		curveVertex(x+corner,y+h);
		curveVertex(x-midDisp,y+h/2.0f);
  
		curveVertex(x+corner,y);
		curveVertex(x+w-corner,y);
		curveVertex(x+w+midDisp,y+h/2.0f);
		endShape();
	}

	private void drawStaffLines(float x, float y, float width, float height)
	{
	    noStroke();
	    fill(15);
	    for (int i = 0; i < 5; i++)
	    {
	    	rect(x + width*0.1f, y + height*(8 + i*5)/40, width*0.8f, height/120);
	    }
	}
	
	private void drawClef(float x, float y, float width, float height)
	{
		image(clef, x + width*0.1f, y + height*0.07f, height*0.40f, height*0.85f);
	}

	// To make Eclipse happy
	private static final long serialVersionUID = 1L;
	
	// These are for scaling and placement of user staffs.  No touchy!
	private static final float DECREMENT = 0.055f;
	private static final float BASE_HEIGHT_SCALE = 0.48f;
	private static final float BASE_WIDTH_SCALE = 0.98f;
	
	// other display related variables
	private int playerCount = 2;
	private PImage background = null;
	private PImage clef = null;
	private PImage keySignature = null;
	private ArrayList<PImage> capsules = new ArrayList<PImage>();
	private HashMap<String, Float> staffPositions = new HashMap<String, Float>();
}
