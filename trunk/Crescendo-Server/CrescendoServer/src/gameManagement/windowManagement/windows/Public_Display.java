package gameManagement.windowManagement.windows;
import processing.core.*; 
import processing.xml.*; 

import gameManagement.messageTranslationSystem.Note;

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

int num_of_players = 1;
staff[] staffList = new staff[4];
staff cap;
staff cap2;
ArrayList<Note> notes;
PImage logo;
PImage bg;


public void setup()
{
  size(1280,1024);
  smooth();
  bg = loadImage("blackground.jpg");
  image(bg,0,0,width,height);
  logo = loadImage("crescendo.png");
  image(logo,300,30,width/2,height/4);
  cap = new staff(1,height/3,width-100,50,height/3,notes);
  cap2 = new staff(2,height/3,width-100,50,(2*height/3),notes);
}

public void draw()
{
 cap.display(); 
 cap2.display();
}
class staff
{
  int playerIndex;
  float xPos;
  float yPos;
  int c;
  float height;
  float width;
  ArrayList<Note> notes = new ArrayList<Note>();
  
  staff(int p, int h, int w, int x, int y, ArrayList<Note> tempNotes)
  {
    playerIndex = p;
    height = h;
    width = w;
    xPos = x;
    yPos = y; 
    notes = tempNotes;
  }
 
  void addNote(Note n)
  {
    notes.add(n);
  }
  
  void addNotes(ArrayList<Note> noteList)
  {
    for(Note n : noteList)
    {
      addNote(n);
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
  
  public void display()
  {
    //draw the capsule container first
    if(playerIndex == 1)
    {
    PImage staff = loadImage("capsule1.png");
    image(staff,xPos,yPos,width,height);
    }
    if(playerIndex == 2)
    {
      PImage staff = loadImage("capsule2.png");
      image(staff,xPos,yPos,width,height);
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
    PApplet.main(new String[] { "--present", "--bgcolor=#666666", "--stop-color=#cccccc", "gameManagement.messageTranslationSystem.Public_Display" });
  }
}
