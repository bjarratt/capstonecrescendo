package gameManagement.windowManagement.windows;

import gameManagement.messageTranslationSystem.Note;
import gameManagement.windowManagement.help.DisplayNote;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;

import keys.KeySignatures;
import keys.Lengths;
import keys.Pitches;
import keys.Players;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class GameWindow extends PApplet 
{
	public void nukeAllNotes()
	{
		lock.lock();
		try
		{
			for (Vector<DisplayNote> notes : playerNotes)
			{
				notes = new Vector<DisplayNote>();
			}
		}
		finally
		{
			lock.unlock();
		}
	}
	
	public void addNote(Note n)
	{
		lock.lock();
		try
		{
			while (!setupComplete) {}
			noLoop();
			if (n != null)
			{
				DisplayNote display = new DisplayNote(n);
				if (0 <= display.getPlayer() && display.getPlayer() < 4)
				{
					display.setStaffPosition(staffPositions.get(display.getPitch()));
					String fileName = display.getStringLength();
					
					if (display.getStaffPosition() < staffPositions.get(Pitches.BSharp5))
					{
						fileName += "_" + "down";
					}
					
					if (display.getPitch().contains(Pitches.Sharp))
					{
						fileName += "_" + Pitches.Sharp.toLowerCase();
					}
					else if (display.getPitch().contains(Pitches.Flat))
					{
						// We do not have images for this yet
	//					fileName += "_" + Pitches.Flat.toLowerCase();
					}
					
					display.setImage(loadImage(fileName + imageFileType));
					
					playerNotes.get(display.getPlayer()).add(display);
				}
			}
		}
		finally
		{
			loop();
			lock.unlock();
		}
	}
	
	/**
	 * Sets the number of players for the game
	 * @param count - the number of players for a game
	 */
	public void setPlayerCount(int count)
	{
		playerCount = count;
		if (playerCount <= 0 || playerCount >= 5)
		{
			playerCount = 1;
		}
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
		synchronized(Thread.currentThread())
		{
			setupComplete = false;
			this.setSize(new Dimension(800, 640));
			nextX.add(new Float(0));
			nextX.add(new Float(0));
			nextX.add(new Float(0));
			nextX.add(new Float(0));
			
			playerNotes.add(new Vector<DisplayNote>());
			playerNotes.add(new Vector<DisplayNote>());
			playerNotes.add(new Vector<DisplayNote>());
			playerNotes.add(new Vector<DisplayNote>());
			
			smooth();
			background = loadImage("blackground.jpg");
			clef = loadImage("Treble_clef.png");
			flat = loadImage("flat.png");
			sharp = loadImage("sharp.png");
			
			initPlayerBackgrounds();
			initStaffPositions();
			
			setupComplete = true;
			this.redraw();
		}
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
		if (0 <= playerIndex && playerIndex < playerNotes.size())
		{
			Dimension region = calcStaffRegion(this.width, this.height);
			Point startPoint = calcStaffPoint(playerIndex, region);
			Vector<DisplayNote> notes = playerNotes.get(playerIndex);
			float increment = 0;
			for (DisplayNote note : notes)
			{
				Dimension noteDim = getNoteDimension(note.getPitch(), note.getStringLength(), region);
				float displacement = getNoteDisplacement(note.getPitch(), note.getStringLength(), note.getStaffPosition());
				image(note.getImage(), 
					  nextX.get(playerIndex) + 0.025f * region.width + increment,
					  (float)startPoint.getY() + region.height*(displacement + staffPositions.get(note.getPitch())*5)/40,
					  (float)noteDim.getWidth(),
					  (float)noteDim.getHeight());
				
				increment += noteDim.width + 0.019f * region.width;
			}
		}
	}
	
	private float getNoteDisplacement(String pitch, String length, float staffPosition)
	{
		float value = 0;
		if (length != null && pitch != null)
		{
			if (length.equals(Lengths.WHOLE) || staffPosition < staffPositions.get(Pitches.BSharp5))
			{
				value = 0.9f;
			}
			else if (length.equals(Lengths.HALF) || 
					 length.equals(Lengths.QUARTER) || 
					 length.equals(Lengths.EIGHTH))
			{
				value = -10.5f;
			}
			
			if (pitch.contains(Pitches.Sharp))
			{
				value -= 2.5f;
			}
		}
		return value;
	}
	
	private Dimension getNoteDimension(String pitch, String length, Dimension staffRegion)
	{
		Dimension noteDim = new Dimension();
		
		if (pitch != null && length != null && staffRegion != null)
		{
			if (length.equals(Lengths.WHOLE))
			{
				noteDim.setSize(staffRegion.height*0.16, staffRegion.height*0.12);
				if (pitch.contains(Pitches.Sharp) || pitch.contains(Pitches.Flat))
				{
					noteDim.setSize(noteDim.getWidth() + 10.7f, noteDim.getHeight() + 8f);
				}
			}
			else if (length.equals(Lengths.HALF) || length.equals(Lengths.QUARTER))
			{
				noteDim.setSize(staffRegion.height*0.14, staffRegion.height*0.4);
				if (pitch.contains(Pitches.Sharp) || pitch.contains(Pitches.Flat))
				{
					noteDim.setSize(noteDim.getWidth() + 7f, noteDim.getHeight() + 10f);
				}
			}
			else if (length.equals(Lengths.EIGHTH))
			{
				noteDim.setSize(staffRegion.height*0.16, staffRegion.height*0.4);
				if (pitch.contains(Pitches.Sharp) || pitch.contains(Pitches.Flat))
				{
					noteDim.setSize(noteDim.getWidth() + 10.7f, noteDim.getHeight() + 8f);
				}
			}
		}
		
		return noteDim;
	}

	// To be called in setup()
	private void initPlayerBackgrounds()
	{
		for (int i = 0; i < 4; i++)
		{
			String image = "capsule";
			String capsule = image + (i + 1) + imageFileType;
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
	
	private Dimension calcStaffRegion(float parentWidth, float parentHeight)
	{
		Dimension region = new Dimension();
		if (0 < parentWidth && 0 < parentHeight)
		{
			float height = (BASE_HEIGHT_SCALE - playerCount * DECREMENT) * this.height;
			float width = BASE_WIDTH_SCALE * this.width;
			
			region.setSize(width, height);
		}
		
		return region;
	}
	
	private Point calcStaffPoint(int playerIndex, Dimension staffRegion)
	{
		Point start = new Point();
		
		if (staffRegion != null && (0 <= playerIndex && playerIndex < 4))
		{
			float x = Math.abs(this.width - staffRegion.width)/2;
			float regionHeight = this.height / playerCount;
			float y = (regionHeight - staffRegion.height)/2 + playerIndex * regionHeight;
			
			start.setLocation(x, y);
		}
		
		return start;
	}

	// Draws the staff for the given player
	private void drawPlayerStaff(int playerIndex)
	{
		// calculate height and width of each player's staff
		Dimension staffRegion = calcStaffRegion(this.width, this.height);
		
		// use height and width to calculate where to place staffs
		Point startPoint = calcStaffPoint(playerIndex, staffRegion);
		
		// draw backgrounds for player's staffs
		image(capsules.get(playerIndex), (float)startPoint.getX(), (float)startPoint.getY(), staffRegion.width, staffRegion.height);
		
		// draw semi-transparent background for staffs
	    roundRect((float)startPoint.getX() + (float)staffRegion.getWidth()/10, (float)startPoint.getY() + staffRegion.height/11, staffRegion.width - staffRegion.width/5, staffRegion.height - staffRegion.height/5);
	    
	    // draw staff lines
	    drawStaffLines((float)startPoint.getX(), (float)startPoint.getY(), staffRegion.width, staffRegion.height);
	    // draw clef, key, and time
	    nextX.set(playerIndex, drawClef((float)startPoint.getX(), (float)startPoint.getY(), staffRegion.width, staffRegion.height));
	    nextX.set(playerIndex, drawKeySignature(keySignature, nextX.get(playerIndex), (float)startPoint.getY(), (float)staffRegion.getWidth(), (float)staffRegion.getHeight()));
	    nextX.set(playerIndex, drawTimeSignature(nextX.get(playerIndex), (float)startPoint.getY(), (float)staffRegion.getWidth(), (float)staffRegion.getHeight()));
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
		
		text(bpm.toString(), x + 0.003f*width, y + height*0.45f);
		text(sub.toString(), x + 0.003f*width, y + height*0.70f);
		
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
	private static final float DECREMENT = 0.03f;
	private static final float BASE_HEIGHT_SCALE = 0.3f;
	private static final float BASE_WIDTH_SCALE = 0.98f;
	private ArrayList<Float> nextX = new ArrayList<Float>();
	
	// other display related variables
	private ArrayList<PImage> capsules = new ArrayList<PImage>();
	private PImage background = null;
	private PImage clef = null;
	private PImage flat = null;
	private PImage sharp = null;
	private HashMap<String, Float> staffPositions = new HashMap<String, Float>();
	private PFont helvetica = null;
	private String imageFileType = ".png";
	
	// Game settings
	private String keySignature = KeySignatures.BMajor;
	private int playerCount = 2;
	private int beatsPerMeasure = 4;
	private int subdivision = 4;
	
	private ArrayList<Vector<DisplayNote>> playerNotes = new ArrayList<Vector<DisplayNote>>();
	boolean setupComplete = false;
	
	private Lock lock = new ReentrantLock();

	public static void main(String[] args) 
	{
		JFrame mainFrame = new JFrame("Test");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new GridLayout(1,1));
		mainFrame.setMinimumSize(new Dimension(800, 600));
		
		GameWindow window = new GameWindow();
		window.setPlayerCount(2);
		window.setKeySignature(KeySignatures.AMajor);
		window.setTimeSignature(2, 4);
		
		mainFrame.getContentPane().add(window);
		window.init();
		window.start();
		mainFrame.setVisible(true);
		
		try 
		{
			Thread.sleep(1000);
			window.addNote(new Note(Pitches.C6, Lengths.EIGHTH, Players.PLAYER_ONE));
			window.addNote(new Note(Pitches.CSharp6, Lengths.EIGHTH, Players.PLAYER_ONE));
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
}
