package gameManagement.windowManagement.publicDisplay.gameWindow;

import gameManagement.windowManagement.publicDisplay.ColorMap;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

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
	public ScoreField(String player, int startScore)
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
	
	public int getScore()
	{
		return score;
	}
	
	// Sets up font, editability, fore- and background colors, and text alignment
	private void initComponents()
	{
		this.addAncestorListener(new AncestorListener() {
			
			@Override public void ancestorRemoved(AncestorEvent event) {}
			@Override public void ancestorMoved(AncestorEvent event) {}
			
			@Override
			public void ancestorAdded(AncestorEvent event) 
			{
				setEditable(false);
				setBorder(null);
				setForeground(Color.white);
				setFont(new Font("Courier New", Font.PLAIN, getHeight()/2));
				setHorizontalAlignment(JTextField.CENTER);
			}
		});
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
}
