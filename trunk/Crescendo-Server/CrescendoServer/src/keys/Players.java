package keys;

import java.util.ArrayList;
import java.util.List;

/**
 * Player keys for both logging and connection management
 * @author Zach
 *
 */
public class Players 
{
	// originators for logging purposes
	public static final String PLAYER_ONE = "player1";
	public static final String PLAYER_TWO = "player2";
	public static final String PLAYER_THREE = "player3";
	public static final String PLAYER_FOUR = "player4";
	public static final String SERVER = "server";
	
	private static ArrayList<String> players = new ArrayList<String>();
	
	static
	{
		players.add(PLAYER_ONE);
		players.add(PLAYER_TWO);
		players.add(PLAYER_THREE);
		players.add(PLAYER_FOUR);
		//players.add(SERVER);
	}
	
	public static List<String> getPlayers()
	{
		return new ArrayList<String>(players);
	}
}
