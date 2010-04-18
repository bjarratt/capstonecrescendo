package gameManagement.windowManagement.windows;

import processing.core.*; 

import java.awt.Dimension; 
import java.util.*; 
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;

import keys.Pitches;

public class PublicDisplay extends PApplet 
{
	private static final int WAIT = 10;
	private static final int NOTE_CAP = 8;
	
	// sync flags
	private boolean setupComplete = false;
	
	private int num_of_players = 0;
	private Staff[] staffs;
	private Staff cap,cap2,cap3,cap4;
	// List of everyone's notes for drawing purposes
	private ArrayList<DrawNote> notes = new ArrayList<DrawNote>();
	private PImage background;
	private PImage[] imgList = new PImage[4];
	private ArrayList<String> players = new ArrayList<String>();
	
	private Lock lock = new ReentrantLock();
	
	// These are the mappings between y coordinate and pitch name
	private HashMap<String, Float> staffPositions = new HashMap<String, Float>();	

	public PublicDisplay()
	{
		super();
	}
	
	public PublicDisplay(int playerCount)
	{
		super();
		this.num_of_players = playerCount;
	}
	
	public void setup()
	{
		synchronized(Thread.currentThread())
		{
			lock.lock();
			try
			{
				size(800,600);
				smooth();
				background = loadImage("blackground.jpg");
		  
				imgList[0] = loadImage("capsule1.png");
				imgList[1] = loadImage("capsule2.png");
				imgList[2] = loadImage("capsule3.png");
				imgList[3] = loadImage("capsule4.png");
				players.add("player1");
				players.add("player2");
				players.add("player3");
				players.add("player4");
				staffs = new Staff[num_of_players];
				
				if(num_of_players == 1)
				{
					cap = new Staff(1,height/3+height/12,width-100,50,height/4);
					cap.setScrollIncrement(cap.width/30);
					staffs[0] = cap;
				}
				else if(num_of_players == 2)
				{
					cap = new Staff(1,height/3,width-100,50,height/4-height/5);
					cap2 = new Staff(2,height/3,width-100,50,(2*height/4));
					cap.setScrollIncrement(cap.width/30);
					cap2.setScrollIncrement(cap2.width/30);
					staffs[0] = cap;
					staffs[1] = cap2;
				}
				else if(num_of_players == 3)
				{
					cap = new Staff(1,height/4,width-100,50,height/4-height/5);
					cap2 = new Staff(2,height/4,width-100,50,height/4+height/12);
					cap3 = new Staff(3,height/4,width-100,50,height/2+height/8);
					cap.setScrollIncrement(cap.width/30);
					cap2.setScrollIncrement(cap2.width/30);
					cap3.setScrollIncrement(cap3.width/30);
					staffs[0] = cap;
					staffs[1] = cap2;
					staffs[2] = cap3;
				}
				else if(num_of_players == 4)
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
		  
				setStaffPositions();
				setupComplete = true;
			}
			finally
			{
				lock.unlock();
			}
		}
	}

	public void draw()
	{
		image(background,0,0,width,height);
		for(int i = 0; i < staffs.length; i++)
		{
			staffs[i].display(imgList[i]);
			for (DrawNote note : notes)
			{
				note.create();
			}
		}
	}
		
	private void setStaffPositions()
	{
		List<String> allNotes = Pitches.getAllNotes();
		
		staffPositions.put(allNotes.get(0), 1f);
		staffPositions.put(allNotes.get(1), 1f);
		staffPositions.put(allNotes.get(2), 1.5f);
		staffPositions.put(allNotes.get(3), 1.5f);
		staffPositions.put(allNotes.get(4), 2f);
		staffPositions.put(allNotes.get(5), 2f);
		staffPositions.put(allNotes.get(6), 2.5f);
		staffPositions.put(allNotes.get(7), 2.5f);
		staffPositions.put(allNotes.get(8), 3f);
		staffPositions.put(allNotes.get(9), 3f);
		staffPositions.put(allNotes.get(10), 3.5f);
		staffPositions.put(allNotes.get(11), 3.5f);
		staffPositions.put(allNotes.get(12), 4f);
		staffPositions.put(allNotes.get(13), 4f);
		staffPositions.put(allNotes.get(14), 4.5f);
		staffPositions.put(allNotes.get(15), 4.5f);
		staffPositions.put(allNotes.get(16), 5f);
	}

	
	public void playNoteHandler(String rawInfo) throws InterruptedException
	{
		lock.lock();
		try 
		{
			while (!setupComplete)
			{
				Thread.sleep(WAIT);
			}
			
			if (rawInfo != null)
			{
				String[] parsed = rawInfo.trim().split("_");
				int index = players.indexOf(parsed[0]);
				if (parsed.length == 3 && index != -1)
				{
					noLoop();
					Staff current = staffs[index];
					current.scrollNotes();
					float position = 1;
					if (staffPositions.containsKey(parsed[1]))
					{
						position = staffPositions.get(parsed[1]);
					}
					DrawNote note = new DrawNote(current.xPos + current.width * 6/7,
												 current.yPos + (current.height/5) + ((position-1)*current.height/8),
												 current.width/30,
												 current.height/8, 
												 parsed[2].charAt(0));
					notes.add(note);
					staffs[index].addNote(note);
				}
			}
		}
		finally
		{
			loop();
			lock.unlock();
		}
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
				drawFilledEllipse();			  
				drawStem();
			}
			if(type == 'h')
			{
				drawOpenEllipse();			  
				drawStem();
			}
			if(type == 'w')
			{
				drawOpenEllipse();
			}
			if(type == 'i')
			{
				drawFilledEllipse();
				drawStem();
				rect(x + (w+8)/2 - (w+5)/10 , y-2*h , w+10, h/4);
			}
		}
	    private void drawFilledEllipse()
	    {
	    	fill(0);
	    	ellipseMode(CENTER);
	    	ellipse(x, y, (float) (h*1.1), h);
	    }
	    
	    private void drawOpenEllipse()
	    {
	    	stroke(0);
	    	noFill();
	    	ellipseMode(CENTER);
	    	for(int i = 0; i< h*0.5; i++)
	    	{
	    		ellipse(x, y, (float)((h*1.1)-i),h-4);
	    	}
	    }
	    
		private void drawStem()
		{
			fill(0);
			rect((float)(x+4*h/10), y-2*h, (h+9)/8, 2*h);
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
		float deltaX;
		// These are all the notes a player has built and sent to the server
		ArrayList<DrawNote> notes = new ArrayList<DrawNote>(); 
		
		Staff(int p, int h, int w, int x, int y)
		{
			playerIndex = p;
			height = h;
			width = w;
			xPos = x;
			yPos = y; 
		}
	  

		public void addNote(DrawNote note)
		{
			if (note != null)
			{
				this.notes.add(note);	
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
		public void setScrollIncrement(float deltaX)
		{
			this.deltaX = deltaX;
		}
	 
		// have the public display call this every time a beat goes by.
		public void scrollNotes()
		{
		    int listSize = notes.size();
		    for (int i = 0; i < listSize; ++i)
		    {
		    	DrawNote note = notes.get(i);
		    	note.setX((float) (note.getX() - this.deltaX - 0.15*height));
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
	
	static PublicDisplay display = new PublicDisplay(4);
	static public void main(String args[]) 
	{
		
		JFrame frame = new JFrame();
		frame.setMinimumSize(new Dimension(800, 600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(display);
		display.init();
		display.start();
		frame.setVisible(true);
		
			Thread t1 = new Thread(new Runnable() 
								   { 
								       public void run() {
								    	   for (int i = 0; i < 10; ++i)
								    	   {
								    		   try {
								    			   Thread.sleep(1000);
												display.playNoteHandler("player2_C6_whole");
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
								    		   
								    	   }
								       } 
								   });
			
			Thread t2 = new Thread(new Runnable() 
								   { 
								       public void run() {
								    	   for (int i = 0; i < 10; ++i)
								    	   {
								    		   try {
								    			   Thread.sleep(1000);
													display.playNoteHandler("player1_C6_eighth");
												} catch (InterruptedException e) {
													e.printStackTrace();
												}
								    	   }
								       } 
								   });

//			t1.run();
//			t2.run();
	}
}