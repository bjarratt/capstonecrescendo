package gameManagement.windowManagement.base;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Toolkit;
import java.security.Security;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import processing.core.PApplet;

/**
 * It makes the program sparkle.  That's really all you need to know.
 * @author Zach
 *
 */
public class WindowDisplay extends PApplet 
{
	public void nextPanel(Wrapper panel) throws InterruptedException
	{
		lock.lock();
		while(!setupComplete || animateScreen)
		{
			Thread.sleep(WAIT);
		}
		if (panel != null)
		{
			panels.set(1, panel);
			setDirection(-1);
			
			Dimension size = panel.getSize();
			
			Point p = autoCenter(size);
			
			panels.get(1).setLocation(p.x + this.getWidth(), p.y);
			this.add(panels.get(1));
			isNextAnimation = true;
			animateScreen = true;
		}
		lock.unlock();
	}
	
	public void previousPanel(Wrapper panel) throws InterruptedException
	{
		lock.lock();
		while(!setupComplete || animateScreen)
		{
			Thread.sleep(WAIT);
		}
		if (panel != null)
		{
			panels.set(1, panel);
			setDirection(1);
			
			Dimension size = panel.getSize();
			
			Point p = autoCenter(size);
			
			panels.get(1).setLocation(p.x - this.getWidth(), p.y);
			this.add(panels.get(1));
			isNextAnimation = false;
			animateScreen = true;
		}
		lock.unlock();
	}
	
	@Override
	public void setup()
	{
		synchronized (Thread.currentThread())
		{
			lock.lock();
			try {
				initSelf();
				initPanels();
				setupComplete = true;
				Thread.currentThread().notifyAll();
			}
			finally {
				lock.unlock();
			}
		}
	}
	
	
	@Override
	public void draw()
	{
		fill(0, 15);
		rect(-1, -1, width + 1, height + 1);
		if (animateScreen)
		{
			fill(255);
			ellipse(random(width), random(height), 4, 4);
			Dimension oldsSize = panels.get(1).getSize();
			Point p = autoCenter(oldsSize);
			
			Point oldPoint = panels.get(0).getLocation();
			Point newPoint = panels.get(1).getLocation();
			
			int oldsX = oldPoint.x + getDirection()*deltaX;
			int newsX = newPoint.x + getDirection()*deltaX;
			
			panels.get(0).setLocation(oldsX, oldPoint.y);
			panels.get(1).setLocation(newsX, newPoint.y);  
	
			if (dynamicCompare(newsX, p.x))
			{
				this.remove(panels.get(0));
				panels.set(0, panels.get(1));
				panels.get(0).startApplet();
				animateScreen = false;
			}
		}
	}
	
	private void initSelf()
	{
		Dimension parentSize = this.getParent().getSize();
		size(parentSize.width, parentSize.height, P3D);
		noStroke();
		smooth();
		frameRate(55);
	}
	
	private void initPanels()
	{
		// This could be initialized a little better, but this seemed to be the only way to get 
		// array list initialized before calling draw()
		panels.add(new Wrapper());
		panels.add(new Wrapper());
		panels.get(0).setBackground(Color.BLACK);
		panels.get(0).setSize(800, 600);
		Point p = autoCenter(panels.get(0).getSize());
		panels.get(0).setLocation(p);
	}

	private void setDirection(int direction)
	{
		this.direction = direction;
		
		if (direction != 0)
		{
			this.direction /= Math.abs(this.direction);
		}
	}
	
	private boolean dynamicCompare(int x, int pointX)
	{
		boolean isDone = false;
		
		if (isNextAnimation && x <= pointX)
		{
			isDone = true;
		}
		else if (!isNextAnimation && x >= pointX)
		{
			isDone = true;
		}
		
		return isDone;
	}

	private int getDirection()
	{
		return direction;
	}
	
	private Point autoCenter(Dimension size)
	{
		Point location = new Point();
		
		if (size != null)
		{
			Dimension winSize = this.getSize();
			double xCoord = Math.abs(winSize.getWidth() - size.getWidth()) / 2;
			double yCoord = Math.abs(winSize.getHeight() - size.getHeight()) / 2;
			location.setLocation(xCoord, yCoord);
		}
		return location;
	}
	
	private static Lock lock = new ReentrantLock();
	
	private final int WAIT = 10;
	private boolean setupComplete = false;
	private boolean isNextAnimation = false;
	private boolean animateScreen = false;
	private int direction = 1;
	private int deltaX = 25;
	private ArrayList<Wrapper> panels = new ArrayList<Wrapper>(2);
}
