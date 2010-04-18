package gameManagement.windowManagement.windows;

import processing.core.*; 

public class game_options extends PApplet {


	private static final long serialVersionUID = 1L;
Cube stage; // external large cube
int noties = 50;
Note[]n = new Note[noties];
PImage bg;
PImage logo;
Option o1,o2,o3,o4,o5;
boolean selection;
PImage icon1,icon2,icon3,icon4,icon5;

// Controls notie's movement
float[]x = new float[noties];
float[]y = new float[noties];
float[]z = new float[noties];
float[]xSpeed = new float[noties];
float[]ySpeed = new float[noties];
float[]zSpeed = new float[noties];

// Controls notie's rotation
float[]xRot = new float[noties];
float[]yRot = new float[noties];
float[]zRot = new float[noties];

// Size of external cube
float bounds = 640;

public void setup() {
  size(800, 600, P3D);
  bg = loadImage("blackground.jpg");
  logo = loadImage("gameoptions.png");
  icon1 = loadImage("player1.png");
  icon2 = loadImage("player2.png");
  icon3 = loadImage("player3.png");
  icon4 = loadImage("player4.png");
  icon5 = loadImage("player1.png");

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
  // Instantiate external large cube
  stage =  new Cube(bounds, bounds, bounds);
  
  //instantiate player icons
  o1 = new Option(1,width/11,width/11,width/12,(height/5+height/2),selection);
  o1.selectOption();
  
  o2 = new Option(2,width/11,width/11,width/4+width/40,(height/5+height/2),selection);
  o2.selectOption();
  
  o3 = new Option(3,width/11,width/11,width/2-width/32,(height/5+height/2),selection);
  o3.selectOption();
  
  o4 = new Option(4,width/11,width/11,width/2+width/7,(height/5+height/2),selection);
  o4.selectOption();
  
  o5 = new Option(5, width/11,width/11,width/2+width/3,(height/5+height/2),selection);
  o5.selectOption();
}

public void draw()
{
  image(bg,0,0,width,height);
  image(logo,width/4,height/12,width/2,height/3);
  lights();
  o1.display(icon1);
  o2.display(icon2);
  o3.display(icon3);
  o4.display(icon4);
  o5.display(icon5);
  
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

    // Draw lines connecting cubbies
   /* stroke(0);
    if (i < cubies-1){
      line(x[i], y[i], z[i], x[i+1], y[i+1], z[i+1]);
    }*/

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
    fill(255,50);
    ellipseMode(CENTER);
    ellipse(x,y,w,h);
    rect(x+w/2-w/8,y,w/11,2*h);
  }
}
class Option
{
  int optionIndex;
  float xPos;
  float yPos;
  float height;
  float width;
  boolean selected;
  PFont font = loadFont("Helvetica-32.vlw");
  
  Option(int o, int h, int w, int x, int y, boolean sel)
  {
    optionIndex = o;
    height = h;
    width = w;
    xPos = x;
    yPos = y; 
    selected = sel;
  }
  
  public void selectOption()
  {
    selected = true;
  }
  
  public void unselectOption()
  {
    selected = false;
  }
  
  public void display(PImage icon)
  {
    //tint the icon depending on player connection
    if(selected == true){tint(255);}
    if(selected == false){tint(0,40);}
    
    if(optionIndex == 1)
    {
      textFont(font);
      fill(255);
      text("Key",xPos+xPos/7,yPos-height);
      image(icon,xPos,yPos,width,height);
    }
    if(optionIndex == 2)
    {
      textFont(font);
      fill(255);
      text("Time",xPos,yPos-height);
      image(icon,xPos,yPos,width,height);
    }
    if(optionIndex == 3)
    {
      textFont(font);
      fill(255);
      text("Tempo",xPos-xPos/26,yPos-height);
      image(icon,xPos,yPos,width,height);
    }
    if(optionIndex == 4)
    {
      textFont(font);
      fill(255);
      text("Bars",xPos,yPos-height);
      image(icon,xPos,yPos,width,height);
    }
    if(optionIndex == 5)
    {
      textFont(font);
      fill(255);
      text("Time Limit",xPos-xPos/22,yPos-height);
      image(icon,xPos,yPos,width,height);
    }
    tint(255);
  }
}


  static public void main(String args[]) {
    PApplet.main(new String[] { "--present", "--bgcolor=#666666", "--stop-color=#cccccc", "displayManager.messageTranslationSystem.game_options" });
  }
}
