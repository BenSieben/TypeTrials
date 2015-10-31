import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

/**
 * Manages what panels to be showing on 
 * the program screen.
 * @author Ben Sieben
 * @version 9 May 2013
 */
public class TypeMenu extends JPanel{
	
	private int height,width, mouseY;
	private double ratioX,ratioY;
	private TypeGame myFrame;
	
	/**
	 * Creates a new TypeMenu
	 * @param w The TypeGame to connect with this
	 * TypeMenu
	 */
	public TypeMenu(TypeGame w){
		super();
		myFrame = w;
		setBackground(Color.WHITE);
		height = getHeight();
		width = getWidth();
		ratioX = 1;
		ratioY = 1;
		mouseY = 0;
		setFocusable(true);
	}
	
	/**
	 * Draws out to the screen whatever is appropriate
	 * for the moment
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		height = getHeight();
		width = getWidth();
		ratioX = width/500.0;
		ratioY = height/500.0;
		if(mouseY >= (int)(120*ratioY) && mouseY <= (int)(470*ratioY)){
			int selectHeight = 0;
			if(mouseY <= (int)(180*ratioY)){
				selectHeight = 120;
			} else if (mouseY <= (int)(240*ratioY)){
				selectHeight = 180;
			} else if (mouseY <= (int)(300*ratioY)){
				selectHeight = 240;
			} else if (mouseY <= (int)(360*ratioY)){
				selectHeight = 300;
			} else if (mouseY <= (int)(420*ratioY)){
				selectHeight = 360;
			} else {
				selectHeight = 420;
			}
			g.setColor(new Color(0,0,0));
			int oWidth = (int)(250*ratioX/2.0);
			int oHeight = (int)(60*ratioY);
			for(int i = 0; i <= 200; i++){
				g.setColor(new Color(i,i,i));
				g.fillRect((int)(50*ratioX)-(int)((i-100)*ratioX),(int)(selectHeight*ratioY),oWidth,oHeight);
			}
		}
		setBackground(Color.BLACK);
		g.setColor(Color.WHITE);
		Font f = new Font("Dialog",Font.BOLD|Font.ITALIC,(int)(40*ratioX));
		g.setFont(f);
		g.drawString("TypeTrials",(int)(10*ratioX),(int)(80*ratioY));
		f = new Font("Dialog",Font.PLAIN,(int)(35*ratioX));
		g.setFont(f);
		g.drawString("Arcade", (int)(10*ratioX), (int)(160*ratioY));
		g.drawString("Time Attack", (int)(10*ratioX), (int)(220*ratioY));
		g.drawString("Practice", (int)(10*ratioX), (int)(280*ratioY));
		g.drawString("Leaderboards", (int)(10*ratioX), (int)(340*ratioY));
		g.drawString("Options", (int)(10*ratioX), (int)(400*ratioY));
		g.drawString("Help", (int)(10*ratioX), (int)(460*ratioY));
	}
	
	/**
	 * Triggered whenever a mouse button has
	 * been both pressed and released, this will
	 * do different actions based on which particular
	 * mouse button was pressed and where the mouse 
	 * was located during the press
	 */
	public void mouseClicked(MouseEvent e){
		int clickedButton = e.getButton();
		mouseY = e.getY()-(int)(30*ratioY);
		if(mouseY >= (int)(120*ratioY) && mouseY <= (int)(470*ratioY) && clickedButton == MouseEvent.BUTTON1){
			int cardForward = 0;
			if(mouseY <= (int)(180*ratioY)){//ARCADE
				cardForward = 1;
			} else if (mouseY <= (int)(240*ratioY)){//TIME ATTACK
				cardForward = 2;
			} else if (mouseY <= (int)(300*ratioY)){//PRACTICE
				cardForward = 3;
			} else if (mouseY <= (int)(360*ratioY)){//LEADERBOARDS
				cardForward = 4;
			} else if (mouseY <= (int)(420*ratioY)){//OPTIONS
				cardForward = 5;
			} else {//HELP
				cardForward = 6;
			}
			myFrame.nextPanel(cardForward);
		}
		repaint();
	}

	/**
	 * Tells mouse has been moved, so the 
	 * coordinates of the mouse are updated
	 */
	public void mouseMoved(MouseEvent e) {
		mouseY = e.getY()-(int)(30*ratioY);
		repaint();
	}

}
