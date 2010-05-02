package gameManagement.windowManagement.publicDisplay;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import keys.GameOptions;
import keys.KeySignatures;

/**
 * For displaying game options selected by player one.  Currently, it only shows four game options:
 * <ul>
 *     <li>Playback Tempo</li>
 *     <li>Initial Key Signature</li>
 *     <li>Number of measures to complete the song</li>
 *     <li>Time Signature</li>
 * </ul>
 * 
 * @author Zach
 */
public class GameOptionsWindow extends JPanel 
{
	/**
	 * Default Constructor
	 */
	public GameOptionsWindow()
	{
		super();
		initImages();
	}
	
	/**
	 * Create a game options window with the given presets
	 * @param key - <code>String</code> representing the key signature
	 * @param beats
	 * @param subdivision
	 * @param tempo
	 * @param measureCount
	 */
	public GameOptionsWindow(String key, int beats, int subdivision, int tempo, int measureCount)
	{
		super();
		initImages();
		
		setKey(key);
		setTime(beats, subdivision);
		setTempo(tempo);
		setMeasureCount(measureCount);
	}
	
	/**
	 * Set the tempo at which the song will be played back at the end of the game.
	 * @param tempo - <code>int</code> in beats per minute
	 */
	public void setTempo(int tempo)
	{
		if (tempo > 0)
		{
			this.playbackTempo = tempo;
			repaint();
		}
	}
	
	/**
	 * Sets the key signature at the beginning of the staff.
	 * @param key - <code>String</code> key from <code>keys.KeySignatures</code>
	 */
	public void setKey(String key)
	{
		if (key != null && KeySignatures.hasKeySignature(key))
		{
			this.keySignature = key;
			repaint();
		}
	}
	
	/**
	 * Set's the time signature at which the notes are drawn.  Currently, it only supports time signatures
	 * <ul>
	 *     <li>2/4</li>
	 *     <li>3/4</li>
	 *     <li>4/4</li>
	 * </ul>
	 * @param beats - how many beats in a measure
	 * @param subdivision - which note gets the downbeat in a measure
	 */
	public void setTime(int beats, int subdivision)
	{
		if ((1 < beats && beats < 5) && subdivision == 4)
		{
			this.beatsPerBar = 4;
			this.subdivision = 4;
			repaint();
		}
	}
	
	/**
	 * Sets the number of measures required for players to win the game
	 * @param count - <code>int</code> representing the number of measures
	 */
	public void setMeasureCount(int count)
	{
		if (count > 0)
		{
			this.measureCount = count;
			repaint();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		List<String> options = GameOptions.getOptions();
		
		// draw main background images
		g2d.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
		
		// game options header-logo
		double scale = 0.45;
		int imageWidth = (int)(getWidth()*scale);
		int imageHeight = (int)(getHeight()*scale);
		g2d.drawImage(logo, Math.abs(getWidth() - imageWidth)/2, getHeight()/10, imageWidth, imageHeight, null);
		
		// Setup font and other settings for drawing the game option headings horizontally across the screen
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Calibri", Font.PLAIN, (int)(getHeight()*0.06)));
		int start = (int)(0.15*getWidth());
		int end = (int)(0.85*getWidth());
		int regionWidth = (end - start)/options.size();
		
		// Draw the game options
		for (int i = 0; i < options.size(); ++i)
		{
			String option = options.get(i);
			int stringWidth = g2d.getFontMetrics().stringWidth(option);
			int descent = g2d.getFontMetrics().getDescent();
			int x = start + i*regionWidth + (regionWidth - stringWidth)/2;
			int y = getHeight()*7/12;
			g2d.drawString(option, x, y);
			g2d.drawLine(x, y + descent, x + stringWidth, y + descent);
			drawOption(g2d, option, x, y + descent);
		}
	}
	
	// There was really no clean way of doing this without creating a separate
	// class for encapsulating game options.  That seems like overkill (especially
	// at this stage in the game), so I just multiplex.  Don't judge; you would do it, too.
	private void drawOption(Graphics2D g2d, String option, int x, int y)
	{
		int shift = g2d.getFontMetrics().getAscent();
		if (option.equals(GameOptions.KEY))
		{
			String displayKey = keySignature;
			if (keySignature.contains("Major"))
			{
				displayKey = keySignature.replace("Major", "");
			}
			g2d.drawString(displayKey, x, y + shift);
		}
		else if (option.equals(GameOptions.TEMPO))
		{
			g2d.drawString(new Integer(playbackTempo).toString(), x, y + shift);
		}
		else if (option.equals(GameOptions.TIME))
		{
			Integer top = new Integer(this.beatsPerBar);
			Integer bottom = new Integer(this.subdivision);
			g2d.drawString(top.toString() + "/" + bottom.toString(), x, y + shift);
		}
		else if (option.equals(GameOptions.BARS))
		{
			g2d.drawString(new Integer(measureCount).toString(), x, y + shift);
		}
	}
	
	// Called from the constructors, this function loads the game options logo and the background.
	private void initImages()
	{
		try 
		{
			setBackground(Color.BLACK);
			background = ImageIO.read(new File("blackground.jpg"));
			logo = ImageIO.read(new File("gameoptions.png"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	// member variables and their defaults
	private String keySignature = KeySignatures.CMajor;
	private int playbackTempo = 120;
	private int beatsPerBar = 4;
	private int subdivision = 4;
	private int measureCount = 16;
	
	private BufferedImage background = null;
	private BufferedImage logo = null;
	
	private static final long serialVersionUID = 1L;
}
