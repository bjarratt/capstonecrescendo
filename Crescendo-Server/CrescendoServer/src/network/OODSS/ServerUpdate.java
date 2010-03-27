package network.OODSS;

import ecologylab.collections.Scope;
import ecologylab.services.distributed.server.clientsessionmanager.SessionHandle;
import ecologylab.services.messages.RequestMessage;
import ecologylab.services.messages.ResponseMessage;
import ecologylab.services.messages.UpdateMessage;

/**
 * Implements a message that will be sent to clients to indicate that
 * another client has posted a message
 * 
 * @author Brandon
 *
 */
public class ServerUpdate extends UpdateMessage {
	@xml_attribute
	private String message;
	
	@xml_attribute
	private String host;
	
	@xml_attribute
	private int port;
	
	/**
	 * Constructor used on client. Fields populated automatically by
	 * ecologylab.xml
	 */
	public ServerUpdate() {
		
	}
	
	/**
	 * Constructor used on server
	 * 
	 * @param message
	 *           the message
	 * 
	 * @param handle
	 *           handle of originating client
	 */
	public ServerUpdate(String message, SessionHandle handle) {
		this.message = message;
		this.host = handle.getInetAddress().toString();
		this.port = handle.getPortNumber();
	}
	
	/**
	 * Called automatically by OODSS on client
	 */
	@Override
	public void processUpdate(Scope appObjScope) {
		// Get the listener
		ServerUpdateListener listener = (ServerUpdateListener) appObjScope
				.get(ServerUpdateListener.SERVER_UPDATE_LISTENER);
		
		// Report incoming update
		if (listener != null) {
			listener.recievedUpdate(this);
		}
		else {
			warning("Listener not set in application scope. Can't display message from\n"
					+ host + ":" + port + ": " + message);
		}
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}
}
