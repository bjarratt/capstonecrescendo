package gameManagement.windowManagement.publicDisplay;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SplashWindow extends JPanel 
{
	/**
	 * Constructor
	 */
	public SplashWindow()
	{
		super();
		initSelf();
	}

	/**
	 * Brighten the player icon to indicate a connected status
	 * @param player - <code>int</code> indicating which player connected 
	 */
	public void connectPlayer(int player)
	{
		if (0 < player && player < 5)
		{
			connections.set(player - 1, true);
			this.repaint();
		}
	}
	
	/**
	 * Dims the corresponding player icon
	 * @param player - <code>int</code> indicating which player disconnected
	 */
	public void disconnectPlayer(int player)
	{
		if (0 < player && player < 5)
		{
			connections.set(player, false);
			this.repaint();
		}
	}
	
	// Read in the images for the background, logo, and player connections
	private void initSelf()
	{
		this.setBackground(Color.BLACK);
		
		try {
			background = ImageIO.read(new File("blackground.jpg"));
			logo = ImageIO.read(new File("crescendo.png"));
			
			players.add(createOpacityWrapper(ImageIO.read(new File("player1.png"))));
			players.add(createOpacityWrapper(ImageIO.read(new File("player2.png"))));
			players.add(createOpacityWrapper(ImageIO.read(new File("player3.png"))));
			players.add(createOpacityWrapper(ImageIO.read(new File("player4.png"))));
			
			for (int i = 0; i < players.size(); ++i)
			{
				connections.add(false);
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// Draw a buffered image into another buffered image so we can control the size.
	private BufferedImage createOpacityWrapper(BufferedImage image) throws IOException
	{
		BufferedImage wrapper = null;
		
		if (image != null)
		{
			wrapper = new BufferedImage(image.getWidth()/3, image.getHeight()/3, BufferedImage.TYPE_INT_ARGB);

			Graphics2D g2d = wrapper.createGraphics();
			g2d.drawImage(image, 0, 0, wrapper.getWidth(), wrapper.getHeight(), null);
			g2d.dispose();
		}
		
		return wrapper;
	}
	
	
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Dimension size = this.getSize();
		float scale = 1.5f;
		int logoWidth = (int)(logo.getWidth()/scale);
		int logoHeight = (int)(logo.getHeight()/scale);
		int start = this.getWidth()*2/10;
		int end = this.getWidth()*8/10;
		int space = (end - start) / players.size();
		
		g.drawImage(background, 0, 0, size.width, size.height, null);
		g.drawImage(logo, (size.width - logoWidth)/2, (size.height - logoHeight)/4, logoWidth, logoHeight, null);
		
		for (int i = 0; i < players.size(); ++i)
		{
			BufferedImage image = players.get(i);
			int x = start + i*space + (space - image.getWidth())/2;
			int y = this.getHeight()*3/5;
			
			
			AlphaComposite ac = null;
			
			if (connections.get(i))
			{
				ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, CONNECTED);
			}
			else
			{
				ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, DISCONNECTED);
			}
			
			((Graphics2D)g).setComposite(ac);
			((Graphics2D)g).drawImage(image, null, x, y);
		}
	}
	
	private static final long serialVersionUID = 1L;
	
	private static final float DISCONNECTED = 0.33F;
	private static final float CONNECTED = 1.0F;
	
	private BufferedImage background = null;
	private BufferedImage logo = null;
	private ArrayList<BufferedImage> players = new ArrayList<BufferedImage>();
	private ArrayList<Boolean> connections = new ArrayList<Boolean>();
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("test");
		frame.setMinimumSize(new Dimension(800, 600));
		frame.setLayout(new GridLayout(1,1));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(new SplashWindow());
		
		frame.setVisible(true);
	}
}
