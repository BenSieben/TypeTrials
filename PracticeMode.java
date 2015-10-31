import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 * The Practice mode for the
 * TypeTrials program
 * @author Ben Sieben
 * @version 16 May 2013
 */
public class PracticeMode extends Mode{
	
	private String alphabet;
	private char currentChar;
	private int width,height,currentCharIndex;
	private double ratioX,ratioY;
	
	/**
	 * Creates a new PracticeMode
	 * @param w the TypeGame this PracticeMode is associated with
	 */
	public PracticeMode(TypeGame w) {
		super(w);
		alphabet = "QWERTYUIOPASDFGHJKLZXCVBNM";
		currentCharIndex = (int)(Math.random()*alphabet.length());
		currentChar = alphabet.charAt(currentCharIndex);
		width = getWidth();
		height = getHeight();
		ratioX = 1;
		ratioY = 1;
	}
	
	/**
	 * Draws the PracticeMode screen
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		width = getWidth();
		height = getHeight();
		ratioX = width/500.0;
		ratioY = height/500.0;
		g.setColor(Color.WHITE);
		setBackground(Color.BLACK);
		Font f = new Font("Dialog",Font.BOLD,(int)(40*ratioX));
		g.setFont(f);
		g.drawString("PRACTICE",(int)(10*ratioX),(int)(70*ratioY));
		f = new Font("Dialog",Font.PLAIN,(int)(20*ratioX));
		g.setFont(f);
		g.setColor(Color.BLUE.brighter());
		for(int i = 0; i < alphabet.length(); i++){
			if(i <= 9){ //top keyboard row
				g.drawRect((int)(40*ratioX*(i+1)),(int)(100*ratioY),(int)(40*ratioX),(int)(40*ratioY));
				if(i == currentCharIndex){
					g.setColor(Color.RED);
				}
				g.drawString(alphabet.charAt(i)+"",(int)(40*ratioX*(i+1)+10*ratioX),(int)(130*ratioY));
				if(i == currentCharIndex){
					g.setColor(Color.blue.brighter());
				}
			}else if(i <= 18){//middle keyboard row
				g.drawRect((int)(40*ratioX*(i-9)+20*ratioX),(int)(140*ratioY),(int)(40*ratioX),(int)(40*ratioY));
				if(i == currentCharIndex){
					g.setColor(Color.RED);
				}
				g.drawString(alphabet.charAt(i)+"",(int)(40*ratioX*(i-9)+30*ratioX),(int)(170*ratioY));
				if(i == currentCharIndex){
					g.setColor(Color.blue.brighter());
				}
			}else{//bottom keyboard row
				g.drawRect((int)(40*ratioX*(i-17)+20*ratioX),(int)(180*ratioY),(int)(40*ratioX),(int)(40*ratioY));
				if(i == currentCharIndex){
					g.setColor(Color.RED);
				}
				g.drawString(alphabet.charAt(i)+"",(int)(40*ratioX*(i-17)+30*ratioX),(int)(210*ratioY));
				if(i == currentCharIndex){
					g.setColor(Color.blue.brighter());
				}
			}
		}
		g.setColor(Color.WHITE);
		f = new Font("Dialog",Font.ITALIC,(int)(20*ratioX));
		g.setFont(f);
		g.drawString("Type the red letter to practice typing",(int)(20*ratioX),(int)(350*ratioY));
		g.drawString("Press the ESC key to exit to the main menu",(int)(20*ratioX),(int)(380*ratioY));
	}

	/**
	 * Detects a key press, so the key needs
	 * to be checked for matching the current
	 * character on the screen
	 */
	public void keyPressed(KeyEvent e) {
		char c = e.getKeyChar();
		if(c == KeyEvent.VK_ESCAPE){
			super.getFrame().firstPanel();
		}
		else if(Character.toUpperCase(c) == currentChar){
			int lastCharIndex = currentCharIndex;
			while(currentCharIndex == lastCharIndex){
				currentCharIndex = (int)(Math.random()*alphabet.length());
			}
			currentChar = alphabet.charAt(currentCharIndex);
		}
		repaint();
	}

}
