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

		ConnectionManager cm = new ConnectionManager();
		lengthTraining testLength = new lengthTraining(1);


		for(Note n : testLength.wantedNotes)
			System.out.println(n.getLength());

		String length = "h";
		if(testLength.compare(length, 0))
			System.out.println("match!");
		else
			System.out.println("no match!");


		pitchTraining testPitch = new pitchTraining(8);

		for(Note n : testPitch.wantedNotes)
			System.out.println(n.getPitch());

		String pitch = "C6";
		if(testPitch.compare(pitch, 0))
			System.out.println("match!");
		else
			System.out.println("no match!");

	}
}