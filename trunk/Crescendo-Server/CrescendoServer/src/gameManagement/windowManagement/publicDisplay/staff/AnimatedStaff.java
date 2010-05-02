package gameManagement.windowManagement.publicDisplay.staff;

import gameManagement.messageTranslationSystem.Note;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import keys.KeySignatures;
import keys.Players;

// TODO Make note scrolling happen here
// TODO Fix the measure scaling
public class AnimatedStaff extends JPanel 
{
	/**
	 * Default Constructor
	 */
	public AnimatedStaff()
	{
		initComponents(Players.PLAYER_ONE, KeySignatures.CMajor, new ArrayList<String>(), 4, 4);
	}
	
	/**
	 * Constructor
	 * @param player - who's turn it is
	 * @param key - the key displayed at the beginning of the staff
	 * @param chordChanges - a list of individual chords to be displayed on top of each measure
	 * @param beats - top number of the time signature
	 * @param subdivision - bottom number of the time signature
	 */
	public AnimatedStaff(String player, String key, List<String> chordChanges, int beats, int subdivision)
	{
		initComponents(player, key, chordChanges, beats, subdivision);
	}
	
	/**
	 * Set which player's turn it is
	 * @param player - One of the strings from <code>keys.Players</code>
	 */
	public void setPlayer(String player)
	{
		List<String> players = Players.getPlayers();
		if (player != null && players.contains(player))
		{
			header.setPlayer(player);
			staff.setPlayer(player);
		}
	}
	
	/**
	 * Sets the key signature displayed at the beginning of the staff
	 * @param key - has to be one of the keys from <code>keys.KeySignatures</code>
	 */
	public void setKey(String key)
	{
		if (key != null && KeySignatures.hasKeySignature(key))
		{
			header.setKeySignature(key);
		}
		else
		{
			header.setKeySignature(KeySignatures.CMajor);
		}
	}
	
	/**
	 * Sets the time signature displayed at the beginning of the staff
	 * @param beatsPerBar - top number, has to be a value from 2 to 4
	 * @param subdivision - bottom number, has to be equal to 4
	 */
	public void setTime(int beatsPerBar, int subdivision)
	{
		if ((2 <= beatsPerBar && beatsPerBar <= 4) && subdivision == 4)
		{
			staff.setTimeSignature(beatsPerBar, subdivision);
			header.setTimeSignature(beatsPerBar, subdivision);
		}
	}
	
	/**
	 * Adds a note to the staff
	 * @param n - <code>Note</code> to be displayed
	 */
	public void addNote(Note n)
	{
		if (n != null)
		{
			staff.addNote(n.getLength(), n.size(), n.getPitch(), n.isCorrect(), n.isTied());
		}
	}
	
	/**
	 * Set all the key changes for the bars.  This actually determines how many 
	 * measures to draw for this particular instance of the game.
	 * @param changes - 
	 */
	public void setKeyChanges(List<String> changes)
	{
		if (changes != null && KeySignatures.hasKeySignatures(changes))
		{
			staff.setMeasureKeys(changes);
		}
		else 
		{
			changes = new ArrayList<String>();
			for (int i = 0; i < 16; ++i)
			{
				changes.add(KeySignatures.CMajor);
			}
			staff.setMeasureKeys(changes);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
	private void initComponents(String player, String headerKey, List<String> measureKeys, int beatsPerBar, int subdivision)
	{
		setPreferredSize(new Dimension(1300, 250));
		
		// Do initial setup for the header and staff
		header = new StaffHeader();
		staff = new Staff();
		
		noScroll = new JScrollPane(header);
		noScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		noScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);		
		
		scroller = new JScrollPane(staff);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		// Setup the given presets
		setPlayer(player);
		setKey(headerKey);
		
		setKeyChanges(measureKeys);
		setTime(beatsPerBar, subdivision);
		
		// Setup layout of the staff.  GridBagLayout is the only layout manager that give me the desired
		// proportions of screen real-estate between the StaffHeader and the Staff.  Sadly this means that
		// it can never be resized because GridBag sucks like that
		this.setBackground(Color.BLACK);
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints headerConstraints = new GridBagConstraints();
		headerConstraints.gridx = 0;
		headerConstraints.gridy = 0;
		headerConstraints.gridheight = 1;
		headerConstraints.gridwidth = 1;
		headerConstraints.weightx = 0.2;
		headerConstraints.weighty = 0.5;
		headerConstraints.fill = GridBagConstraints.BOTH;
		
		GridBagConstraints staffConstraints = new GridBagConstraints();
		staffConstraints.gridx = 1;
		staffConstraints.gridy = 0;
		staffConstraints.gridheight = 1;
		staffConstraints.gridwidth = 1;
		staffConstraints.weightx = 0.8;
		staffConstraints.weighty = 1;
		staffConstraints.fill = GridBagConstraints.BOTH;
		
		this.add(noScroll, headerConstraints);
		this.add(scroller, staffConstraints);
	}
	
	// Private variables 
	private StaffHeader header = null;
	private Staff staff = null;
	private JScrollPane noScroll = null;
	private JScrollPane scroller = null;
	
	private static final long serialVersionUID = 1L;
}
