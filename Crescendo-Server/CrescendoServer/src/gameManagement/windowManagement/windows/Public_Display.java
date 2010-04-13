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

public class Public_Display extends PApplet {

int num_of_players;
staff[] staffs;
staff cap,cap2,cap3,cap4;
ArrayList notes;
PImage bg;
PImage[] imgList = new PImage[4];
draw_note n;

public void setup()
{
  size(1280,1024);
  smooth();
  bg = loadImage("blackground.jpg");
  image(bg,0,0,width,height);
  imgList[0] = loadImage("capsule1.png");
  imgList[1] = loadImage("capsule2.png");
  imgList[2] = loadImage("capsule3.png");
  imgList[3] = loadImage("capsule4.png");
  //n = new draw_note(width/2,height/3,width/40,width/40,'q');
  setActivePlayers(4);
  staffs = new staff[num_of_players];

  if(num_of_players == 1)
  {
    cap = new staff(1,height/3+height/12,width-100,50,height/4,notes);
    staffs[0] = cap;
  }
  if(num_of_players == 2)
  {
    cap = new staff(1,height/3,width-100,50,height/4-height/5,notes);
    cap2 = new staff(2,height/3,width-100,50,(2*height/4),notes);
    staffs[0] = cap;
    staffs[1] = cap2;
  }
  if(num_of_players == 3)
  {
    cap = new staff(1,height/4,width-100,50,height/4-height/5,notes);
    cap2 = new staff(2,height/4,width-100,50,height/4+height/12,notes);
    cap3 = new staff(3,height/4,width-100,50,height/2+height/8,notes);
    staffs[0] = cap;
    staffs[1] = cap2;
    staffs[2] = cap3;
  }
  if(num_of_players == 4)
  {
    cap = new staff(1,height/4,width-100,50,height/50,notes);
    cap2 = new staff(2,height/4,width-100,50,height/4+height/60,notes);
    cap3 = new staff(3,height/4,width-100,50,height/2+height/80,notes);
    cap4 = new staff(4,height/4,width-100,50,height/2+height/4,notes);
    staffs[0] = cap;
    staffs[1] = cap2;
    staffs[2] = cap3;
    staffs[3] = cap4;
  }
}

public void draw()
{
  for(int i = 0; i < staffs.length; i++)
  {
    staffs[i].display(imgList[i]);
  }
}

public void setActivePlayers(int activePlayers)
{
  num_of_players = activePlayers;
}


class draw_note
{
  float x,y,w,h;
  char type;
  
  //default constructor
  draw_note(){}
  
  draw_note(float x, float y, float w, float h, char t)
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
class staff
{
  int playerIndex;
  float xPos;
  float yPos;
  int c;
  float height;
  float width;
  int deltaX;
  ArrayList notes = new ArrayList(); 
  
  staff(int p, int h, int w, int x, int y, ArrayList tempNotes)
  {
    playerIndex = p;
    height = h;
    width = w;
    xPos = x;
    yPos = y; 
    notes = tempNotes;
  }

  public void addNote(draw_note n)
  {
    notes.add(n);
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
  public void setTempo(int tempo)
  {
    this.deltaX = tempo;
  }
 
 // have the public display call this every time a beat goes by.
  public void scrollNotes()
  {
    int listSize = notes.size();
    for (int i = 0; i < listSize; ++i)
    {
      draw_note note = (draw_note)notes.get(i);
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

    //now draw the holding rectangle for the staff lines
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

  static public void main(String args[]) {
    PApplet.main(new String[] { "--present", "--bgcolor=#666666", "--stop-color=#cccccc", "gameManagement.windowManagement.windows.Public_Display" });
  }
}
