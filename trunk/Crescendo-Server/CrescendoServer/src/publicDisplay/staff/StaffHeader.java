package publicDisplay.staff;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import publicDisplay.ColorMap;


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
import java.util.List;

import keys.KeySignatures;
import keys.Pitches;
import keys.Players;

public class StaffHeader extends JPanel 
{
	public StaffHeader()
	{
		initImages();
		
		setPlayer(Players.PLAYER_ONE);
	}
	
	public StaffHeader(final String player, final String keySignature, final int beatsPerBar, final int subdivision)
	{
		initImages();
		
		setPlayer(player);
		setTimeSignature(beatsPerBar, subdivision);
		setKeySignature(keySignature);
	}
	
	public void setPlayer(String player)
	{
		this.setBackground(ColorMap.getPlayerColor(player));
	}
	
	public void setTimeSignature(final int beatsPerBar, final int subdivision)
	{
		this.beatsPerBar = beatsPerBar;
		this.subdivision = subdivision;
		repaint();
	}
	
	public void setKeySignature(String key)
	{
		this.keySignature = KeySignatures.getKeySignature(key);
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Dimension size = this.getSize();
		g2d.setColor(Color.WHITE);
		int width = (int)(size.width);
		int height = (int)(size.height * 0.7);
		int xCoord = this.getX() + Math.abs(size.width - width)/2;
		int yCoord = this.getY() + Math.abs(size.height - height)/2;

		g2d.fillRect(xCoord, yCoord, width, height);
		
		BasicStroke stroke = new BasicStroke(0.005f*size.height);
		g2d.setColor(Color.BLACK);
		g2d.setStroke(stroke);
		
		for (int i = 0; i < 5; ++i)
		{
			int y = (int)calculatStaffLine(yCoord, (float)height, (float)i);
			g2d.drawLine(0, y, width, y);
		}
		
		float scale = height*0.00233f;
		float displacement = 1.4f;
		int nextX = drawClef(g2d, clef, xCoord, (int)(yCoord*displacement), scale);
		nextX = drawKeySignature(g2d, nextX, yCoord, height);
		nextX = drawTimeSignature(g2d, nextX, yCoord, height);
	}
	
	private void initImages()
	{
		try
		{
			clef = ImageIO.read(new File("Treble_clef.png"));
			flat = ImageIO.read(new File("flat.png"));
			sharp = ImageIO.read(new File("sharp.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private int drawClef(Graphics2D g2d, BufferedImage image, int x, int y, float scale)
	{
		int startX = x;
		g2d.drawImage(image, x, y, (int)(image.getWidth()*scale), (int)(image.getHeight()*scale), null);
		return startX + (int)(image.getWidth()*scale);
	}
	
	private int drawKeySignature(Graphics2D g2d, int startX, int startY, int regionHeight)
	{
		for (String accidental : keySignature)
		{
			float position = Pitches.getStaffPosition(accidental);
			int y = (int)calculatStaffLine(startY, regionHeight, position - 1);
			
			// Is the accidental a flat or a sharp?
			BufferedImage symbol = isSharp(accidental) ? sharp : flat;
			
			// Calculate positioning stuff
			float verticalShift = isSharp(accidental) ? regionHeight * 0.095f : 7;
			float scale = isSharp(accidental) ? regionHeight * 0.002f : 5;
			
			// draw it on the staff
			g2d.drawImage(symbol, startX, (int)(y - verticalShift), (int)(symbol.getWidth()*scale), (int)(symbol.getHeight()*scale), null);
			startX += symbol.getWidth()*scale;
		}
		
		return startX;
	}
	
	private int drawTimeSignature(Graphics2D g2d, int startX, int startY, int regionHeight)
	{
		float textHeight = calculatStaffLine(startY, regionHeight, 3) - calculatStaffLine(startY, regionHeight, 0);
		g2d.setFont(new Font("Times New Roman", Font.PLAIN, (int)textHeight));
		
		g2d.drawString(new Integer(this.beatsPerBar).toString(), 
					   startX, 
					   calculatStaffLine(startY, regionHeight, 2));
		g2d.drawString(new Integer(this.subdivision).toString(), startX, calculatStaffLine(startY, regionHeight, 4));
		
		return startX;
	}
	
	private boolean isSharp(String accidental)
	{
		boolean isSharp = false;
		if (accidental != null && accidental.contains(Pitches.Sharp))
		{
			isSharp = true;
		}
		return isSharp;
	}
	
	private float calculatStaffLine(int startY, float regionHeight, float staffLine)
	{
		return startY*1.35f + (staffLine+1f)*(regionHeight*0.7f)/5f;
	}
	
	private static final long serialVersionUID = 1L;

	private List<String> keySignature = KeySignatures.getKeySignature(KeySignatures.AMajor);
	private int beatsPerBar = 4;
	private int subdivision = 4;
	
	private BufferedImage clef = null;
	private BufferedImage flat = null;
	private BufferedImage sharp = null;
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(800, 600));
		frame.setResizable(false);
		frame.setLayout(new GridLayout(1,1));
		frame.getContentPane().add(new StaffHeader());
		frame.setVisible(true);

	}
}
