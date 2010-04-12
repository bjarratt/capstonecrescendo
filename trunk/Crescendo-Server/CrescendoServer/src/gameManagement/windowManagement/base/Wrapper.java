package gameManagement.windowManagement.base;

import java.awt.Panel;

import processing.core.PApplet;

/**
 * Use this class to add PApplets to <code>WindowManager</code>
 * @author Zach
 *
 */
public class Wrapper extends Panel 
{
	public Wrapper() { super(); }
	
	public Wrapper(PApplet applet)
	{
		super();
		setApplet(applet);
	}
	
	public void setApplet(PApplet a)
	{
		applet = a;
		if (applet != null)
		{
			add(applet);
			
		}
	}
	
	public PApplet getApplet()
	{
		return this.applet;
	}
	
	public void startApplet()
	{
		if (applet != null)
		{
			applet.init();
			applet.start();
		}
	}
	
	public void stopApplet()
	{
		if (applet != null)
		{
			applet.stop();
		}
	}
	
	private PApplet applet;
}
