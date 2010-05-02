package gameManagement.windowManagement.publicDisplay.gameWindow;

import java.util.HashMap;
import java.util.List;

import keys.Players;

/**
 * A data structure for holding score values.  This should make it pretty easy for natively drawing
 * the score board on the <code>GameWindow</code> or perhaps a widget-based display.
 * 
 * @author Zach
 */
public class ScoreBoard
{
	/**
	 * Default Constructor
	 */
	public ScoreBoard()
	{
		initScores(playerCount, startScore);
	}
	
	/**
	 * Create a score board with a specified number of players with an initial score
	 * @param playerCount - how many players
	 * @param startScore - starting score for each player
	 */
	public ScoreBoard(int playerCount, int startScore)
	{
		initScores(playerCount, startScore);
	}
	
	public int getScore(String player)
	{
		Integer score = 0;
		
		if (player != null && playerScores.containsKey(player))
		{
			score = playerScores.get(player);
		}
		
		return score.intValue();
	}
	
	/**
	 * Use this function to either increment or decrement a player's score
	 * @param player - player who's score needs updating
	 * @param pointsEarned - the points earned (can be either positive or negative)
	 */
	public void updateScore(String player, int pointsEarned)
	{
		if (player != null && playerScores.containsKey(player))
		{
			playerScores.put(player, getScore(player) + pointsEarned);
		}
	}
	
	/**
	 * Sets the number of players for a given game. It does nothing if the count is a bad number.
	 * <br/>Warning!  This function will reset any data prior prior to the call of setPlayerCount() 
	 * and set each player's score to the initial score.
	 * @param count
	 */
	public void setPlayerCount(int count)
	{
		if (count > 0)
		{
			List<String> players = Players.getPlayers();
			playerScores = new HashMap<String, Integer>();
			for (int i = 0; i < players.size(); ++i)
			{
				playerScores.put(players.get(i), startScore);
			}
		}
	}
	
	public void setInitialScore(int score)
	{
		startScore = score;
	}
	
	public int getInitialScore()
	{
		return startScore;
	}
	
	/**
	 * Resets all scores to the initial score
	 */
	public void resetScores()
	{
		List<String> players = Players.getPlayers();
		for (int i = 0; i < playerCount; ++i)
		{
			playerScores.put(players.get(i), startScore);
		}
	}
	
	// to be used in the constructor(s) to initialize the scores
	private void initScores(int count, int score)
	{
		if (count > 0)
		{
			playerCount = count;
		}
		
		startScore = score;
		List<String> players = Players.getPlayers();
		for (int i = 0; i < playerCount; ++i)
		{
			playerScores.put(players.get(i), startScore);
		}
	}
	
	// class members
	private int playerCount = 1;
	private int startScore = 200;
	private HashMap<String, Integer> playerScores = new HashMap<String, Integer>();
}
