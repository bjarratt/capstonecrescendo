package gameManagement.windowManagement.publicDisplay.staff;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gameManagement.windowManagement.publicDisplay.ColorMap;

import keys.KeySignatures;
import keys.Lengths;
import keys.Pitches;
import keys.Players;


/**
 * This is the primary portion of the staff that is used for drawing what the player has done (i.e. showing a staff,
 * bars, chord changes, notes played, and indication of player's turn).
 * @author Zach
 *
 */
// TODO add rests
// TODO Fix drawing positions of notes and ties
public class Staff extends JPanel 
{
	/**
	 * Constructor
	 * @param player - player to start the game
	 * @param beatsPerBar - the top number of the time signature
	 * @param subdivision - the bottom number of the time signature
	 * @param keys - a list of keys for each bar.  The number of bars for the game is determined by the number of
	 * 				 keys listed
	 */
	public Staff(String player, int beatsPerBar, int subdivision, List<String> keys)
	{
		super();
		initImages();
		this.setBackground(Color.BLACK);
		setPlayer(player);
		this.beatsPerBar = beatsPerBar;
		this.subdivision = subdivision;
		if (keys != null && !keys.isEmpty() && KeySignatures.hasKeySignatures(keys))
		{
			setMeasureKeys(keys);
		}
		else
		{
			ArrayList<String> key = new ArrayList<String>();
			key.add(KeySignatures.CMajor);
			setMeasureKeys(key);
		}
	}
	
	/**
	 * Default constructor
	 */
	public Staff()
	{
		super();
		initImages();
		setPlayer(Players.PLAYER_ONE);
		beatsPerBar = 4;
		subdivision = 4;
		List<String> key = new ArrayList<String>();
		key.add(KeySignatures.CMajor);
		setMeasureKeys(key);
	}
	
	/**
	 * Used to indicate which player is taking a turn
	 * @param player - one of the <code>String</code> keys from the
	 * 				   <code>keys.KeySignatures</code> class
	 */
	public void setPlayer(String player)
	{
		this.setBackground(ColorMap.getPlayerColor(player));
	}

	/**
	 * Constructs an image of the played note along with a set of properties necessary for drawing said note.
	 * @param length - Indicates whether the note is a whole, half, quarter, or eighth note
	 * @param size - this is the numerical length of the note
	 * @param pitch - the actual note played; this is used for vertical positioning on the staff
	 * @param correct - a <code>boolean</code> flag indicating whether the note was in the correct key.
	 * @param hasTie - whether the note should be tied to the following note
	 */
	public int addNote(String length, int size, String pitch, boolean correct, boolean hasTie)
	{
		// If the pitch is an actual pitch
		if (Pitches.getStaffPosition(pitch) != -1)
		{
			// get which line/space the note should fall on
			float position = Pitches.getStaffPosition(pitch);
			
			// build the key for the correct image to display
			String imageKey = length;
			if (position <= 3)
			{
				imageKey += separator + down;
			}
			
			if (pitch.contains(Pitches.Sharp))
			{
				imageKey += separator + Pitches.Sharp;
			}
			else if (pitch.contains(Pitches.Flat))
			{
				imageKey += separator + Pitches.Flat;
			}
			
			// If the key was built correctly...
			if (noteImages.containsKey(imageKey))
			{
				// make a display note to be drawn later
				BufferedImage newNote = noteImages.get(imageKey);
				if (!correct)
				{
					// Add the image to another buffered image where we can draw the error indicator
					// currently a red X.
					newNote = addIncorrectIndicator(newNote);
				}
				
				DisplayNote note = new DisplayNote(position, size, hasTie, newNote);
				playedNotes.add(note);
				repaint();
			}
		}
		int numericalLength = Lengths.getNumericalLength(subdivision);
		return (int)(size*measurePixelLength/(beatsPerBar*numericalLength) + 3);
	}
	
	/**
	 * @return - the height of the white background for the staff
	 */
	public int getStaffRegionHeight()
	{
		return (int)(this.getSize().height * 0.7);
	}
	
	/**
	 * @return - the width of a measure in pixels
	 */
	public float getMeasurePixelLength()
	{
		return measurePixelLength;
	}

	public void setMeasureKeys(Collection<? extends String> keys)
	{
		if (keys != null && !keys.isEmpty())
		{
			measureKeys = new ArrayList<String>(keys);
			measureCount = measureKeys.size();
			repaint();
		}
	}
	
	public void setTimeSignature(int beatsPerBar, int subdivision)
	{
		this.beatsPerBar = beatsPerBar;
		this.subdivision = subdivision;
		repaint();
	}
	
	public void clearNotes()
	{
		playedNotes.removeAllElements();
	}
	
	// The meat of this class.  This function draws everything.
	@Override
	protected void paintComponent(Graphics g)
	{
		if (getParent() != null)
		{
			this.setSize(getParent().getSize());
		}
		// this calculates the width of a measure in pixels
		doMeasureCalcs();
		
		// setup graphics object
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		Dimension size = this.getSize();
		
		// calculate the width, height, and y-coordinate of the staff drawing region
		int width = (int)(size.width);
		int height = getStaffRegionHeight();
		int yCoord = this.getY() + Math.abs(size.height - height)/2;

		// how much to scale note images and appropriate shift for placing on the staff
		float imageScale = height*0.003f;
		
		
		// draw staff background
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.WHITE);
		int regionWidth = ((int)(measurePixelLength * measureCount) <= this.getWidth()) ? (int)(measureCount*measurePixelLength): (int)(measureCount*measurePixelLength)*2;
		g2d.fillRect(this.getX() + Math.abs(size.width - width)/2, 
				   	 this.getY() + Math.abs(size.height - height)/2, 
				   	 regionWidth, 
				   	 height);
		
		// draw the staff lines
		BasicStroke stroke = new BasicStroke(0.005f*size.height);
		g2d.setColor(Color.BLACK);
		g2d.setStroke(stroke);
		for (int i = 0; i < 5; ++i)
		{
			int y = (int)calculatStaffLine(yCoord, (float)height, (float)i);
			g2d.drawLine(0, y, width, y);
		}
		
		// draw bar lines and measure keys
		g2d.setFont(new Font("Times New Roman", Font.PLAIN, (int)(0.1*height)));
		for (int i = 0; i < measureCount; ++i)
		{
			g2d.drawString(this.measureKeys.get(i), i*measurePixelLength, Math.abs(size.height - height)/2 + (int)(0.1*height));
			g2d.drawLine(((i + 1)*(int)measurePixelLength), 
						 (int)calculatStaffLine(yCoord, (float)height, 0), 
						 ((i + 1)*(int)measurePixelLength), 
						 (int)calculatStaffLine(yCoord, (float)height, 4));
		}
		
		// draw notes
		nextX = 3;
		for (final DisplayNote note : playedNotes)
		{
			int staffLine = (int)calculatStaffLine(yCoord, (float)height, note.getStaffPosition());
			float verticalImageShift = note.isInverted() ? height*0.35f : height*0.585f;
			BufferedImage image = note.getImage();
			int y = (int)(staffLine - verticalImageShift);
			int imageWidth = (int)(image.getWidth()*imageScale);
			int imageHeight = (int)(image.getHeight()*imageScale);
			
			g2d.drawImage(image, (int)nextX, y, imageWidth, imageHeight, null);
			if (note.hasTie())
			{
				if (note.isInverted())
				{
					g2d.drawArc((int)nextX + imageWidth, (int)(staffLine - verticalImageShift*0.75f), (int)(imageWidth*note.getNumericalLength()*1.25), 60, 30, 125);
				}
				else
				{
					g2d.drawArc((int)nextX + 2*((int)(imageWidth*0.95))/5, (int)(staffLine - verticalImageShift*0.4f), (int)(imageWidth*note.getNumericalLength()*1.1), 60, -30, -120);
				}
			}			
			int numericalLength = Lengths.getNumericalLength(subdivision);
			nextX += note.getNumericalLength()*measurePixelLength/(beatsPerBar*numericalLength) + 3;
		}
	}
	
	private void doMeasureCalcs()
	{
		float scale = getStaffRegionHeight() * 0.003f;
		float maxNoteWidth = noteImages.get(Lengths.EIGHTH + separator + Pitches.Sharp).getWidth() * scale;
		float maxBeatWidth = 0;
		int totalWidth = 0;
		measurePixelLength = 1;
		
		for (int i = 0; i < Lengths.getNumericalLength(subdivision); ++i)
		{
			maxBeatWidth += maxNoteWidth;
		}
		
		for (int i = 0; i < beatsPerBar; ++i)
		{
			measurePixelLength += maxBeatWidth;
		}
		
		for (int i = 0; i < measureCount; ++i)
		{
			totalWidth += measurePixelLength;
		}
		
		setSize(totalWidth, this.getHeight());
		setPreferredSize(new Dimension(totalWidth, this.getHeight()));
	}

	private BufferedImage addIncorrectIndicator(BufferedImage b)
	{
		BufferedImage wrapper = null;
		
		if (b != null)
		{
			wrapper = new BufferedImage(b.getWidth(), b.getHeight(), BufferedImage.TYPE_INT_ARGB);
			
			Graphics2D g2d = (Graphics2D)wrapper.getGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setStroke(new BasicStroke(6));
			g2d.setColor(Color.RED);
			
			g2d.drawImage(b, null, 0, 0);
			g2d.drawLine(0, 0, wrapper.getWidth(), wrapper.getHeight());
			g2d.drawLine(wrapper.getWidth(), 0, 0, wrapper.getHeight());
		}
			
		return wrapper;
	}
	
	private void initImages()
	{
		try 
		{
			// load regular notes
			noteImages.put(Lengths.EIGHTH, ImageIO.read(new File(Lengths.EIGHTH + fileType)));
			noteImages.put(Lengths.QUARTER, ImageIO.read(new File(Lengths.QUARTER + fileType)));
			noteImages.put(Lengths.HALF, ImageIO.read(new File(Lengths.HALF + fileType)));
			noteImages.put(Lengths.WHOLE, ImageIO.read(new File(Lengths.WHOLE + fileType)));
			
			// load inverted regular notes
			noteImages.put(Lengths.EIGHTH + separator + down, ImageIO.read(new File(Lengths.EIGHTH + separator + down + fileType)));
			noteImages.put(Lengths.QUARTER + separator + down, ImageIO.read(new File(Lengths.QUARTER + separator + down + fileType)));
			noteImages.put(Lengths.HALF + separator + down, ImageIO.read(new File(Lengths.HALF + separator + down + fileType)));
			noteImages.put(Lengths.WHOLE + separator + down, ImageIO.read(new File(Lengths.WHOLE + separator + down + fileType)));
			
			// load sharp notes
			noteImages.put(Lengths.EIGHTH + separator + Pitches.Sharp, ImageIO.read(new File(Lengths.EIGHTH + separator + Pitches.Sharp.toLowerCase() + fileType)));
			noteImages.put(Lengths.QUARTER + separator + Pitches.Sharp, ImageIO.read(new File(Lengths.QUARTER + separator + Pitches.Sharp.toLowerCase() + fileType)));
			noteImages.put(Lengths.HALF + separator + Pitches.Sharp, ImageIO.read(new File(Lengths.HALF + separator + Pitches.Sharp.toLowerCase() + fileType)));
			noteImages.put(Lengths.WHOLE + separator + Pitches.Sharp, ImageIO.read(new File(Lengths.WHOLE + separator + Pitches.Sharp.toLowerCase() + fileType)));
			
			// load inverted sharp notes
			noteImages.put(Lengths.EIGHTH + separator + down + separator + Pitches.Sharp, ImageIO.read(new File(Lengths.EIGHTH + separator + down + separator + Pitches.Sharp.toLowerCase() + fileType)));
			noteImages.put(Lengths.QUARTER + separator + down + separator + Pitches.Sharp, ImageIO.read(new File(Lengths.QUARTER + separator + down + separator + Pitches.Sharp.toLowerCase() + fileType)));
			noteImages.put(Lengths.HALF + separator + down + separator + Pitches.Sharp, ImageIO.read(new File(Lengths.HALF + separator + down + separator + Pitches.Sharp.toLowerCase() + fileType)));
			noteImages.put(Lengths.WHOLE + separator + down + separator + Pitches.Sharp, ImageIO.read(new File(Lengths.WHOLE + separator + down + separator + Pitches.Sharp.toLowerCase() + fileType)));

			noteImages.put(Lengths.EIGHTH + separator + Pitches.Flat, ImageIO.read(new File(Lengths.EIGHTH + separator + Pitches.Flat.toLowerCase() + fileType)));
			noteImages.put(Lengths.QUARTER + separator + Pitches.Flat, ImageIO.read(new File(Lengths.QUARTER + separator + Pitches.Flat.toLowerCase() + fileType)));
			noteImages.put(Lengths.HALF + separator + Pitches.Flat, ImageIO.read(new File(Lengths.HALF + separator + Pitches.Flat.toLowerCase() + fileType)));
			noteImages.put(Lengths.WHOLE + separator + Pitches.Flat, ImageIO.read(new File(Lengths.WHOLE + separator + Pitches.Flat.toLowerCase() + fileType)));
			
			noteImages.put(Lengths.EIGHTH + separator + down + separator + Pitches.Flat, ImageIO.read(new File(Lengths.EIGHTH + separator + down + separator + Pitches.Flat.toLowerCase() + fileType)));
			noteImages.put(Lengths.QUARTER + separator + down + separator + Pitches.Flat, ImageIO.read(new File(Lengths.QUARTER + separator + down + separator  + Pitches.Flat.toLowerCase() + fileType)));
			noteImages.put(Lengths.HALF + separator + down + separator + Pitches.Flat, ImageIO.read(new File(Lengths.HALF + separator + down + separator + Pitches.Flat.toLowerCase() + fileType)));
			noteImages.put(Lengths.WHOLE + separator + down + separator + Pitches.Flat, ImageIO.read(new File(Lengths.WHOLE + separator + down + separator + Pitches.Flat.toLowerCase() + fileType)));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	// Calculate where exactly a staff line is for a given starting coordinate and region height
	private float calculatStaffLine(int startY, float regionHeight, float staffLine)
	{
		return startY*1.35f + (staffLine+1f)*(regionHeight*0.7f)/5f;
	}
	
	
	private int measureCount = 1;
	private int beatsPerBar = 4;
	private int subdivision = 4;
	
	private float measurePixelLength = 1f;
	private float nextX = 0;
	
	// For keeping track of played notes as well as images
	private Vector<DisplayNote> playedNotes = new Vector<DisplayNote>();
	private ArrayList<String> measureKeys = null;
	private HashMap<String, BufferedImage> noteImages = new HashMap<String, BufferedImage>();

	// Constants for setting up storing note images
	private final String separator = "_";
	private final String fileType = ".png";
	private final String down = "down";
	
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args)
	{
		Staff staff = new Staff();
		staff.setPlayer("player1");
		ArrayList<String> keys = new ArrayList<String>();
		for (int i = 0; i < 16; ++i)
		{
			keys.add(KeySignatures.CMajor);
		}
		staff.setMeasureKeys(keys);
		staff.setTimeSignature(3, 4);
		
		JScrollPane scrollPane = new JScrollPane(staff);
		
		JFrame frame = new JFrame();
		frame.setLayout(new GridLayout(1,1));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(1200, 400));
		
		frame.getContentPane().add(scrollPane);
		
		frame.setVisible(true);

//		staff.addNote(Lengths.WHOLE, 8, Pitches.A5, false, true);
		staff.addNote(Lengths.QUARTER, 2, Pitches.A5, true, true);
		staff.addNote(Lengths.QUARTER, 2, Pitches.A5, true, false);
		staff.addNote(Lengths.EIGHTH, 1, Pitches.A5, true, false);
//
		staff.addNote(Lengths.EIGHTH, 1, Pitches.FFlat6, true, false);
//		staff.addNote(Lengths.EIGHTH, 1, Pitches.FFlat6, true, false);
//		staff.addNote(Lengths.HALF, 1, Pitches.FFlat6, true, false);
//		staff.addNote(Lengths.WHOLE, 1, Pitches.BFlat5, true, false);
//		
//		staff.addNote(Lengths.EIGHTH, 1, Pitches.B5, true, true);
//		staff.addNote(Lengths.QUARTER, 1, Pitches.B5, true, false);
//		staff.addNote(Lengths.HALF, 1, Pitches.B5);
//		staff.addNote(Lengths.WHOLE, 1, Pitches.B5);
//		
//		staff.addNote(Lengths.EIGHTH, 1, Pitches.BSharp5);
//		staff.addNote(Lengths.QUARTER, 1, Pitches.BSharp5);
//		staff.addNote(Lengths.HALF, 1, Pitches.BSharp5);
//		staff.addNote(Lengths.WHOLE, 1, Pitches.BSharp5);
	}
}
