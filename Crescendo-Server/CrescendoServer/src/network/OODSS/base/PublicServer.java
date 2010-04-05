package network.OODSS.base;

import java.io.IOException;
import java.net.InetAddress;

import server.TestDisplay;

import ecologylab.collections.Scope;
import ecologylab.net.NetTools;
import ecologylab.services.distributed.server.DoubleThreadedNIOServer;
import ecologylab.xml.TranslationScope;
import ecologylab.xml.XMLTranslationException;

/**
 * PublicServer: A server implemented with OODSS.
 * 
 * @author Brandon Kaster
 *
 */
public class PublicServer {
	
	public static final String DISPLAY_HANDLE = "DISPLAY_HANDLE";
	
	private static TestDisplay display = new TestDisplay();
	private static final int	idleTimeout	= -1;
	private static final int	MTU			= 40000;
	
	public PublicServer() throws IOException {
		// Get base translations with static accessor
		TranslationScope publicServerTranslations = CrescendoTranslation.get();
		
		
		// Creates a scope for the server to use as an application scope as
		// well as individual client session scopes.
		Scope applicationScope = new Scope();
		applicationScope.put(DISPLAY_HANDLE, display);
		try {
			publicServerTranslations.translateToXML(System.out);
		} catch (XMLTranslationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		display.run();
	}
}
