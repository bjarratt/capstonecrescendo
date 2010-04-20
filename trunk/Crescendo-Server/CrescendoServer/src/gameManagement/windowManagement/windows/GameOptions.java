package gameManagement.windowManagement.windows;

import java.util.List;

import processing.core.*; 

public class GameOptions extends PApplet 
{
	private static final long serialVersionUID = 1L;
	private int noties = 50;
	private Note[] n = new Note[noties];
	private PImage bg;
	private PImage logo;
	private Option o1, o2, o3, o4;

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
	
	public void setup() 
	{
		size(800, 600, P3D);
		bg = loadImage("blackground.jpg");
		logo = loadImage("gameoptions.png");
		
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
  
		List<String> options = keys.GameOptions.getOptions();
		int optionHeight = width/11;
		int optionWidth = optionHeight;
		
		//instantiate player icons
		o1 = new Option("Key", width/12 + width/(7*12), 7/10*height - optionHeight);
		
		o2 = new Option("Time", width/4+width/40 + (width/4+width/40)/7, 7/10*height - optionHeight);
  
		o3 = new Option("Tempo", width/2-width/32 + (width/2-width/32)/7, 7/10*height - optionHeight);
  
		o4 = new Option("Bars", width/2+width/7 + (width/2+width/7)/7, 7/10*height - optionHeight);
	}

	public void draw()
	{
		image(bg,0,0,width,height);
		image(logo,width/4,height/12,width/2,height/3);
		lights();
		o1.display();
		o2.display();
		o3.display();
		o4.display();
		
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
		for (int i = 0; i < noties; i++){
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
	private class Cube
	{
		PVector[] vertices = new PVector[24];
		
		Cube(float w, float h, float d) {
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
	}


	private class Note
	{
		float x, y, w, h;
		Note(float x, float y, float w, float h, char t) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}

		public void create()
		{
			fill(255,50);
			ellipseMode(CENTER);
			ellipse(x,y,w,h);
			rect(x+w/2-w/8,y,w/11,2*h);
		}
	}
	
	private class Option
	{
		String optionTitle;
		float xPos;
		float yPos;
		PFont font = createFont("Helvetica", 32);
  
		public Option(String option, int x, int y)
		{
			optionTitle = option;
			xPos = x;
			yPos = y; 
		}
  
		public void display()
		{
			textFont(font);
			fill(255);
			text(optionTitle, xPos/*xPos+xPos/7*/, yPos/*yPos-height*/);
		}
	}

	static public void main(String args[]) {
		PApplet.main(new String[] { "--present", "--bgcolor=#666666", "--stop-color=#cccccc", "displayManager.messageTranslationSystem.game_options" });
	}
}
