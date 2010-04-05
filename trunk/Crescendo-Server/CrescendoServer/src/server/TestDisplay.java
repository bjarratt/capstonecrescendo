package server;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class TestDisplay extends JFrame
{
	public TestDisplay()
	{
		this.setLayout(new GridLayout(1,1));
		
		initComponents();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setText(String text)
	{
		this.text.setText(this.text.getText() + "\n" + text);
	}
	
	public void run()
	{
		this.setVisible(true);
	}
	
	private void initComponents()
	{
		text.setMinimumSize(new Dimension(200, 200));
		
		this.getContentPane().add(text);
		
		this.setMinimumSize(new Dimension(200, 200));
	}
	
	private JTextArea text = new JTextArea();
}
