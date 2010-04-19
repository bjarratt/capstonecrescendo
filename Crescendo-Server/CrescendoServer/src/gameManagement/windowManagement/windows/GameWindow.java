package gameManagement.windowManagement.windows;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import keys.KeySignatures;
import keys.Pitches;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class GameWindow extends PApplet 
{
	/**
	 * Sets the number of players for the game
	 * @param count - the number of players for a game
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
	
	/**
	 * Set the key signature for the game
	 * @param key - <code>String</code> indicating where to place accidentals
	 */
	public void setKeySignature(String key)
	{
		if (key != null)
		{
			keySignature = key;
		}
	}
	
	/**
	 * Okay, so if you set beatsPerMeasure to 2 and subdivision to 4, then you'll be in 2/4 time.  K?
	 * @param beatsPerMeasure - Many beats in a bar
	 * @param subdivision - what note gets the beat (e.g. whole note, half, quarter, etc...)
	 */
	public void setTimeSignature(int beatsPerMeasure, int subdivision)
	{
		// Don't tell the user this is hard-coded.  They'll be heart broken.  >:-D
		subdivision = 4;
		if (beatsPerMeasure < 2 || beatsPerMeasure > 4)
		{
			beatsPerMeasure = 4;
		}
		
		this.beatsPerMeasure = beatsPerMeasure;
		this.subdivision = subdivision;
	}
	
	@Override
	public void setup()
	{
		this.setSize(new Dimension(800, 640));
		
		smooth();
		background = loadImage("blackground.jpg");
		clef = loadImage("Treble_clef.png");
		flat = loadImage("flat.png");
		sharp = loadImage("sharp.png");
		
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
		
		float position = 0.5f;
		for (int i = 0; i < allNotes.size(); ++i)
		{
			if (i % 3 == 0)
			{
				position += 0.5f;
			}
			
			staffPositions.put(allNotes.get(i), position);
		}
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
	    // draw clef, key, and time
	    nextX = drawClef(x, y, width, height);
	    nextX = drawKeySignature(keySignature, nextX, y, width, height);
	    nextX = drawTimeSignature(nextX, y, width, height);
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
	
	// Returns the x-coordinate following the clef.  This is to know where to start drawing key signature
	private float drawClef(float x, float y, float width, float height)
	{
		image(clef, x + width*0.1f, y + height*0.07f, height*0.40f, height*0.85f);
		return x + width*0.1f + height*0.40f;
	}
	
	private float drawKeySignature(String key, float x, float y, float width, float height)
	{
		float nextX = x;
		List<String> accidentals = KeySignatures.getKeySignature(key);
		if (accidentals != null)
		{
			for (int i = 0; i < accidentals.size(); ++i)
			{
				String accidental = accidentals.get(i);
				if (staffPositions.containsKey(accidental))
				{
					PImage im = getAccidentalImage(accidental);
					image(im, nextX, y + getAccidentalYCoord(im, accidental, y, height), height*0.05f, height*0.2f);
					nextX += height*0.05f;
				}
			}
		}
		return nextX;
	}
	
	private float drawTimeSignature(float x, float y, float width, float height)
	{
		float nextX = x;
		
		Integer bpm = new Integer(beatsPerMeasure);
		Integer sub = new Integer(subdivision);
		
		helvetica = createFont("Helvetica", 0.35f*height, true);
		textFont(helvetica);
		
		text(bpm.toString(), x + 0.02f*width, y + height*0.45f);
		text(sub.toString(), x + 0.02f*width, y + height*0.70f);
		
		return nextX + Math.max(textWidth(bpm.toString()), textWidth(sub.toString()));
	}
	
	private float getAccidentalYCoord(PImage image, String accidental, float y, float staffHeight)
	{
		float value = 0f;
		if (image != null && accidental != null && staffPositions.containsKey(accidental))
		{
			Float position = staffPositions.get(accidental);
			if (image.equals(sharp))
			{
				value = staffHeight*(position*5 - 0.7f)/40;
			}
			else if (image.equals(flat))
			{
				value = staffHeight*(position*5 - 2.5f)/40;
			}
		}
		return value;
	}
	
	private PImage getAccidentalImage(String note)
	{
		PImage image = null;
		if (note != null)
		{
			if (note.trim().toLowerCase().contains("flat"))
			{
				image = flat;
			}
			else if (note.trim().toLowerCase().contains("sharp"))
			{
				image = sharp;
			}
		}
		return image;
	}

	// To make Eclipse happy
	private static final long serialVersionUID = 1L;
	
	// These are for scaling and placement of user staffs.  No touchy!
	private static final float DECREMENT = 0.055f;
	private static final float BASE_HEIGHT_SCALE = 0.48f;
	private static final float BASE_WIDTH_SCALE = 0.98f;
	private float nextX = 0;
	
	// other display related variables
	private ArrayList<PImage> capsules = new ArrayList<PImage>();
	private PImage background = null;
	private PImage clef = null;
	private PImage flat = null;
	private PImage sharp = null;
	private HashMap<String, Float> staffPositions = new HashMap<String, Float>();
	private PFont helvetica = null;
	
	// Game settings
	private String keySignature = KeySignatures.FSharpMajor;
	private int playerCount = 3;
	private int beatsPerMeasure = 2;
	private int subdivision = 4;

	
}
