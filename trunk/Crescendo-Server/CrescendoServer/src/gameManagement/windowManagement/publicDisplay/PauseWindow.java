package gameManagement.windowManagement.publicDisplay;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PauseWindow extends JPanel 
{
	public PauseWindow()
	{
		initImages();
	}
	
	private void initImages()
	{
		try 
		{
			this.setBackground(Color.GRAY);
			paused = ImageIO.read(new File("paused.png"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		int scale = 2;
		int logoWidth = paused.getWidth()/scale;
		int logoHeight = paused.getHeight()/scale;
		
		g.drawImage(paused, (this.getWidth() - logoWidth)/2, this.getHeight()/5, logoWidth, logoHeight, null);
	}
	
	private static final long serialVersionUID = 1L;
	private BufferedImage paused = null;
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("test");
		frame.setMinimumSize(new Dimension(800, 600));
		frame.setLayout(new GridLayout(1,1));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(new PauseWindow());
		
		frame.setVisible(true);
	}
}
