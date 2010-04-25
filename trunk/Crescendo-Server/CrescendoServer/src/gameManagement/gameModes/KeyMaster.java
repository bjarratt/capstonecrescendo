package gameManagement.gameModes;

import keys.ChordProgressions;

import java.util.ArrayList;
import java.util.Random;

public class KeyMaster
{
	private ArrayList<String> keyProgression;	//the array of notes the game wants
	private Random rand;
	
	private String currentKey;
	private ArrayList<String> possibleKeys;
	
	private int numberOfMeasures;

	public KeyMaster(String key, int measures, int lengthOfKeyChange)
	{
		keyProgression = new ArrayList<String>();
		rand = new Random();
		
		currentKey = key;
		possibleKeys = ChordProgressions.chordProgressions.get(currentKey);
		
		numberOfMeasures = measures;
			
		for(int i = 0; i < numberOfMeasures; i++)
		{
			if(i%lengthOfKeyChange==1)
				keyProgression.add(currentKey);
			else
			{
				currentKey = getRandomKey();
				keyProgression.add(currentKey);
			}
		}
	}

	private String getRandomKey()
	{
		return possibleKeys.get(rand.nextInt(possibleKeys.size()));	
	}
	
	public ArrayList<String> getKeyProgression()
	{
		return keyProgression;
	}
	
}
