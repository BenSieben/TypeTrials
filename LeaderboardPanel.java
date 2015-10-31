import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * LeaderboardPanel for both Arcade and 
 * TimeAttack leaderboards
 * @author Ben Sieben
 * @version 18 May 2013
 */
public class LeaderboardPanel extends JPanel{
	
	private TypeGame myFrame;
	private int mouseX, mouseY, width, height, highlightSection, wordLength;
	private JPanel selectPanel,scorePanel;
	private boolean isArcade;
	private double ratioX,ratioY;
	private Leaderboard[] leaderboards;
	
	/**
	 * Creates a new LeaderboardPanel, which allows for viewing of
	 * the different Leaderboards and setting which Leaderboard
	 * to view
	 * @param w the TypeGame this LeaderboardPanel is associated with
	 */
	public LeaderboardPanel(TypeGame w){
		super();
		ratioX = 1;
		ratioY = 1;
		isArcade = true;
		wordLength = Mode.SHORT_DIF;
		highlightSection = 1;
		width = getWidth();
		height = getHeight();
		myFrame = w;
		leaderboards = new Leaderboard[6];
		initializeLeaderboards();
		setLayout(new BorderLayout());
		selectPanel = new LeaderboardSelector();
		selectPanel.setBackground(Color.BLACK);
		scorePanel = new JPanel();
		scorePanel.setLayout(new CardLayout());
		for(int i = 0; i < leaderboards.length; i++){
			scorePanel.add(leaderboards[i]);
		}
		add(selectPanel,BorderLayout.NORTH);
		add(scorePanel);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		setBackground(Color.BLACK);
	}
	
	private void initializeLeaderboards(){
		WordManager myManager = myFrame.getLeaderboardManager();
		boolean b = myManager.checkHash();
		if(!b){ //hash don't match because of outside leaderboard editing
			myManager.saveData("");
			JOptionPane.showMessageDialog(this,"Error: Leaderboard load failed. Leaderboard will be reset.",
					"Leaderboard Load Failure",
					JOptionPane.ERROR_MESSAGE);
			leaderboards[0] = new Leaderboard("ArcadeShort");
			leaderboards[1] = new Leaderboard("ArcadeMedium");
			leaderboards[2] = new Leaderboard("ArcadeLong");
			leaderboards[3] = new Leaderboard("TimeAttackShort");
			leaderboards[4] = new Leaderboard("TimeAttackMedium");
			leaderboards[5] = new Leaderboard("TimeAttackLong");
		} else { //hash does match
			leaderboards[0] = new Leaderboard("ArcadeShort");
			leaderboards[1] = new Leaderboard("ArcadeMedium");
			leaderboards[2] = new Leaderboard("ArcadeLong");
			leaderboards[3] = new Leaderboard("TimeAttackShort");
			leaderboards[4] = new Leaderboard("TimeAttackMedium");
			leaderboards[5] = new Leaderboard("TimeAttackLong");
			String s = myManager.getContents();
			int index = "ArcadeShort".length();
			String name = "";
			int score = 0;
			int nEnd = 0;
			while(index < s.indexOf("ArcadeMedium")-1){ //reading the ArcadeShort scores
				index += 4;
				nEnd = s.indexOf(',',index);
				name = s.substring(index,nEnd);
				index = nEnd+1;
				score = Integer.parseInt(s.substring(index,s.indexOf(';',index)));
				index = s.indexOf(';',index) + 1;
				leaderboards[0].addScore(new Score(Score.ARCADE_SCORE,Mode.SHORT_DIF,name,score));
			}
			index = s.indexOf("ArcadeMedium")+"ArcadeMedium".length();
			while(index < s.indexOf("ArcadeLong")-1){ //reading the ArcadeMedium scores
				index += 4;
				nEnd = s.indexOf(',',index);
				name = s.substring(index,nEnd);
				index = nEnd+1;
				score = Integer.parseInt(s.substring(index,s.indexOf(';',index)));
				index = s.indexOf(';',index) + 1;
				leaderboards[1].addScore(new Score(Score.ARCADE_SCORE,Mode.MED_DIF,name,score));
			}
			index = s.indexOf("ArcadeLong")+"ArcadeLong".length();
			while(index < s.indexOf("TimeAttackShort")-1){ //reading the ArcadeLong scores
				index += 4;
				nEnd = s.indexOf(',',index);
				name = s.substring(index,nEnd);
				index = nEnd+1;
				score = Integer.parseInt(s.substring(index,s.indexOf(';',index)));
				index = s.indexOf(';',index) + 1;
				leaderboards[2].addScore(new Score(Score.ARCADE_SCORE,Mode.LONG_DIF,name,score));
			}
			index = s.indexOf("TimeAttackShort")+"TimeAttackShort".length();
			while(index < s.indexOf("TimeAttackMedium")-1){ //reading the TimeAttackShort scores
				index += 4;
				nEnd = s.indexOf(',',index);
				name = s.substring(index,nEnd);
				index = nEnd+1;
				score = Integer.parseInt(s.substring(index,s.indexOf(';',index)));
				index = s.indexOf(';',index) + 1;
				leaderboards[3].addScore(new Score(Score.TIMEATTACK_SCORE,Mode.SHORT_DIF,name,score));
			}
			index = s.indexOf("TimeAttackMedium")+"TimeAttackMedium".length();
			while(index < s.indexOf("TimeAttackLong")-1){ //reading the TimeAttackMedium scores
				index += 4;
				nEnd = s.indexOf(',',index);
				name = s.substring(index,nEnd);
				index = nEnd+1;
				score = Integer.parseInt(s.substring(index,s.indexOf(';',index)));
				index = s.indexOf(';',index) + 1;
				leaderboards[4].addScore(new Score(Score.TIMEATTACK_SCORE,Mode.MED_DIF,name,score));
			}
			index = s.indexOf("TimeAttackLong")+"TimeAttackLong".length();
			while(index < s.length()-1){ //reading the TimeAttackLong scores
				index += 4;
				nEnd = s.indexOf(',',index);
				name = s.substring(index,nEnd);
				index = nEnd+1;
				score = Integer.parseInt(s.substring(index,s.indexOf(';',index)));
				index = s.indexOf(';',index) + 1;
				leaderboards[5].addScore(new Score(Score.TIMEATTACK_SCORE,Mode.LONG_DIF,name,score));
			}
		}
	}
	
	//Determines which Leaderboard should be painting
	//in the lower portion of the screen
	private void determineLeaderboard(){
		if(isArcade){ //showing arcade
			if(wordLength == Mode.SHORT_DIF){ //short difficulty
				setScorePanel(0);
			}else if(wordLength == Mode.MED_DIF){ //medium difficulty
				setScorePanel(1);
			}else {//long difficulty
				setScorePanel(2);
			}
		}else{ //showing time attack
			if(wordLength == Mode.SHORT_DIF){ //short difficulty
				setScorePanel(3);
			}else if(wordLength == Mode.MED_DIF){ //medium difficulty
				setScorePanel(4);
			}else {//long difficulty
				setScorePanel(5);
			}
		}
	}
	
	//Sets the scorePanel to the i's card number
	//@pre: i is between 0 and 5
	private void setScorePanel(int n){
		((CardLayout)scorePanel.getLayout()).first(scorePanel);
		for(int i = 0; i < n; i ++){
			((CardLayout)scorePanel.getLayout()).next(scorePanel);
		}
	}
	
	/**
	 * Detects a mouse click has occured, which may require
	 * a change in which Leaderboard is currently displayed
	 */
	public void mouseClicked(MouseEvent e){
		int clickedButton = e.getButton();
		mouseX = e.getX();
		mouseY = e.getY()-(int)(130*ratioY);
		if(clickedButton == MouseEvent.BUTTON1){
			if(mouseY <= 50){
				if(mouseX <= (int)(250*ratioX)){
					isArcade = true;
				} else {
					isArcade = false;
				}
			} else if (mouseY <= 100){
				if(mouseX <= (int)(500.0/3.0*ratioX)){
					wordLength = Mode.SHORT_DIF;
				} else if (mouseX <= (int)(500.0/3.0*2.0*ratioX)){
					wordLength = Mode.MED_DIF;
				} else {
					wordLength = Mode.LONG_DIF;
				}
			}
		}
		determineLeaderboard();
		repaint();
	}

	/**
	 * Tells the mouse has moved, which may change
	 * which Leaderboard option needs to be "highlighted" 
	 * because of where the mouse currently is
	 */
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY()-(int)(130*ratioY);
		if(mouseY <= 50){
			if(mouseX <= (int)(250*ratioX)){
				highlightSection = 1;
			} else {
				highlightSection = 2;
			}
		} else if (mouseY <= 100){
			if(mouseX <= (int)(500.0/3.0*ratioX)){
				highlightSection = 3;
			} else if (mouseX <= (int)(500.0/3.0*2.0*ratioX)){
				highlightSection = 4;
			} else {
				highlightSection = 5;
			}
		}
		repaint();
	}
	
	/**
	 * Returns the current mode selected in the
	 * leaderboard panel
	 * @return 1 for ArcadeMode, 2 for TimeAttackMode
	 */
	public int getModeSelect(){
		if(isArcade){
			return 1;
		}
		else{
			return 2;
		}
	}
	
	/**
	 * Returns the array of Leaderboards that this
	 * LeaderboardPanel displays
	 * @return the Leaderboards displayed by this
	 * panel
	 */
	public Leaderboard[] getLeaderboards(){
		return leaderboards;
	}
	
	/**
	 * Resets the Leaderboard; should only be used by the 
	 * TypeGame via the OptionPanel
	 */
	public void resetLeaderboards(){
		for(int i = 0; i < leaderboards.length; i++){
			leaderboards[i].reset();
		}
	}
	
	//GUI for selecting which leaderboard to see
	private class LeaderboardSelector extends JPanel{
		
		public LeaderboardSelector(){
			ratioX = 1;
			ratioY = 1;
			setPreferredSize(new Dimension((int)(484*ratioX),(int)(100*ratioY)));
		}
		
		//Paints the option bar at the top of the LeaderboardPanel
		//for selecting which Leaderboard to view
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			boolean b = false;
			width = getWidth();
			height = getHeight();
			ratioX = width/500.0;
			ratioY = height/500.0;
			if(highlightSection == 1 || isArcade){ //highlight Arcade
				for(int i = 0; i <= 200; i++){
					g.setColor(new Color(i,i,i));
					g.fillRect((int)(50*ratioX)-(int)((i-100)*ratioX),0,(int)(250*ratioX/2.0),50);
				}
			} else {
				g.setColor(Color.BLACK);
				g.fillRect(0,0,(int)(249*ratioX),50);
			}
			if (highlightSection == 2 || !isArcade){ //highlight Time Attack
				for(int i = 0; i <= 200; i++){
					g.setColor(new Color(i,i,i));
					g.fillRect((int)(50*ratioX)-(int)((i-100)*ratioX)+(int)(300*ratioX),0,(int)(250*ratioX/2.0),50);
				}
			} else{
				g.setColor(Color.BLACK);
				g.fillRect((int)(250*ratioX),0,(int)(249*ratioX),50);
			}
			if(highlightSection == 4 || wordLength == Mode.MED_DIF){ //highlight medium
				for(int i = 0; i <= 200; i++){
					g.setColor(new Color(i,i,i));
					g.fillRect((int)(-50*ratioX)-(int)((i-100)*ratioX)+(int)(500/3.0*ratioX),50,(int)(250*ratioX/2.0),50);
				}
			}
			if(highlightSection == 3 || wordLength == Mode.SHORT_DIF){ //Highlight short
				for(int i = 0; i <= 200; i++){
					g.setColor(new Color(i,i,i));
					g.fillRect((int)(-50*ratioX)-(int)((i-100)*ratioX),50,(int)(250*ratioX/2.0),50);
				}
			}else {
				b = true;
			}
			if(highlightSection == 5 || wordLength == Mode.LONG_DIF){ //highlight long
				for(int i = 0; i <= 200; i++){
					g.setColor(new Color(i,i,i));
					g.fillRect((int)(-50*ratioX)-(int)((i-100)*ratioX)+(int)(500/1.03*ratioX),50,(int)(250*ratioX/2.0),50);
				}
			}
			if(b){
				g.setColor(Color.BLACK);
				g.fillRect(0,50,(int)(500/3.0*ratioX),50);
			}
			g.setColor(Color.BLACK);
			g.drawLine(0,50,width,50);
			g.setColor(Color.WHITE);
			Font f = new Font("Dialog",Font.PLAIN,(int)(30*ratioX));
			g.setFont(f);
			g.drawString("Arcade",(int)(60*ratioX),40);
			g.drawString("Time Attack",(int)(290*ratioX),40);
			f = new Font("Dialog",Font.PLAIN,(int)(20*ratioX));
			g.drawString("Short",(int)(20*ratioX),90);
			g.drawString("Medium",(int)(20*ratioX+500/3.0*ratioX),90);
			g.drawString("Long",(int)(20*ratioX+500/1.4*ratioX),90);
		}
	}

}
