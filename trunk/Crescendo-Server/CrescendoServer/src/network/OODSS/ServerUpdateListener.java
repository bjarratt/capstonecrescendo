package network.OODSS;

/**
 * 
 * @author Brandon
 * 
 */
public interface ServerUpdateListener {
	public final static String SERVER_UPDATE_LISTENER = "SERVER_UPDATE_LISTENER";
	
	void recievedUpdate(ServerUpdate response);
}
