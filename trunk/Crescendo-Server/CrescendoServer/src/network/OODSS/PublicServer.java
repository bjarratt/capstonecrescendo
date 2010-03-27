package network.OODSS;

import java.io.IOException;
import java.net.InetAddress;

import ecologylab.collections.Scope;
import ecologylab.net.NetTools;
import ecologylab.services.distributed.server.DoubleThreadedNIOServer;
import ecologylab.services.messages.DefaultServicesTranslations;
import ecologylab.tutorials.oodss.historyecho.HistoryEchoRequest;
import ecologylab.tutorials.oodss.historyecho.HistoryEchoResponse;
import ecologylab.xml.TranslationScope;

/**
 * PublicServer: A server implemented with OODSS.
 * 
 * @author Brandon Kaster
 *
 */
public class PublicServer {
	
	private static final int	idleTimeout	= -1;
	private static final int	MTU			= 40000;
	
	public PublicServer() throws IOException {
		// Get base translations with static accessor
		TranslationScope publicServerTranslations = ServerTranslations.get();
		
		// Creates a scope for the server to use as an application scope as
		// well as individual client session scopes.
		Scope applicationScope = new Scope();
		
		// Acquire an array of all local ip-addresses
		InetAddress[] locals = NetTools.getAllInetAddressesForLocalhost();
		
		// Create the server and start the server so that it can accept
		// incoming connections
		DoubleThreadedNIOServer Server = DoubleThreadedNIOServer
				.getInstance(2108, locals, publicServerTranslations,
						applicationScope, idleTimeout, MTU);
		
		Server.start();
	}
	
}
