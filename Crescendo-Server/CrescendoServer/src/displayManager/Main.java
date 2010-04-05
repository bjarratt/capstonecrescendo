package displayManager;

/**
 *	This class simply starts the GameManager
 *
 *	@author Travis Kosarek
 */
public class Main
{
	public static void main(String bikini[])
	{

		new ConnectionManager();
		lengthTraining test = new lengthTraining(1);
		
		
		for(Note n : test.wantedNotes)
			System.out.println(n.getLength());
		
		String length = "h";
		if(test.compareLength(length, 0))
			System.out.println("match!");
		else
			System.out.println("no match!");
	}
}