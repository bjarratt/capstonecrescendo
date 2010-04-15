package gameManagement.windowManagement.windows;

import processing.core.*; 
import processing.xml.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class PublicDisplay extends PApplet 
{
	private int num_of_players;
	private Staff[] staffs;
	private Staff cap,cap2,cap3,cap4;
	private PImage background;
	private PImage[] imgList = new PImage[4];
	private DrawNote n;
	private ArrayList<String> players = new ArrayList<String>();
	
	public void setup()
	{
		size(800, 600);
		smooth();
		background = loadImage("blackground.jpg");
		image(background,0,0,width,height);
		imgList[0] = loadImage("capsule1.png");
		imgList[1] = loadImage("capsule2.png");
		imgList[2] = loadImage("capsule3.png");
		imgList[3] = loadImage("capsule4.png");
		//n = new DrawNote(width/2,height/3,width/40,width/40,'q');  
		players.add("player1");
		players.add("player2");
		players.add("player3");
		players.add("player4");
		setActivePlayers(2);
	  	staffs = new Staff[num_of_players];
	
	  	if(num_of_players == 1)
	  	{
	  		cap = new Staff(1,height/3+height/12,width-100,50,height/4);
	  		staffs[0] = cap;
	  	}
	  	if(num_of_players == 2)
	  	{
		    cap = new Staff(1,height/3,width-100,50,height/4-height/5);
		    cap2 = new Staff(2,height/3,width-100,50,(2*height/4));
		    staffs[0] = cap;
		    staffs[1] = cap2;
	  	}
	  	if(num_of_players == 3)
	  	{
		    cap = new Staff(1,height/4,width-100,50,height/4-height/5);
		    cap2 = new Staff(2,height/4,width-100,50,height/4+height/12);
		    cap3 = new Staff(3,height/4,width-100,50,height/2+height/8);
		    staffs[0] = cap;
		    staffs[1] = cap2;
		    staffs[2] = cap3;
	  	}
	  	if(num_of_players == 4)
	  	{
		    cap = new Staff(1,height/4,width-100,50,height/50);
		    cap2 = new Staff(2,height/4,width-100,50,height/4+height/60);
		    cap3 = new Staff(3,height/4,width-100,50,height/2+height/80);
		    cap4 = new Staff(4,height/4,width-100,50,height/2+height/4);
		    staffs[0] = cap;
		    staffs[1] = cap2;
		    staffs[2] = cap3;
		    staffs[3] = cap4;
	  	}
	}

	public void draw()
	{
		for (int i = 0; i < staffs.length; i++)
		{
			staffs[i].display(imgList[i]);
		}
	}
	
	public void playNoteHandler(String rawInfo)
	{
		if (rawInfo != null)
		{
			String[] parsed = rawInfo.trim().toLowerCase().split("_");
			if (parsed.length == 3)
			{
				for (String player : players)
				{
					if (player.equals(parsed[0]))
					{
						int index = players.indexOf(player);
						staffs[index].addNote(n);
						break;
					}
				}
			}
		}
	}

	public void setActivePlayers(int activePlayers)
	{
		num_of_players = activePlayers;
	}


	class DrawNote
	{
		float x,y,w,h;
		char type;
	  
		//default constructor
		DrawNote(){}
	  
		DrawNote(float x, float y, float w, float h, char t)
		{
			this.x = x;
		    this.y = y;
		    this.w = w;
		    this.h = h;
		    type = t;
		}
	  
		public void setX(float x)
		{
			this.x = x;
		}
	  
		public float getX()
		{
			return this.x;
		}
	  
		public void setY(float y)
		{
			this.y = y;
		}
	  
		public void setWidth(float w)
		{
			this.w = w;
		}
	  
		public void setHeight(float h)
		{
			this.h = h;
		}
	  
		public void create()
		{
			if(type == 'q')
			{
				fill(0);
				ellipseMode(CENTER);
				ellipse(x,y,w,h-4);
				rect(x+w/2-w/10,y-2*h,w/8,2*h);
			}
		    if(type == 'h')
		    {
		    	noFill();
		    	stroke(0);
		    	ellipseMode(CENTER);
		    	ellipse(x,y,w,h-4);
		    	fill(0);
		    	rect(x+w/2-w/10,y-2*h,w/8,2*h);
		    }
		    if(type == 'w')
		    {
		    	noFill();
		    	stroke(0);
		    	ellipseMode(CENTER);
		    	for(int i = 0; i< 8; i++)
		    	{
		    		ellipse(x,y,w-i,h-4);
		    	}
		    }
		    if(type == 'i')
		    {
		    	fill(0);
		    	ellipseMode(CENTER);
		    	ellipse(x,y,w,h-4);
		    	rect(x+w/2-w/10,y-2*h,w/8,2*h);
		    	rect(x+w/2-w/10,y-2*h,w,h/4);
		    }
		}
	}

	class Staff
	{
		int playerIndex;
		float xPos;
		float yPos;
		int c;
		float height;
		float width;
		int deltaX;
		// These are all the notes a player has built and sent to the server
		ArrayList<DrawNote> notes = new ArrayList<DrawNote>(); 
		
		// These are the mappings between y coordinate and pitch name
	  
		Staff(int index, int height, int width, int xCoord, int yCoord, ArrayList<DrawNote> tempNotes)
		{
		    playerIndex = index;
		    this.height = height;
		    this.width = width;
		    this.xPos = xCoord;
		    this.yPos = yCoord; 
		    
		    if (tempNotes != null && !tempNotes.isEmpty())
		    {
		    	notes.addAll(tempNotes);
		    }
		}
		
		Staff(int index, float width, float height, float xCoord, float yCoord)
		{
			playerIndex = index;
			this.width = width;
			this.height = height;
			this.xPos = xCoord;
			this.yPos = yCoord;
		}
	
		public void addNote(DrawNote n)
		{
			if (notes == null)
			{
				notes = new ArrayList<DrawNote>();
			}
			notes.add(n);
		}
		
		public void addNote(String pitch, String length)
		{
			if (pitch != null && length != null)
			{
				
			}
		}
		
		public void addNotes(ArrayList<DrawNote> notes)
		{
			if (notes != null)
			{
				for (DrawNote n : notes)
				{
					this.notes.add(n);
				}
			}
		}
	  
		public void roundRect(float x, float y, float w, float h)
		{
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
	
		// Pretend that each beat is an increment of the x coordinate for every note  
		public void setScrollIncrement(int deltaX)
		{
			this.deltaX = deltaX;
		}
	 
		// have the public display call this every time a note is played.
		public void scrollNotes()
		{
			for (DrawNote note : notes)
			{
				note.setX(note.getX() + this.deltaX);
			}
		}
	  
		public void display(PImage icon)
		{
			//draw the capsule container first
			if(playerIndex == 1)
		    {
				image(icon,xPos,yPos,width,height);
		    }
		    if(playerIndex == 2)
		    {
		    	image(icon,xPos,yPos,width,height);
		    }
		    if(playerIndex == 3)
		    {
		    	image(icon,xPos,yPos,width,height);
		    }
		    if(playerIndex == 4)
		    {
		    	image(icon,xPos,yPos,width,height);
		    }
	
		    //now draw the holding rectangle for the Staff lines
		    fill(255,255,255,200);
		    stroke(0);
		    roundRect(xPos+(width/10),yPos+(height/11),width-(width/5),height-(height/5));
		    //now draw the lines
		    noStroke();
		    fill(150);
		    rect(xPos+(width/10),yPos+(height/5),width-(width/5),height/120);
		    rect(xPos+(width/10),yPos+(height/5)+(height/8),width-(width/5),height/120);
		    rect(xPos+(width/10),yPos+(height/5)+(2*height/8),width-(width/5),height/120);
		    rect(xPos+(width/10),yPos+(height/5)+(3*height/8),width-(width/5),height/120);
		    rect(xPos+(width/10),yPos+(height/5)+(4*height/8),width-(width/5),height/120);
		}
	}
	
	static public void main(String args[]) 
	{
		PApplet.main(new String[] { "--present", "--bgcolor=#666666", "--stop-color=#cccccc", "gameManagement.windowManagement.windows.PublicDisplay" });
	}
}
