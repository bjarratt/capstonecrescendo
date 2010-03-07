package logging;

import java.io.File;

/**
 * <code>LogManager</code>
 * Encapsulates the log file for the server.  It is responsible for reading and writing
 * logs including client connects and disconnects, errors, and player actions in and out of games.  
 * 
 * This implementation follows the singleton design pattern for ensuring a global and easily accessed
 * manager.
 * 
 * @author red.ox.rxns@gmail.com
 *
 */
final public class LogManager 
{
	/**
	 * Method for accessing the LogManager
	 * @return an object of type LogManager
	 */
	public static LogManager getInstance()
	{
		return instance;
	}
	
	
	
	/* Implement read/write and other utility methods here */
	
	// Keep constructor private
	private LogManager() 
	{
		
	}
	
	// The LogManager will need to be available from program start up; therefore,
	// we do not use lazy creation.  This method also withstands a multithreaded 
	// environment.
	private static LogManager instance = new LogManager();
	
	// 
	private File log = null;
}
