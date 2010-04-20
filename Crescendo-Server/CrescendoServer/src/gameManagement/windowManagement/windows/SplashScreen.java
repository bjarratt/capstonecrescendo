package gameManagement.windowManagement.windows;

import processing.core.*; 
import java.util.*; 
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SplashScreen extends PApplet 
{
	private static final long serialVersionUID = 1L;
	private final int MAX_PLAYER_COUNT = 4;
	private int noties = 50;
	private Note[] n = new Note[noties];
	private PImage bg;
	private PImage logo;
	private ArrayList<Player> players = new ArrayList<Player>();
	private boolean connection;
	private ArrayList<PImage> icons = new ArrayList<PImage>();
	
	// Controls notie's movement
	private float[] x = new float[noties];
	private float[] y = new float[noties];
	private float[] z = new float[noties];
	private float[] xSpeed = new float[noties];
	private float[] ySpeed = new float[noties];
	private float[] zSpeed = new float[noties];

	// Controls notie's rotation
	private float[] xRot = new float[noties];
	private float[] yRot = new float[noties];
	private float[] zRot = new float[noties];
	
	// Size of external cube
	private float bounds = 640;
	
	private boolean setupComplete = false;
	private Lock lock = new ReentrantLock();

	public void setConnected(int playerIndex, boolean isConnected)
	{
		lock.lock();
		try
		{
			while (!setupComplete){}
			
			if (0 < playerIndex && playerIndex < 5)
			{
				Player p = players.get(playerIndex-1);
				
				if (isConnected)
				{
					p.connectPlayer();
				}
				else
				{
					p.disconnectPlayer();
				}
			}
		}
		finally
		{
			lock.unlock();
		}
	}
	
	public void setup() 
	{
		synchronized (Thread.currentThread())
		{
			lock.lock();
			try
			{
				setupComplete = false;
				size(800, 600, P3D);
				bg = loadImage("blackground.jpg");
				logo = loadImage("crescendo.png");
				for (int i = 0; i < MAX_PLAYER_COUNT; ++i)
				{
					icons.add(loadImage("player" + (i + 1) + ".png"));
				}
				
				for(int i = 0; i < noties; i++)
				{
					//Noties are randomly sized and typed
					float notieSize = random(5,20);
					char notieType = 'q';
					n[i] = new Note(notieSize,notieSize,notieSize,notieSize,notieType);
					
					x[i] = 0;
					y[i] = 0;
					z[i] = 0;
					
					xSpeed[i] = random(-1, 1);
					ySpeed[i] = random(-1, 1); 
					zSpeed[i] = random(-1, 1); 
					
					xRot[i] = random(40, 100);
					yRot[i] = random(40, 100);
					zRot[i] = random(40, 100);
				}	
				new Cube(bounds, bounds, bounds);
		
				// instantiate player icons
				for (int i = 0; i < 4; ++i)
				{
					players.add(new Player(i + 1, width/11, width/11 , width / 4 + i*width/8, height * 7 / 10, connection));
				}
			}
			finally
			{
				setupComplete = true;
				lock.unlock();
			}
		}
	}

	public void draw()
	{
		image(bg,0,0,width,height);
		image(logo,width/4,height/4,width/2,height/3);
		lights();
		
		for (int i = 0; i < MAX_PLAYER_COUNT; ++i)
		{
			players.get(i).display(icons.get(i));
		}
		
		// Center in display window
		translate(width/2, height/2, 0);
  
		// Outer transparent cube
		noFill(); 
  
		// Rotate everything, including external large cube
		rotateX(frameCount * 0.001f);
		rotateY(frameCount * 0.002f);
		rotateZ(frameCount * 0.001f);
		stroke(255);
		
		// Move and rotate noties
		for (int i = 0; i < noties; i++)
		{
			pushMatrix();
			translate(x[i], y[i], z[i]);
			rotateX(frameCount*PI/xRot[i]);
			rotateY(frameCount*PI/yRot[i]);
			rotateX(frameCount*PI/zRot[i]);
			noStroke();
			n[i].create();
			x[i] += xSpeed[i];
			y[i] += ySpeed[i];
			z[i] += zSpeed[i];
			popMatrix();

			// Check wall collisions
			if (x[i] > bounds/2 || x[i] < -bounds/2){
				xSpeed[i]*=-1;
			}
			if (y[i] > bounds/2 || y[i] < -bounds/2){
				ySpeed[i]*=-1;
			}
			if (z[i] > bounds/2 || z[i] < -bounds/2){
				zSpeed[i]*=-1;
			}
		}
	}


	// Custom Cube Class

	class Cube{
		PVector[] vertices = new PVector[24];
		float w, h, d;

		// Default constructor
		Cube(){ }

		// Constructor 2
		Cube(float w, float h, float d) {
			this.w = w;
			this.h = h;
			this.d = d;

			// cube composed of 6 quads
			//front
			vertices[0] = new PVector(-w/2,-h/2,d/2);
			vertices[1] = new PVector(w/2,-h/2,d/2);
			vertices[2] = new PVector(w/2,h/2,d/2);
			vertices[3] = new PVector(-w/2,h/2,d/2);
			//left
			vertices[4] = new PVector(-w/2,-h/2,d/2);
			vertices[5] = new PVector(-w/2,-h/2,-d/2);
			vertices[6] = new PVector(-w/2,h/2,-d/2);
			vertices[7] = new PVector(-w/2,h/2,d/2);
			//right
			vertices[8] = new PVector(w/2,-h/2,d/2);
			vertices[9] = new PVector(w/2,-h/2,-d/2);
			vertices[10] = new PVector(w/2,h/2,-d/2);
			vertices[11] = new PVector(w/2,h/2,d/2);
			//back
			vertices[12] = new PVector(-w/2,-h/2,-d/2); 
			vertices[13] = new PVector(w/2,-h/2,-d/2);
			vertices[14] = new PVector(w/2,h/2,-d/2);
			vertices[15] = new PVector(-w/2,h/2,-d/2);
			//top
			vertices[16] = new PVector(-w/2,-h/2,d/2);
			vertices[17] = new PVector(-w/2,-h/2,-d/2);
			vertices[18] = new PVector(w/2,-h/2,-d/2);
			vertices[19] = new PVector(w/2,-h/2,d/2);
			//bottom
			vertices[20] = new PVector(-w/2,h/2,d/2);
			vertices[21] = new PVector(-w/2,h/2,-d/2);
			vertices[22] = new PVector(w/2,h/2,-d/2);
			vertices[23] = new PVector(w/2,h/2,d/2);
		}	
		
		public void create(){
			// Draw cube
			for (int i=0; i<6; i++){
				beginShape(QUADS);
				for (int j=0; j<4; j++){
					vertex(vertices[j+4*i].x, vertices[j+4*i].y, vertices[j+4*i].z);
				}
				endShape();
			}
		}
		public void create(int[]quadBG){
			// Draw cube
			for (int i=0; i<6; i++){
				fill(quadBG[i]);
				beginShape(QUADS);
				for (int j=0; j<4; j++){
					vertex(vertices[j+4*i].x, vertices[j+4*i].y, vertices[j+4*i].z);
				}
				endShape();
			}
		}
	}


	class Note
	{
		float x, y, w, h;
		char type;
		//default constructor
		Note(){}
		
		Note(float x, float y, float w, float h, char t) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			type = t;
		}	
		
		public void create()
		{
			fill(255,100);
			ellipseMode(CENTER);
			ellipse(x,y,w,h);
			rect(x+w/2-w/8,y,w/11,2*h);
		}	
	}
	
	class Player
	{
		int playerIndex;
		float xPos;
		float yPos;
		float height;
		float width;
		boolean connected;
		
		Player(int p, int h, int w, int x, int y, boolean con)
		{
			playerIndex = p;
			height = h;
			width = w;
			xPos = x;
		    yPos = y; 
		    connected = con;
		}
  
		public void connectPlayer()
		{
			connected = true;
		}
  
		public void disconnectPlayer()
		{
			connected = false;
		}
  
		public void display(PImage icon)
		{
			//tint the icon depending on player connection
			if(connected == true){tint(255);}
			if(connected == false){tint(0,40);}
    
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
			tint(255);
		}
	}

	static public void main(String args[]) {
		PApplet.main(new String[] { "--present", "--bgcolor=#666666", "--stop-color=#cccccc", "gameManagement.windowManagement.windows.SplashScreen" });
	}
}
