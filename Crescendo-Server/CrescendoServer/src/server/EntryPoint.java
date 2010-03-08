package server;

import java.io.IOException;

import display.DisplayManager;

import logging.LogManager;

public class EntryPoint {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		DisplayManager display = new DisplayManager();
		
		display.Run();
	}
}
