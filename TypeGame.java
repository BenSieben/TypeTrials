import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * JFrame that manages what the screen
 * of the program should be showing at all times during
 * execution
 * @author Ben Sieben
 * @version 23 May 2013
 */
public class TypeGame extends JFrame implements WindowListener, MouseListener, MouseMotionListener{
	
	private JPanel cardPanel;
	private LeaderboardPanel leaderboard;
	private boolean atHome;
	private WordManager leaderboardManager;
	private ArcadeMode arcadeMode;
	private HelpPanel helpPanel;
	private OptionPanel optionPanel;
	private TimeAttackMode timeAttackMode;
	private TypeMenu typeMenu;
	private PracticeMode practiceMode;
	private int currentCard;
	
	/**
	 * Constructs a new TypeGame object to run the game
	 */
	public TypeGame(){
		super();
		leaderboardManager = new WordManager("Leaderboards.txt");
		JFrame myFrame = new JFrame("TypeTrials");
		myFrame.addMouseListener(this);
		myFrame.addMouseMotionListener(this);
		KeyboardFocusManager kManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		kManager.addKeyEventDispatcher(new KeyDispatcher());
		myFrame.setBounds(100, 100, 500, 500);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.addWindowListener(this);
		myFrame.setIconImage(new ImageIcon("TypeTrialsIcon.png").getImage());
		
		cardPanel = makeCardPanel();
		
		myFrame.setLayout(new BorderLayout());
		myFrame.setBackground(Color.WHITE);
		myFrame.add(cardPanel);
		myFrame.setMinimumSize(new Dimension(400,400));
		myFrame.setResizable(true);
		myFrame.setVisible(true);
		
		Container c = myFrame.getContentPane();
		c.add(cardPanel);
		currentCard = 0;
		atHome = true;
	}
	
	/**
	 * Returns the WordManager in charge of
	 * Leaderboard.txt
	 * @return the WordManager in charge of 
	 * Leaderboard.txt
	 */
	public WordManager getLeaderboardManager(){
		return leaderboardManager;
	}
	
	//Makes all the Jpanels that the TypeGame has
	private JPanel makeCardPanel(){
		JPanel cardPanel1 = new JPanel();
		cardPanel1.setLayout(new CardLayout());
		
		typeMenu = new TypeMenu(this);
		arcadeMode = new ArcadeMode(this);
		timeAttackMode = new TimeAttackMode(this);
		practiceMode = new PracticeMode(this);
		leaderboard = new LeaderboardPanel(this);
		optionPanel = new OptionPanel(this);
		helpPanel = new HelpPanel();
		
		cardPanel1.add(typeMenu);
		cardPanel1.add(arcadeMode);
		cardPanel1.add(timeAttackMode);
		cardPanel1.add(practiceMode);
		cardPanel1.add(leaderboard);
		cardPanel1.add(optionPanel);
		cardPanel1.add(helpPanel);
		return cardPanel1;
	}
	
	/**
	 * Turns the CardLayout JPanel forward n cards
	 * @param n the number of cards to move the CardLayout forward
	 * @pre n > 0, n < totalCards + currentCard
	 */
	public void nextPanel(int n){
		for(int i = 0; i < n; i ++){
			((CardLayout)cardPanel.getLayout()).next(cardPanel);
		}
		currentCard = n;
		atHome = false;
		requestFocus();
	}
	
	/**
	 * Makes the first JPanel (menu) in the CardLayout show up
	 */
	public void firstPanel(){
		((CardLayout)cardPanel.getLayout()).first(cardPanel);
		atHome = true;
		requestFocus();
		currentCard = 0;
	}
	
	/**
	 * Adds the input Score to the appropriate
	 * Leaderboard
	 * @param s the score to be added to the
	 * local Leaderboards
	 * @pre the Score has a valid difficulty and mode
	 */
	public void addScore(Score s){
		Leaderboard[] leaderboards = leaderboard.getLeaderboards();
		int dif = s.getDifficulty();
		int mode = s.getMode();
		if(mode == Score.ARCADE_SCORE){
			if(dif == Mode.SHORT_DIF){
				leaderboards[0].addScore(s);
			} else if(dif == Mode.MED_DIF){
				leaderboards[1].addScore(s);
			} else {
				leaderboards[2].addScore(s);
			}
		} else {
			if(dif == Mode.SHORT_DIF){
				leaderboards[3].addScore(s);
			} else if(dif == Mode.MED_DIF){
				leaderboards[4].addScore(s);
			} else{
				leaderboards[5].addScore(s);
			}
		}
	}

	/**
	 * Not used; required by interface
	 */
	public void windowActivated(WindowEvent arg0) {}

	/**
	 * Not used; required by interface
	 */
	public void windowClosed(WindowEvent arg0) {}

	/**
	 * Detects the window is being closed via the 
	 * close button in the top of the window 
	 * or System.exit(0); so program will try 
	 * to save the leaderboards
	 */
	public void windowClosing(WindowEvent e) {
		save();
	}
	
	//Saves the Leaderboards
	private void save() {
		String s = "";
		Leaderboard[] leaderboards = leaderboard.getLeaderboards();
		for(int i = 0; i < leaderboards.length; i++) {
			s += leaderboards[i].toString();
		} 
		leaderboardManager.saveData(s);
		leaderboardManager.addHash();
	}
	
	/**
	 * Resets the Leaderboard; should only be used by the 
	 * OptionPanel to reset the Leaderboards
	 */
	public void resetLeaderboards(){
		leaderboard.resetLeaderboards();
	}

	/**
	 * Not used; required by interface
	 */
	public void windowDeactivated(WindowEvent arg0) {}

	/**
	 * Not used; required by interface
	 */
	public void windowDeiconified(WindowEvent arg0) {}

	/**
	 * Not used; required by interface
	 */
	public void windowIconified(WindowEvent arg0) {}

	/**
	 * Not used; required by interface
	 */
	public void windowOpened(WindowEvent e) {
	}

	/**
	 * Not used; required by interface
	 */
	public void mouseDragged(MouseEvent e) {}

	/**
	 * Detects a mouse movement, so the information
	 * is passed on to the appropriate JPanels
	 */
	public void mouseMoved(MouseEvent e) {
		if(currentCard == 1) arcadeMode.mouseMoved(e);
		else if(currentCard == 4) leaderboard.mouseMoved(e);
		else if(currentCard == 5) optionPanel.mouseMoved(e);
		else if(currentCard == 2) timeAttackMode.mouseMoved(e);
		else if(currentCard == 0)typeMenu.mouseMoved(e);
	}

	/**
	 * Detects a mouse click, so the information
	 * is passed on to the appropriate JPanels
	 */
	public void mouseClicked(MouseEvent e) {
		if(currentCard == 1) arcadeMode.mouseClicked(e);
		else if(currentCard == 4) leaderboard.mouseClicked(e);
		else if(currentCard == 5) optionPanel.mouseClicked(e);
		else if(currentCard == 2) timeAttackMode.mouseClicked(e);
		else if(currentCard == 0)typeMenu.mouseClicked(e);
	}

	/**
	 * Not used; required by interface;
	 */
	public void mouseEntered(MouseEvent e) {}

	/**
	 * Not used; required by interface
	 */
	public void mouseExited(MouseEvent e) {}

	/**
	 * Not used; required by interface
	 */
	public void mousePressed(MouseEvent e) {}

	/**
	 * Not used; required by interface
	 */
	public void mouseReleased(MouseEvent e) {}
	
	//Determines what to do with the current ESC key press
	private void evaluateESC(){
		if(atHome){
			save();
			System.exit(0);
		}
		else{ //Not in menu screen
			if(currentCard == 1) arcadeMode.resetBoard();
			else if(currentCard == 2) timeAttackMode.resetBoard();
			firstPanel();
		}
	}
	
	//Required to manage listening for keyboard presses,
	//since a normal KeyListener won't do the job
	private class KeyDispatcher implements KeyEventDispatcher{
		//Detects any mosue press, release, or type
		public boolean dispatchKeyEvent(KeyEvent e) {
			if(e.getID() == KeyEvent.KEY_PRESSED){
				//First check for escape key
				if(e.getKeyChar() == KeyEvent.VK_ESCAPE){
					evaluateESC();
				}
				else{
					if(currentCard == 1) arcadeMode.keyPressed(e);
					else if(currentCard == 3) practiceMode.keyPressed(e);
					else if(currentCard == 2) timeAttackMode.keyPressed(e);
				}
			}
			return false;
		}
		
	}
	
}
