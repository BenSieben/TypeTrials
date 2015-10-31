import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Displays how to use the
 * TypeGame properly
 * @author Ben Sieben
 * @version 14 May 2013
 */
public class HelpPanel extends JPanel{
	
	private int width, height;
	private double ratioX, ratioY;
	
	/**
	 * Creates a new HelpPanel, which just shows
	 * simplistic instructions on how to use the program
	 */
	public HelpPanel(){
		width = getWidth();
		height = getHeight();
		ratioX = 1;
		ratioY = 1;
	}
	
	/**
	 * Draws the HelpPanel
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		width = getWidth();
		height = getHeight();
		ratioX = width/500.0;
		ratioY = height/500.0;
		setBackground(Color.BLACK);
		g.setColor(Color.WHITE);
		Font f = new Font("Dialog",Font.BOLD,(int)(40*ratioX));
		g.setFont(f);
		g.drawString("HELP",(int)(10*ratioX),(int)(60*ratioY));
		f = new Font("Dialog",Font.ITALIC,(int)(30*ratioX));
		g.setFont(f);
		g.drawString("Left click with the mouse to select",(int)(10*ratioX),(int)(100*ratioY));
		g.drawString("     what you want to do",(int)(10*ratioX),(int)(140*ratioY));
		g.drawString("Use the keyboard in-game to type",(int)(10*ratioX),(int)(220*ratioY));
		g.drawString("     the words that show up",(int)(10*ratioX),(int)(260*ratioY));
		g.drawString("Use the ESC key to return to the",(int)(10*ratioX),(int)(340*ratioY));
		g.drawString("     main menu from here or",(int)(10*ratioX),(int)(380*ratioY));
		g.drawString("     any other mode; use it again",(int)(10*ratioX),(int)(420*ratioY));
		g.drawString("     to exit the game as well",(int)(10*ratioX),(int)(460*ratioY));
	}

}
