package gameManagement.messageTranslationSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import keys.GameState;

public class CHIPhoneGUI extends JPanel implements ActionListener, MouseListener, ChangeListener, KeyListener
{
	private static final long serialVersionUID = -3339327851854047338L;
	
	private String player;
	private gameManagement.messageTranslationSystem.ConnectionManager myCM;
	
	private Random rand;

	private BufferedImage background_vertical = null;
	private BufferedImage menu_button_up = null;
	private BufferedImage menu_button_down = null;
	private BufferedImage back_button_up = null;
	private BufferedImage back_button_down = null;
	private BufferedImage help_button_up = null;
	private BufferedImage help_button_down = null;
	private BufferedImage volume_icon = null;
	private BufferedImage compose_background_red = null;
	private BufferedImage compose_background_green = null;
	private BufferedImage compose_background_blue = null;
	private BufferedImage compose_background_orange = null;

	private BufferedImage connect = null;
	private Rectangle2D connectBox = null;
	
	private BufferedImage disconnect = null;
	private Rectangle2D disconnectBox = null;

	private BufferedImage start = null;
	private Rectangle2D startBox = null;

	private BufferedImage back = null;
	private Rectangle2D backBox = null;

	private BufferedImage play = null;
	private Rectangle2D playBox = null;
	
	private BufferedImage help = null;
	private Rectangle2D helpBox = null;
	
	private BufferedImage pause = null;
	private Rectangle2D pauseBox = null;

	private BufferedImage playSong = null;
	private Rectangle2D playSongBox = null;
	
	private boolean at_connect_screen;
	private boolean at_help_screen;
	private boolean at_splash_screen;
	private boolean at_game_options_screen;
	private boolean at_pause_screen;
	private boolean at_play_screen;
	
	private double background_y;
	
	private String previousState;
	
	private JSlider keySlider;
	private JSlider timeSlider;
	private JSlider tempoSlider;
	private JSlider barsSlider;
	
	private String key;
	private String time;
	private String tempo;
	private String bars;
	
	public CHIPhoneGUI(String player, gameManagement.messageTranslationSystem.ConnectionManager myCM)
	{
		super(true);
		
        this.setPreferredSize(new Dimension(320,480));
		this.setVisible(true);
		this.setLayout(null);
	
		this.player = player;
		this.myCM = myCM;
		rand = new Random();
		
		at_connect_screen = true;
		at_help_screen = false;
		at_splash_screen = false;
		at_game_options_screen = false;
		at_play_screen = false;
		
		background_y = 500;
		
		previousState = "at_connect_screen";
		
		try
		{
			background_vertical = ImageIO.read(new File("background_vertical.png"));
			menu_button_up = ImageIO.read(new File("menu_button_up.png"));
			menu_button_down = ImageIO.read(new File("menu_button_down.png"));
			back_button_up = ImageIO.read(new File("back_button_up.png"));
			back_button_down = ImageIO.read(new File("back_button_down.png"));
			help_button_up = ImageIO.read(new File("help_button_up.png"));
			help_button_down = ImageIO.read(new File("help_button_down.png"));
			volume_icon = ImageIO.read(new File("volume_icon.png"));
			compose_background_red = ImageIO.read(new File("compose_background_red.png"));
			compose_background_green = ImageIO.read(new File("compose_background_green.png"));
			compose_background_blue = ImageIO.read(new File("compose_background_blue.png"));
			compose_background_orange = ImageIO.read(new File("compose_background_orange.png"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		this.addMouseListener(this);
		
		keySlider = new JSlider(SwingConstants.VERTICAL, 0, 11, 4);
		timeSlider = new JSlider(SwingConstants.VERTICAL, 2, 4, 4);
		tempoSlider = new JSlider(SwingConstants.VERTICAL);
		barsSlider = new JSlider(SwingConstants.VERTICAL);
		//TODO add tempo and bars stuff
		
		keySlider.setOpaque(false);
		timeSlider.setOpaque(false);
		tempoSlider.setOpaque(false);
		barsSlider.setOpaque(false);

		keySlider.setSnapToTicks(true);
		timeSlider.setSnapToTicks(true);
		tempoSlider.setSnapToTicks(true);
		barsSlider.setSnapToTicks(true);
		
		key = new String("C");
		time = new String("4/4");
		tempo = new String("120");
		bars = new String("16");
		
		keySlider.addChangeListener(this);
		timeSlider.addChangeListener(this);
		tempoSlider.addChangeListener(this);
		barsSlider.addChangeListener(this);
		
		this.add(keySlider);
		this.add(timeSlider);
		this.add(tempoSlider);
		this.add(barsSlider);
		
//		disconnect = new JButton("Disconnect");
//		disconnect.addActionListener(this);
//		this.add(disconnect);
//		
//		connect = new JButton("Connect");
//		connect.addActionListener(this);
//		this.add(connect);
//				
//		splashScreen = new JButton("Go to Splash Screen State");
//		splashScreen.addActionListener(this);
//		this.add(splashScreen);
//
//		gameOptions = new JButton("Got to Game Options State");
//		gameOptions.addActionListener(this);
//		this.add(gameOptions);
//
//		setTempo = new JButton("Set Tempo (Random)");
//		setTempo.addActionListener(this);
//		this.add(setTempo);
//
//		setKey = new JButton("Set Key (Random)");
//		setKey.addActionListener(this);
//		this.add(setKey);
//
//		setTimeSignature = new JButton("Set Time Signature (Random)");
//		setTimeSignature.addActionListener(this);
//		this.add(setTimeSignature);
//		
//		setNumberOfBars = new JButton("Set Number of Bars (Random)");
//		setNumberOfBars.addActionListener(this);
//		this.add(setNumberOfBars);
//
//		play = new JButton("Go to Play State");
//		play.addActionListener(this);
//		this.add(play);
//
//		pause = new JButton("Go to Pause State");
//		pause.addActionListener(this);
//		this.add(pause);
//		
//		playNote = new JButton("Play a Random Note");
//		playNote.addActionListener(this);
//		this.add(playNote);
//
//		review = new JButton("Review Game");
//		review.addActionListener(this);
//		this.add(review);
	}

	public void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		
		//this is a vertical screen
		g.drawImage(background_vertical, null, 0, 0);
		
		if(at_connect_screen)
		{				
			if(previousState.equals("at_help_screen") && background_y < 480)
			{
				/* This is the back command from the help screen */
				///// Draw static current screen /////
					//this is a vertical screen
					g.drawImage(background_vertical, null, 0, 0);
					
					//draw connect button
					if(connect == null)
						connect = menu_button_up;
					connectBox = new Rectangle2D.Double(50,210,220,50);
					g.drawImage(connect, null, 50, 210);
					g.drawString("connect", 128, 240);
					
					//draw help button
					if(help == null)
						help = help_button_up;
					helpBox = new Rectangle2D.Double(260,10,50,50);
					g.drawImage(help, null, 260, 10);
				//////////////////////////////////////
				
				///// Draw dynamic previous screen /////
					//this is a vertical screen
					g.drawImage(background_vertical, null, 0, (int)background_y);
					
					//draw back button
					g.drawImage(back, null, 20, (int)(background_y + 20));
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
					g.drawString("back", 34, (int)(background_y + 39));
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
					
					if(background_y < 480)
					{
						background_y += .5;
						repaint();
					}
					else
					{
						background_y = 480;
					}	
				////////////////////////////////////
			}
			else if(previousState.equals("at_splash_screen") && background_y < 480)
			{
				/* This is the back command from the splash screen*/
				///// Draw static current screen /////
					//this is a vertical screen
					g.drawImage(background_vertical, null, 0, 0);
					
					//draw connect button
					if(connect == null)
						connect = menu_button_up;
					connectBox = new Rectangle2D.Double(50,210,220,50);
					g.drawImage(connect, null, 50, 210);
					g.drawString("connect", 128, 240);
					
					//draw help button
					if(help == null)
						help = help_button_up;
					helpBox = new Rectangle2D.Double(260,10,50,50);
					g.drawImage(help, null, 260, 10);
				//////////////////////////////////////
				
				///// Draw dynamic previous screen /////
					//this is a vertical screen
					g.drawImage(background_vertical, null, 0, (int)background_y);
					
					//draw back button
					g.drawImage(back, null, 20, (int)(background_y + 20));
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
					g.drawString("back", 34, (int)(background_y + 39));
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
	
					//draw start button
					g.drawImage(start, null, 50, (int)background_y + 210);
					g.drawString("start", 142, (int)background_y + 240);
					
					//draw connect button
					g.drawImage(disconnect, null, 50, (int)background_y + 275);
					g.drawString("disconnect", 116, (int)background_y + 305);
					
					if(background_y < 480)
					{
						background_y += .5;
						repaint();
					}
					else
					{
						background_y = 480;
					}	
				////////////////////////////////////
			}
			else if(previousState.equals("at_pause_screen") && background_y < 480)
			{
				/* This is the back command from the splash screen*/
				///// Draw static current screen /////
					//this is a vertical screen
					g.drawImage(background_vertical, null, 0, 0);
					
					//draw connect button
					if(connect == null)
						connect = menu_button_up;
					connectBox = new Rectangle2D.Double(50,210,220,50);
					g.drawImage(connect, null, 50, 210);
					g.drawString("connect", 128, 240);
					
					//draw help button
					if(help == null)
						help = help_button_up;
					helpBox = new Rectangle2D.Double(260,10,50,50);
					g.drawImage(help, null, 260, 10);
				//////////////////////////////////////
				
				///// Draw dynamic previous screen /////
					//this is a vertical screen
					g.drawImage(background_vertical, null, 0, (int)background_y + 0);

					//draw "game options" text
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
					g.drawString("game options", 125, (int)background_y + 178);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		
					//draw start button
					if(pause == null)
						pause = menu_button_up;
					pauseBox = new Rectangle2D.Double(50,(int)background_y + 210,220,50);	
					g.drawImage(pause, null, 50, (int)background_y + 210);
					g.drawString("pause", 135, (int)background_y + 240);
					
					//draw play song button
					if(playSong == null)
						playSong = menu_button_up;
					playSongBox = new Rectangle2D.Double(50,(int)background_y + 275,220,50);	
					g.drawImage(playSong, null, 50, (int)background_y + 275);
					g.drawString("play song", 121, (int)background_y + 305);
					
					//draw disconnect button
					if(disconnect == null)
						disconnect = menu_button_up;
					disconnectBox = new Rectangle2D.Double(50,(int)background_y + 340,220,50);	
					g.drawImage(disconnect, null, 50, (int)background_y + 340);
					g.drawString("disconnect", 116, (int)background_y + 370);
					
					if(background_y < 480)
					{
						background_y += .5;
						repaint();
					}
					else
					{
						background_y = 480;
					}	
				////////////////////////////////////
			}
			else
			{
				/* This is the current screen */
				///// Draw static current screen /////
					//this is a vertical screen
					g.drawImage(background_vertical, null, 0, 0);
					
					//draw connect button
					if(connect == null)
						connect = menu_button_up;
					connectBox = new Rectangle2D.Double(50,210,220,50);
					g.drawImage(connect, null, 50, 210);
					g.drawString("connect", 128, 240);
					
					//draw help button
					if(help == null)
						help = help_button_up;
					helpBox = new Rectangle2D.Double(260,10,50,50);
					g.drawImage(help, null, 260, 10);
					previousState = "at_connect_screen";
					System.out.println(previousState);
				//////////////////////////////////////
			}
		}
		else if(at_help_screen)
		{		
			if(previousState.equals("at_connect_screen") && background_y > 0)
			{	
				/* This is the next command from the connect screen */	
				///// Draw static previous screen /////
					//this is a vertical screen
					g.drawImage(background_vertical, null, 0, 0);
					
					//draw connect button
					if(connect == null)
						connect = menu_button_up;
					connectBox = new Rectangle2D.Double(50,210,220,50);
					g.drawImage(connect, null, 50, 210);
					g.drawString("connect", 128, 240);
					
					//draw help button
					if(help == null)
						help = help_button_up;
					helpBox = new Rectangle2D.Double(260,10,50,50);
					g.drawImage(help, null, 260, 10);
				///////////////////////////////////////

				///// Draw dynamic current screen /////
					if(back == null)
						back = back_button_up;
					backBox = new Rectangle2D.Double(20,20,51,(int)background_y + 31);	
					
					//this is a vertical screen
					g.drawImage(background_vertical, null, 0, (int)background_y);
					
					//draw back button
					g.drawImage(back, null, 20, (int)background_y + 20);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
					g.drawString("back", 34, (int)background_y + 39);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
	
					if(background_y > 0)
					{
						background_y -= .5;
						repaint();
					}
					else
					{
						background_y = 0;
					}	
				///////////////////////////////////////
			}
			else
			{
				/* This is the current screen */
				///// Draw static current screen /////
					//this is a vertical screen
					g.drawImage(background_vertical, null, 0, 0);
					
					//draw back button
					if(back == null)
						back = back_button_up;
					backBox = new Rectangle2D.Double(20,20,51,31);	
					g.drawImage(back, null, 20, 20);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
					g.drawString("back", 34, 39);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
					
					previousState = "at_help_screen";
					System.out.println(previousState);
				//////////////////////////////////////
			}
		}
		else if(at_splash_screen)
		{	
			if(previousState.equals("at_game_options_screen") && background_y < 480)
			{
				/* This is the back command from the game options screen*/
				///// Draw static current screen /////
					//this is a vertical screen
					g.drawImage(background_vertical, null, 0, 0);
					
					//draw back button
					if(back == null)
						back = back_button_up;
					backBox = new Rectangle2D.Double(20,20,51,31);
					g.drawImage(back, null, 20, 20);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
					g.drawString("back", 34, 39);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		
					//draw start button
					if(start == null)
						start = menu_button_up;
					startBox = new Rectangle2D.Double(50,210,220,50);	
					g.drawImage(start, null, 50, 210);
					g.drawString("start", 142, 240);
					
					//draw disconnect button
					if(disconnect == null)
						disconnect = menu_button_up;
					disconnectBox = new Rectangle2D.Double(50,275,220,50);	
					g.drawImage(disconnect, null, 50, 275);
					g.drawString("disconnect", 116, 305);
				//////////////////////////////////////
				
				///// Draw dynamic previous screen /////
					//this is a vertical screen
					g.drawImage(background_vertical, null, 0, (int)background_y);
					
					//draw back button
					g.drawImage(back, null, 20, (int)background_y + 20);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
					g.drawString("back", 34, (int)background_y + 39);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));

					//draw "game play options" text
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
					g.drawString("game play options", 110, (int)background_y + 178);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
					
					//game option labels
					g.drawString("Key", 25, (int)background_y + 200);
					g.drawString("Time", 95, (int)background_y + 200);
					g.drawString("Tempo", 170, (int)background_y + 200);
					g.drawString("Bars", 260, (int)background_y + 200);
					
					//game option labels
					g.drawString(key, 25, (int)background_y + 225);
					g.drawString(time, 95, (int)background_y + 225);
					g.drawString(tempo, 170, (int)background_y + 225);
					g.drawString(bars, 260, (int)background_y + 225);
					
					//game option sliders
					//JSlider.setBounds(x, y, width, height);
					keySlider.setBounds(20, (int)background_y + 250, 40, 135);
					timeSlider.setBounds(100, (int)background_y + 250, 40, 135);
					tempoSlider.setBounds(180, (int)background_y + 250, 40, 135);
					barsSlider.setBounds(260, (int)background_y + 250, 40, 135);
					
					//draw play button
					g.drawImage(play, null, 50, (int)background_y + 400);
					g.drawString("play", 142, (int)background_y + 430);
					
					if(background_y < 480)
					{
						background_y += .5;
						repaint();
					}
					else
					{
						background_y = 480;
					}	
				////////////////////////////////////////
			}
			else if(previousState.equals("at_connect_screen") && background_y > 0)
			{
				/* This is the next command from the connect screen */	
				///// Draw static previous screen /////
					//this is a vertical screen
					g.drawImage(background_vertical, null, 0, 0);
					
					//draw connect button
					if(connect == null)
						connect = menu_button_up;
					connectBox = new Rectangle2D.Double(50,210,220,50);
					g.drawImage(connect, null, 50, 210);
					g.drawString("connect", 128, 240);
					
					//draw help button
					if(help == null)
						help = help_button_up;
					helpBox = new Rectangle2D.Double(260,10,50,50);
					g.drawImage(help, null, 260, 10);				
				///////////////////////////////////////
				
				///// Draw dynamic current screen /////
					//this is a vertical screen
					g.drawImage(background_vertical, null, 0, (int)background_y + 0);
					
					//draw back button
					if(back == null)
						back = back_button_up;
					backBox = new Rectangle2D.Double(20,20,51,(int)background_y + 31);
					g.drawImage(back, null, 20, (int)background_y + 20);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
					g.drawString("back", 34, (int)background_y + 39);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		
					//draw start button
					if(start == null)
						start = menu_button_up;
					startBox = new Rectangle2D.Double(50,210,220,(int)background_y + 50);	
					g.drawImage(start, null, 50, (int)background_y + 210);
					g.drawString("start", 142, (int)background_y + 240);
					
					//draw disconnect button
					if(disconnect == null)
						disconnect = menu_button_up;
					disconnectBox = new Rectangle2D.Double(50,275,220,(int)background_y + 50);	
					g.drawImage(disconnect, null, 50, (int)background_y + 275);
					g.drawString("disconnect", 116, (int)background_y + 305);

					if(background_y > 0)
					{
						background_y -= .5;
						repaint();
					}
					else
					{
						background_y = 0;
					}	
				///////////////////////////////////////
			}
			else
			{
				/* This is the current screen */
				///// Draw static current screen /////		
					//this is a vertical screen
					g.drawImage(background_vertical, null, 0, 0);
					
					//draw back button
					if(back == null)
						back = back_button_up;
					backBox = new Rectangle2D.Double(20,20,51,31);
					g.drawImage(back, null, 20, 20);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
					g.drawString("back", 34, 39);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		
					//draw start button
					if(start == null)
						start = menu_button_up;
					startBox = new Rectangle2D.Double(50,210,220,50);	
					g.drawImage(start, null, 50, 210);
					g.drawString("start", 142, 240);
					
					//draw disconnect button
					if(disconnect == null)
						disconnect = menu_button_up;
					disconnectBox = new Rectangle2D.Double(50,275,220,50);	
					g.drawImage(disconnect, null, 50, 275);
					g.drawString("disconnect", 116, 305);
					previousState = "at_splash_screen";
					System.out.println(previousState);
				//////////////////////////////////////
			}
		}
		else if(at_game_options_screen)
		{	
			if(previousState.equals("at_splash_screen") && background_y > 0)
			{
				/* This is the next command from the splash screen */	
				///// Draw static previous screen /////
					//this is a vertical screen
					g.drawImage(background_vertical, null, 0, 0);
					
					//draw back button
					if(back == null)
						back = back_button_up;
					backBox = new Rectangle2D.Double(20,20,51,31);
					g.drawImage(back, null, 20, 20);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
					g.drawString("back", 34, 39);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		
					//draw start button
					if(start == null)
						start = menu_button_up;
					startBox = new Rectangle2D.Double(50,210,220,50);	
					g.drawImage(start, null, 50, 210);
					g.drawString("start", 142, 240);
					
					//draw disconnect button
					if(disconnect == null)
						disconnect = menu_button_up;
					disconnectBox = new Rectangle2D.Double(50,275,220,50);	
					g.drawImage(disconnect, null, 50, 275);
					g.drawString("disconnect", 116, 305);
				///////////////////////////////////////
				
				///// Draw dynamic current screen /////
					//this is a vertical screen
					g.drawImage(background_vertical, null, 0, (int)background_y);
					
					//draw back button
					g.drawImage(back, null, 20, (int)background_y + 20);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
					g.drawString("back", 34, (int)background_y + 39);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));

					//draw "game play options" text
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
					g.drawString("game play options", 110, (int)background_y + 178);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
					
					//game option labels
					g.drawString("Key", 25, (int)background_y + 200);
					g.drawString("Time", 95, (int)background_y + 200);
					g.drawString("Tempo", 170, (int)background_y + 200);
					g.drawString("Bars", 260, (int)background_y + 200);
					
					//game option labels
					g.drawString(key, 25, (int)background_y + 225);
					g.drawString(time, 95, (int)background_y + 225);
					g.drawString(tempo, 170, (int)background_y + 225);
					g.drawString(bars, 260, (int)background_y + 225);
					
					//game option sliders
					//JSlider.setBounds(x, y, width, height);
					keySlider.setBounds(20, (int)background_y + 250, 40, 135);
					timeSlider.setBounds(100, (int)background_y + 250, 40, 135);
					tempoSlider.setBounds(180, (int)background_y + 250, 40, 135);
					barsSlider.setBounds(260, (int)background_y + 250, 40, 135);
					
					//draw play button
					g.drawImage(play, null, 50, (int)background_y + 400);
					g.drawString("play", 142, (int)background_y + 430);
					
					if(background_y > 0)
					{
						background_y -= .5;
						repaint();
					}
					else
					{
						background_y = 480;
					}	
				///////////////////////////////////////
			}
			else
			{
				/* This is the current screen */
				///// Draw static current screen /////								
					//this is a vertical screen
					g.drawImage(background_vertical, null, 0, 0);
					
					//draw back button
					if(back == null)
						back = back_button_up;
					backBox = new Rectangle2D.Double(20,20,51,31);	
					g.drawImage(back, null, 20, 20);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
					g.drawString("back", 34, 39);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
	
					//draw "game play options" text
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
					g.drawString("game play options", 110, 178);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
					
					//game option labels
					g.drawString("Key", 25, 200);
					g.drawString("Time", 95, 200);
					g.drawString("Tempo", 170, 200);
					g.drawString("Bars", 260, 200);
					
					//game option labels
					g.drawString(key, 25, 225);
					g.drawString(time, 95, 225);
					g.drawString(tempo, 170, 225);
					g.drawString(bars, 260, 225);
					
					//game option sliders
					//JSlider.setBounds(x, y, width, height);
					keySlider.setBounds(20, 250, 40, 135);
					timeSlider.setBounds(100, 250, 40, 135);
					tempoSlider.setBounds(180, 250, 40, 135);
					barsSlider.setBounds(260, 250, 40, 135);
					
					//draw play button
					if(play == null)
						play = menu_button_up;
					playBox = new Rectangle2D.Double(50,415,220,50);	
					g.drawImage(play, null, 50, 400);
					g.drawString("play", 142, 430);
					previousState = "at_game_options_screen";
					System.out.println(previousState);
				//////////////////////////////////////
			}
		}
		else if(at_pause_screen)
		{	
			if(previousState.equals("at_game_options_screen") && background_y > 0)
			{
				/* This is the next command from the game options screen */	
				///// Draw static previous screen /////
					//this is a vertical screen
					g.drawImage(background_vertical, null, 0, 0);
					
					//draw back button
					if(back == null)
						back = back_button_up;
					backBox = new Rectangle2D.Double(20,20,51,31);	
					g.drawImage(back, null, 20, 20);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
					g.drawString("back", 34, 39);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
	
					//draw "game play options" text
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
					g.drawString("game play options", 110, 178);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
					
					//game option labels
					g.drawString("Key", 25, 200);
					g.drawString("Time", 95, 200);
					g.drawString("Tempo", 170, 200);
					g.drawString("Bars", 260, 200);
					
					//game option labels
					g.drawString(key, 25, 225);
					g.drawString(time, 95, 225);
					g.drawString(tempo, 170, 225);
					g.drawString(bars, 260, 225);
					
					//game option sliders
					//JSlider.setBounds(x, y, width, height);
					keySlider.setBounds(20, 250, 40, 135);
					timeSlider.setBounds(100, 250, 40, 135);
					tempoSlider.setBounds(180, 250, 40, 135);
					barsSlider.setBounds(260, 250, 40, 135);
					
					//draw play button
					if(play == null)
						play = menu_button_up;
					playBox = new Rectangle2D.Double(50,415,220,50);	
					g.drawImage(play, null, 50, 400);
					g.drawString("play", 142, 430);
				///////////////////////////////////////
				
				///// Draw dynamic current screen /////	
					//this is a vertical screen
					g.drawImage(background_vertical, null, 0, (int)background_y);

					//draw "game options" text
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
					g.drawString("game options", 125, (int)background_y + 178);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
					
					//draw start button
					if(pause == null)
						pause = menu_button_up;
					pauseBox = new Rectangle2D.Double(50,(int)background_y + 210,220,50);	
					g.drawImage(pause, null, 50, (int)background_y + 210);
					g.drawString("pause", 135, (int)background_y + 240);
					
					//draw play song button
					if(playSong == null)
						playSong = menu_button_up;
					playSongBox = new Rectangle2D.Double(50,(int)background_y + 275,220,50);	
					g.drawImage(playSong, null, 50, (int)background_y + 275);
					g.drawString("play song", 121, (int)background_y + 305);
					
					//draw disconnect button
					if(disconnect == null)
						disconnect = menu_button_up;
					disconnectBox = new Rectangle2D.Double(50,(int)background_y + 340,220,50);	
					g.drawImage(disconnect, null, 50, (int)background_y + 340);
					g.drawString("disconnect", 116, (int)background_y + 370);
					
					if(background_y > 0)
					{
						background_y -= .5;
						repaint();
					}
					else
					{
						background_y = 0;
					}	
				///////////////////////////////////////
			}
			else
			{
				/* This is the current screen */
				///// Draw static current screen /////		
					//this is a vertical screen
					g.drawImage(background_vertical, null, 0, 0);

					//draw "game options" text
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
					g.drawString("game options", 125, 178);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		
					//draw start button
					if(pause == null)
						pause = menu_button_up;
					pauseBox = new Rectangle2D.Double(50,210,220,50);	
					g.drawImage(pause, null, 50, 210);
					g.drawString("pause", 135, 240);
					
					//draw play song button
					if(playSong == null)
						playSong = menu_button_up;
					playSongBox = new Rectangle2D.Double(50,275,220,50);	
					g.drawImage(playSong, null, 50, 275);
					g.drawString("play song", 121, 305);
					
					//draw disconnect button
					if(disconnect == null)
						disconnect = menu_button_up;
					disconnectBox = new Rectangle2D.Double(50,340,220,50);	
					g.drawImage(disconnect, null, 50, 340);
					g.drawString("disconnect", 116, 370);
					
					previousState = "at_pause_screen";
					System.out.println(previousState);
				//////////////////////////////////////
			}
		}
		else if(at_play_screen)
		{				

		}
	}
	
	private void clearAllBoundingBoxes()
	{
		connectBox = null;
		helpBox = null;
		backBox = null;
		startBox = null;
		disconnectBox = null;
		playBox = null;
	}
	
	public void actionPerformed(ActionEvent e)
	{
//		if(e.getSource() == connect)
//			myCM.sendMessage(player + "_" + GameState.CONNECT);
//		if(e.getSource() == disconnect)
//			myCM.sendMessage(player + "_" + GameState.DISCONNECT);
//		if(e.getSource() == playNote)
//			myCM.sendMessage(player + "_" + generatePitch() + "_" + generateLength());
//		if(e.getSource() == splashScreen)
//			myCM.sendMessage(player + "_" + GameState.SPLASH_SCREEN);
//		if(e.getSource() == gameOptions)
//			myCM.sendMessage(player + "_" + GameState.GAME_OPTIONS);
//		if(e.getSource() == setTempo)
//			myCM.sendMessage(player + "_" + GameState.SET_TEMPO + "_" + generateTempo());
//		if(e.getSource() == setKey)
//			myCM.sendMessage(player + "_" + GameState.SET_KEY + "_" + generateKey());
//		if(e.getSource() == setTimeSignature)
//			myCM.sendMessage(player + "_" + GameState.SET_TIME_SIGNATURE + "_" + generateTimeSignatureNumerator()+ "/4");
//		if(e.getSource() == setNumberOfBars)
//			myCM.sendMessage(player + "_" + GameState.SET_NUMBER_OF_BARS + "_" + generateNumberOfBars());
//		if(e.getSource() == play)
//			myCM.sendMessage(player + "_" + GameState.PLAY);
//		if(e.getSource() == pause)
//			myCM.sendMessage(player + "_" + GameState.PAUSE);
//		if(e.getSource() == review)
//			myCM.sendMessage(player + "_" + GameState.PLAY_SONG);
	}

	/**
	 *	Generates a random Note pitch to be used in a message sent to the server
	 *
	 *	@return a random Note pitch to be used in a message sent to the server
	 */
	private String getPitch(int p)
	{
		switch(p%16)
		{
			case 0:
				return "E5";
			case 1:
				return "F5";
			case 2:
				return "FSharp5";
			case 3:
				return "G5";
			case 4:
				return "GSharp5";
			case 5:
				return "A5";
			case 6:
				return "ASharp5";
			case 7:
				return "B5";
			case 8:
				return "C6";
			case 9:
				return "CSharp6";
			case 10:
				return "D6";
			case 11:
				return "DSharp6";
			case 12:
				return "E6";
			case 13:
				return "F6";
			case 14:
				return "FSharp6";
			default:
				return "rest";
		}
	}

	/**
	 *	Generates a random Note length to be used in a message sent to the server
	 *
	 *	@return a random Note length to be used in a message sent to the server
	 */
	private String generateLength()
	{
		switch(rand.nextInt(4))
		{
			case 0:
				return "eighthnote";
			case 1:
				return "quarternote";
			case 2:
				return "halfnote";
			default:
				return "wholenote";
		}
	}
	
	/**
	 *	Generates a random tempo from 60(Adagio) - 180(Presto)
	 *
	 *	@return a random tempo from 
	 */
	private String getKey(int k)
	{
		switch(k)
		{
			case 0:
				return "G";
			case 1:
				return "FSharp";
			case 2:
				return "F";
			case 3:
				return "EFlat";
			case 4:
				return "E";
			case 5:
				return "DFlat";
			case 6:
				return "D";
			case 7:
				return "C";
			case 8:
				return "BFlat";
			case 9:
				return "B";
			case 10:
				return "AFlat";
			default:
				return "A";
		}
	}
	
	/**
	 *	Generates a random tempo from 60(Adagio) - 180(Presto)
	 *
	 *	@return a random tempo from 
	 */
	private int generateTempo()
	{
		switch(rand.nextInt(9))
		{
			case 0:
				return 60;
			case 1:
				return 65;
			case 2:
				return 70;
			case 3:
				return 80;
			case 4:
				return 95;
			case 5:
				return 110;
			case 6:
				return 120;
			case 7:
				return 145;
			default:
				return 180;
		}
	}
	
	/**
	 *	Generates a random tempo from 60(Adagio) - 180(Presto)
	 *
	 *	@return a random tempo from 
	 */
	private int generateNumberOfBars()
	{
		return rand.nextInt(13)+4;
	}
	
	/**
	 *	Generates a random tempo from 60(Adagio) - 180(Presto)
	 *
	 *	@return a random tempo from 
	 */
	private int generateTimeSignatureNumerator()
	{
		return rand.nextInt(3)+2;
	}

	public void mouseClicked(MouseEvent e)
	{
		if((helpBox != null) && (helpBox.contains(e.getX(),e.getY())))
		{
			System.out.println("help " + rand.nextInt());
			clearAllBoundingBoxes();
			
			background_y = 480;
			
			at_connect_screen = false;
			at_help_screen = true;
		}
		else if((connectBox != null) && (connectBox.contains(e.getX(),e.getY())))
		{
			System.out.println("connect " + rand.nextInt());
			clearAllBoundingBoxes();
			
			background_y = 480;
			
			at_connect_screen = false;
			at_splash_screen = true;
		}
		else if((backBox != null) && (backBox.contains(e.getX(),e.getY())))
		{
			System.out.println("back " + rand.nextInt());
			clearAllBoundingBoxes();

			background_y = 0;
			
			if(previousState.equals("at_help_screen"))
			{
				at_help_screen = false;
				at_connect_screen = true;
			}
			else if(previousState.equals("at_splash_screen"))
			{
				at_splash_screen = false;
				at_connect_screen = true;
			}
			else if(previousState.equals("at_game_options_screen"))
			{
				at_game_options_screen = false;
				at_splash_screen = true;
			}
		}
		else if((startBox != null) && (startBox.contains(e.getX(),e.getY())))
		{
			System.out.println("start " + rand.nextInt());
			clearAllBoundingBoxes();
			
			background_y = 480;
			
			keySlider.setVisible(true);
			timeSlider.setVisible(true);
			tempoSlider.setVisible(true);
			barsSlider.setVisible(true);
			
			at_splash_screen = false;
			at_game_options_screen = true;
		}
		else if((disconnectBox != null) && (disconnectBox.contains(e.getX(),e.getY())))
		{
			System.out.println("disconnect " + rand.nextInt());
			clearAllBoundingBoxes();

			background_y = 0;
			
			at_splash_screen = false;
			at_connect_screen = true;
		}
		else if((playBox != null) && (playBox.contains(e.getX(),e.getY())))
		{
			System.out.println("play " + rand.nextInt());
			clearAllBoundingBoxes();
			
			background_y = 480;
			
			keySlider.setVisible(false);
			timeSlider.setVisible(false);
			tempoSlider.setVisible(false);
			barsSlider.setVisible(false);
			
			at_game_options_screen = false;
			at_pause_screen = true;
		}
	}

	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e)
	{
		if((connectBox != null) && (connectBox.contains(e.getX(),e.getY())))
			connect = menu_button_down;
		if((helpBox != null) && (helpBox.contains(e.getX(),e.getY())))
			help = help_button_down;
		if((backBox != null) && (backBox.contains(e.getX(),e.getY())))
			back = back_button_down;
		if((disconnectBox != null) && (disconnectBox.contains(e.getX(),e.getY())))
			disconnect = menu_button_down;
		if((startBox != null) && (startBox.contains(e.getX(),e.getY())))
			start = menu_button_down;
		if((playBox != null) && (playBox.contains(e.getX(),e.getY())))
			play = menu_button_down;
		
		this.repaint();
	}

	public void mouseReleased(MouseEvent e)
	{
		connect = menu_button_up;
		help = help_button_up;
		back = back_button_up;
		start = menu_button_up;
		disconnect = menu_button_up;
		play = menu_button_up;
		this.repaint();
	}

	public void stateChanged(ChangeEvent e)
	{
		if(e.getSource() == keySlider)
		{
			key = getKey(keySlider.getValue());
			this.repaint();
		}
		else if(e.getSource() == timeSlider)
		{
			time = timeSlider.getValue() + "/4";
			this.repaint();
		}
		else if(e.getSource() == tempoSlider)
		{
			//TODO add tempo stuff
			this.repaint();
		}
		else if(e.getSource() == barsSlider)
		{
			//TODO add bars stuff
			this.repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
