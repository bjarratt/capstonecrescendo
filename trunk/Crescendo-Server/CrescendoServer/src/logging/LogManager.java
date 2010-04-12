package logging;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import keys.Logging;




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
	
	/**
	 * Writes an event worth logging to the log file.  This includes but is not limited to:
	 * <ol> 
	 * 		<li>Server startup/shutdown</li>
	 * 		<li>Client (dis)connections</li>
	 * 		<li>Game start/end</li>
	 * </ol>
	 * @param originator - <code>String</code> that tells who did what.  Look in Logging.java
	 * @param action - <code>String</code> that tells what the action was.  Look in Logging.java
	 * @throws IOException - if writing to the log fails
	 */
	public synchronized void writeLogEntry(final String originator,
										   final String action) 
	{
		if (fileWriter != null)
		{
			try 
			{
				GregorianCalendar c = new GregorianCalendar(zone, locale);

				Integer seconds = c.get(Calendar.SECOND);
				String strSeconds = seconds.toString();
				
				// a little hackey, this is to get prettier/consistent formatting for seconds
				if (seconds < 10)
				{
					strSeconds = "0" + strSeconds;
				}
				
				String entry = String.format("%1$tm/%1$td/%1tY --%2$d:%3$d:%4$s,%5$s,%6$s\n", 
											 c,
											 c.get(Calendar.HOUR_OF_DAY),
											 c.get(Calendar.MINUTE),
											 strSeconds,
											 originator,
											 action);
				fileWriter.append(entry);
				
				fileWriter.flush();

			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
	}
	
	public synchronized void closeLog()
		throws IOException
	{
		fileWriter.flush();
		fileWriter.close();
	}
	
	/**
	 * Reads the log file in for printing either to the screen or to another file.
	 * <i>Warning!</i>  Use this method sparingly because it is synchronized, especially 
	 * after the log file becomes large.
	 * @return a <code>String</code> containing the entire contents of the log file
	 * @throws IOException - if it fails to read from the log file
	 */
	public synchronized String printLog()
	{
		String logContents = "";
		String nextLine = null;
		
		if (fileWriter != null)
		{
			try
			{
				fileWriter.flush();
				BufferedReader fileReader = new BufferedReader(new FileReader(log));
				
				while ((nextLine = fileReader.readLine()) != null)
				{
					logContents += nextLine + "\n";
				}
				
				fileReader.close();
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
		
		return logContents;
	}

	// Return a file object with the path 
	private File getLog()
	{
		String path = System.getProperty(Logging.WORKING_DIRECTORY) + 
					  System.getProperty(Logging.FILE_SEPARATOR);
		File f = new File (path + Logging.LOG_NAME);
		
		return f;
	}
	
	// Private method for building the file path and opening the log file.
	private BufferedWriter openLog(File f)
	{
		BufferedWriter writer = null;
		
		try {
			boolean doesExist = f.exists();
			writer = new BufferedWriter(new FileWriter(f, true));
			if (!doesExist)
			{
				writeHeaders(writer);
			}
		}
		catch (IOException ioe) {
			writer = null;
			ioe.printStackTrace();
		}
		
		return writer;
	}
	
	private void writeHeaders(final BufferedWriter writer)
	{
		if (writer != null)
		{
			String headers = keys.Logging.TIMESTAMP + "," + 
							 keys.Logging.PLAYER + "," + 
							 keys.Logging.ACTION + "\n";
			try {
				writer.append(headers);
				writer.flush();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	// Keep constructor private
	private LogManager() 
	{
		log = getLog();
		fileWriter = openLog(log);
	}
	
	// The LogManager will need to be available from program start up; therefore,
	// we do not use lazy creation.  This method also withstands a multithreaded 
	// environment.
	private static LogManager instance = new LogManager();
	
	private File log = null;						// contains the file path to the .txt file
	private BufferedWriter fileWriter = null;		// for writing log entries to disk

	private Locale locale = Locale.getDefault();	// timezone and locale 
	private TimeZone zone = TimeZone.getDefault();	// are use for time stamps
}
