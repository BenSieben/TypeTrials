import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * JPanel that allows certain settings
 * within TypeTrials to be modified
 * @author Ben Sieben
 * @version 25 May 2013
 */
public class OptionPanel extends JPanel{
	
	private TypeGame myFrame;
	private int width, height, mouseY, highlightSection;
	private double ratioX, ratioY;
	
	/**
	 * Creates a new OptionPanel
	 * @param w the TypeGame this OptionPanel works with
	 */
	public OptionPanel(TypeGame w){
		myFrame = w;
		width = getWidth();
		height = getHeight();
		mouseY = 0;
		ratioX = 1;
		ratioY = 1;
		highlightSection = -10;
	}
	
	/**
	 * Draws the OptionPanel
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		width = getWidth();
		height = getHeight();
		ratioX = width/500.0;
		ratioY = height/500.0;
		setBackground(Color.BLACK);
		for(int i = 0; i <= 200; i++){
			g.setColor(new Color(i,i,i));
			g.fillRect((int)(50*ratioX)-(int)((i-100)*ratioX),(int)((highlightSection*60+80)*ratioY),(int)(125*ratioX),(int)(60*ratioY));
		}
		g.setColor(Color.WHITE);
		Font f = new Font("Dialog",Font.BOLD,(int)(40*ratioX));
		g.setFont(f);
		g.drawString("OPTIONS",(int)(10*ratioX),(int)(75*ratioY));
		f = new Font("Dialog",Font.PLAIN,(int)(30*ratioX));
		g.setFont(f);
		g.drawString("Add to Dictionaries",(int)(10*ratioX),(int)(180*ratioY));
		g.drawString("Clear Leaderboards",(int)(10*ratioX),(int)(240*ratioY));
	}

	/**
	 * Runs whenever the mouse moves; so the 
	 * program needs to determine what to do 
	 * because it may have to do something
	 */
	public void mouseMoved(MouseEvent e) {
		mouseY = e.getY()-(int)(20*ratioY);
		if(mouseY >= (int)(150*ratioY) && mouseY <= (int)(270*ratioY)){
			if(mouseY <= (int)(210*ratioY)){
				highlightSection = 1;
			}else if(mouseY <= (int)(270*ratioY)){
				highlightSection = 2;
			}
		}else{
			highlightSection = -10;
		}
		repaint();
	}

	/**
	 * Runs whenever a mouse click is detected;
	 * so program needs to evaluate what to do
	 * based on where the mouse is during the click
	 */
	public void mouseClicked(MouseEvent e) {
		if(highlightSection == 1){//Add to dictionaries
			Object o = JOptionPane.showInputDialog(null,"Please enter a word to add to the dictionaries",
					"Add Word to Dictionaries",JOptionPane.QUESTION_MESSAGE);
			if(o != null){
				String s = (String)o;
				addWord(s);
			}
		}else if(highlightSection == 2){//Clear leaderboards
			clearLeaderboards();
		}
		repaint();
	}
	
	//Asks to confirm if user wants to really clear
	//the leaderboards or not, and then takes appropriate
	//action
	private void clearLeaderboards(){
		int n = JOptionPane.showConfirmDialog(null,
			    "Clear the leaderboards?",
			    "Clear Leaderboard Confirmation",
			    JOptionPane.YES_NO_OPTION);
		if(n == 0){//confirm clearing leaderboards
			myFrame.resetLeaderboards();
			JOptionPane.showMessageDialog(this,"Leaderboard has been reset.",
					"Leaderboard Reset",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	//Adds a word to the dictionaries, if appropriate
	private void addWord(String s){
		s = s.toLowerCase();
		int wordLength = s.length();
		if(wordLength < 2 || wordLength > 15){ //either too short or too long
			JOptionPane.showMessageDialog(this,"Error: word entry should be between 2 and 15 characters long.",
					"Inappropriate Entry Detected",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		boolean hasNonLetter = false;
		for(int i = 0; i < wordLength; i++){ //checking for any nonletters
			if(Character.isLetter(s.charAt(i)) == false)
				hasNonLetter = true;
		}
		if(hasNonLetter){//not all the characters in the entry are letters
			JOptionPane.showMessageDialog(this,"Error: word entry contains at least one non-letter.",
					"Inappropriate Entry Detected",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(wordLength <= 4){//adding to the short dictionary
			WordManager m = new WordManager("ShortDictionary.txt");
			String contents = m.getContents();
			if(contents.indexOf(s) != -1){//word is found in the dictionary already
				showErrorMessage();
				return;
			}
			m.addString(s);
			showSuccessMessage(s);
			contents = m.getContents();
			contents = contents.replaceAll(" ",System.getProperty("line.separator"));
			m.saveData(contents);
		}else if(wordLength <= 8){//adding to the medium dictionary
			WordManager m = new WordManager("MediumDictionary.txt");
			String contents = m.getContents();
			if(contents.indexOf(s) != -1){//word is found in the dictionary already
				showErrorMessage();
				return;
			}
			m.addString(s);
			showSuccessMessage(s);
			contents = m.getContents();
			contents = contents.replaceAll(" ",System.getProperty("line.separator"));
			m.saveData(contents);
		}else{//adding to the long dictionary
			WordManager m = new WordManager("LongDictionary.txt");
			String contents = m.getContents();
			if(contents.indexOf(s) != -1){//word is found in the dictionary already
				showErrorMessage();
				return;
			}
			m.addString(s);
			showSuccessMessage(s);
			contents = m.getContents();
			contents = contents.replaceAll(" ",System.getProperty("line.separator"));
			m.saveData(contents);
		}
		
	}
	
	//Used 3 times in addWord(), so just put here to only have to type it out once
	private void showErrorMessage(){
		JOptionPane.showMessageDialog(this,"Error: word entry already exists in the dictionary.",
				"Inappropriate Entry Detected",
				JOptionPane.ERROR_MESSAGE);
	}
	
	//Used 3 times in addWord(), so just put here to only have to type it out once
	private void showSuccessMessage(String s){
		JOptionPane.showMessageDialog(this,"Word ("+s+") successfully added to the dictionaries",
				"Successful Word Entry",
				JOptionPane.INFORMATION_MESSAGE);
	}

}
