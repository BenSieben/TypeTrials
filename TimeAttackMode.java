import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

/**
 * The TimeAttack mode for the
 * TypeTrials program
 * @author Ben Sieben
 * @version 23 May 2013
 */
public class TimeAttackMode extends Mode{
	
	private String[] words;
	private int wordIndex, gameStatus, width, height, mouseY, highlightSection, typedLetters, points, timeLeft;
	private double ratioX, ratioY;
	private WordManager myManager;
	private Timer myTimer;
	
	/**
	 * Creates a new time attack game
	 * @param w TypeGame this TimeAttackMode
	 * is associated with
	 */
	public TimeAttackMode(TypeGame w) {
		super(w);
		words = new String[50];
		highlightSection = -5;
		gameStatus = 0;
		wordIndex = 0;
		width = getWidth();
		height = getHeight();
		mouseY = 0;
		ratioX = 1;
		ratioY = 1;
		typedLetters = 0;
		points = 0;
		myManager = new WordManager(null);
		myTimer = new Timer(100,new TimerListener(this));
		timeLeft = 300;
	}
	
	//Initializes the String array to be 
	//ready for playing the game
	private void initializeStringList(){
		if(getDifficulty() == Mode.SHORT_DIF){
			myManager.setFileName("ShortDictionary.txt");
		} else if(getDifficulty() == Mode.MED_DIF){
			myManager.setFileName("MediumDictionary.txt");
		} else {
			myManager.setFileName("LongDictionary.txt");
		}
		int wordsAdded = 0;
		int[] selectedIndeces = new int[50];
		while(wordsAdded < 50){
			int randomIndex = (int)(Math.random()*myManager.getNumOfValues());
			boolean b = checkForDuplicate(selectedIndeces,randomIndex);
			if(!b){
				String word = myManager.getString(randomIndex);
				words[wordsAdded] = word;
				selectedIndeces[wordsAdded] = randomIndex;
				wordsAdded++;
			}
		}
	}
	
	//returns true if the number num is a duplicate
	//returns false if the number num is not a duplicate
	private boolean checkForDuplicate(int[] nums, int num){
		for(int i = 0; i < nums.length; i++){
			if(nums[i] == num)
				return true;
		}
		return false;
	}
	
	/**
	 * Draws the time attack game mode
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		width = getWidth();
		height = getHeight();
		ratioX = width/500.0;
		ratioY = height/500.0;
		Font f = new Font("Dialog",Font.PLAIN,(int)(35*ratioX));
		g.setFont(f);
		g.setColor(Color.WHITE);
		setBackground(Color.BLACK);
		if(gameStatus == 0){ //selecting difficulty
			for(int i = 0; i <= 200; i++){
				g.setColor(new Color(i,i,i));
				g.fillRect((int)(50*ratioX)-(int)((i-100)*ratioX),(int)((highlightSection*100+30)*ratioY),(int)(125*ratioX),(int)(100*ratioY));
			}
			g.setColor(Color.WHITE);
			g.drawString("Short",(int)(10*ratioX),(int)(180*ratioY));
			g.drawString("Medium",(int)(10*ratioX),(int)(280*ratioY));
			g.drawString("Long",(int)(10*ratioX),(int)(380*ratioY));
			f = new Font("Dialog",Font.BOLD|Font.ITALIC,(int)(40*ratioX));
			g.setFont(f);
			g.drawString("Select a Difficulty",(int)(10*ratioX),(int)(80*ratioY));
		}
		else if(gameStatus == 1){ //playing game
			Font f1 = new Font("Dialog",Font.BOLD|Font.ITALIC,(int)(40*ratioX));
			g.setFont(f1);
			g.drawString(timeLeft+"",(int)(5*ratioX),(int)(475*ratioY));
			g.setColor(Color.WHITE);
			g.drawRect((int)(99*ratioX),(int)(439*ratioY),(int)(351*ratioX),(int)(41*ratioY));
			if(timeLeft >= 150){g.setColor(Color.GREEN.brighter());}
			else if(timeLeft >= 50){g.setColor(Color.ORANGE.brighter());}
			else{g.setColor(Color.RED.brighter());} 
			if(timeLeft >= 0){g.fillRect((int)(100*ratioX),(int)(440*ratioY),(int)((timeLeft/300.0)*350.0*ratioX),(int)(40*ratioY));}
			g.setColor(Color.WHITE);
			String dif = "";
			if(getDifficulty() == Mode.SHORT_DIF) dif = "Short";
			else if(getDifficulty() == Mode.MED_DIF) dif = "Medium";
			else dif = "Long";
			g.drawString("TimeAttack - "+dif,(int)(20*ratioX),(int)(80*ratioY));
			g.setFont(f);
			g.drawString(words[wordIndex],(int)(20*ratioX),(int)(150*ratioY));
			g.drawString("Points: "+points,(int)(20*ratioX),(int)(350*ratioY));
			g.drawLine((int)(17*ratioX),(int)(165*ratioY),(int)(30*ratioX),(int)(165*ratioY));
			if(typedLetters >= 10){
				timeLeft += 5;
				typedLetters = 0;
			}
		}
	}

	/**
	 * Finds out what key was pressed
	 * because a key press is detected
	 */
	public void keyPressed(KeyEvent e) {
		char key = e.getKeyChar();
		if(wordIndex < 50 && words[wordIndex].length() > 0 && key == words[wordIndex].charAt(0) && gameStatus == 1){
			if(words[wordIndex].length() == 1){
				words[wordIndex] = "";
				wordIndex++;
				if(wordIndex == words.length){ //all words typed
					gameStatus = 0;
					int pointsC = points;
					resetBoard();
					submitScore(pointsC,getDifficulty(),Score.TIMEATTACK_SCORE);
				}
				points += 100;
			}
			else {
				words[wordIndex] = words[wordIndex].substring(1);
			}
			typedLetters++;
			myTimer.restart();
		} else if(gameStatus == 1){
			typedLetters = 0;
			points -= 20;
			timeLeft -= 10;
			myTimer.restart();
			if(points <= -1000){points = -1000;}
			if(timeLeft <= 0){gameStatus = 2;}
		}
		repaint();
	}
	
	/**
	 * Resets the current state of the time attack mode
	 */
	public void resetBoard(){
		words = new String[50];
		wordIndex = 0;
		gameStatus = 0;
		typedLetters = 0;
		points = 0;
		timeLeft = 300;
		myTimer.stop();
	}

	/**
	 * Mouse move is detected, so a reaction
	 * is necessary
	 */
	public void mouseMoved(MouseEvent e) {
		mouseY = e.getY()-(int)(30*ratioY);
		ratioY = getHeight()/500.0;
		if(gameStatus == 0){ //selecting difficulty
			if(mouseY >= (int)(130*ratioY) && mouseY <= (int)(230*ratioY)){
				highlightSection = 1;
			} else if(mouseY > (int)(230*ratioY) && mouseY <= (int)(330*ratioY)){
				highlightSection = 2;
			} else if(mouseY > (int)(330*ratioY) && mouseY <= (int)(430*ratioY)){
				highlightSection = 3;
			} else {
				highlightSection = -5;
			}
		}
		repaint();
	}

	/**
	 * Mouse click is detected, so a reaction
	 * may be necessary
	 */
	public void mouseClicked(MouseEvent e) {
		mouseY = e.getY()-(int)(30*ratioY);
		if(gameStatus == 0 && e.getButton() == MouseEvent.BUTTON1){ //selecting difficulty
			if(mouseY >= (int)(130*ratioY) && mouseY <= (int)(230*ratioY)){
				setDifficulty(Mode.SHORT_DIF);
				gameStatus++;
				initializeStringList();
			} else if(mouseY > (int)(230*ratioY) && mouseY <= (int)(330*ratioY)){
				setDifficulty(Mode.MED_DIF);
				gameStatus++;
				initializeStringList();
			} else if(mouseY > (int)(330*ratioY) && mouseY <= (int)(430*ratioY)){
				setDifficulty(Mode.LONG_DIF);
				gameStatus++;
				initializeStringList();
			}
		}
		repaint();
	}
	
	//ActionListener for the Timer
	private class TimerListener implements ActionListener{
		
		private TimeAttackMode mode;
		
		public TimerListener(TimeAttackMode mode){
			this.mode = mode;
		}
		
		//Called when Timer runs out, which subtracts
		//from the time that is left
		public void actionPerformed(ActionEvent e){
			timeLeft--;
			if(timeLeft <= 0){
				myTimer.stop();
				gameStatus = 0;
				int pointsC = points;
				resetBoard();
				submitScore(pointsC,getDifficulty(),Score.TIMEATTACK_SCORE);
			}
			mode.repaint();
		}
	}

}
