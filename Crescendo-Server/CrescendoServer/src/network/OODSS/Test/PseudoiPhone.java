package network.OODSS.Test;

import java.io.IOException;

import javax.swing.JOptionPane;

import network.OODSS.base.CrescendoTranslation;
import network.OODSS.messages.ConnectionRequest;

import ecologylab.collections.Scope;
import ecologylab.services.distributed.client.NIOClient;
import ecologylab.services.distributed.exception.MessageTooLargeException;
import ecologylab.xml.TranslationScope;

public class PseudoiPhone 
{
	private static String serverAddress = "localhost";
	
	private static int portNumber = 2108;
	
	public static void main(String[] args) throws IOException
	{
		TranslationScope publicChatTranslations = CrescendoTranslation.get();

		Scope clientScope = new Scope();

		NIOClient client = new NIOClient(serverAddress, 
										 portNumber,
										 publicChatTranslations, 
										 clientScope);

		/*
		 * Enable compression and connect the client to the server.
		 */
		client.allowCompression(true);
		client.useRequestCompression(true);
		client.connect();

		ConnectionRequest request = new ConnectionRequest();
		
		try {
			client.sendMessage(request);
		} catch (MessageTooLargeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * If the client connects, start chat window to run the application. Pass
		 * in client and clientScope instance.
		 */
		if (client.connected())
		{
			System.out.println("I have connected using the CrescendoTranslation scope");
			client.disconnect();
		}
	}
	
}
