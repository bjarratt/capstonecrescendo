package gameManagement.windowManagement.windows;

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

public class Window extends PApplet 
{
	public void nextPanel(Panel panel) throws InterruptedException
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
	
	public void previousPanel(Panel panel) throws InterruptedException
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
			initSelf();
			initPanels();
			setupComplete = true;
			Thread.currentThread().notifyAll();
		}
	}
	
	
	@Override
	public void draw()
	{
		rect(-1, -1, width + 1, height + 1);
		fill(0, 15);
		
		if (animateScreen)
		{
			Dimension newsSize = panels.get(0).getSize();
			Dimension oldsSize = panels.get(1).getSize();
			Point p = autoCenter(oldsSize);
			
			Point oldPoint = panels.get(0).getLocation();
			Point newPoint = panels.get(1).getLocation();
			
			int oldsX = oldPoint.x + getDirection()*deltaX;
			int newsX = newPoint.x + getDirection()*deltaX;
			
			stroke(random(255), random(255), random(255));
			if (isNextAnimation)
			{
				xNewLineIncrement = newsSize.width;
				xOldLineIncrement = oldsSize.width;
			}
			else
			{
				xNewLineIncrement = 0;
				xOldLineIncrement = 0;
			}
			
			if (panels.get(0).getParent() != null)
			{
				line(oldPoint.x + xOldLineIncrement , oldPoint.y, oldPoint.x + xOldLineIncrement, oldPoint.y + oldsSize.height);
			}
			line(newPoint.x + xNewLineIncrement, newPoint.y, newPoint.x + xNewLineIncrement, newPoint.y + newsSize.height);
			
			panels.get(0).setLocation(oldsX, oldPoint.y);
			panels.get(1).setLocation(newsX, newPoint.y);  
	
			if (dynamicCompare(newsX, p.x))
			{
				this.remove(panels.get(0));
				panels.set(0, panels.get(1));
				animateScreen = false;
			}
		}
	}
	
	private void initSelf()
	{
		Dimension size = this.getParent().getSize();
		this.setSize(size);
		noStroke();
		smooth();
		frameRate(55);
	}
	
	private void initPanels()
	{
		// This could be initialized a little better, but this seemed to be the only way to get 
		// array list initialized before calling draw()
		panels.add(new Panel());
		panels.add(new Panel());
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
	
	// for trailing line
	int xNewLineIncrement = 0;
	int xOldLineIncrement = 0;

	private final int WAIT = 10;
	private boolean setupComplete = false;
	private boolean isNextAnimation = false;
	private boolean animateScreen = false;
	private int direction = 1;
	private int deltaX = 18;
	private ArrayList<Panel> panels = new ArrayList<Panel>(2);
}
