package network.OODSS.base;

import java.io.IOException;
import java.net.InetAddress;

import keys.OODSS;


import ecologylab.collections.Scope;
import ecologylab.net.NetTools;
import ecologylab.services.distributed.server.DoubleThreadedNIOServer;
import ecologylab.xml.TranslationScope;
import ecologylab.xml.XMLTranslationException;
import gameManagement.GameManager;

/**
 * PublicServer: A server implemented with OODSS.
 * 
 * @author Brandon Kaster
 *
 */
public class PublicServer {
	
	public static final String DISPLAY_HANDLE = "DISPLAY_HANDLE";
	
	private static GameManager manager = new GameManager();
	private static final int	idleTimeout	= -1;
	private static final int	MTU			= 40000;
	
	public PublicServer() throws IOException {
		// Get base translations with static accessor
		TranslationScope publicServerTranslations = CrescendoTranslation.get();
		
		// Creates a scope for the server to use as an application scope as
		// well as individual client session scopes.
		Scope applicationScope = new Scope();
		
		applicationScope.put(OODSS.GAME_MANAGER, manager);
		
		// Acquire an array of all local ip-addresses
		InetAddress[] locals = NetTools.getAllInetAddressesForLocalhost();
		
		// Create the server and start the server so that it can accept
		// incoming connections
		DoubleThreadedNIOServer server = DoubleThreadedNIOServer.getInstance(2108, 
																			 locals, 
																			 publicServerTranslations,
																			 applicationScope, 
																			 idleTimeout, 
																			 MTU);
		
		server.start();
		manager.run();
	}
}
