package gameManagement.windowManagement.publicDisplay.gameWindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import keys.Players;

/**
 * A data structure for holding score values.  This should make it pretty easy for natively drawing
 * the score board on the <code>GameWindow</code> or perhaps a widget-based display.
 * 
 * @author Zach
 */
public class ScoreBoard extends JPanel
{
	/**
	 * Default Constructor
	 */
	public ScoreBoard()
	{
		super();
		initComponents();
	}
	
	/**
	 * Create a score board with a specified number of players with an initial score
	 * @param playerCount - how many players
	 * @param startScore - starting score for each player
	 */
	public ScoreBoard(int playerCount, int startScore)
	{
		super();
		setPlayerCount(playerCount);
		this.startScore = startScore;
		initComponents();
	}
	
	/**
	 * Sets a player's score to the specified value.  Updates only if there is an entry for the player.
	 * @param player - player
	 * @param score - new score
	 */
	public void setScore(String player, int score)
	{
		if (player != null && playerScores.containsKey(player))
		{
			ScoreField field = playerScores.get(player);
			field.setScore(score);
		}
	}
	
	/**
	 * Get the score of the specified player
	 * @param player - specified player
	 * @return - integer value of player's score
	 */
	public int getScore(String player)
	{
		Integer score = 0;
		
		if (player != null && playerScores.containsKey(player))
		{
			ScoreField field = playerScores.get(player);
			score = field.getScore();
		}
		
		return score.intValue();
	}
	
	public void setPlayerCount(int count)
	{
		if (0 < count && count < 5)
		{
			playerCount = count;
			updateDisplay();
		}
	}
	
	/**
	 * Reset the state of the score board.  
	 * @param count
	 * @param score
	 */
	public void reset(int count, int score)
	{
		removeAll();
		setPlayerCount(count);
		startScore = score;
		
		Set<String> players = playerScores.keySet();
		for (String player : players)
		{
			ScoreField playerScore = playerScores.get(player);
			playerScore.setScore(startScore);
		}
		updateDisplay();
	}
	
	private void updateDisplay()
	{
		this.setVisible(false);
		this.setVisible(true);
	}
	
	// Initializes each player field.
	private void initComponents()
	{
		this.setOpaque(false);
		this.setLayout(null);
		
		List<String> players = Players.getPlayers();
		for (String player : players)
		{
			playerScores.put(player, new ScoreField(player, startScore));
		}
		
		this.addAncestorListener(new AncestorListener() {
			
			@Override public void ancestorRemoved(AncestorEvent event) {}
			@Override public void ancestorMoved(AncestorEvent event) {}
			
			@Override 
			public void ancestorAdded(AncestorEvent event) 
			{
				List<String> players = Players.getPlayers();
				for (int i = 0; i < playerCount; ++i)
				{
					ScoreField field = playerScores.get(players.get(i));
					Dimension d = new Dimension((int)(getWidth()*0.2), getHeight()/2);
					int regionWidth = getWidth()/playerCount;
					Point p = new Point(i*regionWidth + (regionWidth - d.width)/2, (getHeight() - d.height)/2);
					field.setBounds(p.x, p.y, d.width, d.height);
					add(field);
					field.validate();
				}
			}
		});
	}
	
	// class members
	private int playerCount = 1;
	private int startScore = 200;
	private HashMap<String, ScoreField> playerScores = new HashMap<String, ScoreField>();
	
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("ScoreBoard test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(new Dimension(1000, 200));
		frame.setLayout(new GridLayout(1,1));
		frame.getContentPane().setBackground(Color.gray);
		
		ScoreBoard b = new ScoreBoard(4, 200);
		frame.getContentPane().add(b);
		
		frame.setVisible(true);
		
		try 
		{
			Thread.sleep(1000);
			b.reset(2, 1000);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
}
