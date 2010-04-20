package gameManagement.windowManagement.windows;

import java.awt.Dimension;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class GameModesWindow extends PApplet 
{
	public void addGameType(String type)
	{
		lock.lock();
		try 
		{
			while (!setupComplete) {}
			if (type != null && type != "")
			{
				gameTypes.add(type);
			}
		}
		finally {
			lock.unlock();
		}
	}
	
	public void addGameTypes(String[] types)
	{
		if (types != null)
		{
			for (int i = 0; i < types.length; ++i)
			{
				addGameType(types[i]);
			}
		}
	}
	
	public void addGameTypes(List<String> types)
	{
		if (types != null)
		{
			for (String type : types)
			{
				addGameType(type);
			}
		}
	}
	
	@Override
	public void setup()
	{
		synchronized (Thread.currentThread())
		{
			lock.lock();
			try
			{
				setupComplete = false;
				smooth();
				background(0);
				Dimension parentSize = this.getParent().getSize();
				size(parentSize.width, parentSize.height, P3D);
				textAlign(CENTER, CENTER);
				background = loadImage("blackground.jpg");
			}
			finally
			{
				setupComplete = true;
				lock.unlock();
			}
		}
	}
	
	@Override
	public void draw()
	{
		// Create fonts
		headerFont = createFont("Brush Script MT Italic", (float)(height*0.12));
		optionsFont = createFont("Helvetica", (float)(height*0.06));
		
		// Create margins for background image.
		image(background, 100, 100, 
			  Math.min(background.width + Math.abs(this.width - background.width), this.width) - 200, 
			  Math.min(background.height + Math.abs(this.height - background.height), this.height) - 200);
		
		// This code actually writes the wording and places it on the screen.
		textFont(headerFont);
		int yCoord = height / 5;
		text(HEADER, width/2, height/5);
		stroke(255);
		line((width - textWidth(HEADER))/2, yCoord + 2*textDescent(), (width + textWidth(HEADER))/2, yCoord + 2*textDescent());
		
		// This loop adds the game types.  You probably don't need to worry about this too much
		lock.lock();
		try {
			textFont(optionsFont);
			textLeading(textDescent()/2);
			for (int i = 1; i < gameTypes.size() + 1; i++)
			{
				String list = gameTypes.get(i - 1) + "\n";
				text(list, width/2, yCoord + 2* i * textAscent());
			}
		}
		finally {
			lock.unlock();
		}
	}
	
	private PImage background = null;
	private PFont headerFont = null;
	private PFont optionsFont = null;
	private ArrayList<String> gameTypes = new ArrayList<String>();
	private final String HEADER = "Game Modes";
	
	private boolean setupComplete = false;
	private Lock lock = new ReentrantLock();
}
