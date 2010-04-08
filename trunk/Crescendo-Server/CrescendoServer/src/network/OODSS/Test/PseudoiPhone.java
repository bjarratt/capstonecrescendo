package network.OODSS.Test;

import java.io.IOException;

import network.OODSS.base.CrescendoTranslation;
import network.OODSS.messages.ConnectionRequest;

import ecologylab.collections.Scope;
import ecologylab.services.distributed.client.NIOClient;
import ecologylab.services.distributed.exception.MessageTooLargeException;
import ecologylab.xml.TranslationScope;

public class PseudoiPhone implements Runnable
{
	public PseudoiPhone(NIOClient client, Scope appScope)
	{
		this.client = client;
		this.scope = appScope;
	}
	
	public void connect()
	{
		ConnectionRequest connect = new ConnectionRequest();

		try {
			client.sendMessage(connect);
		} catch (MessageTooLargeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() 
	{
		connect();
		
		if (getPlayerNumber() != "")
		{
			// send messaged to the server via the client object here.
			// feel free to use the logic you did before.
			// Look at the comment block below this to see how to send a message via OODSS

			// Use this code to send a message to the server
			// PlayNoteRequest request = new PlayNoteRequest(getPlayerNumber() + "_wholenote_f6");
			// this.client.sendMessage(request);
		}
		
	}
	
	public void setPlayerNumber(String number)
	{
		this.playerNumber = number;
	}
	
	public String getPlayerNumber()
	{
		return this.playerNumber;
	}
	
	private NIOClient client;
	private Scope scope;
	private String playerNumber;
	
	public static final String PHONE_HANDLE = "PHONE_HANDLE";
	
	private static String serverAddress = "localhost";
	private static int portNumber = 2108;

	/**
	 * This code needs to be repeated for each instance of PseudoiPhone.
	 * You might be able to get away with using the same scope object (although doubtful),
	 * but you definitely need to use a different NIOClient object every time.
	 * @param args
	 * @throws IOException
	 */
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

		if (client.connected())
		{
			PseudoiPhone phone = new PseudoiPhone(client, clientScope);
			new Thread(phone).run();
		}
	}

}
