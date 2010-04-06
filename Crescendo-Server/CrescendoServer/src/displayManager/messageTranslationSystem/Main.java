package displayManager.messageTranslationSystem;

import java.awt.Color;

import javax.swing.JFrame;


import displayManager.gameModes.lengthTraining;
import displayManager.gameModes.pitchTraining;

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
		/*
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
		*/
		
	/*	JFrame window = new JFrame("Crescendo");
		window.setBackground(Color.WHITE);
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.getContentPane().add(new DisplayGUI());
		window.pack();
		window.setVisible(true);*/
	}
}