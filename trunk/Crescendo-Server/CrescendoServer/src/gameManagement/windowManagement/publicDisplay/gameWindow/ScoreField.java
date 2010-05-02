package gameManagement.windowManagement.publicDisplay.gameWindow;

import java.awt.Color;
import java.awt.Font;

import gameManagement.windowManagement.publicDisplay.ColorMap;

import javax.swing.JFrame;
import javax.swing.JTextField;

import keys.Players;

public class ScoreField extends JTextField 
{
	/**
	 * Create a score field initialized to a score of 200 and set to player 1's color
	 */
	public ScoreField() 
	{
		initComponents();
		updateDisplay();
	}
	
	/**
	 * Create a score field set to the given score and player
	 * @param startScore - the score displayed whenever the field first becomes visible
	 * @param player - the player associated with this score field, visually indicated by background color
	 */
	public ScoreField(int startScore, String player)
	{
		setScore(startScore);
		setPlayer(player);
		initComponents();
		updateDisplay();
	}
	
	/**
	 * Method for changing the associated player
	 * @param player - the player associated with this score field, visually indicated by background color
	 */
	public void setPlayer(String player)
	{
		if (Players.getPlayers().contains(player))
		{
			this.player = player;
			updateDisplay();
		}
	}
	
	/**
	 * Updates the player score
	 * @param newScore - the player's new score
	 */
	public void setScore(int newScore)
	{
		score = newScore;
		updateDisplay();
	}
	
	// Sets up font, editability, fore- and background colors, and text alignment
	private void initComponents()
	{
		setEditable(false);
		setBorder(null);
		setForeground(Color.white);
		setFont(new Font("Courier New", Font.PLAIN, 40));
		setHorizontalAlignment(JTextField.CENTER);
	}
	
	// used to visually update the background color and the score
	private void updateDisplay()
	{
		Color background = ColorMap.getPlayerColor(player);
		if (background != null)
		{
			setBackground(background);
		}
		setText(String.format("%d", score));
	}
	
	private String player = Players.PLAYER_ONE;
	private int score = 200;
	
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("ScoreField Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(800, 600);
		frame.setLayout(null);
		frame.getContentPane().setBackground(Color.black);
		
		int height = 50;
		int width = 150;
		ScoreField score = new ScoreField(0, Players.PLAYER_THREE);
		score.setBounds((frame.getWidth() - width)/2, (frame.getHeight() - height)/2, width, height);
		frame.add(score);
		
		frame.setVisible(true);
		
		for (int i = 7; i <= 10; ++i)
		{
			try
			{
				Thread.sleep(500);
				score.setScore(i);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
