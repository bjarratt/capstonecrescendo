package network;

import java.io.IOException;

import network.OODSS.PublicServer;

public class ConnectionManager {
	private PublicServer server;
	
	public ConnectionManager() {
		try {
			this.server = new PublicServer();
		}
		catch (IOException e) {
			
		}
	}
}
